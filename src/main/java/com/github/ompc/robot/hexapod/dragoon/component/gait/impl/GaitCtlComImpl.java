package com.github.ompc.robot.hexapod.dragoon.component.gait.impl;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
import com.github.ompc.robot.hexapod.dragoon.device.ServoCtlPiDev;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


@Component
public class GaitCtlComImpl implements GaitCtlCom, Runnable, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServoCtlPiDev servoCtlPiDev;

    private final GaitServoMapper gaitServoMapper = new GaitServoMapper();

    // 步态控制队列
    private final LinkedBlockingQueue<GaitWrap> gaitWrapQueue = new LinkedBlockingQueue<>();

    private final ReentrantLock waitingLock = new ReentrantLock();
    private final Condition waitingCompleteCondition = waitingLock.newCondition();

    @Override
    public void execute(Gait gait, CompletedCallback callback) {
        gaitWrapQueue.offer(new GaitWrap(gait, callback));
    }

    @Override
    public void interrupt() {

        // 清理仍然在步态队列中等待执行的步态
        while (true) {
            final GaitWrap gaitWrap = gaitWrapQueue.poll();
            if (null == gaitWrap) {
                break;
            }
            gaitWrap.callback.onCompleted(Response.interrupted());
        }

        // 通知仍然在等待命令完成的LOOP跳出命令等待
        waitingLock.lock();
        try {
            waitingCompleteCondition.signal();
        } finally {
            waitingLock.unlock();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        final Thread gaitExecutorDaemon = new Thread(this, "DRAGOON-GAIT-EXECUTOR");
        gaitExecutorDaemon.setDaemon(true);
        gaitExecutorDaemon.start();

    }


    // 转换为舵机控制命令
    private ServoCtlPiDev.ServoCmd[] getCmds(final Gait gait) {
        return gait.getPoses().stream()
                .map(pose -> new ServoCtlPiDev.ServoCmd(
                        gaitServoMapper.getServoIndex(
                                pose.getLimb(),
                                pose.getJoint()
                        ),
                        pose.getAngle()
                ))
                .collect(Collectors.toList())
                .toArray(new ServoCtlPiDev.ServoCmd[]{});
    }


    @Override
    public void run() {

        logger.info("{} started!", Thread.currentThread().getName());

        while (true) {

            try {

                final GaitWrap gaitWrap = gaitWrapQueue.take();
                try {
                    Gait gait = gaitWrap.gait;

                    /*
                     * 执行步态(组)
                     */
                    boolean isInterrupted;
                    do {

                        /*
                         * 发送舵机命令
                         */
                        servoCtlPiDev.control(
                                gait.getDurationMs(),
                                getCmds(gait)
                        );

                        /*
                         * 等待舵机命令完成
                         * 因为这台机器的舵机控制没有回馈信号，所以程序不知道什么时候命令完成
                         * 这里根据步态的执行时间做一个短暂的休眠
                         */
                        waitingLock.lock();
                        try {
                            if(isInterrupted = waitingCompleteCondition.await(gait.getDurationMs(), TimeUnit.MILLISECONDS)) {
                                break;
                            }
                        } catch (InterruptedException ie) {
                            isInterrupted = true;
                            break;
                        } finally {
                            waitingLock.unlock();
                        }

                    } while (gait.hasNext() && (gait = gait.getNext()) != null);

                    // 步态执行完成回馈
                    if (null != gaitWrap.callback) {
                        try {
                            gaitWrap.callback.onCompleted(
                                    isInterrupted
                                            ? Response.interrupted()
                                            : Response.success()
                            );
                        } catch (Exception cause) {
                            logger.warn("gait execute completed fire occur error!", cause);
                        }
                    }

                } catch (Exception causeOfControl) {
                    if (null != gaitWrap.callback) {
                        gaitWrap.callback.onCompleted(Response.exception(causeOfControl));
                    }
                } // try-catch for gait

            } catch (Exception cause) {
                logger.warn("gait execute failed.", cause);
            } // try-catch for LOOP

        }

    }


    class GaitWrap {

        private final Gait gait;
        private final CompletedCallback callback;

        GaitWrap(Gait gait, CompletedCallback callback) {
            this.gait = gait;
            this.callback = callback;
        }

    }

}

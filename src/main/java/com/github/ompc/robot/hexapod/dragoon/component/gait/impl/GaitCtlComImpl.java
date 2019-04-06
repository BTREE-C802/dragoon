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
import java.util.stream.Collectors;


@Component
public class GaitCtlComImpl implements GaitCtlCom, Runnable, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServoCtlPiDev servoCtlPiDev;

    private final GaitServoMapper gaitServoMapper = new GaitServoMapper();

    // 步态控制队列
    private final LinkedBlockingQueue<GaitWrap> gaitWrapQueue = new LinkedBlockingQueue<>();

    @Override
    public void execute(Gait gait, CompletedCallback callback) {
        gaitWrapQueue.offer(new GaitWrap(gait, callback));
    }

    @Override
    public void interrupt() {

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
                                pose.getLeg(),
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

        for (; ; ) {

            try {
                final GaitWrap gaitWrap = gaitWrapQueue.take();
                Gait gait = gaitWrap.gait;
                do {
                    servoCtlPiDev.control(
                            gait.getDurationMs(),
                            getCmds(gait)
                    );
                } while (gait.hasNext() && (gait = gait.getNext()) != null);

                // callback
                if (null != gaitWrap.callback) {
                    try {
                        gaitWrap.callback.onCompleted(false);
                    } catch (Exception cause) {
                        logger.warn("gait execute completed fire occur error!", cause);
                    }
                }
            } catch (Exception cause) {
                logger.warn("gait execute failed.", cause);
            }

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

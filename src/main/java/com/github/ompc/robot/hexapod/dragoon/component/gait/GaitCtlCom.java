package com.github.ompc.robot.hexapod.dragoon.component.gait;

import com.github.ompc.robot.hexapod.dragoon.component.Com;

/**
 * 步态控制组件
 */
public interface GaitCtlCom extends Com {

    /**
     * 执行步态
     *
     * @param gait     龙骑兵将要执行的步态
     * @param callback 步态执行完成回调
     */
    void execute(Gait gait, CompletedCallback callback);

    /**
     * 立即中断所有未执行的步态，当前步态允许执行完
     */
    void interrupt();


    /**
     * 步态完成回调
     */
    interface CompletedCallback {

        /**
         * 步态执行完成
         *
         * @param isInterrupted 是否被中断
         */
        void onCompleted(boolean isInterrupted);

    }

}

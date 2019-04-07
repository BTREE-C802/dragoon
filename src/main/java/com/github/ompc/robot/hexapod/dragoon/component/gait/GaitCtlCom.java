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
         * @param response 执行反馈
         */
        void onCompleted(Response response);

    }

    /**
     * 执行反馈
     */
    class Response {

        private final boolean isInterrupted;
        private final boolean isSuccess;
        private Exception exception;

        Response(boolean isInterrupted, boolean isSuccess) {
            this.isInterrupted = isInterrupted;
            this.isSuccess = isSuccess;
        }

        Response(Exception exception) {
            this(false, false);
            this.exception = exception;
        }

        /**
         * 是否被中断
         *
         * @return TRUE | FALSE
         */
        public boolean isInterrupted() {
            return isInterrupted;
        }

        /**
         * 是否执行成功
         *
         * @return TRUE | FALSE
         */
        public boolean isSuccess() {
            return isSuccess;
        }

        /**
         * 获取执行异常，如果执行失败则在此处获取异常信息；
         *
         * @return 执行异常 | null
         */
        public Exception getException() {
            return exception;
        }

        /**
         * 构造中断反馈
         *
         * @return 中断反馈
         */
        public static Response interrupted() {
            return new Response(true, false);
        }

        /**
         * 构造成功反馈
         *
         * @return 成功反馈
         */
        public static Response success() {
            return new Response(false, true);
        }

        /**
         * 构造异常反馈
         *
         * @param exception 异常
         * @return 异常反馈
         */
        public static Response exception(Exception exception) {
            return new Response(exception);
        }

    }

}

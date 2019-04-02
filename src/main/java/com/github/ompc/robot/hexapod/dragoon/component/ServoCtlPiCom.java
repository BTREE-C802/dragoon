package com.github.ompc.robot.hexapod.dragoon.component;

/**
 * 舵机控制组件
 */
public interface ServoCtlPiCom extends PiCom {

    /**
     * 控制舵机
     *
     * @param durationMs 控制持续时间（MS)
     * @param servoCmds  具体舵机控制转动角度集合
     * @throws PiComException 组件执行动作失败
     */
    void control(long durationMs, ServoCmd... servoCmds) throws PiComException;

    /**
     * 舵机命令
     */
    class ServoCmd {

        // 舵机编号
        private final int index;

        // 目标角度
        private final float angle;

        /**
         * 构造舵机命令
         *
         * @param index 舵机编号
         * @param angle 旋转目标角度
         */
        public ServoCmd(int index, float angle) {
            this.index = index;
            this.angle = angle;
        }

        /**
         * 获取舵机编号
         *
         * @return 舵机编号
         */
        public int getIndex() {
            return index;
        }

        /**
         * 获取旋转目标角度
         *
         * @return 旋转目标角度
         */
        public float getAngle() {
            return angle;
        }
    }

}

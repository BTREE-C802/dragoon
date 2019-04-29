package com.github.ompc.robot.hexapod.dragoon.device.servo;

import com.github.ompc.robot.hexapod.dragoon.device.PiDev;
import com.github.ompc.robot.hexapod.dragoon.device.PiDevException;

/**
 * 舵机控制组件
 */
public interface ServoPiDev extends PiDev {

    /**
     * 控制舵机
     *
     * @param durationMs 控制持续时间（MS)
     * @param servoCmds  具体舵机控制命令集合
     * @throws PiDevException 组件执行动作失败
     */
    void control(long durationMs, ServoCmd... servoCmds) throws PiDevException;

    /**
     * 舵机命令
     */
    class ServoCmd {

        // 舵机编号
        private final int index;

        // 目标弧度
        private final double radian;

        /**
         * 构造舵机命令
         *
         * @param index  舵机编号
         * @param radian 旋转目标弧度
         */
        public ServoCmd(int index, double radian) {
            this.index = index;
            this.radian = radian;
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
         * 获取旋转目标弧度
         *
         * @return 旋转目标弧度
         */
        public double getRadian() {
            return radian;
        }
    }

}

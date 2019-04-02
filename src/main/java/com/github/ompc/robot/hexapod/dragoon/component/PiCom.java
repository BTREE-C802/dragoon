package com.github.ompc.robot.hexapod.dragoon.component;

/**
 * 树莓派组件接口
 */
public interface PiCom {

    Type getType();

    /**
     * 组件类型
     */
    enum Type {

        /**
         * 舵机控制
         */
        SERVO_CTL

    }

}

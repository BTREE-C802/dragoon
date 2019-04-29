package com.github.ompc.robot.hexapod.dragoon.device.servo;

/**
 * 舵机脉宽计算器
 */
public interface PulseWidthComputer {

    /**
     * 根据舵机编号计算舵机弧度和脉宽的关系
     *
     * @param servoIndex 舵机编号
     * @param radian     弧度
     * @return 脉宽
     */
    int compute(int servoIndex, double radian);


}

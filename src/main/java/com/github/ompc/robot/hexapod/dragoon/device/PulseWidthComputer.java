package com.github.ompc.robot.hexapod.dragoon.device;

/**
 * 舵机脉宽计算器
 */
public interface PulseWidthComputer {

    /**
     * 根据舵机编号计算舵机夹角和脉宽的关系
     *
     * @param servoIndex 舵机编号
     * @param angle      夹角
     * @return 脉宽
     */
    int compute(int servoIndex, float angle);


}

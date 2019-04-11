package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.math.BigDecimal;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Radians.RAD_PI_QUARTER;

/**
 * 关节
 */
public enum Joint {

    HIP(0),
    KNE(0),
    ANK(RAD_PI_QUARTER);

    private final double deviation;

    Joint(double deviation) {
        this.deviation = deviation;
    }

    Joint(BigDecimal deviation) {
        this.deviation = deviation.doubleValue();
    }


    /**
     * 获取关节偏移值（弧度）
     *
     * @return 关节偏移值（弧度）
     */
    public double getDeviation() {
        return deviation;
    }

    /**
     * 所有关节
     */
    public static Joint[] JOINT_ALL = Joint.values();


}

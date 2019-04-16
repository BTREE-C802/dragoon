package com.github.ompc.robot.hexapod.dragoon.component.gait.builder;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Pose;

import java.math.BigDecimal;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class PoseOperations {

    public static final BigDecimal RAD_PI = BigDecimal.valueOf(Math.PI);
    public static final BigDecimal RAD_PI_ZERO = BigDecimal.ZERO;
    public static final BigDecimal RAD_PI_HALF = RAD_PI.divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_EVEN);
    public static final BigDecimal RAD_PI_QUARTER = RAD_PI.divide(BigDecimal.valueOf(4), BigDecimal.ROUND_HALF_EVEN);

    public static final double LIMB_UPPER_LENGTH = 50.0d;
    public static final double LIMB_LOWER_LENGTH = 50.0d;
    public static final double LIMB_LENGTH = LIMB_UPPER_LENGTH + LIMB_LOWER_LENGTH;
    public static final BigDecimal RAD_ANK_DEVIATION = RAD_PI_QUARTER;

    /**
     * 站立姿势
     *
     * @param highMm 站立高度（毫米）
     * @return 站立姿势
     */
    public static final Pose[] stand(final int highMm) {
        final BigDecimal rate = RAD_PI.divide(BigDecimal.valueOf(LIMB_LENGTH), ROUND_HALF_EVEN);
        final BigDecimal tmp = BigDecimal.valueOf(highMm).multiply(rate);
        return new PoseSelector()
                .limb().joint(HIP).radian(RAD_PI_HALF)
                .limb().joint(ANK).radian(RAD_PI.subtract(tmp).add(RAD_ANK_DEVIATION))
                .limb().joint(KNE).radian(RAD_PI.subtract(tmp))
                .selected();
    }

}

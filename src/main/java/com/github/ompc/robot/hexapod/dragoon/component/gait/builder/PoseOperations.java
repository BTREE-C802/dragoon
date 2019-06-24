package com.github.ompc.robot.hexapod.dragoon.component.gait.builder;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Limb;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Pose;

import java.math.BigDecimal;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.LIMB_ALL_L;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.LIMB_ALL_R;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.builder.PoseSelector.select;
import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigDecimal.valueOf;

public class PoseOperations {

    public static final BigDecimal RAD_PI = valueOf(Math.PI);
    public static final BigDecimal RAD_PI_ZERO = BigDecimal.ZERO;
    public static final BigDecimal RAD_PI_HALF = RAD_PI.divide(valueOf(2), BigDecimal.ROUND_HALF_EVEN);
    public static final BigDecimal RAD_PI_QUARTER = RAD_PI.divide(valueOf(4), BigDecimal.ROUND_HALF_EVEN);

    public static final double LIMB_UPPER_LENGTH = 50.0d;
    public static final double LIMB_LOWER_LENGTH = 70.0d;
    public static final double LIMB_LENGTH = LIMB_UPPER_LENGTH + LIMB_LOWER_LENGTH;
    public static final BigDecimal RAD_ANK_DEVIATION = RAD_PI_QUARTER;

    /**
     * 站立姿势
     *
     * @param highMm 站立高度（毫米）
     * @return 站立姿势
     */
    public static final Pose[] stand(final int highMm) {
        final BigDecimal rate = RAD_PI.divide(valueOf(LIMB_LENGTH), ROUND_HALF_EVEN);
        final BigDecimal radianOfK6 = valueOf(highMm).multiply(rate);
        final BigDecimal radianOfA6 = RAD_PI.add(RAD_PI_QUARTER).subtract(radianOfK6);
        return select()
                .limb(LIMB_ALL_R).joint(KNE).radian(radianOfK6)
                .limb(LIMB_ALL_R).joint(ANK).radian(radianOfA6)
                .limb(LIMB_ALL_L).joint(KNE).radian(RAD_PI.subtract(radianOfK6))
                .limb(LIMB_ALL_L).joint(ANK).radian(RAD_PI.subtract(radianOfA6))
                .limb().joint(HIP).radian(RAD_PI_HALF)
                .selected();
    }

    public static final Pose[] limbDown(final Limb limb, final double radian) {
        return limbUp(limb, -1 * radian);
    }

    public static final Pose[] limbUp(final Limb limb, final double radian) {
        switch (limb) {
            case L_F:
            case L_M:
            case L_H:
                return select()
                        .limb(limb).joint(KNE).radian(radian)
                        .selected();
            case R_F:
            case R_M:
            case R_H:
            default:
                return select()
                        .limb(limb).joint(KNE).radian(-1 * radian)
                        .selected();
        }
    }

    public static final Pose[] limbBack(final Limb limb, final double radian) {
        return limbFront(limb, -1 * radian);
    }

    public static final Pose[] limbFront(final Limb limb, final double radian) {
        switch (limb) {
            case L_F:
            case L_M:
            case L_H:
                return select()
                        .limb(limb).joint(HIP).radian(-1 * radian)
                        .selected();
            case R_F:
            case R_M:
            case R_H:
            default:
                return select()
                        .limb(limb).joint(HIP).radian(radian)
                        .selected();
        }
    }

}

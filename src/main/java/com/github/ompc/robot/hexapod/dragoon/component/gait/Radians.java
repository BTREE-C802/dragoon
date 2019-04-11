package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.math.BigDecimal;

public class Radians {

    public static final BigDecimal RAD_PI = BigDecimal.valueOf(Math.PI);
    public static final BigDecimal RAD_PI_ZERO = BigDecimal.ZERO;
    public static final BigDecimal RAD_PI_HALF = RAD_PI.divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_EVEN);
    public static final BigDecimal RAD_PI_QUARTER = RAD_PI.divide(BigDecimal.valueOf(4), BigDecimal.ROUND_HALF_EVEN);

    public static final double rad_pi = RAD_PI.doubleValue();
    public static final double rad_pi_zero = RAD_PI_ZERO.doubleValue();
    public static final double rad_pi_half = RAD_PI_HALF.doubleValue();
    public static final double rad_pi_quarter = RAD_PI_QUARTER.doubleValue();

}

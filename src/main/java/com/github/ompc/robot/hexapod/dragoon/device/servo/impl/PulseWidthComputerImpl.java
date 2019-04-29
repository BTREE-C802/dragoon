package com.github.ompc.robot.hexapod.dragoon.device.servo.impl;

import com.github.ompc.robot.hexapod.dragoon.device.servo.PulseWidthComputer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.lang.Math.PI;

@Component
public class PulseWidthComputerImpl implements PulseWidthComputer {

    private static final int PULSE_WIDTH_MIN = 500;
    private static final int PULSE_WIDTH_MAX = 2500;
    private static final BigDecimal RADIAN_MIN = BigDecimal.ZERO;
    private static final BigDecimal RADIAN_MAX = BigDecimal.valueOf(PI);
    private static final BigDecimal RADIAN_PW_RATE = BigDecimal.valueOf(PULSE_WIDTH_MAX - PULSE_WIDTH_MIN).divide(RADIAN_MAX, BigDecimal.ROUND_HALF_EVEN);

    @Override
    public int compute(int servoIndex, double radian) {
//        switch (servoIndex) {
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//            case 5:
//            case 6:
//            case 7:
//            case 8:
//            case 9:
//                return computePulseWidth(RADIAN_MAX.subtract(BigDecimal.valueOf(radian)));
//            default:
//                return computePulseWidth(BigDecimal.valueOf(radian));
//        }
        return computePulseWidth(BigDecimal.valueOf(radian));
    }

    // 弧度换算脉宽
    private int computePulseWidth(BigDecimal radian) {
        return modifyPulseWidth(PULSE_WIDTH_MIN + radian.multiply(RADIAN_PW_RATE).intValue());
    }

    // 修正计算出的频宽误差，必须控制在min~max之间
    private int modifyPulseWidth(int pw) {
        if (pw <= PULSE_WIDTH_MIN) {
            return PULSE_WIDTH_MIN;
        } else if (pw >= PULSE_WIDTH_MAX) {
            return PULSE_WIDTH_MAX;
        }
        return pw;
    }

}

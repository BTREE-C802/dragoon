package com.github.ompc.robot.hexapod.dragoon.device.impl;

import com.github.ompc.robot.hexapod.dragoon.device.PulseWidthComputer;
import org.springframework.stereotype.Component;

@Component
public class PulseWidthComputerImpl implements PulseWidthComputer {

    private static final float ANGLE_MIN = 0.0f;
    private static final float ANGLE_MAX = 180.0f;
    private static final float CYCLE_MAX = 360.0f;
    private static final int PULSE_WIDTH_MIN = 500;
    private static final int PULSE_WIDTH_MAX = 2500;
    private static final float AP_RATE = (PULSE_WIDTH_MAX - PULSE_WIDTH_MIN) / ANGLE_MAX;

    @Override
    public int compute(int servoIndex, float angle) {
        final float fixAngle = fixAngle(angle);
        switch (servoIndex) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return computePulseWidth(180.0f - fixAngle);
            default:
                return computePulseWidth(fixAngle);
        }
    }

    // 角度换算脉宽
    private int computePulseWidth(float angle) {
        return fixPw((int) (PULSE_WIDTH_MIN + angle * AP_RATE));
    }

    private float fixAngle(final float angle) {
        final float angleInCycle = angle % CYCLE_MAX;
        return angleInCycle < 0
                ? CYCLE_MAX + angleInCycle
                : angleInCycle;
    }

    // 修正计算出的频宽误差，必须控制在min~max之间
    private int fixPw(int pw) {
        if (pw <= PULSE_WIDTH_MIN) {
            return PULSE_WIDTH_MIN;
        } else if (pw >= PULSE_WIDTH_MAX) {
            return PULSE_WIDTH_MAX;
        }
        return pw;
    }

}

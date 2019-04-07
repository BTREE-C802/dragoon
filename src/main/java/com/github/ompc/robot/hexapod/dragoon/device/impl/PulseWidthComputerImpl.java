package com.github.ompc.robot.hexapod.dragoon.device.impl;

import com.github.ompc.robot.hexapod.dragoon.device.PulseWidthComputer;
import org.springframework.stereotype.Component;

@Component
public class PulseWidthComputerImpl implements PulseWidthComputer {

    private static final int PULSE_WIDTH_MIN = 500;
    private static final int PULSE_WIDTH_MAX = 2500;
    private static final float AP_RATE = (PULSE_WIDTH_MAX - PULSE_WIDTH_MIN) / 180.0f;

    @Override
    public int compute(int servoIndex, float angle) {
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
                return computePulseWidth(180.0f - angle);
            default:
                return computePulseWidth(angle);
        }
    }

    // 角度换算脉宽
    private int computePulseWidth(float angle) {
        return (int) (PULSE_WIDTH_MIN + angle * AP_RATE);
    }

}

package com.github.ompc.robot.hexapod.dragoon.device;

/**
 * 树莓派设备异常
 */
public class PiDevException extends Exception {

    private final PiDev.Type type;

    public PiDevException(final PiDev.Type type,
                          final Exception cause) {
        super(cause);
        this.type = type;
    }

    public PiDev.Type getType() {
        return type;
    }

}

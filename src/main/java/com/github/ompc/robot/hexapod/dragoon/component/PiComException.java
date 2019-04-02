package com.github.ompc.robot.hexapod.dragoon.component;

/**
 * 树莓派组件异常
 */
public class PiComException extends Exception {

    private final PiCom.Type type;

    public PiComException(final PiCom.Type type,
                          final Exception cause) {
        super(cause);
        this.type = type;
    }

    public PiCom.Type getType() {
        return type;
    }
    
}

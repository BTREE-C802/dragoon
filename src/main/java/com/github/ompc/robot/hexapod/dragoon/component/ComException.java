package com.github.ompc.robot.hexapod.dragoon.component;

/**
 * 组件异常
 */
public class ComException extends Exception {

    private final Type type;

    public ComException(final Type type, final String message, final Exception cause) {
        super(message, cause);
        this.type = type;
    }

    public ComException(final Type type, final String message) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    /**
     * 异常类型
     */
    public enum Type {

        /**
         * 消息异常
         */
        MESSENGER
    }

}

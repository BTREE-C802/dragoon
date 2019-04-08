package com.github.ompc.robot.hexapod.dragoon.component.remote;

/**
 * 远程控制命令
 */
public class RemoteCmd<T> {

    // 命令类型
    private final Type type;

    // 命令数据
    private final T data;

    public RemoteCmd(final Type type, final T data) {
        this.type = type;
        this.data = data;
    }

    /**
     * 获取命令类型
     *
     * @return 命令类型
     */
    public Type getType() {
        return type;
    }

    /**
     * 获取命令数据
     *
     * @return 命令数据
     */
    public T getData() {
        return data;
    }

    /**
     * 命令类型
     */
    public enum Type {

        /**
         * 步态调整命令
         */
        GAIT,

        /**
         * 步态调整终止命令
         */
        GAIT_INTERRUPT

    }


}

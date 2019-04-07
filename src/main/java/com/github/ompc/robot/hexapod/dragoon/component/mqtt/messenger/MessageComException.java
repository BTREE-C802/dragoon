package com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger;

import com.github.ompc.robot.hexapod.dragoon.component.ComException;

public class MessageComException extends ComException {

    private final Code code;

    public MessageComException(final Code code,
                               final String message,
                               final Exception cause) {
        super(Type.MESSENGER, message, cause);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    /**
     * 错误码
     */
    public enum Code {

        /**
         * 发布消息错误：通用错误
         */
        PUBLISH_ERROR,

        /**
         * 发布消息错误：超时
         */
        PUBLISH_TIMEOUT,

        /**
         * 订阅消息错误：通用错误
         */
        SUBSCRIBE_ERROR,

    }

}

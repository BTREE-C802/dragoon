package com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger;

import java.nio.charset.Charset;

/**
 * 文本消息监听器
 */
public abstract class TextMessageListener implements MessageListener {

    private final Charset charset;

    /**
     * 构建文本消息监听器
     *
     * @param charset 字符编码
     */
    public TextMessageListener(Charset charset) {
        this.charset = charset;
    }

    /**
     * 构建文本消息监听器(采用系统默认编码)
     */
    public TextMessageListener() {
        this.charset = Charset.defaultCharset();
    }

    @Override
    public void onMessage(String topic, byte[] message) {
        onMessage(topic, new String(message, charset));
    }

    abstract public void onMessage(String topic, String message);

}

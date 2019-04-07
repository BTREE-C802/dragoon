package com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger;

/**
 * 消息接收监听器
 */
public interface MessageListener {

    /**
     * 接收消息
     *
     * @param topic   消息主题
     * @param message 消息内容
     */
    void onMessage(String topic, byte[] message);

}

package com.github.ompc.robot.hexapod.dragoon.component.mqtt.utils;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * MQTT工具类
 */
public class MqttUtils {

    public static void waitForCompletionWithException(IMqttToken token, long timeoutMs) throws MqttException {
        if (timeoutMs > 0) {
            token.waitForCompletion(timeoutMs);
        } else {
            token.waitForCompletion();
        }
        assert token.isComplete();
        if (null != token.getException()) {
            throw token.getException();
        }
    }

    public static void waitForCompletionWithException(IMqttToken token) throws MqttException {
        waitForCompletionWithException(token, -1L);
    }

}

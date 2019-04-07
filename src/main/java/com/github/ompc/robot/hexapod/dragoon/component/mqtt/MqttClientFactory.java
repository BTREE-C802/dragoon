package com.github.ompc.robot.hexapod.dragoon.component.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * MQTT客户端工厂
 */
public interface MqttClientFactory {

    /**
     * 生产MQTT客户端
     *
     * @param config MQTT客户端配置
     * @return MQTT客户端
     * @throws MqttException 生产失败
     */
    MqttClient make(MqttClientConfig config) throws MqttException;

}

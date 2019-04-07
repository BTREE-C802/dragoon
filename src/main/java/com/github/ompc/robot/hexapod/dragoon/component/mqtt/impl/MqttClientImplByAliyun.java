package com.github.ompc.robot.hexapod.dragoon.component.mqtt.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.ScheduledExecutorService;

public class MqttClientImplByAliyun extends MqttClient {

    public MqttClientImplByAliyun(final String serverURI,
                                  final String clientId,
                                  final MqttClientPersistence persistence,
                                  final ScheduledExecutorService executorService) throws MqttException {
        super(serverURI, clientId, persistence, executorService);
    }

}

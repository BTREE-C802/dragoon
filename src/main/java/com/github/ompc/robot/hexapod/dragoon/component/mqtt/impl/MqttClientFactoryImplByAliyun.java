package com.github.ompc.robot.hexapod.dragoon.component.mqtt.impl;

import com.github.ompc.robot.hexapod.dragoon.component.mqtt.MqttClientConfig;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.MqttClientFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.github.ompc.robot.hexapod.dragoon.component.mqtt.utils.MqttUtils.waitForCompletionWithException;
import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

@Component
public class MqttClientFactoryImplByAliyun implements MqttClientFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MqttConnectOptions buildingMqttConnectOptions(MqttClientConfig config) {
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setConnectionTimeout(config.getConnectingTimeoutSec());
        mqttConnectOptions.setUserName(config.getMqttUsername());
        mqttConnectOptions.setPassword(config.getMqttPassword());
        mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(60);
        return mqttConnectOptions;
    }

    private MqttClient buildingMqttClient(MqttClientConfig config) throws MqttException {
        return new MqttClient(
                config.getMqttServerURI(),
                config.getMqttClientId(),
                new MemoryPersistence()
        );
    }

    @Override
    public MqttClient make(MqttClientConfig config) throws MqttException {
        final MqttClient client;
        waitForCompletionWithException(
                (client = buildingMqttClient(config))
                        .connectWithResult(buildingMqttConnectOptions(config))
        );
        logger.info("server:{} connected!", client.getServerURI());
        return client;
    }
}

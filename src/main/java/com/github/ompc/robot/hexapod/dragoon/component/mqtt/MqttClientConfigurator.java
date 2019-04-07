package com.github.ompc.robot.hexapod.dragoon.component.mqtt;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@Configuration
public class MqttClientConfigurator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MqttClientConfig config;

    @Autowired
    private MqttClientFactory factory;

    private MqttClient withCallback(final MqttClient mqttClient) {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                logger.warn(
                        "client:{} lost connection, try reconnect! client -> server:{}",
                        mqttClient.getClientId(),
                        mqttClient.getServerURI(),
                        cause
                );
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                logger.info(
                        "client:{} received topic:{};message:{}",
                        mqttClient.getClientId(),
                        topic,
                        formatMqttMessage(message)
                );
            }

            private String formatMqttMessage(final MqttMessage mqttMessage) {
                return String.format(
                        "[id:%s|qos:%s|dup:%s|retain:%s|payload:%s(bytes)]|%s",
                        mqttMessage.getId(),
                        mqttMessage.getQos(),
                        mqttMessage.isDuplicate(),
                        mqttMessage.isRetained(),
                        ArrayUtils.getLength(mqttMessage.getPayload()),
                        new String(mqttMessage.getPayload())
                );
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                logger.info(
                        "client:{} delivery completed! topics:{}",
                        mqttClient.getClientId(),
                        CollectionUtils.arrayToList(token.getTopics())
                );
            }
        });
        return mqttClient;
    }

    @Bean
    public MqttClient getMqttClient() throws MqttException {
        return factory.make(config);
    }


}

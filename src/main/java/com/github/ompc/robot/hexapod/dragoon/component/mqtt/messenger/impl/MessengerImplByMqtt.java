package com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.impl;

import com.github.ompc.robot.hexapod.dragoon.component.mqtt.MqttClientConfig;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageListener;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException.Code.PUBLISH_ERROR;
import static com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException.Code.SUBSCRIBE_ERROR;
import static com.github.ompc.robot.hexapod.dragoon.component.mqtt.utils.MqttUtils.waitForCompletionWithException;
import static java.lang.String.format;

@Component
public class MessengerImplByMqtt implements Messenger {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private MqttClientConfig mqttConfig;

    @Override
    public void publish(PublishMode mode, String topic, byte[] message, boolean retained) throws MessageComException {
        final MqttTopic publishMqttTopic = mqttClient.getTopic(topic);
        try {
            waitForCompletionWithException(
                    publishMqttTopic.publish(message, mode.getValue(), retained),
                    mqttConfig.getPublishTimeoutMs()
            );
            logger.info("publish topic:{} success", publishMqttTopic);
        } catch (MqttException cause) {
            throw new MessageComException(
                    PUBLISH_ERROR,
                    format("publish topic:%s occur error! code:%s;reason:%s;", topic, cause.getReasonCode(), cause.getMessage()),
                    cause
            );
        }
    }

    @Override
    public void subscribe(String topic, MessageListener listener) throws MessageComException {
        try {
            waitForCompletionWithException(
                    mqttClient.subscribeWithResponse(topic, (t, message) -> {
                        listener.onMessage(t, message.getPayload());
                    }),
                    mqttConfig.getPublishTimeoutMs()
            );
            logger.info("subscribe topic:{} success", topic);
        } catch (MqttException cause) {
            throw new MessageComException(
                    SUBSCRIBE_ERROR,
                    format("subscribe topic:%s occur error! code=%s;reason:%s;", topic, cause.getReasonCode(), cause.getMessage()),
                    cause
            );
        }
    }

    @Override
    public void unsubscribe(String topic) throws MessageComException {
        try {
            mqttClient.unsubscribe(topic);
            logger.info("unsubscribe topic:{} success", topic);
        } catch (MqttException cause) {
            throw new MessageComException(
                    SUBSCRIBE_ERROR,
                    format("unsubscribe topic:%s occur error! code=%s;reason:%s;", topic, cause.getReasonCode(), cause.getMessage()),
                    cause
            );
        }
    }

}

package com.github.ompc.robot.hexapod.dragoon.component.mqtt.impl;

import com.github.ompc.robot.hexapod.dragoon.component.mqtt.MqttClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.github.ompc.robot.hexapod.dragoon.component.mqtt.utils.AliyunIoTSignUtils.signByHMACSHA1;

@Component
public class MqttConfigImplByAliyun implements MqttClientConfig {

    @Value("${dragoon.mqtt.client-id}")
    private String clientId = UUID.randomUUID().toString();

    @Value("${dragoon.mqtt.server}")
    private String mqttServerURI;

    @Value("${dragoon.mqtt.connecting-timeout-sec}")
    private int connectingTimeoutSec = 30;

    @Value("${dragoon.mqtt.publish-timeout-ms}")
    private long publishTimeoutMs = 10000;

    @Value("${dragoon.mqtt.aliyun.product-key}")
    private String productKey;

    @Value("${dragoon.mqtt.aliyun.device-name}")
    private String deviceName;

    @Value("${dragoon.mqtt.aliyun.device-secret}")
    private String deviceSecret;

    private final long timestamp = System.currentTimeMillis();

    @Override
    public String getMqttServerURI() {
        return mqttServerURI;
    }

    @Override
    public String getMqttClientId() {
        return String.format(
                "%s|securemode=3,signmethod=hmacsha1,timestamp=%s|",
                clientId,
                timestamp
        );
    }

    @Override
    public String getMqttUsername() {
        return String.format("%s&%s", deviceName, productKey);
    }

    @Override
    public char[] getMqttPassword() {
        return signByHMACSHA1(clientId, productKey, deviceName, deviceSecret, timestamp).toCharArray();
    }

    @Override
    public int getConnectingTimeoutSec() {
        return connectingTimeoutSec;
    }

    @Override
    public long getPublishTimeoutMs() {
        return publishTimeoutMs;
    }
}

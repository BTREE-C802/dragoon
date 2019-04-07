package com.github.ompc.robot.hexapod.dragoon.component.mqtt;

/**
 * MQTT配置
 */
public interface MqttClientConfig {

    /**
     * 获取MQTT服务器地址
     *
     * @return MQTT服务器地址
     */
    String getMqttServerURI();

    /**
     * 获取MQTT客户端编号
     *
     * @return MQTT客户端编号
     */
    String getMqttClientId();

    /**
     * 获取MQTT账号
     *
     * @return MQTT账号
     */
    String getMqttUsername();

    /**
     * 获取MQTT密码
     *
     * @return MQTT密码
     */
    char[] getMqttPassword();

    /**
     * 获取连接超时设置(秒)
     *
     * @return 连接超时设置(秒)
     */
    int getConnectingTimeoutSec();

    /**
     * 获取发布超时时间设置(秒)
     *
     * @return 发布超时时间设置(秒)
     */
    long getPublishTimeoutMs();

}

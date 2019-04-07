package com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger;

/**
 * 消息信使
 */
public interface Messenger {

    /**
     * 同步发布消息
     *
     * @param mode    发布模式
     * @param topic   消息主题
     * @param message 消息内容
     * @throws MessageComException 同步发布消息失败
     */
    void publish(PublishMode mode, String topic, byte[] message) throws MessageComException;

    /**
     * 发布模式
     */
    enum PublishMode {

        /**
         * 最多一次
         */
        AT_MOST_ONCE(0),

        /**
         * 至少一次
         */
        AT_LEAST_ONCE(1),

        /**
         * 刚好一次
         */
        JUST_ONCE(2);

        private final int value;

        PublishMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 订阅消息
     *
     * @param topic    消息主题
     * @param listener 消息监听器
     * @throws MessageComException 订阅主题失败
     */
    void subscribe(String topic, MessageListener listener) throws MessageComException;

    /**
     * 退订消息
     *
     * @param topic 消息主题
     * @throws MessageComException 退订主题失败
     */
    void unsubscribe(String topic) throws MessageComException;

}

package com.github.ompc.robot.hexapod.dragoon.component.mqtt.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 阿里云IoT签名工具类
 */
public class AliyunIoTSignUtils {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * 使用HMAC-SHA1算法签名
     *
     * @param clientId     客户端ID
     * @param productKey   产品KEY
     * @param deviceName   设备名
     * @param deviceSecret 设备密钥
     * @param timestamp    时间戳
     * @return 签名字符串
     */
    public static String signByHMACSHA1(final String clientId,
                                        final String productKey,
                                        final String deviceName,
                                        final String deviceSecret,
                                        final long timestamp) {
        return sign(clientId, productKey, deviceName, deviceSecret, timestamp, "HMACSHA1");
    }

    /**
     * 签名
     *
     * @param clientId     客户端ID
     * @param productKey   产品KEY
     * @param deviceName   设备名
     * @param deviceSecret 设备密钥
     * @param timestamp    时间戳
     * @param signMethod   签名方式
     * @return 签名字符串
     */
    public static String sign(final String clientId,
                              final String productKey,
                              final String deviceName,
                              final String deviceSecret,
                              final long timestamp,
                              final String signMethod) {
        try {
            return encryptHMAC(
                    signMethod,
                    String.format(
                            "clientId%sdeviceName%sproductKey%stimestamp%s",
                            clientId,
                            deviceName,
                            productKey,
                            timestamp
                    ),
                    deviceSecret
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // HMAC加密
    private static String encryptHMAC(final String signMethod,
                                      final String content,
                                      final String key) throws NoSuchAlgorithmException, InvalidKeyException {
        final SecretKey secretKey = new SecretKeySpec(key.getBytes(UTF8), signMethod);
        final Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return bytesToHexString(mac.doFinal(content.getBytes(UTF8)));
    }

    // 数组转16进制字符串
    private static final String bytesToHexString(final byte[] bArray) {
        return DatatypeConverter.printHexBinary(bArray);
    }

}

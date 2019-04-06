package com.github.ompc.robot.hexapod.dragoon.device.impl;

import com.github.ompc.robot.hexapod.dragoon.device.PiDevException;
import com.github.ompc.robot.hexapod.dragoon.device.ServoCtlPiDev;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

@Component
public class ServoCtlPiDevImpl implements ServoCtlPiDev, InitializingBean {

    private static final byte[] MAGIC_CODE = {0x55, 0x55};
    private static final byte CMD_SERVO_MOVE = 0x03;

    private static final int MAX_NUM = 32;
    private static final int SIZE_OF_MC = MAGIC_CODE.length;
    private static final int SIZE_OF_CMD = 3;
    private static final int SIZE_OF_DATA_LENGTH = 2;
    private static final int SIZE_OF_NUM = 1;
    private static final int SIZE_OF_DURATION = 2;
    private static final int BUFFER_SIZE = SIZE_OF_MC + computeDataLength(MAX_NUM);

    private static final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE)
            .order(ByteOrder.LITTLE_ENDIAN);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Serial piSerial;

    @Override
    public void control(long durationMs, ServoCmd... servoCmds) throws PiDevException {

        if (null == servoCmds
                || servoCmds.length == 0) {
            return;
        }

        if (servoCmds.length > MAX_NUM) {
            throw new IllegalArgumentException(String.format("%s large then %s", servoCmds.length, MAX_NUM));
        }

        synchronized (buffer) {
            buffer.put(MAGIC_CODE)
                    .put(computeDataLength(servoCmds.length))
                    .put(CMD_SERVO_MOVE)
                    .put((byte) servoCmds.length)
                    .putShort((short) durationMs);
            Arrays.stream(servoCmds).forEach(cmd -> {
                buffer.put((byte) cmd.getIndex()).putShort(computeAngle(cmd.getAngle()));
            });

            buffer.flip();
            try {
                debug(buffer);
                piSerial.write(buffer);
                piSerial.flush();
            } catch (IOException cause) {
                throw new PiDevException(getType(), cause);
            } finally {
                buffer.flip();
            }
        }

    }

    private void debug(ByteBuffer buffer) {
        final byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        final StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("0x%02x", b)).append("|");
        }
        logger.info("piSerial.write:{}", sb.toString());
        buffer.position(buffer.position() - data.length);
    }


    // 计算指令的数据长度
    private static byte computeDataLength(int num) {
        return (byte) (
                SIZE_OF_DATA_LENGTH
                        + SIZE_OF_NUM
                        + SIZE_OF_DURATION
                        + SIZE_OF_CMD * num
        );
    }

    // 计算舵机角度和脉宽的换算(角度->脉宽)
    private short computeAngle(float angle) {
        final int pwMin = 500;
        final int pwMax = 2500;
        final double apRate = (pwMax - pwMin) / 180.0;
        return (short) (pwMin + angle * apRate);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        piSerial.open(
                new SerialConfig()
                        .device(SerialPort.getDefaultPort())
                        .baud(Baud._9600)
        );
    }

    @Override
    public Type getType() {
        return Type.SERVO_CTL;
    }
}

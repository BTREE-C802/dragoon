package com.github.ompc.robot.hexapod.dragoon.component.impl;

import com.github.ompc.robot.hexapod.dragoon.component.PiComException;
import com.github.ompc.robot.hexapod.dragoon.component.ServoCtlPiCom;
import com.pi4j.io.serial.*;
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
public class ServoCtlPiComImpl implements ServoCtlPiCom, InitializingBean {

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
            .order(ByteOrder.BIG_ENDIAN);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Serial piSerial;

    @Override
    public void control(long durationMs, ServoCmd... servoCmds) throws PiComException {

        if (null == servoCmds
                || servoCmds.length == 0) {
            return;
        }

        if (servoCmds.length > MAX_NUM) {
            throw new IllegalArgumentException(String.format("%s large then %s", servoCmds.length, MAX_NUM));
        }

        synchronized (buffer) {
            buffer.put(MAGIC_CODE)
                    .putShort(computeDataLength(servoCmds.length))
                    .put(CMD_SERVO_MOVE)
                    .put((byte) servoCmds.length)
                    .putShort((short) durationMs);
            Arrays.stream(servoCmds).forEach(cmd -> {
                buffer.put((byte) cmd.getIndex()).putShort(computeAngle(cmd.getAngle()));
            });

            buffer.flip();
            try {
                piSerial.write(debug(buffer));
            } catch (IOException cause) {
                throw new PiComException(getType(), cause);
            } finally {
                buffer.clear();
            }
        }

    }

    private ByteBuffer debug(ByteBuffer buffer) {
        final byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        final StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(Integer.toHexString(b)).append("|");
        }
        sb.append("\n");
        logger.info("DATA={}",sb.toString());

        return ByteBuffer.wrap(data);
    }


    // 计算指令的数据长度
    private static short computeDataLength(int num) {
        return (short) (
                SIZE_OF_DATA_LENGTH
                        + SIZE_OF_NUM
                        + SIZE_OF_DURATION
                        + SIZE_OF_CMD * num
        );
    }

    private short computeAngle(float angle) {
        return (short) (angle * 100);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        piSerial.open(
                new SerialConfig()
                        .device(SerialPort.getDefaultPort())
                        .baud(Baud._9600)
                        .dataBits(DataBits._8)
                        .parity(Parity.NONE)
                        .flowControl(FlowControl.NONE)
        );
    }

    @Override
    public Type getType() {
        return Type.SERVO_CTL;
    }
}

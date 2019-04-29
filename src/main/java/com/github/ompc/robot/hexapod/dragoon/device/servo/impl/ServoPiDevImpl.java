package com.github.ompc.robot.hexapod.dragoon.device.servo.impl;

import com.github.ompc.robot.hexapod.dragoon.component.EnvCom;
import com.github.ompc.robot.hexapod.dragoon.device.PiDevException;
import com.github.ompc.robot.hexapod.dragoon.device.MockPiSerial;
import com.github.ompc.robot.hexapod.dragoon.device.servo.PulseWidthComputer;
import com.github.ompc.robot.hexapod.dragoon.device.servo.ServoPiDev;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialPort;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

@Component
public class ServoPiDevImpl implements ServoPiDev, InitializingBean {

    private static final byte[] MAGIC_CODE = {0x55, 0x55};
    private static final byte CMD_SERVO_MOVE = 0x03;

    /*
     * 最大舵机个数
     */
    private static final int MAX_SERVO_NUM = 32;

    /*
     * MC区大小
     */
    private static final int SIZE_OF_MC = MAGIC_CODE.length;

    /*
     * 指令区大小
     */
    private static final int SIZE_OF_CMD = 1;

    /*
     * 单个舵机控制区大小
     */
    private static final int SIZE_OF_SINGLE_SERVO_CMD = 3;

    /*
     * 数据长度区大小
     */
    private static final int SIZE_OF_DATA_LENGTH = 1;

    /*
     * 舵机数量区大小
     */
    private static final int SIZE_OF_NUM = 1;

    /*
     * 时间区大小
     */
    private static final int SIZE_OF_DURATION = 2;

    /*
     * BUFFER大小：MC+最大舵机数下数据区大小
     */
    private static final int BUFFER_SIZE = SIZE_OF_MC + computeDataLength(MAX_SERVO_NUM);

    private static final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE)
            .order(ByteOrder.LITTLE_ENDIAN);

    @Autowired
    private Serial piSerial;

    @Autowired
    private EnvCom envCom;

    @Autowired
    private PulseWidthComputer pulseWidthComputer;

    // 从外部传递来的舵机控制命令有可能存在相同舵机多个指令的情况
    // 此时需要在此进行merge，相同舵机的控制命令以最后一个为准！
    private ServoCmd[] merge(ServoCmd... servoCmds) {
        return Arrays.stream(servoCmds)
                .collect(toMap(ServoCmd::getIndex, identity(), (key1, key2) -> key2))
                .values()
                .toArray(new ServoCmd[]{});
    }

    @Override
    public void control(final long durationMs, final ServoCmd... servoCmds) throws PiDevException {

        if (isEmpty(servoCmds)) {
            return;
        }

        final ServoCmd[] mergeCmds = merge(servoCmds);

        if (mergeCmds.length > MAX_SERVO_NUM) {
            throw new IllegalArgumentException(String.format("%s large then %s", mergeCmds.length, MAX_SERVO_NUM));
        }

        synchronized (buffer) {
            buffer.put(MAGIC_CODE)
                    .put(computeDataLength(mergeCmds.length))
                    .put(CMD_SERVO_MOVE)
                    .put((byte) mergeCmds.length)
                    .putShort((short) durationMs);
            Arrays.stream(mergeCmds).forEach(cmd -> {
                buffer.put((byte) cmd.getIndex())
                        .putShort((short) pulseWidthComputer.compute(cmd.getIndex(), cmd.getRadian()));
            });

            buffer.flip();
            try {
                while (buffer.hasRemaining()) {
                    piSerial.write(buffer);
                }
                piSerial.flush();
            } catch (IOException cause) {
                throw new PiDevException(getType(), cause);
            } finally {
                // 如果指令写入失败，则抛弃所有指令数据
                // 从头开始
                buffer.clear();
            }
        }

    }


    // 计算指令的数据长度
    private static byte computeDataLength(int num) {
        return (byte) (
                SIZE_OF_DATA_LENGTH
                        + SIZE_OF_CMD
                        + SIZE_OF_NUM
                        + SIZE_OF_DURATION
                        + SIZE_OF_SINGLE_SERVO_CMD * num
        );
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (envCom.isProd()) {
            piSerial.open(
                    new SerialConfig()
                            .device(SerialPort.getDefaultPort())
                            .baud(Baud._9600)
            );
        } else if (envCom.isTest()) {
            piSerial = new MockPiSerial();
        }
    }

    @Override
    public Type getType() {
        return Type.SERVO;
    }
}

package test.com.github.ompc.robot.hexapod.dragoon;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TestCase {

    private static final byte[] MAGIC_CODE = {0x55, 0x55};
    private static final byte CMD_SERVO_MOVE = 0x03;

    private static final int MAX_NUM = 32;
    private static final int SIZE_OF_MC = MAGIC_CODE.length;
    private static final int SIZE_OF_CMD = 3;
    private static final int SIZE_OF_DATA_LENGTH = 2;
    private static final int SIZE_OF_NUM = 1;
    private static final int SIZE_OF_DURATION = 2;
    private static final int BUFFER_SIZE = SIZE_OF_MC + computeDataLength(MAX_NUM);

    @Test
    public void test() {
        final ByteBuffer buffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
        for (int index = 0; index < 10; index++) {
            buffer.put(MAGIC_CODE)
                    .put(computeDataLength(1))
                    .put(CMD_SERVO_MOVE)
                    .put((byte) 1)
                    .putShort((short) 1000)
                    .put((byte)1)
                    .putShort((short)2000);
            buffer.flip();

            try {
                write(debug(buffer));
            } finally {
                buffer.clear();
            }

        }
    }

    private void write(final ByteBuffer buffer) {
        final byte[] src = new byte[buffer.remaining()];
        buffer.get(src);
        for (byte b : src) {
            //System.out.println(String.format("%x", b));
        }
    }

    private ByteBuffer debug(ByteBuffer buffer) {
        final byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        final StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("0x%02x", b)).append("|");
        }
        System.out.println(sb.toString());
        buffer.position(buffer.position() - data.length);

        return buffer;
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

}

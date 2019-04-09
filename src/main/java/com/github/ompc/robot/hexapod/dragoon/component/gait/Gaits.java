package com.github.ompc.robot.hexapod.dragoon.component.gait;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.ANK;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.KNE;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.LIMB_ALL;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;
import static org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * 姿态控制集合
 */
public class Gaits {

    /**
     * 机械臂中臂长度（厘米）
     */
    public static final float LIMB_MIDDLE_LENGTH_CM = 5f;

    /**
     * 复位姿态
     *
     * @param durationMs 姿态时长
     * @return 复位姿态
     */
    public static final Gait reset(final long durationMs) {
        return stand(durationMs, 3);
    }

    /**
     * 站立姿态
     *
     * @param durationMs 姿态时长
     * @param highCm     站立高度
     * @return 站立姿态
     */
    public static final Gait stand(final long durationMs,
                                   final float highCm) {

        // 计算夹角
        float kneAngle = (float) Math.toDegrees(Math.asin(highCm / LIMB_MIDDLE_LENGTH_CM));
        float ankAngle = 180f - kneAngle;

        return new GaitBuilder()
                .changeTo(
                        durationMs,
                        poses(LIMB_ALL, toArray(KNE), kneAngle),
                        poses(LIMB_ALL, toArray(ANK), ankAngle))
                .build();
    }

    public static void main(String... args) {



    }

}

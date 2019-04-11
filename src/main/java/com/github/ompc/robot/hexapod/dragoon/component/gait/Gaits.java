package com.github.ompc.robot.hexapod.dragoon.component.gait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.LIMB_ALL;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.pose;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Radians.RAD_PI;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Radians.rad_pi_half;
import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * 姿态控制集合
 */
public class Gaits {

    /**
     * 复位姿态
     *
     * @param durationMs 姿态时长
     * @return 复位姿态
     */
    public static final Gait reset(final long durationMs) {
        return stand(durationMs, 30);
    }

    /**
     * 站立姿态
     *
     * @param durationMs 姿态时长
     * @param highMm     站立高度（毫米）
     * @return 站立姿态
     */
    public static final Gait stand(final long durationMs,
                                   final int highMm) {

        final Collection<Pose> poses = new ArrayList<>();
        for (final Limb limb : Limb.values()) {
            final BigDecimal rate = RAD_PI.divide(BigDecimal.valueOf(limb.getLength()), ROUND_HALF_EVEN);
            final BigDecimal ank = BigDecimal.valueOf(highMm).multiply(rate);
            poses.add(pose(limb, ANK, RAD_PI.subtract(ank).add(BigDecimal.valueOf(ANK.getDeviation())).doubleValue()));
            poses.add(pose(limb, KNE, RAD_PI.subtract(ank).doubleValue()));
        }

        poses.addAll(asList(poses(LIMB_ALL, toArray(HIP), rad_pi_half)));
        return new GaitBuilder()
                .changeTo(durationMs, poses)
                .build();
    }


}

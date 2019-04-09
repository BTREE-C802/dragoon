package com.github.ompc.robot.hexapod.dragoon.component.gait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.ANK;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.KNE;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.pose;

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
        return stand(durationMs, 7);
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
            final float rate = 180.0f / limb.getLength();
            final float ank = highMm * rate;
            poses.add(pose(limb, ANK, ank));
            poses.add(pose(limb, KNE, 180.0f - ank));
        }

        return new GaitBuilder()
                .changeTo(durationMs, poses)
                .build();
    }

    public static void main(String... args) {

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final Gait gait = new GaitBuilder().changeTo(stand(2000, 30)).build();
        System.out.println(gson.toJson(gait));

    }

}

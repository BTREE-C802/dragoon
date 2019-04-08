package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 步态构造器
 */
public class GaitBuilder {

    private Gait gait;

    // 获取最后一个步态
    private Gait last(Gait gait) {
        return gait.hasNext()
                ? last(gait.getNext())
                : gait;
    }

    /**
     * 步态调整到...
     *
     * @param durationMs 步态调整执行时间
     * @param poses      步态最终姿态
     * @return this
     */
    public GaitBuilder changeTo(final long durationMs,
                                final Pose... poses) {
        final Gait current;
        if (null == gait) {
            gait = current = new Gait();
        } else {
            final Gait last = last(gait);
            current = new Gait();
            last.setNext(current);
        }

        current.setDurationMs(durationMs);
        current.getPoses().addAll(Arrays.asList(poses));
        return this;
    }

    /**
     * 步态调整到...
     *
     * @param durationMs 步态调整执行时间
     * @param posesArray 步态最终姿态(集合)
     * @return this
     */
    public GaitBuilder changeTo(final long durationMs,
                                final Pose[]... posesArray) {
        final Collection<Pose> mergePoses = new ArrayList<>();
        for (final Pose[] poses : posesArray) {
            mergePoses.addAll(Arrays.asList(poses));
        }
        return changeTo(durationMs, mergePoses.toArray(new Pose[mergePoses.size()]));
    }

    /**
     * 构建步态
     *
     * @return 步态
     */
    public Gait build() {
        return gait;
    }

}

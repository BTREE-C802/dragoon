package com.github.ompc.robot.hexapod.dragoon.component.gait;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

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
    public GaitBuilder changeTo(final long durationMs, final Pose... poses) {
        return changeTo(new Gait(durationMs, asList(poses)));
    }

    public GaitBuilder changeTo(final long durationMs, final Collection<Pose> poses) {
        return changeTo(new Gait(durationMs, poses));
    }

    /**
     * 步态调整到...
     *
     * @param durationMs 步态调整执行时间
     * @param posesArray 步态最终姿态(集合)
     * @return this
     */
    public GaitBuilder changeTo(final long durationMs, final Pose[]... posesArray) {
        final Collection<Pose> mergePoses = new ArrayList<>();
        for (final Pose[] poses : posesArray) {
            CollectionUtils.addAll(mergePoses, poses);
        }
        return changeTo(new Gait(durationMs, mergePoses));
    }

    /**
     * 步态调整到...
     *
     * @param gait 下一个步态
     * @return this
     */
    public GaitBuilder changeTo(final Gait gait) {
        if (null == this.gait) {
            this.gait = gait;
            return this;
        }
        final Gait last = last(this.gait);
        last.setNext(gait);
        return this;
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

package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 步态构造器
 */
public class GaitBuilder {

    private Gait gait;

    private Gait appendGaitAtLast(Gait gait) {
        final Gait last;
        if (null == gait) {
            last = new Gait();
        } else {
            if (gait.hasNext()) {
                last = appendGaitAtLast(gait.getNext());
            } else {
                gait.setNext(last = new Gait());
            }
        }
        return last;
    }

    private Gait last(Gait gait) {
        return gait.hasNext()
                ? last(gait.getNext())
                : gait;
    }

    public GaitBuilder append(final long durationMs,
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

    public GaitBuilder append(final long durationMs,
                              final Pose[]... posesArray) {
        final Collection<Pose> mergePoses = new ArrayList<>();
        for (final Pose[] poses : posesArray) {
            mergePoses.addAll(Arrays.asList(poses));
        }
        return append(durationMs, mergePoses.toArray(new Pose[mergePoses.size()]));
    }

    public Gait build() {
        return gait;
    }

}

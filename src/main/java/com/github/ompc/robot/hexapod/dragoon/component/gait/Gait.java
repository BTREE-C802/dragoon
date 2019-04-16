package com.github.ompc.robot.hexapod.dragoon.component.gait;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 步态(组)
 */
public class Gait {

    private final long durationMs;
    private final Collection<Pose> poses = new ArrayList<>();
    private Gait next;

    public Gait(final long durationMs) {
        this.durationMs = durationMs;
    }

    public Gait(final long durationMs, final Collection<Pose> poses) {
        this.durationMs = durationMs;
        this.poses.addAll(poses);
    }

    /**
     * 获取步态持续时间
     *
     * @return 步态持续时间
     */
    public long getDurationMs() {
        return durationMs;
    }

    /**
     * 获取步态所有姿态
     *
     * @return 步态所有姿态
     */
    public Collection<Pose> getPoses() {
        return poses;
    }

    // 获取最后一个步态
    private Gait last(Gait gait) {
        Gait current = gait;
        for (final Iterator<Gait> gaitIt = gait.iterator(); gaitIt.hasNext(); current = gaitIt.next()) ;
        return current;
    }

    /**
     * 添加步态到最后
     *
     * @param gait 步态
     */
    public void append(Gait gait) {
        last(this).next = gait;
    }



    /**
     * 从当前步态返回迭代器，让后续能遍历从当前步态开始之后的步态
     *
     * @return 步态迭代器
     */
    public @NotNull Iterator<Gait> iterator() {
        return new Iterator<Gait>() {

            private Gait current = Gait.this;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Gait next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                final Gait returnGait = current;
                current = current.next;
                return returnGait;
            }
        };
    }

}

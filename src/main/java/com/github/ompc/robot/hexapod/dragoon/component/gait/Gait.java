package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 步态(组)
 */
public class Gait {

    private long durationMs;
    private Collection<Pose> poses = new ArrayList<>();
    private Gait next;

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

    /**
     * 获取下一组步态(如有)
     *
     * @return 下一组步态，如果没有返回null
     */
    public Gait getNext() {
        return next;
    }

    /**
     * 是否有下一组步态
     *
     * @return TRUE|FALSE
     */
    public boolean hasNext() {
        return null != getNext();
    }


    /**
     * 设置步态执行持续时间
     *
     * @param durationMs 步态执行持续时间
     */
    void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    /**
     * 设置下一个步态
     *
     * @param next 下一个步态
     */
    void setNext(Gait next) {
        this.next = next;
    }

}

package com.vzurauskas.duelscripts2;

public class Strike {
    private final BodyPart target;

    public Strike(BodyPart target) {
        this.target = target;
    }

    public BodyPart target() {
        return target;
    }
}

package com.vzurauskas.duelscripts2;

public class FighterBodyPart {
    private final Fighter fighter;
    private final BodyPart bodyPart;

    public FighterBodyPart(Fighter fighter, BodyPart bodyPart) {
        this.fighter = fighter;
        this.bodyPart = bodyPart;
    }

    public Fighter fighter() {
        return fighter;
    }

    public BodyPart bodyPart() {
        return bodyPart;
    }

    public boolean isBeingParried() {
        return fighter.parryingBodyPart() == bodyPart;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FighterBodyPart that = (FighterBodyPart) obj;
        return fighter.equals(that.fighter) && bodyPart == that.bodyPart;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(fighter, bodyPart);
    }

    @Override
    public String toString() {
        return "%s's %s".formatted(fighter.name(), bodyPart);
    }
}

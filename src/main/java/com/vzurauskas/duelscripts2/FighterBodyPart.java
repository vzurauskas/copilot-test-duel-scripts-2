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
        return fighter.parryingFighterBodyPart().equals(this);
    }

    public int calculateBaseDamage(int weaponDamage) {
        return (int)(weaponDamage * bodyPart.multiplier());
    }

    public static FighterBodyPart head(Fighter fighter) {
        return new FighterBodyPart(fighter, BodyPart.HEAD);
    }

    public static FighterBodyPart torso(Fighter fighter) {
        return new FighterBodyPart(fighter, BodyPart.TORSO);
    }

    public static FighterBodyPart legs(Fighter fighter) {
        return new FighterBodyPart(fighter, BodyPart.LEGS);
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

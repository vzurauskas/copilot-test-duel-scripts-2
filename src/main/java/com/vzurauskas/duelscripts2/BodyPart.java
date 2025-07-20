package com.vzurauskas.duelscripts2;

public final class BodyPart {
    private final Fighter fighter;
    private final double multiplier;
    private final String name;

    private BodyPart(
        Fighter fighter, double multiplier, String name
    ) {
        this.fighter = fighter;
        this.multiplier = multiplier;
        this.name = name;
    }

    public static BodyPart head(Fighter fighter) {
        return new BodyPart(fighter, 1.7, "head");
    }

    public static BodyPart torso(Fighter fighter) {
        return new BodyPart(fighter, 1.0, "torso");
    }

    public static BodyPart legs(Fighter fighter) {
        return new BodyPart(fighter, 0.5, "legs");
    }

    public int calculateBaseDamage(int weaponDamage) {
        return (int) (weaponDamage * multiplier);
    }

    public boolean isBeingParried() {
        return this.equals(fighter.parryingFighterBodyPart());
    }

    public Fighter fighter() {
        return fighter;
    }

    public Strike receiveStrike(Fighter striker) {
        if (this.isBeingParried()) {
            return Strike.parried(striker, this);
        } else {
            return striker.weapon().hit(striker, this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BodyPart that = (BodyPart) obj;
        return Double.compare(that.multiplier, multiplier) == 0 && 
               fighter.equals(that.fighter) && 
               name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(fighter, multiplier, name);
    }

    @Override
    public String toString() {
        return "%s's %s".formatted(fighter.name(), name);
    }
}

package demo.orm;

import net.programmer.igoodie.annotation.Goodie;

public class GearBaseStats {

    @Goodie private String rarity;

    @Goodie private RangeGoodie damage;

    @Goodie private RangeGoodie durability;

    @Goodie private RangeGoodie attackspeed;

    public String getRarity() {
        return rarity;
    }

    public RangeGoodie getDamage() {
        return damage;
    }

    public RangeGoodie getAttackspeed() {
        return attackspeed;
    }

    public RangeGoodie getDurability() {
        return durability;
    }

    public GearBaseStats() {}

    @Override
    public String toString() {
        return "GearBaseStats{" +
                "rarity='" + rarity + '\'' +
                ", damage=" + damage +
                ", durability=" + durability +
                ", attackspeed=" + attackspeed +
                '}';
    }

}

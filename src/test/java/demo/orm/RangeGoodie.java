package demo.orm;

import net.programmer.igoodie.annotation.Goodie;

public class RangeGoodie {

    @Goodie private int min;
    @Goodie private int max;

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public RangeGoodie() {}

    public RangeGoodie(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "RangeGoodie{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

}

package com.adventofcode.input;

import java.util.Optional;

public class LanternFish {
    private int daysToReproduce;

    public LanternFish() {
        this(8);
    }

    public LanternFish(int daysToReproduce) {
        this.daysToReproduce = daysToReproduce;
    }

    public int daysToReproduce() {
        return daysToReproduce;
    }

    public Optional<LanternFish> nextDay() {
        this.daysToReproduce--;
        if (this.daysToReproduce < 0) {
            this.daysToReproduce = 6;
            return Optional.of(new LanternFish());
        }
        return Optional.empty();
    }
}

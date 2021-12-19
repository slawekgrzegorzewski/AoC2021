package com.adventofcode.input.day19;

import java.util.Objects;

public record BeaconPosition(int x, int y, int z) {



    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeaconPosition that = (BeaconPosition) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


}

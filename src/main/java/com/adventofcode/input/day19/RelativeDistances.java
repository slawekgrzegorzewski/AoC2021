package com.adventofcode.input.day19;

import java.util.Objects;

public record RelativeDistances(BeaconPosition bp1, BeaconPosition bp2, int scanner, double distance) {

    public static RelativeDistances of(int scanner, BeaconPosition bp1, BeaconPosition bp2) {
        return new RelativeDistances(
                bp1,
                bp2,
                scanner,
                Math.sqrt(Math.pow(bp1.x() - bp2.x(), 2) + Math.pow(bp1.y() - bp2.y(), 2) + Math.pow(bp1.z() - bp2.z(), 2))
        );
    }

    @Override
    public int scanner() {
        return scanner;
    }
}

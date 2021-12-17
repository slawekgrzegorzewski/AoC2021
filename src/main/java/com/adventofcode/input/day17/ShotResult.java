package com.adventofcode.input.day17;

import com.adventofcode.input.Coordinates;

public record ShotResult(Coordinates finalPosition, int maxHeight, Velocity originalVelocity, Velocity lastVelocity) {

}

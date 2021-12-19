package com.adventofcode.input.day19;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.adventofcode.input.day19.Transformation.Axis.*;

public class Transformation {


    public enum Axis {
        X(0), Y(1), Z(2);

        private final int order;

        Axis(int order) {
            this.order = order;
        }

        public int order() {
            return order;
        }
    }

    public static final List<Axis[]> possibleAxisTransformations = List.of(
            new Axis[]{X, Y, Z},
            new Axis[]{X, Z, Y},
            new Axis[]{Y, X, Z},
            new Axis[]{Y, Z, X},
            new Axis[]{Z, X, Y},
            new Axis[]{Z, Y, X}
    );

    public static final List<int[]> possibleDirectionsTransformations = List.of(
            new int[]{1, 1, 1},
            new int[]{1, 1, -1},
            new int[]{1, -1, 1},
            new int[]{1, -1, -1},
            new int[]{-1, 1, 1},
            new int[]{-1, 1, -1},
            new int[]{-1, -1, 1},
            new int[]{-1, -1, -1}
    );

    Axis[] axisTransformation;

    int[] directionTransformation;
    BeaconPosition difference;
    private final List<SamePair<BeaconPosition>> pairs;

    public Transformation(List<SamePair<BeaconPosition>> pairs) {
        this.pairs = pairs;
        figureOUtTransformation();
    }

    private void figureOUtTransformation() {
        for (Axis[] axisTransformation : possibleAxisTransformations) {
            for (int[] directionTransformation : possibleDirectionsTransformations) {
                List<BeaconPosition> differences = getDifferences(axisTransformation, directionTransformation);
                if (checkAllEquals(differences)) {
                    this.axisTransformation = axisTransformation;
                    this.directionTransformation = directionTransformation;
                    this.difference = differences.get(0);
                }
            }
        }
    }

    private boolean checkAllEquals(List<BeaconPosition> transformationResult) {
        return IntStream.range(1, transformationResult.size())
                .allMatch(i -> transformationResult.get(0).equals(transformationResult.get(i)));
    }

    private List<BeaconPosition> getDifferences(Axis[] axisTransformation, int[] directionTransformation) {
        return pairs
                .stream()
                .map(e -> getDifference(e.first(), e.second(), axisTransformation, directionTransformation))
                .toList();
    }

    private BeaconPosition getDifference(BeaconPosition firstScanner, BeaconPosition secondScanner, Axis[] axisTransformation, int[] directionTransformation) {
        return new BeaconPosition(
                firstScanner.x() + directionTransformation[0] * getAxis(secondScanner, axisTransformation[0]),
                firstScanner.y() + directionTransformation[1] * getAxis(secondScanner, axisTransformation[1]),
                firstScanner.z() + directionTransformation[2] * getAxis(secondScanner, axisTransformation[2])
        );
    }

    private int getAxis(BeaconPosition secondScanner, Axis axis) {
        return switch (axis) {
            case X -> secondScanner.x();
            case Y -> secondScanner.y();
            case Z -> secondScanner.z();
        };
    }

    public List<BeaconPosition> transform(List<BeaconPosition> values) {
        return values
                .stream()
                .map(beaconPosition -> new BeaconPosition(
                        difference.x() - directionTransformation[0] * getAxis(beaconPosition, axisTransformation[0]),
                        difference.y() - directionTransformation[1] * getAxis(beaconPosition, axisTransformation[1]),
                        difference.z() - directionTransformation[2] * getAxis(beaconPosition, axisTransformation[2])

                )).toList();
    }

    public List<BeaconPosition> reTransform(List<BeaconPosition> values) {
        return values
                .stream()
                .map(beaconPosition -> {
                    int[] newValues = new int[3];
                    newValues[axisTransformation[0].order] = (difference.x() - beaconPosition.x()) * directionTransformation[0];
                    newValues[axisTransformation[1].order] = (difference.y() - beaconPosition.y()) * directionTransformation[1];
                    newValues[axisTransformation[2].order] = (difference.z() - beaconPosition.z()) * directionTransformation[2];
                    return new BeaconPosition(newValues[0], newValues[1], newValues[2]);
                }).toList();
    }

    public boolean transformationFound() {
        return this.axisTransformation != null;
    }

    @Override
    public String toString() {
        return Arrays.stream(axisTransformation).map(String::valueOf).collect(Collectors.joining(","))
                + " " +
                Arrays.stream(directionTransformation).mapToObj(String::valueOf).collect(Collectors.joining(","))
                + " " + difference;
    }
}

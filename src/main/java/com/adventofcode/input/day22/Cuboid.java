package com.adventofcode.input.day22;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Cuboid {
    private final long fromX, fromY, fromZ, toX, toY, toZ;

    public static Optional<Cuboid> of(long fromX, long toX, long fromY, long toY, long fromZ, long toZ) {
        if (fromX > toX || fromY > toY || fromZ > toZ) return Optional.empty();
        return Optional.of(new Cuboid(fromX, fromY, fromZ, toX, toY, toZ));
    }

    public static Optional<Cuboid> lineXY(long x, long y, long fromZ, long toZ) {
        return Cuboid.of(x, x, y, y, fromZ, toZ);
    }

    public static Optional<Cuboid> lineXZ(long x, long z, long fromY, long toY) {
        return Cuboid.of(x, x, fromY, toY, z, z);
    }

    public static Optional<Cuboid> lineYZ(long y, long z, long fromX, long toX) {
        return Cuboid.of(fromX, toX, y, y, z, z);
    }

    public static Optional<Cuboid> surfaceX(long x, long fromY, long toY, long fromZ, long toZ) {
        return Cuboid.of(x, x, fromY, toY, fromZ, toZ);
    }

    public static Optional<Cuboid> surfaceY(long y, long fromX, long toX, long fromZ, long toZ) {
        return Cuboid.of(fromX, toX, y, y, fromZ, toZ);
    }

    public static Optional<Cuboid> surfaceZ(long z, long fromX, long toX, long fromY, long toY) {
        return Cuboid.of(fromX, toX, fromY, toY, z, z);
    }

    public Cuboid(long fromX, long fromY, long fromZ, long toX, long toY, long toZ) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.fromZ = fromZ;
        this.toX = toX;
        this.toY = toY;
        this.toZ = toZ;
    }

    public long fromX() {
        return fromX;
    }

    public long fromY() {
        return fromY;
    }

    public long fromZ() {
        return fromZ;
    }

    public long toX() {
        return toX;
    }

    public long toY() {
        return toY;
    }

    public long toZ() {
        return toZ;
    }

    public Optional<Cuboid> common(Cuboid other) {
        long fromX = Math.max(this.fromX, other.fromX);
        long toX = Math.min(this.toX, other.toX);
        long fromY = Math.max(this.fromY, other.fromY);
        long toY = Math.min(this.toY, other.toY);
        long fromZ = Math.max(this.fromZ, other.fromZ);
        long toZ = Math.min(this.toZ, other.toZ);
        if (fromX > toX || fromY > toY || fromZ > toZ) {
            return Optional.empty();
        } else {
            return Optional.of(new Cuboid(fromX, fromY, fromZ, toX, toY, toZ));
        }
    }

    public List<Cuboid> subtract(Cuboid other) {
        Cuboid common = common(other).orElse(null);
        if (common == null) return List.of(this);
        if (common.equals(this)) return List.of();
        List<Cuboid> allCuboidsAfterSplit = Stream.of(
                        of(fromX, common.fromX - 1, fromY, common.fromY - 1, fromZ, common.fromZ - 1),
                        of(fromX, common.fromX - 1, fromY, common.fromY - 1, common.fromZ + 1, common.toZ - 1),
                        of(fromX, common.fromX - 1, fromY, common.fromY - 1, common.toZ + 1, toZ),

                        of(fromX, common.fromX - 1, common.fromY + 1, common.toY - 1, fromZ, common.fromZ - 1),
                        of(fromX, common.fromX - 1, common.fromY + 1, common.toY - 1, common.fromZ + 1, common.toZ - 1),
                        of(fromX, common.fromX - 1, common.fromY + 1, common.toY - 1, common.toZ + 1, toZ),

                        of(fromX, common.fromX - 1, common.toY + 1, toY, fromZ, common.fromZ - 1),
                        of(fromX, common.fromX - 1, common.toY + 1, toY, common.fromZ + 1, common.toZ - 1),
                        of(fromX, common.fromX - 1, common.toY + 1, toY, common.toZ + 1, toZ),

                        of(common.fromX + 1, common.toX - 1, fromY, common.fromY - 1, fromZ, common.fromZ - 1),
                        of(common.fromX + 1, common.toX - 1, fromY, common.fromY - 1, common.fromZ + 1, common.toZ - 1),
                        of(common.fromX + 1, common.toX - 1, fromY, common.fromY - 1, common.toZ + 1, toZ),

                        of(common.fromX + 1, common.toX - 1, common.fromY + 1, common.toY - 1, fromZ, common.fromZ - 1),
                        of(common.fromX + 1, common.toX - 1, common.fromY + 1, common.toY - 1, common.toZ + 1, toZ),

                        of(common.fromX + 1, common.toX - 1, common.toY + 1, toY, fromZ, common.fromZ - 1),
                        of(common.fromX + 1, common.toX - 1, common.toY + 1, toY, common.fromZ + 1, common.toZ - 1),
                        of(common.fromX + 1, common.toX - 1, common.toY + 1, toY, common.toZ + 1, toZ),

                        of(common.toX + 1, toX, fromY, common.fromY - 1, fromZ, common.fromZ - 1),
                        of(common.toX + 1, toX, fromY, common.fromY - 1, common.fromZ + 1, common.toZ - 1),
                        of(common.toX + 1, toX, fromY, common.fromY - 1, common.toZ + 1, toZ),

                        of(common.toX + 1, toX, common.fromY + 1, common.toY - 1, fromZ, common.fromZ - 1),
                        of(common.toX + 1, toX, common.fromY + 1, common.toY - 1, common.fromZ + 1, common.toZ - 1),
                        of(common.toX + 1, toX, common.fromY + 1, common.toY - 1, common.toZ + 1, toZ),

                        of(common.toX + 1, toX, common.toY + 1, toY, fromZ, common.fromZ - 1),
                        of(common.toX + 1, toX, common.toY + 1, toY, common.fromZ + 1, common.toZ - 1),
                        of(common.toX + 1, toX, common.toY + 1, toY, common.toZ + 1, toZ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        compactCuboids(allCuboidsAfterSplit);

        return allCuboidsAfterSplit.stream().filter(c -> !common.equals(c)).toList();
    }

    public static void compactCuboids(List<Cuboid> allCuboidsAfterSplit) {
        boolean noAdjacentCells = false;
        while (!noAdjacentCells && allCuboidsAfterSplit.size() > 1) {
            noAdjacentCells = true;
            for (int i = 0; i < allCuboidsAfterSplit.size(); i++) {
                for (int j = i + 1; j < allCuboidsAfterSplit.size(); j++) {
                    if (allCuboidsAfterSplit.get(i).isAdjacent(allCuboidsAfterSplit.get(j))) {
                        noAdjacentCells = false;
                        Cuboid compacted = allCuboidsAfterSplit.get(i).compact(allCuboidsAfterSplit.get(j)).orElseThrow();
                        allCuboidsAfterSplit.remove(j);
                        allCuboidsAfterSplit.remove(i);
                        allCuboidsAfterSplit.add(compacted);
                        break;
                    }
                }
                if (!noAdjacentCells) break;
            }
        }
    }

    public long count() {
        return Math.abs((this.fromX - this.toX) * (fromY - toY) * (fromZ - toZ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuboid cuboid = (Cuboid) o;
        return fromX == cuboid.fromX && fromY == cuboid.fromY && fromZ == cuboid.fromZ && toX == cuboid.toX && toY == cuboid.toY && toZ == cuboid.toZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromX, fromY, fromZ, toX, toY, toZ);
    }

    public boolean containsPoint(long x, long y, long z) {
        return x >= fromX && x <= toX && y >= fromY && y <= toY && z >= fromZ && z <= toZ;
    }

    public Optional<Cuboid> compact(Cuboid other) {
        if (!isAdjacent(other))
            return Optional.empty();
        if (isAdjacentOnX(other) && isAdjacentOnY(other)) {
            return Optional.of(new Cuboid(fromX, fromY, Math.min(this.fromZ, other.fromZ), toX, toY, Math.max(this.toZ, other.toZ)));
        }
        if (isAdjacentOnX(other) && isAdjacentOnZ(other)) {
            return Optional.of(new Cuboid(fromX, Math.min(this.fromY, other.fromY), fromZ, toX, Math.max(this.toY, other.toY), toZ));
        }
        return Optional.of(new Cuboid(Math.min(this.fromX, other.fromX), fromY, fromZ, Math.max(this.toX, other.toX), toY, toZ));
    }

    public boolean isAdjacent(Cuboid other) {
        return (isAdjacentOnX(other) && isAdjacentOnY(other) && touches(fromZ, toZ, other.fromZ, other.toZ))
                || (isAdjacentOnX(other) && isAdjacentOnZ(other) && touches(fromY, toY, other.fromY, other.toY))
                || (isAdjacentOnY(other) && isAdjacentOnZ(other) && touches(fromX, toX, other.fromX, other.toX));
    }

    private boolean isAdjacentOnX(Cuboid other) {
        return this.fromX == other.fromX && this.toX == other.toX;
    }

    private boolean isAdjacentOnY(Cuboid other) {
        return this.fromY == other.fromY && this.toY == other.toY;
    }

    private boolean isAdjacentOnZ(Cuboid other) {
        return this.fromZ == other.fromZ && this.toZ == other.toZ;
    }

    private boolean touches(long from, long to, long otherFrom, long otherTo) {
        long minFrom = Math.min(from, otherFrom);
        long maxTo = Math.max(to, otherFrom);
        return (minFrom == from && maxTo == otherTo && to == otherFrom - 2)
                || (minFrom == otherFrom && maxTo == to && otherTo == from - 2);
    }

    @Override
    public String toString() {
        return String.format("x=%d..%d,y=%d..%d,z=%d..%d", fromX, toX, fromY, toY, fromZ, toZ);
    }
}

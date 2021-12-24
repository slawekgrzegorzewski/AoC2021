package com.adventofcode.input.day24;

public enum MemoryField {
    W, X, Y, Z;

    public long get(Memory m){
        return switch (this) {
            case W -> m.w();
            case X -> m.x();
            case Y -> m.y();
            case Z -> m.z();
        };
    }

    public void set(Memory m, Long value){
        switch (this) {
            case W -> m.setW(value);
            case X -> m.setX(value);
            case Y -> m.setY(value);
            case Z -> m.setZ(value);
        };
    }
}

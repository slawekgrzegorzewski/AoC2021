package com.adventofcode.input;

public class Instruction {
    private final String command;
    private final int argument;

    public Instruction(String command, int argument) {
        this.command = command;
        this.argument = argument;
    }

    public String command() {
        return command;
    }

    public int argument() {
        return argument;
    }
}

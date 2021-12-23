package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day23.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.adventofcode.input.day23.Amphipod.*;

public class Day23 {

    private final List<Room2> input;
    private final List<Integer> roomsExits = List.of(2, 4, 6, 8);
    private Integer minCost = Integer.MAX_VALUE;

    public static void main(String[] input) throws IOException {
        Day23 day23 = new Day23();
        System.out.println("part1 = " + day23.part1());
        System.out.println("part2 = " + day23.part2());
    }

    public Day23() throws IOException {
        input = Input.day23("/day23");
    }

    int part1() {
        List<Amphipod> hallway = new ArrayList<>();
        for (int i = 0; i < 11; i++) hallway.add(null);
        minCost = Integer.MAX_VALUE;
        calculateCost(new ArrayList<>(hallway), input.stream().map(Room::copy).toList(), 0);
        return minCost;
    }

    int part2() {
        List<Amphipod> hallway = new ArrayList<>();
        List<Room> newInput = List.of(
                new Room4(0, input.get(0).first(), D, D, input.get(0).second()),
                new Room4(1, input.get(1).first(), C, B, input.get(1).second()),
                new Room4(2, input.get(2).first(), B, A, input.get(2).second()),
                new Room4(3, input.get(3).first(), A, C, input.get(3).second())
        );
        for (int i = 0; i < 11; i++) hallway.add(null);
        minCost = Integer.MAX_VALUE;
        calculateCost(new ArrayList<>(hallway), newInput.stream().map(Room::copy).toList(), 0);
        return minCost;
    }

    private void calculateCost(List<Amphipod> hallway, List<Room> rooms, int cost) {
        if (cost + costOfInsertingAllInHallway(hallway, rooms) > minCost) return;
        List<Move> moves = possibleMoves(hallway, rooms);
        if (moves.isEmpty() && rooms.stream().allMatch(Room::isFilledWithCorrectAmphipods)) {
            if (minCost > cost) {
                System.out.println("cost = " + cost);
                minCost = cost;
            }
            return;
        }

        for (Move move : moves) {
            List<Amphipod> copyOfHallway = new ArrayList<>(hallway);
            List<Room> copyOfRooms = rooms.stream().map(Room::copy).toList();
            int stepCost = performMove(move, copyOfHallway, copyOfRooms);
            calculateCost(copyOfHallway, copyOfRooms, cost + stepCost);
        }
    }

    private int costOfInsertingAllInHallway(List<Amphipod> hallway, List<Room> rooms) {
        int cost = 0;
        for (int i = 0; i < hallway.size(); i++) {
            Amphipod amphipod = hallway.get(i);
            if (amphipod != null) {
                Room room = rooms.get(amphipod.targetRoom());
                cost += Math.abs(i - roomsExits.get(amphipod.targetRoom())) +
                        (room.isFull() ? 1 : room.stepsToEnter()) * amphipod.moveCost();
            }
        }
        return cost;
    }

    private int performMove(Move move, List<Amphipod> copyOfHallway, List<Room> copyOfRooms) {
        int stepCost = 0;
        if (move instanceof MoveIn m) {
            Room room = copyOfRooms.get(m.to().number());
            Amphipod amphipod = copyOfHallway.get(m.from());
            stepCost = cost(amphipod, room, m.from());
            room.enterRoom(amphipod);
            copyOfHallway.set(m.from(), null);
        }
        if (move instanceof MoveOut m) {
            Room room = copyOfRooms.get(m.from().number());
            Amphipod amphipod = room.exitRoom().orElseThrow();
            stepCost = cost(amphipod, room, m.to());
            if (copyOfHallway.get(m.to()) != null) {
                throw new RuntimeException("Position already taken");
            }
            copyOfHallway.set(m.to(), amphipod);
        }
        if (!check(copyOfHallway, copyOfRooms)) {
            throw new RuntimeException("Chack failed");
        }
        return stepCost;
    }

    private boolean check(List<Amphipod> hallway, List<Room> rooms) {
        List<Amphipod> inHallway = hallway.stream().filter(Objects::nonNull).toList();
        List<Amphipod> inRooms = rooms.stream().flatMap(r -> r.getAllAmphipods().stream()).filter(Objects::nonNull).toList();
        List<Amphipod> all = new ArrayList<>(inHallway);
        all.addAll(inRooms);
        int capacity = rooms.get(0).capacity();
        return all.size() == 4 * capacity
                && all.stream().mapToInt(a -> a == A ? 1 : 0).sum() == capacity
                && all.stream().mapToInt(a -> a == Amphipod.B ? 1 : 0).sum() == capacity
                && all.stream().mapToInt(a -> a == C ? 1 : 0).sum() == capacity
                && all.stream().mapToInt(a -> a == D ? 1 : 0).sum() == capacity;
    }

    private int cost(Amphipod amphipod, Room room, int hallwayPosition) {
        return amphipod.moveCost() * (Math.abs(roomsExits.get(room.number()) - hallwayPosition) + room.stepsToLeave());
    }

    private List<Move> possibleMoves(List<Amphipod> hallway, List<Room> rooms) {
        List<Move> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(possibleOutMoves(hallway, rooms));
        possibleMoves.addAll(possibleInMoves(hallway, rooms));
        return possibleMoves;
    }

    private List<MoveOut> possibleOutMoves(List<Amphipod> hallway, List<Room> rooms) {
        List<MoveOut> possibleMoves = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (!room.canExit()) continue;
            for (int j = 0; j < hallway.size(); j++) {
                if (roomsExits.contains(j)) continue;
                int from = Math.min(j, roomsExits.get(i));
                int to = Math.max(j, roomsExits.get(i));
                if (hallway.subList(from, to + 1).stream().allMatch(Objects::isNull)) {
                    possibleMoves.add(new MoveOut(room, j));
                }
            }
        }
        return possibleMoves;
    }

    private List<MoveIn> possibleInMoves(List<Amphipod> hallway, List<Room> rooms) {
        List<MoveIn> possibleMoves = new ArrayList<>();
        for (int i = 0; i < hallway.size(); i++) {
            Amphipod amphipod = hallway.get(i);
            if (amphipod == null) continue;
            int targetRoom = amphipod.targetRoom();
            if (rooms.get(targetRoom).canEnter(amphipod)) {
                int from = Math.min(i, roomsExits.get(targetRoom));
                int to = Math.max(i, roomsExits.get(targetRoom));
                List<Amphipod> path = new ArrayList<>(hallway.subList(from, to + 1));
                if (path.get(0) == amphipod) {
                    path.remove(0);
                } else if (path.get(path.size() - 1) == amphipod) {
                    path.remove(path.size() - 1);
                } else {
                    throw new RuntimeException("Moving amphipod not found on path");
                }
                if (path.stream().allMatch(Objects::isNull)) {
                    possibleMoves.add(new MoveIn(i, rooms.get(targetRoom)));
                }
            }

        }
        return possibleMoves;
    }

    private void restorePath(List<Amphipod> hallway, List<Room> rooms, int cost, List<Integer> movesList) {
        if (movesList.isEmpty()) return;
        List<Move> moves = possibleMoves(hallway, rooms);
        Move move = moves.get(movesList.get(0));
        int stepCost = performMove(move, hallway, rooms);
        cost += stepCost;
        System.out.println("stepCost = " + stepCost + "; cost = " + cost);
        System.out.println();
        System.out.println();
        movesList.remove(0);
        restorePath(hallway, rooms, cost, movesList);
    }

    private void print(List<Amphipod> hallway, List<Room> rooms) {
        StringBuilder s = new StringBuilder("#############").append("\n");

        s.append("#");
        hallway.stream().map(a -> a == null ? "." : a.name()).forEach(s::append);
        s.append("#").append("\n");

        if (rooms.get(0) instanceof Room2) {
            s.append("###");
            rooms.stream().map(Room2.class::cast).map(r -> (r.first() == null ? "." : r.first().name()) + "#").forEach(s::append);
            s.append("##").append("\n");

            s.append("  #");
            rooms.stream().map(Room2.class::cast).map(r -> (r.second() == null ? "." : r.second().name()) + "#").forEach(s::append);
            s.append("  ").append("\n");
        } else {
            s.append("###");
            rooms.stream().map(Room4.class::cast).map(r -> (r.first() == null ? "." : r.first().name()) + "#").forEach(s::append);
            s.append("##").append("\n");

            s.append("  #");
            rooms.stream().map(Room4.class::cast).map(r -> (r.second() == null ? "." : r.second().name()) + "#").forEach(s::append);
            s.append("  ").append("\n");

            s.append("  #");
            rooms.stream().map(Room4.class::cast).map(r -> (r.third() == null ? "." : r.third().name()) + "#").forEach(s::append);
            s.append("  ").append("\n");

            s.append("  #");
            rooms.stream().map(Room4.class::cast).map(r -> (r.fourth() == null ? "." : r.fourth().name()) + "#").forEach(s::append);
            s.append("  ").append("\n");
        }

        s.append("  #########  ").append("\n");
        System.out.println(s);
    }
}


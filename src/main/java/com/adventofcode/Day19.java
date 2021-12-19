package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day15.DijkstraShortestPath;
import com.adventofcode.input.day15.Graph;
import com.adventofcode.input.day15.Node;
import com.adventofcode.input.day19.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.adventofcode.Utils.*;
import static com.adventofcode.input.Input.pairs;
import static com.adventofcode.input.day19.SamePair.samePair;
import static java.util.Optional.of;

public class Day19 {

    private final Map<SamePair<Integer>, Transformation> transformations;
    private final Graph graphOfTransformations;
    Map<Integer, List<BeaconPosition>> scannersReport;
    Map<Integer, List<RelativeDistances>> scannersReportDistances;

    public static void main(String[] input) throws IOException {
        Day19 day19 = new Day19();
        System.out.println("part1 = " + day19.part1());
        System.out.println("part2 = " + day19.part2());
    }

    public Day19() throws IOException {
        scannersReport = Input.scannersReport("/day19");

        scannersReportDistances = scannersReport.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> pairs(e.getValue())
                                .stream()
                                .map(d -> RelativeDistances.of(e.getKey(), d.first(), d.second()))
                                .collect(Collectors.toList())
                ));

        transformations = pairs(new ArrayList<>(scannersReportDistances.entrySet()))
                .stream()
                .map(pair -> samePair(pair.first().getKey(), pair.second().getKey()))
                .map(indexedSupplier(this::findCommonDistances))
                .map(indexed(this::pairUpBeacons))
                .filter(indexedPredicate(m -> !m.isEmpty()))
                .map(indexed(pairedUpBeacons -> pairedUpBeacons.entrySet().stream().map(p -> samePair(p.getKey(), p.getValue())).toList()))
                .map(indexed(Transformation::new))
                .filter(indexedPredicate(Transformation::transformationFound))
                .collect(Collectors.toMap(
                        v -> samePair(v.first().second(), v.first().first()),
                        Pair::second
                ));

        graphOfTransformations = createGraphOfTransformations(transformations.keySet().stream().toList());
    }

    private Map<RelativeDistances, RelativeDistances> findCommonDistances(SamePair<Integer> index) {
        List<RelativeDistances> firstScannerBeacons = scannersReportDistances.get(index.first());
        List<RelativeDistances> secondScannerBeacons = scannersReportDistances.get(index.second());
        List<RelativeDistances> common = new ArrayList<>(firstScannerBeacons);
        common = common.stream()
                .filter(rd -> secondScannerBeacons.stream().anyMatch(rd2 -> rd.distance() == rd2.distance()))
                .collect(Collectors.toList());
        return common.stream().collect(Collectors.toMap(
                rd -> rd,
                rd -> secondScannerBeacons.stream().filter(rd2 -> rd.distance() == rd2.distance()).findFirst().orElseThrow()
        ));
    }

    private Map<BeaconPosition, BeaconPosition> pairUpBeacons(Map<RelativeDistances, RelativeDistances> commonDistances) {
        Map<BeaconPosition, BeaconPosition> pairs = new HashMap<>();
        commonDistances.forEach((rd1, rd2) -> {
            Map.Entry<RelativeDistances, RelativeDistances> otherDistanceComingFromRD1BP1 =
                    commonDistances.entrySet().stream()
                            .filter(entry -> entry.getKey() != rd1)
                            .filter(entry -> Arrays.asList(entry.getKey().bp1(), entry.getKey().bp2()).contains(rd1.bp1()))
                            .findAny()
                            .orElse(null);
            if (otherDistanceComingFromRD1BP1 != null) {
                if (Set.of(otherDistanceComingFromRD1BP1.getValue().bp1(), otherDistanceComingFromRD1BP1.getValue().bp2()).contains(rd2.bp1())) {
                    pairs.put(rd1.bp1(), rd2.bp1());
                    pairs.put(rd1.bp2(), rd2.bp2());
                } else {
                    pairs.put(rd1.bp1(), rd2.bp2());
                    pairs.put(rd1.bp2(), rd2.bp1());
                }
            }
        });
        return pairs;
    }

    private Graph createGraphOfTransformations(List<SamePair<Integer>> pairs) {
        Map<Integer, Node> nodes = new HashMap<>();
        for (SamePair<Integer> pair : pairs) {
            Node firstNode = nodes.computeIfAbsent(pair.first(), (key) -> new Node(String.valueOf(key)));
            Node secondNode = nodes.computeIfAbsent(pair.second(), (key) -> new Node(String.valueOf(key)));
            firstNode.addDestination(secondNode, 1);
            secondNode.addDestination(firstNode, 1);
        }
        Graph g = new Graph();
        nodes.values().forEach(g::addNode);
        new DijkstraShortestPath().calculate(nodes.get(0));
        return g;
    }


    int part1() {
        Set<BeaconPosition> uniqueBeaconPositions = new HashSet<>(scannersReport.get(0));

        for (int i = 1; i < scannersReport.size(); i++) {
            uniqueBeaconPositions.addAll(transformToZero(i, scannersReport::get));
        }

        return uniqueBeaconPositions.size();
    }

    int part2() {
        Map<Integer, BeaconPosition> positions = new HashMap<>();
        positions.put(0, new BeaconPosition(0, 0, 0));
        for (int i = 1; i < scannersReport.size(); i++) {
            positions.put(i, transformInitialToZero(i, j -> new BeaconPosition(0, 0, 0)));
        }
        return pairs(positions.values().stream().toList())
                .stream()
                .mapToInt(p -> calculateManhattanDistance(p.first(), p.second()))
                .max()
                .orElseThrow();
    }

    private BeaconPosition transformInitialToZero(int i, Function<Integer, BeaconPosition> initialSource) {
        return transformToZero(i, j -> List.of(initialSource.apply(j))).get(0);
    }

    private List<BeaconPosition> transformToZero(int i, Function<Integer, List<BeaconPosition>> initialSource) {
        Node node = graphOfTransformations.findNode(String.valueOf(i));
        List<Node> shortestPath = node.getShortestPath();
        int transformFrom = i;
        int transformTo = Integer.parseInt(shortestPath.get(shortestPath.size() - 1).getName());
        List<BeaconPosition> transforming = doTransformation(transformations, transformFrom, transformTo, initialSource.apply(transformFrom));
        for (int j = shortestPath.size() - 2; j >= 0; j--) {
            transformFrom = transformTo;
            transformTo = Integer.parseInt(shortestPath.get(j).getName());
            transforming = doTransformation(transformations, transformFrom, transformTo, transforming);
        }
        return transforming;
    }

    private List<BeaconPosition> doTransformation(Map<SamePair<Integer>, Transformation> transformations, int from, int to, List<BeaconPosition> transforamtions) {
        Transformation transformation = transformations.get(samePair(from, to));
        if (transformation == null) {
            return of(transforamtions)
                    .map(transformations.get(samePair(to, from))::reTransform)
                    .orElseThrow();
        } else {
            return of(transforamtions)
                    .map(transformation::transform)
                    .orElseThrow();
        }
    }

    private int calculateManhattanDistance(BeaconPosition bp, BeaconPosition bp1) {
        return Math.abs(bp.x() - bp1.x())
                + Math.abs(bp.y() - bp1.y())
                + Math.abs(bp.z() - bp1.z());
    }
}


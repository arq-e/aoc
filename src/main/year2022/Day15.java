package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day15 extends Day {

    public static void main(String[] args) throws IOException {
        Day15 day = new Day15();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int row = 2000000;
        Map<int[], Integer> sensors = new HashMap<>();
        Set<Integer> beacons = new HashSet<>();
        convertInput(list, sensors, beacons, row);

        Set<Integer> coverage = new HashSet<>();
        for (int[] ints : sensors.keySet()) {
            findCoverage(sensors.get(ints),ints, coverage,row);
        }

        System.out.println(coverage.size() - beacons.size());
    }

    public void solve2(List<String> list) {
        int max = 4000000;

        Map<int[], Integer> map = new HashMap<>();
        convertInput(list, map, null, 0);


        Map<Integer, Set<Integer>> boundaryValues = new HashMap<>();
        for (int[] ints : map.keySet()) {
            findBoundary(ints, map.get(ints)+1, boundaryValues, max);
        }

        long res = 0;
        boolean found = false;
        for (Integer i: boundaryValues.keySet()) {
            if (i >= 0 && i <= max) {
                for (Integer j : boundaryValues.get(i)) {
                    found = true;
                    for (int[] ints : map.keySet()) {
                        if (inDistance(i, j, ints,map.get(ints))) {
                            found = false;
                            break;
                        }
                    }
                    if (found) {
                        System.out.println(i);
                        System.out.println(j);
                        res = (long)i*4000000 + (long)j;

                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        System.out.println(res);
    }

    private void findCoverage(int distance, int[] sensor, Set<Integer> coverage, int row) {

        distance -= Math.abs(row-sensor[1]);
        if (distance >= 0) {
            for (int i = 0; i <= distance; i++) {
                coverage.add(sensor[0]+i);
                coverage.add(sensor[0]-i);
            }
        }
    }

    private void findBoundary(int[] sensor,int distance, Map<Integer, Set<Integer>> boundary, int max) {
        for (int i = -distance; i <= distance; i++) {
            if (sensor[0]+i >= 0 && sensor[0]+i <= max) {
                int j = distance - Math.abs(i);
                if (!boundary.containsKey(sensor[0]+i)) {
                    boundary.put(sensor[0]+i, new HashSet<>());
                }
                if (sensor[1]+j >= 0 && sensor[1]+j <= max) {
                    boundary.get((sensor[0]+i)).add(sensor[1]+j);
                }
                if (sensor[1]-j >= 0 && sensor[1]-j <= max) {
                    boundary.get((sensor[0]+i)).add(sensor[1]-j);
                }
            }
        }
    }

    private boolean inDistance(int x, int y, int[] sensor, int distance) {
        return Math.abs(sensor[0]-x) + Math.abs(sensor[1]-y) <= distance;
    }

    private int findDistance(int[] sensor, int[] beacon) {
        return Math.abs(sensor[0]-beacon[0]) + Math.abs(sensor[1]-beacon[1]);
    }


    private void convertInput(List<String> list, Map<int[], Integer> map, Set<Integer> beacons, int target) {
        for (String s : list) {
            int[] sensor = new int[2];
            int[] beacon = new int[2];
            s = s.replaceAll("[^0-9\\-]", " ");
            String[] strs = s.trim().split(" +");
            sensor[0] = Integer.parseInt(strs[0]);
            sensor[1] = Integer.parseInt(strs[1]);
            beacon[0] = Integer.parseInt(strs[2]);
            beacon[1] = Integer.parseInt(strs[3]);
            map.put(sensor, findDistance(sensor,beacon));
            if (beacons != null&& beacon[1] == target) {
                beacons.add(beacon[1]);
            }
        }
    }
}

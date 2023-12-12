package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Day11 extends Day {

    public static void main(String[] args) throws IOException {
        Day11 day = new Day11();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = countSumOfDistancesBetweenGalaxies(list, 1);
        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = countSumOfDistancesBetweenGalaxies(list, (int)1e6-1);
        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long countSumOfDistancesBetweenGalaxies(List<String> list, int spaceExpansion) {
        long sum = 0;
        char[][] map = convertTo2DCharArray(list);
        Set<Integer> emptyRows = new HashSet<>();
        Set<Integer> emptyCols = new HashSet<>();
        List<int[]> galaxiesPositions = new ArrayList<>();

        for (int i = 0; i < map.length; ++i) {
            boolean empty = true;
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] == '#') {
                    empty = false;
                    galaxiesPositions.add(new int[]{i, j});
                }
            }
            if (empty) emptyRows.add(i);
        }
        for (int j = 0; j < map[0].length; ++j) {
            boolean empty = true;
            for (int i = 0; i < map.length; ++i) {
                if (map[i][j] == '#') {
                    empty = false;
                    break;
                }
            }
            if (empty) emptyCols.add(j);
        }

        for (int i = 0; i < galaxiesPositions.size(); ++i) {
            for (int j = i + 1; j < galaxiesPositions.size(); ++j) {
                int[] firstGalaxy = galaxiesPositions.get(i);
                int[] secondGalaxy = galaxiesPositions.get(j);
                sum += Math.abs(firstGalaxy[0] - secondGalaxy[0]) + Math.abs(firstGalaxy[1] - secondGalaxy[1]);
                for (int row : emptyRows) {
                    if (row > firstGalaxy[0] && row < secondGalaxy[0] || row < firstGalaxy[0] && row > secondGalaxy[0]) sum += spaceExpansion;
                }
                for  (int col : emptyCols) {
                    if (col > firstGalaxy[1] && col < secondGalaxy[1] || col < firstGalaxy[1] && col > secondGalaxy[1]) sum += spaceExpansion;
                }
            }
        }

        return sum;
    }
}

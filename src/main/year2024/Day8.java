package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;
import main.utils.MathUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 extends Day {

    public static void main(String[] args) throws IOException {
        Day8 day = new Day8();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        Map<Character, List<int[]>> nodes = readAntennasPositions(map);

        for (char node : nodes.keySet()) {
            for (int[] first : nodes.get(node)) {
                for (int[] second : nodes.get(node)) {
                    if (second[0] != first[0] || second[1] != first[1]) {
                        int dx = Math.abs(first[0] - second[0]);
                        int dy = Math.abs(first[1] - second[1]);

                        int x = first[0] < second[0] ? first[0] - dx : first[0] + dx;
                        int y = first[1] < second[1] ? first[1] - dy : first[1] + dy;
                        if (ArraysUtils.inRange(x, y, map) && map[x][y] !='#') {
                            map[x][y] = '#';
                            ++sum;
                        }

                        x = second[0] < first[0] ? second[0] - dx : second[0] + dx;
                        y = second[1] < first[1] ? second[1] - dy : second[1] + dy;
                        if (ArraysUtils.inRange(x, y, map) && map[x][y] !='#') {
                            map[x][y] = '#';
                            ++sum;
                        }                        
                    }
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        Map<Character, List<int[]>> nodes = readAntennasPositions(map);

        for (char node : nodes.keySet()) {
            for (int[] first : nodes.get(node)) {
                for (int[] second : nodes.get(node)) {
                    if (second[0] != first[0] || second[1] != first[1]) {
                        int dx = first[0] - second[0];
                        int dy = first[1] - second[1];
                        sum += countAntinodes(map, first[0], first[1], dx, dy);
                        sum += countAntinodes(map, first[0], first[1], -dx, -dy);                 
                    }
                }
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int countAntinodes(char[][] map, int x, int y, int dx, int dy) {
        int res = 0;
        while (ArraysUtils.inRange(x, y, map)) {
            if (map[x][y] !='#') {
                map[x][y] = '#';
                ++res;
            }
            x += dx;
            y += dy;
        }

        return res;
    }

    private Map<Character, List<int[]>> readAntennasPositions(char[][] map) {
        Map<Character, List<int[]>> antennas = new HashMap<>();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] >= 'A' && map[i][j] <= 'Z' || map[i][j] >= 'a' && map[i][j] <= 'z'
                        || map[i][j] >= '0' && map[i][j] <= '9') {
                    if (!antennas.containsKey(map[i][j])) {
                        antennas.put(map[i][j], new ArrayList<>());
                    }
                    antennas.get(map[i][j]).add(new int[]{i, j});
                }
            }
        }       
        
        return antennas;
    }
}

package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Day14 extends Day {

    public static void main(String[] args) throws IOException {
        Day14 day = new Day14();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        int max = map.length;
        for (int i = 0; i < map[0].length; ++i) {
            int cur = max;
            for (int j = 0; j < map.length; ++j) {
                if (map[j][i] == 'O') {
                    sum += cur--;
                } else if (map[j][i] == '#') {
                    cur = max - j - 1;
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        Map<String, Integer> states = new HashMap<>();
        int state = 0;
        states.put(convert2DArrayToString(map), 0);
        for (int l = 1; l <= 1000000000; ++l) {
            runCycle(map);
            String str = convert2DArrayToString(map);
            if (!states.containsKey(str)) {
                states.put(str, l);
            } else {
                int cycleLength = l - states.get(str);
                state = ((int)1e9 - states.get(str)) % cycleLength + states.get(str);
                break;
            }
        }

        for (String s : states.keySet()) {
            if (states.get(s) == state) {
                for (int i = 0; i < map.length; ++i) {
                    for (int j = 0; j < map[0].length; ++j) {
                        map[i][j] = s.charAt(i*map[0].length + j);
                    }
                }
                break;
            }
        }

        System.out.printf("Part 2 answer is: %d\n", calculateNorthTotalLoad(map));
    }

    private String convert2DArrayToString(char[][] arr) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < arr.length; ++j) {
            for (int k = 0; k < arr[0].length; ++k) {
                sb.append(arr[j][k]);
            }
        }  
        return sb.toString();
    }

    private void runCycle(char[][] map) {
        for (int j = 0; j < map[0].length; ++j) {
            roll(map,0, map.length, j,j);
        }
        for (int j = 0; j < map.length; ++j) {
            roll(map,j,j, 0, map[0].length);
        }                
        for (int j = 0; j < map[0].length; ++j) {
            roll(map,map.length-1, -1, j ,j);
        }
        for (int j = 0; j < map.length; ++j) {
            roll(map,j,j, map[0].length-1, -1);
        }           
    }

    private void roll(char[][] map, int x1, int x2, int y1, int y2) {
        int rX = x1, rY = y1, diffX = 0, diffY = 0;
        if (x1 == x2) {
            ++x2;
            diffY = y2 > y1 ? 1 : -1;
        } else {
            ++y2;
            diffX = x2 > x1 ? 1 : -1;
        }

        for (int i = x1;i != x2; i += diffX == 0 ? 1 : diffX) {
            for (int j = y1;j != y2; j += diffY == 0 ? 1 : diffY) {
                while (inRange(rX, rY, map) && map[rX][rY] != '.') {
                    rX += x1 == x2 ? 0 : diffX;
                    rY += y1 == y2 ? 0 : diffY;
                }
                if (!inRange(rX, rY, map)) break;
                if (map[i][j] == 'O' && (diffX != 0 && (i - rX) / diffX > 0 || diffY != 0 && (j - rY) / diffY > 0)) {
                    map[rX][rY] = 'O';
                    map[i][j] = '.';
                } else if (map[i][j] == '#') {
                    rX = i + diffX;
                    rY = j + diffY;
                }
            }
        }
    }

    private boolean inRange(int x, int y, char[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }

    private int calculateNorthTotalLoad(char[][] map) {
        int max = map.length;
        int sum = 0;
        for (int i = 0; i < map[0].length; ++i) {
            for (int j = 0; j < map.length; ++j) {
                if (map[j][i] == 'O') {
                    sum += max - j;
                }
            }
        }
        return sum;
    }

}
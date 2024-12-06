package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Day6 extends Day {
    int[][] dirs = new int[][]{{-1,0}, {0,1}, {1,0}, {0,-1}};

    public static void main(String[] args) throws IOException {
        Day6 day = new Day6();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        int x = 0;
        int y = 0;
        int dir = 0;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == '^') {
                    x = i;
                    y = j;
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", findGuardPath(map, x, y, dir).size());
    }

    public void solve2(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        int x0 = 0;
        int y0 = 0;
        int dir0 = 0;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == '^') {
                    x0 = i;
                    y0 = j;
                }
            }
        }

        Set<Integer> guardPath = findGuardPath(map, x0, y0, dir0);
        guardPath.remove(1000 * x0 + y0);

        Set<Integer> commonPath = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        for (int pos : guardPath) {
            int obsX = pos / 1000;
            int obsY = pos % 1000;
            map[obsX][obsY] = '#';
            int x = x0;
            int y = y0;
            int dir = dir0;
            boolean isNewCommonPart = true;
            while (true) {
                int p = x * 10000 + y * 10 + dir;
                if (commonPath.contains(p) || visited.contains(p)) {
                    ++sum;
                    break;
                }
                if (isNewCommonPart) {
                    commonPath.add(p);
                } else {
                    visited.add(p);
                }
                if (isLeavingMap(map, x, y, dir)) break;

                int nx = x + dirs[dir][0];
                int ny = y + dirs[dir][1];
                if (ArraysUtils.inRange(nx, ny, map)) {
                    if (map[nx][ny] == '#') {
                        if (nx == obsX && ny == obsY && isNewCommonPart) {
                            x0 = x + dirs[dir][0];
                            y0 = y + dirs[dir][1];
                            dir0 = dir;
                            isNewCommonPart = false;
                        }
                        dir = (dir + 1) % 4;
                    } else {
                        x = nx;
                        y = ny;
                    }
                }
            }
            map[obsX][obsY] = '.';
            visited.clear();
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private LinkedHashSet<Integer> findGuardPath(char[][] map, int x, int y, int dir) {
        LinkedHashSet<Integer> visited = new LinkedHashSet<>();
        while (true) {
            visited.add(x * 1000 + y);
            if (isLeavingMap(map, x, y, dir)) break;
            
            int nx = x + dirs[dir][0];
            int ny = y + dirs[dir][1];
            if (map[nx][ny] == '#') {
                dir = (dir + 1) % 4;
            } else {
                x = nx;
                y = ny;
            }
        }

        return visited;
    }

    private boolean isLeavingMap(char[][] map, int x, int y, int dir) {
        if (x == 0 && dir == 0 || y == map[0].length - 1 && dir == 1
                || x == map.length - 1 && dir == 2 || y == 0 && dir == 3) {
            return true;
        }
        return false;
    }
}

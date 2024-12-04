package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class Day4 extends Day {
    int[][] dirs = new int[][]{{-1,-1},{-1,0}, {-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1}};

    public static void main(String[] args) throws IOException {
        Day4 day = new Day4();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        Instant start = Instant.now();
        day.solve1(input);
        Instant p1 = Instant.now();
        day.solve2(input);
        Instant p2 = Instant.now();
        System.out.println(p1.toEpochMilli() - start.toEpochMilli());
        System.out.println(p2.toEpochMilli() - start.toEpochMilli());
    }

    public void solve1(List<String> list) {
        int sum = 0;
        char[] template = {'X', 'M', 'A', 'S'};
        char[][] arr = convertTo2DCharArray(list);

        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[0].length; ++j) {
                if (arr[i][j] == 'X') {
                    sum += xmasCount(i, j, arr, template);
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    private int xmasCount(int x, int y, char[][] arr, char[] template) {
        int res = 0;

        for (int k = 0; k < 8; ++k) {
            boolean xmasFound = true;
            int nx = x;
            int ny = y;
            for (int i = 1; i < template.length; ++i) {
                nx += dirs[k][0];
                ny += dirs[k][1];
                if (!ArraysUtils.inRange(nx, ny, arr) || arr[nx][ny] != template[i]) {
                    xmasFound = false;
                    break;
                }
            }
            if (xmasFound) {
                ++res;
            }
        }

        return res;
    }

    public void solve2(List<String> list) {
        int sum = 0;
        char[][] arr = convertTo2DCharArray(list);
        for (int i = 1; i < arr.length - 1; ++i) {
            for (int j = 1; j < arr[0].length - 1; ++j) {
                if (arr[i][j] == 'A') {
                    if (findXmas(i, j, arr)) {
                        ++sum;
                    }

                } 
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    public boolean findXmas(int x, int y, char[][] arr) {
        int xmasCount = 0;
        for (int i = 0; i < 8; i += 2) {
            int fx = x + dirs[i][0];
            int fy = y + dirs[i][1];
            int sx = x + dirs[(i + 4) % 8][0];
            int sy = y + dirs[(i + 4) % 8][1];
            if (arr[fx][fy] == 'M' && arr[sx][sy] == 'S') {
                ++xmasCount;
            }
        }

        if (xmasCount == 2) {
            return true;
        }
        return false;
    }
}

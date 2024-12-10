package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Day10 extends Day {
    int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public static void main(String[] args) throws IOException {
        Day10 day = new Day10();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;

        char[][] map = convertTo2DCharArray(list);
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == '0') {
                    sum += countPoints(map, i, j, true);
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;

        char[][] map = convertTo2DCharArray(list);
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == '0') {
                    sum += countPoints(map, i, j, false);
                }
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int countPoints(char[][] map, int x, int y, boolean countOnce) {
        int res = 0;
        
        Deque<int[]> dq = new ArrayDeque<>();
        dq.offerLast(new int[]{x, y, 0});
        boolean[][] visited = new boolean[map.length][map[0].length];
        while (dq.size() > 0) {
            int[] curr = dq.pollFirst();
            if (map[curr[0]][curr[1]] == '9') {
                ++res;
            }
            for (int i = 0; i < 4; ++i) {
                int nx = curr[0] + dirs[i][0];
                int ny = curr[1] + dirs[i][1];
                int m = curr[2] + 1;
                if (ArraysUtils.inRange(nx, ny, map) 
                        && (!countOnce || countOnce && !visited[nx][ny])
                        && map[nx][ny] - map[curr[0]][curr[1]] == 1) {
                    dq.offer(new int[]{nx, ny, m});
                    if (countOnce) {
                        visited[nx][ny] = true;
                    }

                }
            }
        }

        return res;
    }

}

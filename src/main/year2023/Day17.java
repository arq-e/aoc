package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Arrays;

public class Day17 extends Day {
    int[][] moves = new int[][]{{0,1},{-1,0},{0,-1},{1,0}};

    public static void main(String[] args) throws IOException {
        Day17 day = new Day17();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        System.out.printf("Part 1 answer is: %d\n", findCheapestPath(convertTo2DCharArray(list), 0, 3));
    }

    public void solve2(List<String> list) {
        System.out.printf("Part 2 answer is: %d\n", findCheapestPath(convertTo2DCharArray(list),4,10));
    }

    private int findCheapestPath(char[][] map, int minLength, int maxLength) {
        PriorityQueue<int[]> active = new PriorityQueue<>((a,b) -> a[2] - b[2]);
        int[][][][] bestScores = new int[map.length][map[0].length][4][maxLength+1];
        for (int[][][] row : bestScores) {
            for (int[][] pos : row) {
                for (int[] dir : pos) {
                    Arrays.fill(dir, Integer.MAX_VALUE);
                }
            }
        }
        active.add(new int[]{0,0,0,0,0});
        active.add(new int[]{0,0,0,3,0});
        while (active.size() > 0) {
            int[] cur = active.poll();
            if (cur[0] == map.length-1 && cur[1] == map[0].length-1) {
                return cur[2];
            }
            if (cur[2] <= bestScores[cur[0]][cur[1]][cur[3]][cur[4]]) {
                move(active, cur, map, bestScores, minLength, maxLength);               
            }
        }
        
        return -1;
    }

    private void move(PriorityQueue<int[]> pq, int[] cur, char[][] map, int[][][][] visited, int min, int max) {
        for (int i = 0; i < 4; ++i) {
            if ((i+2) % 4 == cur[3]) continue;
            if (cur[3] == i && cur[4] < max || cur[3] != i && cur[4] >= min) {
                int step = cur[3] == i ? cur[4] + 1 : 1;
                int x = cur[0] + moves[i][0];
                int y = cur[1] + moves[i][1];
                if (inRange(x, y, map) && isNewBest(map, visited, x, y, cur[2], i, step)) {
                    pq.offer(new int[]{x, y, visited[x][y][i][step], i, step});
                }
            }
        }
    }

    private boolean isNewBest(char[][] map, int[][][][] bestScores, int x, int y, int val, int dir, int step) {
        if (val + (map[x][y] - '0') < bestScores[x][y][dir][step]) {
            bestScores[x][y][dir][step] = val + (map[x][y] - '0');
            return true;
        }
        return false;
    }

    public static boolean inRange(int x, int y, char[][] arr) {
        return x >= 0 && x < arr.length && y >= 0 && y < arr.length;
    }    
}
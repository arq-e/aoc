package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day20 extends Day {
    int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    Map<Integer, Integer> counts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Day20 day = new Day20();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        int[] start = findPosition(map, 'S');
        int[][] scores = runDijkstra(map, start);

        System.out.printf("Part 1 answer is: %d\n", 
                findAllStrongCheats(map, scores, 100, 2));
    }

    public void solve2(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        int[] start = findPosition(map, 'S');
        int[][] scores = runDijkstra(map, start);

        System.out.printf("Part 2 answer is: %d\n", 
                findAllStrongCheats(map, scores, 100, 20));
    }

    private int[][] runDijkstra(char[][] map, int[] start) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
        int[][] scores = new int[map.length][map[0].length];
        for (int i = 0; i < scores.length; ++i) {
            Arrays.fill(scores[i], Integer.MAX_VALUE);
        }
        scores[start[0]][start[1]] = 0;
        pq.offer(new int[]{start[0], start[1], 0});
        while (pq.size() > 0) {
            int[] curr = pq.poll();
            if (curr[2] > scores[curr[0]][curr[1]]) {
                continue;
            }
            scores[curr[0]][curr[1]] = curr[2];
            for (int i = 0; i < 4; ++i){
                int nx = curr[0] + dirs[i][0];
                int ny = curr[1] + dirs[i][1];
                if (ArraysUtils.inRange(nx, ny, map) && map[nx][ny] != '#' && scores[nx][ny] > curr[2] + 1) {
                    scores[nx][ny] = curr[2] + 1;
                    pq.offer(new int[]{nx, ny, curr[2] + 1});
                }
            }
        }

        return scores;
    }

    private int findAllStrongCheats(char[][] map, int[][] scores, 
            int minCheatStrength, int maxCheatLength) {
        int count = 0;
        for (int i = 0; i < scores.length; ++i) {
            for (int j = 0; j < scores.length; ++j) {
                if (scores[i][j] < Integer.MAX_VALUE && scores[i][j] > 100) {
                    count += lookForCheats(scores, new int[]{i, j}, maxCheatLength, minCheatStrength, scores[i][j]);
                }
            }
        }
        return count;
    }

    private int lookForCheats(int[][] scores, int[] pos, int maxCheatLength, 
            int minCheatStrength, int baseValue) {
        int cheatCount = 0;
        Deque<int[]> dq = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> cheatEndpoints = new HashSet<>();
        dq.offer(new int[]{pos[0], pos[1], 0});
        while (dq.size() > 0) {
            int[] curr = dq.pollFirst();
            for (int i = 0; i < 4; ++i) {
                int nx = curr[0] + dirs[i][0];
                int ny = curr[1] + dirs[i][1];
                if (ArraysUtils.inRange(nx, ny, scores)) {
                    if (!cheatEndpoints.contains(nx * 1000 + ny) 
                            && baseValue - scores[nx][ny] >= minCheatStrength + curr[2] + 1) {
                        cheatEndpoints.add(nx * 1000 + ny);
                        int val = baseValue - scores[nx][ny] - curr[2] - 1;
                        counts.put(val, counts.getOrDefault(val, 0) + 1);
                        if (!counts.containsKey(val)) {
                            counts.put(val, 0);
                        }
                        counts.put(val, counts.get(val) + 1);
                        cheatCount++;

                    }
                    if (!visited.contains(nx * 1000 + ny) && curr[2] + 1 < maxCheatLength) {
                        dq.offerLast(new int[]{nx, ny, curr[2] + 1});
                        visited.add(nx * 1000 + ny);                                
                    }
                }
            }
        }

        return cheatCount;
    }

    private int[] findPosition(char[][] map, char sym) {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == sym) {
                    return new int[]{i, j};
                } 
            }
        }
        return new int[]{-1,-1};
    }
}

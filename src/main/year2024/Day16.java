package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day16 extends Day {
    int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    public static void main(String[] args) throws IOException {
        Day16 day = new Day16();
        
        List<String> sample = AdventInputReader.getSample();
        day.solve1(sample);
        day.solve2(sample);

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        sum = runDijkstra(map, findPosition(map, 'S'), new HashMap<>(), true);

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);

        Map<Integer, List<Integer>> incPositions = new HashMap<>();
        runDijkstra(map, findPosition(map, 'S'), incPositions, true);
        sum = restorePaths(incPositions, findPosition(map, 'E'));

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int restorePaths(Map<Integer, List<Integer>> incPositions, int[] finishPos) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visitedStates = new HashSet<>();
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < 4; ++i) {
            if (incPositions.containsKey(finishPos[0] * 10000 + finishPos[1] * 10 + i)) {
                dq.offer(finishPos[0] * 10000 + finishPos[1] * 10 + i);
            }
        }
        while(dq.size() > 0) {
            int curr = dq.poll();
            visited.add(curr / 10);
            if (incPositions.containsKey(curr)) {
                for (int p : incPositions.get(curr)) {
                    if (!visitedStates.contains(p)) {
                        dq.offerLast(p);
                        visitedStates.add(p);
                    }

                }
            }
        }

        return visited.size();
    }

    private int runDijkstra(char[][] map, int[] startPos, 
            Map<Integer, List<Integer>> incPositions, boolean trackRoutes) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[3] - b[3]);

        pq.offer(new int[]{startPos[0], startPos[1], 1, 0});
        int[][][] bestScores = new int[map.length][map[0].length][4];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                Arrays.fill(bestScores[i][j], Integer.MAX_VALUE);
            }
        }
        bestScores[startPos[0]][startPos[1]][1] = 0;
        while (pq.size() > 0) {
            int[] curr = pq.poll(); 
            if (curr[3] > bestScores[curr[0]][curr[1]][curr[2]]) {
                continue;
            }
            if (map[curr[0]][curr[1]] == 'E') {
                return curr[3];
            }
            int nx = curr[0] + dirs[curr[2]][0];
            int ny = curr[1] + dirs[curr[2]][1];
            if (ArraysUtils.inRange(nx, ny, map) && map[nx][ny] != '#') {
                if (curr[3] + 1 < bestScores[nx][ny][curr[2]]) {
                    bestScores[nx][ny][curr[2]] = curr[3] + 1;
                    pq.offer(new int[]{nx, ny, curr[2], curr[3] + 1});
                    if (trackRoutes) {
                        int nextPos = nx * 10000 + ny * 10 + curr[2];
                        if (!incPositions.containsKey(nextPos)) {
                            incPositions.put(nextPos, new ArrayList<>());
                        }
                        incPositions.get(nextPos).add(curr[0] * 10000 + curr[1] * 10 + curr[2]);
                    }
                } else if (curr[3] + 1 == bestScores[nx][ny][curr[2]]) {
                    if (trackRoutes) {
                        int nextPos = nx * 10000 + ny * 10 + curr[2];
                        incPositions.get(nextPos).add(curr[0] * 10000 + curr[1] * 10 + curr[2]);
                    }
                }
            }
            for (int i = 0; i < 4; ++i) {
                if (i != curr[2]) {
                    if (curr[3] + 1000 < bestScores[curr[0]][curr[1]][i]) {
                        bestScores[curr[0]][curr[1]][i] = curr[3] + 1000;
                        pq.offer(new int[]{curr[0], curr[1], i, curr[3] + 1000});
                        if (trackRoutes) {
                            int nextPos = curr[0] * 10000 + curr[1] * 10 + i;
                            if (!incPositions.containsKey(nextPos)) {
                                incPositions.put(nextPos, new ArrayList<>());
                            }
                            incPositions.get(nextPos).add(curr[0] * 10000 + curr[1] * 10 + curr[2]);
                        }
                    }   else if (curr[3] + 1000 == bestScores[curr[0]][curr[1]][i]) {
                        if (trackRoutes) {
                            int nextPos = curr[0] * 10000 + curr[1] * 10 + i;
                            incPositions.get(nextPos).add(curr[0] * 10000 + curr[1] * 10 + curr[2]);
                        }
                    }                 
                }
            }
        }
        return -1;
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

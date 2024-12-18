package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class Day18 extends Day {
    int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};    
    int mapLimit = 70;
    int pt1Limit = 1024;

    public static void main(String[] args) throws IOException {
        Day18 day = new Day18();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = new char[mapLimit + 1][mapLimit + 1];
        int idx = 0;
        for (String s : list) {
            int[] c = parseInts(s);
            map[c[1]][c[0]] = '#';
            if (++idx == pt1Limit) {
                break;
            }
        }

        System.out.printf("Part 1 answer is: %d\n", runDijkstra(map));
    }

    public void solve2(List<String> list) {
        String res = "";
        char[][] map = new char[mapLimit + 1][mapLimit + 1];
        for (String s : list) {
            int[] c = parseInts(s);
            map[c[1]][c[0]] = '#';
        }

        for (int j = list.size() - 1; j >= 0; --j) {
            if (runDijkstra(map) != Integer.MAX_VALUE) {
                break;
            }
            int[] c = parseInts(list.get(j));
            map[c[1]][c[0]] = ' ';
            res = list.get(j);
        }

        System.out.printf("Part 2 answer is: %s\n", res);
    }

    private int runDijkstra(char[][] map) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
        int[][] visited = new int[mapLimit + 1][mapLimit + 1];
        for (int i = 0; i <= mapLimit; ++i) {
            Arrays.fill(visited[i], Integer.MAX_VALUE);
        }
        pq.offer(new int[]{0,0,0});      
        
        while (pq.size() > 0) {
            int[] curr = pq.poll();
            if (visited[curr[0]][curr[1]] <= curr[2]) {
                continue;
            }
            visited[curr[0]][curr[1]] = curr[2];
            for (int[] dir : dirs) {
                int nx = curr[0] + dir[0];
                int ny = curr[1] + dir[1];
                if (ArraysUtils.inRange(nx, ny, map) && map[nx][ny] != '#' && visited[nx][ny] > curr[2] + 1) {
                    pq.offer(new int[]{nx, ny, curr[2] + 1});
                }
            }
        }

        return visited[mapLimit][mapLimit];
    }

    private static int[] parseInts(String s) {
        return Stream.of(s.replaceAll("[^0123456789-]", "\s").trim().split("\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
    }    
}

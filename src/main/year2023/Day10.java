package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayDeque;

public class Day10 extends Day {
    private Map<Character, int[][]> pipeConnections;
    public static void main(String[] args) throws IOException {
        Day10 day = new Day10();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public Day10() {
        pipeConnections = new HashMap<>();
        pipeConnections.put('|', new int[][]{{-1,0},{1,0}});
        pipeConnections.put('-', new int[][]{{0,-1},{0,1}});
        pipeConnections.put('L', new int[][]{{-1,0},{0,1}});
        pipeConnections.put('7', new int[][]{{1,0},{0,-1}});
        pipeConnections.put('J', new int[][]{{-1,0},{0,-1}});
        pipeConnections.put('F', new int[][]{{1,0},{0,1}});
    }

    public void solve1(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        int[] startingPosition = getStartingPosition(map);
        restoreStartingPipe(map, startingPosition[0], startingPosition[1]);
        System.out.printf("Part 1 answer is: %d\n", traverseLoop(map, new boolean[map.length][map[0].length], startingPosition[0], startingPosition[1]));
    }

    public void solve2(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        boolean[][] visited = new boolean[map.length][map[0].length];
        int[] startingPosition = getStartingPosition(map);
        restoreStartingPipe(map, startingPosition[0], startingPosition[1]);
        traverseLoop(map, visited, startingPosition[0], startingPosition[1]);
        
        int count = 0;
        for (int i = 0; i < map.length; ++i) {
            int cur = 0;
            for (int j = 0; j < map[i].length; ++j) {
                if (visited[i][j] && (map[i][j] == '|' || map[i][j] == 'F' || map[i][j] == '7')) ++cur;
                else if (!visited[i][j] && cur % 2 == 1) {
                    ++count;
                }
            }
        } 
        System.out.printf("Part 2 answer is: %d\n", count);
    }

    private int[] getStartingPosition(char[][] map) {
        int[] pos = new int[2];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] == 'S') {
                    pos[0] = i;
                    pos[1] = j;
                    break;
                }
            }
        }
        return pos;
    }

    private int traverseLoop(char[][] map, boolean[][] visited, int x, int y) {
        Deque<int[]> active = new ArrayDeque<>();
        for (int[] move : pipeConnections.get(map[x][y]))  {
            active.addLast(new int[]{x+move[0], y+move[1], 1});
        }
        visited[x][y] = true;
        
        int dist = 0;
        while (active.size() > 0) {
            int[] curr = active.pollFirst();
            dist = curr[2];
            for (int[] move : pipeConnections.get(map[curr[0]][curr[1]])) {
                if (!visited[curr[0] + move[0]][curr[1] + move[1]]) {
                    active.add(new int[]{curr[0] + move[0], curr[1] + move[1], curr[2] + 1});
                    break;
                }
            }
            visited[curr[0]][curr[1]] = true;
        }
    
        return dist;
    }

    private void restoreStartingPipe(char[][] map, int x, int y) {
        boolean[] adj = new boolean[4];
        if (x - 1 >= 0 && (map[x-1][y] == '|' || map[x-1][y] == '7' || map[x-1][y] == 'F')){
            adj[1] = true;
        }
        if (y - 1 >= 0 && (map[x][y-1] == '-' || map[x][y-1] == '7' || map[x][y-1] == 'J')){
            adj[3] = true;
        }
        if (x + 1 < map.length && (map[x+1][y] == '|' || map[x+1][y] == 'L' || map[x+1][y] == 'J')){
            adj[0] = true;
        }
        if (y + 1 < map.length && (map[x][y+1] == '-' || map[x][y+1] == '7' || map[x][y+1] == 'J')){
            adj[2] = true;
        }

        if (adj[0] && adj[1]) {
            map[x][y] = '|';
        } else if (adj[0] && adj[2]) {
            map[x][y] = 'F';
        } else if (adj[0] && adj[3]) {
            map[x][y] = '7';
        } else if (adj[2] && adj[3]) {
            map[x][y] = '-';
        } else if (adj[1] && adj[2]) {
            map[x][y] = 'L';
        } else if (adj[1] && adj[3]) {
            map[x][y] = 'J';
        }
    }
}
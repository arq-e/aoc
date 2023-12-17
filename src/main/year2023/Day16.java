package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;

public class Day16 extends Day {

    public static void main(String[] args) throws IOException {
        Day16 day = new Day16();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        System.out.printf("Part 1 answer is: %d\n", calculateBeamPath(map, 0,0,0, new boolean[map.length][map[0].length][4]));
    }

    public void solve2(List<String> list) {
        char[][] map = convertTo2DCharArray(list);
        int max = 0;
        boolean[][][] visitedByDirection = new boolean[map.length][map[0].length][4];
        for (int k = 0; k < map.length; ++k) {
            max = Math.max(max, calculateBeamPath(map, k, 0, 0,visitedByDirection));
            max = Math.max(max, calculateBeamPath(map, k, map[0].length-1, 2,visitedByDirection));
        }
        for (int k = 0; k < map[0].length; ++k) {
            max = Math.max(max, calculateBeamPath(map, 0, k, 3,visitedByDirection));
            max = Math.max(max, calculateBeamPath(map, map.length-1, k, 1, visitedByDirection));
        }
        System.out.printf("Part 2 answer is: %d\n", max);
    }

    private int calculateBeamPath(char[][] map, int x, int y, int dir, boolean[][][] visitedByDirection) {
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{x, y, dir});
        while (deque.size() > 0) {
            int[] cur = deque.pollFirst();
            findNextPositions(map, cur[0], cur[1], cur[2], deque, visitedByDirection);
        }
        return countEnergized(visitedByDirection);
    }

    private int countEnergized(boolean[][][] visitedByDirection) {
        int sum = 0;
        for (int i = 0; i < visitedByDirection.length; ++i) {
            for (int j = 0; j < visitedByDirection[0].length; ++j) {
                for (int k = 0; k < 4; ++k) {
                    if (visitedByDirection[i][j][k]) {
                        ++sum;
                        break;
                    }
                }
            }
        }     
        clearVisited(visitedByDirection);
        return sum;  
    }

    private void clearVisited(boolean[][][] visited) {
        for (int i = 0; i < visited.length; ++i) {
            for (int j = 0; j < visited[0].length; ++j) {
                for (int k = 0; k < 4; ++k) {
                    visited[i][j][k] = false;
                }
            }
        }
    }

    private void findNextPositions(char[][] map, int x, int y, int dir, Deque<int[]> deque, boolean[][][] visitedByDirection) {
        if (!inRange(x, y, map) || visitedByDirection[x][y][dir]) return;
        if (dir == 0) {
            if (map[x][y] == '/') {
                deque.offer(new int[]{x-1, y, 1});
            } else if (map[x][y] == '\\') {
                deque.offer(new int[]{x+1, y, 3});            
            } else if (map[x][y] == '|') {
                deque.offer(new int[]{x-1, y, 1});
                deque.offer(new int[]{x+1, y, 3});     
            } else  {
                deque.offer(new int[]{x, y+1, 0});
            }
        } else if (dir == 1) {
            if (map[x][y] == '/') {
                deque.offer(new int[]{x, y+1, 0});
            } else if (map[x][y] == '\\') {
                deque.offer(new int[]{x, y-1, 2});             
            } else if (map[x][y] == '-') {
                deque.offer(new int[]{x, y-1, 2});
                deque.offer(new int[]{x, y+1, 0});
            } else {
                deque.offer(new int[]{x-1, y, 1});
            }         
        } else if (dir == 2) {
            if (map[x][y] == '/') {
                deque.offer(new int[]{x+1, y, 3});              
            } else if (map[x][y] == '\\') {
                deque.offer(new int[]{x-1, y, 1});       
            } else if (map[x][y] == '|') {
                deque.offer(new int[]{x-1, y, 1});
                deque.offer(new int[]{x+1, y, 3});  
            } else {
                deque.offer(new int[]{x, y-1, 2});
            }                
        } else if (dir == 3) {
            if (map[x][y] == '/') {
                deque.offer(new int[]{x, y-1, 2});          
            } else if (map[x][y] == '\\') {
                deque.offer(new int[]{x, y+1, 0});    
            } else if (map[x][y] == '-') {
                deque.offer(new int[]{x, y-1, 2});
                deque.offer(new int[]{x, y+1, 0});
            } else {
                deque.offer(new int[]{x+1, y, 3});
            }             
        }
        visitedByDirection[x][y][dir] = true;
    }

    private boolean inRange(int x, int y, char[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }
}
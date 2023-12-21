package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Arrays;

public class Day21 extends Day {
    int[][] dirs = new int[][]{{0,1}, {1,0},{-1, 0}, {0,-1}};
    int[][] borderStarts = new int[][]{{0,65}, {65,0}, {130, 65}, {65, 130}};
    int[][] cornerStarts = new int[][]{{0,0}, {0,130}, {130,0}, {130,130}};
    public static void main(String[] args) throws IOException {
        Day21 day = new Day21();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = convertTo2DCharArray(list);   

        System.out.printf("Part 1 answer is: %d\n", countPositions(map, getStart(map), 64, null));
    }

    public void solve2(List<String> list) {
        long sum = 0;
        int steps = 26501365;
        char[][] map = convertTo2DCharArray(list); 
        int[] oddEvenCenter = new int[2];
        countOddEven(map, getStart(map), oddEvenCenter); 
        sum += oddEvenCenter[1];

        int[][] oddEvenBorder = new int[4][2];
        int[] timeToFillBorder = new int[4];
        int[][] oddEvenCorner = new int[4][2];
        int[] timeToFillCorner = new int[4];
        for (int i = 0; i < 4; ++i) {
            timeToFillBorder[i] = countOddEven(map, borderStarts[i], oddEvenBorder[i]);
            timeToFillCorner[i] = countOddEven(map, cornerStarts[i], oddEvenCorner[i]);
        }
        steps -= 66;                
        sum += countBorder(map, steps, oddEvenBorder, timeToFillBorder);
        steps -= 66;   
        sum += countCorner(map, steps, oddEvenCorner, timeToFillCorner);
        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long countBorder(char[][] map, int steps, int[][] oddEvenBorder, int[] timeToFill) {
        long res = 0;
        boolean odd = true;
        while (steps > 0) {
            for (int i = 0; i < 4; ++i) {
                if (steps >= timeToFill[i]) {
                    res += odd ? oddEvenBorder[i][1] : oddEvenBorder[i][0];
                } else {
                    res += countPositions(map, borderStarts[i], steps, null);
                }
            }
            odd = !odd;
            steps -= 131;
        }
        return res;
    }

    private long countCorner(char[][] map, int steps, int[][] oddEvenEdge, int[] timeToFill) {

        boolean odd = false;
        List<List<Integer>> reached = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            reached.add(new ArrayList<>());
            countPositions(map, cornerStarts[i], timeToFill[i]+1, reached.get(i));
        }
        
        List<List<Integer>> tails = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            tails.add(new ArrayList<>());
        }
        long[] res = new long[4];
        int[][] count = new int[4][2];
        while (steps > 0) {
            for (int i = 0; i < 4; ++i) {
                if (steps >= timeToFill[i]) {
                    res[i] += odd ? oddEvenEdge[i][1] : oddEvenEdge[i][0];
                    if(odd) count[i][1]++;
                    else count[i][0]++;
                } else {
                    tails.get(i).add(reached.get(i).get(steps));
                    res[i] += reached.get(i).get(steps);
                }
            }
            odd = !odd;
            steps -= 131;
        }
        long total = 0;
        for (int i = 0; i < 4; ++i) {
            total += res[i];
        }
        for (int i = 0; i < 4; ++i) {
            odd = true;
            while (count[i][0] > 0 || count[i][1] > 0) {
                if (odd) {
                    count[i][1]--;
                    res[i] -= oddEvenEdge[i][1];
                } else {
                    count[i][0]--;
                    res[i] -= oddEvenEdge[i][0];                    
                }
                total += res[i];
                odd = !odd;
            }
            int pos = 0;
            while (pos < tails.get(i).size()) {
                res[i] -= tails.get(i).get(pos);
                total += res[i];
                ++pos;
            }
        }

        return total;
    }

    public int countOddEven(char[][] map, int[] start, int[] visited) {      

        int[][] grid = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; ++i) {
            Arrays.fill(grid[i], -1);
        }
        Deque<int[]> deque = new ArrayDeque<>();

        map[start[0]][start[1]] = '.';
        deque.add(new int[]{start[0], start[1], 0});
        int max = 0;
        while (deque.size() > 0) {
            int[] cur = deque.pollFirst();
            max = Math.max(cur[2], max);
            for (int[] dir : dirs) {
                int next0 = cur[0] + dir[0];
                int next1 = cur[1] + dir[1];
                if (inRange(next0, next1, map) && map[next0][next1] == '.' && grid[next0][next1] < 0) {
                    deque.offerLast(new int[]{next0, next1, cur[2]+1});
                    grid[next0][next1] = (cur[2] + 1) % 2;
                }
            }
        }
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if ((grid[i][j] == 1)) {
                    ++visited[1];
                } else if  (grid[i][j] == 0){
                    ++visited[0];
                }
            }
        }

        return max;
    }


    public int countPositions(char[][] map, int[] start, int turns, List<Integer> reached) {
        map[start[0]][start[1]] = '.';
        boolean[][][] grid = new boolean[map.length][map[0].length][turns+1];
        Deque<int[]> deque = new ArrayDeque<>();
        deque.add(new int[]{start[0], start[1], 0});
        while (deque.size() > 0) {
            int[] cur = deque.pollFirst();
            if (cur[2] >= turns) continue;
            for (int[] dir : dirs) {
                int next0 = cur[0] + dir[0];
                int next1 = cur[1] + dir[1];
                if (inRange(next0, next1, map) && map[next0][next1] == '.' && !grid[next0][next1][cur[2]+1]) {
                    deque.offerLast(new int[]{next0, next1, cur[2]+1});
                    grid[next0][next1][cur[2]+1] = true;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (grid[i][j][turns]) {
                    ++res;
                }
            }
        }
        if (reached != null) {
            for (int k = 0; k <= turns; ++k) {
                int count = 0;
                for (int i = 0; i < map.length; ++i) {
                    for (int j = 0; j < map[0].length; ++j) {
                        if (grid[i][j][k]) {
                            ++count;
                        }
                    }
                }
                reached.add(count);
            }

        }
        return res;
    }   

    private int[] getStart(char[][] map) {
        int[] start = new int[2];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                }
            }
        }   

        return start;          
    }

    private boolean inRange(int x, int y, char[][] arr) {
        return x >= 0 && x < arr.length && y >= 0 && y < arr.length;
    }
}

package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day18 extends Day {

    final int[][] directions = new int[][]{{1,0,0},{0,0,1},{0,1,0},{-1,0,0},{0,0,-1},{0,-1,0}};
    int maxi;
    int maxj;
    int maxk;
    int[][][] grid;

    public static void main(String[] args) throws IOException {
        Day18 day = new Day18();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        buildGrid(list);
        System.out.println(countSurfaceArea(false));
    }

    public void solve2(List<String> list) {
        buildGrid(list);
        System.out.println(countSurfaceArea(true));
    }

    private int countSurfaceArea(boolean countExteriorOnly) {
        int valueToCheck = 0;
        if (countExteriorOnly) {
            valueToCheck = 2;
            findWaterCoverage();
        }
        int count = 0;
        for (int i = 0; i < maxi+1; i++) {
            for (int j = 0; j < maxj+1; j++) {
                for (int k = 0; k<maxk+1; k++) {
                    if (grid[i][j][k] == 1) {
                        for (int[] dir : directions) {
                            if (grid[i+dir[0]][j+dir[1]][k+dir[2]] == valueToCheck) count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private  void findWaterCoverage() {
        Deque<int[]> deque= new ArrayDeque<>();
        deque.add(new int[]{0,0,0});
        while (deque.size() > 0) {
            int[] p = deque.pollLast();
            for (int[] dir : directions) {
                if (inBounds(p[0] + dir[0], dir) && inBounds(p[1] + dir[1], dir) &&
                        inBounds(p[2] + dir[2], dir) && grid[p[0]+dir[0]][p[1]+dir[1]][p[2]+dir[2]] == 0) {
                    deque.addFirst(new int[]{p[0]+dir[0],p[1]+dir[1],p[2]+dir[2]});
                    grid[p[0]+dir[0]][p[1]+dir[1]][p[2]+dir[2]] = 2;
                }
            }
        }
    }


    private boolean inBounds(int n, int[] dir) {
        if (n < 0) return false;
        if (dir[0] == 1 && n > maxi) return false;
        if (dir[1] == 1 && n > maxj) return false;
        if (dir[2] == 1 && n > maxk) return false;
        return true;
    }

    private void buildGrid(List<String> list) {
        if (grid == null) {
            List<int[]> droplets = new ArrayList<>();
            for (String s : list) {
                int[] coordinates = new int[3];
                String[] strs = s.split(",");
                for (int i = 0; i< 3; i++) {
                    coordinates[i] = Integer.parseInt(strs[i])+1;
                }
                if (coordinates[0] >= maxi) maxi = coordinates[0]+1;
                if (coordinates[1] >= maxj) maxj = coordinates[1]+1;
                if (coordinates[2] >= maxk) maxk = coordinates[2]+1;
                droplets.add(coordinates);
            }
            grid = new int[maxi+1][maxj+1][maxk+1];
            for (int[] pos : droplets.toArray(new int[0][])) {
                grid[pos[0]][pos[1]][pos[2]] = 1;
            }            
        }
    }
}

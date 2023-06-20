package main.year2021;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends Day {

    public static void main(String[] args) throws IOException {
        Day5 day = new Day5();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        int[][] input = convertInput(list);
        char[][] grid = new char[1000][1000];
        for (char[] chars : grid) {
            Arrays.fill(chars, '.');
        }
        for (int[] entry : input) {
            if (entry[0] == entry[2] || entry[1] == entry[3]) {
                drawLine(grid, entry);
            }
        }
        for (char[] chars : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (chars[j] != '.' && chars[j] > '1') ++sum;
            }
        }
        System.out.println(sum);
    }


    public void solve2(List<String> list) {
        int sum = 0;
        int[][] input = convertInput(list);
        char[][] grid = new char[1000][1000];
        for (char[] chars : grid) {
            Arrays.fill(chars, '.');
        }
        for (int[] entry : input) {
            drawLine(grid, entry);
        }
        for (char[] chars : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (chars[j] != '.' && chars[j] > '1') ++sum;
            }
        }
        System.out.println(sum);
    }

    private void drawLine(char[][] grid, int[] entry) {
        if (entry[0] == entry[2]) {
            int start = Math.min(entry[1], entry[3]);
            int end = Math.max(entry[1], entry[3]);
            for (int i = start; i <= end; i++) {
                if (grid[i][entry[0]] == '.') grid[i][entry[0]] = '1';
                else ++grid[i][entry[0]];
            }
        } else if (entry[1] == entry[3]) {
            int start = Math.min(entry[0], entry[2]);
            int end = Math.max(entry[0], entry[2]);
            for (int i = start; i <= end; i++) {
                if (grid[entry[1]][i] == '.') grid[entry[1]][i] = '1';
                else ++grid[entry[1]][i];
            }
        } else {
            int[] step;
            if (entry[2] > entry[0] && entry[3] > entry[1]){
                step = new int[]{1,1};
            } else if (entry[2] < entry[0] && entry[3] < entry[1]) {
                step = new int[]{-1,-1};
            } else if (entry[2] > entry[0]) {
                step = new int[]{1,-1};
            } else step = new int[]{-1,1};
            int[] pos = new int[]{entry[0], entry[1]};
            while(pos[0] != entry[2]) {
                if (grid[pos[1]][pos[0]] == '.') grid[pos[1]][pos[0]] = '1';
                else ++grid[pos[1]][pos[0]];
                pos[0] += step[0];
                pos[1] += step[1];
            }
            if (grid[pos[1]][pos[0]] == '.') grid[pos[1]][pos[0]] = '1';
            else ++grid[pos[1]][pos[0]];
        }
    }

    private int[][] convertInput(List<String> list) {
        List<int[]> res = new ArrayList<>();
        for (String s : list) {
            String[] vals = s.split(" -> ");
            String[] start = vals[0].split(",");
            String[] end = vals[1].split(",");
            int[] str = new int[4];
            str[0] = Integer.parseInt(start[0]);
            str[1] = Integer.parseInt(start[1]);
            str[2] = Integer.parseInt(end[0]);
            str[3] = Integer.parseInt(end[1]);
            res.add(str);
        }
        return res.toArray(new int[0][]);
    }
}

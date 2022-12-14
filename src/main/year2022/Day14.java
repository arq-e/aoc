package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day14 extends Day {

    public static void main(String[] args) throws IOException {
        Day14 day = new Day14();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        System.out.println(devouredBySand(convertInput(list,0)));
    }

    public void solve2(List<String> list) {
        System.out.println(devouredBySand(convertInput(list,2)));
    }

    private int devouredBySand(char[][] map) {
        int lenX = map.length;
        int lenY = map[0].length;
        boolean overflow = false;
        int count = 0;
        while (true) {
            int x = 0;
            int y = 500;
            if (map[x][y] == 'o') break;
            while (true) {
                if (!outOfBound(x+1,y, lenX, lenY) && map[x+1][y] == '.') {
                    x++;
                } else if (!outOfBound(x+1,y-1, lenX, lenY) && map[x+1][y-1] == '.') {
                    x++;
                    y--;
                } else if (!outOfBound(x+1,y+1, lenX, lenY) &&map[x+1][y+1] == '.' ) {
                    x++;
                    y++;
                } else if (outOfBound(x+1,y, lenX, lenY) || outOfBound(x+1,y-1, lenX, lenY)
                        || outOfBound(x+1,y+1, lenX, lenY)) {
                    overflow = true;
                    break;
                } else{
                    map[x][y] = 'o';
                    count++;
                    break;
                }
            }
            if (overflow) break;
         }

        return count;
    }

    private boolean outOfBound(int x, int y, int lenX, int lenY) {
        return x < 0 || x >= lenX || y < 0 || y >= lenY;
    }

    private char[][] convertInput(List<String> list, int extraDepth) {
        Map<int[], int[]> walls = new HashMap<>();
        int depth = 0;
        int width = 0;
        for (String s: list) {
            int[][] positions = parsePositions(s);
            for (int i = 0; i < positions.length-1; i++) {
                walls.put(positions[i], positions[i+1]);
                if (positions[i][0] > width) width = positions[i][0];
                if (positions[i][1] > depth) depth = positions[i][1];
            }
        }

        char[][] chars = new char[depth+extraDepth+1][width*2];
        for (char[] aChar : chars) {
            Arrays.fill(aChar, '.');
        }

        for (int[] pair : walls.keySet()) {
            int[] end = walls.get(pair);
            if (pair[1] == end[1]) {
                for (int i = Math.min(pair[0], end[0]); i <= Math.max(pair[0], end[0]); i++) {
                    chars[end[1]][i] = '#';
                }
            } else {
                for (int i = Math.min(pair[1], end[1]); i <= Math.max(pair[1], end[1]); i++) {
                    chars[i][end[0]] = '#';
                }
            }
        }

        if (extraDepth > 0) {
            for (int i = 0; i < chars[0].length;i++) {
                chars[depth+extraDepth][i] = '#';
            }
        }

        chars[0][500] ='+';
        return chars;
    }

    private int[][] parsePositions(String s) {
        List<int[]> positions = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            String[] pos;
            if (s.indexOf(" ", i) > 0) {
                pos = s.substring(i, s.indexOf(" ", i)).split(",");
            } else {
                pos = s.substring(i).split(",");
            }
            int[] p = new int[2];
            for (int j = 0; j < 2; j++) {
                p[j] = Integer.parseInt(pos[j]);
            }

            positions.add(p);
            if (s.indexOf(" ", i) < 0) break;
            else i = s.indexOf(" ", i) + 4;
        }
        return positions.toArray(new int[0][0]);
    }
}

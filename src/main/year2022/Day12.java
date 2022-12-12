package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 extends Day {

    public static void main(String[] args) throws IOException {
        Day12 day = new Day12();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve(input);
    }

    public void solve(List<String> list) {

        char[][] heightsMap = readInput(list);
        int[] start = findPosition(heightsMap, 'S');
        int[] end = findPosition(heightsMap,'E');

        int[][] pathMap = calculatePaths(heightsMap, end);

        int res = pathMap[start[0]][start[1]];
        System.out.println(res);
        solve2(list);
    }

    public void solve2(List<String> list) {
        char[][] heightsMap = readInput(list);
        int[] start = findPosition(heightsMap, 'S');
        int[] end = findPosition(heightsMap,'E');

        int[][] pathMap = calculatePaths(heightsMap, end);

        int min = pathMap[start[0]][start[1]];
        for (int i = 0; i < heightsMap.length; i++) {
            for (int j = 0; j < heightsMap[0].length;j++) {
                if (heightsMap[i][j] == 'a' && pathMap[i][j] > 0) {
                    min = Math.min(min, pathMap[i][j]);
                }
            }
        }
        System.out.println(min);
    }

    private int[] findPosition(char[][] heightsMap, char position) {
        for (int i = 0; i < heightsMap.length; i++) {
            for (int j = 0; j < heightsMap[0].length; j++) {
                if (heightsMap[i][j] == position) {
                    if (position == 'E') heightsMap[i][j] = 'z';
                    else if (position == 'S') heightsMap[i][j] = 'a';
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }

    private char[][] readInput(List<String> list) {

        char[][] heightsMap = new char[list.size()][list.get(0).length()];
        for (int i = 0; i < list.size(); i++) {
            heightsMap[i] = list.get(i).toCharArray();
        }
        return heightsMap;
    }

    private int[][] calculatePaths(char[][] heightsMap, int[] end) {
        int[][] pathMap = new int[heightsMap.length][heightsMap[0].length];
        for (int[] ints : pathMap) {
            Arrays.fill(ints, -1);
        }
        pathMap[end[0]][end[1]] = 0;

        List<int[]> activeRoutes = new ArrayList<>();
        activeRoutes.add(end);

        int[] direction = new int[]{1, 0, -1, 0, 1};
        while (activeRoutes.size() > 0) {
            int size = activeRoutes.size();
            for (int i = 0; i < size; i++) {
                int[] cur = activeRoutes.get(0);
                for (int j = 0; j < 4; j++) {
                    int x = cur[0] + direction[j];
                    int y = cur[1] + direction[j+1];

                    if (!(x >= pathMap.length || x < 0 || y >= pathMap[0].length || y < 0)
                            && heightsMap[cur[0]][cur[1]] - heightsMap[x][y] <= 1){

                        if (pathMap[x][y] == -1) {
                            pathMap[x][y] = pathMap[cur[0]][cur[1]] + 1;
                            activeRoutes.add(new int[]{x,y});
                        } else {
                            pathMap[x][y] = Math.min(pathMap[x][y], pathMap[cur[0]][cur[1]] + 1);
                        }
                    }
                }
                activeRoutes.remove(0);
            }
        }

        return pathMap;
    }

}

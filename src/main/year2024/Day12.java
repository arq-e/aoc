package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day12 extends Day {
    int[][] dirs =  {{-1,0}, {0,1}, {1,0}, {0,-1}};
    int[][] dirs8 = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

    public static void main(String[] args) throws IOException {
        Day12 day = new Day12();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        int[][] gardensMap = new int[map.length][map[0].length];
        int gardenNum = 1;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (gardensMap[i][j] == 0) {
                    sum += doSomething(map, i, j, gardensMap, gardenNum++, false);
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        char[][] map = convertTo2DCharArray(list);
        int[][] gardensMap = new int[map.length][map[0].length];
        int gardenNum = 1;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (gardensMap[i][j] == 0) {
                    sum += doSomething(map, i, j, gardensMap, gardenNum++, true);

                }
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }


    private int doSomething(char[][] map, int x, int y, int[][] gardenMap, int gardenNum, boolean countOnlySides) {
        int p = 4, s = 1;
        Deque<int[]> dq = new ArrayDeque<>();
        dq.offer(new int[]{x, y});
        gardenMap[x][y] = gardenNum;

        while (dq.size() > 0) {
            int[] curr = dq.poll();
            for (int[] dir : dirs) {
                int nx = curr[0] + dir[0];
                int ny = curr[1] + dir[1];
                if (ArraysUtils.inRange(nx, ny, map) && gardenMap[nx][ny] == 0 && map[nx][ny] == map[x][y]) {
                    ++s;
                    gardenMap[nx][ny] = gardenNum;
                    dq.offer(new int[]{nx, ny});

                    int conn = 0;
                    if (!countOnlySides) {
                        for (int[] d : dirs) {
                            int adjX = nx + d[0];
                            int adjY = ny + d[1];
                            if (ArraysUtils.inRange(adjX, adjY, map) && gardenMap[adjX][adjY] == gardenNum) {
                                ++conn;
                            }
                        }
                        if (conn == 1) {
                            p += 2;
                        } else if (conn == 2) {
                            p += 0;
                        } else if (conn == 3) {
                            p -= 2;
                        } else if (conn == 4) {
                            p -= 4;
                        }
                    }
                }
            }
        }

        if (countOnlySides) {
            return countFenceCorners(gardenMap, gardenNum) * s;
        }
        return p * s;
    }


    private int countFenceCorners(int[][] gardenMap, int gardenNum) {
        int num = 0;
        for (int i = 0; i < gardenMap.length; ++i) {
            for (int j = 0; j < gardenMap[0].length; ++j) {
                if (gardenMap[i][j] == gardenNum) {
                    int[] neighbours = new int[8];
                    for (int k = 0; k < 8; ++k) {
                        int nx = i + dirs8[k][0];
                        int ny = j + dirs8[k][1];
                        if (ArraysUtils.inRange(nx, ny, gardenMap) && gardenMap[nx][ny] == gardenNum) {
                            neighbours[k] = 1;
                        }
                    }
                    for (int k = 0; k < 4; ++k) {
                        if (neighbours[k*2] == 1 && neighbours[(k*2+2)%8] == 1 && neighbours[(k*2+1)%8] == 0
                                || neighbours[k*2] == 0 && neighbours[(k*2+2)%8] == 0) {
                            ++num;
                        }
                    }
                }
            }
        }

        return num;
    }

}

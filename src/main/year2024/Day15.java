package main.year2024;

import main.utils.AdventInputReader;
import main.utils.ArraysUtils;
import main.utils.Day;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day15 extends Day {
    int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    private char[] movements;

    public static void main(String[] args) throws IOException {
        Day15 day = new Day15();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = parseMap(list);
        int[] pos = findRobot(map);
        moveRobot(map, pos[0], pos[1]);

        System.out.printf("Part 1 answer is: %d\n", countScore(map));
    }

    public void solve2(List<String> list) {
        char[][] map = expandMap(parseMap(list));
        int[] pos = findRobot(map);
        moveRobot(map, pos[0], pos[1]);

        System.out.printf("Part 2 answer is: %d\n", countScore(map));
    }

    private void moveRobot(char[][] map, int x, int y) {
        char[] d = new char[]{'^','>','v','<'};
        for (char m : movements) {
            int dir = 0;
            for (int i = 0; i < 4; ++i) {
                if (m == d[i]) {
                    dir = i;
                    break;
                }
            }
            int nx = x + dirs[dir][0];
            int ny = y + dirs[dir][1];
            if (ArraysUtils.inRange(nx, ny, map) && map[nx][ny] != '#') {
                if (dir % 2 == 1) {
                    if (moveHorizontal(map, nx, ny, dir)) {
                        map[nx][ny] = '@';
                        map[x][y] = '.';
                        x = nx;
                        y = ny;
                    }
                } else {
                    if(moveVertical(map, nx, ny, dir)) {
                        map[nx][ny] = '@';   
                        map[x][y] = '.';
                        x = nx;
                        y = ny;  
                    }
                }
            }
        }
    }

    private boolean moveHorizontal(char[][] map, int x, int y, int dir) {
        int len = 1;
        while (true) {
            if (map[x][y] == '.') {
                while (len-- > 0) {
                    map[x][y] = map[x - dirs[dir][0]][y - dirs[dir][1]];
                    x -= dirs[dir][0];
                    y -= dirs[dir][1];
                }
                break;
            } else if (map[x][y] == '#') {
                return false;
            } else {
                x += dirs[dir][0];
                y += dirs[dir][1];
                ++len;
            }
        }

        return true;
    }

    private boolean moveVertical(char[][] map, int x, int y, int dir) {
        if (map[x][y] == '#') {
            return false;
        } else if (map[x][y] == '.') {
            return true;
        }
        Map<Integer, Set<Integer>> movablePositions = new HashMap<>();
        movablePositions.put(x, new HashSet<>());
        movablePositions.get(x).add(y);
        if (map[x][y] == '[') {
            movablePositions.get(x).add(y + 1);
        } else if (map[x][y] == ']') {
            movablePositions.get(x).add(y - 1);
        }
        int prevX = x;
        x += dirs[dir][0];
        while (true) {
            boolean freeRow = true;
            for (int i : movablePositions.get(prevX)) {
                if (map[x][i] == '.')
                    continue;

                if (map[x][i] == '#') {
                    return false;
                } else {
                    freeRow = false;
                    if (!movablePositions.containsKey(x)) 
                        movablePositions.put(x, new HashSet<>());
                    if (map[x][i] == '[') {
                        movablePositions.get(x).add(i + 1);
                    } else if (map[x][i] == ']') {
                        movablePositions.get(x).add(i - 1);
                    } 
                    movablePositions.get(x).add(i);
                    prevX = x;
                }
            }

            if (freeRow){
                break;
            } else {
                x += dirs[dir][0];
            }
        }    
        
        while (movablePositions.size() > 0) {
            int prev = x - dirs[dir][0];
            for (int p : movablePositions.get(prev)) {
                map[x][p] = map[prev][p];
                map[prev][p] = '.';
            }
            movablePositions.remove(prev);
            x = prev;
        }   

        return true;
    }

    private char[][] parseMap(List<String> list) {
        int p = 0;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).length() <= 1) {
                p = i + 1;
                break;
            }
        }

        char[][] map = new char[p][];
        for (int i = 0; i < map.length; ++i) {
            map[i] = list.get(i).toCharArray();
        }

        String moves = "";
        for (int i = p; i < list.size(); ++i) {
            moves = moves + list.get(i);
        }
        movements = moves.toCharArray();

        return map;
    }

    private int[] findRobot(char[][] map) {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] == '@') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    private int countScore(char[][] map) {
        int score = 0;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] == 'O' || map[i][j] == '[') {
                    score += i * 100;
                    score += j;
                }
            }
        }

        return score;
    }

    private char[][] expandMap(char[][] map) {
        char[][] expandedMap = new char[map.length][2 * map[0].length];
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] == '#') {
                    expandedMap[i][j*2] = '#';
                    expandedMap[i][j*2 + 1] = '#';
                } else if (map[i][j] == '.') {
                    expandedMap[i][j*2] = '.';
                    expandedMap[i][j*2 + 1] = '.';                    
                } else if (map[i][j] == 'O') {
                    expandedMap[i][j*2] = '[';
                    expandedMap[i][j*2 + 1] = ']'; 
                } else if (map[i][j] == '@') {
                    expandedMap[i][j*2] = '@';
                    expandedMap[i][j*2 + 1] = '.';                     
                }
            }
        }

        return expandedMap;
    }
}

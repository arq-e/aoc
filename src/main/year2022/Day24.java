package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day24 extends Day {

    public static void main(String[] args) throws IOException {
        Day24 day = new Day24();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        char[][] map = convertInput(list);
        List<Blizzard> blizzards = findBlizzards(map);
        System.out.println(move(map, blizzards, findStart(map), findEnd(map), 0));
    }

    public void solve2(List<String> list) {
        char[][] map = convertInput(list);
        List<Blizzard> blizzards = findBlizzards(map);
        System.out.println(move(map, blizzards, findStart(map), findEnd(map), 2));
    }

    private int move (char[][] map, List<Blizzard> blizzards,int[] startPos, int[] endPos, int backwardRuns) {
        List<int[]> groupPositions = new ArrayList<>();
        groupPositions.add(startPos);
        int turn = 0;
        while (groupPositions.size() > 0) {
            ++turn;
            moveBlizzards(map, blizzards);
            if (moveElves(map, groupPositions, endPos)) break;
        }
        if (backwardRuns > 0) {
            return turn + move(map, blizzards, endPos, startPos, --backwardRuns);
        } else return turn;
    }

    private void moveBlizzards(char[][] map, List<Blizzard> blizzards) {
        for (Blizzard blizzard : blizzards) {
            if (map[blizzard.position[0]][blizzard.position[1]] > '1'
                    && map[blizzard.position[0]][blizzard.position[1]]<= '9') {
                --map[blizzard.position[0]][blizzard.position[1]];
            } else map[blizzard.position[0]][blizzard.position[1]] = '.';
            blizzard.makeMove(map);
            if (map[blizzard.position[0]][blizzard.position[1]] == '.') {
                map[blizzard.position[0]][blizzard.position[1]] = '1';
            }  else ++map[blizzard.position[0]][blizzard.position[1]];
        }
    }

    private boolean moveElves(char[][] map, List<int[]> groupPositions, int[] endPos){
        int size = groupPositions.size();
        int k = 0;
        while (k  < size) {
            int[] directions = {1, 0, -1, 0, 1};
            for (int i = 0; i < 4; i++) {
                int[] newPos = new int[]{groupPositions.get(k)[0]+directions[i],groupPositions.get(k)[1]+directions[i+1]};
                if (newPos[0] == endPos[0] && newPos[1] == endPos[1]) {
                    return true;
                }
                if (outOfBounds(map, newPos)) continue;
                if (!elvesAlreadyHere(groupPositions, newPos) && map[newPos[0]][newPos[1]] == '.') {
                    groupPositions.add(newPos);
                }
            }
            if (map[groupPositions.get(k)[0]][groupPositions.get(k)[1]] != '.') {
                groupPositions.remove(k);
                size--;
            } else k++;
        }
        return false;
    }

    private boolean outOfBounds(char[][] map, int[] position) {
        return position[0] < 0 || position[0] >= map.length
                || position[1] < 0 || position[1] >= map[0].length
                || map[position[0]][position[1]] == '#';
    }

    private boolean elvesAlreadyHere(List<int[]> positions, int[] pos) {
        for (int[] position:positions) {
            if (position[0] == pos[0] && position[1] == pos[1]) return true;
        }
        return false;
    }

    private int[] findStart(char[][] map) {
        int[] groupPosition = new int[2];
        for (int j = 0; j < map[0].length; j++) {
            if (map[0][j] == '.') {
                groupPosition[1] = j;
                break;
            }
        }
        return groupPosition;
    }

    private int[] findEnd(char[][] map) {
        int[] groupPosition = new int[2];
        for (int j = 0; j < map[0].length; j++) {
            if (map[map.length-1][j] == '.') {
                groupPosition[0] = map.length-1;
                groupPosition[1] = j;
                break;
            }
        }
        return groupPosition;
    }

    private List<Blizzard> findBlizzards(char[][] map) {
        List<Blizzard> blizzards = new ArrayList<>();
        for (int i = 1; i < map.length-1; i++) {
            for (int j = 1; j < map[1].length-1; j++) {
                if (map[i][j] != '.') {
                    blizzards.add(new Blizzard(new int[]{i, j}, map[i][j]));
                    map[i][j] = '1';
                }
            }
        }
        return blizzards;
    }

    private char[][] convertInput(List<String> list) {
        List<char[]> list1 = new ArrayList<>();
        for (String s : list) {
            list1.add(s.toCharArray());
        }
        return list1.toArray(new char[0][]);
    }

    static class Blizzard {
        int[] position;
        char direction;

        Blizzard(int[] position, char direction) {
            this.position = position;
            this.direction = direction;
        }

        public void makeMove(char[][] map) {
            switch (direction) {
                case '^':
                    position[0]-=1;
                    if (position[0] <= 0) position[0] = map.length-2;
                    break;
                case '>':
                    position[1]+=1;
                    if (position[1] >= map[0].length-1) position[1] = 1;
                    break;
                case '<':
                    position[1]-=1;
                    if (position[1] <= 0) position[1] = map[0].length-2;
                    break;
                case 'v':
                    position[0]+=1;
                    if (position[0] >= map.length-1) position[0] = 1;
                    break;
            }
        }
    }
}

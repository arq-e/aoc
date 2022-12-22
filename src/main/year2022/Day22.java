package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day22 extends Day {

    int direction;

    public static void main(String[] args) throws IOException {
        Day22 day = new Day22();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<char[]> map = new ArrayList<>();
        String path = convertInput(list, map);
        move(map.toArray(new char[0][]), path, false);
    }

    public void solve2(List<String> list) {
        List<char[]> map = new ArrayList<>();
        String path = convertInput(list, map);
        move(map.toArray(new char[0][]), path, true);
    }

    private void findNewPosition(char[][] map, int[] pos, int[] newPos) {
       int dir = -1;
        if (pos[0] < 50 && direction == 2) { //A -> E
            newPos[0] = 149-pos[0];
            newPos[1] = 0;
            dir = 0;
        } else if (pos[1] >=50 && pos[1] < 100 && direction == 3) {//A -> F
            newPos[0] = 100+pos[1];
            newPos[1] = 0;
            dir =  0;
        } else if (pos[1] >= 100 && direction == 3) {//B -> F
            newPos[0] = 199;
            newPos[1] = pos[1] - 100;
            dir = 3;
        } else if (pos[0] < 50 && direction == 0) {//B -> D
            newPos[0] = 149-pos[0];
            newPos[1] = 99;
            dir =  2;
        } else if (pos[1] >= 100 && direction == 1) {//B -> C
            newPos[0] = pos[1]-50;
            newPos[1] = 99;
            dir =  2;
        } else if (pos[0] >= 50 && pos[0] < 100 && direction == 2) {//C -> E
            newPos[0] = 100;
            newPos[1] = pos[0]-50;
            dir = 1;
        } else if (pos[0] >= 50 && pos[0] < 100  && direction == 0) { //C -> B
            newPos[0] = 49;
            newPos[1] = pos[0]+50;
            dir = 3;
        } else if (pos[0] >= 100 && pos[0] < 150  && direction == 0) {//D -> B
            newPos[0] = 149-pos[0];
            newPos[1] = 149;
            dir = 2;
        } else if ( pos[1] >=50 && pos[1] < 100 && direction == 1) {//D -> F
            newPos[0] = 100+pos[1];
            newPos[1] = 49;
            dir =  2;
        } else if ( pos[1] < 50 && direction == 3) { //E -> C
            newPos[0] = 50+pos[1];
            newPos[1] = 50;
            dir =  0;
        } else if (pos[0] >= 100 && pos[0] < 150 && direction == 2) {//E -> A
            newPos[0] = 149-pos[0];
            newPos[1] = 50;
            dir = 0;
        } else if (pos[0] >= 150 && direction == 2) {//F -> A
            newPos[0] = 0;
            newPos[1] = pos[0] - 100;
            dir =  1;
        } else if (pos[1] < 50 && direction == 1) {//F -> B
            newPos[0] = 0;
            newPos[1] = pos[1] +100;
            dir =  1;
        } else if (pos[0] >= 150 && direction == 0) {//F -> D
            newPos[0] = 149;
            newPos[1] = pos[0]-100;
            dir =  3;
        }
        if (map[newPos[0]][newPos[1]] == '#') {
            newPos[0] = pos[0];
            newPos[1] = pos[1];
            dir = -1;
        }
        if (dir != -1) direction = dir;
    }

    private void move(char[][] map, String path, boolean part2) {
        int[] pos = new int[2];
        for (int j = 0; j < map[0].length;j++) {
            if (map[0][j] == '.') {
                pos[1] = j;
                break;
            }
        }
        int i = 0;
        direction = 0;
        boolean goCommand = false;
        while (i < path.length()) {
            if (goCommand) {
                String command = path.substring(i, i+1);
                turn(map, pos, command);
                ++i;
            } else {
                int end = i+1;
                while (end < path.length() && path.charAt(end) < 60) {
                    end++;
                }
                int length = Integer.parseInt(path.substring(i,end));
                pos = moveForward(map, pos, length, part2);
                i = end;
            }
            goCommand = !goCommand;
        }
        System.out.println(1000*(pos[0]+1) + 4*(pos[1]+1) + direction);
    }

    private void turn(char[][] map, int[] pos, String command) {
        if (command.equals("R")) {
            direction = (direction + 1) %4;
        } else {
            direction = (direction + 3) %4;
        }
        map[pos[0]][pos[1]] = getDirectionChar(direction);
    }

    private int[] moveForward(char[][] map, int[] pos, int length, boolean part2) {
        int[] step = findStep(direction);
        int[] newPos = Arrays.copyOf(pos, 2);
        addStep(newPos, step);
        for (int i = 0; i < length; i++) {
            map[pos[0]][pos[1]] = getDirectionChar(direction);
            if (newPos[0] < 0 || newPos[1] < 0 ||
                    newPos[0] >= map.length || newPos[1] >= map[newPos[0]].length || map[newPos[0]][newPos[1]] == ' ') {
                if (part2) {
                    findNewPosition(map,pos,newPos);
                    step = findStep(direction);
                } else {
                    newPos = findOpposite(map, pos, direction);
                }
                if (Arrays.equals(newPos, pos)){
                    break;
                }
            } else if (map[newPos[0]][newPos[1]] == '#') break;
            pos = Arrays.copyOf(newPos, 2);
            addStep(newPos, step);
        }
        return pos;
    }

    private int[] findOpposite(char[][] map, int[] pos, int direction) {
        int[] oppositeStep = findStep((direction +2) %4);
        int[] newPos = Arrays.copyOf(pos, 2);
        while (newPos[0] < map.length && newPos[0] >= 0 && newPos[1] >= 0
                && newPos[1] < map[newPos[0]].length &&map[newPos[0]][newPos[1]] != ' ') {
            addStep(newPos, oppositeStep);
        }
        int[] step = findStep(direction);
        addStep(newPos, step);
        if (map[newPos[0]][newPos[1]] != '#') {
            return newPos;
        } else return pos;
    }

    private void addStep(int[] pos , int[] step) {
        pos[0] += step[0];
        pos[1] += step[1];
    }

    private char getDirectionChar(int direction) {
        if (direction == 0) {
            return '>';
        } else if (direction == 1) {
            return 'v';
        } else if (direction == 2) {
            return '<';
        } else return '^';
    }
    private int[] findStep(int direction) {
        if (direction == 0) {
            return new int[]{0,1};
        } else if (direction == 1) {
            return new int[]{1,0};
        } else if (direction == 2) {
            return new int[]{0,-1};
        } else return new int[]{-1,0};
    }

    private String convertInput(List<String> list, List<char[]> list1) {
        int i = 0;
        for (; i < list.size();i++) {
            if (list.get(i).equals(""))  {
                i++;
                break;
            }
            list1.add(list.get(i).toCharArray());
        }
        return list.get(i);
    }
}

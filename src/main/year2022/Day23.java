package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day23 extends Day {

    int[] directions = new int[]{0,1,2,3};

    public static void main(String[] args) throws IOException {
        Day23 day = new Day23();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Set<Elf> elves = new HashSet<>();
        char[][] map = convertInput(list, elves);
        moveElves(map, elves, 10);
        findEdges(elves);
    }

    public void solve2(List<String> list) {
        Set<Elf> elves = new HashSet<>();
        char[][] map = convertInput(list, elves);
        System.out.println(moveElves1(map, elves));
    }

    private void findEdges(Set<Elf> elves) {
        int minx = Integer.MAX_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxx = 0;
        int maxy = 0;
        for (Elf elf : elves) {
            if (elf.position[0] < minx) minx = elf.position[0];
            if (elf.position[0] > maxx) maxx = elf.position[0];
            if (elf.position[1] < miny) miny = elf.position[1];
            if (elf.position[1] > maxy) maxy = elf.position[1];
        }
        System.out.println((maxx - minx+1)*(maxy-miny+1) - elves.size());
    }

    private void moveElves(char[][] map, Set<Elf> elves, int turns) {
        for (int i = 0; i < turns; i++) {
            Map<int[], List<Elf>> newPositions = prepareMoving(map, elves);
            makeMoves(map, newPositions);
        }
    }

    private int moveElves1(char[][] map, Set<Elf> elves) {
        directions = new int[]{0,1,2,3};
        boolean end = false;
        int i = 1;
        while(!end) {
            Map<int[], List<Elf>> newPositions = prepareMoving(map, elves);
            end = makeMoves(map, newPositions);
            if (!end) ++i;
        }
        return i;
    }

    private boolean makeMoves(char[][] map, Map<int[], List<Elf>> proposedPositions) {
        boolean noMoves = true;
        if (proposedPositions.size() == 0) return true;
        for (int[] pos : proposedPositions.keySet()) {
            if (proposedPositions.get(pos).size() == 1) {
                Elf elf = proposedPositions.get(pos).get(0);
                map[pos[0]][pos[1]] = '#';
                map[elf.position[0]][elf.position[1]] = '.';
                elf.position[0] = pos[0];
                elf.position[1] = pos[1];
                noMoves = false;
            }
        }
        int dir = directions[0];
        System.arraycopy(directions, 1, directions, 0, 3);
        directions[3] = dir;
        return noMoves;
    }


    private Map<int[], List<Elf>> prepareMoving(char[][] map, Set<Elf> elves) {
        Map<int[], List<Elf>> proposedPositions = new HashMap<>();
        for (Elf elf : elves) {
            if (anyNeighbours(map,elf)) {
                if (checkDirection(map, elf)) {
                    addPosition(proposedPositions, elf);
                }
            }
        }
        return proposedPositions;
    }

    private boolean anyNeighbours(char[][] map, Elf elf) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,-1},{0,1}, {-1,-1}, {-1, 1}, {1,-1}, {1,1}};
        for (int[] i : dirs) {
            if (map[elf.position[0]+i[0]][elf.position[1] + i[1]] == '#') return true;
        }
        return false;
    }

    private void addPosition(Map<int[], List<Elf>> proposedPositions, Elf elf) {
        int[] newPosition = getNewPosition(elf);
        for (int[] pos : proposedPositions.keySet()) {
            if (pos[0] == newPosition[0] &&  pos[1] == newPosition[1]) {
                proposedPositions.get(pos).add(elf);
                return;
            }
        }
        proposedPositions.put(newPosition, new ArrayList<>());
        proposedPositions.get(newPosition).add(elf);
    }

    private int[] getNewPosition(Elf elf) {
        int[][] moves = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        return new int[]{elf.position[0]+moves[elf.moveDirection][0], elf.position[1] + moves[elf.moveDirection][1]};
    }

    private boolean checkDirection(char[][] map, Elf elf) {
        boolean res = false;
        for (int i = 0; i < 4; i++) {
            if (directions[i] == 0) {
                elf.moveDirection = 0;
                res = map[elf.position[0]-1][elf.position[1]-1] == '.' && map[elf.position[0]-1][elf.position[1]] == '.'
                        && map[elf.position[0]-1][elf.position[1]+1] == '.';
            } else if (directions[i] == 1) {
                elf.moveDirection = 1;
                res = map[elf.position[0]+1][elf.position[1]-1] == '.' && map[elf.position[0]+1][elf.position[1]] == '.'
                        && map[elf.position[0]+1][elf.position[1]+1] == '.';
            } else if (directions[i] == 2) {
                elf.moveDirection = 2;
                res =  map[elf.position[0]-1][elf.position[1]-1] == '.' && map[elf.position[0]][elf.position[1]-1] == '.'
                        && map[elf.position[0]+1][elf.position[1]-1] == '.';
            } else if (directions[i] == 3) {
                elf.moveDirection = 3;
                res = map[elf.position[0]-1][elf.position[1]+1] == '.' && map[elf.position[0]][elf.position[1]+1] == '.'
                        && map[elf.position[0]+1][elf.position[1]+1] == '.';
            }
            if (res) return true;
        }
        return false;
    }

    public char[][] convertInput(List<String>list, Set<Elf> elves) {
        char[][] map = new char[20000][20000];
        int x = 8000;
        int y = 8000;
        for (char[] chars : map) {
            Arrays.fill(chars, '.');
        }
        for (String s : list) {
            for (int i = 0; i < s.length();i++) {
                map[x][y] = s.charAt(i);
                if (map[x][y] == '#') elves.add(new Elf(new int[]{x, y}));
                ++y;
            }
            ++x;
            y = 8000;
        }
        return map;
    }

    static class Elf {
        int[] position;
        int[] directions;
        int moveDirection;

        Elf (int[] pos) {
            this.position = pos;
            directions = new int[]{0,1,2,3};
        }
    }

}

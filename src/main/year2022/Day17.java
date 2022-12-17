package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day17 extends Day {

    List<char[]> caveMap;
    List<Integer> heightChanges;

    public static void main(String[] args) throws IOException {
        Day17 day = new Day17();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        String jet = list.get(0);
        heightChanges = new ArrayList<>();
        System.out.println(findHeigth(2022,jet, false));
    }

    public void solve2(List<String> list) {
        long num = 1000000000000L;
        String jet = list.get(0);
        heightChanges = new ArrayList<>();
        findHeigth(5000,jet, true);
        int[] periodHeight = findPeriodHeight();
        long res = num/periodHeight[0]*periodHeight[1]
                + findHeigth((int)((num)%periodHeight[0]), jet, false);
        System.out.println(res);
    }

    private int[] findPeriodHeight() {
        int period = 0;
        int height = 0;
        boolean found  = true;
        for (int k = 0; k < heightChanges.size()/2; k++) {
            for (int i = (heightChanges.size()-k)/2 + k-1; i > k+30; i--) {
                found  = true;
                height = 0;
                for (int j = 0; j < i-k ;j++) {
                    if (heightChanges.get(j+k).intValue() != heightChanges.get(i+j).intValue()) {
                        found = false;
                        break;
                    }
                    height += heightChanges.get(k+j);
                }
                if (found) {
                    period = i-k;
                    break;
                }
            }
            if (found) break;
        }
        return new int[]{period, height};
    }



    private int findHeigth(int time, String jet, boolean saveHeightDiff) {
        caveMap = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            caveMap.add(new char[]{'.','.','.','.','.','.','.'});
        }
        int height = 0;
        int height0 = 0;
        int turn = 0;
        for (int i = 0; i < time; i++) {
            int start = height+3;
            while (start + 4 >= caveMap.size()) {
                caveMap.add(new char[]{'.','.','.','.','.','.','.'});
            }
            int[][] rockCoordinates = initRockCoordinates(i%5,start);
            boolean turnDown = false;
            while (true) {
                boolean stop = false;
                int[][] newCoordinates;
                if (!turnDown) {
                    newCoordinates = moveRock(rockCoordinates, jet.charAt(turn%jet.length()));
                    turn++;
                }
                else newCoordinates = moveRock(rockCoordinates, 'd');
                if (checkCoordinates(newCoordinates)) {
                    rockCoordinates = newCoordinates;
                } else if (turnDown) {
                    stop = true;
                }
                turnDown = !turnDown;
                if (stop ) {
                    height = drawRock(rockCoordinates, height);
                    break;
                }
            }
            if (saveHeightDiff) {
                heightChanges.add(height-height0);
                height0 = height;
            }
        }
        return height;
    }


    private int drawRock(int[][] coordinates, int height) {
        int newHeight = 0;
        for (int[] coordinate : coordinates) {
            caveMap.get(coordinate[0])[coordinate[1]] = '#';
            if (coordinate[0] > newHeight) newHeight = coordinate[0];
        }
        return  Math.max(newHeight+1,height);
    }

    private int[][] moveRock(int[][] coordinates, char direction) {
        int[] move = new int[2];
        if (direction == '<') {
            move[1] = -1;
        } else if (direction == '>') {
            move[1] = 1;
        } else {
            move[0] = -1;
        }
        int[][] newCoordinates = new int[coordinates.length][2];
        for (int i = 0; i < coordinates.length;i++) {
            newCoordinates[i][0] = coordinates[i][0]+move[0];
            newCoordinates[i][1] = coordinates[i][1]+move[1];
        }
        return newCoordinates;
    }

    private boolean checkCoordinates(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            if (coordinate[1] < 0 || coordinate[1] > 6) return false;
            if (coordinate[0] < 0) return false;
            if (caveMap.get(coordinate[0])[coordinate[1]] == '#') return false;
        }
        return true;
    }

    private int[][] initRockCoordinates(int patternNumber, int start) {
        int[][] coordinates = new int[0][];
        if (patternNumber == 0) {
            coordinates = new int[4][2];
            for (int i = 0; i < 4; i++) {
                coordinates[i][0] = start;
                coordinates[i][1] = 2+i;
            }
        } else if (patternNumber == 1) {
            coordinates = new int[4][2];
            coordinates[0][0] = start;
            coordinates[0][1] = 3;
            coordinates[1][0] = start+1;
            coordinates[1][1] = 2;
            coordinates[2][0] = start+1;
            coordinates[2][1] = 4;
            coordinates[3][0] = start+2;
            coordinates[3][1] = 3;
        } else if (patternNumber == 2) {
            coordinates = new int[5][2];
            coordinates[0][0] = start;
            coordinates[0][1] = 2;
            coordinates[1][0] = start;
            coordinates[1][1] = 3;
            coordinates[2][0] = start;
            coordinates[2][1] = 4;
            coordinates[3][0] = start+1;
            coordinates[3][1] = 4;
            coordinates[4][0] = start+2;
            coordinates[4][1] = 4;
        } else if (patternNumber == 3) {
            coordinates = new int[4][2];
            for (int i = 0; i < 4; i++) {
                coordinates[i][0] = start+i;
                coordinates[i][1] = 2;
            }
        } else if (patternNumber == 4) {
            coordinates = new int[4][2];
            for (int i = 0; i < 2; i++) {
                coordinates[i][0] = start+i;
                coordinates[i][1] = 2;
                coordinates[i+2][0] = start+i;
                coordinates[i+2][1] = 3;
            }
        }
        return coordinates;
    }
}

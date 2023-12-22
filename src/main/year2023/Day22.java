package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;
import main.utils.ParsingUtils;

import java.io.IOException;
import java.util.*;

public class Day22 extends Day {

    public static void main(String[] args) throws IOException {
        Day22 day = new Day22();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        Map<Integer, int[][]> lines = new HashMap<>();
        Map<Integer, Set<Brick>> brickRow = new HashMap<>();
        List<Brick> bricks = new ArrayList<>();        
        processBricks(lines, brickRow, list, bricks);
        for (Brick brick : bricks) {
            if(tryRemove(lines, brick, brickRow)) {
                ++sum;
            } 
        }
        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        Map<Integer, int[][]> lines = new HashMap<>();
        Map<Integer, Set<Brick>> brickRow = new HashMap<>();
        List<Brick> bricks = new ArrayList<>();        
        processBricks(lines, brickRow, list, bricks);

        for (Brick brick : bricks) {
            if(!tryRemove(lines, brick, brickRow)) {
                sum += desintagrate(lines, brick, brickRow);
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private void processBricks(Map<Integer, int[][]> lines, Map<Integer, Set<Brick>> brickRow, List<String> list, List<Brick> bricks) {
        int ind = 1;
        for (String s : list) {
            List<Integer> coords = ParsingUtils.getInts(s, 0);
            Brick brick = new Brick(coords, ind);
            bricks.add(brick);
            ++ind;
        }       
        Collections.sort(bricks);
        int max = bricks.get(bricks.size()-1).z[1];
        for (int i = 0; i <= max; ++i) {
            lines.put(i, new int[500][500]);
            brickRow.put(i, new HashSet<>());            
        }        
        for (Brick brick : bricks) {
            drawBrick(lines, brick, brickRow, true);
        }
    }

    private void drawBrick(Map<Integer, int[][]> lines, Brick brick, Map<Integer, Set<Brick>> brickRow, boolean tryDown) {
        if (tryDown) {
            while (brick.z[0] > 1 && !checkBrick(lines, brick.z[0], brick)) {
                brick.moveDown();
            }
        }
        for (int i = brick.z[0]; i <= brick.z[1]; ++i) {
            for (int j = brick.x[0]; j <= brick.x[1]; ++j) {
                for (int k = brick.y[0]; k <= brick.y[1]; ++k) {
                    lines.get(i)[j][k] = brick.index;
                }
            }
            brickRow.get(i).add(brick);
        }
    }

    private void removeBrick(Map<Integer, int[][]> lines, Brick brick) {
        for (int i = brick.z[0]; i <= brick.z[1]; ++i) {
            for (int j = brick.x[0]; j <= brick.x[1]; ++j) {
                for (int k = brick.y[0]; k <= brick.y[1]; ++k) {
                    lines.get(i)[j][k] = 0;
                }
            }
        }  
    }

    private boolean tryRemove(Map<Integer, int[][]> lines, Brick brick, Map<Integer, Set<Brick>> brickRow) {
        if (!brickRow.containsKey(brick.z[1] + 1) || brickRow.get(brick.z[1]+1).size() == 0) {
            return true;
        }    
        removeBrick(lines, brick);  
        boolean res = checkRow(lines, brick.z[1]+1, brickRow);
        drawBrick(lines, brick, brickRow, false);
        return res;
    }
    private boolean checkRow(Map<Integer, int[][]> lines, int row, Map<Integer, Set<Brick>> brickRow) {
        boolean weCan = false;
        if (!brickRow.containsKey(row)) return true;
        for (Brick brick : brickRow.get(row)) {
            weCan = false;
            weCan = checkBrick(lines, row, brick);
            if (!weCan) break;
        }
        return weCan;
    }

    private boolean checkBrick(Map<Integer, int[][]> lines, int row, Brick brick) {
        boolean weCan = false;
        if (!lines.containsKey(row-1) || row == 1) return true;
            for (int j = brick.x[0]; j <= brick.x[1]; ++j) {
                for (int k = brick.y[0]; k <= brick.y[1]; ++k) {
                    if (lines.get(row-1)[j][k] != 0) weCan = true;
                }
            }
        return weCan;
    }

    private int desintagrate(Map<Integer, int[][]> lines, Brick brick, Map<Integer, Set<Brick>> brickRow) {
        removeBrick(lines, brick);  
        Set<Brick> removed = new HashSet<>();
        removed.add(brick);
        for (int i = brick.z[1] + 1; i < lines.size(); ++i) {
            for (Brick brick1 : brickRow.get(i)) {
                if (!checkBrick(lines, i, brick1) && !removed.contains(brick1)) {
                    removed.add(brick1);
                    removeBrick(lines, brick1);
                }
            }
        }
        for (Brick removedBrick : removed) {
            drawBrick(lines, removedBrick, brickRow, false);
        }
        return removed.size() - 1;
    }

    public class Brick implements Comparable<Brick>{
        int index;
        int direction;
        int[] x;
        int[] y;
        int[] z;

        Brick (List<Integer> nums, int index) {
            this.index = index;
            x = new int[2];
            x[0] = Math.min(nums.get(0), nums.get(3));
            x[1] = Math.max(nums.get(0), nums.get(3));
            y = new int[2];
            y[0] = Math.min(nums.get(1), nums.get(4));
            y[1] = Math.max(nums.get(1), nums.get(4));            
            z = new int[2];
            z[0] = Math.min(nums.get(2), nums.get(5));
            z[1] = Math.max(nums.get(2), nums.get(5));    

            if (x[0] != x[1]) direction = 0;
            else if (y[0] != y[1]) direction = 1;
            else if (z[0] != z[1]) direction = 2;
            else direction = -1;
        }

        public void moveDown(){
            --z[0];
            --z[1];
        }

        @Override
        public int compareTo(Brick s) {
            return this.z[0] - s.z[0];
        }
    }
}

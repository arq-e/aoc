package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Day13 extends Day {

    public static void main(String[] args) throws IOException {
        Day13 day = new Day13();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        List<String> note = new ArrayList<>();
        for (String s : list) {
            if (s.length() != 0) {
                note.add(s);
            } else {
                sum += calculateReflectionScore(convertTo2DCharArray(note), 0);
                note.clear();
            }
        }
        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        List<String> note = new ArrayList<>();
        for (String s : list) {
            if (s.length() != 0) {
                note.add(s);
            } else {
                sum += calculateReflectionScore(convertTo2DCharArray(note), 1);
                note.clear();
            }
        }
        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int calculateReflectionScore(char[][] note, int maxDiff) {
        int score = 0;
        boolean reflectionFound = false;
        for (int i = 0; i < note.length-1; ++i) {
            if (reflectionFound = checkSymmetry(note, i, i+1, 0, maxDiff, true)) {
                score = (i + 1) * 100;
                break;
            }
        }

        if (!reflectionFound) {
            for (int j = 0; j < note[0].length-1; ++j) {
                if (reflectionFound = checkSymmetry(note, j, j+1, 0, maxDiff, false)) {
                    score = j + 1;
                    break;
                }
            }
        }
        return score;
    }

    private boolean checkSymmetry(char[][] note, int low, int high, int diff,
                                          int maxDiff, boolean checkRows) {     
        if (low < 0) return diff == maxDiff;
        if(checkRows) {
            if (high >= note.length) return diff == maxDiff;
            for (int j = 0; j < note[0].length; ++j) {
                if (note[low][j] != note[high][j]) {
                        ++diff;
                }
            }
        } else {
            if (high >= note[0].length) return diff == maxDiff;
            for (int j = 0; j < note.length; ++j) {
                if (note[j][low] != note[j][high]) {
                    ++diff;
                }
            }                
        }
        return checkSymmetry(note, low-1, high+1, diff, maxDiff,checkRows)
                       && diff <= maxDiff;
    }
}
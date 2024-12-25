package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Day25 extends Day {

    public static void main(String[] args) throws IOException {
        Day25 day = new Day25();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        List<String> part = new ArrayList<>();
        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        for (String s : list) {
            if (s.length() > 0) {
                part.add(s);
            } else {
                if (part.get(0).charAt(0) == '#') {
                    locks.add(convert(part, '.'));
                } else {
                    keys.add(convert(part, '#'));
                }
                part.clear();
            }
        }
        if (part.size() > 0) {
            if (part.get(0).charAt(0) == '#') {
                locks.add(convert(part, '.'));
            } else {
                keys.add(convert(part, '#'));
            }
        }


        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                boolean matchFound = true;
                for (int i = 0; i < lock.size(); ++i) {
                    if (lock.get(i) + key.get(i) > 5) {
                        matchFound = false;
                        break;
                    }
                }
                if (matchFound) {
                    ++sum;
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        System.out.println("AoC 2024 completed!");
    }
    
    private List<Integer> convert(List<String> lock, char sym) {
        List<Integer> res = new ArrayList<>();
        char[][] map = convertTo2DCharArray(lock);
        for (int j = 0; j < map[0].length; ++j) {
            for (int i = 0; i < map.length; ++i) {
                if (map[i][j] == sym) {
                    if (sym == '.') {
                        res.add(i - 1);
                    } else {
                        res.add(map.length - i - 1);
                    }
                    break;
                }
            }
        }

        return res;
    }
}

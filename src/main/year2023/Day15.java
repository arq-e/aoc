package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Day15 extends Day {

    public static void main(String[] args) throws IOException {
        Day15 day = new Day15();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            String[] arr = s.split(",");
            for (int i = 0; i < arr.length; ++i) {
                int hash = 0;
                for (int j = 0; j < arr[i].length(); ++j) {
                    hash += arr[i].charAt(j);
                    hash *= 17;
                    hash %= 256;
                }
                sum += hash;
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        Map<Integer, List<String[]>> map = new HashMap<>();
        for (int i = 0; i < 256; ++i) {
            map.put(i, new ArrayList<>());
        }
        for (String s : list) {
            String[] arr = s.split(",");
            for (int i = 0; i < arr.length; ++i) {
                int hash = 0;
                for (int j = 0; j < arr[i].length(); ++j) {
                    if (arr[i].charAt(j) == '-' || arr[i].charAt(j) == '=') break;
                    hash += arr[i].charAt(j);
                    hash *= 17;
                    hash %= 256;
                }
                if (arr[i].endsWith("-")) {
                    removeLens(map.get(hash), arr[i].substring(0, arr[i].indexOf("-")));
                } else {
                    replaceLens(map.get(hash), arr[i].split("="));
                }
            }
        }
        for (int i = 0;  i < 256; ++i) {
            int pos = 1;
            for (String[] s1 : map.get(i)) {
                sum += (i + 1) * pos * (s1[1].charAt(0)-'0');
                ++pos;
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private void replaceLens(List<String[]> box, String[] lens) {
        boolean labelFound = false;
        for (String[] s: box) {
            if (s[0].equals(lens[0])) {
                s[1] = lens[1];
                labelFound = true;
                break;
            }
        }
        if (!labelFound) {
            box.add(lens);
        }
    }

    private void removeLens(List<String[]> set, String label) {
        String[] toRemove = null;
        for (String[] s : set) {
            if (s[0].equals(label)) {
                toRemove = s;
                break;
            }
        }
        set.remove(toRemove);
    }
}

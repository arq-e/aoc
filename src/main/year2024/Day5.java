package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day5 extends Day {

    public static void main(String[] args) throws IOException {
        Day5 day = new Day5();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        int idx = parseRules(list, rules);

        for (int i = idx; i < list.size(); ++i) {
            boolean isValid = true;
            int[] report = Stream.of(list.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
            isValid = validateReport(report, rules);

            if (isValid) {
                int p = report.length / 2;
                sum += report[p];
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        int idx = parseRules(list, rules);

        for (int i = idx; i < list.size(); ++i) {
            boolean isValid = true;
            int[] report = Stream.of(list.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
            isValid = validateReport(report, rules);

            if (!isValid) {
                fixReport(report, rules);
                int p = report.length / 2;
                sum += report[p];
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int parseRules(List<String> list, Map<Integer, Set<Integer>> rules) {
        int idx = 0;
        for (String s : list) {
            if (s.length() == 0) {
                break;
            }
            String[] sp = s.split("\\|");
            int f = Integer.parseInt(sp[0]);
            int l = Integer.parseInt(sp[1]);
            if (!rules.containsKey(f)) {
                rules.put(f, new HashSet<>());
            }
            rules.get(f).add(l);
            ++idx;
        }

        return idx + 1;
    }

    private boolean validateReport(int[] report, Map<Integer, Set<Integer>> rules) {
        for (int j = 0; j < report.length; ++j) {
            for (int k = j + 1; k < report.length; ++k) {
                if (!rules.containsKey(report[j]) 
                        || !rules.get(report[j]).contains(report[k])) {
                    return false;
                }
            }
        }

        return true;
    }

    private void fixReport(int[] report, Map<Integer, Set<Integer>> rules) {
        for (int i = 0; i < report.length; ++i) {
            for (int j = i + 1; j < report.length; ++j) {
                if (!rules.containsKey(report[i]) 
                        || !rules.get(report[i]).contains(report[j])) {
                    swap(report, i, j);
                    i = 0;
                    j = 0;
                }
            }
        }
    }

    private void swap(int[] report, int x, int y) {
        int temp = report[x];
        report[x] = report[y];
        report[y] = temp;
    }
}

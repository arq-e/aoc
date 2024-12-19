package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day19 extends Day {

    public static void main(String[] args) throws IOException {
        Day19 day = new Day19();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = 0;
        String[] patterns = list.get(0).split(", ");
        for (int i = 2; i < list.size(); ++i) {
            sum += tryDesign(list.get(i), patterns, false);
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        String[] patterns = list.get(0).split(", ");
        for (int i = 2; i < list.size(); ++i) {
            sum += tryDesign(list.get(i), patterns, true);
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long tryDesign(String design, String[] patterns, boolean countDesigns) {
        long[] dp = new long[design.length() + 1];
        dp[0] = 1;
        for (int i = 0; i < dp.length; ++i) {
            if (dp[i] > 0) {
                for (String t : patterns) {
                    if (design.substring(i).startsWith(t)) {
                        if (countDesigns) {
                            dp[i + t.length()] += dp[i];
                        } else {
                            dp[i + t.length()] = 1;
                        }
                    }
                }
            }
        } 

        return dp[design.length()];
    }
}

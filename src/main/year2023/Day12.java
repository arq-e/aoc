package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

public class Day12 extends Day {

    public static void main(String[] args) throws IOException {
        Day12 day = new Day12();

        Instant start = Instant.now();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
        Instant end = Instant.now();
        System.out.println(end.toEpochMilli() - start.toEpochMilli());
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            String[] split = s.split("\s+");
            int[] groups = Stream.of(split[1].split(",")).mapToInt(Integer::parseInt).toArray();
            String row = split[0];
            sum += countArrangements(row + ".", groups);
        }
        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        for (String s : list) {
            String[] split = s.split("\s+");
            int[] groups = Stream.of(split[1].split(",")).mapToInt(Integer::parseInt).toArray();
            String row = split[0];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; ++i) {
                sb.append(split[0]).append("?");
            }
            sb.append(row).append(".");
            int[] unfoldedGroups = new int[groups.length*5];
            for (int i = 0; i < unfoldedGroups.length; ++i) {
                unfoldedGroups[i] = groups[i % groups.length];
            }
            sum += countArrangements(sb.toString(), unfoldedGroups);
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long countArrangements(String s, int[] groups) {
        int max = 0;
        for (int i = 0; i < groups.length; ++i) {
            max = Math.max(groups[i], max);
        }
        long[][][] dp = new long[s.length()+1][groups.length+1][max+2];
        dp[0][0][0] = 1;
        for (int i = 0; i < s.length(); ++i) {
            for (int j = 0; j <= groups.length; ++j) {
                for (int k = 0; k <= max; ++k){
                    if (dp[i][j][k] != 0) {
                        if (s.charAt(i) == '.' || s.charAt(i) == '?') {
                            if (k == 0) {
                                dp[i+1][j][0] += dp[i][j][k];
                            } else if (j < groups.length && k == groups[j]) {
                                dp[i+1][j+1][0] += dp[i][j][k];
                            }
                        }
                        if (s.charAt(i) == '#' || s.charAt(i) == '?') {
                            dp[i+1][j][k+1] += dp[i][j][k];
                        }
                    }

                }
            }
        }
        return dp[s.length()][groups.length][0];
    }
}
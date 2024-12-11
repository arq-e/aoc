package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;
import main.utils.MathUtils;
import main.utils.ParsingUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class Day11 extends Day {
    Map<Long, Long> memo = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Day11 day = new Day11();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = 0;
        memo = new HashMap<>();
        for (String s : list) {
            int[] stones = Stream.of(s.split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int st : stones) {
                sum += rec((long)st, 0, 25);
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        memo = new HashMap<>();
        for (String s : list) {
            int[] stones = Stream.of(s.split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int st : stones) {
                sum += rec((long)st, 0, 75);
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long rec(long stone, int turn, int turnLimit) {
        if (turn == turnLimit) {
            return 1;
        }
        if (memo.containsKey((long)(stone * 100 + turn))) {
            return memo.get((long)(stone * 100 + turn));
        }
        
        long d = 1l;
        int k = 0;
        while (stone / d > 0) {
            d *= 10;
            ++k;
        }
        long res = 0;
        if (stone == 0) {
            res = rec(stone + 1, turn + 1, turnLimit);
        } else if (k % 2 == 0) {
            d = 1;
            for (int i = 0; i < k / 2; ++i) {
                d *= 10;
            }
            res = rec(stone / d, turn + 1, turnLimit) + rec(stone % d, turn + 1, turnLimit);
        } else {
            res = rec(stone * 2024, turn + 1, turnLimit);
        }

        memo.put((long)(stone * 100 + turn), res);
        return res;
    }


}

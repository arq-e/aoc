package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 extends Day {

    public static void main(String[] args) throws IOException {
        Day1 day = new Day1();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        int sum = 0;
        for (String s : list) {
            String[] split = s.split("\s+");
            first.add(Integer.parseInt(split[0]));
            second.add(Integer.parseInt(split[1]));
        }

        Collections.sort(first);
        Collections.sort(second);

        for (int i = 0; i < first.size(); ++i ) {
            sum += Math.abs(first.get(i) - second.get(i));
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        long sum = 0;
        for (String s : list) {
            
            String[] split = s.split("\s+");
            first.add(Integer.parseInt(split[0]));
            second.add(Integer.parseInt(split[1]));
        }

        for (int i = 0; i < first.size(); ++i) {
            for (int j = 0; j < second.size(); ++j) {
                if (first.get(i).intValue() == second.get(j).intValue()) {
                    sum += first.get(i);
                }
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }
}

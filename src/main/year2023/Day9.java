package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day9 extends Day {

    public static void main(String[] args) throws IOException {
        Day9 day = new Day9();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            List<List<Integer>> sequences = calcSequences(s);
            int nextVal = 0;
            for (int i = sequences.size()-2; i >= 0; --i) {
                nextVal += sequences.get(i).get(sequences.get(i).size()-1);
            }
            sum += nextVal;
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        for (String s : list) {
            List<List<Integer>> sequences = calcSequences(s);
            int prev = 0;
            for (int i = sequences.size()-2; i >= 0; --i) {
                prev = sequences.get(i).get(0) - prev;
            }
            sum += prev;
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private List<List<Integer>> calcSequences(String s) {
        List<List<Integer>> sequences = new ArrayList<>();
        sequences.add(new ArrayList<>());        
        int[] nums = Stream.of(s.split("\s+")).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < nums.length; ++i) {
            sequences.get(0).add(nums[i]);
        }
        int row = 0;
        boolean allZerosSequence = false;
        while(!allZerosSequence) {
            allZerosSequence = true;
            sequences.add(new ArrayList<>());
            for (int i = 1; i < sequences.get(row).size(); ++i) {
                sequences.get(row+1).add(sequences.get(row).get(i) - sequences.get(row).get(i-1));
                if (allZerosSequence) allZerosSequence = (sequences.get(row+1).get(i-1) == 0);
            }
            ++row;
        }
        return sequences;
    }
}

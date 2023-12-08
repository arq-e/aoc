package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day8 extends Day {
    private Map<String, String> left;
    private Map<String, String> right;
    private char[] instructions;
    private boolean parsingComplete = false;

    public static void main(String[] args) throws IOException {
        Day8 day = new Day8();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        parseInput(list);

        String currNode = "AAA";
        int turn = 0;
        while (!currNode.equals("ZZZ")) {
            if (instructions[turn % instructions.length] == 'L') {
                currNode = left.get(currNode);
            } else {
                currNode = right.get(currNode); 
            }
            ++turn;
        }

        System.out.printf("Part 1 answer is: %d\n", turn);
    }

    public void solve2(List<String> list) {
        parseInput(list);

        Set<String> endNodes = new HashSet<>(left.keySet());
        endNodes.removeIf(s -> !s.endsWith("Z"));
        List<Integer> scores = new ArrayList<>();
        for (String s : left.keySet()) {
            if (s.endsWith("A")) {
                int turn = 0;
                String currNode = s;
                while (!endNodes.contains(currNode)) {
                    if (instructions[turn % instructions.length] == 'L') {
                        currNode = left.get(currNode);
                    } else {
                        currNode = right.get(currNode);
                    }
                    ++turn;
                }
                scores.add(turn);
            }
        }

        long score = scores.get(0);
        for (int i = 1; i < scores.size(); ++i) {
            score = lcm(score, scores.get(i));
        }

        System.out.printf("Part 2 answer is: %d\n", score);
    }

    private void parseInput(List<String> input) {
        if (!parsingComplete) {
            instructions = input.get(0).toCharArray();
            left = new HashMap<>();
            right = new HashMap<>();
            for (int i = 2; i < input.size(); ++i) {
                if (input.get(i) != null && input.get(i).length() > 0) {
                    left.put(input.get(i).substring(0,3), input.get(i).substring(7,10));
                    right.put(input.get(i).substring(0,3), input.get(i).substring(12,15));
                }
            }
            parsingComplete = true;
        }
    }

    private long gcd(long a, long b){
        return b == 0 ? a : gcd(b, a % b);		
    }

    private long lcm(long a, long b){
        return a / gcd(a, b) * b;
    }
}
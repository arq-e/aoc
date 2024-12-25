package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day22 extends Day {
    int mod = 16777216;
    int genLimit = 2000;

    public static void main(String[] args) throws IOException {
        Day22 day = new Day22();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = 0;
        for (String s : list) {
            long num = Long.parseLong(s);
            for (int i = 0; i < genLimit; ++i) {
                num = calcSecret(num);
            }
            sum += num;
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

        int buyerId = 0;
        for (String s : list) {
            long num = Long.parseLong(s);
            int[] changesSeq = new int[4];
            long prev = num % 10;
            for (int i = 0; i < genLimit; ++i) {
                num = calcSecret(num);
                long next = num % 10;
                
                int change = (int)(next - prev);
                for (int j = 3; j > 0; --j) {
                    changesSeq[j] = changesSeq[j-1];
                }
                changesSeq[0] = change;
                
                if (i >= 3) {
                    int val = 0;
                    for (int j = 3; j >= 0; --j) {
                        val *= 21;
                        val += (10 + changesSeq[j]);
                    }

                    if (!map.containsKey(val)) {
                        map.put(val, new HashMap<>());
                    }
                    if (!map.get(val).containsKey(buyerId)) {
                        map.get(val).put(buyerId, (int)next);
                    }
                }
                prev = next;
            }
            ++buyerId;
        }

        long bestSum = 0;
        for (int seq : map.keySet()) {
            int currSum = 0;
            for (int price : map.get(seq).keySet()) {
                currSum += map.get(seq).get(price);
            }
            bestSum = Math.max(bestSum, currSum);
        }

        System.out.printf("Part 2 answer is: %d\n", bestSum);
    }

    private long calcSecret(long num) {
        num ^= (num * 64);
        num %= mod;
        num ^= (num / 32);
        num %= mod;
        num ^= (num * 2048);
        num %= mod;

        return num;
    }
}

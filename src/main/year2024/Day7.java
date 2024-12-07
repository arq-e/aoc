package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Day7 extends Day {

    public static void main(String[] args) throws IOException {
        Day7 day = new Day7();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = 0;
        for (String s : list) {
            long[] split = Stream.of(s.split("\s|:\s")).mapToLong(Long::parseLong).toArray();
            long target = split[0];
            int[] sym  = new int[split.length - 2];
            int cur = 0;
            int max = 1;
            for (int i = 0; i < sym.length; ++i) {
                max *= 2;
            }

            boolean isGood = false;
            while (cur <= max) {
                long res = split[1];
                for (int i = 0; i < sym.length; ++i) {
                    if (sym[i] == 0) {
                        res += split[i + 2];
                    } else {
                        res *= split[i + 2];                        
                    }
                }
                if (res == target) {
                    isGood = true;
                    break;
                }

                ++cur;
                int val = cur;
                int p = sym.length - 1;
                sym  = new int[split.length - 2];
                while(val > 0 && p >= 0) {
                    sym[p--] = val % 2;
                    val /= 2;
                }

            }
            if (isGood) {
                sum += target;
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        for (String s : list) {
            long[] nums = Stream.of(s.split("\s|:\s")).mapToLong(Long::parseLong).toArray();
            long target = nums[0];
            int[] sym  = new int[nums.length - 2];
            int cur = 0;
            int max = 1;
            for (int i = 0; i < sym.length; ++i) {
                max *= 3;
            }

            boolean isGood = false;
            while (cur <= max) {
                long res = nums[1];
                for (int i = 0; i < sym.length; ++i) {
                    if (sym[i] == 0) {
                        res += nums[i + 2];
                    } else if (sym[i] == 1){
                        res *= nums[i + 2];                        
                    } else {
                        long d = 10;
                        while (nums[i + 2] / d > 0) {
                            d *= 10;
                        }
                        res = res * d + nums[i + 2];
                    }
                }
                if (res == target) {
                    isGood = true;
                    break;
                }

                ++cur;
                int val = cur;
                int p = sym.length - 1;
                sym  = new int[nums.length - 2];
                while(val > 0 && p >= 0) {
                    sym[p--] = val % 3;
                    val /= 3;
                }

            }
            if (isGood) {
                sum += target;
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }
}

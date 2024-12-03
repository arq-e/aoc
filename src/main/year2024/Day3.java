package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day3 extends Day {

    public static void main(String[] args) throws IOException {
        Day3 day = new Day3();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            for (int i = 0; i < s.length(); ++i) {
                if (s.substring(i).startsWith("mul(")) {
                    String mulParams = s.substring(i + 4, s.indexOf(")", i));
                    String[] nums = mulParams.split(",");
                    if (nums.length == 2) {
                        int first = 0;
                        int second = 0;
                        try {
                            first = Integer.parseInt(nums[0]);
                            second = Integer.parseInt(nums[1]);
                        } catch (NumberFormatException e) {}
                        sum += second * first;
                    }
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        boolean mulEnabled = true;
        for (String s : list) {
            for (int i = 0; i < s.length(); ++i) {
                if (s.substring(i).startsWith("do()")) {
                    mulEnabled = true;
                } else if (s.substring(i).startsWith("don't()")) {
                    mulEnabled = false;
                }
                if (mulEnabled) {
                    if (s.substring(i).startsWith("mul(")) {
                        String mulParams = s.substring(i + 4, s.indexOf(")", i));
                        String[] nums = mulParams.split(",");
                        if (nums.length == 2) {
                            int first = 0;
                            int second = 0;
                            try {
                                first = Integer.parseInt(nums[0]);
                                second = Integer.parseInt(nums[1]);
                            } catch (NumberFormatException e) {}
                            sum += second * first;
                        }
                    }
                }

            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }
}

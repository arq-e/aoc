package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.List;

public class day4 {
    public static void main(String[] args) throws IOException{
        List<String> input = AdventInputReader.getInput(2022,4);

        part1(input);
        part2(input);
    }

    public static void part1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            String[] pairs = s.split("[,-]");
            int[] nums = new int[4];
            for (int i = 0; i < 4; i++) {
                nums[i] = Integer.parseInt(pairs[i]);
            }

            if ((nums[3] >= nums[1] && nums[2] <= nums[0]) || (nums[1] >= nums[3] && nums[0] <= nums[2])) {
                sum++;
            }
        }
        System.out.println(sum);
    }

    public static void part2(List<String> list) {
        int sum = 0;
        for (String s : list) {
            String[] pairs = s.split("[,-]");

            int[] nums = new int[4];
            for (int i = 0; i < 4; i++) {
                nums[i] = Integer.parseInt(pairs[i]);
            }

            if (nums[3] >= nums[0] && nums[1] >= nums[2]) sum++;
        }
        System.out.println(sum);
    }
}

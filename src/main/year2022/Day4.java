package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day4 extends Day {

    public static void main(String[] args) throws IOException{
        Day4 day = new Day4();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            String[] pairs = s.split("[,-]");
            int[] nums = new int[4];
            for (int i = 0; i < 4; i++) {
                nums[i] = Integer.parseInt(pairs[i]);
            }

            if ((nums[3] >= nums[1] && nums[2] <= nums[0])
                    || (nums[1] >= nums[3] && nums[0] <= nums[2])) {
                sum++;
            }
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
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

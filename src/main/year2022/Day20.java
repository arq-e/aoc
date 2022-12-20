package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day20 extends Day {

    public static void main(String[] args) throws IOException {
        Day20 day = new Day20();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<Element> nums = convertInput(list, false, 0);
        mix(nums);
        System.out.println(calculateSum(nums));
    }

    public void solve2(List<String> list) {
        long decryptionKey = 811589153;
        List<Element> nums = convertInput(list, true, decryptionKey);
        for (int i = 0; i < 10; i++) {
            mix(nums);
        }
        System.out.println(calculateSum(nums));
    }

    private long calculateSum(List<Element> nums) {
        long sum = 0;
        int pos = 0;
        for (int i = 0 ; i < nums.size(); i++) {
            if (nums.get(i).getVal() == 0) {
                pos = i;
                break;
            }
        }
        for (int i = 0; i < 3; i++) {
            pos += 1000;
            sum += (nums.get((pos)%nums.size()).getVal());
        }
        return sum;
    }

    private void mix(List<Element> nums) {
        int count = 0;
        int pos = 0;
        while (count < nums.size()) {
            for (int j = 0; j < nums.size();j++) {
                if (nums.get(j).getPos() == count) {
                    pos = j;
                }
            }
            Element num = nums.get(pos);
            count++;
            if (num.getVal() != 0) {
                int diff =(int) (num.getVal()%(nums.size()-1)+pos) % (nums.size()-1);
                if (diff <= 0) diff = nums.size()-1+diff;
                nums.remove(pos);
                nums.add(diff, num);
            }
        }
    }

    private List<Element> convertInput(List<String> list, boolean applyDecryption, long decryptionKey) {
        List<Element> list1 = new ArrayList<>();
         int i = 0;
         for (String s : list) {
             long num = Long.parseLong(s);
             if (applyDecryption) num *=decryptionKey;
             list1.add(new Element(num, i));
             i++;
         }
         return list1;
    }

    public static class Element {
        private final long val;
        private final int pos;

        Element (long val, int pos) {
            this.val = val;
            this.pos = pos;
        }

        public long getVal() {
            return val;
        }

        public int getPos() {
            return pos;
        }
    }
}

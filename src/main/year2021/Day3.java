package main.year2021;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day3 extends Day {

    public static void main(String[] args) throws IOException {
        Day3 day = new Day3();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int length = list.get(0).length()-1;
        int[] count1 = new int[list.get(0).length()];
        for (String s : list) {
            for (int i = 0; i < s.length();i++) {
                if (s.charAt(i) == '1') ++count1[i];
            }
        }
        int firstNum = 0;
        int secondNum = 0;
        int mult = 1;
        for (int i = length; i  >= 0;i--) {
            if (count1[i] < list.size()/2) secondNum += mult;
            else firstNum += mult;
            mult *= 2;
        }
        System.out.println(firstNum*secondNum);
    }



    public void solve2(List<String> list) {
        List<String> list1 = new ArrayList<>(list);
        System.out.println(findNumber(list,true)*findNumber(list1,false));
    }

    private int findNumber(List<String> list, boolean acceptMostCommon) {
        int i = 0;
        int count1 = 0;
        while (list.size() > 1) {
            for (String s : list) {
                if (s.charAt(i) == '1') ++count1;
            }
            char acceptedValue = '0';
            if (acceptMostCommon && (count1 > list.size() / 2 ||
                    (count1 == list.size() / 2 && list.size() % 2 == 0))) acceptedValue = '1';
            else if (!acceptMostCommon && (count1 < list.size() / 2 ||
                    (count1 == list.size() / 2 && list.size() % 2 == 1))) acceptedValue = '1';
            int size = list.size();
            int j = 0;
            while (j < size) {
                if (list.get(j).charAt(i) != acceptedValue) {
                    list.remove(j);
                    size--;
                } else j++;
            }
            i++;
            count1 = 0;
        }
        return Integer.parseInt(list.get(0),2);
    }
}

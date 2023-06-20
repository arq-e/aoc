package main.year2021;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day1 extends Day {

    public static void main(String[] args) throws IOException {
        Day1 day = new Day1();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (int i = 1; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i)) > Integer.parseInt(list.get(i-1))) sum++;
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;

        for (int i = 3; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i)) > Integer.parseInt(list.get(i-3))) sum++;
        }

        System.out.println(sum);
    }
}

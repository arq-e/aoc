package main.template;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day0 extends Day {

    public static void main(String[] args) throws IOException {
        Day0 day = new Day0();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve(input);
    }

    public void solve(List<String> list) {
        int sum = 0;
        for (String s : list) {

        }

        System.out.println(sum);
        solve2(list);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        for (String s : list) {

        }

        System.out.println(sum);
    }
}

package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day2 extends Day {

    public static void main(String[] args) throws IOException{
        Day2 day = new Day2();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve(input);
    }

    public void solve(List<String> list) {
        int sum = 0;
        for (String s : list) {
            if (s.charAt(2) == 'X') {
                sum += 1;
                if (s.charAt(0) == 'C') sum += 6;
                else if (s.charAt(0)  == 'A') sum += 3;
            } else if (s.charAt(2) == 'Y') {
                sum += 2;
                if (s.charAt(0)  == 'A') sum += 6;
                else if (s.charAt(0)  == 'B') sum += 3;
            } else {
                sum += 3;
                if (s.charAt(0)  == 'B') sum += 6;
                else if (s.charAt(0)  == 'C') sum += 3;
            }
        }
        System.out.println(sum);

        solve2(list);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        for (String s : list) {
            if (s.charAt(2) == 'X') {
                if (s.charAt(0) == 'C') sum += 2;
                else if (s.charAt(0) == 'A') sum += 3;
                else if (s.charAt(0) == 'B') sum += 1;
            } else if (s.charAt(2) == 'Y') {
                sum += 3;
                if (s.charAt(0) == 'A') sum += 1;
                else if (s.charAt(0) == 'B') sum += 2;
                else if (s.charAt(0) == 'C') sum += 3;
            } else {
                sum += 6;
                if (s.charAt(0) == 'A') sum += 2;
                else if (s.charAt(0) == 'B') sum += 3;
                else if (s.charAt(0) == 'C') sum += 1;
            }
        }

        System.out.println(sum);
    }
}

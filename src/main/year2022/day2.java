package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.List;

public class day2 {
    public static void main(String[] args) throws IOException{
        List<String> input = AdventInputReader.getInput(2022,2);

        part1(input);
        part2(input);
    }

    public static void part1(List<String> list) {
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
    }

    public static void part2(List<String> list) {
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

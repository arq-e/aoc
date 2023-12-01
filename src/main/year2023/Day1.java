package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day1 extends Day {
    private final String[] literal = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};       
    public static void main(String[] args) throws IOException {
        Day1 day = new Day1();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (String s : list) {
            sum += calculateCalibrationValue(s, false);     
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        for (String s : list) {
            sum += calculateCalibrationValue(s, true);     
        }
        System.out.println(sum);
    }

    private int calculateCalibrationValue(String s, boolean checkLiteral) {
        int first = -1;
        int last = -1;
        for (int i = 0;  i < s.length(); ++i) {
            if (s.charAt(i) <= 58 && s.charAt(i) > 48) {
                if (first < 0) {
                    first = s.charAt(i) - 48;
                }
                last = (s.charAt(i) - 48);
            } else if (checkLiteral) {
                int val = digitFromLiteral(s, i);
                if (val != -1) {
                    if (first < 0){
                        first = val;
                    }
                    last = val;
                }
            }
        }        
        return first * 10 + last;    
    }

    private int digitFromLiteral(String s, int pos) {
        for (int j = 0; j < literal.length; ++j) {
            if (s.substring(pos).startsWith(literal[j])) {
                return j + 1;
            }
        }       
        return -1; 
    }
}

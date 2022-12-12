package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 extends Day {

    public static void main(String[] args) throws IOException{
        Day3 day = new Day3();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();
        int sum = 0;
        for (String s : list) {
            for (int i = 0; i < s.length()/2;i++) {
                set1.add(s.charAt(i));
                set2.add(s.charAt(i+s.length()/2));
            }
            for (Character ch : set1) {
                if (set2.contains(ch)) {
                    if (ch >= 'a') sum += ch - 'a' + 1;
                    else sum += ch - 'A' + 27;
                }
            }
            set1.clear();
            set2.clear();
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();
        Set<Character> set3 = new HashSet<>();

        int sum = 0;
        for (int i = 0; i < list.size();i+=3) {
            for (int k = 0; k < list.get(i).length();k++) {
                set1.add(list.get(i).charAt(k));
            }
            for (int k = 0; k < list.get(i+1).length();k++) {
                set2.add(list.get(i+1).charAt(k));
            }
            for (int k = 0; k < list.get(i+2).length();k++) {
                set3.add(list.get(i+2).charAt(k));
            }
            for (Character ch : set1) {
                if (set2.contains(ch) && set3.contains(ch)) {
                    if (ch >= 'a') sum += ch - 'a' + 1;
                    else sum += ch - 'A' + 27;
                }
            }
            set1.clear();
            set2.clear();
            set3.clear();
        }
        System.out.println(sum);
    }
}

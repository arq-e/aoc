package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day6 extends Day {

    public static void main(String[] args) throws IOException{
        Day6 day = new Day6();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve(input);
    }

    public void solve(List<String> list) {
        int sum = 0;
        String str = list.get(0);
        Deque<Character> deque = new ArrayDeque<>(4);
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < str.length();i++) {
            deque.addLast(str.charAt(i));
            if ( deque.size() > 4) {
                deque.removeFirst();
            }
            if (deque.size() == 4) {

                set.addAll(deque);
                if (set.size() == 4) {
                    sum = i+1;
                    break;

                }
                set.clear();
            }
        }

        System.out.println(sum);

        solve2(list);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        String str = list.get(0);
        Deque<Character> deque = new ArrayDeque<>(14);
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < str.length();i++) {
            deque.addLast(str.charAt(i));
            if (deque.size() > 14) {
                deque.removeFirst();
            }
            if (deque.size() == 14) {

                set.addAll(deque);
                if (set.size() == 14) {
                    sum = i+1;
                    break;
                }
                set.clear();
            }
        }

        System.out.println(sum);
    }
}

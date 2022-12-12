package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day6 extends Day {

    public static void main(String[] args) throws IOException{
        Day6 day = new Day6();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        System.out.println(findStartOfMessage(list.get(0), 4));
    }

    public void solve2(List<String> list) {
        System.out.println(findStartOfMessage(list.get(0), 14));
    }

    private int findStartOfMessage(String s, int length) {
        Deque<Character> deque = new ArrayDeque<>(length);
        int index = 0;
        for (int i = 0; i < s.length();i++) {
            deque.addLast(s.charAt(i));
            if (deque.size() > length) {
                deque.removeFirst();
            }
            if (deque.size() == length) {
                if (deque.stream().distinct().count() == (long)length) {
                    index  = i+1;
                    break;
                }
            }
        }

        return index;
    }


}

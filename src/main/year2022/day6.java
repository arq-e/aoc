package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.*;

public class day6 {

    public static void main(String[] args) throws IOException {

        List<String> input = AdventInputReader.getInput(2022, 6);
        part1(input);
        part2(input);
    }

    public static void part1(List<String> list) {
        int sum = 0;

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        String str = sb.toString();
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
    }

    public static void part2(List<String> list) {
        int sum = 0;


        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        String str = sb.toString();
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

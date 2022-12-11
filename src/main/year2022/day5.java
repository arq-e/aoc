package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.*;

public class day5 {
    public static void main(String[] args) throws IOException{
        List<String> input = AdventInputReader.getInput(2022, 5);
        solve(input);

    }

    public static void solve(List<String> list) {
        int i;
        Map<Integer, Deque<Character>> map = new HashMap<>();
        for (i = 0; i < list.size();i++) {
            if (list.get(i).charAt(1) == '1') {
                break;
            }
            int num = 1;
            for (int j = 1; j < list.get(i).length(); j += 4) {
                if (list.get(i).charAt(j) != ' ') {
                    if (!map.containsKey(num)) {
                        map.put(num, new ArrayDeque<>());
                    }
                    map.get(num).addLast(list.get(i).charAt(j));
                }
                num += 1;
            }
        }

        for (i+=2; i < list.size(); i++) {
                String str = list.get(i);
                str = str.replaceAll("[^0-9]"," ");
                String[] values = str.trim().split(" +");
                int[] intValues = new int[3];
                for (int j = 0; j < 3; j++) {
                    intValues[j] = Integer.parseInt(values[j]);
                }

                for (int j = 0; j < intValues[0];j++) {
                    char crate = map.get(intValues[1]).pollFirst();
                    map.get(intValues[2]).addFirst(crate);
                }
        }

        StringBuilder sb = new StringBuilder();
        for (Integer j: map.keySet()) {
            sb.append(map.get(j).pop());
        }

        System.out.println(sb.toString());

        solve2(list);
    }

    public static void solve2(List<String> list) {
        int i;
        Map<Integer, Deque<Character>> map = new HashMap<>();
        for (i = 0; i < list.size();i++) {
            if (list.get(i).charAt(1) == '1') {
                break;
            }
            int num = 1;
            for (int j = 1; j < list.get(i).length(); j += 4) {
                if (list.get(i).charAt(j) != ' ') {
                    if (!map.containsKey(num)) {
                        map.put(num, new ArrayDeque<>());
                    }
                    map.get(num).addLast(list.get(i).charAt(j));
                }
                num += 1;
            }
        }

        for (i+=2; i < list.size(); i++) {
            String str = list.get(i);
            str = str.replaceAll("[^0-9]"," ");
            String[] values = str.trim().split(" +");
            int[] intValues = new int[3];
            for (int j = 0; j < 3; j++) {
                intValues[j] = Integer.parseInt(values[j]);
            }

            char[] crates = new char[intValues[0]];
            for (int j = 0; j < intValues[0];j++) {
                crates[j] = map.get(intValues[1]).pollFirst();
            }
            for (int j = intValues[0]-1; j>=0; j--) {
                map.get(intValues[2]).addFirst(crates[j]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Integer j: map.keySet()) {
            sb.append(map.get(j).pop());
        }

        System.out.println(sb.toString());
    }
}

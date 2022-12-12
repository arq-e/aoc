package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day5 extends Day {

    public static void main(String[] args) throws IOException{
        Day5 day = new Day5();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Map<Integer, LinkedList<Character>> map = convertInput(list, false);

        StringBuilder sb = new StringBuilder();
        for (Integer j: map.keySet()) {
            sb.append(map.get(j).pop());
        }

        System.out.println(sb.toString());
    }

    public void solve2(List<String> list) {
        Map<Integer, LinkedList<Character>> map = convertInput(list, true);

        StringBuilder sb = new StringBuilder();
        for (Integer j: map.keySet()) {
            sb.append(map.get(j).pop());
        }

        System.out.println(sb.toString());
    }

    private Map<Integer, LinkedList<Character>> convertInput(List<String> list, boolean multiplePick) {
        Map<Integer, LinkedList<Character>> map = new HashMap<>();
        int i = 0;
        do {
            String s = list.get(i);
            int num = 1;
            for (int j = 1; j < s.length(); j += 4) {
                if (s.charAt(j) != ' ') {
                    if (!map.containsKey(num)) {
                        map.put(num, new LinkedList<>());
                    }
                    map.get(num).addLast(list.get(i).charAt(j));
                }
                num += 1;
            }
            i++;
        } while (list.get(i).contains("["));

        for (i+=2; i < list.size(); i++) {
            String s = list.get(i);
            s = s.replaceAll("[^0-9]"," ");
            String[] values = s.trim().split(" +");
            int[] intValues = new int[3];
            for (int j = 0; j < 3; j++) {
                intValues[j] = Integer.parseInt(values[j]);
            }

            if (multiplePick) {
                char[] crates = new char[intValues[0]];
                for (int j = 0; j < intValues[0];j++) {
                    crates[j] = map.get(intValues[1]).pollFirst();
                }
                for (int j = intValues[0]-1; j>=0; j--) {
                    map.get(intValues[2]).addFirst(crates[j]);
                }
            } else {
                for (int j = 0; j < intValues[0];j++) {
                    char crate = map.get(intValues[1]).pollFirst();
                    map.get(intValues[2]).addFirst(crate);
                }
            }

        }

        return map;
    }
}

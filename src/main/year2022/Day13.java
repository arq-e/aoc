package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day13 extends Day {

    public static void main(String[] args) throws IOException {
        Day13 day = new Day13();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (int i = 0; i < list.size()-1; i+=3) {
            String s1 = list.get(i);
            String s2 = list.get(i+1);

            int res = compareElements(s1, s2);
            if (res >= 0) {
                sum += i/3+1;
            }
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        list.removeIf(s -> s.length() == 0);
        list.add("[[2]]");
        list.add("[[6]]");
        list.sort((o1, o2) -> compareElements(o2, o1));
        System.out.println((list.indexOf("[[2]]")+1)*(list.indexOf("[[6]]")+1));
    }

    private String[] splitElements(String s) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '[') {
                int endPoint = findClosure(s, i);
                list.add(s.substring(i+1, endPoint));
                i = endPoint;
            } else if (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                int endPoint = findSeparator(s, i);
                list.add(s.substring(i,endPoint));
                i = endPoint;
            }
            i++;
        }
        return list.toArray(new String[0]);
    }

    private int compareElements(String s1, String s2) {
        if (isNum(s1) && isNum(s2)) {
            return compareNums(s1, s2);
        } else {
            return compareLists(s1, s2);
        }
    }

    private int compareNums(String s1, String s2) {
        if (s1.length() > 0 && s2.length() > 0) {
            return Integer.parseInt(s2) - Integer.parseInt(s1);
        } else if (s1.length() == 0 && s2.length() == 0){
            return 0;
        } else if (s1.length() == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public int compareLists(String s1, String s2) {
        int res = 0;
        String[] elements1 = splitElements(s1);
        String[] elements2 = splitElements(s2);
        if (elements1.length == 0 && elements2.length > 0) return 1;
        for (int i = 0; i < elements1.length; i++) {
            if (i == elements2.length) return -1;
            else if (elements1[i].length() == 0) {
                if (elements2[i].length() != 0) return 1;
            } else res = compareElements(elements1[i],elements2[i]);
            if (res != 0) return res;
        }
        if (elements1.length < elements2.length) return 1;
        return res;
    }

    private int findClosure(String s, int start) {
        int count = 0;
        for (int i = start; i < s.length();i++) {
            if (s.charAt(i) == '[') count++;
            else if (s.charAt(i) == ']') {
                count--;
                if (count == 0) return i;
            }
        }
        return -1;
    }

    private int findSeparator(String s, int start) {
        for (int i = start; i < s.length();i++) {
            if (s.charAt(i) == ',' || s.charAt(i) == ']') return i;
        }
        return s.length();
    }

    private boolean isNum(String s) {
        return s.replaceAll("[0-9]", "").length() <= 0;
    }
}

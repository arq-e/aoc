package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.*;

public class day11 {

    public static void main(String[] args) throws IOException {
        List<String> input = AdventInputReader.getInput(2022,11);

        solve(input);
    }

    public static void solve(List<String> list) {
        List<List<Integer>> monkeyItems = new ArrayList<>();
        List<Integer> monkeyTest = new ArrayList<>();
        List<int[]> monkeyAction = new ArrayList<>();
        List<int[]> monkeyOperation = new ArrayList<>();
        int command = 0;
        for (String s : list) {
            if (command == 0){
                monkeyItems.add(new ArrayList<>());
            } else if (command == 1) {
                String[] items = s.substring(18).split(", ");
                for (String str : items) {
                    monkeyItems.get(monkeyItems.size()-1).add(Integer.parseInt(str));
                }
            } else if (command == 2) {
                int[] operation = new int[2];
                if (s.contains("+")) {
                    operation[0] = 1;
                } else {
                    operation[0] = 2;
                }
                if (!s.substring(25).equals("old")) {
                    operation[1] = Integer.parseInt(s.substring(25));
                }
                monkeyOperation.add(operation);
            } else if (command == 3) {
                monkeyTest.add(Integer.parseInt(s.substring(21)));
            } else if (command == 4) {
                int[] action = new int[2];
                action[0] = Integer.parseInt(s.substring(29));
                monkeyAction.add(action);
            } else  if (command == 5) {
                monkeyAction.get(monkeyAction.size()-1)[1] = Integer.parseInt(s.substring(30));
            } else if (command == 6) {
                command = -1;
            }
            command++;
        }
        int[] inspected = new int[monkeyItems.size()];
        List<List<Long>> monkeyItemsCopy = new ArrayList<>();
        for (List<Integer> items : monkeyItems) {
            monkeyItemsCopy.add(new ArrayList<>());
            for (Integer j : items) {
                monkeyItemsCopy.get(monkeyItemsCopy.size()-1).add(Long.valueOf(j));
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < monkeyItems.size(); j++) {
                for (Integer item : monkeyItems.get(j)) {
                    inspected[j]++;
                    int newWorryLevel = item;
                    if (monkeyOperation.get(j)[1] == 0) {
                        if (monkeyOperation.get(j)[0] == 1) {
                            newWorryLevel += item;
                        } else {
                            newWorryLevel *= item;
                        }
                    } else {
                        if (monkeyOperation.get(j)[0] == 1) {
                            newWorryLevel += monkeyOperation.get(j)[1];
                        } else {
                            newWorryLevel *= monkeyOperation.get(j)[1];
                        }
                    }
                    newWorryLevel /= 3;
                    if (newWorryLevel % monkeyTest.get(j) == 0) {
                        monkeyItems.get(monkeyAction.get(j)[0]).add(newWorryLevel);
                    } else {
                        monkeyItems.get(monkeyAction.get(j)[1]).add(newWorryLevel);
                    }
                }
                monkeyItems.get(j).clear();
            }
        }

        Arrays.sort(inspected);
        long res = 1;
        for (int i = 0; i < 2; i++) {
            res *= inspected[inspected.length-1-i];
        }
        System.out.println(res);
        solve2(monkeyItemsCopy, monkeyOperation, monkeyTest, monkeyAction);
    }

    public static void solve2(List<List<Long>> monkeyItems, List<int[]> monkeyOperation,
                              List<Integer> monkeyTest, List<int[]> monkeyAction) {
        int[] inspected = new int[monkeyItems.size()];
        int div = 1;
        for (Integer i : monkeyTest) {
            div *= i;
        }
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < monkeyItems.size(); j++) {
                for (Long item : monkeyItems.get(j)) {
                    inspected[j]++;
                    long newWorryLevel = item % div;
                    if (monkeyOperation.get(j)[1] == 0) {
                        if (monkeyOperation.get(j)[0] == 1) {
                            newWorryLevel += item % div;
                        } else {
                            newWorryLevel *= item % div;
                        }
                    } else {
                        if (monkeyOperation.get(j)[0] == 1) {
                            newWorryLevel += monkeyOperation.get(j)[1];
                        } else {
                            newWorryLevel *= monkeyOperation.get(j)[1];
                        }
                    }
                    newWorryLevel  %= div;
                    if (newWorryLevel % monkeyTest.get(j) == 0) {
                        monkeyItems.get(monkeyAction.get(j)[0]).add(newWorryLevel);
                    } else {
                        monkeyItems.get(monkeyAction.get(j)[1]).add(newWorryLevel);
                    }
                }
                monkeyItems.get(j).clear();
            }
        }

        Arrays.sort(inspected);
        long res = 1;
        for (int i = 0; i < 2; i++) {
            res *= inspected[inspected.length-1-i];
        }
        System.out.println(res);
    }
}

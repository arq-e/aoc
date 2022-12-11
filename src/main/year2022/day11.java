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
        for (int i = 0; i < list.size(); i+= 7) {
            monkeyItems.add(new ArrayList<>());
            String[] items = list.get(i+1).substring(18).split(", ");
            for (String str : items) {
                monkeyItems.get(monkeyItems.size()-1).add(Integer.parseInt(str));
            }

            int[] operation = new int[2];
            if (list.get(i+2).contains("+")) {
                operation[0] = 1;
            } else {
                operation[0] = 2;
            }
            if (!list.get(i+2).endsWith("old")) {
                operation[1] = Integer.parseInt(list.get(i+2).substring(25));
            }
            monkeyOperation.add(operation);

            monkeyTest.add(Integer.parseInt(list.get(i+3).substring(21)));

            int[] action = new int[2];
            action[0] = Integer.parseInt(list.get(i+4).substring(29));
            action[1] = Integer.parseInt(list.get(i+5).substring(30));
            monkeyAction.add(action);
        }

        List<List<Long>> monkeyItemsCopy = new ArrayList<>();
        for (List<Integer> items : monkeyItems) {
            monkeyItemsCopy.add(new ArrayList<>());
            for (Integer j : items) {
                monkeyItemsCopy.get(monkeyItemsCopy.size()-1).add(Long.valueOf(j));
            }
        }

        int[] inspected = new int[monkeyItems.size()];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < monkeyItems.size(); j++) {
                for (Integer item : monkeyItems.get(j)) {
                    inspected[j]++;
                    if (monkeyOperation.get(j)[1] == 0) {
                        if (monkeyOperation.get(j)[0] == 1) {
                            item += item;
                        } else {
                            item *= item;
                        }
                    } else {
                        if (monkeyOperation.get(j)[0] == 1) {
                            item += monkeyOperation.get(j)[1];
                        } else {
                            item  *= monkeyOperation.get(j)[1];
                        }
                    }
                    item /= 3;
                    if (item % monkeyTest.get(j) == 0) {
                        monkeyItems.get(monkeyAction.get(j)[0]).add(item);
                    } else {
                        monkeyItems.get(monkeyAction.get(j)[1]).add(item);
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
                    item %= div;
                    if (monkeyOperation.get(j)[1] == 0) {
                        if (monkeyOperation.get(j)[0] == 1) {
                            item += item % div;
                        } else {
                            item *= item % div;
                        }
                    } else {
                        if (monkeyOperation.get(j)[0] == 1) {
                            item += monkeyOperation.get(j)[1];
                        } else {
                            item *= monkeyOperation.get(j)[1];
                        }
                    }
                    if (item % monkeyTest.get(j) == 0) {
                        monkeyItems.get(monkeyAction.get(j)[0]).add(item);
                    } else {
                        monkeyItems.get(monkeyAction.get(j)[1]).add(item);
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
package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day11 extends Day {

    public static void main(String[] args) throws IOException{
        Day11 day = new Day11();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<Monkey> monkeys = convertInput(list);

        System.out.println(calculateBusiness(monkeys,20,3, false));
    }

    public void solve2(List<String> list) {
        List<Monkey> monkeys = convertInput(list);
        int div = 1;
        for (Monkey monkey : monkeys) {
            div *= monkey.getTest();
        }
        System.out.println(calculateBusiness(monkeys,10000,div, true));
    }

    private long calculateBusiness(List<Monkey> monkeys, int turns, int div, boolean overflow) {
        int[] inspected = new int[monkeys.size()];
        for (int i = 0; i < turns; i++) {
            for (int j = 0; j < monkeys.size(); j++) {
                Monkey monkey = monkeys.get(j);
                for (Long item : monkey.getItems()) {
                    inspected[j]++;
                    if (monkey.getOperation()[1] == 0) {
                        if (monkey.getOperation()[0] == 1) {
                            item += item;
                        } else {
                            item *= item;
                        }
                    } else {
                        if (monkey.getOperation()[0] == 1) {
                            item += monkey.getOperation()[1];
                        } else {
                            item *= monkey.getOperation()[1];
                        }
                    }
                    if (!overflow) {
                        item /= div;
                    } else item %= div;
                    if (item % monkey.getTest() == 0) {
                        monkeys.get(monkey.getAction()[0]).getItems().add(item);
                    } else {
                        monkeys.get(monkey.getAction()[1]).getItems().add(item);
                    }
                }
                monkey.getItems().clear();
            }
        }

        Arrays.sort(inspected);
        long res = 1;
        for (int i = 0; i < 2; i++) {
            res *= inspected[inspected.length-1-i];
        }
        return res;
    }

    private List<Monkey> convertInput(List<String> input) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < input.size(); i+= 7) {

            String[] strItems = input.get(i+1).substring(18).split(", ");
            List<Long> monkeyItems = new ArrayList<>();
            for (String str : strItems) {
                monkeyItems.add(Long.parseLong(str));
            }

            int[] operation = new int[2];
            if (input.get(i+2).contains("+")) {
                operation[0] = 1;
            } else {
                operation[0] = 2;
            }
            if (!input.get(i+2).endsWith("old")) {
                operation[1] = Integer.parseInt(input.get(i+2).substring(25));
            }

            int test = Integer.parseInt(input.get(i+3).substring(21));

            int[] action = new int[2];
            action[0] = Integer.parseInt(input.get(i+4).substring(29));
            action[1] = Integer.parseInt(input.get(i+5).substring(30));

            monkeys.add(new Monkey(monkeyItems, test, action, operation));
        }
        return monkeys;
    }

    class Monkey {
        private List<Long> items;
        private int test;
        private int[] action;
        private int[] operation;

        public Monkey(List<Long> items, int test, int[] action, int[] operation){
            this.items = items;
            this.test = test;
            this.action = action;
            this.operation = operation;
        }
        public List<Long> getItems() {
            return items;
        }

        public Integer getTest() {
            return test;
        }

        public int[] getAction() {
            return action;
        }

        public int[] getOperation() {
            return operation;
        }
    }
}
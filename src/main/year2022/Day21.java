package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day21 extends Day {

    public static void main(String[] args) throws IOException {
        Day21 day = new Day21();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<Monkey> monkeysWithoutNum = convertInput(list);
        List<Monkey> monkeysWithNum = divideMonkeys(monkeysWithoutNum);
        resolveMonkeys(monkeysWithoutNum, monkeysWithNum, false);

        for (Monkey monkey : monkeysWithNum) {
            if (monkey.getName().equals("root")) {
                System.out.println(monkey.getNumber());
                break;
            }
        }
    }

    public void solve2(List<String> list) {
        List<Monkey> monkeys = convertInput(list);
        Monkey root = null;
        for (Monkey m: monkeys) {
            if (m.getName().equals("root")) {
                root = m;
                break;
            }
        }
        Monkey rootLeft = null;
        Monkey rootRight = null;
        for (Monkey m: monkeys) {
            if (m.getName().equals("humn")) {
                m.number = -1;
            }
            if (m.getName().equals(root.getWaitFor()[0])){
                rootLeft = m;
            }
            if (m.getName().equals(root.getWaitFor()[1])){
                rootRight = m;
            }
        }

        List<Monkey> monkeysWithNum = new ArrayList<>();
        List<Monkey> monkeysWithoutNum = new ArrayList<>();
        for (Monkey m : monkeys) {
            if (m.getNumber() > 0) {
                monkeysWithNum.add(m);
            } else if (!m.getName().equals("humn")){
                monkeysWithoutNum.add(m);
            }
        }
        resolveMonkeys(monkeysWithoutNum,monkeysWithNum,true);

        long target;
        Monkey targetMonkey;
        if (rootLeft.getNumber() != 0) {
            target = rootLeft.getNumber();
            targetMonkey = rootRight;
        } else {
            target = rootRight.getNumber();
            targetMonkey = rootLeft;
        }

        System.out.println(calculateHumanNumber(monkeys, targetMonkey, target));
    }

    private List<Monkey> divideMonkeys(List<Monkey> monkeys) {
        List<Monkey> monkeysWithNum = new ArrayList<>();
        int i = 0;
        while (i < monkeys.size()) {
            if (monkeys.get(i).getNumber() > 0) {
                monkeysWithNum.add(monkeys.get(i));
                monkeys.remove(i);
            } else i++;
        }
        return monkeysWithNum;
    }

    private void resolveMonkeys(List<Monkey> monkeysWithoutNum, List<Monkey> monkeysWithNum, boolean partTwo) {
        int count = 0;
        int count1 = -1;
        while (monkeysWithoutNum.size() > 0 || partTwo && count==count1) {
            for (Monkey m : monkeysWithoutNum) {
                long first = 0;
                long second = 0;
                for (Monkey m1 : monkeysWithNum) {
                    if (m1.getName().equals(m.waitFor[0]) && m1.getNumber()>0) {
                        first =m1.getNumber();
                    }
                    if (m1.getName().equals(m.waitFor[1]) && m1.getNumber()>0) {
                        second =m1.getNumber();
                    }
                }
                if (first > 0 && second > 0) {
                    m.number = calculateNum(m, first, second);
                    m.yelled = true;
                    monkeysWithNum.add(m);
                    count++;
                }
            }
            if (count == count1) break;
            count1 = count;
            int i = 0;
            while (i < monkeysWithoutNum.size()) {
                if (monkeysWithoutNum.get(i).yelled) {
                    monkeysWithoutNum.remove(i);
                } else i++;
            }
        }
    }

    private long calculateNum(Monkey m, long first, long second) {
        if (m.getOperation().equals("+") ) {
            return first+second;
        } else if (m.getOperation().equals("-")) {
            return first-second;
        } else if (m.getOperation().equals("*")) {
            return first*second;
        } else {
            if (second != 0) return first/second;
            else return 0;
        }
    }

    private long calculateHumanNumber(List<Monkey> monkeys, Monkey targetMonkey, long target) {
        Monkey rootLeft = null;
        Monkey rootRight = null;
        for (Monkey m: monkeys) {
            if (m.getName().equals(targetMonkey.getWaitFor()[0])){
                rootLeft = m;
            }
            if (m.getName().equals(targetMonkey.getWaitFor()[1])){
                rootRight = m;
            }
            if (rootLeft!=null && rootRight != null) break;
        }

        if (rootLeft.getNumber() > 0) {
            long newTarget = adjustTarget(targetMonkey, target, false, rootLeft.getNumber());
            if (rootRight.getName().equals("humn")) return newTarget;
            else return calculateHumanNumber(monkeys, rootRight, newTarget);
        } else {
            long newTarget = adjustTarget(targetMonkey, target, true, rootRight.getNumber());
            if (rootLeft.getName().equals("humn")) return newTarget;
            else return calculateHumanNumber(monkeys, rootLeft, newTarget);
        }
    }

    private long adjustTarget(Monkey monkey, long target, boolean targetIsLeft, long secondVal) {
        if (monkey.getOperation().equals("+")) return target - secondVal;
        else if (monkey.getOperation().equals("*")) return target / secondVal;
        if (targetIsLeft) {
            if (monkey.getOperation().equals("-")) return target + secondVal;
            else return target*secondVal;
        } else {
            if (monkey.getOperation().equals("-")) return secondVal - target;
            else return secondVal / target;
        }
    }

    private List<Monkey> convertInput(List<String> list) {
        List<Monkey> list1 = new ArrayList<>();
        for (String s : list) {
            String name = s.substring(0,s.indexOf(":"));
            String num = s.replaceAll("[^0-9]", "");
            if (num.length() > 0) {
                int number = Integer.parseInt(num);
                Monkey monkey = new Monkey(name, number);
                list1.add(monkey);
            } else {
                String operation = s.replaceAll("[^-+*/]", "");
                String[] waitFor = s.substring(s.indexOf(":")+2).trim().split("[^a-z]+");
                Monkey monkey = new Monkey(name, operation, waitFor);
                list1.add(monkey);
            }
        }
        return list1;
    }

    public  class Monkey {
        String name;
        long number;
        String operation;
        String[] waitFor;
        boolean yelled;

        Monkey (String name, int number){
             this.name = name;
             this.number = number;
             yelled = false;
        }

        Monkey(String name, String operation, String[] waitFor) {
            this.name = name;
            this.operation = operation;
            this.waitFor = waitFor;
            this.number = 0;
            yelled = false;
        }

        public boolean isYelled() {
            return yelled;
        }

        public String getName() {
            return name;
        }

        public long getNumber() {
            return number;
        }

        public String getOperation() {
            return operation;
        }

        public String[] getWaitFor() {
            return waitFor;
        }

        public void printMonkey() {
            if (number != 0) System.out.print(name + " " + number);
            else System.out.print(name + " " + waitFor[0] + " " + operation + " " + waitFor[1]);
            System.out.println();
        }
    }
}

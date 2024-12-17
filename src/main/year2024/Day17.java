package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class Day17 extends Day {

    public static void main(String[] args) throws IOException {
        Day17 day = new Day17();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Computer computer = buildComputer(list);
        computer.runInstruction();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < computer.output.size(); ++i) {
            sb.append(computer.output.get(i)).append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.printf("Part 1 answer is: %s\n", sb.toString());

    }

    public void solve2(List<String> list) {
        Computer computer = buildComputer(list);
        computer.runOnlyOnce = true;

        SortedSet<Long> prev = new TreeSet<>();
        SortedSet<Long> next = new TreeSet<>();
        prev.add(0l);

        for (int i = computer.instruction.length - 1; i >= 0; --i) {
            for (long num : prev) {
                findValue(computer, num, computer.instruction[i], next);
            }
            prev.clear();
            prev.addAll(next);
            next.clear();
        }

        System.out.printf("Part 2 answer is: %d\n", prev.first());
    }

    private void findValue(Computer computer, long prev, long target, Set<Long> next) {
        for (int i = 0; i < 8; ++i) {
            computer.reset(prev * 8 + i);
            computer.runInstruction();
            if (computer.output.get(0).equals(target)) {
                next.add(prev * 8 + i);
            }
        }
    }

    private static long[] parseInts(String s) {
        return Stream.of(s.replaceAll("[^0123456789-]", "\s").trim().split("\s+"))
                    .mapToLong(Long::parseLong)
                    .toArray();
    }

    public Computer buildComputer(List<String> list) {
        long[] regA = parseInts(list.get(0));
        long[] regB = parseInts(list.get(1));
        long[] regC = parseInts(list.get(2));
        long[] instructions = parseInts(list.get(4));
        return new Computer(regA[0], regB[0], regC[0], instructions);
    }

    class Computer {
        long[] instruction;
        List<Long> output;
        int idx;
        long regA;
        long regB;
        long regC;
        boolean runOnlyOnce;

        Computer(long A, long B, long C, long[] instruction) {
            regA = A;
            regB = B;
            regC = C;
            this.instruction = instruction;
            idx = 0;
            output = new ArrayList<>();
            runOnlyOnce = false;
        }



        public void reset(long regA) {
            this.regA = regA;
            this.regB = 0;
            this.regC = 0;
            this.idx = 0;
            output.clear();
        }

        public int runInstruction() {
            while (idx < instruction.length - 1) {
                performOperation((int)instruction[idx], (int)instruction[idx+1]);
                if (output.size() == instruction.length) {
                    break;
                }
            }
            return 0;
        }

        private long getComboValue(int value) {
            switch (value) {
                case 0,1,2,3 -> {return value;}
                case 4 -> {return regA;}
                case 5 -> {return regB;}
                case 6 -> {return regC;}
            }
            return -1;
        }

        private int performOperation(int operator, int operand) {
            switch (operator) {
                case 0 -> {adv(getComboValue(operand));}
                case 1 -> {bxl(operand);}
                case 2 -> {bst(getComboValue(operand));}
                case 3 -> {jnz(operand);}
                case 4 -> {bxc(operand);}
                case 5 -> {out(getComboValue(operand));}
                case 6 -> {bdv(getComboValue(operand));}
                case 7 -> {cdv(getComboValue(operand));}
            }
            return 0;
        }

        private void adv(long value) {
            idx += 2;
            regA = (long)(regA / (long)Math.pow(2, value));
        }

        private void bxl(long value) {
            idx += 2;
            regB = regB ^ value;
        }

        private void bst(long value) {
            idx += 2;
            regB = value % 8;
        }

        private void jnz(long value) {
            if (regA == 0 || runOnlyOnce) {
                idx += 2;
                return;
            }
            idx = (int)value;
        }

        private void bxc(long value) {
            regB = regB ^ regC;
            idx += 2;
        }

        private void out(long value) {
            output.add((value % 8));
            idx += 2;
        }

        private void bdv(long value) {
            regB = (long)(regA / Math.pow(2, value));
            idx += 2;
        }

        private void cdv(long value) {
            regC = (long)(regA / Math.pow(2, value));
            idx += 2;
        }
    }
}

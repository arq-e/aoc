package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Day13 extends Day {
    int aCost = 3;
    int bCost = 1;

    public static void main(String[] args) throws IOException {
        Day13 day = new Day13();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i += 4) {
            sum += smartMonkeySolveEquations(parseMachine(i, list, false));
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        for (int i = 0; i < list.size(); i += 4) {
            sum += smartMonkeySolveEquations(parseMachine(i, list, true));
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long smartMonkeySolveEquations(Machine m) {
        long j = -1;
        long i = -1;
        long del = m.prize[0] * m.buttonB[1] - m.buttonB[0] * m.prize[1]; 
        long dot = (m.buttonA[0] * m.buttonB[1] - m.buttonB[0] * m.buttonA[1]);
        if (del % dot == 0) {
            i = del / dot;
            if ((m.prize[1] -  m.buttonA[1] * i) % m.buttonB[1] == 0) {
                j = (m.prize[1] -  m.buttonA[1] * i) / m.buttonB[1];
            }

        }
        if (i >= 0 && j >= 0) {
            return i * aCost + j * bCost;
        }

        return 0;
    }

    private Machine parseMachine(int idx, List<String> list, boolean addExtraSteps) {
        long[] buttonA = parseInts(list.get(idx++));
        long[] buttonB = parseInts(list.get(idx++));
        long[] prize = parseInts(list.get(idx++));
        if (addExtraSteps) {
            for (int i = 0; i < 2; ++i) {
                prize[i] += 10000000000000l;
            }
        }

        return new Machine(buttonA, buttonB, prize);
    }

    public static long[] parseInts(String s) {
        return Stream.of(s.replaceAll("[^0123456789-]", "\s").trim().split("\s+"))
                    .mapToLong(Long::parseLong)
                    .toArray();
    }

    record Machine(long[] buttonA, long[] buttonB, long[] prize) {}
}

package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class Day6 extends Day {

    public static void main(String[] args) throws IOException {
        Day6 day = new Day6();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long res = 1;
        int[] times = Stream.of(list.get(0).split("\s+")).skip(1).mapToInt(Integer::parseInt).toArray();
        int[] dist = Stream.of(list.get(1).split("\s+")).skip(1).mapToInt(Integer::parseInt).toArray();

        for (int i = 0;  i < times.length; ++i) {
            res *= countWins(times[i], dist[i]);
        }

        System.out.printf("Part 1 answer is: %d\n", res);
    }

    public void solve2(List<String> list) {
        long time = Long.parseLong(list.get(0).replaceAll("[^0-9]", ""));
        long dist = Long.parseLong(list.get(1).replaceAll("[^0-9]", ""));

        System.out.printf("Part 2 answer is: %d\n", countWins(time, dist));
    }

    private int countWins(long time, long dist) {
        double d = Math.sqrt(Math.pow(time, 2) - 4 * dist);       
        int x1 = (int) Math.ceil((time + d) / 2) - 1;
        int x2 = (int) (time - d) / 2; 

        return x1 - x2;
    }
}

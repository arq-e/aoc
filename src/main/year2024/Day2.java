package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Day2 extends Day {

    public static void main(String[] args) throws IOException {
        Day2 day = new Day2();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;

        for (String s : list) {
            int[] arr = Stream.of(s.split(" ")).mapToInt(Integer::parseInt).toArray();
            boolean inc = false;
            if (arr[arr.length - 1] - arr[0] > 0) {
                inc = true;
            }
            boolean min = true;
            for (int i = 1; i < arr.length; ++i) {
                if (inc) {
                    if (arr[i] - arr[i - 1] <= 0 || arr[i]- arr[i - 1] > 3) {
                        min = false;
                        break;
                    }
                } else {
                    if (arr[i - 1] - arr[i] <= 0 || arr[i - 1]- arr[i] > 3) {
                        min = false;
                        break;
                    }                    
                }
            }

            if (min) {
                ++sum;
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        for (String s : list) {
            int[] arr = Stream.of(s.split(" ")).mapToInt(Integer::parseInt).toArray();
            boolean inc = false;
            if (arr[arr.length - 1] - arr[0] > 0) {
                inc = true;
            }
            boolean min = true;
            for (int j = 0; j < arr.length; ++j) {
                min = true;
                for (int i = 1; i < arr.length; ++i) {
                    if (j == i || j == i - 1 && j == 0) {
                        continue;
                    }
                    int p = i - 1;
                    if (j == i - 1) {
                        p = i - 2;
                    }
                    if (inc) {
                        if (arr[i] - arr[p] <= 0 || arr[i] - arr[p] > 3) {
                            min = false;
                            break;
                        }
                    } else {
                        if (arr[p] - arr[i] <= 0 || arr[p] - arr[i] > 3) {
                            min = false;
                            break;
                        }                    
                    }
                }
                if (min) {
                    break;
                }
            }

            if (min) {
                ++sum;
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }
}

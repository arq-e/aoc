package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.PriorityQueue;

public class day1 {

    public static void main(String[] args) throws IOException{
        AdventInputReader.createYearDir(Year.now().getValue());
        List<String> input = AdventInputReader.getInput(2022, 1);

        part1(input);

        part2(input);
    }

    public static void part1(List<String> input) {
        int maxSum = 0;
        int sum = 0;
        for (String s : input) {
            if (s.equals("")) {
                maxSum = Math.max(sum,maxSum);
                sum = 0;
            }
            else sum += Integer.parseInt(s);
        }

        System.out.println(maxSum);
    }

    public static void part2(List<String> input) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(3);
        int sum = 0;
        for (String s : input) {
            if (s.equals("")) {
                priorityQueue.add(sum);
                sum = 0;
                if (priorityQueue.size() > 3) priorityQueue.poll();
            }
            else sum += Integer.parseInt(s);
        }

        sum = 0;
        for (Integer i : priorityQueue) {
            sum += i;
        }
        System.out.println(sum);
    }
}

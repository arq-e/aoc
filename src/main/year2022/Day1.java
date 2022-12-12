package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

public class Day1 extends Day {

    public static void main(String[] args) throws IOException{
        Day1 day = new Day1();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int maxSum = 0;
        int sum = 0;
        for (String s : list) {
            if (s.equals("")) {
                maxSum = Math.max(sum,maxSum);
                sum = 0;
            }
            else sum += Integer.parseInt(s);
        }


        System.out.println(maxSum);
    }

    public void solve2(List<String> list) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(3);
        int sum = 0;
        for (String s : list) {
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

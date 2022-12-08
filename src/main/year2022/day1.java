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

        solve(input);

    }

    public static void solve(List<String> list) {
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

        solve2(list);
    }

    public static void solve2(List<String> list) {
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

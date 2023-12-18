package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;


import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Day18 extends Day {

    public static void main(String[] args) throws IOException {
        Day18 day = new Day18();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        List<int[]> commands = new ArrayList<>();
        for (String s : list) {
            String[] split = s.split(" ");
            int dir = 0;
            if (split[0].equals("D")) {
                dir = 1;
            } else if (split[0].equals("L")) {
                dir = 2;
            } else if (split[0].equals("U")) {
                dir = 3;
            } 
            commands.add(new int[]{dir, Integer.parseInt(split[1])});
        }
        dig23(commands);
        System.out.printf("Part 1 answer is: %d\n", dig23(commands));
    }

    public void solve2(List<String> list) {
        List<int[]> commands = new ArrayList<>();
        for (String s : list) {
            String[] split = s.split(" ");
            String num = split[2].substring(2,7);
            int range = Integer.parseInt(num, 16);
            commands.add(new int[]{split[2].charAt(7)-'0', range});
        }
        dig23(commands);
        System.out.printf("Part 2 answer is: %d\n", dig23(commands));
    }

    private long dig23(List<int[]> commands) {
        long sum = 0;
        long x = 0;
        long y = 0;
        long minX = 0;
        long minY = 0;
        List<long[]> list = new ArrayList<>();
        list.add(new long[]{0,0});
        long perimeter = 0;
        for (int[] command : commands) {
            perimeter += command[1];
            if (command[0] == 0) {
                y += command[1];
                if (!(x== 0 && y == 0)) list.add(new long[]{x,y});
            } else if (command[0] == 1) {
                x += command[1];
                if (!(x== 0 && y == 0)) list.add(new long[]{x, y});
            } else if (command[0] == 2) {
                y-=command[1];
                if (!(x== 0 && y == 0)) list.add(new long[]{x,y});
                if (y < minY) minY = y;
            } else if (command[0] == 3) {
                x -= command[1];
                if (!(x== 0 && y == 0)) list.add(new long[]{x,y});
                if (x < minX) minX = x;
            }
        }

        for (int i = 0; i < list.size()-1; ++i) {
            sum += (long)(list.get(i)[0]*list.get(i+1)[1] - list.get(i+1)[0]*list.get(i)[1]);
        }
        
        sum += (long)(list.get(list.size()-1)[0]*list.get(0)[1] - list.get(0)[0]*list.get(list.size()-1)[1]);
        sum = Math.abs(sum) / 2;
        long count = sum - perimeter / 2 + 1;

        return count + perimeter;
    }

}

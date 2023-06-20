package main.year2021;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day2 extends Day {

    public static void main(String[] args) throws IOException {
        Day2 day = new Day2();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int horizontalChange = 0;
        int verticalChange = 0;
        for (String s : list) {
            String[] commands = s.split(" ");
            if (commands[0].equals("forward")) {
                horizontalChange += Integer.parseInt(commands[1]);
            } else if (commands[0].equals("down")) {
                verticalChange += Integer.parseInt(commands[1]);
            } else verticalChange -= Integer.parseInt(commands[1]);
        }

        System.out.println(horizontalChange*verticalChange);
    }

    public void solve2(List<String> list) {
        int horizontalChange = 0;
        int verticalChange = 0;
        int aim = 0;
        for (String s : list) {
            String[] commands = s.split(" ");
            if (commands[0].equals("forward")) {
                horizontalChange += Integer.parseInt(commands[1]);
                verticalChange += aim*Integer.parseInt(commands[1]);
            } else if (commands[0].equals("down")) {
                aim += Integer.parseInt(commands[1]);
            } else aim -= Integer.parseInt(commands[1]);
        }

        System.out.println(horizontalChange*verticalChange);
    }
}

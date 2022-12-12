package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day10 extends Day {

    public static void main(String[] args) throws IOException{
        Day10 day = new Day10();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {

        int x = 1;
        int n = 1;
        int sum = 0;
        for (String s : list) {
            String[] lineSplit = s.split(" ");
            if (n == 20 || n == 60 || n == 100 || n == 140 || n == 180 || n == 220) sum += x*n;
            if (lineSplit[0].equals("noop")) {
                n++;
            } else {
                if (n == 19 || n == 59 || n == 99 || n == 139 || n == 179 || n == 219) {
                    sum += x*(n+1);

                }
                x += Integer.parseInt(lineSplit[1]);
                n += 2;
            }
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        int n = 0;
        int x = 1;
        char[][] screen = new char[6][40];
        for (String s : list) {
            String[] lineSplit = s.split(" ");
            if (lineSplit[0].equals("noop")) {
                int row = (n) / 40;
                if (n%40 == x%40 || n%40 == (x-1)%40 || n%40 == (x+1)%40) {
                    screen[row][n%40] = '#';
                } else {
                    screen[row][n%40] = '.';
                }
                n++;
            } else {
                for(int i = 0; i < 2; i++) {
                    int row = n / 40;
                    if (n%40 == x%40 || n%40 == (x-1)%40 || n%40 == (x+1)%40) {
                        screen[row][n%40] = '#';
                    } else {
                        screen[row][n%40] = '.';
                    }
                    n++;
                }
                x += Integer.parseInt(lineSplit[1]);
            }
        }
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 40; j++) {
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }
    }
}

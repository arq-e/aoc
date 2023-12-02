package main.year2023;

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
        int red = 12, green = 13, blue = 14;
        boolean impossibleSet;
        String[] words;

        int sum = 0;
        for (int j = 0; j < list.size(); ++j) {
            words = list.get(j).substring(list.get(j).indexOf(":") + 2).split(" ");

            impossibleSet = false;
            for (int i = 1; i < words.length; i += 2) {
                if (words[i].charAt(0) == 'r') {
                    impossibleSet = Integer.parseInt(words[i-1]) > red;
                } else if (words[i].charAt(0) == 'g') {
                    impossibleSet = Integer.parseInt(words[i-1]) > green;
                } else if (words[i].charAt(0) == 'b') {
                    impossibleSet = Integer.parseInt(words[i-1]) > blue;
                }
                if (impossibleSet) break;
            }
            if (!impossibleSet) sum += (j + 1);
        }

        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        int red, green, blue;
        String[] words;
        
        int sum = 0;
        for (int j = 0; j < list.size(); ++j) {
            words = list.get(j).substring(list.get(j).indexOf(":") + 2).split(" ");
            red = 0;
            green = 0;
            blue = 0;

            for (int i = 1; i < words.length; i += 2) {
                if (words[i].charAt(0) == 'r') {
                    red = Math.max(red, Integer.parseInt(words[i-1]));
                } else if (words[i].charAt(0) == 'g') {
                    green = Math.max(green, Integer.parseInt(words[i-1]));
                } else if (words[i].charAt(0) == 'b') {
                    blue = Math.max(blue, Integer.parseInt(words[i-1]));
                }
            }
            sum += (red * green * blue);
        }

        System.out.println(sum);
    }
}

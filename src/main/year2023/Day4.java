package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Day4 extends Day {

    public static void main(String[] args) throws IOException {
        Day4 day = new Day4();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;

        for (String s : list) {
            String[] cardSplit = s.split("\\|");
            Set<String> winning = new HashSet<>(Arrays.asList(cardSplit[0].trim().split(":")[1].split("\s+")));
            String[] active = cardSplit[1].trim().split("\s+");

            int points = 0;
            for (String num : active) {
    	        if (winning.contains(num)) {
                    if (points == 0) points = 1;
                    else points *= 2;
                }
            }       
            sum += points; 
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0, curr = 0;
        int[] copies = new int[list.size()];
        Arrays.fill(copies, 1);

        for (String s : list) {
            String[] cardSplit = s.split("\\|");
            Set<String> winning = new HashSet<>(Arrays.asList(cardSplit[0].trim().split(":")[1].split("\s+")));
            String[] active = cardSplit[1].trim().split("\s+");

            int addTo = curr + 1;
            for (String num : active) {
                if (winning.contains(num) && addTo < copies.length) {
                    copies[addTo++] += copies[curr];
                }
            }       
            sum += copies[curr++];
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }
}
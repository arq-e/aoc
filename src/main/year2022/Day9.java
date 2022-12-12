package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day9 extends Day {

    public static void main(String[] args) throws IOException{
        Day9 day = new Day9();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {

        System.out.println(moveRope(list,2));
    }

    public void solve2(List<String> list) {

        System.out.println(moveRope(list,10));
    }

    private int moveRope(List<String> list, int knots) {
        Map<Integer, Set<Integer>> positions = new HashMap<>();
        positions.put(0, new HashSet<>());
        positions.get(0).add(0);

        List<int[]> pos = new ArrayList<>();
        for (int i = 0; i < knots; i++) {
            int[] p = new int[2];
            pos.add(p);
        }
        int[] posH = new int[2];
        int[] posT = new int[2];
        for (String s : list) {
            int turns = Integer.parseInt(s.substring(2));
            for (int i = 0; i < turns; i++) {
                if (s.charAt(0) == 'U') {
                    pos.get(0)[0]--;
                } else if (s.charAt(0) == 'D') {
                    pos.get(0)[0]++;
                } else if (s.charAt(0) == 'R') {
                    pos.get(0)[1]++;
                } else {
                    pos.get(0)[1]--;
                }
                for (int j = 1; j < knots; j++) {
                    if (pos.get(j)[0] - pos.get(j-1)[0] > 1) {
                        if (pos.get(j)[1] > pos.get(j-1)[1]) {
                            pos.get(j)[1]--;
                        } else if (pos.get(j)[1] < pos.get(j-1)[1]){
                            pos.get(j)[1]++;
                        }
                        pos.get(j)[0]--;
                    } else if (pos.get(j-1)[0] - pos.get(j)[0] > 1) {
                        if (pos.get(j)[1] > pos.get(j-1)[1]) {
                            pos.get(j)[1]--;
                        } else if (pos.get(j)[1] < pos.get(j-1)[1]){
                            pos.get(j)[1]++;
                        }
                        pos.get(j)[0]++;
                    } else if (pos.get(j-1)[1] - pos.get(j)[1] > 1) {
                        if (pos.get(j)[0] > pos.get(j-1)[0]) {
                            pos.get(j)[0]--;
                        } else if (pos.get(j)[0] < pos.get(j-1)[0]){
                            pos.get(j)[0]++;
                        }
                        pos.get(j)[1]++;
                    } else if (pos.get(j)[1] - pos.get(j-1)[1] > 1) {
                        if (pos.get(j)[0] > pos.get(j-1)[0]) {
                            pos.get(j)[0]--;
                        } else if (pos.get(j)[0] < pos.get(j-1)[0]){
                            pos.get(j)[0]++;
                        }
                        pos.get(j)[1]--;
                    }
                }
                if (!positions.containsKey(pos.get(knots-1)[0])) {
                    positions.put(pos.get(knots-1)[0], new HashSet<>());
                }
                positions.get(pos.get(knots-1)[0]).add(pos.get(knots-1)[1]);
            }
        }

        int res = 0;
        for (Integer key : positions.keySet()) {
            res += positions.get(key).size();
        }
        return res;
    }
}

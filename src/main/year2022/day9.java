package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.*;

public class day9 {

    public static void main(String[] args) throws IOException {
        List<String> input = AdventInputReader.getInput(2022,9);

        solve(input);

    }

    public static void solve(List<String> list) {

        Map<Integer, Set<Integer>> positions = new HashMap<>();
        positions.put(0,new HashSet<>());
        positions.get(0).add(0);
        int[] posH = new int[2];
        int[] posT = new int[2];

        for (String s : list) {
            int turns = Integer.parseInt(s.substring(2));
            for (int i = 0; i < turns; i++) {
                if (s.charAt(0) == 'U') {
                    posH[0]--;
                    if (posT[0] - posH[0] > 1) {
                        if (posT[1] != posH[1]) {
                            posT[1] = posH[1];
                        }
                        posT[0]--;
                    }
                } else if (s.charAt(0) == 'D') {
                    posH[0]++;
                    if (posH[0] - posT[0] > 1) {
                        if (posT[1] != posH[1]) {
                            posT[1] = posH[1];
                        }
                        posT[0]++;
                    }
                } else if (s.charAt(0) == 'R') {
                    posH[1]++;
                    if (posH[1] - posT[1] > 1) {
                        if (posT[0] != posH[0]) {
                            posT[0] = posH[0];
                        }
                        posT[1]++;
                    }
                } else {
                    posH[1]--;
                    if (posT[1] - posH[1] > 1) {
                        if (posT[0] != posH[0]) {
                            posT[0] = posH[0];
                        }
                        posT[1]--;
                    }
                }
                if (!positions.containsKey(posT[0])) {
                    positions.put(posT[0], new HashSet<>());
                }
                positions.get(posT[0]).add(posT[1]);
            }
        }

        int sum = 0;
        for (Integer p : positions.keySet()) {
                sum+=positions.get(p).size();
        }
        System.out.println(sum);

        solve2(list);
    }

    public static void solve2(List<String> list) {

        Map<Integer, Set<Integer>> positions = new HashMap<>();

        positions.put(0,new HashSet<>());
        positions.get(0).add(0);

        List<int[]> pos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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
                for (int j = 1; j < 10; j++) {
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
                if (!positions.containsKey(pos.get(9)[0])) {
                    positions.put(pos.get(9)[0], new HashSet<>());
                }
                positions.get(pos.get(9)[0]).add(pos.get(9)[1]);
            }
        }

        int sum = 0;
        for (Integer p : positions.keySet()) {
            sum+=positions.get(p).size();
        }

        System.out.println(sum);
    }
}

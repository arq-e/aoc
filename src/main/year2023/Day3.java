package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.ArrayList;

public class Day3 extends Day {
    private final int[][] pos = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public static void main(String[] args) throws IOException {
        Day3 day = new Day3();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0, cur;
        boolean isPartNumber;
        char[][] arr = convertTo2DCharArray(list);

        for (int i = 0; i < arr.length; ++i) {
            cur = 0;
            isPartNumber = false;
            for (int j = 0; j < arr[i].length; ++j) {
                if (arr[i][j] >= 48 && arr[i][j] <= 58) {
                    cur = cur * 10 + (arr[i][j] - 48);
                    if (!isPartNumber) isPartNumber = findAdj(arr, i, j, ".0123456789", false, null);
                } else {
                    if (isPartNumber) {
                        sum += cur;
                        isPartNumber = false;
                    }
                    cur = 0;
                }
            }
            if (isPartNumber) {
                sum += cur;
            }            
        }
        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0, cur;
        char[][] arr = convertTo2DCharArray(list);

        TreeSet<int[]> adjGears = new TreeSet<>((a,b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        TreeMap<int[], List<Integer>> gearMap = new TreeMap<>((a,b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        for (int i = 0; i < arr.length; ++i) {
            cur = 0;
            for (int j = 0; j < arr[i].length; ++j) {
                if (arr[i][j] >= 48 && arr[i][j] <= 58) {
                    cur = cur * 10 + (arr[i][j] - 48);
                    findAdj(arr, i, j, "*", true, adjGears); 
                } else {
                    for (int[] gearPos : adjGears) {
                        if (!gearMap.containsKey(gearPos)) {
                            gearMap.put(gearPos, new ArrayList<>());
                        } 
                        gearMap.get(gearPos).add(cur);
                    }
                    adjGears.clear();
                    cur = 0;
                }
            }
            for (int[] gearPos : adjGears) {
                if (!gearMap.containsKey(gearPos)) {
                    gearMap.put(gearPos, new ArrayList<>());
                } 
                gearMap.get(gearPos).add(cur);
            }
            adjGears.clear();        
        }

        for (List<Integer> adjNums : gearMap.values()) {
            if (adjNums.size() == 2) {
                sum += adjNums.get(0) * adjNums.get(1);
            }
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private boolean findAdj(char[][] arr, int x, int y, String symbolsString, boolean takeSymbols, Collection<int[]> collection) {
        boolean symbolFound = false;
        Set<Character> symbols = new HashSet<>();
        for (int i = 0; i < symbolsString.length(); ++i) {
            symbols.add(symbolsString.charAt(i));
        }

        for (int i = 0; i < 8; ++i) {
            if (x + pos[i][0] >= 0 && x + pos[i][0] < arr.length && y + pos[i][1] >= 0 && y + pos[i][1] < arr[0].length) {
                if (symbols.contains(arr[x + pos[i][0]][y + pos[i][1]]) && takeSymbols || !symbols.contains(arr[x + pos[i][0]][y + pos[i][1]]) && !takeSymbols) {
                    symbolFound = true;
                    if (collection != null) collection.add(new int[]{x + pos[i][0], y + pos[i][1]});
                    else if (symbolFound) break;  
                }
            }
        }

        return symbolFound;
    }
}
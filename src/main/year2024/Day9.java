package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day9 extends Day {

    public static void main(String[] args) throws IOException {
        Day9 day = new Day9();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long sum = 0;
        for (String s : list) {
            int[] arr = convertInputToArray(s, new HashMap<>());

            int l = arr.length - 1;
            for (int i = 0; i < l; ++i) {
                if (arr[i] == -1) {
                    while (arr[l] == - 1) {
                        --l;
                    }
                    if (l > i) {
                        arr[i] = arr[l];
                        arr[l] = -1;
                        --l;
                    }
                }
            }

            sum += calcScore(arr);
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        long sum = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (String s : list) {
            int[] arr = convertInputToArray(s, map);
            int pos = arr.length - 1;
            while (pos >= 0) {
                int fileEnd = pos;
                if (arr[pos] != -1) {
                    while (pos >= 0 && arr[pos] == arr[fileEnd] ) {
                        --pos;
                    }
                    int len = fileEnd - pos;
                    int fileStart = pos + 1;
  
                    int spaceStart = -1;
                    for (int key : map.keySet()) {
                        if (key >= fileStart) {
                            break;
                        }
                        if (map.get(key) >= len) {
                            spaceStart = key;
                            break;
                        }
                    }
                    if (spaceStart >= 0) {
                        for (int j = 0; j < len; ++j) {
                            arr[j + spaceStart] = arr[fileEnd];
                            arr[fileStart + j] = -1;
                        }
                        int spaceLeft = map.get(spaceStart) - len;
                        map.remove(spaceStart);
                        if (spaceLeft > 0) {
                            map.put(spaceStart+len, spaceLeft);
                        }
                    }
                } else 
                    --pos;
            }
            
            sum += calcScore(arr);
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int[] convertInputToArray(String s, Map<Integer, Integer> freeSpaceMap) {
        List<Integer> res = new ArrayList<>();
        int fileNum = 0;
        boolean isFile = true;
        for (char ch : s.toCharArray()) {
            int l = ch - '0';
            if (isFile) {
                for (int i = 0; i < l; ++i) {
                    res.add(fileNum);
                }
                ++fileNum;
            } else {
                freeSpaceMap.put(res.size(), l);
                for (int i = 0; i < l; ++i) {
                    res.add(-1);
                }
            }
            isFile = !isFile;
        }

        int[] arr = new int[res.size()];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = res.get(i);
        }

        return arr;
    }

    private long calcScore(int[] arr) {
        long res = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] != -1) {
                res += arr[i] * i;
            }
        }   
        return res;     
    }
}

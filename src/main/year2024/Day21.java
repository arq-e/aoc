package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class Day21 extends Day {
    Map<Integer, Integer> map = new HashMap<>();
    Map<Integer, Integer> dirMap = new HashMap<>();
    Map<String, Map<Integer,Long>> memo = new HashMap<>();
    int[][] numericPad = new int[][]{{7,8,9},{4,5,6},{1,2,3},{-1,0,10}};
    int[][] dirPad = new int[][]{{-1,1,5},{4,3,2}};
    public static void main(String[] args) throws IOException {
        Day21 day = new Day21();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        memo.clear();
        map.put(0, 10 * 3 + 1);
        map.put(1, 10 * 2 + 0);
        map.put(2, 10 * 2 + 1);
        map.put(3, 10 * 2 + 2);
        map.put(4, 10 * 1 + 0);
        map.put(5, 10 * 1 + 1);
        map.put(6, 10 * 1 + 2);
        map.put(7, 10 * 0 + 0);
        map.put(8, 10 * 0 + 1);
        map.put(9, 10 * 0 + 2);
        map.put(10, 10 * 3 + 2);
        dirMap.put(1, 10 * 0 + 1);
        dirMap.put(2, 10 * 1 + 2);
        dirMap.put(3, 10 * 1 + 1);
        dirMap.put(4, 10 * 1 + 0);
        dirMap.put(5, 10 * 0 + 2);

        long sum = 0;
        for (String s : list) {
            int nums[] = parseInts(s);
            int n = 0;
            if (s.charAt(0) <= '9') {
                n = s.charAt(0)- '0';
            } else {
                n = 10;
            }
            int nx = map.get(n) / 10;
            int ny = map.get(n) % 10;
            long l = buildNumeric(s, 1, 3, 2, nx, ny, new ArrayList<>(),2);
            sum += nums[0] * l;
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        memo.clear();
        long sum = 0;
        for (String s : list) {
            int nums[] = parseInts(s);
            int n = 0;
            if (s.charAt(0) <= '9') {
                n = s.charAt(0)- '0';
            } else {
                n = 10;
            }
            int nx = map.get(n) / 10;
            int ny = map.get(n) % 10;
            long l = buildNumeric(s, 1, 3, 2, nx, ny, new ArrayList<>(),25);
            sum += l * nums[0];
        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private long buildNumeric(String command, int pos, int x, int y, int nx, int ny, List<Character> curr, int limit) {
        if (pos == command.length() && x == nx && y == ny) {
            curr.add('A');
            StringBuilder sb = new StringBuilder();
            for (char ch : curr) {
                sb.append(ch);
            }
            long res = restorePrev(sb.toString(), 0, limit);
            curr.remove(curr.size() - 1);            
            return res;
        } else {
            long best = Long.MAX_VALUE;
            int count = 0;
            while (x == nx && y == ny) {
                curr.add('A');
                int num = 0;
                if (command.charAt(pos) <= '9') {
                    num = command.charAt(pos) - '0';
                } else {
                    num = 10;
                }
                nx = map.get(num) / 10;
                ny = map.get(num) % 10;   
                ++pos;  
                count++;           
            }
            if (x > nx) {
                    curr.add('^');
                    best = Math.min(best, buildNumeric(command, pos, x - 1, y, nx, ny, curr, limit));
                    curr.remove(curr.size() - 1);
            } 
            if (x < nx) {
                if (y != 0 || x + 1 != 3) {
                    curr.add('v');
                    best = Math.min(best, buildNumeric(command, pos, x + 1, y, nx, ny, curr, limit));
                    curr.remove(curr.size() - 1);
                }

            } 
            if (y > ny) {
                if (x != 3 || y - 1 > 0) {
                    curr.add('<');
                    best = Math.min(best, buildNumeric(command, pos, x, y - 1, nx, ny, curr, limit));
                    curr.remove(curr.size() - 1);  
                }
            } 
            if (y < ny) {
                curr.add('>');
                best = Math.min(best, buildNumeric(command, pos, x, y + 1, nx, ny, curr, limit));
                curr.remove(curr.size() - 1);                                  
            }
            while (count-- > 0) {
                curr.remove(curr.size() - 1);
            }

            return best;
        }
    }

    private long restorePrev(String s, int l, int limit) {
        if (memo.containsKey(s) && memo.get(s).containsKey(l)) {
            return memo.get(s).get(l);
        }
        if (l == limit) {
            return (long)s.length();
        } else {
            long res = 0;
            int start = 0;
            while (s.indexOf('A', start) >= 0) {
                String next = s.substring(start, s.indexOf('A', start) + 1);

                int num = 0;
                if (next.charAt(0) == '^') {
                    num = 1;
                } else if (next.charAt(0) == 'A') {
                    num = 5;
                } else if (next.charAt(0) == 'v') {
                    num = 3;
                } else if (next.charAt(0) == '<') {
                    num = 4;
                } else if (next.charAt(0) == '>') {
                    num = 2;
                }
                int nx = dirMap.get(num) / 10;
                int ny = dirMap.get(num) % 10; 
                res += buildDirectional(next, 1, 0, 2, nx, ny, new ArrayList<>(), l+1, limit);
                start = s.indexOf('A', start) + 1;
            }
            if (!memo.containsKey(s)) {
                memo.put(s, new HashMap<>());
            }
            memo.get(s).put(l, res);
            return res;
        }

    }

    private long buildDirectional(String command, int pos, int x, int y, int nx, int ny, List<Character> curr, int l, int limit) {
        if (pos >= command.length() && x == nx && y == ny) {
            curr.add('A');
            StringBuilder sb = new StringBuilder();
            for (char ch : curr) {
                sb.append(ch);
            }         
            long res = restorePrev(sb.toString(), l, limit);
            curr.remove(curr.size() - 1);            
            return res;
        } else {
            long best = Long.MAX_VALUE;
            int count = 0;
            while (x == nx && y == ny) {
                curr.add('A');
                int num = 0;
                if (command.charAt(pos) == '^') {
                    num = 1;
                } else if (command.charAt(pos) == 'A') {
                    num = 5;
                } else if (command.charAt(pos) == 'v') {
                    num = 3;
                } else if (command.charAt(pos) == '<') {
                    num = 4;
                } else if (command.charAt(pos) == '>') {
                    num = 2;
                }
                nx = dirMap.get(num) / 10;
                ny = dirMap.get(num) % 10; 
                ++pos;  
                ++count;          
            }
            if (x > nx) {
                if (y != 0 || x - 1 != 0) {
                    curr.add('^');
                    best = Math.min(best, buildDirectional(command, pos, x - 1, y, nx, ny, curr, l, limit));
                    curr.remove(curr.size() - 1);
                }

            } 
            if (x < nx ) {
                curr.add('v');
                best = Math.min(best, buildDirectional(command, pos, x + 1, y, nx, ny, curr, l, limit));
                curr.remove(curr.size() - 1);
            } 
            if (y > ny) {
                if (x != 0 || y - 1 != 0) {
                    curr.add('<');
                    best = Math.min(best, buildDirectional(command, pos, x, y - 1, nx, ny, curr, l, limit));
                    curr.remove(curr.size() - 1);         
                }
            } 
            if (y < ny) {
                curr.add('>');
                best = Math.min(best, buildDirectional(command, pos, x, y + 1, nx, ny, curr, l, limit));
                curr.remove(curr.size() - 1);                                  
            }
            while (count-- > 0) {
                curr.remove(curr.size() - 1);
            }

            return best;
        }
    }

    private static int[] parseInts(String s) {
        return Stream.of(s.replaceAll("[^0123456789-]", "\s").trim().split("\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
    }   
}
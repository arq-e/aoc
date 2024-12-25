package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day24 extends Day {
    public static void main(String[] args) throws IOException {
        Day24 day = new Day24();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Map<String, Integer> vals = new HashMap<>();
        int x = 0;
        for (String s : list) {
            if (s.length() == 0) {
                break;
            }
            String[] split = s.split(": ");
            vals.put(split[0], Integer.parseInt(split[1]));
            ++x;
        }
        Deque<String[]> dq = new ArrayDeque<>();
        for (int i = x + 1; i < list.size(); ++i) {
            String[] split = list.get(i).split("\s");
            if (vals.containsKey(split[0]) && vals.containsKey(split[2])) {
                operation(split, vals);
            } else {
                dq.offerFirst(split);
            }

        }
        while (dq.size() > 0) {
            String[] split = dq.pollFirst();
            if (vals.containsKey(split[0]) && vals.containsKey(split[2])) {
                operation(split, vals);
            } else {
                dq.offerLast(split);
            }       
        }

        System.out.printf("Part 1 answer is: %d\n", calcResult("z", vals));
    }

    public void solve2(List<String> list) {
        Map<String, Integer> vals = new HashMap<>();
        int p = 0;
        for (String s : list) {
            if (s.length() == 0) {
                break;
            }
            String[] split = s.split(": ");
            vals.put(split[0], Integer.parseInt(split[1]));
            ++p;
        }

        Set<String[]> rules = new HashSet<>();

        for (int i = p + 1; i < list.size(); ++i) {
            String[] split = list.get(i).split("\s");
            rules.add(new String[]{split[0], split[1], split[2], split[4]});
        }        

        Set<String> wrong = new TreeSet<>();
        for (String[] rule : rules) {
            if (rule[3].startsWith("z") && !rule[1].equals("XOR") && !rule[3].equals("z45")) {
                wrong.add(rule[3]);
            }
            if (rule[1].equals("XOR") && !rule[3].startsWith("z")
                    && !rule[0].startsWith("x") && !rule[0].startsWith("y")
                    && !rule[2].startsWith("x") && !rule[2].startsWith("y")) {
                wrong.add(rule[3]);
            }
            if (rule[1].equals("AND") && !rule[0].equals("x00") && !rule[2].equals("x00")) {
                for (String[] rule2 : rules) {
                    if ((rule[3].equals(rule2[0]) || rule[3].equals(rule2[2])) && !rule2[1].equals("OR")) {
                        wrong.add(rule[3]);
                    }
                }
            }
            if (rule[1].equals("XOR")) {
                for (String[] rule2 : rules) {
                    if ((rule[3].equals(rule2[0]) || rule[3].equals(rule2[2])) && rule2[1].equals("OR")) {
                        wrong.add(rule[3]);
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String w : wrong) {
            sb.append(w).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);    
        }

        System.out.printf("Part 2 answer is: %s\n", sb.toString());
    }

    private long calcResult(String start, Map<String, Integer> vals) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            if (vals.containsKey(start+i)) {
                sb.append(vals.get(start+i));
            } else if (vals.containsKey(start+"0"+i )) {
                sb.append(vals.get(start+"0"+i));                
            } else {
                break;
            }
            ++i;
        }

        return Long.parseLong(sb.reverse().toString(), 2);
    }

        
    private void operation(String[] split, Map<String, Integer> vals) {
        int val = 0;
        switch (split[1]) {
            case "XOR":
                val = vals.get(split[0]) ^vals.get(split[2]);
                vals.put(split[4], val);
                break;
            case "OR":
                val = vals.get(split[0]) | vals.get(split[2]);
                vals.put(split[4], val);
                break;
            case "AND":
                val = vals.get(split[0]) & vals.get(split[2]);
                vals.put(split[4], val);
                break;                
            default:
                break;
        }
    }
}

package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;
import main.utils.ParsingUtils;

import java.io.IOException;
import java.util.*;

public class Day19 extends Day {

    public static void main(String[] args) throws IOException {
        Day19 day = new Day19();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        Map<String, Workflow> workflows = new HashMap<>();
        int pos = 0;
        for (String s : list) {
            ++pos;
            if (s.length() == 0) break;
            String[] split= s.split("[{,:}]");
            workflows.put(split[0], new Workflow(split));
        }

        for (;pos < list.size(); ++pos) {
            List<Integer> stats = ParsingUtils.getInts(list.get(pos), 0);
            sum += checkRoute(stats, workflows, "in");
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        Map<String, Workflow> workflows = new HashMap<>();
        for (String s : list) {
            if (s.length() == 0) break;
            String[] split = s.split("[{,:}]");
            workflows.put(split[0], new Workflow(split));
        }

        long sum = countValidCombinations1(workflows, "in", new long[]{1, 4000, 1, 4000, 1, 4000, 1, 4000});
        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private int checkRoute(List<Integer> stats, Map<String, Workflow> workflows, String start) {
        while (!start.equals("A") && !start.equals("R")) {
            start = processWorkflow(stats, workflows.get(start), start);
        }
        int sum = 0;
        if (start.equals("A")) {
            for (int i = 0; i < stats.size(); ++i) {
                sum += stats.get(i);
            }
        }
        return sum;
    }

    private String processWorkflow(List<Integer> stats, Workflow work, String cur) {
        for (int i = 0;  i< work.seq.size(); ++i) {
            String action = work.seq.get(i);
            int num = categoryToNum(action);
            int type = getOperationType(action);
            if (type < 0) {
                return work.actions.get(action);
            } else {
                int val = Integer.parseInt(action.substring(2));            
                if (type == 0 && stats.get(num) < val) return work.actions.get(action); 
                else if (type == 1 && stats.get(num) > val) return work.actions.get(action);
            }
        }
        return "";
    }

    private long countValidCombinations1(Map<String, Workflow> workflows, String start, long[] ranges) {
        if (start.equals("A")) {
            long res = 1;
            for (int i = 0; i < 8; i += 2) {
                res *= (ranges[i+1] - ranges[i] + 1);
            }
            return res;
        } else if (start.equals("R")) {
            return 0;
        } else {
            long res = 0;
            Workflow work = workflows.get(start);
            for (int i = 0; i < work.seq.size(); ++i) {
                String action = work.seq.get(i);
                int type = getOperationType(action);
                int num = categoryToNum(action);
                if (type < 0) {
                    res += countValidCombinations1(workflows, work.actions.get(action), ranges.clone());                          
                } else {
                    int val = Integer.parseInt(action.substring(2));
                    if (type == 1 && val >= ranges[num*2]) {
                        long temp = ranges[num*2];
                        ranges[num*2] = val + 1;
                        res += countValidCombinations1(workflows, work.actions.get(action), ranges.clone());  
                        ranges[num*2] = temp;
                        ranges[num*2 + 1] = val;
                    } else if (type == 0 && val <= ranges[num*2 + 1]) {
                        long temp = ranges[num*2 + 1];
                        ranges[num*2 + 1] = val-1;
                        res += countValidCombinations1(workflows, work.actions.get(action), ranges.clone());  
                        ranges[num*2 + 1] = temp;
                        ranges[num*2] = val;                           
                    } else res += countValidCombinations1(workflows, work.actions.get(action), ranges.clone());
                }
            }
            return res;
        }
    }

    private int categoryToNum(String rule) {
        char category = rule.charAt(0);
        if (category == 'x') return 0;
        else if (category == 'm') return 1;
        else if (category == 'a') return 2;
        else if (category == 's') return 3;
        return -1;
    }

    private int getOperationType(String rule) {
        if (rule.length() > 1) {
            if(rule.charAt(1) == '<') return 0;
            else if (rule.charAt(1) == '>') return 1; 
        }
        return -1;
    }

    public class Workflow {
        Map<String, String> actions;
        List<String> seq;        

        public Workflow(String[] s) {
            actions = new HashMap<>();
            seq = new ArrayList<>();
            int pos = 1;
            while (pos < s.length) {
                if (s[pos].contains(">") || s[pos].contains("<") ) {
                    seq.add(s[pos]);
                    actions.put(s[pos], s[pos+1]);
                    pos += 2;
                } else {
                    seq.add(s[pos]);
                    actions.put(s[pos], s[pos]);
                    ++pos;
                }
            }
        }
    }
}

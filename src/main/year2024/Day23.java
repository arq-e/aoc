package main.year2024;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day23 extends Day {
    public int max = 0;
    public List<String> best = new ArrayList<>();
    public Set<String> checked = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Day23 day = new Day23();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Map<String, Set<String>> conn = new HashMap<>();
        Set<String> nodes = new HashSet<>();
        readConnections(list, conn, nodes);
        int sum = 0;

        Set<String> visited = new HashSet<>();
        for (String c : nodes) {
            if (c.startsWith("t")) {
                for (String c2 : conn.get(c)) {
                    for (String c3 : conn.get(c)) {
                        if (!visited.contains(c + c2 + c3) && conn.get(c2).contains(c3)) {
                            ++sum;
                            visited.add(c + c3 + c2);
                            visited.add(c2 + c + c3);
                            visited.add(c2 + c3 + c);
                            visited.add(c3 + c2 + c);
                            visited.add(c3 + c + c2);
                        }
                    }
                }
            }
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        max = 0;
        best.clear();
        checked.clear();
        Map<String, Set<String>> conn = new HashMap<>();
        Set<String> nodes = new HashSet<>();
        readConnections(list, conn, nodes);
        
        for (String c : nodes) {
            if (!checked.contains(c)) {
                checked.add(c);
                Set<String> active = new HashSet<>();
                Set<String> visited = new HashSet<>();
                for (String s : conn.get(c)) {
                    active.add(s);
                }
                visited.add(c);
                rec(conn, visited, active);
           }
        }

        Collections.sort(best);
        StringBuilder sb = new StringBuilder();
        for (String s : best) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        System.out.printf("Part 2 answer is: %s\n", sb.toString());
    }

    private void readConnections(List<String> list, Map<String, Set<String>> conn, Set<String> nodes) {
        for (String s : list) {
            String[] str = s.split("-");
            if (!conn.containsKey(str[0])) {
                conn.put(str[0], new HashSet<>());
            }
            if (!conn.containsKey(str[1])) {
                conn.put(str[1], new HashSet<>());
            }
            conn.get(str[0]).add(str[1]);
            conn.get(str[1]).add(str[0]);
            nodes.add(str[0]);
            nodes.add(str[1]);
        }
    }
    
    private void rec(Map<String, Set<String>> conn, Set<String> visited, Set<String> active) {
        if (active.size() == 0) {
            if (visited.size() > max) {
                max = visited.size();
                best.clear();
                best.addAll(visited);
                Collections.sort(best);
            }
            for (String c : visited) {
                checked.add(c);
            }
        } else {
            Set<String> possibleConnections = new HashSet<>();
            String bestNode = "";
            int maxPossible = -1;
            for (String c : active) {
                if (!visited.contains(c)) {
                    Set<String> temp = new HashSet<>();
                    for (String c1 : active) {
                        if (!visited.contains(c1) && conn.get(c).contains(c1)) {
                            temp.add(c1);
                        }
                    }
                    if (temp.size() > maxPossible) {
                        possibleConnections.clear();
                        possibleConnections.addAll(temp);
                        maxPossible = temp.size();
                        bestNode = c;
                    }
                }
            }
            visited.add(bestNode);
            rec(conn, visited, possibleConnections);
            visited.remove(bestNode);
        }
    }
}

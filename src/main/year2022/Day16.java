package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.IOException;
import java.util.*;

public class Day16 extends Day {

    private  Map<String,Map<String,Integer>> map;

    public static void main(String[] args) throws IOException {
        Day16 day = new Day16();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Map<String, List<String>> connections = new HashMap<>();
        Map<String, Integer> flowRates = new HashMap<>();
        convertInput(list, connections, flowRates);
        Set<String> closed = new HashSet<>();
        for (String s : flowRates.keySet()) {
            if (flowRates.get(s) > 0) closed.add(s);
        }
        calculatePaths(connections, closed);

        String start = "AA";
        int total = movePlayer(flowRates, start, null, 0, 0, closed, 29);
        System.out.println(total);
    }

    public void solve2(List<String> list) {
        Date date = new Date();
        Map<String, List<String>> connections = new HashMap<>();
        Map<String, Integer> flowRates = new HashMap<>();
        convertInput(list, connections, flowRates);
        Set<String> closed = new HashSet<>();
        for (String s : flowRates.keySet()) {
            if (flowRates.get(s) > 0) {
                closed.add(s);
            }
        }
        calculatePaths(connections, closed);

        String start = "AA";
        int total = movePlayer(flowRates, start,start, 0, 0, closed, 25);
        Date date1 = new Date();
        System.out.println(total);
        System.out.println(date1.getTime() - date.getTime());
    }

    private void calculatePaths(Map<String, List<String>> connections, Set<String> closed) {
        if (map == null) {
            map = new HashMap<>();
            for (String s : closed) {
                map.put(s, new HashMap<>());
                for (String s1 : closed) {
                    if (!s.equals(s1)) {
                        map.get(s).put(s1,checkRoute(connections, s, s1, new HashSet<>()));
                    }
                }
            }
            map.put("AA", new HashMap<>());
            for (String s : closed) {
                map.get("AA").put(s,checkRoute(connections, "AA", s, new HashSet<>()));
            }
        }
    }

    private int movePlayer(Map<String, Integer> flowRates,
                               String first,String second, int time,int time1, Set<String> closed, int maxTime) {
        if (time >= maxTime) return 0;
        Map<String,Integer> map1 = map.get(first);
        int max = 0;
        for (String s : closed) {
                if (!s.equals(second)) {
                    Set<String> closed1 = new HashSet<>(closed);
                    closed1.remove(s);
                    int sum = 0;
                    sum += ((maxTime - time - map1.get(s))*flowRates.get(s));
                    if (second != null) {
                        sum += movePlayer(flowRates, second, s, time1,time+map1.get(s)+1,closed1,maxTime);
                    } else {
                        sum += movePlayer(flowRates, s, null, time+map1.get(s)+1,time1,closed1,maxTime);
                    }
                    if (sum > max) {
                        max = sum;
                    }
                }
        }
        return max;
    }


    private int checkRoute(Map<String, List<String>> connections, String start, String end, Set<String> visited) {
        if (start.equals(end)) return 0;
        visited.add(start);
        int min = connections.size();
        for (String s : connections.get(start)) {
            if (!visited.contains(s)) {
                int curr =  1 + checkRoute(connections, s, end, new HashSet<>(visited));
                if (curr < min) min = curr;
            }
        }
        return min;
    }

    private void convertInput(List<String> list, Map<String, List<String>> connections, Map<String, Integer> flowRates) {
        for (String s : list) {
            Integer flowRate = Integer.parseInt(s.replaceAll("[^0-9]",""));
            String[] valves = s.substring(1).replaceAll("[^A-Z]"," ").trim().split(" +");
            connections.put(valves[0], new ArrayList<>());
            flowRates.put(valves[0], flowRate);
            for (int i = 1; i< valves.length; i++) {
                connections.get(valves[0]).add(valves[i]);
            }
        }
    }

}

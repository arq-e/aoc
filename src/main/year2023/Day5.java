package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day5 extends Day {

    public static void main(String[] args) throws IOException {
        Day5 day = new Day5();
    
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        long[] positions = Stream.of(list.get(0).substring(list.get(0).indexOf(":") + 2).split(" "))
                                .mapToLong(Long::parseLong).toArray();
        boolean[] visited = new boolean[positions.length];
        for (int i = 3; i < list.size(); ++i) {
            if (list.get(i).length() == 0) {
                ++i;
                Arrays.fill(visited, false);
                continue;
            }
            long[] params = Stream.of(list.get(i).split(" ")).mapToLong(Long::parseLong).toArray();

            for (int j = 0; j < positions.length; ++j) {
                if (!visited[j] && positions[j] >= params[1] && positions[j] < params[1] + params[2]) {
                    positions[j]  += (params[0] - params[1]);
                    visited[j] = true;
                }
            }
        }

        Arrays.sort(positions);
        System.out.printf("Part 1 answer is: %d\n", positions[0]);
    }

    public void solve2(List<String> list) {
        long[] startingRanges = Stream.of(list.get(0).substring(list.get(0).indexOf(":") + 2)
                                    .split(" ")).mapToLong(Long::parseLong).toArray();
       
        Map<Long, Long> active = new HashMap<>();
        for (int i = 0; i < startingRanges.length; i += 2) {
            active.put(startingRanges[i], startingRanges[i]+startingRanges[i+1]-1);
        }
        Map<Long, Long> processed = new HashMap<>();
        for (int i = 3; i < list.size(); ++i) {
            if (list.get(i).length() == 0) {
                ++i;
                active.putAll(processed);
                processed.clear();
                continue;
                
            }

            long[] params = Stream.of(list.get(i).split(" ")).mapToLong(Long::parseLong).toArray();
            long[] sourceRange = {params[1], params[1] + params[2] - 1};
            long[] destRange = {params[0], params[0] + params[2] - 1};
            
            Map<Long, Long> notProcessed = new HashMap<>();
            for (long start : active.keySet()) {
                if (sourceRange[0] > active.get(start) || sourceRange[1] < start) {
                    notProcessed.put(start, active.get(start));
                } else if (sourceRange[0] <= start && sourceRange[1] >= active.get(start).longValue()) {
                    processed.put(destRange[0] + start - sourceRange[0], destRange[0] + active.get(start) - sourceRange[0]);
                } else if (sourceRange[0] <= start && sourceRange[1] < active.get(start)) {
                    processed.put(destRange[0] + start - sourceRange[0], destRange[1]);
                    notProcessed.put(sourceRange[1] + 1, active.get(start));
                } else if (sourceRange[0] > start && sourceRange[1] >= active.get(start)){
                    processed.put(destRange[0], destRange[0] + active.get(start) - sourceRange[0]);
                    notProcessed.put(start, sourceRange[0] - 1);
                } else if (sourceRange[0] > start && sourceRange[1] < active.get(start)) {
                    processed.put(destRange[0], destRange[1]);
                    notProcessed.put(start, sourceRange[0] - 1);
                    notProcessed.put(sourceRange[1] + 1, active.get(start));
                }
            }
            active.clear();
            active.putAll(notProcessed);
        }
        active.putAll(processed);

        System.out.printf("Part 2 answer is: %d\n", active.keySet().stream().reduce(Long::min).get());
    }
}
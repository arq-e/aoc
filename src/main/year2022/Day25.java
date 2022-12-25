package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day25 extends Day {

    public static void main(String[] args) throws IOException {
        Day25 day = new Day25();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        System.out.println(calculateNumber(list));
    }

    private String calculateNumber(List<String> list) {
        long res = 0;
        for (String s : list) {
            long curRes = 0;
            long deg = 1;
            for (int i = s.length() - 1; i >= 0; i--) {
                res += addNumber(s.charAt(i), deg);
                deg *= 5;
            }
            res += curRes;
        }
        long deg = 1;
        while (res / deg > 2) {
            deg *= 5;
        }
        return buildSNAFUNumber(res, deg);
    }

    private String buildSNAFUNumber(long res, long deg) {
        if (deg==0 && res == 0) return "";
        else if (deg == 0) return null;
        StringBuilder sb =  new StringBuilder();
        String next;
        long curr;
        if (res > 0) {
            curr = res - 2*deg;
            next = buildSNAFUNumber(curr, deg/5);
            if (next != null) {
                sb.append('2');
                sb.append(next);
                return sb.toString();
            }
            curr = res - deg;
            next = buildSNAFUNumber(curr, deg/5);
            if (next != null) {
                sb.append('1');
                sb.append(next);
                return sb.toString();
            }
        }
        curr = res;
        next = buildSNAFUNumber(curr, deg/5);
        if (next != null) {
            sb.append('0');
            sb.append(next);
            return sb.toString();
        }
        if (res < 0) {
            curr = res + deg;
            next = buildSNAFUNumber(curr, deg/5);
            if (next != null) {
                sb.append('-');
                sb.append(next);
                return sb.toString();
            }
            curr = res + 2*deg;
            next = buildSNAFUNumber(curr, deg/5);
            if (next != null) {
                sb.append('=');
                sb.append(next);
                return sb.toString();
            }
        }
        return null;
    }

    private long addNumber(char symbol, long degree) {
        if (symbol == '2') return 2*degree;
        else if (symbol == '1') return degree;
        else if (symbol == '0') return 0;
        else if (symbol == '-') return -degree;
        else if (symbol == '=') return -2*degree;
        return 0;
    }

    public void solve2(List<String> list) {
        System.out.println("AoC 2022 completed!");
    }

}

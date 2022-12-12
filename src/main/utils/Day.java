package main.utils;

import java.util.List;

public abstract class Day {
    private String className;

    public Day() {
        className = this.getClass().getName();
    }

    public abstract void solve1(List<String> list);

    public abstract void solve2(List<String> list);

    public int getDay() {
        return Integer.parseInt(className.substring(className.lastIndexOf(".")+4));
    }

    public int getYear() {
        return Integer.parseInt(className.substring(className.lastIndexOf(".")-4
                ,className.lastIndexOf(".")));
    }

    private char[][] convertInputToCharArray(List<String> list) {
        char[][] input = new char[list.size()][list.get(0).length()];

        for (int i = 0; i < list.size(); i++) {
            input[i] = list.get(i).toCharArray();
        }
        return input;
    }
}

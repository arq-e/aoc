package main.utils;

public abstract class Day {
    private String className;

    public Day() {
        className = this.getClass().getName();
    }

    public int getDay() {
        return Integer.parseInt(className.substring(className.lastIndexOf(".")+4));
    }

    public int getYear() {
        return Integer.parseInt(className.substring(className.lastIndexOf(".")-4
                ,className.lastIndexOf(".")));
    }
}

package main.utils;

public class ArraysUtils {
    
    public static boolean inRange(int x, int y, char[][] arr) {
        return x >= 0 && x < arr.length && y >= 0 && y < arr[0].length;
    }    
}

package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.List;

public class Day8 extends Day {

    public static void main(String[] args) throws IOException{
        Day8 day = new Day8();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum =  0;

        int[][] visible = findVisibleTrees(list);

        for (int[] ints : visible) {
            for (int j = 0; j < visible[0].length; j++) {
                if (ints[j] >= 1) {
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {

        int[][] trees = convertInput(list);
        int[][] visible = findVisibleTrees(list);

        int max = 0;
        for (int i = 1; i < visible.length-1;i++) {
            for (int j = 1; j < trees[0].length-1;j++) {
                if (visible[i][j] >= 1) {
                    int res = 1;
                    int k = i-1;
                    int curr = 0;
                    while (k >= 0) {
                        curr++;
                        if (trees[k][j] < visible[i][j]) {
                            k--;
                        } else break;
                    }
                    res *= curr;

                    k = i+1;
                    curr = 0;
                    while (k < visible.length) {
                        curr++;
                        if (trees[k][j] < visible[i][j]) {
                            k++;
                        } else break;
                    }
                    res *= curr;

                    k = j+1;
                    curr = 0;
                    while (k < visible[0].length) {
                        curr++;
                        if (trees[i][k] < visible[i][j]) {
                            k++;
                        } else break;
                    }
                    res *= curr;

                    k = j-1;
                    curr = 0;
                    while (k >= 0) {
                        curr++;
                        if (trees[i][k] < visible[i][j]) {
                            k--;
                        } else break;

                    }
                    res *= curr;

                    if (res > max) max = res;
                }
            }
        }
        System.out.println(max);
    }

    private int[][] convertInput(List<String> list) {
        int[][] trees = new int[list.size()][list.get(0).length()];
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            for (int j = 0; j < list.get(i).length(); j++) {
                trees[i][j] = s.charAt(j);
            }
        }

        return trees;
    }

    private int[][] findVisibleTrees(List<String> list) {
        int[][] trees = convertInput(list);

        int[][] visible = new int[trees.length][trees[0].length];
        for (int i = 0; i < trees.length;i++) {
            visible[i][0] = 1;
            visible[i][trees[0].length-1] = 1;
        }
        for (int i = 0; i < trees[0].length;i++) {
            visible[0][i] = 1;
            visible[trees.length-1][i] = 1;
        }

        for (int i = 1; i < trees.length-1;i++) {
            int left = trees[i][0];
            for (int j = 1; j < trees[0].length-1;j++) {
                if (trees[i][j] > left) {
                    visible[i][j] = trees[i][j];
                    left = trees[i][j];
                }
            }
            int right = trees[i][trees[0].length-1];
            for (int j = trees[0].length-2; j > 0;j--) {
                if (trees[i][j] > right) {
                    visible[i][j] = trees[i][j];
                    right = trees[i][j];
                }
            }
        }

        for (int i = 1; i < trees[0].length-1;i++) {
            int upper = trees[0][i];
            for (int j = 1; j < trees.length-1;j++) {
                if (trees[j][i] > upper) {
                    visible[j][i] = trees[j][i];
                    upper = trees[j][i];
                }
            }
            int down = trees[trees.length-1][i];
            for (int j = trees.length - 2; j > 0;j--) {
                if (trees[j][i] > down) {
                    visible[j][i] = trees[j][i];
                    down = trees[j][i];
                }
            }
        }

        return visible;
    }
}

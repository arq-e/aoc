package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.List;

public class day8 {

    public static void main(String[] args) throws IOException {
        List<String> input = AdventInputReader.getInput(2022, 8);

        part1(input);
        part2(input);
    }

    public static void part1(List<String> input) {
        int sum =  0;
        int[][] trees = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            for (int j = 0; j < input.get(i).length(); j++) {
                trees[i][j] = s.charAt(j);
            }
        }


        int[][] accept = new int[trees.length][trees[0].length];
        for (int i = 0; i < trees.length;i++) {
            accept[i][0] = 1;
            accept[i][trees[0].length-1] = 1;
        }
        for (int i = 0; i < trees[0].length;i++) {
            accept[0][i] = 1;
            accept[trees.length-1][i] = 1;
        }

        for (int i = 1; i < trees.length-1;i++) {
            int left = trees[i][0];
            for (int j = 1; j < trees[0].length-1;j++) {
                if (trees[i][j] > left) {
                    accept[i][j] = 1;
                    left = trees[i][j];
                }
            }
            int right = trees[i][trees[0].length-1];
            for (int j = trees[0].length-2; j > 0;j--) {
                if (trees[i][j] > right) {
                    accept[i][j] = 1;
                    right = trees[i][j];
                }
            }
        }

        for (int i = 1; i < trees[0].length-1;i++) {
            int upper = trees[0][i];
            for (int j = 1; j < trees.length-1;j++) {
                if (trees[j][i] > upper) {
                    accept[j][i] = 1;
                    upper = trees[j][i];
                }
            }
            int down = trees[trees.length-1][i];
            for (int j = trees.length - 2; j > 0;j--) {
                if (trees[j][i] > down) {
                    accept[j][i] = 1;
                    down = trees[j][i];
                }
            }
        }

        for (int i = 0; i < input.size();i++) {
            for (int j = 0; j < trees[0].length;j++) {
                if (accept[i][j] >= 1) {
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }

    public static void part2(List<String> input) {
        int sum =  0;
        int[][] trees = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            for (int j = 0; j < input.get(i).length(); j++) {
                trees[i][j] = s.charAt(j);
            }
        }

        int[][] accept = new int[trees.length][trees[0].length];
        for (int i = 0; i < trees.length;i++) {
            accept[i][0] = 1;
            accept[i][trees[0].length-1] = 1;
        }
        for (int i = 0; i < trees[0].length;i++) {
            accept[0][i] = 1;
            accept[trees.length-1][i] = 1;
        }

        for (int i = 1; i < trees.length-1;i++) {
            int left = trees[i][0];
            for (int j = 1; j < trees[0].length-1;j++) {
                if (trees[i][j] > left) {
                    accept[i][j] = trees[i][j];
                    left = trees[i][j];
                }
            }
            int right = trees[i][trees[0].length-1];
            for (int j = trees[0].length-2; j > 0;j--) {
                if (trees[i][j] > right) {
                    accept[i][j] = trees[i][j];
                    right = trees[i][j];
                }
            }
        }

        for (int i = 1; i < trees[0].length-1;i++) {
            int upper = trees[0][i];
            for (int j = 1; j < trees.length-1;j++) {
                if (trees[j][i] > upper) {
                    accept[j][i] = trees[j][i];
                    upper = trees[j][i];
                }
            }
            int down = trees[trees.length-1][i];
            for (int j = trees.length - 2; j > 0;j--) {
                if (trees[j][i] > down) {
                    accept[j][i] = trees[j][i];
                    down = trees[j][i];
                }
            }
        }

        int max = 0;
        for (int i = 1; i < input.size()-1;i++) {
            for (int j = 1; j < trees[0].length-1;j++) {
                if (accept[i][j] >= 1) {
                    int res = 1;
                    int k = i-1;
                    int curr = 0;
                    while (k >= 0) {
                        curr++;
                        if (trees[k][j] < accept[i][j]) {
                            k--;
                        } else break;
                    }
                    res *= curr;

                    k = i+1;
                    curr = 0;
                    while (k < accept.length) {
                        curr++;
                        if (trees[k][j] < accept[i][j]) {
                            k++;
                        } else break;
                    }
                    res *= curr;

                    k = j+1;
                    curr = 0;
                    while (k < accept[0].length) {
                        curr++;
                        if (trees[i][k] < accept[i][j]) {
                            k++;
                        } else break;
                    }
                    res *= curr;

                    k = j-1;
                    curr = 0;
                    while (k >= 0) {
                        curr++;
                        if (trees[i][k] < accept[i][j]) {
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
}

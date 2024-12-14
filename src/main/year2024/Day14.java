package main.year2024;

import main.utils.AdventInputReader;

import main.utils.Day;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day14 extends Day {
    int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    static int lx;
    static int ly;

    public static void main(String[] args) throws IOException {
        Day14 day = new Day14();

        lx = 101;
        ly = 103;
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        List<Robot> robots = new ArrayList<>();
        for (String s : list) {
            int[] nums = parseInts(s);
            Robot robot = new Robot();
            robot.x = nums[0];
            robot.y = nums[1];
            robot.dx = nums[2];
            robot.dy = nums[3];
            robots.add(robot);
        }

        for (int i = 0; i < 100; ++i) {
            for (Robot robot : robots) {
                int nx = robot.x + robot.dx;
                int ny = robot.y + robot.dy;
                if (nx < 0) {
                    nx += lx;
                }
                if (nx >= lx) {
                    nx -= lx;
                }
                if (ny < 0) {
                    ny += ly;
                }
                if (ny >= ly) {
                    ny -= ly;
                }
                robot.x = nx;
                robot.y = ny;
            }
        }
        int[] nums = new int[4];
        for (Robot r : robots) {
            if (r.x < lx / 2 && r.y < ly / 2) {
                nums[0]++;
            } else if (r.x < lx / 2 && r.y > ly / 2) {
                nums[1]++;
            } else if (r.x > lx / 2 && r.y < ly / 2) {
                nums[2]++;
            } else if (r.x > lx / 2 && r.y > ly / 2) {
                nums[3]++;
            }
        }

        sum = nums[0];
        for (int i = 1; i < 4; ++i) {
            sum *= nums[i];
        }

        System.out.printf("Part 1 answer is: %d\n", sum);
    }

    public void solve2(List<String> list) {
        int sum = 0;
        List<Robot> robots = new ArrayList<>();
        for (String s : list) {
            int[] nums = parseInts(s);
            Robot robot = new Robot();
            robot.x = nums[0];
            robot.y = nums[1];
            robot.dx = nums[2];
            robot.dy = nums[3];
            robots.add(robot);
        }

        for (int i = 0; i < 10000; ++i) {
            for (Robot robot : robots) {
                int nx = robot.x + robot.dx;
                int ny = robot.y + robot.dy;
                if (nx < 0) {
                    nx += lx;
                }
                if (nx >= lx) {
                    nx -= lx;
                }
                if (ny < 0) {
                    ny += ly;
                }
                if (ny >= ly) {
                    ny -= ly;
                }
                robot.x = nx;
                robot.y = ny;
            }
            int[][] map = new int[ly][lx];
            boolean invalid = false;
            for (Robot r : robots) {
                map[r.y][r.x]++;
                if (map[r.y][r.x] > 1) {
                    invalid = true;
                }
            }
    
            if (!invalid) {
                sum = i + 1;
                for (int k = 0; k < map.length; ++k) {
                    for (int j = 0; j < map[0].length; ++j) {
                        if (map[k][j] == 0) {
                            System.out.print('.');
                        } else {
                            System.out.print('*');
                        }
                    }
                    System.out.println();
                }
                break;
            }

        }

        System.out.printf("Part 2 answer is: %d\n", sum);
    }

    private static int[] parseInts(String s) {
        return Stream.of(s.replaceAll("[^0123456789-]", "\s").trim().split("\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
    }

    class Robot{
        int x;
        int y;
        int dx;
        int dy;
    }
}

package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;

public class Day19 extends Day {

    public static void main(String[] args) throws IOException {
        Day19 day = new Day19();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;
        List<Blueprint> blueprints = convertInput(list);
        for (Blueprint blueprint : blueprints) {
            int geodes = collectGeodesBFS(blueprint, 24);
            sum += blueprint.getNumber()*geodes;
        }
        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        int res = 1;
        List<Blueprint> blueprints = convertInput(list);
        for (Blueprint blueprint : blueprints) {
            int geodes = collectGeodesBFS(blueprint, 32);
            res *= geodes;
            if (blueprint.getNumber() >= 3) break;
        }
        System.out.println(res);
    }

    private int collectGeodes(Blueprint blueprint, int time, int[] resources, int[] workingRobots, int endTime) {
        if (time >= endTime) {
            return resources[3];
        }
        int max = 0;
        boolean[] doNotBuild = new boolean[]{false,false,false,false};
        for (int i = 2; i < 4; i++) {
            if (workingRobots[i-1] == 0) doNotBuild[i] = true;
        }
        for (int i = 0; i < 3; i++) {
            if (isEnough(blueprint, workingRobots[i], i)) {
                doNotBuild[i] = true;
            }
        }
        while (true) {
            for (int i = 0; i < 4; i++) {
                if (!doNotBuild[i] && canBuildRobot(resources,blueprint, i)) {
                    doNotBuild[i] = true;
                    int[] resources1 = buildRobot(resources, blueprint,i);
                    for (int j = 0; j < 4;j++) {
                        resources1[j] += workingRobots[j];
                    }
                    int[] workingRobots1 = Arrays.copyOf(workingRobots,4);
                    ++workingRobots1[i];
                    int count = collectGeodes(blueprint, time+1, resources1, workingRobots1, endTime);
                    if (count > max) max = count;
                }
            }
            boolean allRobotsBuilt = true;
            for (int i = 0; i < 4; i++) {
                allRobotsBuilt &= doNotBuild[i];
            }
            if (allRobotsBuilt) break;

            ++time;
            for (int i = 0; i < 4;i++) {
                resources[i] += workingRobots[i];
            }
            if (time >= 24) break;
        }
        return  Math.max(max,resources[3]);
    }

    private boolean isEnough(Blueprint blueprint, int robots, int type) {
        if (type == 2) {
            return robots >= blueprint.getGeodeRobotCost()[1];
        } else if (type == 1) {
            return robots >= blueprint.getObsidianRobotCost()[1];
        } else if (type == 0) {
            return robots >= blueprint.getOreRobotCost() && robots >= blueprint.getClayRobotCost()
                    && robots >= blueprint.getObsidianRobotCost()[0] && robots >= blueprint.getGeodeRobotCost()[0];
        }
        return false;
    }

    private int collectGeodesBFS(Blueprint blueprint, int endTime) {
        Deque<int[][]> deque = new ArrayDeque<>();
        deque.add(new int[][]{{0}, new int[]{1,0,0,0}, new int[]{0,0,0,0}});
        int[] enoughRobots = new int[]{isEnough(blueprint,0),isEnough(blueprint,1),isEnough(blueprint,2)};
        int max = 0;
        while (deque.size() > 0) {
            int[][] state = deque.pollLast();
            int time = state[0][0];
            boolean[] triedRobot = new boolean[]{false,false,false,false};
            for (int i = 2; i < 4; i++) {
                if (state[1][i-1] == 0) triedRobot[i] = true;
            }
            for (int i = 0; i < 3; i++) {
                if (state[1][i] >= enoughRobots[i]) triedRobot[i] = true;
            }
            boolean build4 = false;
            while (time < endTime) {
                for (int i = 3; i >=0; i--) {
                    if (!triedRobot[i] && canBuildRobot(state[2],blueprint,i)) {
                        triedRobot[i] = true;
                        int[] resources1 = buildRobot(state[2], blueprint,i);
                        for (int j = 0; j < 4;j++) {
                            resources1[j] += state[1][j];
                        }
                        int[] workingRobots1 = Arrays.copyOf(state[1],4);
                        ++workingRobots1[i];
                        int[][] newState = new int[][]{{time+1}, workingRobots1, resources1};
                        deque.addFirst(newState);
                        if (i == 3) {
                            build4 = true;
                            break;
                        }
                    }
                }
                boolean allRobotsBuilt = true;
                for (int i = 0; i < 4; i++) {
                    allRobotsBuilt &= triedRobot[i];
                }
                if (allRobotsBuilt || build4) break;

                ++time;
                for (int j = 0; j < 4;j++) {
                    state[2][j] += state[1][j];
                }
            }
            if (state[2][3] > max) max = state[2][3];
        }
        return max;
    }

    private int isEnough(Blueprint blueprint, int type) {
        if (type == 2) {
            return blueprint.getGeodeRobotCost()[1];
        } else if (type == 1) {
            return blueprint.getObsidianRobotCost()[1];
        } else if (type == 0) {
            int max = blueprint.getOreRobotCost();
            max = Math.max(max, blueprint.getClayRobotCost());
            max = Math.max(max, blueprint.getObsidianRobotCost()[0]);
            return Math.max(max, blueprint.getGeodeRobotCost()[0]);
        }
        return -1;
    }

    private int[] buildRobot(int[] resources, Blueprint blueprint, int robot) {
        int[] resources1 = Arrays.copyOf(resources, 4);
        if (robot == 0) {
            resources1[0] -= blueprint.getOreRobotCost();
        } else if (robot == 1) {
            resources1[0] -= blueprint.getClayRobotCost();
        } else if (robot == 2) {
            resources1[0] -= blueprint.getObsidianRobotCost()[0];
            resources1[1] -= blueprint.getObsidianRobotCost()[1];
        } else if (robot == 3) {
            resources1[0] -= blueprint.getGeodeRobotCost()[0];
            resources1[2] -= blueprint.getGeodeRobotCost()[1];
        }
        return resources1;
    }

    private boolean canBuildRobot(int[] resources, Blueprint blueprint, int robot) {
        if (robot == 3 && resources[0] >= blueprint.getGeodeRobotCost()[0]
                && resources[2] >= blueprint.getGeodeRobotCost()[1]) {
            return true;
        } else if (robot == 2 && resources[0] >= blueprint.getObsidianRobotCost()[0]
                && resources[1] >= blueprint.getObsidianRobotCost()[1]) {
            return true;
        } else if (robot == 1 && resources[0] >= blueprint.getClayRobotCost()) {
            return true;
        }else if (robot == 0 && resources[0] >= blueprint.getOreRobotCost()) {
            return true;
        }
        return false;
    }

    private List<Blueprint> convertInput(List<String> list) {
        List<Blueprint> blueprints = new ArrayList<>();
        for (String s : list) {
            String[] numStrs = s.replaceAll("[^0-9]", " ").trim().split(" +");
            int number = Integer.parseInt(numStrs[0]);
            int oreCost = Integer.parseInt(numStrs[1]);
            int clayCost = Integer.parseInt(numStrs[2]);
            int[] obsidianCost = new int[]{Integer.parseInt(numStrs[3]), Integer.parseInt(numStrs[4])};
            int[] geodeCost = new int[]{Integer.parseInt(numStrs[5]), Integer.parseInt(numStrs[6])};
            blueprints.add(new Blueprint(number, oreCost, clayCost, obsidianCost, geodeCost));
        }
        return blueprints;
    }

    public class Blueprint {
        private final int number;
        private final int oreRobotCost;
        private final int clayRobotCost;
        private final int[] obsidianRobotCost;
        private final int[] geodeRobotCost;

        public Blueprint(int number, int oreCost, int clayCost, int[] obsidianCost, int[] geodeCost) {
            this.number = number;
            this.oreRobotCost = oreCost;
            this.clayRobotCost = clayCost;
            this.obsidianRobotCost = obsidianCost;
            this.geodeRobotCost = geodeCost;
        }

        public int getNumber() {
            return number;
        }

        public int getOreRobotCost() {
            return oreRobotCost;
        }

        public int getClayRobotCost() {
            return clayRobotCost;
        }

        public int[] getObsidianRobotCost() {
            return obsidianRobotCost;
        }

        public int[] getGeodeRobotCost() {
            return geodeRobotCost;
        }
    }

}

package main.year2022;

import main.utils.AdventInputReader;
import main.utils.Day;

import java.io.IOException;
import java.util.*;


public class Day7 extends Day {

    public static void main(String[] args) throws IOException{
        Day7 day = new Day7();
        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        int sum = 0;

        List<Directory> dirList = convertInput(list);

        for (Directory dir : dirList) {
            int size = dir.getSize();
            if (size <= 100000) sum += size;
        }

        System.out.println(sum);
    }

    public void solve2(List<String> list) {
        int space = 40000000;

        List<Directory> dirList = convertInput(list);

        List<Integer> dirSizes = new ArrayList<>();
        for (Directory dir: dirList) {
            dirSizes.add(dir.getSize());
        }
        int sumToRemove = dirSizes.get(0) - space;
        Collections.sort(dirSizes);

        int sumRemoved = 0;
        for (Integer j : dirSizes){
            if (j >= sumToRemove) {
                sumRemoved = j;
                break;
            }
        }
        System.out.println(sumRemoved);
    }

    private List<Directory> convertInput(List<String> list) {
        Stack<Directory> dirStack = new Stack<>();
        Directory activeDir = new Directory(" ");
        List<Directory> dirList = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith("$ cd")) {
                String adress = s.substring(5);
                if (adress.equals("..")) {
                    dirStack.pop();
                    activeDir = dirStack.peek();
                } else {
                    if (adress.equals("/")) {
                        Directory dir = new Directory("/");
                        activeDir = dir;
                        dirList.add(dir);
                        dirStack.add(dir);
                    } else {
                        for (Directory dir : activeDir.dirs) {
                            if (dir.name.equals(adress)) {
                                activeDir = dir;
                                dirList.add(dir);
                                dirStack.add(dir);
                                break;
                            }
                        }
                    }
                }
            } else if (s.startsWith("$ ls")) {
                continue;
            } else if (s.startsWith("dir")) {
                Directory childDir = new Directory(s.substring(4));
                activeDir.dirs.add(childDir);
            } else {
                activeDir.files.put(s.substring(s.indexOf(' ')+1),
                        Integer.parseInt(s.substring(0, s.indexOf(' '))));
            }
        }
        return  dirList;
    }

    public class Directory {
        String name;
        List<Directory> dirs;
        Map<String, Integer> files;
        int size;

        public Directory(String name) {
            this.name = name;
            dirs = new ArrayList<>();
            files = new HashMap<>();
            size = 0;
        }

        public int getSize() {
            size = 0;
            if (dirs.size() != 0) {
                for (Directory dir : dirs) {
                    size += dir.getSize();
                }
            }
            if (files.size() != 0) {
                for (String file: files.keySet()) {
                    size += files.get(file);
                }
            }
            return size;
        }
    }
}



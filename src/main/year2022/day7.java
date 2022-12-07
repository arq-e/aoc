package main.year2022;

import main.AdventInputReader;

import java.io.IOException;
import java.util.*;


public class day7 {
    public static void main(String[] args) throws IOException {
        List<String> input = AdventInputReader.getInput(2022, 7);

        part1(input);
        part2(input);
    }

    public static void part1(List<String> list) {
        int sum = 0;
        Map<String, List<String>> dirs = new HashMap<>();
        dirs.put("/", new ArrayList<>());
        Stack<Directory> dirStack = new Stack<>();
        Directory activeDir = new Directory(" ");
        Set<Directory> dirsSet = new HashSet<>();
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
                        dirsSet.add(dir);
                        dirStack.add(dir);
                    } else {
                        for (Directory dir : activeDir.dirs) {
                            if (dir.name.equals(adress)) {
                                activeDir = dir;
                                dirsSet.add(dir);
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

        for (Directory dir : dirsSet) {
            int size = dir.getSize();
            if (size <= 100000) sum += size;
        }

        System.out.println(sum);
    }

    public static void part2(List<String> list) {
        int sum = 0;
        int space = 40000000;

        Map<String, Integer> files = new HashMap<>();
        Map<String, List<String>> dirs = new HashMap<>();
        dirs.put("/", new ArrayList<>());
        Stack<Directory> dirStack = new Stack<>();
        Directory activeDir = new Directory(" ");
        List<Directory> dirsSet = new ArrayList<>();
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
                        dirsSet.add(dir);
                        dirStack.add(dir);
                    } else {
                        for (Directory dir : activeDir.dirs) {
                            if (dir.name.equals(adress)) {
                                activeDir = dir;
                                dirsSet.add(dir);
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

        List<Integer> dirSizes = new ArrayList<>();
        int i = 0;
        for (Directory dir: dirsSet) {
            dirSizes.add(dir.getSize());
        }
        sum = dirSizes.get(0);
        int sumToRemove = dirSizes.get(0) - space;
        Collections.sort(dirSizes);

        int sumRemoved = 0;
        for (int j = 0; j  < dirSizes.size();j++){
            if (dirSizes.get(j) >= sumToRemove) {
                sumRemoved = dirSizes.get(j);
                break;
            }
        }
        System.out.println(sumRemoved);
    }
}

class Directory {
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

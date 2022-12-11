package main.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TemplatesGenerator {

    public static void main(String[] args) throws IOException{
        TemplatesGenerator tf = new TemplatesGenerator();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Year:");
        int year = sc.nextInt();
        tf.fill(year);
    }

    public void fill(int year) throws IOException {

        Path dir = Paths.get(System.getProperty("user.dir")+"/src/main/year" + year);
        if (!dir.toFile().exists()){
            Files.createDirectory(dir);
        }
        List<String> list = new ArrayList<>();
        Path template = Paths.get(System.getProperty("user.dir")+"/src/main/template/Day0.java");
        if (!template.toFile().exists()) System.out.println("Template doesn't found!");
        else list = Files.readAllLines(template);
        for (int j = 0; j < list.size();j++) {
            list.add(0,list.get(0).replaceAll("main.template", "main.year"+year));
            list.remove(1);
        }

        for (int i = 1; i < 26; i++) {
            Path file =  Paths.get(dir.toString()+"/Day" + i +".java");
            if (!file.toFile().exists()){
                Files.createFile(file);
                FileWriter fileWriter = new FileWriter(String.valueOf(file));
                for (String s : list) {
                    fileWriter.append(s.replaceAll("Day0", "Day" + i));
                    fileWriter.append("\n");
                }
                fileWriter.close();
            }
        }
    }

}

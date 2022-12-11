package main.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class AdventInputReader {

    public static List<String> getInput(int year, int day) throws IOException{

        Path path = Paths.get(System.getProperty("user.dir")+"/inputs/"+year+"/input"+ day + ".txt");
        List<String> inputStrings = new ArrayList<>();
        try (InputStream is = Files.newInputStream(path)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line = bufferedReader.readLine();
            do {
                inputStrings.add(line);
                line = bufferedReader.readLine();
            } while (line != null);
        } catch (IOException e) {
            inputStrings = downloadInput(year, day);
        }

        return inputStrings;
    }

    public static List<String> downloadInput(int year, int day) throws IOException {
        if (year < 2015 || year > Year.now().getValue()
                || year == Year.now().getValue() && MonthDay.now().getMonth().getValue() != 12) {
            System.out.println("Invalid Year!");
            return null;
        } else if (day < 1 || day > 25 || (year == Year.now().getValue() && day > MonthDay.now().getDayOfMonth())) {
            System.out.println("Invalid Day!");
            return null;
        }


        Path path = Paths.get(findOrCreateInputDir(year) + "/input" +day +".txt");

        if (!path.toFile().exists()) {
            URL url = new URL("https://adventofcode.com/" + year + "/day/"
                    + day + "/input");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Cookie","session="
                    + Files.readString(Path.of(System.getProperty("user.dir")+"/session_id")));
            connection.setRequestProperty("User-Agent", "github.com/arq-e/aoc aragwaith@gmail.com");

            Files.copy(connection.getInputStream(), path);
        }

        return Files.readAllLines(path);
    }

    public static String findOrCreateInputDir(int year) throws IOException{
        Path dir = Paths.get(System.getProperty("user.dir")+"/inputs/");
        if (!dir.toFile().exists()){
            Files.createDirectory(dir);
        }
        dir = Paths.get(System.getProperty("user.dir")+"/inputs/"+year +"/");
        if (!dir.toFile().exists()){
            Files.createDirectory(dir);
        }
        return dir.toString();
    }

}

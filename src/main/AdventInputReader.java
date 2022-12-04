package main;

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

    static public List<String> getInput(int year, int dayOfMonth) throws IOException{
        return readInput(year, dayOfMonth);
    }

    static public List<String> readInput(int year, int day) throws IOException{
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
            inputStrings = downoloadInput(year, day);
        }

        return inputStrings;
    }


    static public List<String> downoloadInput(int year, int day) throws IOException {
        if (year < 2015 || year > Year.now().getValue()
                || year == Year.now().getValue() && MonthDay.now().getMonth().getValue() != 12) {
            System.out.println("Invalid Year!");
            return null;
        } else if (day < 1 || day > 25 || (year == Year.now().getValue() && day > MonthDay.now().getDayOfMonth())) {
            System.out.println("Invalid Day!");
            return null;
        }

        Path path = Paths.get(System.getProperty("user.dir")+"/inputs/"+year +"/input" +day +".txt");
        if (!path.toFile().exists()) {
            URL url = new URL("https://adventofcode.com/" + year + "/day/"
                    + day + "/input");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Cookie","session="
                    + Files.readString(Path.of(System.getProperty("user.dir")+"/session_id")));

            Files.copy(connection.getInputStream(), path);
        }

        return Files.readAllLines(path);
    }

    static public void createYearDir(int year) throws IOException{
        Path dir = Paths.get(System.getProperty("user.dir")+"/inputs/"+year +"/");
        if (!dir.toFile().exists()){
            Files.createDirectory(dir);
        }
    }

}

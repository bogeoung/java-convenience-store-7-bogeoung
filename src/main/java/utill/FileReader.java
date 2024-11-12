package utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String> parseMdFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath));
        List<String> contents = readAll(reader);
        reader.close();
        return contents;
    }

    private static List<String> readAll(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            lines.add(line);
        }
        return lines;
    }
}

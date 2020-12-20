package net.programmer.igoodie.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class FileUtilities {

    public static String stringFromFile(File file) throws IOException {
        return stringFromFile(file, StandardCharsets.UTF_8);
    }

    public static String stringFromFile(File file, Charset charset) throws IOException {
        return Files.lines(file.toPath(), charset)
                .collect(Collectors.joining("\r\n"));
    }

    public static void writeToFile(String text, File file) throws IOException {
        if (!file.exists()) {
            System.out.println(file);
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(text);
        printWriter.close();
    }

}

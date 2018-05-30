package tk.roydgar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

    public static void printSourceFile(String fileName){
        int lineCounter = 1;
        try (BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()))) {
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println((lineCounter++) + "\t" + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

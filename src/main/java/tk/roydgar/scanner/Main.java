package tk.roydgar.scanner;

import java.io.IOException;

import static tk.roydgar.scanner.constants.Constants.SOURCE_FILE_NAME;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new Scanner(SOURCE_FILE_NAME).run().getTokens());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

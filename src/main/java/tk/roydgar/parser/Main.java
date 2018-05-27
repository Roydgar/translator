package tk.roydgar.parser;

import tk.roydgar.scanner.Scanner;
import tk.roydgar.scanner.constants.Constants;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            System.out.println(new Scanner(Constants.SOURCE_FILE_NAME).run().getTokens());
            new Parser(new Scanner(Constants.SOURCE_FILE_NAME).run()).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

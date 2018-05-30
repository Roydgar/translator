package tk.roydgar.parser;

import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.Scanner;
import tk.roydgar.scanner.constants.Constants;

public class Main {
    public static void main(String[] args){
        try {
            InfoTables infoTables = new Scanner(Constants.SOURCE_FILE_NAME).run();
            if (infoTables.getScannerErrors().isEmpty()) {
                infoTables = new Parser(infoTables).run();
            }
            printTables(infoTables);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printTables(InfoTables tables) {
        System.out.println("---------\nTokens:\n");
        System.out.println(tables.getTokens());
        System.out.println("---------\nIdentifier table:\n");
        System.out.println(tables.getIdnTab());
        System.out.println("---------\nConstant table:\n");
        System.out.println(tables.getConstTab() + "\n\n");

        if (!tables.getScannerErrors().isEmpty()) {
            System.out.println("---------\nErrors:\n");
            System.out.println(tables.getScannerErrors() + "\n\n");
        }


        if (!tables.getParserErrors().isEmpty()) {
            System.out.println("---------\nParser errors:\n");
            System.out.println(tables.getParserErrors());
        }

        System.out.println("--------\nTree");
        tables.getParserTree().print();


    }
}

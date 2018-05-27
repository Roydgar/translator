package tk.roydgar;

import tk.roydgar.generator.CodeGenerator;
import tk.roydgar.parser.Parser;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.Scanner;
import tk.roydgar.scanner.constants.Constants;

import java.io.IOException;

public class Translator {

    private String sourceFileName;

    public Translator(String filename) {
        this.sourceFileName = filename;
    }

    public void run() throws IOException {
        InfoTables infoTables = new Scanner(sourceFileName).run();

        if (!infoTables.getErrors().isEmpty()) {
            System.err.println(infoTables.getErrors());
            return;
        }

        Parser parser = new Parser(infoTables);
        parser.run();

        if (!parser.getErrors().isEmpty()) {
            System.err.println(parser.getErrors());
            return;
        }

        new CodeGenerator(parser.getTree(), Constants.SOURCE_FILE_NAME).run();
    }

    public static void main(String[] args){
        try {
            new Translator(Constants.SOURCE_FILE_NAME).run();
        } catch (IOException fileNotFound) {
            fileNotFound.printStackTrace();
        }
    }
}

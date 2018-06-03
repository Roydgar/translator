package tk.roydgar;

import tk.roydgar.generator.CodeGenerator;
import tk.roydgar.parser.Parser;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.Scanner;
import tk.roydgar.scanner.constants.Constants;

import java.io.IOException;

public class Translator {

    public void run(String sourceFileName) throws IOException {
        InfoTables infoTables = new Scanner(sourceFileName).run();

        if (!infoTables.getScannerErrors().isEmpty()) {
            throw new TranslatorException(infoTables.getScannerErrors());
        }

        new Parser(infoTables).run();

        if (!infoTables.getParserErrors().isEmpty()) {
            throw new TranslatorException(infoTables.getParserErrors());
        }

        new CodeGenerator(infoTables).run();

        if (!infoTables.getGeneratorErrors().isEmpty()) {
            throw new TranslatorException(infoTables.getGeneratorErrors());
        }
    }

    public static void main(String[] args){
        try {
            new Translator().run(Constants.SOURCE_FILE_NAME);
        } catch (IOException fileNotFound) {
            fileNotFound.printStackTrace();
        }
    }
}

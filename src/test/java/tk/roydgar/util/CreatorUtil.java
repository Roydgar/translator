package tk.roydgar.util;

import tk.roydgar.Translator;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.generator.CodeGenerator;
import tk.roydgar.parser.Parser;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.Scanner;

import java.io.IOException;

import static tk.roydgar.util.FileUtil.printSourceFile;

public class CreatorUtil {
    public static InfoTables runParser(String sourceFileName) {
        try {
            Parser parser = new Parser(new Scanner(sourceFileName).run());
            return parser.run();
        } catch (IOException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        }
    }

    public static InfoTables runScanner(String sourceFileName) {
        try {
            Scanner scanner = new Scanner(sourceFileName);
            return scanner.run();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public static CodeGenerator createCodeGenerator(String sourceFileName) {
        try {
            return new CodeGenerator(new Parser(new Scanner(sourceFileName).run()).run());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Translator createTranslator() {
        return new Translator();
    }
}

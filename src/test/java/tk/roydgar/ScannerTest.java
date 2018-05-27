package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.scanner.Scanner;
import tk.roydgar.scanner.constants.Constants;
import tk.roydgar.scanner.constants.ErrorMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ScannerTest {

    private void printSourceFile(String fileName) throws IOException{
        int lineCounter = 1;
        try (BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()))) {
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println((lineCounter++) + "\t" + s);
            }
        }
    }

    private Scanner createScanner(String sourceFileName) {
        try {
            printSourceFile(SourceFileNames.CORRECT);
            Scanner scanner = new Scanner(sourceFileName);
            scanner.run();
            return scanner;
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Test
    public void correctSourceTest() {
        Scanner scanner = createScanner(SourceFileNames.CORRECT);
        assertTrue(scanner.getErrorMessages().isEmpty());
    }

    @Test
    public void commentErrorTest() {
        Scanner scanner = createScanner(SourceFileNames.COMMENT_ERROR);
        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_EXPECTED_COMMENT_CLOSING));
    }

    @Test
    public void identifierErrorTest() {
        Scanner scanner = createScanner(SourceFileNames.IDENTIFIER_ERROR);
        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_WRONG_IDENTIFIER));
    }

    @Test
    public void ConstantErrorTest() {
        Scanner scanner = createScanner(SourceFileNames.CONSTANT_ERROR);
        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_WRONG_CONST_DEF));
    }

    @Test
    public void SymbolErrorTest() {
        Scanner scanner = createScanner(SourceFileNames.SYMBOL_ERROR);
        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_WRONG_SYMBOL));
    }

    @Test
    public void SeveralErrorsTest() {
        Scanner scanner = createScanner(SourceFileNames.SEVERAL_ERRORS);

        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_WRONG_SYMBOL));
        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_WRONG_IDENTIFIER));
        assertTrue(scanner.getErrorMessages().contains(ErrorMessages.ERROR_WRONG_CONST_DEF));
    }
}

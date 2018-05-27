package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.scanner.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScannerTest {

    private void printSourceFile(String fileName) throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
        int lineCounter = 1;
        try {
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println((lineCounter++) +"\t" + s);
            }
        } finally {
            in.close();
        }
    }

    @Test
    public void correctSourceTest() {
        try {
            printSourceFile(SourceFileNames.CORRECT);
            new Scanner(SourceFileNames.CORRECT).run();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void commentErrorTest() {
        try {
            printSourceFile(SourceFileNames.COMMENT_ERROR);
            new Scanner(SourceFileNames.COMMENT_ERROR).run();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void identifierErrorTest() {
        try {
            printSourceFile(SourceFileNames.IDENTIFIER_ERROR);
            new Scanner(SourceFileNames.IDENTIFIER_ERROR).run();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void ConstantErrorTest() {
        try {
            printSourceFile(SourceFileNames.CONSTANT_ERROR);
            new Scanner(SourceFileNames.CONSTANT_ERROR).run();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void SymbolErrorTest() {
        try {
            printSourceFile(SourceFileNames.SYMBOL_ERROR);
            new Scanner(SourceFileNames.SYMBOL_ERROR).run();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void SeveralErrorsTest() {
        try {
            printSourceFile(SourceFileNames.SEVERAL_ERRORS);
            new Scanner(SourceFileNames.SEVERAL_ERRORS).run();
        } catch (IOException e) { e.printStackTrace(); }
    }
}

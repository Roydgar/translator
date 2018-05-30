package tk.roydgar;

import org.junit.Test;
import tk.roydgar.parser.Tree;
import tk.roydgar.parser.constants.ParserErrors;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.parser.Parser;
import tk.roydgar.scanner.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static tk.roydgar.util.CreatorUtil.createParser;


public class ParserTest {


    @Test
    public void testCorrectSourceFile() {
        Parser parser = createParser(SourceFileNames.CORRECT);

        assertTrue(parser.getErrors().isEmpty());
    }

    @Test
    public void testMissingDot() {
        Parser parser = createParser(SourceFileNames.MISSING_DOT);
        assertTrue(parser.getErrors().contains(ParserErrors.expectedDot));
    }

    @Test
    public void testMissingIdentifier() {
        Parser parser = createParser(SourceFileNames.MISSING_IDENTIFIER);
        assertTrue(parser.getErrors().contains(ParserErrors.expectedIdentifier));
    }

    @Test
    public void testMissingKeyword() {
        Parser parser = createParser(SourceFileNames.MISSING_KEYWORD);
        assertTrue(parser.getErrors().contains(ParserErrors.expectedKeyWord));
    }

    @Test
    public void testMissingSemicolon() {
        Parser parser = createParser(SourceFileNames.MISSING_SEMICOLON);
        assertTrue(parser.getErrors().contains(ParserErrors.expectedSemicolon));
    }

    @Test
    public void testWrongKeyword() {
        Parser parser = createParser(SourceFileNames.WRONG_KEYWORD);
        assertTrue(parser.getErrors().contains(ParserErrors.expectedProgramKeyword));
    }
}

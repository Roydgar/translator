package tk.roydgar;

import org.junit.Test;
import tk.roydgar.parser.Tree;
import tk.roydgar.parser.constants.ParserErrors;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.parser.Parser;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.Scanner;
import tk.roydgar.util.FileUtil;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static tk.roydgar.util.CreatorUtil.createParser;


public class ParserTestWithOutput {



    @Test
    public void testCorrectSourceFile() {
        Parser parser = createParser(SourceFileNames.CORRECT);

        FileUtil.printSourceFile(SourceFileNames.CORRECT);
        parser.getTree().print();

        assertTrue(parser.getErrors().isEmpty());
    }

    @Test
    public void testMissingDot() {
        Parser parser = createParser(SourceFileNames.MISSING_DOT);

        FileUtil.printSourceFile(SourceFileNames.MISSING_DOT);
        System.out.println(parser.getErrors());

        assertTrue(parser.getErrors().contains(ParserErrors.expectedDot));
    }

    @Test
    public void testMissingIdentifier() {
        Parser parser = createParser(SourceFileNames.MISSING_IDENTIFIER);

        FileUtil.printSourceFile(SourceFileNames.MISSING_IDENTIFIER);
        System.out.println(parser.getErrors());

        assertTrue(parser.getErrors().contains(ParserErrors.expectedIdentifier));
    }

    @Test
    public void testMissingKeyword() {
        Parser parser = createParser(SourceFileNames.MISSING_KEYWORD);

        FileUtil.printSourceFile(SourceFileNames.MISSING_KEYWORD);
        System.out.println(parser.getErrors());

        assertTrue(parser.getErrors().contains(ParserErrors.expectedKeyWord));
    }

    @Test
    public void testMissingSemicolon() {
        Parser parser = createParser(SourceFileNames.MISSING_SEMICOLON);

        FileUtil.printSourceFile(SourceFileNames.MISSING_SEMICOLON);
        System.out.println(parser.getErrors());

        assertTrue(parser.getErrors().contains(ParserErrors.expectedSemicolon));
    }

    @Test
    public void testWrongKeyword() {
        Parser parser = createParser(SourceFileNames.WRONG_KEYWORD);

        FileUtil.printSourceFile(SourceFileNames.MISSING_KEYWORD);
        System.out.println(parser.getErrors());

        assertTrue(parser.getErrors().contains(ParserErrors.expectedProgramKeyword));
    }
}

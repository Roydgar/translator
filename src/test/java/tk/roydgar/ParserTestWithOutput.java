package tk.roydgar;

import org.junit.Test;
import tk.roydgar.parser.constants.ErrorMessages;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.util.FileUtil;

import static org.junit.Assert.assertTrue;
import static tk.roydgar.util.CreatorUtil.runParser;


public class ParserTestWithOutput {

    @Test
    public void testCorrectSourceFile() {
        InfoTables tables = runParser(SourceFileNames.CORRECT);

        FileUtil.printSourceFile(SourceFileNames.CORRECT);
        assertTrue(tables.getParserErrors().isEmpty());
    }

    @Test
    public void testMissingDot() {
        InfoTables tables = runParser(SourceFileNames.MISSING_DOT);

        FileUtil.printSourceFile(SourceFileNames.MISSING_DOT);
        System.out.println(tables.getParserErrors());

        assertTrue(tables.getParserErrors().contains(ErrorMessages.expectedDot));
    }

    @Test
    public void testMissingIdentifier() {
        InfoTables tables = runParser(SourceFileNames.MISSING_IDENTIFIER);

        FileUtil.printSourceFile(SourceFileNames.MISSING_IDENTIFIER);
        System.out.println(tables.getParserErrors());

        assertTrue(tables.getParserErrors().contains(ErrorMessages.expectedIdentifier));
    }

    @Test
    public void testMissingKeyword() {
        InfoTables tables = runParser(SourceFileNames.MISSING_KEYWORD);

        FileUtil.printSourceFile(SourceFileNames.MISSING_KEYWORD);
        System.out.println(tables.getParserErrors());

        assertTrue(tables.getParserErrors().contains(ErrorMessages.expectedKeyWord));
    }

    @Test
    public void testMissingSemicolon() {
        InfoTables tables = runParser(SourceFileNames.MISSING_SEMICOLON);

        FileUtil.printSourceFile(SourceFileNames.MISSING_SEMICOLON);
        System.out.println(tables.getParserErrors());

        assertTrue(tables.getParserErrors().contains(ErrorMessages.expectedSemicolon));
    }

    @Test
    public void testWrongKeyword() {
        InfoTables tables = runParser(SourceFileNames.WRONG_KEYWORD);

        FileUtil.printSourceFile(SourceFileNames.WRONG_KEYWORD);
        System.out.println(tables.getParserErrors());

        assertTrue(tables.getParserErrors().contains(ErrorMessages.expectedProgramKeyword));
    }
}

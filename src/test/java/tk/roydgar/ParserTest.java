package tk.roydgar;

import org.junit.Test;
import tk.roydgar.parser.constants.ParserErrors;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.scanner.InfoTables;

import static org.junit.Assert.assertTrue;
import static tk.roydgar.util.CreatorUtil.runParser;


public class ParserTest {


    @Test
    public void testCorrectSourceFile() {
        InfoTables tables = runParser(SourceFileNames.CORRECT);

        assertTrue(tables.getParserErrors().isEmpty());
    }

    @Test
    public void testMissingDot() {
        InfoTables tables = runParser(SourceFileNames.MISSING_DOT);
        assertTrue(tables.getParserErrors().contains(ParserErrors.expectedDot));
    }

    @Test
    public void testMissingIdentifier() {
        InfoTables tables = runParser(SourceFileNames.MISSING_IDENTIFIER);
        assertTrue(tables.getParserErrors().contains(ParserErrors.expectedIdentifier));
    }

    @Test
    public void testMissingKeyword() {
        InfoTables tables = runParser(SourceFileNames.MISSING_KEYWORD);
        assertTrue(tables.getParserErrors().contains(ParserErrors.expectedKeyWord));
    }

    @Test
    public void testMissingSemicolon() {
        InfoTables tables = runParser(SourceFileNames.MISSING_SEMICOLON);
        assertTrue(tables.getParserErrors().contains(ParserErrors.expectedSemicolon));
    }

    @Test
    public void testWrongKeyword() {
        InfoTables tables = runParser(SourceFileNames.WRONG_KEYWORD);
        assertTrue(tables.getParserErrors().contains(ParserErrors.expectedProgramKeyword));
    }
}

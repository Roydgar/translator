package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.ParserErrors;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.parser.Parser;
import tk.roydgar.scanner.Scanner;
import java.io.IOException;
import tk.roydgar.parser.Tree;
import static org.junit.Assert.assertTrue;


public class ParserTest {

    private Parser createParser(String sourceFileName) {
        try {
            Parser parser = new Parser(new Scanner(sourceFileName).run());
            parser.run();
            return parser;
        } catch (IOException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        }
    }

    @Test
    public void testCorrectSourceFile() {
        Parser parser = createParser(SourceFileNames.CORRECT);

        Tree tree = parser.getTree();
        tree.print();

        assertTrue(parser.getErrors().toString().isEmpty());
    }

    @Test
    public void testMissingDot() {
        Parser parser = createParser(SourceFileNames.MISSING_DOT);
        assertTrue(parser.getErrors().toString().contains(ParserErrors.expectedDot));
    }

    @Test
    public void testMissingIdentifier() {
        Parser parser = createParser(SourceFileNames.MISSING_IDENTIFIER);
        assertTrue(parser.getErrors().toString().contains(ParserErrors.expectedIdentifier));
    }

    @Test
    public void testMissingKeyword() {
        Parser parser = createParser(SourceFileNames.MISSING_KEYWORD);
        assertTrue(parser.getErrors().toString().contains(ParserErrors.expectedKeyWord));
    }

    @Test
    public void testMissingSemicolon() {
        Parser parser = createParser(SourceFileNames.MISSING_SEMICOLON);
        assertTrue(parser.getErrors().toString().contains(ParserErrors.expectedSemicolon));
    }

    @Test
    public void testWrongKeyword() {
        Parser parser = createParser(SourceFileNames.WRONG_KEYWORD);
        assertTrue(parser.getErrors().toString().contains(ParserErrors.expectedProgramKeyword));
    }
}

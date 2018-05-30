package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.constants.ErrorMessages;

import static org.junit.Assert.assertTrue;
import static tk.roydgar.util.CreatorUtil.runScanner;

public class ScannerTest {

    @Test
    public void correctSourceTest() {
        InfoTables tables = runScanner(SourceFileNames.CORRECT);
        assertTrue(tables.getScannerErrors().isEmpty());
    }

    @Test
    public void commentErrorTest() {
        InfoTables tables = runScanner(SourceFileNames.COMMENT_ERROR);
        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_EXPECTED_COMMENT_CLOSING));
    }

    @Test
    public void identifierErrorTest() {
        InfoTables tables = runScanner(SourceFileNames.IDENTIFIER_ERROR);
        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_WRONG_IDENTIFIER));
    }

    @Test
    public void ConstantErrorTest() {
        InfoTables tables = runScanner(SourceFileNames.CONSTANT_ERROR);
        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_WRONG_CONST_DEF));
    }

    @Test
    public void SymbolErrorTest() {
        InfoTables tables = runScanner(SourceFileNames.SYMBOL_ERROR);
        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_WRONG_SYMBOL));
    }

    @Test
    public void SeveralErrorsTest() {
        InfoTables tables = runScanner(SourceFileNames.SEVERAL_ERRORS);

        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_WRONG_SYMBOL));
        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_WRONG_IDENTIFIER));
        assertTrue(tables.getScannerErrors().contains(ErrorMessages.ERROR_WRONG_CONST_DEF));
    }
}

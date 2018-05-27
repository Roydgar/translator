package tk.roydgar.scanner.constants;

public interface Constants {
    String SOURCE_FILE_NAME     = "src/main/java/tk/roydgar/scanner/source/source.txt";
    String SOURCE_FILE_EXT      = ".txt";
    String LISTING_FILE_EXT     = ".out";
    int ASCII_TABLE_SIZE        = 128;
    int ATTR_WHITESPACE         = 0;
    int ATTR_CONST              = 1;
    int ATTR_IDENTIFIER_KEYWORD = 2;
    int ATTR_DELIMITER          = 3;
    int ATTR_COMMENT            = 5;
    int ATTR_INCORRECT          = 6;

    int NEW_LINE_CHARACTER      = 10;
    int EOF                     = -1;
    int SYMBOL_SHARP            = '#';
    int SYMBOL_ASTERISK         = '*';
    int SYMBOL_OPENED_BRACKET   = '(';
    int SYMBOL_CLOSED_BRACKET   = ')';
    int SYMBOL_OPENED_ANGLE_BRACKET   = '<';
    int SYMBOL_CLOSED_ANGLE_BRACKET   = '>';

    String ERROR_WRONG_CONST_DEF           = "Illegal constant definition";
    String ERROR_EXPECTED_COMMENT         = "Expected a comment, but end of a file reached";
    String ERROR_EXPECTED_COMMENT_CLOSING = "*) expected but end of a file reached";
    String ERROR_WRONG_SYMBOL              = "Illegal symbol";
    String ERROR_WRONG_IDENTIFIER          = "Illegal Identifier. Identifier cannot start with digit";
    String ERROR_WRONG_DATE_FORMAT         = "Wrong date format";
    String ERROR_EXPECTED_DATE_CLOSING        = "Closing date identifier error. Date block wasn't closed";

    String LISTING_OUTPUT_FORMATTING        = "%4d %4d %10d    %s\n";
    String CONSOLE_OUTPUT_FORMATTING        = "%4d %4d %10d    %s";
    String LISTING_FORMATTING               = "Lexer: Error: %s '%s'\n";
    String DATE_FORMAT                      = "hha:mm";
}

package tk.roydgar.scanner.constants;

public interface Constants {
    String SOURCE_FILE_NAME     = "src/main/java/tk/roydgar/scanner/source/source.signal";
    String SOURCE_FILE_EXT      = ".signal";
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
    int SYMBOL_MINUS             = '-';
    int SYMBOL_PLUS             = '+';
    int SYMBOL_OPENED_ANGLE_BRACKET   = '<';
    int SYMBOL_CLOSED_ANGLE_BRACKET   = '>';

    String LISTING_OUTPUT_FORMATTING        = "%4d %4d %10d    %s\n";
    String CONSOLE_OUTPUT_FORMATTING        = "%4d %4d %10d    %s";
    String LISTING_FORMATTING               = "Lexer: Error (line %d row %d) %s %s\n";
    String DATE_FORMAT                      = "hha:mm";
}

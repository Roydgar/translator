package tk.roydgar.parser.constants;

public interface ErrorMessages {
    String PROGRAM           = "program";
    String BEGIN             = "begin or const declarations";
    String END               = "end";
    String DOT               = ".";
    String SEMICOLON         = ";";
    String SHARP             = "#";
    String EQUAL             = "=";
    String CONST             = "const";
    String IDENTIFIER        = "any identifier";
    String IDENTIDIER_OR_BEG = "any identifier or begin";
    String UNSIGNED_CONSTANT = "any constant";

    String expectedDot            = "expected: '.'";
    String expectedIdentifier     = "expected: 'any identifier'";
    String expectedKeyWord        = "expected: 'program'";
    String expectedSemicolon      = "expected: ';'";
    String expectedProgramKeyword = "expected: 'program'";

    String FORMAT            = "Parser error: expected: '%s', actual: '%s'.\n";

}

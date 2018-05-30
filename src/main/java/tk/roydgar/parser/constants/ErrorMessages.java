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
    String UNSIGNED_CONSTANT = "any constant";

    String FORMAT            = "Parser error: expected: '%s', actual: '%s'.\n";
}

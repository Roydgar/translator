package tk.roydgar.scanner.constants;

public interface ErrorMessages {
    String ERROR_WRONG_CONST_DEF           = "Illegal constant definition";
    String ERROR_EXPECTED_COMMENT         = "Expected a comment, but end of a file reached";
    String ERROR_EXPECTED_COMMENT_CLOSING = "*) expected but end of a file reached";
    String ERROR_WRONG_SYMBOL              = "Illegal symbol";
    String ERROR_WRONG_IDENTIFIER          = "Illegal Identifier. Identifier cannot start with digit";
    String ERROR_WRONG_DATE_FORMAT         = "Wrong date format";
    String ERROR_EXPECTED_DATE_CLOSING        = "Closing date identifier error. Date block wasn't closed";
}

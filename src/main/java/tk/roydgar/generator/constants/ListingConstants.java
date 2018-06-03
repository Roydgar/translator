package tk.roydgar.generator.constants;

public interface ListingConstants {
    String LST_FILE_EXTENSION = ".lst";
    String EQU                = "EQU";
    String VERSION            = ".386\n\n";
    String PROG               = ";%s\n\n";
    String DATA_SEGMENT       = "data SEGMENT\n\n";
    String DATA_ENDS          = "data ENDS\n\n";
    String DECLARATION        = "%s\tEQU\t%s\n";
    String CONSTANT_DECLARATION  = "%s\t%s\t%s\n";

    String BODY               = "code SEGMENT\n\t\tASSUME cs:code, ds:data\n\n" +
            "begin:\n" +
            "\tpush ebp\n" +
            "\tmov ebp, esp\n" +
            "\txor eax, eax\n" +
            "\tmov esp, ebp\n" +
            "\tpop ebp\n" +
            "\tret 0\n" +
            "\tmov ax, 4c00h\n" +
            "\tint 21h\n" +
            "code ENDS\n" +
            "\tEND begin";

    String NUMBER_FORMAT_PATTERN = "#.#";
}

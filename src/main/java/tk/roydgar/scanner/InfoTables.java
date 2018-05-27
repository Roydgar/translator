package tk.roydgar.scanner;

import java.util.*;

import static tk.roydgar.scanner.constants.Constants.*;

public class InfoTables {
    private Map<String, Integer> keyTab   = new HashMap<>();     //key word table
    private Map<String, Integer> idnTab   = new HashMap<>();     //identifiers table
    private Map<String, Integer> constTab = new HashMap<>();     //constant table
    private Map<Character, Integer> dmTab = new HashMap<>();     //delimiter table
    private int idnLexCode   = 400;                             //code of the last inserted identifier token
    private int constLexCode = 500;                              //code of the last inserted constant token
    private int[] attributes;                                    //attributes table

    private List<Token> lexems = new ArrayList<>();

    private int[] getAttributeTable() {
        int[] attributes = new int[ASCII_TABLE_SIZE];

        Arrays.fill(attributes, ATTR_INCORRECT);
        Arrays.fill(attributes, 8, 11, 0);
        attributes[32] = ATTR_WHITESPACE;
        attributes[13] = ATTR_WHITESPACE;
        attributes[43] = ATTR_DELIMITER; attributes[45] = ATTR_DELIMITER;
        attributes[59] = ATTR_DELIMITER; attributes[61] = ATTR_DELIMITER;
        attributes[60] = ATTR_DELIMITER; attributes[62] = ATTR_DELIMITER;
        attributes[35] = ATTR_DELIMITER; attributes[46] = ATTR_DELIMITER;
        attributes[58] = ATTR_DELIMITER;
        attributes[40] = ATTR_COMMENT;
        Arrays.fill(attributes, 48, 57, ATTR_CONST);
        Arrays.fill(attributes, 65, 123, ATTR_IDENTIFIER_KEYWORD);

        return attributes;
    }

    public int getAttribute(int i) { return attributes[i]; }

    public InfoTables() {
        attributes = getAttributeTable();
        formTables();
    }

    private void formTables() {
        dmTab.put('=', 0);  dmTab.put('+', 1);
        dmTab.put('-', 2);  dmTab.put(';', 3);
        dmTab.put('#', 4);  dmTab.put('.', 5);
        dmTab.put('<', 6);  dmTab.put('>', 7);
        dmTab.put(':', 8);
        keyTab.put("program", 301); keyTab.put("begin", 302);
        keyTab.put("end", 303);     keyTab.put("const", 304);
    }

    public boolean keyTabSearch(String value)   { return keyTab.containsKey(value);   }
    public int getKeyTabLexCode(String value)   { return keyTab.getOrDefault(value, -1); }
    public boolean idnTabSearch(String value)   { return idnTab.containsKey(value);   }
    public int getIdnTabLexCode(String value)   { return idnTab.getOrDefault(value, -1); }
    public boolean constTabSearch(String value) { return constTab.containsKey(value); }
    public int getConstTabLexCode(String value) { return constTab.getOrDefault(value, -1); }

    public int getDmTabLexCode(int value)       { return dmTab.get((char)value); }
    public int idnTabForm(String value)         { idnTab.put(value, ++idnLexCode);       return idnLexCode;    }
    public int constTabForm(String value)       { constTab.put(value, ++constLexCode);   return constLexCode;  }


    public static class Token {
        public String name;
        public int code;

        Token(String name, int code) {
            this.name = name;
            this.code = code;

        }

        @Override
        public String toString() {
            return name + " : " + code;
        }
    }

    public void addToken(String token, int code) {
        lexems.add(new Token(token, code));
    }

    public List<Token> getTokens() { return lexems; }
}

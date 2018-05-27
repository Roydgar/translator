package tk.roydgar.scanner;

import tk.roydgar.scanner.constants.Constants;
import tk.roydgar.scanner.constants.ErrorMessages;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static tk.roydgar.scanner.constants.Constants.*;

public class Scanner {

    private class TSymbol {
        private int value;
        private int attr;
        private TSymbol(int value, int attr) {
            this.value = value;
            this.attr = attr;
        }

        @Override
        public String toString() {
            return "Character: " + (char)value + "; Attribute: " + attr;
        }
    }

    private BufferedReader reader;
    private FileWriter writer;
    private InfoTables infoTables = new InfoTables();

    private String listingFilePath;

    private boolean suppressOutput;
    private boolean suppressListing;
    private boolean date;

    private int lexCode = 0;
    private int charCounter = 0;
    private int tokenPos = 0;

    private TSymbol symbol;
    private StringBuilder buffer;
    private StringBuilder errorMessages = new StringBuilder();

    private int lineCount;

    public Scanner(String filePath) throws IOException{
        reader = new BufferedReader(new FileReader(filePath));
        listingFilePath = filePath.replace(SOURCE_FILE_EXT, LISTING_FILE_EXT);
        infoTables.setOutputFileName(filePath.replace(SOURCE_FILE_EXT, LISTING_FILE_EXT));
        writer = new FileWriter(listingFilePath);
    }

    public String getErrorMessages() {
        return errorMessages.toString();
    }

    private void processError(String error) throws IOException{
        errorMessages.append(String.format(LISTING_FORMATTING, lineCount, charCounter, error, buffer));
        suppressOutput = true;
        suppressListing = true;
    }

    private TSymbol readChar() throws IOException{
        try {
            int ch = Character.toLowerCase(reader.read());
            charCounter++;
            return new TSymbol(ch, infoTables.getAttribute(ch));
        } catch (ArrayIndexOutOfBoundsException eof) {  //throws exception if end of the file reached
            return new TSymbol(EOF, EOF);
        }
    }

    private void writeToListing(String token, int line, int row, int lexCode) throws IOException{
        infoTables.addToken(token, lexCode);
        writer.write(String.format(LISTING_OUTPUT_FORMATTING, line, row, lexCode, token));
    }

    public InfoTables run() throws IOException{
        try {
            symbol = readChar();
            lineCount = symbol.value == EOF ? 0 : 1;
            do {
                buffer = new StringBuilder();
                suppressOutput = false;
                tokenPos = charCounter;

                switch (symbol.attr) {
                    case ATTR_WHITESPACE:
                        processWhitespaces();
                        break;

                    case ATTR_CONST:
                        processConstant();
                        break;

                    case ATTR_IDENTIFIER_KEYWORD:
                        processIdentifier();
                        break;

                    case ATTR_DELIMITER:
                        processDelimiter();
                        break;

                    case ATTR_COMMENT:
                        processComment();
                        break;
                    case ATTR_INCORRECT:
                        processIncorrectSymbol();
                        break;
                }

                if (!suppressOutput) {
                    writeToListing(buffer.toString(), lineCount, tokenPos, lexCode);
                }
            } while (symbol.value != EOF);

            if (suppressListing)
                writer = new FileWriter(listingFilePath);

            infoTables.setErrors(errorMessages.toString());
        } finally {
            writer.close();
            reader.close();
        }

        return infoTables;
    }

    private void processIncorrectSymbol() throws IOException {
        buffer.append((char)symbol.value);
        processError(ErrorMessages.ERROR_WRONG_SYMBOL);
        symbol = readChar();
    }

    private void processComment() throws IOException{
        symbol = readChar();
        if (symbol.value == EOF) {
            processError(ErrorMessages.ERROR_EXPECTED_COMMENT);
        }
        else {
            if (symbol.value == SYMBOL_ASTERISK) {
                do {
                    while (symbol.value != EOF && symbol.value != SYMBOL_ASTERISK) {
                        symbol = readChar();
                        if (symbol.value == NEW_LINE_CHARACTER) {
                            charCounter = 0;
                            lineCount++;
                        }
                    }

                    if (symbol.value ==EOF) {
                        processError(ErrorMessages.ERROR_EXPECTED_COMMENT_CLOSING );
                        break;
                    } else {
                        symbol = readChar();
                    }
                } while (symbol.value != SYMBOL_CLOSED_BRACKET);
            } else
                processError(ErrorMessages.ERROR_WRONG_SYMBOL);
            if (symbol.value == SYMBOL_CLOSED_BRACKET)
                suppressOutput = true;
            symbol = readChar();
        }
    }

    private void processDelimiter() throws IOException{
        lexCode = infoTables.getDmTabLexCode(symbol.value);
        buffer.append((char) symbol.value);
        if (symbol.value == SYMBOL_OPENED_ANGLE_BRACKET) {
            date = true;
        }
        symbol = readChar();
    }

    private void processIdentifier() throws IOException{
        while(symbol.value != EOF && symbol.attr == ATTR_IDENTIFIER_KEYWORD || symbol.attr == ATTR_CONST) {
            buffer.append((char)symbol.value);
            symbol = readChar();
        }
        if (infoTables.keyTabSearch(buffer.toString()))
            lexCode = infoTables.getKeyTabLexCode(buffer.toString());
        else
        if (infoTables.idnTabSearch(buffer.toString()))
            processError(ErrorMessages.ERROR_REPEAT_IDENTIFIER);
        else
            lexCode = infoTables.idnTabForm(buffer.toString());
    }

    private void processConstant() throws IOException{
        boolean checkSharp = false;
        boolean error      = false;

        if (date) {
            processDate();
            return;
        }
        while (symbol.value != EOF && symbol.attr == ATTR_CONST || symbol.value == SYMBOL_SHARP
                || symbol.value == Constants.SYMBOL_MINUS ||  symbol.value == Constants.SYMBOL_PLUS) {
            buffer.append((char)symbol.value);
            symbol = readChar();
            if ((char)symbol.value == SYMBOL_SHARP) {
                if (checkSharp)
                    error = true;
                checkSharp = true;
            }
        }

        if (error)
            processError(ErrorMessages.ERROR_WRONG_CONST_DEF);

        if (symbol.attr == ATTR_IDENTIFIER_KEYWORD)
            processError(ErrorMessages.ERROR_WRONG_IDENTIFIER);

        if (infoTables.constTabSearch(buffer.toString()))
            lexCode = infoTables.getConstTabLexCode(buffer.toString());
        else
            lexCode = infoTables.constTabForm(buffer.toString());
    }

    private void processDate() throws IOException {
        while (symbol.value != SYMBOL_CLOSED_ANGLE_BRACKET) {
            buffer.append((char)symbol.value);

            if (symbol.value ==EOF) {
                buffer = new StringBuilder();
                processError(ErrorMessages.ERROR_EXPECTED_DATE_CLOSING);
                return;
            } else {
                symbol = readChar();
            }
        }

        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setLenient(false);

        try {
            formatter.parse(buffer.toString());
        } catch (ParseException e) {
            processError(ErrorMessages.ERROR_WRONG_DATE_FORMAT);
        }
        if (infoTables.idnTabSearch(buffer.toString()))
            lexCode = infoTables.getIdnTabLexCode(buffer.toString());
        else
            lexCode = infoTables.idnTabForm(buffer.toString());
        date = false;
    }

    private void processWhitespaces() throws IOException{
        while (symbol.value != EOF) {
            symbol = readChar();
            if (symbol.attr != ATTR_WHITESPACE)
                break;
            if (symbol.value == NEW_LINE_CHARACTER) {
                lineCount++;
                charCounter = 0;
            }
        }
        suppressOutput = true;
    }
}

package tk.roydgar.parser;

import tk.roydgar.parser.constants.*;
import tk.roydgar.scanner.InfoTables;

import java.util.List;

public class Parser {

    private List<InfoTables.Token> tokens;
    private int tokenCounter = 0;
    private InfoTables.Token currentToken;

    private void scanNextToken() {
        if (tokenCounter < tokens.size()) {
            currentToken = tokens.get(tokenCounter++);
        }
    }

    public Parser(InfoTables infoTables) {
        this.tokens = infoTables.getTokens();
    }

    public void run() {
        scanNextToken();
        try {
            programBlock();
        } catch (ParserErrorException e) {
            skipStatement();
        }

        try {
            declarations();
        } catch (ParserErrorException e) {
            skipStatement();
        }

        try {
            block();
        } catch (ParserErrorException e) { }
    }

    private void skipStatement() {
        while(currentToken.code != DelimitersCodes.SEMICOLON) {
            scanNextToken();
        }
        scanNextToken();
    }

    private void error(String expected) throws ParserErrorException{
        System.out.format(ErrorMessages.FORMAT, expected, currentToken.name);
        throw new ParserErrorException();
    }

    private void programBlock() throws ParserErrorException{
        program();
        identifier();
        semicolon();
    }

    private void program() throws ParserErrorException{
        if (currentToken.code == KeywordCodes.PROGRAM) {
            scanNextToken();
        } else {
            error(ErrorMessages.PROGRAM);
        }
    }

    private void identifier() throws ParserErrorException{
        if (currentToken.code >= IdentifierCodes.FROM && currentToken.code <= IdentifierCodes.TO) {
            scanNextToken();
        } else {
            error(ErrorMessages.IDENTIFIER);
        }
    }

    private void semicolon() throws ParserErrorException{
        if (currentToken.code == DelimitersCodes.SEMICOLON) {
            scanNextToken();
        } else {
            error(ErrorMessages.SEMICOLON);
        }
    }

    private void declarations() throws ParserErrorException{
        boolean present = constant();

        if (!present) {
            return;
        }

        identifier();
        equal();
        unsignedConstant();
        semicolon();
        declarations();
    }

    private boolean constant() throws ParserErrorException{
        if (currentToken.code == KeywordCodes.CONST) {
            scanNextToken();
            return true;
        }
        return false;
    }

    private void equal() throws ParserErrorException{
        if (currentToken.code == DelimitersCodes.EQUALS) {
            scanNextToken();
        } else {
            error(ErrorMessages.EQUAL);
        }
    }

    private void unsignedConstant() throws ParserErrorException{
        if (currentToken.code >= ConstantCodes.FROM && currentToken.code <= ConstantCodes.TO) {
            scanNextToken();
        } else {
            error(ErrorMessages.UNSIGNED_CONSTANT);
        }
    }

    private void block() throws ParserErrorException{
        begin();
        end();
        dot();
    }

    private void begin() throws ParserErrorException{
        if (currentToken.code == KeywordCodes.BEGIN) {
            scanNextToken();
        } else {
            error(ErrorMessages.BEGIN);
        }
    }

    private void end() throws ParserErrorException{
        if (currentToken.code == KeywordCodes.END) {
            scanNextToken();
        } else {
            error(ErrorMessages.END);
        }
    }

    private void dot() throws ParserErrorException{
        if (currentToken.code == DelimitersCodes.DOT) {
        } else {
            error(ErrorMessages.DOT);
        }
    }


}

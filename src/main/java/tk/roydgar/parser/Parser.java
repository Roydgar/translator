package tk.roydgar.parser;

import tk.roydgar.parser.constants.*;
import tk.roydgar.parser.constants.ConstantCodes;
import tk.roydgar.parser.constants.DelimitersCodes;
import tk.roydgar.parser.constants.IdentifierCodes;
import tk.roydgar.parser.constants.KeywordCodes;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.constants.Keywords;

import java.util.List;

public class Parser {

    private List<InfoTables.Token> tokens;
    private InfoTables infoTables;
    private int tokenCounter = 0;
    private InfoTables.Token currentToken;
    private StringBuilder errors = new StringBuilder();

    private Tree tree;
    private boolean errorOccured;

    private void scanNextToken() {
        if (tokenCounter < tokens.size()) {
            currentToken = tokens.get(tokenCounter++);
        }
    }

    public Parser(InfoTables infoTables) {
        this.tokens = infoTables.getTokens();
        this.infoTables = infoTables;
        tree = new Tree(infoTables.getOutputFileName());
    }

    private void skipStatement() {
        if (currentToken.code == KeywordCodes.BEGIN) {
            return;
        }

        while(currentToken.code != DelimitersCodes.SEMICOLON) {
            scanNextToken();
        }

        scanNextToken();
    }

    private void error(String expected) throws ParserErrorException{
        String errorMessage = String.format(ErrorMessages.FORMAT, expected, currentToken.name);
        errors.append(errorMessage);
        errorOccured = true;
        throw new ParserErrorException();
    }


    public InfoTables run() {

        scanNextToken();
        try {
            programBlock();
        } catch (ParserErrorException e) {
            skipStatement();
        }
        tree.addTail();

        infoTables.setParserErrors(errors.toString());

        if (!errors.toString().isEmpty()) {
            tree = new Tree();
        }

        infoTables.setParserTree(tree);

        return infoTables;
    }

    private void programBlock() throws ParserErrorException{
        program();
        String identifier = identifier();
        semicolon();
        try {
            block();
        } catch (ParserErrorException e) { }
        tree.setProgramIdentifier(identifier);
    }

    private void program() throws ParserErrorException{
        if (currentToken.code == KeywordCodes.PROGRAM) {
            scanNextToken();
        } else {
            error(ErrorMessages.PROGRAM);
        }
    }

    private String identifier() throws ParserErrorException{
        if (currentToken.code >= IdentifierCodes.FROM && currentToken.code <= IdentifierCodes.TO) {
            String identifier = currentToken.name;
            scanNextToken();
            return identifier;
        } else {
            error(ErrorMessages.IDENTIFIER);
        }
        return "";
    }

    private void semicolon() throws ParserErrorException{
        if (currentToken.code == DelimitersCodes.SEMICOLON) {
            scanNextToken();
        } else {
            error(ErrorMessages.SEMICOLON);
        }
    }

    private void declarations() throws ParserErrorException{
        constant();
        constantDeclarationList();

    }

    private void constantDeclarationList() throws ParserErrorException {
        constantDeclaration();
    }

    private void constantDeclaration() throws ParserErrorException {
        if (currentToken.code == KeywordCodes.BEGIN) {
            return;
        }

        String identifier = identifier();
        equal();
        String constant = unsignedConstant();
        semicolon();

        tree.addDeclaration(identifier, constant);
        constantDeclaration();
    }

    private void constant(){
        if (currentToken.code == KeywordCodes.CONST) {
            scanNextToken();
        }
    }

    private void equal() throws ParserErrorException{
        if (currentToken.code == DelimitersCodes.EQUALS) {
            scanNextToken();
        } else {
            error(ErrorMessages.EQUAL);
        }
    }

    private String unsignedConstant() throws ParserErrorException{
        if (currentToken.code >= ConstantCodes.FROM && currentToken.code <= ConstantCodes.TO) {
            String constant = currentToken.name;
            scanNextToken();
            return constant;
        } else {
            error(ErrorMessages.UNSIGNED_CONSTANT);
        }
        return "";
    }

    private void block() throws ParserErrorException{
        try {
            declarations();
        } catch (ParserErrorException e) {
            skipStatement();
        }
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

    public Tree getTree() {
        return tree;
    }

    public String getErrors() {
        return errors.toString();
    }

    class ParserErrorException extends Exception {
    }
}

package tk.roydgar.parser;

import tk.roydgar.parser.constants.*;
import tk.roydgar.parser.constants.ConstantCodes;
import tk.roydgar.parser.constants.DelimitersCodes;
import tk.roydgar.parser.constants.IdentifierCodes;
import tk.roydgar.parser.constants.KeywordCodes;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.constants.Delimiters;
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

    private void error(String expected) {
        String errorMessage = String.format(ErrorMessages.FORMAT, expected, currentToken.name);
        errors.append(errorMessage);
        errorOccured = true;
        throw new SyntaxErrorException();
    }


    public String getErrors() {
        return errors.toString();
    }

    public InfoTables run() {
        scanNextToken();

        try {
            signalProgram();
        } catch (SyntaxErrorException syntaxError) {
            tree = new Tree();
            infoTables.setParserErrors(errors.toString());
            return infoTables;
        }

        tree.addTail();
        infoTables.setParserTree(tree);
        return infoTables;
    }

    private void signalProgram() {
        program();
    }

    private void program() {
        programKeyword();
        String programName = procedureIdentifier();
        tree.setProgramIdentifier(programName);
        semicolon();
        block();
        dot();
    }

    private void programKeyword() {
        if (currentToken.code == KeywordCodes.PROGRAM) {
            scanNextToken();
        } else {
            error(Keywords.PROGRAM);
        }
    }

    private String procedureIdentifier() {
        return identifier();
    }

    private void semicolon() {
        if (currentToken.code == DelimitersCodes.SEMICOLON) {
            scanNextToken();
        } else {
            error(Delimiters.SEMICOLON);
        }
    }

    private void block() {
        declarations();
        beginKeyword();
        statementList();
        endKeyword();
    }

    private void beginKeyword() {
        if (currentToken.code == KeywordCodes.BEGIN) {
            scanNextToken();
        } else {
            System.out.println(currentToken);
            error(Keywords.BEGIN);
        }
    }

    private void endKeyword() {
        if (currentToken.code == KeywordCodes.END) {
            scanNextToken();
        } else {
            error(Keywords.END);
        }
    }


    private void statementList() {
        empty();
    }


    private void empty() {
    }

    private void dot() {
        if (currentToken.code == DelimitersCodes.DOT) {
            scanNextToken();
        } else {
            error(Delimiters.DOT);
        }
    }

    private void declarations() {
        constantDeclarations();
    }

    private void constantDeclarations() {
        boolean present = constKeyword();

        if (!present) {
            return;
        }

        constantDeclarationsList();
    }

    private boolean constKeyword() {
        if (currentToken.code == KeywordCodes.CONST) {
            scanNextToken();
            return true;
        }
        return false;
    }

    private void constantDeclarationsList() {
        if (currentToken.code == KeywordCodes.BEGIN) {
            return;
        }

        constantDeclaration();
        constantDeclarationsList();
    }

    private void constantDeclaration() {
        String identifier = constantIdentifier();
        equals();
        String value = constant();
        semicolon();
        tree.addDeclaration(identifier, value);
    }

    private String constantIdentifier() {
        return identifier();
    }

    private void equals() {
        if (currentToken.code == DelimitersCodes.EQUALS) {
            scanNextToken();
        } else {
            error(Delimiters.EQUALS);
        }
    }

    private String constant() {
        if (currentToken.code >= ConstantCodes.FROM && currentToken.code <= ConstantCodes.TO) {
            String value = currentToken.name;
            scanNextToken();
            return value;
        } else {
            error(ErrorMessages.UNSIGNED_CONSTANT);
        }
        return "";
    }

    private String identifier() {
        if (currentToken.code >= IdentifierCodes.FROM && currentToken.code <= IdentifierCodes.TO) {
            String name = currentToken.name;
            scanNextToken();
            return name;
        } else {
            error(ErrorMessages.IDENTIFIER);
        }
        return "";
    }

    private class SyntaxErrorException extends RuntimeException {
    }

}

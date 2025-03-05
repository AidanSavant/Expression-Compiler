package Compiler.models.Lexer;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Predicate;

import Compiler.models.Logger.*;

public class Lexer extends Loggable {
    private String program;
    private int programCursor;

    private final ArrayList<Token> tokens = new ArrayList<>(3);
    private int tokensCursor;

    private static final HashMap<Character, TokenKind> SYMBOLMAP = new HashMap<>() {{
        // Arithmetic operators
        put('+', TokenKind.ADD_OP);
        put('-', TokenKind.SUB_OP);
        put('*', TokenKind.MUL_OP);
        put('/', TokenKind.DIV_OP);

        // Logical operators
        put('&', TokenKind.AND_OP);
        put('|', TokenKind.OR_OP);
        put('~', TokenKind.NOT_OP);

        // Parentheses
        put('(', TokenKind.LEFT_PAREN);
        put(')', TokenKind.RIGHT_PAREN);
    }};

    /* == Public interface == */
    public Lexer(String program) {
        setProgram(program);
        setProgramCursor(0);

        tokenize();
        setTokensCursor(0);
    }

    public void tokenize() {
        while(!atProgramEnd()) {
            char currChar = getCurrChar();

            if (Character.isWhitespace(currChar)) {
                incProgramCursor();
            }
            else if (Character.isDigit(currChar)) {
                lexIntLit();
            }
            else if (SYMBOLMAP.containsKey(currChar)) {
                lexSymbol();
            }
            else {
                logger.logFatalErr("'" + currChar + "' is an invalid character!");
            }
        }

        tokens.add(new Token("", TokenKind.EOF));
    }

    public Token nextToken() {
        Token token = getCurrToken();
        incTokenCursor();

        return token;
    }

    /* == Utility public utility functions == */
    public String toPretty() {
        StringBuilder prettified = new StringBuilder();

        for(Token token : tokens) {
            prettified.append(token).append("\n");
        }

        return prettified.toString();
    }

    /* == Private lexing methods == */
    private void lexSymbol() {
        char symbol = getCurrChar();
        TokenKind kind = SYMBOLMAP.get(symbol);
        
        tokens.add(new Token(String.valueOf(symbol), kind));
        
        incProgramCursor();
    }

    private void lexIntLit() {
        tokens.add(new Token(lexWhile(Character::isDigit), TokenKind.INT_LIT));
    }

    private String lexWhile(Predicate<Character> predicate) {
        StringBuilder sb = new StringBuilder();

        while(!atProgramEnd() && predicate.test(getCurrChar())) {
            sb.append(getCurrChar());
            incProgramCursor();
        }

        return sb.toString();
    }

    /* == Private utility functions == */
    private boolean atProgramEnd() {
        return getProgramCursor() >= getProgram().length();
    }

    private boolean atTokensEnd() {
        return this.getTokensCursor() >= tokens.size();
    }

    private char getCurrChar() {
        if(atProgramEnd()) {
            logger.logFatalErr("Attempt to read past end of program!");
        }

        return program.charAt(programCursor);
    }

    private void incProgramCursor() {
        setProgramCursor(getProgramCursor() + 1);
    }

    private Token getCurrToken() {
        if(atTokensEnd()) {
            logger.logFatalErr("Attempt to read past end of tokens!");
        }

        return tokens.get(tokensCursor);
    }

    private void incTokenCursor() {
        setTokensCursor(getTokensCursor() + 1);
    }

    /* == Getters & setters == */
    public int getProgramCursor() {
        return programCursor;
    }

    private void setProgramCursor(int programCursor) {
        if(programCursor < 0 || programCursor > program.length()) {
            logger.logFatalErr("Invalid program cursor position: " + programCursor);
        }

        this.programCursor = programCursor;
    }

    public String getProgram() {
        return program;
    }

    private void setProgram(String program) {
        if(program == null || program.isEmpty()) {
            logger.logFatalErr("Program cannot be null or empty!");
        }

        this.program = program;
    }

    private int getTokensCursor() {
        return tokensCursor;
    }

    private void setTokensCursor(int tokensCursor) {
        if(atTokensEnd()) {
            logger.logFatalErr("Invalid tokens cursor position: " + tokensCursor);
        }

        this.tokensCursor = tokensCursor;
    }
}
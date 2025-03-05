package Compiler.models.Lexer;

import Compiler.models.Logger.Loggable;

public class Token extends Loggable {
    private String lexeme;
    private TokenKind kind;

    /* === Public interface === */
    public Token(String lexeme, TokenKind kind) {
        this.setLexeme(lexeme);
        this.setKind(kind);
    }

    /* === Getters & setters === */
    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        if(lexeme == null) {
            logger.logFatalErr("Invalid lexeme state!");
        }

        this.lexeme = lexeme;
    }

    public TokenKind getKind() {
        return this.kind;
    }

    public void setKind(TokenKind kind) {
        if(kind == null) {
            logger.logFatalErr("Invalid token kind state!");
        }

        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Token: \"" + lexeme + "\", kind: " + kind;
    }
}

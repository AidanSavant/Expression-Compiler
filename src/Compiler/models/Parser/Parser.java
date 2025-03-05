package Compiler.models.Parser;

import Compiler.models.Lexer.Lexer;
import Compiler.models.Lexer.Token;
import Compiler.models.Lexer.TokenKind;
import Compiler.models.Logger.Loggable;
import Compiler.models.Parser.Ast.Impls.BinaryOp;
import Compiler.models.Parser.Ast.Impls.Int;
import Compiler.models.Parser.Ast.Impls.UnaryOp;
import Compiler.models.Parser.Ast.Interfaces.Expr;

public class Parser extends Loggable {
    private Lexer lexer;
    private Token currToken;

    /* == Public interface == */
    public Parser(Lexer lexer) {
        this.setLexer(lexer);
        this.setCurrToken(lexer.nextToken());
    }

    public Expr parse() {
        return parseOr();
    }

    private Expr parseOr() {
        Expr lhs = parseAnd();

        while(matchesKind(TokenKind.OR_OP)) {
            advance(TokenKind.OR_OP);
            lhs = new BinaryOp(lhs, parseAnd(), TokenKind.OR_OP);
        }

        return lhs;
    }

    private Expr parseAnd() {
        Expr lhs = parseNot();

        while(matchesKind(TokenKind.AND_OP)) {
            advance(TokenKind.AND_OP);
            lhs = new BinaryOp(lhs, parseAnd(), TokenKind.AND_OP);
        }

        return lhs;
    }

    private Expr parseNot() {
        if(matchesKind(TokenKind.NOT_OP)) {
            advance(TokenKind.NOT_OP);
            return new UnaryOp(parseNot());
        }

        return parseSumDiff();
    }

    private Expr parseSumDiff() {
        Expr lhs = parseProdQuot();

        while(matchesKind(TokenKind.ADD_OP, TokenKind.SUB_OP)) {
            TokenKind kind = currToken.getKind();
            advance(kind);

            lhs = new BinaryOp(lhs, parseProdQuot(), kind);
        }

        return lhs;
    }

    private Expr parseProdQuot() {
        Expr lhs = parseParen();

        while(matchesKind(TokenKind.MUL_OP, TokenKind.DIV_OP)) {
            TokenKind kind = currToken.getKind();
            advance(kind);

            lhs = new BinaryOp(lhs, parseParen(), kind);
        }

        return lhs;
    }

    private Expr parseParen() {
        if(matchesKind(TokenKind.LEFT_PAREN)) {
            advance(TokenKind.LEFT_PAREN);
            Expr expr = parse();
            advance(TokenKind.RIGHT_PAREN);

            return expr;
        }

        if(matchesKind(TokenKind.NOT_OP)) {
            advance(TokenKind.NOT_OP);
            return new UnaryOp(parseParen());
        }

        return parseInt();
    }

    private Expr parseInt() {
        if(currToken.getKind() == TokenKind.INT_LIT) {
            int value = Integer.parseInt(getCurrToken().getLexeme());
            advance(TokenKind.INT_LIT);

            return new Int(value);
        }
        else {
            logger.logFatalErr("Syntax error! Expected integer literal, but got " + currToken.getKind() + "!");
            return null; // unreachable
        }
    }

    /* == Private utility functions == */
    private boolean matchesKind(TokenKind... kinds) {
        for(TokenKind kind : kinds) {
            if(currToken.getKind() == kind) {
                return true;
            }
        }

        return false;
    }

    private void advance(TokenKind kind) {
        if(currToken.getKind() == kind) {
            setCurrToken(lexer.nextToken());
        }
        else {
            logger.logFatalErr("Unexpected token! Expected: " + kind + ", but got: " + currToken.getKind() + "!");
        }
    }

    /* == Getters & setters == */
    public Lexer getLexer() {
        return lexer;
    }

    private void setLexer(Lexer lexer) {
        if(lexer == null) {
            logger.logFatalErr("Invalid lexer state!");
        }

        this.lexer = lexer;
    }

    public Token getCurrToken() {
        return currToken;
    }

    private void setCurrToken(Token currToken) {
        if(currToken == null) {
            logger.logFatalErr("Invalid current token!");
        }

        this.currToken = currToken;
    }
}

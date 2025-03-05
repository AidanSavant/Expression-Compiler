package Compiler.models.Parser.Ast.Interfaces;

import Compiler.models.Lexer.TokenKind;

public interface Expr {
    /* == Abstract method(s) == */
    String genx64();
}

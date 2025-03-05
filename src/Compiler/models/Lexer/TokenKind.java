package Compiler.models.Lexer;

public enum TokenKind {
    /* === Integer literal === */
    INT_LIT,

    /* === Arithmetic binary operations === */
    ADD_OP, SUB_OP, MUL_OP, DIV_OP,

    /* === Logical binary operations === */
    AND_OP, OR_OP,

    /* === Logical unary operation(s) === */
    NOT_OP,

    /* === Parentheses === */
    LEFT_PAREN, RIGHT_PAREN,

    /* === Misc === */
    EOF,
    INVALID,
}

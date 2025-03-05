package Compiler.models.Parser.Ast.Impls;

import Compiler.models.CodeGen.CodeGen;
import Compiler.models.Lexer.TokenKind;
import Compiler.models.Parser.Ast.Interfaces.Expr;

public class BinaryOp implements Expr {
    private Expr lhs, rhs;
    private TokenKind kind;

    private final CodeGen codeGen = new CodeGen();

    /* == Public interface == */
    public BinaryOp(Expr lhs, Expr rhs, TokenKind kind) {
        this.setLhs(lhs);
        this.setRhs(rhs);
        this.setKind(kind);
    }

    /* == Getters & setters == */
    public Expr getLhs() {
        return lhs;
    }

    public void setLhs(Expr lhs) {
        this.lhs = lhs;
    }

    public Expr getRhs() {
        return rhs;
    }

    public void setRhs(Expr rhs) {
        this.rhs = rhs;
    }

    public TokenKind getKind() {
        return kind;
    }

    public void setKind(TokenKind kind) {
        this.kind = kind;
    }

    /* == Code generation == */
    @Override
    public String genx64() {
        return codeGen.genCode(getKind(), getLhs(), getRhs());
    }

    @Override
    public String toString() {
        return "(" + getLhs().toString() + " " + getKind().toString() + " " + getRhs().toString() + ")";
    }
}

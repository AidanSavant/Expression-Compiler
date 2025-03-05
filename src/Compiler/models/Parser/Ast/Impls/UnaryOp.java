package Compiler.models.Parser.Ast.Impls;

import Compiler.models.CodeGen.CodeGen;
import Compiler.models.Lexer.TokenKind;
import Compiler.models.Parser.Ast.Interfaces.Expr;

public class UnaryOp implements Expr {
    private Expr operand;
    private final CodeGen codeGen = new CodeGen();

    public UnaryOp(Expr operand) {
        this.setOperand(operand);
    }

    public Expr getOperand() {
        return operand;
    }

    private void setOperand(Expr operand) {
        this.operand = operand;
    }

    @Override
    public String genx64() {
        return codeGen.genCode(TokenKind.NOT_OP, getOperand(), null);
    }

    @Override
    public String toString() {
        return "~" + getOperand().toString();
    }
}

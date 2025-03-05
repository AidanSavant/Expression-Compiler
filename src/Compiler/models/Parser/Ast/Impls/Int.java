package Compiler.models.Parser.Ast.Impls;

import Compiler.models.Parser.Ast.Interfaces.Expr;

public class Int implements Expr {
    private int value;

    public Int(int value) {
        this.setValue(value);
    }

    public int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    @Override
    public String genx64() {
        return "mov rax, " + getValue() + "\n";
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}

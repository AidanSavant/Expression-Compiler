package Compiler.models.CodeGen;

import java.util.HashMap;
import java.util.function.BiFunction;

import Compiler.models.Lexer.TokenKind;
import Compiler.models.Parser.Ast.Interfaces.Expr;

public class CodeGen {
    private final HashMap<TokenKind, BiFunction<Expr, Expr, String>> codeGenerators = new HashMap<>() {{
        /* == Arithmetic binary operations == */
        put(TokenKind.ADD_OP, (left, right) -> genAdd(left, right));
        put(TokenKind.SUB_OP, (left, right) -> genSub(left, right));
        put(TokenKind.MUL_OP, (left, right) -> genMul(left, right));
        put(TokenKind.DIV_OP, (left, right) -> genDiv(left, right));

        /* == Logical binary operations == */
        put(TokenKind.AND_OP, (left, right) -> genAnd(left, right));
        put(TokenKind.OR_OP,  (left, right) -> genOr(left, right));

        /* == Logical unary operation(s) == */
        put(TokenKind.NOT_OP, (left, _right) -> genNot(left, null));
    }};

    /* == Getter(s) == */
    public HashMap<TokenKind, BiFunction<Expr, Expr, String>> getCodeGenerators() {
        return codeGenerators;
    }

    /* === Public interface === */
    public String genCode(TokenKind nodeKind, Expr left, Expr right) {
        return getCodeGenerators()
                .get(nodeKind)
                .apply(left, right);
    }

    /* === Private interface === */
    private StringBuilder genLeftRight(Expr left, Expr right) {
        StringBuilder x64 = new StringBuilder();

        x64.append(left.genx64()).append("push rax\n");
        x64.append(right.genx64()).append("pop rbx\n");

        return x64;
    }

    /* == Arithmetic binary operations == */
    private String genAdd(Expr left, Expr right) {
        return genLeftRight(left, right)
                .append("add rax, rbx\n")
                .toString();
    }

    private String genSub(Expr left, Expr right) {
        return genLeftRight(right, left)
                .append("sub rax, rbx\n")
                .toString();
    }

    private String genMul(Expr left, Expr right) {
        return genLeftRight(left, right)
                .append("imul rax, rbx\n")
                .toString();
    }

    private String genDiv(Expr left, Expr right) {
        return genLeftRight(right, left)
                .append("xor rdx, rdx\n")
                .append("div rbx\n")
                .toString();
    }

    /* == Logical binary operations == */
    private String genAnd(Expr left, Expr right) {
        return genLeftRight(left, right)
                .append("and rax, rbx")
                .toString();
    }

    private String genOr(Expr left, Expr right) {
        return genLeftRight(left, right)
                .append("or rax, rbx")
                .toString();
    }

    /* == Logical unary operations == */
    private String genNot(Expr left, Expr _right) {
        return left.genx64() + "\n" + "not rax\n";
    }
}

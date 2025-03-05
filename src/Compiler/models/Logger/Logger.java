package Compiler.models.Logger;

public class Logger {
    /* === Callee env info === */
    private final StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
    private final String className = caller.getClassName();
    private final String methodName = caller.getMethodName();
    private final int lineNum = caller.getLineNumber();

    public void logFatalErr(String msg) {
        System.err.printf("[%s:%d] [%s]: %s%s%s%n", className, lineNum, methodName, "\u001B[31m", msg, "\u001B[0m");
        System.exit(1);
    }
}

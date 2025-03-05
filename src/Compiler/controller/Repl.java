package Compiler.controller;

import Compiler.views.CompilerView;

import java.util.Scanner;

public class Repl {
    private final CompilerView compilerView = new CompilerView();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while(true) {
            System.out.print("> ");
            System.out.flush();

            compilerView.viewCompilation(scanner.nextLine());
        }
    }
}

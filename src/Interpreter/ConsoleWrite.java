package Interpreter;

import AST.BuiltInMethodDeclarationNode;

import java.util.List;

public class ConsoleWrite extends BuiltInMethodDeclarationNode {
    @Override
    public List<InterpreterDataType> Execute(List<InterpreterDataType> params) {
        for (var i : params) {
            System.out.print(i.toString());
        }
        System.out.println();
        return List.of();
    }
}

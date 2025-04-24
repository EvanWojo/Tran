package AST;

import Interpreter.InterpreterDataType;

import java.util.List;

public abstract class BuiltInMethodDeclarationNode extends MethodDeclarationNode {
    public boolean isVariadic = false;
    public abstract List<InterpreterDataType> Execute(List<InterpreterDataType> params);
    @Override
    public String toString() {
        return "Built-in method, isVariadic = " + isVariadic + super.toString();
    }
}

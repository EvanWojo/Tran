package AST;

import java.util.ArrayList;
import java.util.List;

public class ConstructorNode implements Node {
    public List<VariableDeclarationNode> parameters = new ArrayList<>();
    public List<VariableDeclarationNode> locals = new ArrayList<>();
    public List<StatementNode> statements = new ArrayList<>();

    @Override
    public String toString() {
        return "Constructor: " + Node.variableDeclarationListToString(parameters) + "\n" + Node.variableDeclarationListToString(locals) + "\n" + Node.statementListToString(statements);
    }
}

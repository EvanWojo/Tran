package AST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoopNode implements StatementNode {
    public Optional<VariableReferenceNode> assignment;
    public ExpressionNode expression;
    public List<StatementNode> statements = new ArrayList<>();

    @Override
    public String toString() {
        return "loop " + (assignment.map(variableReferenceNode -> variableReferenceNode.name + " = ").orElse("")) + expression + "\n" + Node.statementListToString(statements);
    }
}

package AST;

public class AssignmentNode implements StatementNode {
    public VariableReferenceNode target;
    public ExpressionNode expression;

    @Override
    public String toString() {
        return  target + " = " + expression + "\n";
    }

}

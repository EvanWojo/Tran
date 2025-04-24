package AST;

public class BooleanOpNode implements ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;
    public enum BooleanOperations { and, or }
    public BooleanOperations op;
    @Override
    public String toString() {
        return left + ((op== BooleanOperations.and) ? " and " : " or ") + right;
    }
}

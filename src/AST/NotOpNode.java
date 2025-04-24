package AST;

public class NotOpNode implements ExpressionNode {
    public ExpressionNode left;
    @Override
    public String toString() {
        return "not " + left.toString();
    }
}

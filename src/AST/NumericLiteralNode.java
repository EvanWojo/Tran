package AST;

public class NumericLiteralNode implements ExpressionNode {
    public float value;

    @Override
    public String toString() {
        return value + " ";
    }
}

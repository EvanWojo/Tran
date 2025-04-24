package AST;

public class StringLiteralNode implements ExpressionNode {
    public String value;

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}

package AST;

public class CharLiteralNode implements ExpressionNode {
    public char value;
    @Override
    public String toString() {
        return "'" + value + "'";
    }
}

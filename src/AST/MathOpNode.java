package AST;

public class MathOpNode implements ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;
    public enum MathOperations { add, subtract, multiply, divide, modulo }
    public MathOperations op;

    private String opToString() {
        switch (op) {
            case add -> {return " + ";}
            case subtract -> {return " - ";}
            case multiply -> {return " * ";}
            case divide -> {return " / ";}
            case modulo -> {return " % ";}
        }
        throw new AssertionError("Unreachable");
    }

    @Override
    public String toString() {
        return left + opToString() + right;
    }
}

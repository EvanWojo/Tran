package AST;

public class CompareNode implements ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;
    public enum CompareOperations { lt, le, gt, ge, eq, ne}
    public CompareOperations op;
    private String opToString() {
        switch (op) {
            case lt -> {return " < ";}
            case le -> {return " <= ";}
            case gt -> {return " > ";}
            case ge -> {return " >= ";}
            case eq -> {return " == ";}
            case ne -> {return " != ";}
        }
        throw new AssertionError("Unreachable");
    }

    @Override
    public String toString() {
        return left + opToString() + right;
    }
}

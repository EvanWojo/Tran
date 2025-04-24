package AST;

import java.util.ArrayList;
import java.util.List;

public class NewNode implements Node, ExpressionNode{
    public String className;
    public List<ExpressionNode> parameters = new ArrayList<>();

    @Override
    public String toString() {
        return "new " + className + "(" + Node.parameterListToString(parameters) + ")";
    }
}

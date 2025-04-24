package AST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// This is used for cases where a method call is part of an expression.
// This pre-supposes that the method has only one return value.
public class MethodCallExpressionNode implements ExpressionNode {
    public Optional<String> objectName;
    public String methodName;
    public List<ExpressionNode> parameters = new ArrayList<>();
    @Override
    public String toString() {
        return (objectName.map(s -> s + ".").orElse("")) +
                methodName + " (" + Node.parameterListToString(parameters) + ")" ;
    }
}

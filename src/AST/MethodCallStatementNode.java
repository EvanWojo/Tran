package AST;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

// This is used in the case of a method call NOT used as an expression.
// This allows for multiple return values.
public class MethodCallStatementNode implements StatementNode {
    public MethodCallStatementNode() {
        returnValues = new LinkedList<>();
    }

    public MethodCallStatementNode(MethodCallExpressionNode mce) {
        returnValues = new LinkedList<>();
        objectName = mce.objectName;
        methodName = mce.methodName;
        parameters = mce.parameters;
    }

    public Optional<String> objectName;
    public String methodName;
    public List<VariableReferenceNode> returnValues = new ArrayList<>();
    public List<ExpressionNode> parameters = new ArrayList<>();
    public String toString() {
        return
                Node.variableReferenceListToString(returnValues) + (returnValues.isEmpty() ? "" : " = ") +
                objectName.map(s -> s + ".").orElse("") +
                methodName + " (" + Node.parameterListToString(parameters) + ")" ;
    }
}

package AST;

import java.util.Optional;

public class VariableDeclarationNode implements Node {
    public String type;
    public String name;
    public Optional<ExpressionNode> initializer = Optional.empty();

    @Override
    public String toString() {
        return type + " " + name;
    }
}

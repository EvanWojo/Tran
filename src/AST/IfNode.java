package AST;

import javax.swing.plaf.nimbus.State;
import java.util.List;
import java.util.Optional;

public class IfNode implements StatementNode {
    public ExpressionNode condition;
    public List<StatementNode> statements;
    public Optional<ElseNode> elseStatement;

    @Override
    public String toString() {
        return "if (" + condition + ")\n" + statements + (elseStatement.isEmpty()?"" : elseStatement) + "\n";
    }
}

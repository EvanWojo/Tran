package AST;

import java.util.List;
import java.util.Optional;

public class ElseNode {
    public List<StatementNode> statements;

    @Override
    public String toString() {
        return "else: " + statements;
    }
}

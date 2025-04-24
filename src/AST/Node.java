package AST;

import java.util.List;

public interface Node {
    public abstract String toString();
    static String statementListToString(List<StatementNode> stmts){
        StringBuilder sb = new StringBuilder();
        for (var s : stmts) {
            sb.append(s.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    static String parameterListToString(List<ExpressionNode> params){
        StringBuilder sb = new StringBuilder();
        for (var p : params) {
            sb.append(p.toString());
            sb.append(",");
        }
        return sb.toString();
    }

    static String variableReferenceListToString(List<VariableReferenceNode> vrns){
        StringBuilder sb = new StringBuilder();
        for (var r : vrns)
            sb.append(r.toString()).append(",");
        return sb.toString();
    }

    static String variableDeclarationListToString(List<VariableDeclarationNode> vdns){
        StringBuilder sb = new StringBuilder();
        for (var v : vdns)
            sb.append(v.toString()).append(",");
        return sb.toString();
    }
}

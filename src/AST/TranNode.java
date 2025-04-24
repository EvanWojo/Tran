package AST;

import java.util.LinkedList;
import java.util.List;

public class TranNode {
    public List<ClassNode> Classes = new LinkedList<>();
    public List<InterfaceNode> Interfaces = new LinkedList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i : Interfaces) {
            sb.append(i.toString());
        }
        for (var c : Classes) {
            sb.append(c.toString());
        }
        return sb.toString();
    }
}

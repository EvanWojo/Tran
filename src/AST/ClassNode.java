package AST;

import java.util.ArrayList;
import java.util.List;

public class ClassNode implements Node {
    public String name;
    public List<String> interfaces = new ArrayList<>();

    public List<ConstructorNode> constructors = new ArrayList<>();
    public List<MethodDeclarationNode> methods = new ArrayList<>();
    public List<MemberNode> members = new ArrayList<>();

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Class " + name);
        sb.append(" \n");
        if (!interfaces.isEmpty()) {
            sb.append(" extends ");
            for (var i : interfaces) {
                sb.append(i);
                sb.append(" \n");
            }
        }
        for (var m : members) {
            sb.append(m);
        }
        for (var m : methods) {
            sb.append(m);
            sb.append(" \n");
        }
        return sb.toString();
    }
}




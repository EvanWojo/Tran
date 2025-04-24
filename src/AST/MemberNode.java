package AST;

import java.util.List;
import java.util.Optional;

public class MemberNode implements Node {
    public VariableDeclarationNode declaration;

    @Override
    public String toString() {
        return  declaration +
                "\n" ;
    }
}

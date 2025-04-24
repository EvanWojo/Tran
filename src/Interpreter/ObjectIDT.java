package Interpreter;

import AST.ClassNode;

import java.util.HashMap;

public class ObjectIDT implements InterpreterDataType {
    public final HashMap<String,InterpreterDataType> members = new HashMap<>();
    public final ClassNode astNode;

    public ObjectIDT(ClassNode astNode) {
        this.astNode = astNode;
    }

    @Override
    public void Assign(InterpreterDataType in) {
            throw new RuntimeException("Trying to assign to an object IDT from a " + in.getClass());
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (var m : members.entrySet())
            out.append(m.getKey()).append(" : ").append(m.getValue().toString()).append("\n");
        return out.toString();
    }
}

package Interpreter;

public class StringIDT implements InterpreterDataType {
    public String Value;

    public StringIDT(String s) {
        Value = s;
    }

    @Override
    public void Assign(InterpreterDataType in) {
        if (in instanceof StringIDT inv) {
            Value = inv.Value;
        } else {
            throw new RuntimeException("Trying to assign to a string IDT from a " + in.getClass());
        }
    }

    @Override
    public String toString() {
        return Value;
    }
}

package Interpreter;

public class CharIDT implements InterpreterDataType {
    public char Value;

    public CharIDT(char value) {
        Value = value;
    }

    @Override
    public void Assign(InterpreterDataType in) {
        if (in instanceof CharIDT inv) {
            Value = inv.Value;
        } else {
            throw new RuntimeException("Trying to assign to a character IDT from a " + in.getClass());
        }
    }

    public String toString() {
        return String.valueOf(Value);
    }
}

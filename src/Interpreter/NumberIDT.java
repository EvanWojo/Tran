package Interpreter;

public class NumberIDT implements InterpreterDataType {
    public float Value;

    public NumberIDT(float v) {
        Value = v;
    }

    @Override
    public void Assign(InterpreterDataType in) {
        if (in instanceof NumberIDT inv) {
            Value = inv.Value;
        } else {
            throw new RuntimeException("Trying to assign to a number IDT from a " + in.getClass());
        }
    }

    public String toString() {
        return String.valueOf(Value);
    }
}

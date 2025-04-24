package Interpreter;

import java.util.Optional;

public class ReferenceIDT implements InterpreterDataType{
    public Optional<ObjectIDT> refersTo;
    @Override
    public void Assign(InterpreterDataType in) {
        if (in instanceof ReferenceIDT inv) {
            refersTo = inv.refersTo;
        } else if (in instanceof ObjectIDT obj) {
            refersTo = Optional.of(obj);
        } else {
            throw new RuntimeException("Trying to assign to a reference IDT from a " + in.getClass());
        }
    }

    @Override
    public String toString() {
        if (refersTo.isPresent()) {
            return refersTo.get().toString();
        }
        return "<<<NULL REFERENCE>>>";
    }

}

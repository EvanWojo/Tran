package Interpreter;

import AST.*;

import java.util.*;

public class Interpreter {
    private TranNode top;
    private BuiltInMethodDeclarationNode consoleWrite;

    /**
     * Constructor - get the interpreter ready to run. Set members from parameters and "prepare" the class.
     * <p>
     * Store the tran node.
     * Add any built-in methods to the AST
     *
     * @param top - the head of the AST
     */
    public Interpreter(TranNode top) {
        this.top = top;
        consoleWrite = new ConsoleWrite();
        ClassNode builtInClass = new ClassNode();
        builtInClass.name = "console";
        builtInClass.methods.add(consoleWrite);
        builtInClass.methods.get(0).name = "write";
        builtInClass.methods.get(0).isShared = true;
        top.Classes.add(builtInClass);
    }

    /**
     * This is the public interface to the interpreter. After parsing, we will create an interpreter and call start to
     * start interpreting the code.
     * <p>
     * Search the classes in Tran for a method that is "isShared", named "start", that is not private and has no parameters
     * Call "InterpretMethodCall" on that method, then return.
     * Throw an exception if no such method exists.
     */
    public void start() {
        // Find the "start" method
        for (int i = 0; i < top.Classes.size(); i++) {
            for (int j = 0; j < top.Classes.get(i).methods.size(); j++) {
                if (top.Classes.get(i).methods.get(j).name.equals("start") && top.Classes.get(i).methods.get(j).parameters.isEmpty() && top.Classes.get(i).methods.get(j).isShared && !top.Classes.get(i).methods.get(j).isPrivate) {
                    interpretMethodCall(Optional.empty(), top.Classes.get(i).methods.get(j), new LinkedList<>());
                    return;
                }
            }
        }
        throw new RuntimeException("No 'start' method found");
    }

    //              Running Methods

    /**
     * Find the method (local to this class, shared (like Java's system.out.print), or a method on another class)
     * Evaluate the parameters to have a list of values
     * Use interpretMethodCall() to actually run the method.
     * <p>
     * Call GetParameters() to get the parameter value list
     * Find the method. This is tricky - there are several cases:
     * someLocalMethod() - has NO object name. Look in "object"
     * console.write() - the objectName is a CLASS and the method is shared
     * bestStudent.getGPA() - the objectName is a local or a member
     * <p>
     * Once you find the method, call InterpretMethodCall() on it. Return the list that it returns.
     * Throw an exception if we can't find a match.
     *
     * @param object - the object we are inside right now (might be empty)
     * @param locals - the current local variables
     * @param mc     - the method call
     * @return - the return values
     */
    private List<InterpreterDataType> findMethodForMethodCallAndRunIt(Optional<ObjectIDT> object, HashMap<String, InterpreterDataType> locals, MethodCallStatementNode mc) {
        List<InterpreterDataType> values = getParameters(object, locals, mc); //Get the parameter value list.
        if (mc.objectName.isEmpty() && object.isPresent()) { //No object name
            for (int i = 0; i < object.get().astNode.methods.size(); i++) {
                if (object.get().astNode.methods.get(i).name.equals(mc.methodName)) {
                    return interpretMethodCall(object, object.get().astNode.methods.get(i), values);
                }
            }
        }
        MethodDeclarationNode md = matchMethodName(mc); //Match method name to one in a class
        if (mc.objectName.isPresent()) {
            if (md != null && md.isShared) { //If object name is a class and the method is shared
                for (int i = 0; i < top.Classes.size(); i++) {
                    if (mc.objectName.get().equals(top.Classes.get(i).name))
                        return interpretMethodCall(object, md, values);
                }
            }
            for (int i = 0; i < locals.size(); i++) { //Object name is a local
                if (locals.containsKey(mc.objectName.get()) && md != null) {
                    return interpretMethodCall(((ReferenceIDT)locals.get(mc.objectName.get())).refersTo, md, values);
                }
            }
            for (int i = 0; i < top.Classes.size(); i++) { //Object name is a member
                for (int j = 0; j < top.Classes.get(i).members.size(); j++) {
                    if (mc.objectName.get().equals(top.Classes.get(i).members.get(j).declaration.name)) {
                        return interpretMethodCall(object, md, values);
                    }
                }
            }
        }
        return null;
    }

    //Iterates through the classes and their methods until a method with the matching name is found. Returns null if nothing is found.
    private MethodDeclarationNode matchMethodName(MethodCallStatementNode mc) {

        MethodDeclarationNode method = null;
        for (int i = 0; i < top.Classes.size(); i++) {
            for (int j = 0; j < top.Classes.get(i).methods.size(); j++) {
                if (mc.methodName.equals(top.Classes.get(i).methods.get(j).name)) {
                    method = top.Classes.get(i).methods.get(j);
                }
            }
        }
        return method;
    }

    /**
     * Run a "prepared" method (found, parameters evaluated)
     * This is split from findMethodForMethodCallAndRunIt() because there are a few cases where we don't need to do the finding:
     * in start() and dealing with loops with iterator objects, for example.
     * <p>
     * Check to see if "m" is a built-in. If so, call Execute() on it and return
     * Make local variables, per "m"
     * If the number of passed in values doesn't match m's "expectations", throw
     * Add the parameters by name to locals.
     * Call InterpretStatementBlock
     * Build the return list - find the names from "m", then get the values for those names and add them to the list.
     *
     * @param object - The object this method is being called on (might be empty for shared)
     * @param m      - Which method is being called
     * @param values - The values to be passed in
     * @return the returned values from the method
     */
    private List<InterpreterDataType> interpretMethodCall(Optional<ObjectIDT> object, MethodDeclarationNode m, List<InterpreterDataType> values) {
        var retVal = new LinkedList<InterpreterDataType>();
        if (m instanceof BuiltInMethodDeclarationNode) {
            return consoleWrite.Execute(values);
        }
        HashMap<String, InterpreterDataType> locals = new HashMap<>();
        for (int i = 0; i < m.locals.size(); i++) {
            locals.put(m.locals.get(i).name, instantiate(m.locals.get(i).type));
        }
        List<InterpreterDataType> params = new ArrayList<>(values);
        if (m.parameters.size() != values.size()) {
            throw new RuntimeException("Wrong number of parameters");
        }
        for (int i = 0; i < params.size(); i++) {
            locals.put(m.locals.get(i).name, params.get(i));
        }
        for (int i = 0; i < m.returns.size(); i++) {
            locals.put(m.returns.get(i).name, instantiate(m.returns.get(i).type));
        }
        interpretStatementBlock(object, m.statements, locals);

        for (int i = 0; i < m.locals.size(); i++) {
            if (locals.containsKey(m.locals.get(i).name)) {
                retVal.add(locals.get(m.locals.get(i).name));
            }
        }
        for (int i = 0; i < m.returns.size(); i++) {
            if (locals.containsKey(m.returns.get(i).name)) {
                retVal.add(locals.get(m.returns.get(i).name));
            }
        }
        return retVal;
    }

    //              Running Constructors

    /**
     * This is a special case of the code for methods. Just different enough to make it worthwhile to split it out.
     * <p>
     * Call GetParameters() to populate a list of IDT's
     * Call GetClassByName() to find the class for the constructor
     * If we didn't find the class, throw an exception
     * Find a constructor that is a good match - use DoesConstructorMatch()
     * Call InterpretConstructorCall() on the good match
     *
     * @param callerObj - the object that we are inside when we called the constructor
     * @param locals    - the current local variables (used to fill parameters)
     * @param mc        - the method call for this construction
     * @param newOne    - the object that we just created that we are calling the constructor for
     */
    private void findConstructorAndRunIt(Optional<ObjectIDT> callerObj, HashMap<String, InterpreterDataType> locals, MethodCallStatementNode mc, ObjectIDT newOne) {
        List<InterpreterDataType> values = getParameters(callerObj, locals, mc);
        Optional<ClassNode> classNode = getClassByName(newOne.astNode.name);
        if (classNode.isEmpty()) throw new RuntimeException("Class not found");
        for (int i = 0; i < classNode.get().constructors.size(); i++) {
            if (doesConstructorMatch(classNode.get().constructors.get(i), mc, values))
                interpretConstructorCall(newOne, classNode.get().constructors.get(i), values);
            else
                throw new RuntimeException("Constructor not found");
        }
    }

    /**
     * Similar to interpretMethodCall, but "just different enough" - for example, constructors don't return anything.
     * <p>
     * Creates local variables (as defined by the ConstructorNode), calls Instantiate() to do the creation
     * Checks to ensure that the right number of parameters were passed in, if not throw.
     * Adds the parameters (with the names from the ConstructorNode) to the locals.
     * Calls InterpretStatementBlock
     *
     * @param object - the object that we allocated
     * @param c      - which constructor is being called
     * @param values - the parameter values being passed to the constructor
     */
    private void interpretConstructorCall(ObjectIDT object, ConstructorNode c, List<InterpreterDataType> values) {
        HashMap<String, InterpreterDataType> locals = new HashMap<>();
        for (int i = 0; i < c.locals.size(); i++) {
            locals.put(c.locals.get(i).name, instantiate(c.locals.get(i).type));
        }
        if (values.size() != c.parameters.size()) {
            throw new RuntimeException("Wrong number of parameters");
        }
        for (int i = 0; i < c.parameters.size(); i++) {
            locals.put(c.parameters.get(i).name, values.get(i));
        }
        interpretStatementBlock(Optional.ofNullable(object), c.statements, locals);
    }

    //              Running Instructions

    /**
     * Given a block (which could be from a method or an "if" or "loop" block, run each statement.
     * Blocks, by definition, do ever statement, so iterating over the statements makes sense.
     * <p>
     * For each statement in statements:
     * check the type:
     * For AssignmentNode, FindVariable() to get the target. Evaluate() the expression. Call Assign() on the target with the result of Evaluate()
     * For MethodCallStatementNode, call doMethodCall(). Loop over the returned values and copy the into our local variables
     * For LoopNode - there are 2 kinds.
     * Setup:
     * If this is a Loop over an iterator (an Object node whose class has "iterator" as an interface)
     * Find the "getNext()" method; throw an exception if there isn't one
     * Loop:
     * While we are not done:
     * if this is a boolean loop, Evaluate() to get true or false.
     * if this is an iterator, call "getNext()" - it has 2 return values. The first is a boolean (was there another?), the second is a value
     * If the loop has an assignment variable, populate it: for boolean loops, the true/false. For iterators, the "second value"
     * If our answer from above is "true", InterpretStatementBlock() on the body of the loop.
     * For If - Evaluate() the condition. If true, InterpretStatementBlock() on the if's statements. If not AND there is an else, InterpretStatementBlock on the else body.
     *
     * @param object     - the object that this statement block belongs to (used to get member variables and any members without an object)
     * @param statements - the statements to run
     * @param locals     - the local variables
     */
    private void interpretStatementBlock(Optional<ObjectIDT> object, List<StatementNode> statements, HashMap<String, InterpreterDataType> locals) {

        for (int i = 0; i < statements.size(); i++) {
            switch (statements.get(i)) { //Loop through all the statements passed in
                case AssignmentNode assignmentNode -> {
                    InterpreterDataType target = findVariable(assignmentNode.target.name, locals, object); //Get the target
                    target.Assign(evaluate(locals, object, assignmentNode.expression)); //Assign the evaluation of the expression to the target
                }
                case MethodCallStatementNode methodCallStatementNode -> {
                    List<InterpreterDataType> values = findMethodForMethodCallAndRunIt(object, locals, methodCallStatementNode);
                    if (values == null) throw new RuntimeException("No method call found");
                    for (int j = 0; j < values.size(); j++) {
                        locals.put(values.get(j).getClass().getName(), values.get(j)); //Copy the return values from the method into the local variables
                    }
                }
                case LoopNode loopNode -> {
                    if (object.isPresent()) {
                        MethodDeclarationNode getNext = new MethodDeclarationNode();
                        for (int j = 0; j < object.get().astNode.interfaces.size(); i++) { //Check if iterator is an interface to this object
                            if (object.get().astNode.interfaces.get(j).equals("iterator")) {
                                for (int k = 0; k < object.get().astNode.methods.size(); k++) { //Look for getNext in this object's methods
                                    if (object.get().astNode.methods.get(k).name.equals("getNext"))
                                        getNext = object.get().astNode.methods.get(k);
                                    if (k == object.get().astNode.methods.size() - 1) {
                                        throw new RuntimeException("getNext method not found");
                                    }
                                }

                            }
                        }
                        while (true) { //Iterator loop
                            List<InterpreterDataType> result = interpretMethodCall(object, getNext, locals.values().stream().toList());
                            boolean hasNext = ((BooleanIDT) result.getFirst()).Value; //Boolean which decides to continue the loop
                            if (!hasNext) break;

                            InterpreterDataType next = (InterpreterDataType) result.getLast();
                            if (loopNode.assignment.isPresent() && locals.containsKey(loopNode.assignment.get().name)) {
                                locals.put(loopNode.assignment.get().name, next);
                            }
                            interpretStatementBlock(object, loopNode.statements, locals);
                        }
                    }
                    while (true) { //Non-iterator loop
                        BooleanIDT condition = (BooleanIDT) evaluate(locals, object, loopNode.expression);
                        if (!condition.Value) break;
                        loopNode.assignment.ifPresent(variableReferenceNode -> locals.put(variableReferenceNode.name, condition));
                        interpretStatementBlock(object, loopNode.statements, locals);
                    }
                }
                case IfNode ifNode -> {
                    BooleanIDT condition = (BooleanIDT) evaluate(locals, object, ifNode.condition);
                    if (condition.Value)
                        interpretStatementBlock(object, ifNode.statements, locals);
                    else
                        ifNode.elseStatement.ifPresent(elseNode -> interpretStatementBlock(object, elseNode.statements, locals));
                }
                case null, default -> throw new RuntimeException("Unknown statement type");
            }
        }
    }

    /**
     * evaluate() processes everything that is an expression - math, variables, boolean expressions.
     * There is a good bit of recursion in here, since math and comparisons have left and right sides that need to be evaluated.
     * <p>
     * See the How To Write an Interpreter document for examples
     * For each possible ExpressionNode, do the work to resolve it:
     * BooleanLiteralNode - create a new BooleanLiteralNode with the same value
     * - Same for all the basic data types
     * BooleanOpNode - Evaluate() left and right, then perform either and/or on the results.
     * CompareNode - Evaluate() both sides. Do good comparison for each data type
     * MathOpNode - Evaluate() both sides. If they are both numbers, do the math using the built-in operators. Also handle String + String as concatenation (like Java)
     * MethodCallExpression - call doMethodCall() and return the first value
     * VariableReferenceNode - call findVariable()
     *
     * @param locals     the local variables
     * @param object     - the current object we are running
     * @param expression - some expression to evaluate
     * @return a value
     */
    private InterpreterDataType evaluate(HashMap<String, InterpreterDataType> locals, Optional<ObjectIDT> object, ExpressionNode expression) {
        switch (expression) {
            case BooleanLiteralNode booleanLiteralNode -> {
                return new BooleanIDT(booleanLiteralNode.value);
            }
            case StringLiteralNode stringLiteralNode -> {
                return new StringIDT(stringLiteralNode.value);
            }
            case CharLiteralNode charLiteralNode -> {
                return new CharIDT(charLiteralNode.value);
            }
            case NumericLiteralNode numericLiteralNode -> {
                return new NumberIDT(numericLiteralNode.value);
            }
            case BooleanOpNode booleanOpNode -> {
                boolean left = ((BooleanIDT) evaluate(locals, object, booleanOpNode.left)).Value;
                boolean right = ((BooleanIDT) evaluate(locals, object, booleanOpNode.right)).Value;
                if (booleanOpNode.op.equals(BooleanOpNode.BooleanOperations.and))
                    return new BooleanIDT(left && right);
                else
                    return new BooleanIDT(left || right);
            }
            case CompareNode compareNode -> {
                InterpreterDataType left = evaluate(locals, object, compareNode.left);
                InterpreterDataType right = evaluate(locals, object, compareNode.right);
                if (left instanceof NumberIDT && right instanceof NumberIDT) {
                    switch (compareNode.op) {
                        case eq -> {
                            return new BooleanIDT((((NumberIDT) left)).Value == (((NumberIDT) right)).Value);
                        }
                        case lt -> {
                            return new BooleanIDT((((NumberIDT) left)).Value < ((NumberIDT) right).Value);
                        }
                        case gt -> {
                            return new BooleanIDT((((NumberIDT) left)).Value > ((NumberIDT) right).Value);
                        }
                        case ne -> {
                            return new BooleanIDT((((NumberIDT) left)).Value != ((NumberIDT) right).Value);
                        }
                        case le -> {
                            return new BooleanIDT((((NumberIDT) left)).Value <= ((NumberIDT) right).Value);
                        }
                        case ge -> {
                            return new BooleanIDT((((NumberIDT) left)).Value >= ((NumberIDT) right).Value);
                        }
                        default -> throw new RuntimeException("Unknown operator");
                    }
                }
                if (left instanceof CharIDT && right instanceof CharIDT) {
                    switch (compareNode.op) {
                        case eq -> {
                            return new BooleanIDT(((CharIDT) left).Value == ((CharIDT) right).Value);
                        }
                        case lt -> {
                            return new BooleanIDT(((CharIDT) left).Value < ((CharIDT) right).Value);
                        }
                        case gt -> {
                            return new BooleanIDT(((CharIDT) left).Value > ((CharIDT) right).Value);
                        }
                        case ne -> {
                            return new BooleanIDT(((CharIDT) left).Value != ((CharIDT) right).Value);
                        }
                        case le -> {
                            return new BooleanIDT(((CharIDT) left).Value <= ((CharIDT) right).Value);
                        }
                        case ge -> {
                            return new BooleanIDT(((CharIDT) left).Value >= ((CharIDT) right).Value);
                        }
                        default -> throw new RuntimeException("Unknown operator");
                    }
                }
                throw new RuntimeException("Can't compare with given type");
            }
            case MathOpNode mathOpNode -> {
                InterpreterDataType left = evaluate(locals, object, mathOpNode.left);
                InterpreterDataType right = evaluate(locals, object, mathOpNode.right);
                if (left instanceof NumberIDT && right instanceof NumberIDT) {
                    switch (mathOpNode.op) {
                        case add -> {
                            return new NumberIDT(((NumberIDT) left).Value + ((NumberIDT) right).Value);
                        }
                        case subtract -> {
                            return new NumberIDT(((NumberIDT) left).Value - ((NumberIDT) right).Value);
                        }
                        case multiply -> {
                            return new NumberIDT(((NumberIDT) left).Value * ((NumberIDT) right).Value);
                        }
                        case divide -> {
                            return new NumberIDT(((NumberIDT) left).Value / ((NumberIDT) right).Value);
                        }
                        case modulo -> {
                            return new NumberIDT(((NumberIDT) left).Value % ((NumberIDT) right).Value);
                        }
                        default -> throw new RuntimeException("Unknown operator");
                    }
                }
                if (left instanceof StringIDT && right instanceof StringIDT) {
                    if (mathOpNode.op.equals(MathOpNode.MathOperations.add)) {
                        return new StringIDT(((StringIDT) left).Value.concat(((StringIDT) right).Value));
                    }
                    throw new RuntimeException("Unknown operator");
                }
            }
            case MethodCallExpressionNode methodCallExpressionNode -> {
                return Objects.requireNonNull(findMethodForMethodCallAndRunIt(object, locals, new MethodCallStatementNode(methodCallExpressionNode))).getFirst();
            }
            case VariableReferenceNode variableReferenceNode -> {
                return findVariable(variableReferenceNode.name, locals, object);
            }
            case NewNode newNode -> {
                String className = newNode.className;
                List<ExpressionNode> parameters = newNode.parameters;
                Optional<ClassNode> classNode = getClassByName(className);
                ObjectIDT newObject;
                if (classNode.isPresent()) {
                    newObject = new ObjectIDT(classNode.get());
                    for (int i = 0; i < newObject.astNode.members.size(); i++) {
                        newObject.members.put(newObject.astNode.members.get(i).declaration.name, instantiate(newObject.astNode.members.get(i).declaration.type));
                    }
                } else
                    throw new RuntimeException("Unknown class " + className);
                MethodCallStatementNode constructorCall = new MethodCallStatementNode();
                constructorCall.methodName = "construct";
                constructorCall.parameters = parameters;
                findConstructorAndRunIt(object, locals, constructorCall, newObject);
                ReferenceIDT referenceIDT = new ReferenceIDT();
                referenceIDT.Assign(newObject);
                return referenceIDT;
            }
            default -> throw new RuntimeException("Unknown expression type");
        }
        throw new IllegalArgumentException();
    }

    //              Utility Methods

    /**
     * Used when trying to find a match to a method call. Given a method declaration, does it match this method call?
     * We double-check with the parameters, too, although in theory JUST checking the declaration to the call should be enough.
     * <p>
     * Match names, parameter counts (both declared count vs method call and declared count vs value list), return counts.
     * If all of those match, consider the types (use TypeMatchToIDT).
     * If everything is OK, return true, else return false.
     * Note - if m is a built-in and isVariadic is true, skip all the parameter validation.
     *
     * @param m          - the method declaration we are considering
     * @param mc         - the method call we are trying to match
     * @param parameters - the parameter values for this method call
     * @return does this method match the method call?
     */
    private boolean doesMatch(MethodDeclarationNode m, MethodCallStatementNode mc, List<InterpreterDataType> parameters) {
        if (!m.name.equals(mc.methodName)) return false;
        if (!(m instanceof BuiltInMethodDeclarationNode)) {
            if (m.parameters.size() != mc.parameters.size()) return false;
            if (m.parameters.size() != parameters.size()) return false;
            if (m.returns.size() != mc.returnValues.size()) return false;
            for (int i = 0; i < parameters.size(); i++) {
                if (!typeMatchToIDT(m.parameters.get(i).type, parameters.get(i))) return false;
            }
        }
        return true;
    }

    /**
     * Very similar to DoesMatch() except simpler - there are no return values, the name will always match.
     *
     * @param c          - a particular constructor
     * @param mc         - the method call
     * @param parameters - the parameter values
     * @return does this constructor match the method call?
     */
    private boolean doesConstructorMatch(ConstructorNode c, MethodCallStatementNode mc, List<InterpreterDataType> parameters) {
        if (c.parameters.size() != mc.parameters.size()) return false;
        for (int i = 0; i < mc.parameters.size(); i++) {
            if(!typeMatchToIDT(c.parameters.get(i).type, parameters.get(i))) return false;
        }
        return true;
    }

    /**
     * Used when we call a method to get the list of values for the parameters.
     * <p>
     * for each parameter in the method call, call Evaluate() on the parameter to get an IDT and add it to a list
     *
     * @param object - the current object
     * @param locals - the local variables
     * @param mc     - a method call
     * @return the list of method values
     */
    private List<InterpreterDataType> getParameters(Optional<ObjectIDT> object, HashMap<String, InterpreterDataType> locals, MethodCallStatementNode mc) {
        List<InterpreterDataType> parameters = new ArrayList<>();
        for (ExpressionNode p : mc.parameters) {
            parameters.add(evaluate(locals, object, p));
        }
        return parameters;
    }

    /**
     * Used when we have an IDT and we want to see if it matches a type definition
     * Commonly, when someone is making a function call - do the parameter values match the method declaration?
     * <p>
     * If the IDT is a simple type (boolean, number, etc) - does the string type match the name of that IDT ("boolean", etc)
     * If the IDT is an object, check to see if the name matches OR the class has an interface that matches
     * If the IDT is a reference, check the inner (refered to) type
     *
     * @param type the name of a data type (parameter to a method)
     * @param idt  the IDT someone is trying to pass to this method
     * @return is this OK?
     */
    private boolean typeMatchToIDT(String type, InterpreterDataType idt) {
        switch (type) {
            case "string":
                if (idt instanceof StringIDT) {
                    return true;
                }
                break;
            case "boolean":
                if (idt instanceof BooleanIDT) {
                    return true;
                }
                break;
            case "character":
                if (idt instanceof CharIDT) {
                    return true;
                }
                break;
            case "number":
                if (idt instanceof NumberIDT) {
                    return true;
                }
                break;
        }
        if (idt instanceof ObjectIDT) {
            if (((ObjectIDT) idt).astNode.name.equals(type)) {
                return true;
            } else {
                for (int i = 0; i < ((ObjectIDT) idt).astNode.interfaces.size(); i++) {
                    if (((ObjectIDT) idt).astNode.interfaces.get(i).equals(type)) {
                        return true;
                    }
                }
            }
        }
        if (idt instanceof ReferenceIDT) {
            if (((ReferenceIDT) idt).refersTo.get().astNode.name.equals(type)) {
                return true;
            } else {
                for (int i = 0; i < ((ReferenceIDT) idt).refersTo.get().astNode.interfaces.size(); i++) {
                    if (((ReferenceIDT) idt).refersTo.get().astNode.interfaces.get(i).equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Find a method in an object that is the right match for a method call (same name, parameters match, etc. Uses doesMatch() to do most of the work)
     * <p>
     * Given a method call, we want to loop over the methods for that class, looking for a method that matches (use DoesMatch) or throw
     *
     * @param object     - an object that we want to find a method on
     * @param mc         - the method call
     * @param parameters - the parameter value list
     * @return a method or throws an exception
     */
    private MethodDeclarationNode getMethodFromObject(ObjectIDT object, MethodCallStatementNode mc, List<InterpreterDataType> parameters) {
        for (int i = 0; i < object.astNode.methods.size(); i++) {
            if (doesMatch(object.astNode.methods.get(i), mc, parameters)) {
                return object.astNode.methods.get(i);
            }
        }
        throw new RuntimeException("Unable to resolve method call " + mc);
    }

    /**
     * Find a class, given the name. Just loops over the TranNode's classes member, matching by name.
     * <p>
     * Loop over each class in the top node, comparing names to find a match.
     *
     * @param name Name of the class to find
     * @return either a class node or empty if that class doesn't exist
     */
    private Optional<ClassNode> getClassByName(String name) {
        for (int i = 0; i < top.Classes.size(); i++) {
            if (top.Classes.get(i).name.equals(name)) {
                return Optional.of(top.Classes.get(i));
            }
        }
        return Optional.empty();
    }

    /**
     * Given an execution environment (the current object, the current local variables), find a variable by name.
     *
     * @param name   - the variable that we are looking for
     * @param locals - the current method's local variables
     * @param object - the current object (so we can find members)
     * @return the IDT that we are looking for or throw an exception
     */
    private InterpreterDataType findVariable(String name, HashMap<String, InterpreterDataType> locals, Optional<ObjectIDT> object) {
        if (locals.containsKey(name)) {
            return locals.get(name);
        }
        if (object.isPresent()) {
            if (object.get().members.containsKey(name)) {
                return object.get().members.get(name);
            }
        }
        throw new RuntimeException("Unable to find variable " + name);
    }

    /**
     * Given a string (the type name), make an IDT for it.
     *
     * @param type The name of the type (string, number, boolean, character). Defaults to ReferenceIDT if not one of those.
     * @return an IDT with default values (0 for number, "" for string, false for boolean, ' ' for character)
     */
    private InterpreterDataType instantiate(String type) {
        return switch (type) {
            case "string" -> new StringIDT("");
            case "number" -> new NumberIDT(0);
            case "boolean" -> new BooleanIDT(false);
            case " character" -> new CharIDT(' ');
            default -> new ReferenceIDT();
        };
    }
}

package Tests;

import AST.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;

public class ParserTests {
    private TranNode LexAndParse(String input, int tokenCount) throws Exception {
        var l = new Lexer(input);
        var tokens = l.Lex();
        Assertions.assertEquals(tokenCount, tokens.size());
        var tran = new TranNode();
        var p = new Parser(tran, tokens);
        p.Tran();
        return tran;
    }

    @Test
    public void testInterface() throws Exception {
        var t = LexAndParse("interface someName\r\n\tupdateClock()\r\n\tsquare() : number s", 15);
        Assertions.assertEquals(1, t.Interfaces.size());
        Assertions.assertEquals(2, t.Interfaces.getFirst().methods.size());
    }

    @Test
    public void testClassWithOneMethod() throws Exception {
        var t = LexAndParse("class Tran\r\n\thelloWorld()\r\n\t\tx = 1 + 1", 16);
        Assertions.assertEquals(1, t.Classes.size());
        Assertions.assertEquals(1, t.Classes.getFirst().methods.size());
        Assertions.assertEquals(1, t.Classes.getFirst().methods.getFirst().statements.size());
    }

    @Test
    public void testClassWithMultipleMembers() throws Exception {
        var t = LexAndParse("class Tran\n" +
                "\tnumber w\n" +
                "\tstring x\n" +
                "\tboolean y\n" +
                "\tcharacter z", 16);
        Assertions.assertEquals(1, t.Classes.size());
        Assertions.assertEquals(4, t.Classes.getFirst().members.size());
        var m = t.Classes.getFirst().members;
        Assertions.assertEquals("number", m.getFirst().declaration.type);
        Assertions.assertEquals("w", m.getFirst().declaration.name);
        Assertions.assertEquals("string", m.get(1).declaration.type);
        Assertions.assertEquals("x", m.get(1).declaration.name);
        Assertions.assertEquals("boolean", m.get(2).declaration.type);
        Assertions.assertEquals("y", m.get(2).declaration.name);
        Assertions.assertEquals("character", m.get(3).declaration.type);
        Assertions.assertEquals("z", m.get(3).declaration.name);
    }

    @Test
    public void testClassWithMethodsAndMembers() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\tnumber w\n" +
                        "\tstring x\n" +
                        "\tboolean y\n" +
                        "\tcharacter z\n" +
                        "\thelloWorld()\n" +
                        "\t\tx = 1 + 1"
                , 28);
        Assertions.assertEquals(1, t.Classes.size());
        var m = t.Classes.getFirst().members;
        Assertions.assertEquals(4, t.Classes.getFirst().members.size()); // scramble test order to break the "duplicate code" warning
        Assertions.assertEquals("boolean", m.get(2).declaration.type);
        Assertions.assertEquals("y", m.get(2).declaration.name);
        Assertions.assertEquals("character", m.get(3).declaration.type);
        Assertions.assertEquals("z", m.get(3).declaration.name);
        Assertions.assertEquals("string", m.get(1).declaration.type);
        Assertions.assertEquals("x", m.get(1).declaration.name);
        Assertions.assertEquals("number", m.getFirst().declaration.type);
        Assertions.assertEquals("w", m.getFirst().declaration.name);

        Assertions.assertEquals(1, t.Classes.getFirst().methods.size());
        Assertions.assertEquals(1, t.Classes.getFirst().methods.getFirst().statements.size());
    }

    @Test
    public void testClassIf() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tif n>100\n" +
                        "\t\t\tkeepGoing = false"
                , 21);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertEquals("AST.IfNode", myMethod.statements.getFirst().getClass().getName());
        Assertions.assertTrue(((IfNode) (myMethod.statements.getFirst())).elseStatement.isEmpty());
    }

    @Test
    public void testClassIfElse() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tif n>100\n" +
                        "\t\t\tkeepGoing = false\n" +
                        "\t\telse \n" +
                        "\t\t\tif n>=500\n" +
                        "\t\t\t\tkeepGoing = true\n"
                , 37);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertEquals("AST.IfNode", myMethod.statements.getFirst().getClass().getName());
        Assertions.assertTrue(((IfNode) (myMethod.statements.getFirst())).elseStatement.isPresent());
        Assertions.assertEquals(1, ((IfNode) (myMethod.statements.getFirst())).elseStatement.orElseThrow().statements.size());
    }

    @Test
    public void testLoopVariable() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tloop n\n" +
                        "\t\t\tkeepGoing = false\n"
                , 20);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertInstanceOf(LoopNode.class, myMethod.statements.getFirst());
    }

    @Test
    public void testLoopCondition() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tloop n<100\n" +
                        "\t\t\tkeepGoing = false\n"
                , 22);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertInstanceOf(LoopNode.class, myMethod.statements.getFirst());
        Assertions.assertInstanceOf(CompareNode.class, ((LoopNode) myMethod.statements.getFirst()).expression);
    }

    @Test
    public void testLoopConditionWithVariable() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tloop c = n<100\n" +
                        "\t\t\tkeepGoing = false\n"
                , 24);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertInstanceOf(LoopNode.class, myMethod.statements.getFirst());
        Assertions.assertInstanceOf(CompareNode.class, ((LoopNode) myMethod.statements.getFirst()).expression);
        Assertions.assertTrue(((LoopNode) myMethod.statements.getFirst()).assignment.isPresent());
    }

    @Test
    public void testMethodCallWithMulitpleVariables() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\ta,b,c,d,e = doSomething()\n"
                , 25);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        var firstStatement = myMethod.statements.getFirst();
        Assertions.assertInstanceOf(MethodCallStatementNode.class, firstStatement);
        Assertions.assertEquals(5, ((MethodCallStatementNode) firstStatement).returnValues.size());
    }

    @Test
    public void testDeclarationAndInitialization() throws Exception {
        var t = LexAndParse("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tnumber n = 5.0\n"
                , 16);
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(0, myMethod.statements.size());
        var firstLocal = myMethod.locals.getFirst();
        Assertions.assertTrue(firstLocal.initializer.isPresent());
        Assertions.assertEquals(5, ((NumericLiteralNode)(firstLocal.initializer.get())).value);
    }
}

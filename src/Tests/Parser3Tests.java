package Tests;

import AST.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Tran.*;
public class Parser3Tests
{
    @Test
    public void testDisambiguate() throws Exception {

        var l = new Lexer("class Tran\n" +
                "\thelloWorld() : number a, number b, number avg\n" +
                "\t\ta=b\n" +
                "\t\tb=a\n"+
                "\t\tavg=b\n" +
                "\tnumber z\n" +
                "\tnumber x\n" +
                "\tnumber y\n" );
        var tokens= l.Lex();
        TranNode t = new TranNode();
        Parser p = new Parser(t, tokens);
        p.Tran();
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(3, myMethod.statements.size());

        Assertions.assertEquals("b = a\n", ((AssignmentNode)myMethod.statements.get(1)).toString());
        Assertions.assertInstanceOf(AssignmentNode.class, myMethod.statements.get(2));

    }

    @Test
    public void testVariableReference() throws Exception {
        var l = new Lexer("class Tran\n" +
                "\thelloWorld() : number a, number b, number avg\n" +
                "\t\ta=b\n" +
                "\t\tb=avg\n"+
                "\t\tavg=a\n" +
                "\tnumber z\n" +
                "\tnumber x\n" +
                "\tnumber y\n" +
                "" );
        var tokens = l.Lex();
        TranNode t = new TranNode();
        Parser p = new Parser(t, tokens);
        p.Tran();
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.get(0);

        Assertions.assertEquals("b", ((VariableReferenceNode)((AssignmentNode)myMethod.statements.get(1)).target).toString());
        Assertions.assertEquals("avg", ((VariableReferenceNode)((AssignmentNode)myMethod.statements.get(2)).target).toString());

        Assertions.assertEquals("[number a, number b, number avg]", ((myMethod.returns)).toString());

    }
    @Test
    public void testassign() throws Exception {


        var l = new Lexer("class Tran\n" +
                "\thelloWorld()\n" +
                "\t\tnumber a\n" +
                "\t\tnumber b\n" +
                "\t\tnumber avg\n" +
                "\t\ta=b\n" +
                "\t\tb=a\n" );
        var tokens = l.Lex();
        TranNode t = new TranNode();
        Parser p = new Parser(t, tokens);
        p.Tran();
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(2, myMethod.statements.size());
        Assertions.assertEquals("a = b\n", (((AssignmentNode) myMethod.statements.get(0)).toString()));
        Assertions.assertEquals("b = a\n", (((AssignmentNode) myMethod.statements.get(1)).toString()));
    }


    @Test
    public void test_if_else() throws Exception {

        var l = new Lexer("class Tran\n" +
                "\thelloWorld()\n" +
                "\t\tif n>b\n" +
                "\t\t\tn = a\n" +
                "\t\telse \n" +
                "\t\t\tif n>=a\n" +
                "\t\t\t\tn=b\n");
        var tokens = l.Lex();
        TranNode t = new TranNode();
        Parser p = new Parser(t, tokens);
        p.Tran();
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertInstanceOf(IfNode.class, myMethod.statements.getFirst());
        Assertions.assertEquals("n > b", ((IfNode) myMethod.statements.getFirst()).condition.toString());

        Assertions.assertFalse(((IfNode) (myMethod.statements.getFirst())).elseStatement.isEmpty());
    }
    @Test
    public void testloop() throws Exception {

        var l = new Lexer("class Tran\n" +
                "\thelloWorld()\n" +
                "\t\tloop n>a \n" +
                "\t\t\tn = a");
        var tokens = l.Lex();
        TranNode t = new TranNode();
        Parser p = new Parser(t, tokens);
        p.Tran();
        Assertions.assertEquals(1, t.Classes.size());
        var myClass = t.Classes.getFirst();
        Assertions.assertEquals(1, myClass.methods.size());
        var myMethod = myClass.methods.getFirst();
        Assertions.assertEquals(1, myMethod.statements.size());
        Assertions.assertEquals("AST.LoopNode", myMethod.statements.getFirst().getClass().getName());
        Assertions.assertEquals("n > a", ( ( ((LoopNode) myMethod.statements.getFirst()).expression)).toString());
    }
}
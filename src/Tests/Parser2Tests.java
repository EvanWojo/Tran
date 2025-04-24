package Tests;

import AST.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import Tran.*;
public class Parser2Tests {

    // Test 1: Class Parsing
    @Test
    public void testClassParsing() throws Exception {
        var tran = new TranNode();
        List list = List.of(
                new Token(Token.TokenTypes.CLASS, 1, 1),
                new Token(Token.TokenTypes.WORD, 1, 2, "Tran"),
                new Token(Token.TokenTypes.NEWLINE, 1, 3),
                new Token(Token.TokenTypes.INDENT, 2, 1),
                new Token(Token.TokenTypes.WORD, 2, 2, "number"),
                new Token(Token.TokenTypes.WORD, 2, 3, "x"),
                new Token(Token.TokenTypes.NEWLINE, 2, 4),
                new Token(Token.TokenTypes.WORD, 4, 2, "string"),
                new Token(Token.TokenTypes.WORD, 4, 3, "y"),
                new Token(Token.TokenTypes.DEDENT, 5, 1)
        );
        var tokens = new LinkedList<>(list);//converting list to linked list so the token manager can handle this
        var p = new Parser(tran, tokens);
        p.Tran();
        Assertions.assertEquals(1, tran.Classes.size());
        Assertions.assertEquals("Tran", tran.Classes.getFirst().name);

    }

    // Test 2: Class with Implements
    /*
The below code is in the token list for this test:
interface someName
    square() : number s
interface someNameTwo
    squareTwo() : number STwo
class TranExample implements someName,someNameTwo
    number x
    string y
     */
    @Test
    public void testClassImplements() throws Exception {
        var tran = new TranNode();
        List list = List.of(
                new Token(Token.TokenTypes.INTERFACE, 1, 1),
                new Token(Token.TokenTypes.WORD, 1, 1, "someName"),
                new Token(Token.TokenTypes.NEWLINE, 1, 3),
                new Token(Token.TokenTypes.INDENT, 2, 1),
                new Token(Token.TokenTypes.WORD, 2, 2, "square"),
                new Token(Token.TokenTypes.LPAREN, 2, 3),
                new Token(Token.TokenTypes.RPAREN, 2, 4),
                new Token(Token.TokenTypes.COLON, 2, 5),
                new Token(Token.TokenTypes.WORD, 2, 6, "number"),
                new Token(Token.TokenTypes.WORD, 2, 7, "s"),
                new Token(Token.TokenTypes.NEWLINE, 2, 8),
                new Token(Token.TokenTypes.DEDENT, 3, 1),
                new Token(Token.TokenTypes.NEWLINE, 4, 2),

                new Token(Token.TokenTypes.INTERFACE, 5, 1),
                new Token(Token.TokenTypes.WORD, 5, 1, "someNameTwo"),
                new Token(Token.TokenTypes.NEWLINE, 5, 3),
                new Token(Token.TokenTypes.INDENT, 6, 1),
                new Token(Token.TokenTypes.WORD, 6, 2, "squareTwo"),
                new Token(Token.TokenTypes.LPAREN, 6, 3),
                new Token(Token.TokenTypes.RPAREN, 6, 4),
                new Token(Token.TokenTypes.COLON, 6, 5),
                new Token(Token.TokenTypes.WORD, 6, 6, "number"),
                new Token(Token.TokenTypes.WORD, 6, 7, "STwo"),
                new Token(Token.TokenTypes.NEWLINE, 6, 8),
                new Token(Token.TokenTypes.DEDENT, 7, 1),
                new Token(Token.TokenTypes.NEWLINE, 8, 2),


                new Token(Token.TokenTypes.CLASS, 9, 1),
                new Token(Token.TokenTypes.WORD, 9, 2, "Tran"),
                new Token(Token.TokenTypes.IMPLEMENTS, 9, 3),
                new Token(Token.TokenTypes.WORD, 9, 4, "someName"),
                new Token(Token.TokenTypes.COMMA, 9, 9),
                new Token(Token.TokenTypes.WORD, 9, 4, "someNameTwo"),
                new Token(Token.TokenTypes.NEWLINE, 9, 3),
                new Token(Token.TokenTypes.INDENT, 10, 1),
                new Token(Token.TokenTypes.WORD, 10, 2, "number"),
                new Token(Token.TokenTypes.WORD, 10, 3, "x"),
                new Token(Token.TokenTypes.NEWLINE, 10, 4),
                new Token(Token.TokenTypes.WORD, 11, 2, "string"),
                new Token(Token.TokenTypes.WORD, 11, 3, "y"),
                new Token(Token.TokenTypes.DEDENT, 11, 1)
        );
        var tokens = new LinkedList<>(list);//converting list to linked list so the token manager can handle this
        var p = new Parser(tran, tokens);
        p.Tran();
        var clazz = tran.Classes.getFirst();
        Assertions.assertEquals("Tran", clazz.name);
        Assertions.assertEquals(2, clazz.interfaces.size());
        Assertions.assertEquals("someName", clazz.interfaces.getFirst());
        Assertions.assertEquals("someNameTwo", clazz.interfaces.get(1));
        Assertions.assertEquals(2, tran.Interfaces.size());


    }
    // Test 3: Constructor Parsing
    /*
class Tran
    number x
    string y
    construct()
     */
    @Test
    public void testConstructorParsing() throws Exception {
        var tran = new TranNode();
        var list = List.of(
                new Token(Token.TokenTypes.CLASS, 1, 1),
                new Token(Token.TokenTypes.WORD, 1, 2, "Tran"),
                new Token(Token.TokenTypes.NEWLINE, 1, 3),
                new Token(Token.TokenTypes.INDENT, 2, 1),
                new Token(Token.TokenTypes.WORD, 2, 2, "number"),
                new Token(Token.TokenTypes.WORD, 2, 3, "x"),
                new Token(Token.TokenTypes.NEWLINE, 2, 4),
                new Token(Token.TokenTypes.WORD, 4, 2, "string"),
                new Token(Token.TokenTypes.WORD, 4, 3, "y"),
                new Token(Token.TokenTypes.NEWLINE, 4, 3),
                new Token(Token.TokenTypes.CONSTRUCT, 5, 2),
                new Token(Token.TokenTypes.LPAREN, 5, 3),
                new Token(Token.TokenTypes.RPAREN, 5, 4),
                new Token(Token.TokenTypes.NEWLINE, 5, 5),
                new Token(Token.TokenTypes.INDENT, 2, 1),
                new Token(Token.TokenTypes.DEDENT, 2, 1),

                new Token(Token.TokenTypes.DEDENT, 8, 1)

        );
        var tokens = new LinkedList<>(list);//converting list to linked list so the token manager can handle this
        var p = new Parser(tran, tokens);
        p.Tran();
        Assertions.assertEquals(1, tran.Classes.getFirst().constructors.size());
        Assertions.assertEquals(0, tran.Classes.getFirst().constructors.getFirst().statements.size());//

    }


    // Test 4: Class with members
    /*
The below code is in the token list for this test:
interface someName
    square() : number s
class TranExample implements someName
    number m
    start()
        number x
        number y
     */
    @Test
    public void testMembers_and_methoddeclaration() throws Exception {
        var tran = new TranNode();
        //Ignore the line and column number here, all you will be using the line number and columnNumber in parser is for printing syntax error in Tran code lexed by you.
        List<Token> list = List.of(
                new Token(Token.TokenTypes.INTERFACE, 1, 9),
                new Token(Token.TokenTypes.WORD, 1, 18, "someName"),
                new Token(Token.TokenTypes.NEWLINE, 2, 0),
                new Token(Token.TokenTypes.INDENT, 2, 4),
                new Token(Token.TokenTypes.WORD, 2, 10, "square"),
                new Token(Token.TokenTypes.LPAREN, 2, 11),
                new Token(Token.TokenTypes.RPAREN, 2, 12),
                new Token(Token.TokenTypes.COLON, 2, 14),
                new Token(Token.TokenTypes.WORD, 2, 21, "number"),
                new Token(Token.TokenTypes.WORD, 2, 23, "s"),
                new Token(Token.TokenTypes.NEWLINE, 3, 0),
                new Token(Token.TokenTypes.DEDENT, 3, 0),
                new Token(Token.TokenTypes.CLASS, 3, 5),
                new Token(Token.TokenTypes.WORD, 3, 17, "TranExample"),
                new Token(Token.TokenTypes.IMPLEMENTS, 3, 28),
                new Token(Token.TokenTypes.WORD, 3, 37, "someName"),
                new Token(Token.TokenTypes.NEWLINE, 4, 0),
                new Token(Token.TokenTypes.INDENT, 4, 4),
                new Token(Token.TokenTypes.WORD, 4, 10, "number"),
                new Token(Token.TokenTypes.WORD, 4, 12, "m"),
                new Token(Token.TokenTypes.NEWLINE, 5, 0),
                new Token(Token.TokenTypes.WORD, 4, 10, "string"),
                new Token(Token.TokenTypes.WORD, 4, 12, "str"),
                new Token(Token.TokenTypes.NEWLINE, 5, 0),
                new Token(Token.TokenTypes.WORD, 5, 9, "start"),
                new Token(Token.TokenTypes.LPAREN, 5, 10),
                new Token(Token.TokenTypes.RPAREN, 5, 11),
                new Token(Token.TokenTypes.NEWLINE, 6, 0),
                new Token(Token.TokenTypes.INDENT, 6, 8),
                new Token(Token.TokenTypes.WORD, 6, 14, "number"),
                new Token(Token.TokenTypes.WORD, 6, 16, "x"),
                new Token(Token.TokenTypes.NEWLINE, 7, 0),
                new Token(Token.TokenTypes.WORD, 7, 14, "number"),
                new Token(Token.TokenTypes.WORD, 7, 16, "y"),
                new Token(Token.TokenTypes.NEWLINE, 8, 0),
                new Token(Token.TokenTypes.DEDENT, 8, 4),
                new Token(Token.TokenTypes.DEDENT, 8, 4)

        );

           /*
        Lexer L= new Lexer("interface someName\n" +
                "    square() : number s\n" +
                "class TranExample implements someName\n" +
                "    number m\n"+
                "    start()\n" +
                "        number x\n" +
                "        number y\n" );
        var LT= L.Lex();
         System.out.println(LT);
        */

        var tokens = new LinkedList<>(list);//converting list to linked list so the token manager can handle this
        var p = new Parser(tran, tokens);
        p.Tran();
        var clazz = tran.Classes.getFirst();
        Assertions.assertEquals("s", tran.Interfaces.get(0).methods.getFirst().returns.get(0).name);
        Assertions.assertEquals("someName", clazz.interfaces.getFirst());
        Assertions.assertEquals(2, tran.Classes.getFirst().members.size());
        Assertions.assertEquals("m", tran.Classes.getFirst().members.getFirst().declaration.name);
        Assertions.assertEquals(2, tran.Classes.getFirst().methods.getFirst().locals.size());
        Assertions.assertEquals("x", tran.Classes.getFirst().methods.getFirst().locals.get(0).name);
        Assertions.assertEquals("y", tran.Classes.getFirst().methods.getFirst().locals.get(1).name);
    }

    @Test
    public void testLoop() throws Exception {
        Lexer L = new Lexer("class Tran\n" +
                        "\thelloWorld()\n" +
                        "\t\tloop\n" );
        var rev= L.Lex();
        TranNode t= new TranNode();
        Parser p= new Parser(t,rev);
        Assertions.assertThrows(SyntaxErrorException.class, p::Tran);
    }

    @Test
    public void testClassIf() throws Exception {
        Lexer L = new Lexer("class Tran\n" +
                "\thelloWorld()\n" +
                "\t\tif\n" );
        var rev= L.Lex();
        TranNode t= new TranNode();
        Parser p= new Parser(t,rev);
        Assertions.assertThrows(SyntaxErrorException.class, p::Tran);
    }
}
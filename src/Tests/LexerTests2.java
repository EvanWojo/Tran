package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;

public class LexerTests2 {
    @Test
    public void KeyWordLexerTest() {
        var l = new Lexer("class interface something if else loop");
        try {
            var res = l.Lex();
            Assertions.assertEquals(6, res.size());
            Assertions.assertEquals(Token.TokenTypes.CLASS, res.get(0).getType());
            Assertions.assertEquals(Token.TokenTypes.INTERFACE, res.get(1).getType());
            Assertions.assertEquals(Token.TokenTypes.WORD, res.get(2).getType());
            Assertions.assertEquals("something", res.get(2).getValue());
            Assertions.assertEquals(Token.TokenTypes.IF, res.get(3).getType());
            Assertions.assertEquals(Token.TokenTypes.ELSE, res.get(4).getType());
            Assertions.assertEquals(Token.TokenTypes.LOOP, res.get(5).getType());
        }
        catch (Exception e) {
            Assertions.fail("exception occurred: " +  e.getMessage());
        }
    }

    @Test
    public void QuotedStringLexerTest() {
        var l = new Lexer("test \"hello\" \"there\" 1.2");
        try {
            var res = l.Lex();
            Assertions.assertEquals(4, res.size());
            Assertions.assertEquals(Token.TokenTypes.WORD, res.get(0).getType());
            Assertions.assertEquals("test", res.get(0).getValue());
            Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, res.get(1).getType());
            Assertions.assertEquals("hello", res.get(1).getValue());
            Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, res.get(2).getType());
            Assertions.assertEquals("there", res.get(2).getValue());
            Assertions.assertEquals(Token.TokenTypes.NUMBER, res.get(3).getType());
            Assertions.assertEquals("1.2", res.get(3).getValue());
        }
        catch (Exception e) {
            Assertions.fail("exception occurred: " +  e.getMessage());
        }
    }

    @Test
    public void TestIt() {
        var l = new Lexer("interface someName\n" +
                "\tupdateClock()\n" +
                "\tsquare() : number s\n" +
                "\t\n" +
                "class tran implements someName { no inheritance }\n" +
                "\tnumber x\n" +
                "\t\taccessor:  { a longer (although nonsensical) accessor }\n" +
                "\t\t\tvalue = 0\n" +
                "\t\t\tloop x.times()\n" +
                "\t\t\t\tvalue = value + 1\n" +
                "\t\t\t\t\n" +
                "\tstring y\n" +
                "\t\taccessor: value = y.clone()\n" +
                "\t\tmutator: y = value\n" +
                "\t{ \n" +
                "\t\tthese enable us to access the values:\n" +
                "\t\tmyTran.x = 5\n" +
                "\t\tconsole.print (myTran.y)\n" +
                "\t}\n" +
                "\n" +
                "\tdatetime now { this is a member}\n" +
                "\t\n" +
                "\tconstruct() \n" +
                "\t\tnow = clock.getDate()\n" +
                "\t\tx = 0\n" +
                "\t\ty = \"\"\n" +
                "\n" +
                "\tconstruct(number n) {overloading}\n" +
                "\t\tnow = clock.getDate()\n" +
                "\t\tx = n\n" +
                "\t\ty = \"\"\n" +
                "\n" +
                "\tupdateClock()\n" +
                "\t\tnow = clock.getDate()\t\t \n" +
                "\n" +
                "\tsquare() : number s\n" +
                "\t\ts = x * x\n" +
                "\n" +
                "\tallMyData() : number n, string t\n" +
                "\t\tt = y.clone() { some built in to string }\n" +
                "\t\tn = x\n" +
                "\n" +
                "\trepeat() : string t\n" +
                "\t\tt = \"\" { loop replaces for/while/do-until }\n" +
                "\t\tloop x.times() { times is a method on number that returns an iterator (1,2,3,4, ..., x)\n" +
                "\t\t\tt = t + y\n" +
                "\n" +
                "\twhileTest() \n" +
                "\t\tboolean keepGoing\n" +
                "\t\tkeepGoing = true\n" +
                "\t\tloop keepGoing\n" +
                "\t\t\tif n > 100\n" +
                "\t\t\t\tkeepGoing = false\n" +
                "\t\tconsole.print (temp)\n" +
                "\t\t\n" +
                "\n" +
                "\n" +
                "\tprintNumbers()\n" +
                "\t\tnumber temp\n" +
                "\t\ttemp = loop x.times()\n" +
                "\t\t\tconsole.print (temp)\n" +
                "\n" +
                "\tshared printNumbers(number t)\n" +
                "\t\t{ referencing x, y now would be an error! }\n" +
                "\t\tnumber temp\n" +
                "\t\ttemp=loop t.times()\n" +
                "\t\t\tconsole.print (temp)\n" +
                "\t\t\t\t\t\n" +
                "\t{ exceptions? }\n" +
                "\t{ generics? } \n" +
                "\n" +
                "\t{ \"normal\" math }\n" +
                "\n");
                try {
                    var res = l.Lex();
                    Assertions.assertNotEquals(1,res.size());
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
    }
}

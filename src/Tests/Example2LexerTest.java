package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Example2LexerTest{

	@Test
	public void Example2LexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class Example2\n"+
			"    number x\n"+
			"    string y\n"+
			"construct() {called when someone uses “new” }\n"+
			"    x = 0\n"+
			"    y = \"\"\n"+
			"doSomeWork()\n"+
			"    x=10\n"+
			"    loop x.times()\n"+
			"        console.print(\"In The Block\")\n"+
			"    console.print(\"Out of the block\")\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(58, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("Example2", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(4).getType());
		Assertions.assertEquals("number", tokens.get(4).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("x", tokens.get(5).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(7).getType());
		Assertions.assertEquals("string", tokens.get(7).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(8).getType());
		Assertions.assertEquals("y", tokens.get(8).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(9).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(10).getType());
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(11).getType());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(12).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(15).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(16).getType());
		Assertions.assertEquals("x", tokens.get(16).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(17).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(18).getType());
		Assertions.assertEquals("0", tokens.get(18).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(19).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(20).getType());
		Assertions.assertEquals("y", tokens.get(20).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(24).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(25).getType());
		Assertions.assertEquals("doSomeWork", tokens.get(25).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(26).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(28).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(30).getType());
		Assertions.assertEquals("x", tokens.get(30).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(31).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(32).getType());
		Assertions.assertEquals("10", tokens.get(32).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(33).getType());
		Assertions.assertEquals(Token.TokenTypes.LOOP, tokens.get(34).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(35).getType());
		Assertions.assertEquals("x", tokens.get(35).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(36).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(37).getType());
		Assertions.assertEquals("times", tokens.get(37).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(38).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(39).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(40).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(41).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(42).getType());
		Assertions.assertEquals("console", tokens.get(42).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(43).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(44).getType());
		Assertions.assertEquals("print", tokens.get(44).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(45).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(46).getType());
		Assertions.assertEquals("In The Block", tokens.get(46).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(47).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(48).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(49).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(50).getType());
		Assertions.assertEquals("console", tokens.get(50).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(51).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(52).getType());
		Assertions.assertEquals("print", tokens.get(52).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(53).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(54).getType());
		Assertions.assertEquals("Out of the block", tokens.get(54).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(55).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(56).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(57).getType());
	}
}

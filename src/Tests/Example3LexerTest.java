package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Example3LexerTest{

	@Test
	public void Example3LexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class Example3\n"+
			"    number x\n"+
			"    string y\n"+
			"    construct() {called when someone uses \"new\" }\n"+
			"        x = 0\n"+
			"        y = \"\"\n"+
			"    construct(number n) {An example of an overloaded constructor}\n"+
			"        x = n\n"+
			"        y = \"\"\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(41, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("Example3", tokens.get(1).getValue());
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
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(10).getType());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(11).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(12).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(15).getType());
		Assertions.assertEquals("x", tokens.get(15).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(16).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(17).getType());
		Assertions.assertEquals("0", tokens.get(17).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(18).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(19).getType());
		Assertions.assertEquals("y", tokens.get(19).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(20).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(24).getType());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(25).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(26).getType());
		Assertions.assertEquals("number", tokens.get(26).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(27).getType());
		Assertions.assertEquals("n", tokens.get(27).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(28).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(30).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(31).getType());
		Assertions.assertEquals("x", tokens.get(31).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(32).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(33).getType());
		Assertions.assertEquals("n", tokens.get(33).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(34).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(35).getType());
		Assertions.assertEquals("y", tokens.get(35).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(36).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(37).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(38).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(39).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(40).getType());
	}
}

package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Lexer1MixedTestLexerTest{

	@Test
	public void Lexer1MixedTestLexerTest() throws Exception {
		var lexer = new Lexer(
			"implements words class in interface the loop middle if are else new ok private shared construct\n"+
			"words at the beginning implements class interface loop if else new private shared construct class end are also ok\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(37, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.IMPLEMENTS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("words", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(3).getType());
		Assertions.assertEquals("in", tokens.get(3).getValue());
		Assertions.assertEquals(Token.TokenTypes.INTERFACE, tokens.get(4).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("the", tokens.get(5).getValue());
		Assertions.assertEquals(Token.TokenTypes.LOOP, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(7).getType());
		Assertions.assertEquals("middle", tokens.get(7).getValue());
		Assertions.assertEquals(Token.TokenTypes.IF, tokens.get(8).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(9).getType());
		Assertions.assertEquals("are", tokens.get(9).getValue());
		Assertions.assertEquals(Token.TokenTypes.ELSE, tokens.get(10).getType());
		Assertions.assertEquals(Token.TokenTypes.NEW, tokens.get(11).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(12).getType());
		Assertions.assertEquals("ok", tokens.get(12).getValue());
		Assertions.assertEquals(Token.TokenTypes.PRIVATE, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(15).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(16).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(17).getType());
		Assertions.assertEquals("words", tokens.get(17).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(18).getType());
		Assertions.assertEquals("at", tokens.get(18).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(19).getType());
		Assertions.assertEquals("the", tokens.get(19).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(20).getType());
		Assertions.assertEquals("beginning", tokens.get(20).getValue());
		Assertions.assertEquals(Token.TokenTypes.IMPLEMENTS, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.INTERFACE, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.LOOP, tokens.get(24).getType());
		Assertions.assertEquals(Token.TokenTypes.IF, tokens.get(25).getType());
		Assertions.assertEquals(Token.TokenTypes.ELSE, tokens.get(26).getType());
		Assertions.assertEquals(Token.TokenTypes.NEW, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.PRIVATE, tokens.get(28).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(30).getType());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(31).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(32).getType());
		Assertions.assertEquals("end", tokens.get(32).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(33).getType());
		Assertions.assertEquals("are", tokens.get(33).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(34).getType());
		Assertions.assertEquals("also", tokens.get(34).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(35).getType());
		Assertions.assertEquals("ok", tokens.get(35).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(36).getType());
	}
}

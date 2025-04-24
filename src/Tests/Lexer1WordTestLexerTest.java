package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Lexer1WordTestLexerTest{

	@Test
	public void Lexer1WordTestLexerTest() throws Exception {
		var lexer = new Lexer(
			"this is a test of our lexer\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(8, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(0).getType());
		Assertions.assertEquals("this", tokens.get(0).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("is", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(2).getType());
		Assertions.assertEquals("a", tokens.get(2).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(3).getType());
		Assertions.assertEquals("test", tokens.get(3).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(4).getType());
		Assertions.assertEquals("of", tokens.get(4).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("our", tokens.get(5).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(6).getType());
		Assertions.assertEquals("lexer", tokens.get(6).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(7).getType());
	}
}

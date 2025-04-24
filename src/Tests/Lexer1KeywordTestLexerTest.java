package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Lexer1KeywordTestLexerTest{

	@Test
	public void Lexer1KeywordTestLexerTest() throws Exception {
		var lexer = new Lexer(
			"implements class interface loop if else new private shared construct"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(10, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.IMPLEMENTS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(1).getType());
		Assertions.assertEquals(Token.TokenTypes.INTERFACE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.LOOP, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.IF, tokens.get(4).getType());
		Assertions.assertEquals(Token.TokenTypes.ELSE, tokens.get(5).getType());
		Assertions.assertEquals(Token.TokenTypes.NEW, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.PRIVATE, tokens.get(7).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(8).getType());
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(9).getType());
	}
}

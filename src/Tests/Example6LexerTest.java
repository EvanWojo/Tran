package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Example6LexerTest{

	@Test
	public void Example6LexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class Example6\n"+
			"    shared square(number x) : number s\n"+
			"\t\ts = x * x\n"+
			"    private shared start()\n"+
			"\t\tnumber t\n"+
			"\t\tt = 3.07\n"+
			"\t\tt = Example6.square(t)\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(47, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("Example6", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(4).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("square", tokens.get(5).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(7).getType());
		Assertions.assertEquals("number", tokens.get(7).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(8).getType());
		Assertions.assertEquals("x", tokens.get(8).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(9).getType());
		Assertions.assertEquals(Token.TokenTypes.COLON, tokens.get(10).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(11).getType());
		Assertions.assertEquals("number", tokens.get(11).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(12).getType());
		Assertions.assertEquals("s", tokens.get(12).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(15).getType());
		Assertions.assertEquals("s", tokens.get(15).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(16).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(17).getType());
		Assertions.assertEquals("x", tokens.get(17).getValue());
		Assertions.assertEquals(Token.TokenTypes.TIMES, tokens.get(18).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(19).getType());
		Assertions.assertEquals("x", tokens.get(19).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(20).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.PRIVATE, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(24).getType());
		Assertions.assertEquals("start", tokens.get(24).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(25).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(26).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(28).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(29).getType());
		Assertions.assertEquals("number", tokens.get(29).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(30).getType());
		Assertions.assertEquals("t", tokens.get(30).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(31).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(32).getType());
		Assertions.assertEquals("t", tokens.get(32).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(33).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(34).getType());
		Assertions.assertEquals("3.07", tokens.get(34).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(35).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(36).getType());
		Assertions.assertEquals("t", tokens.get(36).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(37).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(38).getType());
		Assertions.assertEquals("Example6", tokens.get(38).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(39).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(40).getType());
		Assertions.assertEquals("square", tokens.get(40).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(41).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(42).getType());
		Assertions.assertEquals("t", tokens.get(42).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(43).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(44).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(45).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(46).getType());
	}
}

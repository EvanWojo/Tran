package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Example7LexerTest{

	@Test
	public void Example7LexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class Example7\n"+
			"    shared allMyData(number x) : number n, string t\n"+
			"        t = \"hello\"\n"+
			"        n = x\n"+
			"    shared start()\n"+
			"        number num \n"+
			"        string s\n"+
			"        num, s = Example7.allMyData(45)\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(52, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("Example7", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(4).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("allMyData", tokens.get(5).getValue());
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
		Assertions.assertEquals("n", tokens.get(12).getValue());
		Assertions.assertEquals(Token.TokenTypes.COMMA, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(14).getType());
		Assertions.assertEquals("string", tokens.get(14).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(15).getType());
		Assertions.assertEquals("t", tokens.get(15).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(16).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(17).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(18).getType());
		Assertions.assertEquals("t", tokens.get(18).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(19).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(20).getType());
		Assertions.assertEquals("hello", tokens.get(20).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(22).getType());
		Assertions.assertEquals("n", tokens.get(22).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(24).getType());
		Assertions.assertEquals("x", tokens.get(24).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(25).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(26).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(28).getType());
		Assertions.assertEquals("start", tokens.get(28).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(30).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(31).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(32).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(33).getType());
		Assertions.assertEquals("number", tokens.get(33).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(34).getType());
		Assertions.assertEquals("num", tokens.get(34).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(35).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(36).getType());
		Assertions.assertEquals("string", tokens.get(36).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(37).getType());
		Assertions.assertEquals("s", tokens.get(37).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(38).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(39).getType());
		Assertions.assertEquals("num", tokens.get(39).getValue());
		Assertions.assertEquals(Token.TokenTypes.COMMA, tokens.get(40).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(41).getType());
		Assertions.assertEquals("s", tokens.get(41).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(42).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(43).getType());
		Assertions.assertEquals("Example7", tokens.get(43).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(44).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(45).getType());
		Assertions.assertEquals("allMyData", tokens.get(45).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(46).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(47).getType());
		Assertions.assertEquals("45", tokens.get(47).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(48).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(49).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(50).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(51).getType());
	}
}

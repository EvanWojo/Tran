package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Example5LexerTest{

	@Test
	public void Example5LexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class Example5\n"+
			"    printNumbers()\n"+
			"\t\tnumber temp\n"+
			"\t\tloop temp = x.times()\n"+
			"\t\t\tconsole.print (temp)\n"+
			"    shared main()\n"+
			"\t\tprintNumbers() \n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(43, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("Example5", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(4).getType());
		Assertions.assertEquals("printNumbers", tokens.get(4).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(5).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(7).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(8).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(9).getType());
		Assertions.assertEquals("number", tokens.get(9).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(10).getType());
		Assertions.assertEquals("temp", tokens.get(10).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(11).getType());
		Assertions.assertEquals(Token.TokenTypes.LOOP, tokens.get(12).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(13).getType());
		Assertions.assertEquals("temp", tokens.get(13).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(15).getType());
		Assertions.assertEquals("x", tokens.get(15).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(16).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(17).getType());
		Assertions.assertEquals("times", tokens.get(17).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(18).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(19).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(20).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(22).getType());
		Assertions.assertEquals("console", tokens.get(22).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(24).getType());
		Assertions.assertEquals("print", tokens.get(24).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(25).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(26).getType());
		Assertions.assertEquals("temp", tokens.get(26).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(28).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(30).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(31).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(32).getType());
		Assertions.assertEquals("main", tokens.get(32).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(33).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(34).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(35).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(36).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(37).getType());
		Assertions.assertEquals("printNumbers", tokens.get(37).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(38).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(39).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(40).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(41).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(42).getType());
	}
}

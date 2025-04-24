package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class Example4LexerTest{

	@Test
	public void Example4LexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class Example4\n"+
			"\tnumber a\n"+
			"{doesn’t require an instance of Example. To use it: Example.helloWorld()  }\n"+
			"    shared helloWorld()  { no parameters, no return value, public} \n"+
			"\t\tconsole.print(\"Hello World\")\n"+
			"\n"+
			"\tadd(number a, number b) : number sum {parameters, return values, public}\n"+
			"\t\tsum = a + b\n"+
			"\t\n"+
			"\tprivate setA()  {can only be called inside the class}\n"+
			"\t\ta = 42 "+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(57, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("Example4", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(4).getType());
		Assertions.assertEquals("number", tokens.get(4).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("a", tokens.get(5).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(7).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(8).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(9).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(10).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(11).getType());
		Assertions.assertEquals("helloWorld", tokens.get(11).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(12).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(15).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(16).getType());
		Assertions.assertEquals("console", tokens.get(16).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(17).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(18).getType());
		Assertions.assertEquals("print", tokens.get(18).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(19).getType());
		Assertions.assertEquals(Token.TokenTypes.QUOTEDSTRING, tokens.get(20).getType());
		Assertions.assertEquals("Hello World", tokens.get(20).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(24).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(25).getType());
		Assertions.assertEquals("add", tokens.get(25).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(26).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(27).getType());
		Assertions.assertEquals("number", tokens.get(27).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(28).getType());
		Assertions.assertEquals("a", tokens.get(28).getValue());
		Assertions.assertEquals(Token.TokenTypes.COMMA, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(30).getType());
		Assertions.assertEquals("number", tokens.get(30).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(31).getType());
		Assertions.assertEquals("b", tokens.get(31).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(32).getType());
		Assertions.assertEquals(Token.TokenTypes.COLON, tokens.get(33).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(34).getType());
		Assertions.assertEquals("number", tokens.get(34).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(35).getType());
		Assertions.assertEquals("sum", tokens.get(35).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(36).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(37).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(38).getType());
		Assertions.assertEquals("sum", tokens.get(38).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(39).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(40).getType());
		Assertions.assertEquals("a", tokens.get(40).getValue());
		Assertions.assertEquals(Token.TokenTypes.PLUS, tokens.get(41).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(42).getType());
		Assertions.assertEquals("b", tokens.get(42).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(43).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(44).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(45).getType());
		Assertions.assertEquals(Token.TokenTypes.PRIVATE, tokens.get(46).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(47).getType());
		Assertions.assertEquals("setA", tokens.get(47).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(48).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(49).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(50).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(51).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(52).getType());
		Assertions.assertEquals("a", tokens.get(52).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(53).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(54).getType());
		Assertions.assertEquals("42", tokens.get(54).getValue());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(55).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(56).getType());
	}
}

package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class FahrenheitLexerTest{

	@Test
	public void FahrenheitLexerTestTest() throws Exception {
		var lexer = new Lexer(
			"class fahrenheit\n"+
			"    number temperature\n"+
			"            \n"+
			"    construct(number t)\n"+
			"        temperature = t\n"+
			"       \n"+
			"    fromCelsius(celsius c)\n"+
			"        temperature = (c.get() * 9 / 5 ) + 32\n"+
			"\n"+
			"    get() : number c\n"+
			"        c = temperature \n"+
			"        \n"+
			"    toCelsius() : celsius c \n"+
			"        number convert\n"+
			"        convert = (temperature -32)*5/9\n"+
			"        c = new celsius(convert)\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(93, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("fahrenheit", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(4).getType());
		Assertions.assertEquals("number", tokens.get(4).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(5).getType());
		Assertions.assertEquals("temperature", tokens.get(5).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(7).getType());
		Assertions.assertEquals(Token.TokenTypes.CONSTRUCT, tokens.get(8).getType());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(9).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(10).getType());
		Assertions.assertEquals("number", tokens.get(10).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(11).getType());
		Assertions.assertEquals("t", tokens.get(11).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(12).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(14).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(15).getType());
		Assertions.assertEquals("temperature", tokens.get(15).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(16).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(17).getType());
		Assertions.assertEquals("t", tokens.get(17).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(18).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(19).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(20).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(21).getType());
		Assertions.assertEquals("fromCelsius", tokens.get(21).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(23).getType());
		Assertions.assertEquals("celsius", tokens.get(23).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(24).getType());
		Assertions.assertEquals("c", tokens.get(24).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(25).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(26).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(28).getType());
		Assertions.assertEquals("temperature", tokens.get(28).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(30).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(31).getType());
		Assertions.assertEquals("c", tokens.get(31).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(32).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(33).getType());
		Assertions.assertEquals("get", tokens.get(33).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(34).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(35).getType());
		Assertions.assertEquals(Token.TokenTypes.TIMES, tokens.get(36).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(37).getType());
		Assertions.assertEquals("9", tokens.get(37).getValue());
		Assertions.assertEquals(Token.TokenTypes.DIVIDE, tokens.get(38).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(39).getType());
		Assertions.assertEquals("5", tokens.get(39).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(40).getType());
		Assertions.assertEquals(Token.TokenTypes.PLUS, tokens.get(41).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(42).getType());
		Assertions.assertEquals("32", tokens.get(42).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(43).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(44).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(45).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(46).getType());
		Assertions.assertEquals("get", tokens.get(46).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(47).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(48).getType());
		Assertions.assertEquals(Token.TokenTypes.COLON, tokens.get(49).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(50).getType());
		Assertions.assertEquals("number", tokens.get(50).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(51).getType());
		Assertions.assertEquals("c", tokens.get(51).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(52).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(53).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(54).getType());
		Assertions.assertEquals("c", tokens.get(54).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(55).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(56).getType());
		Assertions.assertEquals("temperature", tokens.get(56).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(57).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(58).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(59).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(60).getType());
		Assertions.assertEquals("toCelsius", tokens.get(60).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(61).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(62).getType());
		Assertions.assertEquals(Token.TokenTypes.COLON, tokens.get(63).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(64).getType());
		Assertions.assertEquals("celsius", tokens.get(64).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(65).getType());
		Assertions.assertEquals("c", tokens.get(65).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(66).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(67).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(68).getType());
		Assertions.assertEquals("number", tokens.get(68).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(69).getType());
		Assertions.assertEquals("convert", tokens.get(69).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(70).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(71).getType());
		Assertions.assertEquals("convert", tokens.get(71).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(72).getType());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(73).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(74).getType());
		Assertions.assertEquals("temperature", tokens.get(74).getValue());
		Assertions.assertEquals(Token.TokenTypes.MINUS, tokens.get(75).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(76).getType());
		Assertions.assertEquals("32", tokens.get(76).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(77).getType());
		Assertions.assertEquals(Token.TokenTypes.TIMES, tokens.get(78).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(79).getType());
		Assertions.assertEquals("5", tokens.get(79).getValue());
		Assertions.assertEquals(Token.TokenTypes.DIVIDE, tokens.get(80).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(81).getType());
		Assertions.assertEquals("9", tokens.get(81).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(82).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(83).getType());
		Assertions.assertEquals("c", tokens.get(83).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(84).getType());
		Assertions.assertEquals(Token.TokenTypes.NEW, tokens.get(85).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(86).getType());
		Assertions.assertEquals("celsius", tokens.get(86).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(87).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(88).getType());
		Assertions.assertEquals("convert", tokens.get(88).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(89).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(90).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(91).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(92).getType());
	}
}

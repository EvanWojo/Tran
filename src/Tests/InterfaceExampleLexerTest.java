package Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Tran.*;
public class InterfaceExampleLexerTest{

	@Test
	public void InterfaceExampleLexerTestTest() throws Exception {
		var lexer = new Lexer(
			"interface someName\n"+
			"    square() : number s\n"+
			"\n"+
			"class TranExample implements someName\n"+
			"    shared start()\n"+
			"        number x = 10\n"+
			"        number y = TranExample.square(x)\n"+
			"        console.print(y)\n"+
			"\n"+
			"    square(number x) : number s {The method we defined in the interface is used here!}\n"+
			"        s = x*x\n"+
			"\n"+
			"class UseTranExample\n"+
			"\tsomeMethod()\n"+
			"\t\tsomeName t = new TranExample() {t is an instance of someName}\n"+
			"\t\tt.square(20)\n"+
			"" );
		var tokens = lexer.Lex();
		Assertions.assertEquals(94, tokens.size());
		Assertions.assertEquals(Token.TokenTypes.INTERFACE, tokens.get(0).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(1).getType());
		Assertions.assertEquals("someName", tokens.get(1).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(2).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(3).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(4).getType());
		Assertions.assertEquals("square", tokens.get(4).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(5).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(6).getType());
		Assertions.assertEquals(Token.TokenTypes.COLON, tokens.get(7).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(8).getType());
		Assertions.assertEquals("number", tokens.get(8).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(9).getType());
		Assertions.assertEquals("s", tokens.get(9).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(10).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(11).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(12).getType());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(13).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(14).getType());
		Assertions.assertEquals("TranExample", tokens.get(14).getValue());
		Assertions.assertEquals(Token.TokenTypes.IMPLEMENTS, tokens.get(15).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(16).getType());
		Assertions.assertEquals("someName", tokens.get(16).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(17).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(18).getType());
		Assertions.assertEquals(Token.TokenTypes.SHARED, tokens.get(19).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(20).getType());
		Assertions.assertEquals("start", tokens.get(20).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(21).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(22).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(23).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(24).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(25).getType());
		Assertions.assertEquals("number", tokens.get(25).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(26).getType());
		Assertions.assertEquals("x", tokens.get(26).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(27).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(28).getType());
		Assertions.assertEquals("10", tokens.get(28).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(29).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(30).getType());
		Assertions.assertEquals("number", tokens.get(30).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(31).getType());
		Assertions.assertEquals("y", tokens.get(31).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(32).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(33).getType());
		Assertions.assertEquals("TranExample", tokens.get(33).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(34).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(35).getType());
		Assertions.assertEquals("square", tokens.get(35).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(36).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(37).getType());
		Assertions.assertEquals("x", tokens.get(37).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(38).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(39).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(40).getType());
		Assertions.assertEquals("console", tokens.get(40).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(41).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(42).getType());
		Assertions.assertEquals("print", tokens.get(42).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(43).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(44).getType());
		Assertions.assertEquals("y", tokens.get(44).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(45).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(46).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(47).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(48).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(49).getType());
		Assertions.assertEquals("square", tokens.get(49).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(50).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(51).getType());
		Assertions.assertEquals("number", tokens.get(51).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(52).getType());
		Assertions.assertEquals("x", tokens.get(52).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(53).getType());
		Assertions.assertEquals(Token.TokenTypes.COLON, tokens.get(54).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(55).getType());
		Assertions.assertEquals("number", tokens.get(55).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(56).getType());
		Assertions.assertEquals("s", tokens.get(56).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(57).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(58).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(59).getType());
		Assertions.assertEquals("s", tokens.get(59).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(60).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(61).getType());
		Assertions.assertEquals("x", tokens.get(61).getValue());
		Assertions.assertEquals(Token.TokenTypes.TIMES, tokens.get(62).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(63).getType());
		Assertions.assertEquals("x", tokens.get(63).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(64).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(65).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(66).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(67).getType());
		Assertions.assertEquals(Token.TokenTypes.CLASS, tokens.get(68).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(69).getType());
		Assertions.assertEquals("UseTranExample", tokens.get(69).getValue());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(70).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(71).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(72).getType());
		Assertions.assertEquals("someMethod", tokens.get(72).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(73).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(74).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(75).getType());
		Assertions.assertEquals(Token.TokenTypes.INDENT, tokens.get(76).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(77).getType());
		Assertions.assertEquals("someName", tokens.get(77).getValue());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(78).getType());
		Assertions.assertEquals("t", tokens.get(78).getValue());
		Assertions.assertEquals(Token.TokenTypes.ASSIGN, tokens.get(79).getType());
		Assertions.assertEquals(Token.TokenTypes.NEW, tokens.get(80).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(81).getType());
		Assertions.assertEquals("TranExample", tokens.get(81).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(82).getType());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(83).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(84).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(85).getType());
		Assertions.assertEquals("t", tokens.get(85).getValue());
		Assertions.assertEquals(Token.TokenTypes.DOT, tokens.get(86).getType());
		Assertions.assertEquals(Token.TokenTypes.WORD, tokens.get(87).getType());
		Assertions.assertEquals("square", tokens.get(87).getValue());
		Assertions.assertEquals(Token.TokenTypes.LPAREN, tokens.get(88).getType());
		Assertions.assertEquals(Token.TokenTypes.NUMBER, tokens.get(89).getType());
		Assertions.assertEquals("20", tokens.get(89).getValue());
		Assertions.assertEquals(Token.TokenTypes.RPAREN, tokens.get(90).getType());
		Assertions.assertEquals(Token.TokenTypes.NEWLINE, tokens.get(91).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(92).getType());
		Assertions.assertEquals(Token.TokenTypes.DEDENT, tokens.get(93).getType());
	}
}

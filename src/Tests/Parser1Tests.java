package Tests;

import AST.TranNode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Tran.*;
import java.util.*;

public class Parser1Tests {
    @Test
    public void testInterface() throws Exception {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(Token.TokenTypes.INTERFACE, 1, 1, "interface"));
        tokens.add(new Token(Token.TokenTypes.WORD, 1, 11, "someName"));
        tokens.add(new Token(Token.TokenTypes.NEWLINE, 1, 19));
        tokens.add(new Token(Token.TokenTypes.INDENT, 2, 1));
        tokens.add(new Token(Token.TokenTypes.WORD, 2, 2, "updateClock"));
        tokens.add(new Token(Token.TokenTypes.LPAREN, 2, 13));
        tokens.add(new Token(Token.TokenTypes.RPAREN, 2, 14));
        tokens.add(new Token(Token.TokenTypes.NEWLINE, 2, 15));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 2, "square"));
        tokens.add(new Token(Token.TokenTypes.LPAREN, 3, 8));
        tokens.add(new Token(Token.TokenTypes.RPAREN, 3, 9));
        tokens.add(new Token(Token.TokenTypes.COLON, 3, 11));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 13, "number"));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 20, "s"));
        tokens.add(new Token(Token.TokenTypes.DEDENT, 4, 23));

        var tran = new TranNode();
        var p = new Parser(tran, tokens);
        p.Tran();
        Assertions.assertEquals(1, tran.Interfaces.size());
        Assertions.assertEquals(2, tran.Interfaces.getFirst().methods.size());
    }
    @Test
    public void testprivateInterface() throws Exception {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(Token.TokenTypes.INTERFACE, 1, 1, "interface"));
        tokens.add(new Token(Token.TokenTypes.WORD, 1, 11, "someName"));
        tokens.add(new Token(Token.TokenTypes.NEWLINE, 1, 19));
        tokens.add(new Token(Token.TokenTypes.INDENT, 2, 1));
        tokens.add(new Token(Token.TokenTypes.WORD, 2, 2, "updateClock"));
        tokens.add(new Token(Token.TokenTypes.LPAREN, 2, 13));
        tokens.add(new Token(Token.TokenTypes.RPAREN, 2, 14));
        tokens.add(new Token(Token.TokenTypes.NEWLINE, 2, 15));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 2, "square"));
        tokens.add(new Token(Token.TokenTypes.LPAREN, 3, 8));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 13, "number"));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 20, "p"));
        tokens.add(new Token(Token.TokenTypes.RPAREN, 3, 9));
        tokens.add(new Token(Token.TokenTypes.COLON, 3, 11));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 13, "number"));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 20, "s"));
        tokens.add(new Token(Token.TokenTypes.DEDENT, 4, 23));

        var tran = new TranNode();
        var p = new Parser(tran, tokens);
        p.Tran();
        Assertions.assertEquals(1, tran.Interfaces.size());
        Assertions.assertEquals(2, tran.Interfaces.getFirst().methods.size());
        Assertions.assertEquals("updateClock", tran.Interfaces.getFirst().methods.getFirst().name);
        Assertions.assertEquals("s", tran.Interfaces.getFirst().methods.get(1).returns.getFirst().name);
        Assertions.assertEquals("p", tran.Interfaces.getFirst().methods.get(1).parameters.getFirst().name);



    }

    @Test
    public void testParserConstructor() throws Exception {
        // Given an input string and expected token count
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(Token.TokenTypes.INTERFACE, 1, 1, "interface"));
        tokens.add(new Token(Token.TokenTypes.WORD, 1, 11, "someName"));
        tokens.add(new Token(Token.TokenTypes.NEWLINE, 1, 19));
        tokens.add(new Token(Token.TokenTypes.INDENT, 2, 1));
        tokens.add(new Token(Token.TokenTypes.WORD, 2, 2, "updateClock"));
        tokens.add(new Token(Token.TokenTypes.LPAREN, 2, 13));
        tokens.add(new Token(Token.TokenTypes.RPAREN, 2, 14));
        tokens.add(new Token(Token.TokenTypes.NEWLINE, 2, 15));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 2, "square"));
        tokens.add(new Token(Token.TokenTypes.LPAREN, 3, 8));
        tokens.add(new Token(Token.TokenTypes.RPAREN, 3, 9));
        tokens.add(new Token(Token.TokenTypes.COLON, 3, 11));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 13, "number"));
        tokens.add(new Token(Token.TokenTypes.WORD, 3, 20, "s"));
        tokens.add(new Token(Token.TokenTypes.DEDENT, 4, 23));

        var tran = new TranNode();
        var p = new Parser(tran, tokens);

        // Create a TranNode
        TranNode tranNode = new TranNode();

        // Create the Parser with the TranNode and tokens
        Parser parser = new Parser(tranNode, tokens);

    }

    // Helper method to create tokens
    private Token createToken(Token.TokenTypes type, int line, int column, String value) {
        return new Token(type, line, column, value);
    }

    private Token createToken(Token.TokenTypes type, int line, int column) {
        return new Token(type, line, column);
    }

    @Test
    public void testMatchAndRemove() throws SyntaxErrorException {
        Token token1 = createToken(Token.TokenTypes.WORD, 1, 1, "hello");
        Token token2 = createToken(Token.TokenTypes.NUMBER, 1, 2, "123");
        TokenManager tokenManager = new TokenManager(new LinkedList<>(Arrays.asList(token1, token2)));

        // Check if the first token matches and is removed
        Optional<Token> matchedToken = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
        assertTrue(matchedToken.isPresent(), "Token should match WORD and be removed");
        assertEquals(token1, matchedToken.get(), "The matched token should be the first token");

        // Check if the second token is now the first
        Optional<Token> nextToken = tokenManager.matchAndRemove(Token.TokenTypes.NUMBER);
        assertTrue(nextToken.isPresent(), "Token should match NUMBER and be removed");
        assertEquals(token2, nextToken.get(), "The next token should be the second token");

        // Check if token manager is empty
        Assertions.assertTrue(tokenManager.done(), "Token manager should be empty");
    }

    @Test
    public void testPeek() {
        Token token1 = createToken(Token.TokenTypes.WORD, 1, 1, "hello");
        Token token2 = createToken(Token.TokenTypes.NUMBER, 1, 2, "123");
        TokenManager tokenManager = new TokenManager(new LinkedList<>(Arrays.asList(token1, token2)));

        // Check peeking the first token
        Optional<Token> peekToken = tokenManager.peek(0);
        assertTrue(peekToken.isPresent(), "First token should be peeked");
        assertEquals(token1, peekToken.get(), "The first peeked token should be the first token");

        // Check peeking the second token
        Optional<Token> secondPeekToken = tokenManager.peek(1);
        assertTrue(secondPeekToken.isPresent(), "Second token should be peeked");
        assertEquals(token2, secondPeekToken.get(), "The second peeked token should be the second token");


    }

    @Test
    public void testNextTwoTokensMatch() {
        Token token1 = createToken(Token.TokenTypes.WORD, 1, 1, "hello");
        Token token2 = createToken(Token.TokenTypes.NUMBER, 1, 2, "123");
        TokenManager tokenManager = new TokenManager(new LinkedList<>(Arrays.asList(token1, token2)));

        // Check if the first two tokens match the given types
        Assertions.assertTrue(tokenManager.nextTwoTokensMatch(Token.TokenTypes.WORD, Token.TokenTypes.NUMBER),
                "First two tokens should match WORD and NUMBER");


    }

    @Test
    public void testGetCurrentLine() throws SyntaxErrorException {
        Token token1 = createToken(Token.TokenTypes.WORD, 1, 1, "hello");
        TokenManager tokenManager = new TokenManager(new LinkedList<>(Arrays.asList(token1)));

        // Check if the current line is returned correctly
        Assertions.assertEquals(1, tokenManager.getCurrentLine(), "The current line should be 1");
    }

    @Test
    public void testGetCurrentColumnNumber() throws SyntaxErrorException {
        Token token1 = createToken(Token.TokenTypes.WORD, 1, 5, "hello");
        TokenManager tokenManager = new TokenManager(new LinkedList<>(Arrays.asList(token1)));

        // Check if the current column is returned correctly
        Assertions.assertEquals(5, tokenManager.getCurrentColumnNumber(), "The current column should be 5");
    }
}
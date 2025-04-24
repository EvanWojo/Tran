package Tran;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TokenManager {

    LinkedList<Token> tokens;

    public TokenManager(List<Token> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    public boolean done() {
	    return tokens.isEmpty();
    }

    public Optional<Token> matchAndRemove(Token.TokenTypes t) {

        if (tokens.peek() != null) {
            if (t.equals(tokens.peek().getType())) {
                return Optional.ofNullable(tokens.poll());
            }
        }
        return Optional.empty();
    }

    public Optional<Token> peek(int i) {
	    return Optional.ofNullable(tokens.get(i));
    }

    public boolean nextTwoTokensMatch(Token.TokenTypes first, Token.TokenTypes second) {
        try {
            return tokens.getFirst().getType() == first && tokens.get(1).getType() == second;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean nextIsEither(Token.TokenTypes first, Token.TokenTypes second) {
        try {
            return tokens.getFirst().getType() == first || tokens.getFirst().getType() == second;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public int getCurrentLine() {
            return tokens.getFirst().getLineNumber();
    }

    public int getCurrentColumnNumber() {
            return tokens.getFirst().getColumnNumber();
    }
}

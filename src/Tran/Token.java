package Tran;
import java.util.Optional;

public class Token {
    public enum TokenTypes {
        WORD,  NUMBER, // These require a String value
        ASSIGN, LPAREN, RPAREN, COLON, DOT, // punctuation
        PLUS, MINUS, TIMES, DIVIDE, MODULO, COMMA, // punctuation
        EQUAL, NOTEQUAL, LESSTHAN, LESSTHANEQUAL, GREATERTHAN, GREATERTHANEQUAL, // punctuation
        IMPLEMENTS, CLASS, INTERFACE, LOOP, IF, ELSE, // keywords
        INDENT, DEDENT, NEWLINE, // blocks
        QUOTEDSTRING, QUOTEDCHARACTER,
        NEW, PRIVATE, SHARED, CONSTRUCT
    }

    private final Optional<String> value;

    private final TokenTypes type;

    private final int columnNumber;

    public int getColumnNumber() {
        return columnNumber;
    }

    private final int lineNumber;

    public int getLineNumber() {
        return lineNumber;
    }

    public Token(TokenTypes type, int lineNumber, int columnNumber ){
        this(type, lineNumber,columnNumber, Optional.empty() );
    }

    public Token(TokenTypes type, int lineNumber, int columnNumber, String value ){
        this(type, lineNumber,columnNumber, Optional.of(value) );
    }

    private Token(TokenTypes type, int lineNumber, int columnNumber, Optional<String> value ) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.type = type;
        this.value = value;
    }

    public TokenTypes getType() { return type; }

    public String getValue() {
           return value.orElse("");
    }

    @Override
    public String toString() {
       return type + " " + (value.orElse("")) + "@" + lineNumber + "," + columnNumber;
    }
}

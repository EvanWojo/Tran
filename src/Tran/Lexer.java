package Tran;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Lexer {

    private final TextManager textManager;
    private final HashMap<String, Token.TokenTypes> keywords = new HashMap<>();
    private final HashMap<String, Token.TokenTypes> punctuation = new HashMap<>();
    private int lineNumber = 1;
    private int columnNumber;
    private int currentIndentationLevel;
    private int previousIndentationLevel;
    private List<Token> tokens;


    public Lexer(String input) {

        textManager = new TextManager(input);

        keywords.put("if", Token.TokenTypes.IF);
        keywords.put("else", Token.TokenTypes.ELSE);
        keywords.put("loop", Token.TokenTypes.LOOP);
        keywords.put("interface", Token.TokenTypes.INTERFACE);
        keywords.put("class", Token.TokenTypes.CLASS);
        keywords.put("implements", Token.TokenTypes.IMPLEMENTS);
        keywords.put("new", Token.TokenTypes.NEW);
        keywords.put("private", Token.TokenTypes.PRIVATE);
        keywords.put("shared", Token.TokenTypes.SHARED);
        keywords.put("construct", Token.TokenTypes.CONSTRUCT);

        punctuation.put("=", Token.TokenTypes.ASSIGN);
        punctuation.put("(", Token.TokenTypes.LPAREN);
        punctuation.put(")", Token.TokenTypes.RPAREN);
        punctuation.put(":", Token.TokenTypes.COLON);
        punctuation.put(".", Token.TokenTypes.DOT);
        punctuation.put("+", Token.TokenTypes.PLUS);
        punctuation.put("-", Token.TokenTypes.MINUS);
        punctuation.put("*", Token.TokenTypes.TIMES);
        punctuation.put("/", Token.TokenTypes.DIVIDE);
        punctuation.put("%", Token.TokenTypes.MODULO);
        punctuation.put(",", Token.TokenTypes.COMMA);
        punctuation.put("==", Token.TokenTypes.EQUAL);
        punctuation.put("!=", Token.TokenTypes.NOTEQUAL);
        punctuation.put("<", Token.TokenTypes.LESSTHAN);
        punctuation.put("<=", Token.TokenTypes.LESSTHANEQUAL);
        punctuation.put(">", Token.TokenTypes.GREATERTHAN);
        punctuation.put(">=", Token.TokenTypes.GREATERTHANEQUAL);
        punctuation.put("    ", Token.TokenTypes.INDENT);
        punctuation.put("\t", Token.TokenTypes.INDENT);

        tokens = new LinkedList<>();

    }

    public List<Token> Lex() throws Exception {

        while (!textManager.isAtEnd()) {
            char p = textManager.peekCharacter();

            if (Character.isLetter(p)) { //Reading words
                tokens.add(readWord());
            } else if (Character.isDigit(p)) { //Reading letters
                tokens.add(readNumber());
            } else if (p == '"') { //Reading quoted strings
                tokens.add(readQuotedString());
            } else if (p == '\'') { //Reading quoted characters
                tokens.add(readQuotedCharacter());
            } else if (p == '{') { //Consuming comments
                readComment();
            } else if (p == '\n') { //Reading newlines
                newline(tokens);
            } else if (p == ' ') { //Consuming whitespace/making indentation
                try {
                    if (textManager.peekCharacter(1) == ' ' && textManager.peekCharacter(2) == ' ' && textManager.peekCharacter(3) == ' ') {
                        for (int i = 0; i < 4; i++) {
                            textManager.getCharacter();
                        }
                        tokens.add(new Token(Token.TokenTypes.INDENT, lineNumber, columnNumber));
                    } else
                        textManager.getCharacter();
                } catch (IndexOutOfBoundsException e) {
                    textManager.getCharacter();
                }

            } else if (p == '\t') {
                textManager.getCharacter();
                tokens.add(new Token(Token.TokenTypes.INDENT, lineNumber, columnNumber));
            } else if (p == '\r') {
                textManager.getCharacter();
            } else { //Base case is punctuation
                tokens.add(readPunctuation());
            }
        }

        for (int i = 0; i < currentIndentationLevel; i++) {
            tokens.add(new Token(Token.TokenTypes.DEDENT, lineNumber, columnNumber));
        }
        return tokens;
    }

    private Token readWord() {

        char c, peek = ' ';
        String word = "";

        do {

            c = textManager.getCharacter();
            columnNumber++;
            word = word.concat(String.valueOf(c));

            if (!textManager.isAtEnd())
                peek = textManager.peekCharacter();

        } while ((Character.isLetter(peek) || Character.isDigit(peek)) && !textManager.isAtEnd());

        if (keywords.containsKey(word)) {
            return new Token(keywords.get(word), lineNumber, columnNumber - word.length());
        } else {
            return new Token(Token.TokenTypes.WORD, lineNumber, columnNumber - word.length(), word);
        }

    }

    private Token readNumber() throws SyntaxErrorException {

        char c, peek = ' ';
        String number = "";
        boolean decimal = false;

        do {

            c = textManager.getCharacter();
            columnNumber++;

            if (decimal && c == '.')
                throw new SyntaxErrorException("Too many decimals", lineNumber, columnNumber);

            if (c == '.') //Once a decimal is read flip decimal check to true
                decimal = true;
            number = number.concat(String.valueOf(c));


            if (!textManager.isAtEnd())
                peek = textManager.peekCharacter();

        } while (!textManager.isAtEnd() && peek != ' ' && peek != '\t' && peek != '\n' && Character.isDigit(peek) || peek == '.');

        return new Token(Token.TokenTypes.NUMBER, lineNumber, columnNumber - number.length(), number);

    }

    private Token readPunctuation() throws SyntaxErrorException {

        char c = textManager.getCharacter();
        String punc = "";

        if (c == '<' || c == '>' || c == '!' || c == '=') {
            punc = punc.concat(String.valueOf(c));
            if (!textManager.isAtEnd()) {
                if (textManager.peekCharacter() == '=') {
                    c = textManager.getCharacter();
                    punc = punc.concat(String.valueOf(c));
                }
            }
        } else {
            punc = punc.concat(String.valueOf(c));
        }

        if (punctuation.containsKey(punc))
            return new Token(punctuation.get(punc), lineNumber, columnNumber - punc.length());
        else
            throw new SyntaxErrorException("Unknown character", lineNumber, columnNumber);

    }

    private Token readQuotedString() throws SyntaxErrorException {

        char c;
        String string = "";
        textManager.getCharacter();
        columnNumber++;

        do {

            c = textManager.getCharacter();
            columnNumber++;

            if (c != '"')
                string = string.concat(String.valueOf(c));

        } while (!textManager.isAtEnd() && c != '"');

        if (textManager.isAtEnd() && c != '"')
            throw new SyntaxErrorException("Unterminated string", lineNumber, columnNumber);
        else
            return new Token(Token.TokenTypes.QUOTEDSTRING, lineNumber, columnNumber - string.length(), string);
    }

    private Token readQuotedCharacter() throws SyntaxErrorException {

        char c;
        String character = "";
        textManager.getCharacter(); //Consume the apostrophe
        columnNumber++;

        c = textManager.getCharacter();
        columnNumber++;

        if (c != '\'')
            character = character.concat(String.valueOf(c));

        c = textManager.getCharacter();

        if (c != '\'')
            throw new SyntaxErrorException("Invalid quoted character", lineNumber, columnNumber);
        else
            return new Token(Token.TokenTypes.QUOTEDCHARACTER, lineNumber, columnNumber - character.length(), character);

    }

    private void readComment() throws SyntaxErrorException {

        char c = ' ';
        textManager.getCharacter();
        columnNumber++;

        do {

            c = textManager.getCharacter();
            columnNumber++;

        } while (!textManager.isAtEnd() && c != '}');

        if (textManager.isAtEnd() && c != '}')
            throw new SyntaxErrorException("Unterminated comment", lineNumber, columnNumber);

    }

    private int checkForIndentation() throws SyntaxErrorException {

        int spaceCount = 0;

        try {

            do {

                if (textManager.peekCharacter() == '\t') {
                    spaceCount += 4;
                    textManager.getCharacter();
                } else if (textManager.peekCharacter() == '\n') {
                    columnNumber = 0;
                    currentIndentationLevel = 0;
                    textManager.getCharacter();
                    tokens.add(new Token(Token.TokenTypes.NEWLINE, ++lineNumber, columnNumber));
                    spaceCount = 0;
                } else if (textManager.peekCharacter() == ' ') {
                    textManager.getCharacter();
                    spaceCount++;
                }

            } while (!textManager.isAtEnd() && textManager.peekCharacter() == '\t' || textManager.peekCharacter() == ' ' || textManager.peekCharacter() == '\n');

            if ((spaceCount % 4) != 0)
                throw new SyntaxErrorException("Incorrect indentation format", lineNumber, columnNumber);
            else
                currentIndentationLevel = spaceCount / 4;

        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
        return currentIndentationLevel;
    }

    private void newline(List<Token> tokens) throws SyntaxErrorException {

        lineNumber++;
        columnNumber = 0;
        tokens.add(new Token(Token.TokenTypes.NEWLINE, lineNumber, columnNumber));
        textManager.getCharacter();

        if (textManager.isAtEnd()) {
            for (int i = 0; i < currentIndentationLevel; i++)
                tokens.add(new Token(Token.TokenTypes.DEDENT, lineNumber, columnNumber));
            currentIndentationLevel = 0;
            return;
        }

        previousIndentationLevel = currentIndentationLevel; //Set previous indent
        currentIndentationLevel = 0; //Reset current

        if (!textManager.isAtEnd()) {

            currentIndentationLevel = checkForIndentation();

            if (currentIndentationLevel == -1) {
                for (int i = 0; i < previousIndentationLevel; i++)
                    tokens.add(new Token(Token.TokenTypes.DEDENT, lineNumber, columnNumber));
            }
            else if (currentIndentationLevel > previousIndentationLevel) {

                for (int i = previousIndentationLevel; i < currentIndentationLevel; i++) {
                    tokens.add(new Token(Token.TokenTypes.INDENT, lineNumber, columnNumber));
                }

            } else if (currentIndentationLevel < previousIndentationLevel) {
                for (int i = previousIndentationLevel; i > currentIndentationLevel; i--) {
                    tokens.add(new Token(Token.TokenTypes.DEDENT, lineNumber, columnNumber));
                }
            }
        }
    }
}

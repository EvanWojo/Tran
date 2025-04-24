package Tran;
public class TextManager {
    int position;
    final String text;

    public TextManager(String input) {
        this.text = input;
    }

    public boolean isAtEnd() {
        return position == text.length();
    }

    public char peekCharacter() {
           return text.charAt(position);
    }

    public char peekCharacter(int dist) {
            return text.charAt(position + dist);
    }

    public char getCharacter() {
            return text.charAt(position++);
    }
}

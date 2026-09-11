import java.io.IOException;
import java.util.Hashtable;

public class Scanner {
    private int peek = ' ';
    private Hashtable<String, Word> words = new Hashtable<>();

    private void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Scanner() {
        reserve(new Word("let", Tag.LET));
        reserve(new Word("print", Tag.PRINT));
    }

    private void readch() throws IOException {
        peek = System.in.read();
    }

    public Token scan() throws IOException {
        for (;; readch()) {
            if (peek == ' ' || peek == '\t' || peek == '\r' || peek == '\n') continue;
            else break;
        }

        if (Character.isDigit((char) peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit((char) peek, 10);
                readch();
            } while (Character.isDigit((char) peek));
            return new Num(v);
        }

        if (Character.isLetter((char) peek)) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append((char) peek);
                readch();
            } while (Character.isLetterOrDigit((char) peek));
            
            String s = sb.toString();
            Word w = words.get(s);
            if (w != null) return w;
            
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        Token t = new Token(peek);
        peek = ' ';
        return t;
    }
}
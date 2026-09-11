public class Word extends Token {
    public final String lexeme;
    public Word(String s, int tag) { super(tag); this.lexeme = s; }
    @Override
    public String toString() { return lexeme; }
}
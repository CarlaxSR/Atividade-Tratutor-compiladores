public class Token {
    public final int tag;
    public Token(int t) { this.tag = t; }
    @Override
    public String toString() { return "" + (char) tag; }
}
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Scanner scanner;
    private Token lookahead;
    private List<String> instructions = new ArrayList<>();

    public Parser(Scanner s) throws IOException {
        this.scanner = s;
        move();
    }

    public List<String> getInstructions() {
        return instructions;
    }

    private void emit(String instruction) {
        instructions.add(instruction);
    }

    private void move() throws IOException {
        lookahead = scanner.scan();
    }

    private void match(int t) throws IOException {
        if (lookahead.tag == t) move();
        else throw new RuntimeException("Erro sintatico proximo ao token: " + lookahead);
    }

    public void program() throws IOException {
        while (lookahead.tag != -1 && lookahead.tag != 65535) {
            stmt();
        }
    }

    private void stmt() throws IOException {
        if (lookahead.tag == Tag.LET) {
            match(Tag.LET);
            Word id = (Word) lookahead;
            match(Tag.ID);
            match('=');
            expr();
            match(';');
            emit("pop " + id.lexeme);
        } else if (lookahead.tag == Tag.PRINT) {
            match(Tag.PRINT);
            expr();
            match(';');
            emit("print");
        } else {
            return;
        }
    }

    private void expr() throws IOException {
        term();
        while (lookahead.tag == '+' || lookahead.tag == '-') {
            int op = lookahead.tag;
            move();
            term();
            if (op == '+') emit("add");
            else emit("sub");
        }
    }

    private void term() throws IOException {
        fact();
        while (lookahead.tag == '*' || lookahead.tag == '/') {
            int op = lookahead.tag;
            move();
            fact();
            if (op == '*') emit("mul");
            else emit("div");
        }
    }

    private void fact() throws IOException {
        if (lookahead.tag == Tag.NUM) {
            Num n = (Num) lookahead;
            emit("push " + n.value);
            match(Tag.NUM);
        } else if (lookahead.tag == Tag.ID) {
            Word w = (Word) lookahead;
            emit("push " + w.lexeme);
            match(Tag.ID);
        } else {
            throw new RuntimeException("Fator invalido: " + lookahead);
        }
    }
}
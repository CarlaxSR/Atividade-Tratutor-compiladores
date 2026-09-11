import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner();
            Parser parser = new Parser(scanner);
            
            parser.program();
            List<String> instructions = parser.getInstructions();
            
            Interpreter interpreter = new Interpreter();
            interpreter.execute(instructions);
            
        } catch (IOException e) {
            System.err.println("Erro de E/S: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Erro de execucao ou sintaxe: " + e.getMessage());
        }
    }
}
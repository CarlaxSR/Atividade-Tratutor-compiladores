import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Interpreter {
    private Stack<Integer> stack = new Stack<>();
    private Map<String, Integer> memory = new HashMap<>();

    public void execute(List<String> instructions) {
        for (String inst : instructions) {
            String[] parts = inst.split(" ");
            String cmd = parts[0];

            switch (cmd) {
                case "push":
                    if (Character.isDigit(parts[1].charAt(0))) {
                        stack.push(Integer.parseInt(parts[1]));
                    } else {
                        String varName = parts[1];
                        if (!memory.containsKey(varName)) {
                            throw new RuntimeException("Variavel nao inicializada: " + varName);
                        }
                        stack.push(memory.get(varName));
                    }
                    break;
                case "pop":
                    String varName = parts[1];
                    memory.put(varName, stack.pop());
                    break;
                case "add":
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a + b);
                    break;
                case "sub":
                    b = stack.pop();
                    a = stack.pop();
                    stack.push(a - b);
                    break;
                case "mul":
                    b = stack.pop();
                    a = stack.pop();
                    stack.push(a * b);
                    break;
                case "div":
                    b = stack.pop();
                    a = stack.pop();
                    stack.push(a / b);
                    break;
                case "print":
                    System.out.println(stack.pop());
                    break;
            }
        }
    }
}
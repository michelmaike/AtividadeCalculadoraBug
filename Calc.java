import java.util.Scanner;
import java.util.Stack;

public class Calc {

    public static String infixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        String postfix = "";
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                postfix += c;
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                }
            }
            else {
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix += stack.pop();
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            postfix += stack.pop();
        }
        return postfix;
    }

    
    public static String infixToPrefix(String infix) {
  
        String reversed = "";
        for (int i = infix.length() - 1; i >= 0; i--) {
            char c = infix.charAt(i);
            if (c == '(') {
                reversed += ')';
            } else if (c == ')') {
                reversed += '(';
            } else {
                reversed += c;
            }
        }
     
        String postfix = infixToPostfix(reversed);

        return new StringBuilder(postfix).reverse().toString();
    }


    public static double evaluatePostfix(String postfix) {

        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                stack.push((double) (c - '0'));
            }
            else {
                double x = stack.pop();
                double y = stack.pop();

                switch (c) {
                    case '+':
                        stack.push(y + x);
                        break;
                    case '-':
                        stack.push(y - x);
                        break;
                    case '*':
                        stack.push(y * x);
                        break;
                    case '/':
                        stack.push(y / x);
                        break;
                }
            }
        }
        return stack.pop();
    }

    public static double evaluatePrefix(String prefix) {
        String reversed = new StringBuilder(prefix).reverse().toString();
        return evaluatePostfix(reversed);
    }

    public static int getPrecedence(char op) {
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
   
        System.out.println("Digite o tipo de notação da expressão (infixa, pós-fixa ou pré-fixa):");
        String tipo = sc.nextLine().toLowerCase();
  
        System.out.println("Digite a expressão:");
        String expressao = sc.nextLine();
    
        String infixa, posfixa, prefixa;
        double resultado;
        switch (tipo) {
            case "infixa":
                infixa = expressao;
                posfixa = infixToPostfix(expressao);
                prefixa = infixToPrefix(expressao);
                resultado = evaluatePostfix(posfixa);
                break;
            case "pós-fixa":
                infixa = infixToPrefix(expressao);
                posfixa = expressao;
                prefixa = infixToPrefix(infixa);
                resultado = evaluatePostfix(expressao);
                break;
            case "pré-fixa":
                infixa = infixToPrefix(expressao);
                posfixa = infixToPostfix(infixa);
                prefixa = expressao;
                resultado = evaluatePrefix(expressao);
                break;
            default:
                System.out.println("Tipo de notação inválido.");
                return;
        }
        System.out.println("Expressão infixa: " + infixa);
        System.out.println("Expressão pós-fixa: " + posfixa);
        System.out.println("Expressão pré-fixa: " + prefixa);
        System.out.println("Resultado: " + resultado);
    }
}

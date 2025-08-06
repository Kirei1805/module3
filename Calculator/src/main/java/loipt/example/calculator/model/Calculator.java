package loipt.example.calculator.model;

public class Calculator {
    
    public static double calculate(double firstOperand, double secondOperand, String operator) throws ArithmeticException {
        switch (operator.toLowerCase()) {
            case "addition":
            case "+":
                return firstOperand + secondOperand;
            case "subtraction":
            case "-":
                return firstOperand - secondOperand;
            case "multiplication":
            case "*":
                return firstOperand * secondOperand;
            case "division":
            case "/":
                if (secondOperand == 0) {
                    throw new ArithmeticException("Không thể chia cho 0!");
                }
                return firstOperand / secondOperand;
            default:
                throw new IllegalArgumentException("Toán tử không hợp lệ: " + operator);
        }
    }
} 
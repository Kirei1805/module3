package loipt.example.calculator.controller;

import loipt.example.calculator.model.Calculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CalculatorServlet", urlPatterns = "/calculate")
public class CalculatorServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String firstOperandStr = request.getParameter("firstOperand");
            String secondOperandStr = request.getParameter("secondOperand");
            String operator = request.getParameter("operator");

            if (firstOperandStr == null || firstOperandStr.trim().isEmpty() ||
                secondOperandStr == null || secondOperandStr.trim().isEmpty() ||
                operator == null || operator.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }

            double firstOperand = Double.parseDouble(firstOperandStr);
            double secondOperand = Double.parseDouble(secondOperandStr);

            double result = Calculator.calculate(firstOperand, secondOperand, operator);

            request.setAttribute("firstOperand", firstOperand);
            request.setAttribute("secondOperand", secondOperand);
            request.setAttribute("operator", operator);
            request.setAttribute("result", result);

            request.getRequestDispatcher("/result.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Vui lòng nhập số hợp lệ!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ArithmeticException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
} 
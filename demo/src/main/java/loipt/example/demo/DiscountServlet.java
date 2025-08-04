package loipt.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DiscountServlet", urlPatterns = "/display-discount")
public class DiscountServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String discountStr = request.getParameter("discount");
        
        double listPrice = Double.parseDouble(priceStr);
        double discountPercent = Double.parseDouble(discountStr);
        
        double discountAmount = listPrice * discountPercent * 0.01;
        double discountPrice = listPrice - discountAmount;
        
        request.setAttribute("description", description);
        request.setAttribute("listPrice", listPrice);
        request.setAttribute("discountPercent", discountPercent);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("discountPrice", discountPrice);
        
        request.getRequestDispatcher("/display-discount.jsp").forward(request, response);
    }
} 
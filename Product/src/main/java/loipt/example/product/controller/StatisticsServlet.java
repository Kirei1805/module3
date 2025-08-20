package loipt.example.product.controller;

import loipt.example.product.model.ProductStatistics;
import loipt.example.product.service.ProductService;
import loipt.example.product.service.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StatisticsServlet", urlPatterns = "/statistics")
public class StatisticsServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showStatistics(request, response);
    }

    private void showStatistics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductStatistics statistics = productService.getProductStatistics();
            request.setAttribute("statistics", statistics);

            RequestDispatcher dispatcher = request.getRequestDispatcher("product/statistics.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy thống kê");
        }
    }
}

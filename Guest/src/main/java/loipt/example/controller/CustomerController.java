package loipt.example.controller;

import loipt.example.model.Customer;
import loipt.example.service.CustomerService;
import loipt.example.service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerController", urlPatterns = {"/customers"})
public class CustomerController extends HttpServlet {
    
    private CustomerService customerService;
    
    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        List<Customer> customers = customerService.getAllCustomers();
        request.setAttribute("customers", customers);

        System.out.println("Number of customers: " + customers.size());
        for (Customer c : customers) {
            System.out.println("Customer: " + c.getName() + " - " + c.getAddress());
        }
        
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
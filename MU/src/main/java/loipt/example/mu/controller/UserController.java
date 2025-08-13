package loipt.example.mu.controller;

import loipt.example.mu.entity.User;
import loipt.example.mu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/users"})
public class UserController extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "search":
                searchUsers(request, response);
                break;
            case "sort":
                sortUsers(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                createUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            case "search":
                searchUsers(request, response);
                break;
            case "sort":
                sortUsers(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/create.jsp").forward(request, response);
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(name, email, country);
        userService.createUser(user);

        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(id, name, email, country);
        userService.updateUser(user);

        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);

        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void searchUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String country = request.getParameter("country");
        String sortBy = request.getParameter("sortBy");
        
        List<User> users;
        if (sortBy != null && !sortBy.isEmpty()) {
            users = userService.searchByCountryAndSort(country, sortBy);
        } else {
            users = userService.searchByCountry(country);
        }
        
        request.setAttribute("users", users);
        request.setAttribute("searchCountry", country);
        request.setAttribute("sortBy", sortBy);
        request.getRequestDispatcher("/WEB-INF/views/user/list.jsp").forward(request, response);
    }

    private void sortUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        String country = request.getParameter("country");
        
        List<User> users;
        if (country != null && !country.isEmpty()) {
            users = userService.searchByCountryAndSort(country, sortBy);
        } else {
            users = userService.getAllUsersSorted(sortBy);
        }
        
        request.setAttribute("users", users);
        request.setAttribute("searchCountry", country);
        request.setAttribute("sortBy", sortBy);
        request.getRequestDispatcher("/WEB-INF/views/user/list.jsp").forward(request, response);
    }
}

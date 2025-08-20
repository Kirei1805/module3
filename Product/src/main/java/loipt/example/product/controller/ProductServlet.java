package loipt.example.product.controller;

import loipt.example.product.model.Category;
import loipt.example.product.model.Product;
import loipt.example.product.service.ProductService;
import loipt.example.product.service.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            case "view":
                showViewForm(request, response);
                break;
            case "search":
                showSearchForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                createProduct(request, response);
                break;
            case "edit":
                updateProduct(request, response);
                break;
            case "search":
                searchProduct(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> list = productService.getAllProducts();
        request.setAttribute("products", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(request, response);
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int productAmount = Integer.parseInt(request.getParameter("productAmount"));
        String productDescription = request.getParameter("productDescription");
        String productStatus = request.getParameter("productStatus");
        String categoryType = request.getParameter("categoryType");

        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductAmount(productAmount);
        product.setProductDescription(productDescription);
        product.setProductStatus(productStatus);

        Category newCategory = null;

        try {
            if ("new".equals(categoryType)) {
                // Tạo category mới
                String newCategoryCode = request.getParameter("newCategoryCode");
                String newCategoryName = request.getParameter("newCategoryName");
                String newCategoryDescription = request.getParameter("newCategoryDescription");

                if (newCategoryCode != null && !newCategoryCode.trim().isEmpty() &&
                    newCategoryName != null && !newCategoryName.trim().isEmpty()) {
                    
                    newCategory = new Category();
                    newCategory.setCategoryCode(newCategoryCode.trim());
                    newCategory.setCategoryName(newCategoryName.trim());
                    newCategory.setDescription(newCategoryDescription != null ? newCategoryDescription.trim() : "");
                    newCategory.setStatus("Active");
                } else {
                    throw new Exception("Vui lòng nhập đầy đủ thông tin danh mục mới!");
                }
            } else {
                // Sử dụng category hiện có
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                product.setCategoryId(categoryId);
            }

            // Sử dụng ProductServiceImpl với transaction tích hợp
            ((ProductServiceImpl) productService).addProductWithTransaction(product, newCategory);
            response.sendRedirect("/products");
            
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("product", product);
            RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existing = productService.getProductById(id);
        request.setAttribute("product", existing);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int productAmount = Integer.parseInt(request.getParameter("productAmount"));
        String productDescription = request.getParameter("productDescription");
        String productStatus = request.getParameter("productStatus");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductAmount(productAmount);
        product.setProductDescription(productDescription);
        product.setProductStatus(productStatus);
        product.setCategoryId(categoryId);

        try {
            // Sử dụng ProductServiceImpl với transaction tích hợp
            ((ProductServiceImpl) productService).updateProductWithTransaction(id, product, null);
            response.sendRedirect("/products");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("product", product);
            RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            // Sử dụng ProductServiceImpl với transaction tích hợp
            ((ProductServiceImpl) productService).deleteProductWithTransaction(id);
            response.sendRedirect("/products");
        } catch (Exception e) {
            // Có thể hiển thị lỗi nếu cần
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            response.sendRedirect("/products");
        }
    }

    private void showViewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProductById(id);
        request.setAttribute("product", product);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/view.jsp");
        dispatcher.forward(request, response);
    }

    private void showSearchForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/search.jsp");
        dispatcher.forward(request, response);
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Product> results = productService.searchProducts(keyword);
        request.setAttribute("results", results);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/search.jsp");
        dispatcher.forward(request, response);
    }
}


package loipt.example.product.controller;

import loipt.example.product.model.Category;
import loipt.example.product.model.Product;
import loipt.example.product.dto.PageResultDTO;
import loipt.example.product.dto.ProductDTO;
import loipt.example.product.dto.ProductSearchDTO;
import loipt.example.product.service.ProductService;
import loipt.example.product.service.ProductServiceImpl;
import loipt.example.product.repository.CategoryRepository;
import loipt.example.product.repository.CategoryRepositoryImpl;

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
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set encoding cho request và response
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        
        switch (action) {
            case "create":
                showFormCreate(req, resp);
                break;
            case "edit":
                showFormEdit(req, resp);
                break;
            case "view":
                showFormView(req, resp);
                break;
            case "search":
                showFormSearch(req, resp);
                break;
            case "delete":
                deleteById(req, resp);
                break;
            default:
                showList(req, resp);
                break;
        }
    }

    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Lấy danh sách category để hiển thị trong form
            req.setAttribute("categoryList", categoryRepository.findAll());
            req.getRequestDispatcher("/product/create.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormEdit(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.getProductById(id);
            req.setAttribute("product", product);
            req.setAttribute("categoryList", categoryRepository.findAll());
            req.getRequestDispatcher("/product/edit.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormView(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            ProductDTO product = ((ProductServiceImpl) productService).getProductWithCategoryInfoById(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/product/view.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormSearch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("categoryList", categoryRepository.findAll());
            req.getRequestDispatcher("/product/search.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int deleteId = Integer.parseInt(req.getParameter("id"));
            ((ProductServiceImpl) productService).deleteProductWithTransaction(deleteId);
            String mess = "Delete success";
            resp.sendRedirect("/products?mess=" + mess);
        } catch (Exception e) {
            String mess = "Delete failed: " + e.getMessage();
            try {
                resp.sendRedirect("/products?mess=" + mess);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int page = 1;
            int pageSize = 10;
            try {
                page = Integer.parseInt(req.getParameter("page"));
            } catch (Exception ignored) {}
            try {
                pageSize = Integer.parseInt(req.getParameter("pageSize"));
            } catch (Exception ignored) {}

            PageResultDTO<ProductDTO> result = ((ProductServiceImpl) productService).getProductsPaged(page, pageSize);
            req.setAttribute("page", result);
            req.setAttribute("products", result.getData());
            req.setAttribute("categoryList", categoryRepository.findAll());
            req.getRequestDispatcher("/product/list.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                save(req, resp);
                break;
            case "edit":
                update(req, resp);
                break;
            case "delete":
                deleteById(req, resp);
                break;
            case "search":
                search(req, resp);
                break;
            default:
                showList(req, resp);
                break;
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String productCode = req.getParameter("productCode");
            String productName = req.getParameter("productName");
            double productPrice = Double.parseDouble(req.getParameter("productPrice"));
            int productAmount = Integer.parseInt(req.getParameter("productAmount"));
            String productDescription = req.getParameter("productDescription");
            String productStatus = req.getParameter("productStatus");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            Product product = new Product();
            product.setProductCode(productCode);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductAmount(productAmount);
            product.setProductDescription(productDescription);
            product.setProductStatus(productStatus);
            product.setCategoryId(categoryId);

            ((ProductServiceImpl) productService).addProductWithTransaction(product, null);
            String mess = "Add success";
            resp.sendRedirect("/products?mess=" + mess);
            
        } catch (Exception e) {
            String mess = "Add failed: " + e.getMessage();
            try {
                resp.sendRedirect("/products?mess=" + mess);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String productCode = req.getParameter("productCode");
            String productName = req.getParameter("productName");
            double productPrice = Double.parseDouble(req.getParameter("productPrice"));
            int productAmount = Integer.parseInt(req.getParameter("productAmount"));
            String productDescription = req.getParameter("productDescription");
            String productStatus = req.getParameter("productStatus");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            Product product = new Product();
            product.setProductCode(productCode);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductAmount(productAmount);
            product.setProductDescription(productDescription);
            product.setProductStatus(productStatus);
            product.setCategoryId(categoryId);

            ((ProductServiceImpl) productService).updateProductWithTransaction(id, product, null);
            String mess = "Update success";
            resp.sendRedirect("/products?mess=" + mess);
            
        } catch (Exception e) {
            System.err.println("Update error: " + e.getMessage());
            e.printStackTrace();
            String mess = "Update failed: " + e.getMessage();
            try {
                resp.sendRedirect("/products?mess=" + mess);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String keyword = req.getParameter("keyword");
            int categoryId = 0;
            double minPrice = 0;
            double maxPrice = Double.MAX_VALUE;
            String status = req.getParameter("status");
            int page = 1;
            int pageSize = 10;

            try { categoryId = Integer.parseInt(req.getParameter("categoryId")); } catch (Exception ignored) {}
            try { minPrice = Double.parseDouble(req.getParameter("minPrice")); } catch (Exception ignored) {}
            try { maxPrice = Double.parseDouble(req.getParameter("maxPrice")); } catch (Exception ignored) {}
            try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
            try { pageSize = Integer.parseInt(req.getParameter("pageSize")); } catch (Exception ignored) {}

            ProductSearchDTO searchDTO = new ProductSearchDTO(keyword, categoryId, minPrice, maxPrice, status, page, pageSize, "Id", "ASC");
            PageResultDTO<ProductDTO> result = ((ProductServiceImpl) productService).searchProducts(searchDTO);
            req.setAttribute("page", result);
            req.setAttribute("results", result.getData());
            req.setAttribute("categoryList", categoryRepository.findAll());
            req.getRequestDispatcher("/product/search.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


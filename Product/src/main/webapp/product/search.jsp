<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, loipt.example.product.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("results");
    String keyword = request.getParameter("keyword");
%>
<html>
<head>
    <title>Tìm kiếm sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .search-container { max-width: 800px; margin: 0 auto; }
        .search-form { margin-bottom: 20px; }
        .search-input { width: 70%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        .btn { padding: 10px 20px; margin: 5px; border: none; border-radius: 4px; cursor: pointer; }
        .btn-primary { background: #007bff; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn-info { background: #17a2b8; color: white; }
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; color: white; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-buttons { display: flex; gap: 5px; }
        .status { padding: 4px 8px; border-radius: 3px; font-size: 12px; }
        .status-instock { background: #d4edda; color: #155724; }
        .status-outstock { background: #f8d7da; color: #721c24; }
        .result-info { background: #e7f3ff; padding: 10px; border-radius: 4px; margin: 10px 0; }
        .no-result { background: #fff3cd; padding: 10px; border-radius: 4px; margin: 10px 0; text-align: center; }
    </style>
</head>
<body>
    <div class="search-container">
        <h2>Tìm kiếm sản phẩm</h2>
        
        <form action="/products" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" class="search-input"
                   value="<%= keyword != null ? keyword : "" %>" 
                   placeholder="Nhập tên sản phẩm cần tìm..." required>
            <button type="submit" class="btn btn-info">Tìm kiếm</button>
        </form>

        <div>
            <a href="/products" class="btn btn-secondary">Quay lại danh sách</a>
            <% if (keyword != null && !keyword.trim().isEmpty()) { %>
                <div class="result-info">
                    Kết quả tìm kiếm cho: <strong>"<%= keyword %>"</strong>
                </div>
            <% } %>
        </div>

        <% if (products != null && !products.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Mã SP</th>
                        <th>Tên sản phẩm</th>
                        <th>Giá (VNĐ)</th>
                        <th>Số lượng</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Product p : products) { %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getProductCode() %></td>
                        <td><strong><%= p.getProductName() %></strong></td>
                        <td><%= String.format("%,.0f", p.getProductPrice()) %> VNĐ</td>
                        <td><%= p.getProductAmount() %></td>
                        <td>
                            <span class="status <%= "Còn hàng".equals(p.getProductStatus()) ? "status-instock" : "status-outstock" %>">
                                <%= p.getProductStatus() %>
                            </span>
                        </td>
                        <td>
                            <div class="action-buttons">
                                <a href="/products?action=view&id=<%= p.getId() %>" class="btn btn-primary">Xem</a>
                                <a href="/products?action=edit&id=<%= p.getId() %>" class="btn btn-warning">Sửa</a>
                                <form action="/products?action=delete" method="post" style="display:inline">
                                    <input type="hidden" name="id" value="<%= p.getId() %>">
                                    <button type="submit" class="btn btn-danger" 
                                            onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            
            <div class="result-info">
                Tìm thấy <strong><%= products.size() %></strong> sản phẩm phù hợp.
            </div>
        <% } else if (keyword != null && !keyword.trim().isEmpty()) { %>
            <div class="no-result">
                Không tìm thấy sản phẩm nào phù hợp với từ khóa "<%= keyword %>".
            </div>
        <% } %>
    </div>
</body>
</html>

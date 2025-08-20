<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="loipt.example.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .detail-container { max-width: 600px; margin: 0 auto; }
        .detail-item { margin-bottom: 15px; padding: 10px; border-bottom: 1px solid #eee; }
        .detail-label { font-weight: bold; color: #333; }
        .detail-value { margin-left: 10px; }
        .btn { padding: 10px 20px; margin: 5px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-primary { background: #007bff; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; color: white; }
        .description { background: #f8f9fa; padding: 15px; border-radius: 4px; margin: 15px 0; }
    </style>
</head>
<body>
    <div class="detail-container">
        <h2>Chi tiết sản phẩm</h2>
        
        <div class="detail-item">
            <span class="detail-label">ID:</span>
            <span class="detail-value"><%= product.getId() %></span>
        </div>
        
        <div class="detail-item">
            <span class="detail-label">Mã sản phẩm:</span>
            <span class="detail-value"><%= product.getProductCode() %></span>
        </div>
        
        <div class="detail-item">
            <span class="detail-label">Tên sản phẩm:</span>
            <span class="detail-value"><strong><%= product.getProductName() %></strong></span>
        </div>
        
        <div class="detail-item">
            <span class="detail-label">Giá:</span>
            <span class="detail-value"><strong><%= String.format("%,.0f", product.getProductPrice()) %> VNĐ</strong></span>
        </div>
        
        <div class="detail-item">
            <span class="detail-label">Số lượng:</span>
            <span class="detail-value"><%= product.getProductAmount() %></span>
        </div>
        
        <div class="detail-item">
            <span class="detail-label">Trạng thái:</span>
            <span class="detail-value"><%= product.getProductStatus() %></span>
        </div>
        
        <div class="detail-item">
            <span class="detail-label">Mô tả:</span>
            <div class="description">
                <%= product.getProductDescription() != null ? product.getProductDescription() : "Không có mô tả" %>
            </div>
        </div>
        
        <div>
            <a href="/products" class="btn btn-secondary">Quay lại danh sách</a>
            <a href="/products?action=edit&id=<%= product.getId() %>" class="btn btn-warning">Chỉnh sửa</a>
            <form action="/products?action=delete" method="post" style="display:inline">
                <input type="hidden" name="id" value="<%= product.getId() %>">
                <button type="submit" class="btn btn-danger" 
                        onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa sản phẩm</button>
            </form>
        </div>
    </div>
</body>
</html>

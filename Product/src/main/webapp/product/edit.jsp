<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="loipt.example.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
<head>
    <title>Chỉnh sửa sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .btn { padding: 10px 20px; margin: 5px; border: none; border-radius: 4px; cursor: pointer; }
        .btn-primary { background: #007bff; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .required { color: red; }
        .row { display: flex; gap: 15px; }
        .col { flex: 1; }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Chỉnh sửa sản phẩm</h2>
        
        <form action="/products?action=edit" method="post">
            <input type="hidden" name="id" value="<%= product.getId() %>">

            <div class="form-group">
                <label for="productCode">Mã sản phẩm <span class="required">*</span></label>
                <input type="text" id="productCode" name="productCode" 
                       value="<%= product.getProductCode() != null ? product.getProductCode() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="productName">Tên sản phẩm <span class="required">*</span></label>
                <input type="text" id="productName" name="productName" 
                       value="<%= product.getProductName() != null ? product.getProductName() : "" %>" required>
            </div>

            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="productPrice">Giá sản phẩm (VNĐ) <span class="required">*</span></label>
                        <input type="number" id="productPrice" name="productPrice" 
                               step="0.01" min="0" value="<%= product.getProductPrice() %>" required>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group">
                        <label for="productAmount">Số lượng <span class="required">*</span></label>
                        <input type="number" id="productAmount" name="productAmount" 
                               min="0" value="<%= product.getProductAmount() %>" required>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="productDescription">Mô tả</label>
                <textarea id="productDescription" name="productDescription" 
                          rows="3"><%= product.getProductDescription() != null ? product.getProductDescription() : "" %></textarea>
            </div>

            <div class="form-group">
                <label for="productStatus">Trạng thái</label>
                <select id="productStatus" name="productStatus">
                    <option value="Còn hàng" <%= "Còn hàng".equals(product.getProductStatus()) ? "selected" : "" %>>Còn hàng</option>
                    <option value="Hết hàng" <%= "Hết hàng".equals(product.getProductStatus()) ? "selected" : "" %>>Hết hàng</option>
                    <option value="Sắp hết" <%= "Sắp hết".equals(product.getProductStatus()) ? "selected" : "" %>>Sắp hết</option>
                </select>
            </div>

            <div>
                <a href="/products" class="btn btn-secondary">Quay lại</a>
                <button type="submit" class="btn btn-primary">Cập nhật sản phẩm</button>
            </div>
        </form>
    </div>
</body>
</html>

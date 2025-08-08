<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="loipt.example.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
<head>
    <title>Chỉnh sửa sản phẩm</title>
</head>
<body>
<h2>Chỉnh sửa sản phẩm</h2>
<form action="/products?action=edit" method="post">
    <input type="hidden" name="id" value="<%= product.getId() %>">

    <label>Tên sản phẩm:</label><br>
    <input type="text" name="name" value="<%= product.getName() %>" required><br><br>

    <label>Giá sản phẩm:</label><br>
    <input type="number" step="0.01" name="price" value="<%= product.getPrice() %>" required><br><br>

    <label>Mô tả:</label><br>
    <textarea name="description" rows="3"><%= product.getDescription() %></textarea><br><br>

    <label>Nhà sản xuất:</label><br>
    <input type="text" name="manufacturer" value="<%= product.getManufacturer() %>"><br><br>

    <button type="submit">Cập nhật</button>
    <a href="/products">Hủy</a>
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="loipt.example.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
</head>
<body>
<h2>Thông tin chi tiết sản phẩm</h2>
<p><strong>ID:</strong> <%= product.getId() %></p>
<p><strong>Tên sản phẩm:</strong> <%= product.getName() %></p>
<p><strong>Giá:</strong> <%= product.getPrice() %></p>
<p><strong>Mô tả:</strong> <%= product.getDescription() %></p>
<p><strong>Nhà sản xuất:</strong> <%= product.getManufacturer() %></p>

<a href="/products">Quay lại danh sách</a>
</body>
</html>

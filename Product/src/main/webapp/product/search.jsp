<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, loipt.example.product.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("results");
    String keyword = request.getParameter("keyword");
%>
<html>
<head>
    <title>Kết quả tìm kiếm</title>
</head>
<body>

<h2>Tìm kiếm sản phẩm</h2>
<form action="/products" method="get">
    <input type="hidden" name="action" value="search">
    <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>" placeholder="Nhập tên sản phẩm..." required>
    <button type="submit">Tìm kiếm</button>
    <a href="/products">Quay lại danh sách</a>
</form>
<hr>

<h3>Kết quả cho từ khóa: "<%= keyword %>"</h3>
<%
    if (products != null && !products.isEmpty()) {
%>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Nhà sản xuất</th>
        <th>Hành động</th>
    </tr>
    <%
        for (Product p : products) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getName() %></td>
        <td><%= p.getPrice() %></td>
        <td><%= p.getManufacturer() %></td>
        <td>
            <a href="/products?action=view&id=<%= p.getId() %>">Xem</a>
            <a href="/products?action=edit&id=<%= p.getId() %>">Sửa</a>
            <a href="/products?action=delete&id=<%= p.getId() %>" onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<p>Không tìm thấy sản phẩm nào.</p>
<%
    }
%>

</body>
</html>

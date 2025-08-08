<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
</head>
<body>
<h2>Danh sách sản phẩm</h2>
<a href="/products?action=create">Thêm mới</a> |
<a href="/products?action=search">Tìm kiếm</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Nhà sản xuất</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.manufacturer}</td>
            <td>
                <a href="/products?action=view&id=${product.id}">Xem</a> |
                <a href="/products?action=edit&id=${product.id}">Sửa</a> |
                <form action="/products?action=delete" method="post" style="display:inline">
                    <input type="hidden" name="id" value="${product.id}">
                    <input type="submit" value="Xóa" onclick="return confirm('Xóa sản phẩm này?')">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm sản phẩm mới</title>
</head>
<body>
<h2>Thêm sản phẩm mới</h2>
<form action="/products?action=create" method="post">
    <label>Tên sản phẩm:</label><br>
    <input type="text" name="name" required><br><br>

    <label>Giá sản phẩm:</label><br>
    <input type="number" step="0.01" name="price" required><br><br>

    <label>Mô tả:</label><br>
    <textarea name="description" rows="3"></textarea><br><br>

    <label>Nhà sản xuất:</label><br>
    <input type="text" name="manufacturer"><br><br>

    <button type="submit">Lưu</button>
    <a href="/products">Hủy</a>
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .header { background: #f0f0f0; padding: 10px; margin-bottom: 20px; }
        .btn { padding: 8px 16px; margin: 2px; text-decoration: none; border-radius: 4px; }
        .btn-primary { background: #007bff; color: white; }
        .btn-success { background: #28a745; color: white; }
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; color: white; }
        .btn-info { background: #17a2b8; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-buttons { display: flex; gap: 5px; }
        .status { padding: 4px 8px; border-radius: 3px; font-size: 12px; }
        .status-instock { background: #d4edda; color: #155724; }
        .status-outstock { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Quản lý sản phẩm</h2>
        <div>
            <a href="/products?action=create" class="btn btn-success">Thêm mới</a>
            <a href="/products?action=search" class="btn btn-info">Tìm kiếm</a>
            <a href="/statistics" class="btn btn-warning">Thống kê</a>
            <a href="/transaction" class="btn btn-secondary">Test Transaction</a>
        </div>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Danh mục</th>
                <th>Giá (VNĐ)</th>
                <th>Số lượng</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.productCode}</td>
                    <td><strong>${product.productName}</strong></td>
                    <td>${product.categoryId}</td>
                    <td>${String.format("%,.0f", product.productPrice)} VNĐ</td>
                    <td>${product.productAmount}</td>
                    <td>
                        <span class="status ${'Còn hàng'.equals(product.productStatus) ? 'status-instock' : 'status-outstock'}">
                            ${product.productStatus}
                        </span>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <a href="/products?action=view&id=${product.id}" class="btn btn-primary">Xem</a>
                            <a href="/products?action=edit&id=${product.id}" class="btn btn-warning">Sửa</a>
                            <form action="/products?action=delete" method="post" style="display:inline">
                                <input type="hidden" name="id" value="${product.id}">
                                <button type="submit" class="btn btn-danger" 
                                        onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty products}">
        <p style="text-align: center; color: #666; margin-top: 20px;">
            Không có sản phẩm nào trong danh sách.
        </p>
    </c:if>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .search-container { max-width: 800px; margin: 0 auto; }
        .search-form { 
            margin-bottom: 20px; 
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .search-input { 
            width: 70%; 
            padding: 10px; 
            border: 1px solid #ddd; 
            border-radius: 4px; 
            margin-right: 10px;
        }
        .btn { 
            padding: 10px 20px; 
            margin: 5px; 
            border: none; 
            border-radius: 4px; 
            cursor: pointer; 
            text-decoration: none;
            display: inline-block;
        }
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
        .result-info { 
            background: #e7f3ff; 
            padding: 10px; 
            border-radius: 4px; 
            margin: 10px 0; 
        }
        .no-result { 
            background: #fff3cd; 
            padding: 10px; 
            border-radius: 4px; 
            margin: 10px 0; 
            text-align: center; 
        }
    </style>
</head>
<body>
<div class="search-container">
    <h1>Tìm kiếm sản phẩm</h1>
    
    <form action="/products" method="get" class="search-form">
        <input type="hidden" name="action" value="search">
        
        <input type="text" name="keyword" class="search-input"
               value="${param.keyword}" 
               placeholder="Nhập tên sản phẩm cần tìm..." required>
        <button type="submit" class="btn btn-info">Tìm kiếm</button>
        <a href="/products" class="btn btn-secondary">Quay lại danh sách</a>
    </form>

    <c:if test="${not empty param.keyword}">
        <div class="result-info">
            Kết quả tìm kiếm cho: <strong>"${param.keyword}"</strong>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty results}">
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
                    <c:forEach var="product" items="${results}">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.productCode}</td>
                            <td><strong>${product.productName}</strong></td>
                            <td>${product.categoryName}</td>
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
            
            <c:if test="${not empty page}">
                <div class="result-info">
                    Trang <strong>${page.currentPage}</strong> / <strong>${page.totalPages}</strong> - 
                    Tổng: <strong>${page.totalRecords}</strong> sản phẩm.
                </div>
            </c:if>
        </c:when>
        <c:when test="${not empty param.keyword}">
            <div class="no-result">
                Không tìm thấy sản phẩm nào phù hợp với từ khóa "${param.keyword}".
            </div>
        </c:when>
    </c:choose>
</div>
</body>
</html>

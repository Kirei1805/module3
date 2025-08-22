<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .detail-container { 
            max-width: 600px; 
            margin: 20px auto; 
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .detail-item { 
            margin-bottom: 15px; 
            padding: 10px; 
            border-bottom: 1px solid #eee; 
        }
        .detail-label { 
            font-weight: bold; 
            color: #333; 
            display: inline-block;
            width: 120px;
        }
        .detail-value { 
            margin-left: 10px; 
            color: #666;
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
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; color: white; }
        .description { 
            background: #f8f9fa; 
            padding: 15px; 
            border-radius: 4px; 
            margin: 15px 0; 
            border-left: 4px solid #007bff;
        }
        .status-badge {
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-instock { 
            background: #d4edda; 
            color: #155724; 
        }
        .status-outstock { 
            background: #f8d7da; 
            color: #721c24; 
        }
        .price-highlight {
            color: #e74c3c;
            font-size: 18px;
            font-weight: bold;
        }
        .product-name {
            color: #2c3e50;
            font-size: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="detail-container">
    <h1>Chi tiết sản phẩm</h1>
    
    <div class="detail-item">
        <span class="detail-label">ID:</span>
        <span class="detail-value">${product.id}</span>
    </div>
    
    <div class="detail-item">
        <span class="detail-label">Mã sản phẩm:</span>
        <span class="detail-value">${product.productCode}</span>
    </div>
    
    <div class="detail-item">
        <span class="detail-label">Tên sản phẩm:</span>
        <span class="detail-value product-name">${product.productName}</span>
    </div>
    
    <div class="detail-item">
        <span class="detail-label">Danh mục:</span>
        <span class="detail-value">${product.categoryName}</span>
    </div>

    <div class="detail-item">
        <span class="detail-label">Giá:</span>
        <span class="detail-value price-highlight">${String.format("%,.0f", product.productPrice)} VNĐ</span>
    </div>
    
    <div class="detail-item">
        <span class="detail-label">Số lượng:</span>
        <span class="detail-value">${product.productAmount}</span>
    </div>
    
    <div class="detail-item">
        <span class="detail-label">Trạng thái:</span>
        <span class="detail-value">
            <span class="status-badge ${'Còn hàng'.equals(product.productStatus) ? 'status-instock' : 'status-outstock'}">
                ${product.productStatus}
            </span>
        </span>
    </div>
    
    <div class="detail-item">
        <span class="detail-label">Mô tả:</span>
        <div class="description">
            <c:choose>
                <c:when test="${not empty product.productDescription}">
                    ${product.productDescription}
                </c:when>
                <c:otherwise>
                    <em>Không có mô tả</em>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <div style="margin-top: 30px; text-align: center;">
        <a href="/products" class="btn btn-secondary">Quay lại danh sách</a>
        <a href="/products?action=edit&id=${product.id}" class="btn btn-warning">Chỉnh sửa</a>
        <form action="/products?action=delete" method="post" style="display:inline">
            <input type="hidden" name="id" value="${product.id}">
            <button type="submit" class="btn btn-danger" 
                    onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa sản phẩm</button>
        </form>
    </div>
</div>

</body>
</html>

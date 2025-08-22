<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa sản phẩm</title>
    <style>
        small {
            color: red;
        }
        .form-container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group textarea {
            resize: vertical;
            min-height: 80px;
        }
        .btn {
            padding: 10px 20px;
            margin: 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-primary {
            background: #007bff;
            color: white;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn:disabled {
            background: #ccc;
            cursor: not-allowed;
        }
        .row {
            display: flex;
            gap: 15px;
        }
        .col {
            flex: 1;
        }
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
<div class="form-container">
    <form action="/products?action=edit" method="post">
        <h1>Chỉnh sửa sản phẩm</h1>
        
        <!-- Hiển thị lỗi nếu có -->
        <c:if test="${not empty error}">
            <div class="error-message">
                <strong>Lỗi:</strong> ${error}
            </div>
        </c:if>
        
        <input type="hidden" name="id" value="${product.id}">
        
        <div class="form-group">
            <label for="productCode">Mã sản phẩm</label>
            <input id="productCode" name="productCode" 
                   placeholder="Nhập mã sản phẩm (VD: P001)"
                   value="${product.productCode}" required>
            <small id="productCodeError"></small>
        </div>
        
        <div class="form-group">
            <label for="productName">Tên sản phẩm</label>
            <input id="productName" name="productName" 
                   placeholder="Nhập tên sản phẩm"
                   value="${product.productName}" required>
            <small id="productNameError"></small>
        </div>
        
        <div class="form-group">
            <label for="categoryId">Danh mục</label>
            <select id="categoryId" name="categoryId" required>
                <option value="">-------Chọn danh mục-------</option>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.id}" 
                            ${product.categoryId == category.id ? 'selected' : ''}>
                        ${category.categoryName} (${category.categoryCode})
                    </option>
                </c:forEach>
            </select>
            <small id="categoryIdError"></small>
        </div>
        
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="productPrice">Giá sản phẩm (VNĐ)</label>
                    <input id="productPrice" name="productPrice" 
                           type="number" step="0.01" min="0" placeholder="Nhập giá sản phẩm"
                           value="${product.productPrice}" required>
                    <small id="productPriceError"></small>
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="productAmount">Số lượng</label>
                    <input id="productAmount" name="productAmount" 
                           type="number" min="0" placeholder="Nhập số lượng"
                           value="${product.productAmount}" required>
                    <small id="productAmountError"></small>
                </div>
            </div>
        </div>
        
        <div class="form-group">
            <label for="productDescription">Mô tả</label>
            <textarea id="productDescription" name="productDescription" 
                      placeholder="Nhập mô tả sản phẩm">${product.productDescription}</textarea>
        </div>
        
        <div class="form-group">
            <label for="productStatus">Trạng thái</label>
            <select id="productStatus" name="productStatus">
                <option value="Còn hàng" ${product.productStatus == 'Còn hàng' ? 'selected' : ''}>Còn hàng</option>
                <option value="Hết hàng" ${product.productStatus == 'Hết hàng' ? 'selected' : ''}>Hết hàng</option>
                <option value="Sắp hết" ${product.productStatus == 'Sắp hết' ? 'selected' : ''}>Sắp hết</option>
            </select>
        </div>
        
        <div>
            <button type="submit" class="btn btn-primary">Cập nhật</button>
            <a href="/products" class="btn btn-secondary">Quay lại</a>
        </div>
    </form>
</div>

</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="loipt.example.product.repository.CategoryRepository, loipt.example.product.repository.CategoryRepositoryImpl, loipt.example.product.model.Category, java.util.List" %>
<%
    CategoryRepository categoryRepo = new CategoryRepositoryImpl();
    List<Category> categories = categoryRepo.findAll();
%>
<html>
<head>
    <title>Thêm sản phẩm mới</title>
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
        .error { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 20px; border: 1px solid #f5c6cb; }
        .category-section { border: 1px solid #ddd; padding: 15px; border-radius: 4px; margin: 15px 0; }
        .category-toggle { margin-bottom: 10px; }
        .new-category-form { display: none; }
        .new-category-form.show { display: block; }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Thêm sản phẩm mới</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error">
                <strong>Lỗi:</strong> <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="/products?action=create" method="post">
            <div class="form-group">
                <label for="productCode">Mã sản phẩm <span class="required">*</span></label>
                <input type="text" id="productCode" name="productCode" required 
                       placeholder="Nhập mã sản phẩm (VD: P001)"
                       value="<%= request.getAttribute("product") != null ? ((loipt.example.product.model.Product)request.getAttribute("product")).getProductCode() : "" %>">
            </div>

            <div class="form-group">
                <label for="productName">Tên sản phẩm <span class="required">*</span></label>
                <input type="text" id="productName" name="productName" required 
                       placeholder="Nhập tên sản phẩm"
                       value="<%= request.getAttribute("product") != null ? ((loipt.example.product.model.Product)request.getAttribute("product")).getProductName() : "" %>">
            </div>

            <div class="category-section">
                <h3>Danh mục sản phẩm</h3>
                <div class="category-toggle">
                    <input type="radio" id="existing-category" name="categoryType" value="existing" checked onchange="toggleCategoryForm()">
                    <label for="existing-category">Chọn danh mục hiện có</label>
                    <br>
                    <input type="radio" id="new-category" name="categoryType" value="new" onchange="toggleCategoryForm()">
                    <label for="new-category">Tạo danh mục mới</label>
                </div>

                <div id="existing-category-form">
                    <div class="form-group">
                        <label for="categoryId">Chọn danh mục <span class="required">*</span></label>
                        <select id="categoryId" name="categoryId" required>
                            <option value="">Chọn danh mục</option>
                            <% for (Category category : categories) { %>
                                <option value="<%= category.getId() %>" 
                                        <%= request.getAttribute("product") != null && 
                                            ((loipt.example.product.model.Product)request.getAttribute("product")).getCategoryId() == category.getId() ? "selected" : "" %>>
                                    <%= category.getCategoryName() %> (<%= category.getCategoryCode() %>)
                                </option>
                            <% } %>
                        </select>
                    </div>
                </div>

                <div id="new-category-form" class="new-category-form">
                    <div class="form-group">
                        <label for="newCategoryCode">Mã danh mục mới <span class="required">*</span></label>
                        <input type="text" id="newCategoryCode" name="newCategoryCode" 
                               placeholder="Nhập mã danh mục (VD: CAT004)">
                    </div>
                    <div class="form-group">
                        <label for="newCategoryName">Tên danh mục mới <span class="required">*</span></label>
                        <input type="text" id="newCategoryName" name="newCategoryName" 
                               placeholder="Nhập tên danh mục">
                    </div>
                    <div class="form-group">
                        <label for="newCategoryDescription">Mô tả danh mục</label>
                        <textarea id="newCategoryDescription" name="newCategoryDescription" 
                                  rows="2" placeholder="Nhập mô tả danh mục"></textarea>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="productPrice">Giá sản phẩm (VNĐ) <span class="required">*</span></label>
                        <input type="number" id="productPrice" name="productPrice" 
                               step="0.01" min="0" required placeholder="Nhập giá sản phẩm"
                               value="<%= request.getAttribute("product") != null ? ((loipt.example.product.model.Product)request.getAttribute("product")).getProductPrice() : "" %>">
                    </div>
                </div>
                <div class="col">
                    <div class="form-group">
                        <label for="productAmount">Số lượng <span class="required">*</span></label>
                        <input type="number" id="productAmount" name="productAmount" 
                               min="0" required placeholder="Nhập số lượng"
                               value="<%= request.getAttribute("product") != null ? ((loipt.example.product.model.Product)request.getAttribute("product")).getProductAmount() : "" %>">
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="productDescription">Mô tả</label>
                <textarea id="productDescription" name="productDescription" 
                          rows="3" placeholder="Nhập mô tả sản phẩm"><%= request.getAttribute("product") != null ? ((loipt.example.product.model.Product)request.getAttribute("product")).getProductDescription() : "" %></textarea>
            </div>

            <div class="form-group">
                <label for="productStatus">Trạng thái</label>
                <select id="productStatus" name="productStatus">
                    <option value="Còn hàng" <%= request.getAttribute("product") != null && "Còn hàng".equals(((loipt.example.product.model.Product)request.getAttribute("product")).getProductStatus()) ? "selected" : "" %>>Còn hàng</option>
                    <option value="Hết hàng" <%= request.getAttribute("product") != null && "Hết hàng".equals(((loipt.example.product.model.Product)request.getAttribute("product")).getProductStatus()) ? "selected" : "" %>>Hết hàng</option>
                    <option value="Sắp hết" <%= request.getAttribute("product") != null && "Sắp hết".equals(((loipt.example.product.model.Product)request.getAttribute("product")).getProductStatus()) ? "selected" : "" %>>Sắp hết</option>
                </select>
            </div>

            <div>
                <a href="/products" class="btn btn-secondary">Quay lại</a>
                <button type="submit" class="btn btn-primary">Lưu sản phẩm</button>
            </div>
        </form>
    </div>

    <script>
        function toggleCategoryForm() {
            const existingForm = document.getElementById('existing-category-form');
            const newForm = document.getElementById('new-category-form');
            const existingRadio = document.getElementById('existing-category');
            
            if (existingRadio.checked) {
                existingForm.style.display = 'block';
                newForm.classList.remove('show');
                // Disable new category fields
                document.getElementById('newCategoryCode').required = false;
                document.getElementById('newCategoryName').required = false;
                document.getElementById('categoryId').required = true;
            } else {
                existingForm.style.display = 'none';
                newForm.classList.add('show');
                // Enable new category fields
                document.getElementById('newCategoryCode').required = true;
                document.getElementById('newCategoryName').required = true;
                document.getElementById('categoryId').required = false;
            }
        }
        
        // Initialize form state
        document.addEventListener('DOMContentLoaded', function() {
            toggleCategoryForm();
        });
    </script>
</body>
</html>

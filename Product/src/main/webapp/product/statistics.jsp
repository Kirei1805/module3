<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="loipt.example.product.model.ProductStatistics" %>
<%
    ProductStatistics statistics = (ProductStatistics) request.getAttribute("statistics");
%>
<html>
<head>
    <title>Thống kê sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin: 20px 0; }
        .stat-card { background: #f8f9fa; padding: 20px; border-radius: 8px; text-align: center; border: 1px solid #dee2e6; }
        .stat-number { font-size: 2em; font-weight: bold; color: #007bff; }
        .stat-label { color: #6c757d; margin-top: 5px; }
        .btn { padding: 10px 20px; margin: 5px; text-decoration: none; border-radius: 4px; display: inline-block; }
        .btn-primary { background: #007bff; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .progress-bar { width: 100%; height: 20px; background: #e9ecef; border-radius: 10px; overflow: hidden; margin: 10px 0; }
        .progress-fill { height: 100%; background: #28a745; transition: width 0.3s ease; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Thống kê sản phẩm</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number"><%= statistics.getTotalProducts() %></div>
                <div class="stat-label">Tổng số sản phẩm</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-number"><%= statistics.getTotalQuantity() %></div>
                <div class="stat-label">Tổng số lượng</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-number"><%= String.format("%,.0f", statistics.getAveragePrice()) %> VNĐ</div>
                <div class="stat-label">Giá trung bình</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-number"><%= statistics.getInStock() %></div>
                <div class="stat-label">Còn hàng</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-number"><%= statistics.getOutOfStock() %></div>
                <div class="stat-label">Hết hàng</div>
            </div>
        </div>
        
        <% if (statistics.getTotalProducts() > 0) { %>
            <div style="margin: 30px 0;">
                <h3>Tỷ lệ tồn kho</h3>
                <div>
                    <label>Còn hàng: <%= statistics.getInStock() %>/<%= statistics.getTotalProducts() %> 
                           (<%= String.format("%.1f", (double)statistics.getInStock()/statistics.getTotalProducts()*100) %>%)</label>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: <%= (double)statistics.getInStock()/statistics.getTotalProducts()*100 %>%"></div>
                    </div>
                </div>
                
                <div style="margin-top: 15px;">
                    <label>Hết hàng: <%= statistics.getOutOfStock() %>/<%= statistics.getTotalProducts() %> 
                           (<%= String.format("%.1f", (double)statistics.getOutOfStock()/statistics.getTotalProducts()*100) %>%)</label>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: <%= (double)statistics.getOutOfStock()/statistics.getTotalProducts()*100 %>%"></div>
                    </div>
                </div>
            </div>
        <% } %>
        
        <div>
            <a href="/products" class="btn btn-primary">Quay lại danh sách</a>
            <a href="/transaction" class="btn btn-secondary">Test Transaction</a>
        </div>
    </div>
</body>
</html>

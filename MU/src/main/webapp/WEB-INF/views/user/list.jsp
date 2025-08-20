<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý User</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .search-section {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 10px;
        }
        label {
            display: inline-block;
            width: 100px;
            font-weight: bold;
        }
        input[type="text"], select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 200px;
        }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 2px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        .btn-warning {
            background-color: #ffc107;
            color: black;
        }
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        .btn-info {
            background-color: #17a2b8;
            color: white;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .actions {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Quản lý User</h1>
        
        <!-- Form tìm kiếm và sắp xếp -->
        <div class="search-section">
            <form action="${pageContext.request.contextPath}/users" method="post">
                <input type="hidden" name="action" value="search">
                <div class="form-group">
                    <label for="country">Tìm theo Country:</label>
                    <input type="text" id="country" name="country" value="${searchCountry}" placeholder="Nhập tên quốc gia...">
                </div>
                <div class="form-group">
                    <label for="sortBy">Sắp xếp theo:</label>
                    <select id="sortBy" name="sortBy">
                        <option value="name" ${sortBy == 'name' ? 'selected' : ''}>Tên</option>
                        <option value="email" ${sortBy == 'email' ? 'selected' : ''}>Email</option>
                        <option value="country" ${sortBy == 'country' ? 'selected' : ''}>Quốc gia</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-info">Xem tất cả</a>
            </form>
        </div>

        <!-- Nút thêm mới -->
        <a href="${pageContext.request.contextPath}/users?action=create" class="btn btn-success">Thêm User mới</a>

        <!-- Bảng danh sách -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>
                        <a href="${pageContext.request.contextPath}/users?action=sort&sortBy=name&country=${searchCountry}" class="btn btn-info" style="padding: 2px 6px; font-size: 12px;">Tên</a>
                    </th>
                    <th>
                        <a href="${pageContext.request.contextPath}/users?action=sort&sortBy=email&country=${searchCountry}" class="btn btn-info" style="padding: 2px 6px; font-size: 12px;">Email</a>
                    </th>
                    <th>
                        <a href="${pageContext.request.contextPath}/users?action=sort&sortBy=country&country=${searchCountry}" class="btn btn-info" style="padding: 2px 6px; font-size: 12px;">Quốc gia</a>
                    </th>
                    <th class="actions">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.country}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}" class="btn btn-warning">Sửa</a>
                            <a href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}" 
                               class="btn btn-danger" 
                               onclick="return confirm('Bạn có chắc chắn muốn xóa user này?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty users}">
            <p style="text-align: center; color: #666; margin-top: 20px;">Không có user nào được tìm thấy.</p>
        </c:if>
    </div>
</body>
</html>


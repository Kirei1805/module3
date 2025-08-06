<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách khách hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .debug-info {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-family: monospace;
            font-size: 12px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border: 2px solid #4CAF50;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        .customer-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 5px;
            border: 2px solid #ddd;
        }
        .image-placeholder {
            width: 80px;
            height: 80px;
            background-color: #e0e0e0;
            border: 2px solid #ddd;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #666;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Danh sách khách hàng</h1>

        <table>
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Ngày sinh</th>
                    <th>Địa chỉ</th>
                    <th>Ảnh</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${customers}">
                    <tr>
                        <td><c:out value="${customer.name}" /></td>
                        <td><c:out value="${customer.birthDate}" /></td>
                        <td><c:out value="${customer.address}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty customer.image}">
                                    <img src="${customer.image}" alt="Ảnh khách hàng" class="customer-image" 
                                         onerror="this.parentElement.innerHTML='<div class=\'image-placeholder\'>Không có ảnh</div>'">
                                </c:when>
                                <c:otherwise>
                                    <div class="image-placeholder">Không có ảnh</div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
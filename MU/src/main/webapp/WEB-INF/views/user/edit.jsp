<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chỉnh sửa User</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="email"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 5px;
        }
        .btn-warning {
            background-color: #ffc107;
            color: black;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Chỉnh sửa User</h1>
        
        <form action="${pageContext.request.contextPath}/users" method="post">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" value="${user.id}">
            
            <div class="form-group">
                <label for="name">Tên:</label>
                <input type="text" id="name" name="name" value="${user.name}" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${user.email}" required>
            </div>
            
            <div class="form-group">
                <label for="country">Quốc gia:</label>
                <input type="text" id="country" name="country" value="${user.country}" required>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn btn-warning">Cập nhật</button>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý User - MU</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .welcome-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            padding: 12px 24px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <h1>Chào mừng đến với hệ thống quản lý User</h1>
        <p>Hệ thống hỗ trợ các chức năng:</p>
        <ul style="text-align: left; display: inline-block;">
            <li>Thêm, sửa, xóa User</li>
            <li>Tìm kiếm theo Country</li>
            <li>Sắp xếp theo Name, Email, Country</li>
        </ul>
        <br><br>
        <a href="users" class="btn">Bắt đầu quản lý User</a>
    </div>
</body>
</html>
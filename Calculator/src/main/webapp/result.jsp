<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .result-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border: 1px solid #ddd;
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
            font-size: 2.5em;
        }
        .calculation {
            font-size: 2em;
            color: #4CAF50;
            margin: 20px 0;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            border: 2px solid #e9ecef;
        }
        .back-btn {
            background-color: #2196F3;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
        }
        .back-btn:hover {
            background-color: #1976D2;
        }
        .operator-symbol {
            font-weight: bold;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="result-container">
        <h1>Result:</h1>
        
        <div class="calculation">
            <%= request.getAttribute("firstOperand") %> 
            <span class="operator-symbol">
                <% 
                    String operator = (String) request.getAttribute("operator");
                    String symbol = "";
                    switch(operator) {
                        case "addition": symbol = "+"; break;
                        case "subtraction": symbol = "-"; break;
                        case "multiplication": symbol = "*"; break;
                        case "division": symbol = "/"; break;
                        default: symbol = operator;
                    }
                %>
                <%= symbol %>
            </span> 
            <%= request.getAttribute("secondOperand") %> 
            = 
            <%= request.getAttribute("result") %>
        </div>
        
        <a href="index.jsp" class="back-btn">Quay lại máy tính</a>
    </div>
</body>
</html> 
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Discount Calculator</title>
</head>
<body>
    <h1>Product Discount Calculator</h1>
    
    <form action="display-discount" method="post">
        <p>
            <label>Product Description:</label>
            <input type="text" name="description" required>
        </p>
        
        <p>
            <label>List Price:</label>
            <input type="number" name="price" step="0.01" required>
        </p>
        
        <p>
            <label>Discount Percent:</label>
            <input type="number" name="discount" step="0.01" required>
        </p>
        
        <p>
            <input type="submit" value="Calculate Discount">
        </p>
    </form>
</body>
</html>
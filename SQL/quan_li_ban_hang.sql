
CREATE DATABASE QuanLyBanHang;
USE QuanLyBanHang;

CREATE TABLE Customer (
    cID INT PRIMARY KEY,
    cName VARCHAR(100),
    cAge INT
);

CREATE TABLE `Order` (
    oID INT PRIMARY KEY,
    cID INT,
    oDate DATE,
    oTotalPrice DECIMAL(18,2),
    FOREIGN KEY (cID) REFERENCES Customer(cID)
);

CREATE TABLE Product (
    pID INT PRIMARY KEY,
    pName VARCHAR(100),
    pPrice DECIMAL(18,2)
);

CREATE TABLE OrderDetail (
    oID INT,
    pID INT,
    odQTY INT,
    PRIMARY KEY (oID, pID),
    FOREIGN KEY (oID) REFERENCES `Order`(oID),
    FOREIGN KEY (pID) REFERENCES Product(pID)
);

INSERT INTO Customer (cID, cName, cAge) VALUES
(1, 'Minh Quan', 10),
(2, 'Ngoc Oanh', 20),
(3, 'Hong Ha', 50);

INSERT INTO `Order` (oID, cID, oDate, oTotalPrice) VALUES
(1, 1, '2006-03-21', NULL),
(2, 2, '2006-03-23', NULL),
(3, 1, '2006-03-16', NULL);

INSERT INTO Product (pID, pName, pPrice) VALUES
(1, 'May Giat', 3),
(2, 'Tu Lanh', 5),
(3, 'Dieu Hoa', 7),
(4, 'Quat', 2),
(5, 'Bep Dien', 2);

INSERT INTO OrderDetail (oID, pID, odQTY) VALUES
(1, 1, 3),
(1, 3, 7),
(1, 4, 2),
(2, 1, 1),
(2, 2, 1),
(2, 3, 10),
(3, 1, 1),
(3, 5, 5);


select oID, oDate, oTotalPrice as oPrice
from `Order`;


SELECT DISTINCT C.cID, C.cName, P.pName
FROM Customer C
JOIN `Order` O ON C.cID = O.cID
JOIN OrderDetail OD ON O.oID = OD.oID
JOIN Product P ON OD.pID = P.pID;


SELECT cID, cName
FROM Customer
WHERE cID NOT IN (
    SELECT DISTINCT cID
    FROM `Order`
);


SELECT 
    O.oID,
    O.oDate,
    SUM(OD.odQTY * P.pPrice) AS oPrice
FROM `Order` O
JOIN OrderDetail OD ON O.oID = OD.oID
JOIN Product P ON OD.pID = P.pID
GROUP BY O.oID, O.oDate;


UPDATE `Order` O
SET oTotalPrice = (
    SELECT SUM(OD.odQTY * P.pPrice)
    FROM OrderDetail OD
    JOIN Product P ON OD.pID = P.pID
    WHERE OD.oID = O.oID
);

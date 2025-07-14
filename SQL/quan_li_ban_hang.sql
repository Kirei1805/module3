CREATE DATABASE IF NOT EXISTS QuanLyBanHang;
USE QuanLyBanHang;

CREATE TABLE Customer (
    cID INT PRIMARY KEY,
    cName NVARCHAR(100),
    cAge INT
);

CREATE TABLE Product (
    pID INT PRIMARY KEY,
    pName NVARCHAR(100),
    pPrice DECIMAL(18,2)
);

CREATE TABLE `Order` (
    oID INT PRIMARY KEY,
    cID INT,
    oDate DATE,
    oTotalPrice DECIMAL(18,2),
    FOREIGN KEY (cID) REFERENCES Customer(cID)
);

CREATE TABLE OrderDetail (
    oID INT,
    pID INT,
    odQTY INT,
    PRIMARY KEY (oID, pID),
    FOREIGN KEY (oID) REFERENCES `Order`(oID),
    FOREIGN KEY (pID) REFERENCES Product(pID)
);


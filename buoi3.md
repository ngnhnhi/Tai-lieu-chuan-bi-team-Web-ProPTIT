# Buổi 3: SQL CƠ BẢN

## I. Các thao tác cơ bản: SELECT, INSERT INTO, UPDATE, DELETE, từ khóa AS, DISTINCT
### 1. SELECT
- `SELECT` dùng để chọn dữ liệu từ một hay nhiều cột trong CSDL.
- SELECT syntax:  
```sql
SELECT column1, column2, ... FROM table_name;
```
- Lấy tất cả các cột trong bảng:
```sql
SELECT * FROM table_name;
```

### 2. INSERT INTO
- `INSERT INTO` dùng để thêm hàng dữ liệu mới vào bảng. Có hai cách dùng `SELECT INTO`:
    - **Cách 1: Chỉ định cả tên cột và giá trị được chèn**   
    ```sql
    INSERT INTO table_name (column1, column2, column3, ...)
    VALUES (value1, value2, value3, ...);
    ```  
    Lưu ý: Dữ liệu ở những cột không được chọn sẽ trả về giá trị mặc định/null/báo lỗi nếu có ràng buộc `not null`.  

    - **Cách 2: Thêm dữ liệu cho tất cả các cột**  
    ```sql
    INSERT INTO table_name
    VALUES (value1, value2, value3, ...);
    ```  
- Ví dụ:
```sql
-- Chỉ định cột
INSERT INTO Customers (CustomerName, City, Country)
VALUES ('Cardinal', 'Stavanger', 'Norway');

-- Tất cả các cột
INSERT INTO Customers
VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');

-- Thêm nhiều hàng
INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
VALUES
('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway'),
('Greasy Burger', 'Per Olsen', 'Gateveien 15', 'Sandnes', '4306', 'Norway'),
('Tasty Tee', 'Finn Egan', 'Streetroad 19B', 'Liverpool', 'L1 0AA', 'UK');
```

### 3. UPDATE
- `UPDATE` dùng để cập nhật/sửa đổi một hay nhiều hàng trong một bảng.
- UPDATE Syntax:
```sql
UPDATE table_name
SET column1 = value1, column2 = value2, ...
WHERE condition;
```  
- Ví dụ:  
```sql
-- Sửa đổi 1 hàng
UPDATE Customers
SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
WHERE CustomerID = 1;

-- Sửa đổi nhiều hàng
UPDATE Customers
SET ContactName='Juan'
WHERE Country='Mexico';

-- Sửa đổi tất cả các hàng
UPDATE Customers
SET ContactName='Juan';
```

### 4. DELETE
- `DELETE` dùng để xóa các hàng đã có trong bảng.
- DELETE Syntax:
```sql
DELETE FROM table_name WHERE condition;
```
- Ví dụ:
```sql
DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
```

### 5. Từ khóa AS
`AS` được dùng để đổi tên một cột hoặc bảng bằng một biệt danh. Một biệt danh chỉ tồn tại trong suốt thời gian của truy vấn.  
- **Biệt danh cho cột**  
Lưu ý: Nếu tên biệt danh có dấu cách thì cần đặt trong ngoặc vuông.
```sql
SELECT CustomerName AS Customer, ContactName AS [Contact Person]
FROM Customers;

-- Đặt biệt danh cho tổ hợp nhiều cột
SELECT CustomerName, CONCAT(Address,', ',PostalCode,', ',City,', ',Country) AS Address
FROM Customers;

-- Đặt tên cho cột tính toán
SELECT COUNT(OrderID) AS TotalOrders, 
       SUM(Price * Quantity) AS TotalRevenue
FROM OrderDetails;
```
- **Biệt danh cho bảng**
```sql
SELECT o.OrderID, o.OrderDate, c.CustomerName
FROM Customers AS c, Orders AS o
WHERE c.CustomerName="Around the Horn" AND c.CustomerID=o.CustomerID;
```
### 6. DISTINCT
- `SELECT DISTINCT` dùng để trả về các giá trị duy nhât khi một cột có nhiều giá trị trùng lặp.
- Syntax:
```sql
SELECT DISTINCT column1, column2, ...
FROM table_name;
```
- Ví dụ: 
```sql
SELECT DISTINCT Country, CustomerName
FROM Customers;
```

## II. Lọc dữ liệu: WHERE, HAVING
### 1. WHERE
- `WHERE` dùng để lọc dữ liệu theo điều kiện cụ thể.
- Một số toán tử thường dùng:

| Toán tử | Mô tả | Ví dụ |
| :--- | :--- | :--- |
| **`=`** | Bằng | `WHERE Age = 25` |
| **`>`** | Lớn hơn | `WHERE Salary > 5000` |
| **`<`** | Nhỏ hơn | `WHERE Quantity < 10` |
| **`>=`** | Lớn hơn hoặc bằng | `WHERE Age >= 18` |
| **`<=`** | Nhỏ hơn hoặc bằng | `WHERE Price <= 100` |
| **`<>`** hoặc **`!=`** | Không bằng (Khác). *Lưu ý: Toán tử `!=` tùy thuộc vào từng phiên bản SQL.* | `WHERE Status <> 'InActive'` |
| **`BETWEEN`** | Nằm trong một khoảng giá trị nhất định (bao gồm cả biên) | `WHERE Price BETWEEN 10 AND 50` |
| **`LIKE`** | Tìm kiếm theo một khuôn mẫu (Pattern) dữ liệu | `WHERE Name LIKE 'A%'` |
| **`IN`** | Chỉ định nhiều giá trị có thể chấp nhận được cho một cột | `WHERE Country IN ('Vietnam', 'USA', 'Japan')` |
- Ví dụ:
```sql
SELECT * FROM Customers
WHERE CustomerID > 80;
```

### 2. HAVING
- `HAVING` được dùng để lọc kết quả của một truy vấn `GROUP BY` dựa trên các hàm tổng hợp.
- Trong khi mệnh đề `WHERE` dùng để lọc các hàng riêng lẻ trước khi nhóm, mệnh đề `HAVING` lọc các nhóm sau khi quá trình tổng hợp đã được thực hiện.
- Syntax:
```sql
SELECT column1, aggregate_function(column2), column3, ...
FROM table_name
WHERE condition
GROUP BY column1, column3
HAVING condition -- The condition on grouped data
ORDER BY column_name;
```
- Ví dụ:
```sql
SELECT Country, COUNT(CustomerID) AS [Number of Customers]
FROM Customers
GROUP BY Country
HAVING COUNT(CustomerID) > 5;
```
## III. Kết hợp bảng và kết quả: JOIN, UNION
## IV. Tổng hợp và nhóm dữ liệu: COUNT, SUM, AVG, GROUP BY
## V. Truy vấn con: Subquery
## VI. Thứ tự thực thi logic của truy vấn
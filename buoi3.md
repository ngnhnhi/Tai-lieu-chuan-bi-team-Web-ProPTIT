# Buổi 3: SQL CƠ BẢN

- [Buổi 3: SQL CƠ BẢN](#buổi-3-sql-cơ-bản)
  - [I. Các thao tác cơ bản: SELECT, INSERT INTO, UPDATE, DELETE, từ khóa AS, DISTINCT](#i-các-thao-tác-cơ-bản-select-insert-into-update-delete-từ-khóa-as-distinct)
    - [1. Câu lệnh SELECT](#1-câu-lệnh-select)
    - [2. Câu lệnh INSERT INTO](#2-câu-lệnh-insert-into)
    - [3. Câu lệnh UPDATE](#3-câu-lệnh-update)
    - [4. Câu lệnh DELETE](#4-câu-lệnh-delete)
    - [5. Từ khóa AS](#5-từ-khóa-as)
    - [6. DISTINCT](#6-distinct)
  - [II. Lọc dữ liệu: WHERE, HAVING](#ii-lọc-dữ-liệu-where-having)
    - [1. Câu lệnh WHERE](#1-câu-lệnh-where)
    - [2. Câu lệnh HAVING](#2-câu-lệnh-having)
  - [III. Kết hợp bảng và kết quả: JOIN, UNION](#iii-kết-hợp-bảng-và-kết-quả-join-union)
    - [1. Câu lệnh JOIN](#1-câu-lệnh-join)
      - [a. Câu lệnh (INNER) JOIN](#a-câu-lệnh-inner-join)
      - [b. Câu lệnh LEFT JOIN, RIGHT JOIN](#b-câu-lệnh-left-join-right-join)
    - [2. Câu lệnh UNION](#2-câu-lệnh-union)
  - [IV. Tổng hợp và nhóm dữ liệu: COUNT, SUM, AVG, GROUP BY](#iv-tổng-hợp-và-nhóm-dữ-liệu-count-sum-avg-group-by)
    - [1. Hàm COUNT()](#1-hàm-count)
    - [2. Hàm SUM()](#2-hàm-sum)
    - [3. Hàm AVG()](#3-hàm-avg)
    - [4. Câu lệnh GROUP BY](#4-câu-lệnh-group-by)
  - [V. Truy vấn con: Subquery](#v-truy-vấn-con-subquery)
    - [1. Định nghĩa](#1-định-nghĩa)
    - [2. Các loại Subquery](#2-các-loại-subquery)
      - [a. Single-Row Subquery](#a-single-row-subquery)
      - [b. Multi-Row Subquery](#b-multi-row-subquery)
      - [c. Correlated Subquery](#c-correlated-subquery)
  - [VI. Thứ tự thực thi logic của truy vấn](#vi-thứ-tự-thực-thi-logic-của-truy-vấn)

## I. Các thao tác cơ bản: SELECT, INSERT INTO, UPDATE, DELETE, từ khóa AS, DISTINCT
### 1. Câu lệnh SELECT
- `SELECT` dùng để chọn dữ liệu từ một hay nhiều cột trong CSDL.
- SELECT syntax:  
```sql
SELECT column1, column2, ... FROM table_name;
```
- Lấy tất cả các cột trong bảng:
```sql
SELECT * FROM table_name;
```

### 2. Câu lệnh INSERT INTO
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

### 3. Câu lệnh UPDATE
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

### 4. Câu lệnh DELETE
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
### 1. Câu lệnh WHERE
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

### 2. Câu lệnh HAVING
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
### 1. Câu lệnh JOIN
`JOIN` dùng để kết hợp các hàng từ >= 2 bảng, dựa vào một cột chung giữa chúng.
#### a. Câu lệnh (INNER) JOIN
- `INNER JOIN` trả về những hàng có giá trị giống nhau ở các bảng. Đây là dạng mặc định của `JOIN`.
- Syntax:
```sql
SELECT column_name(s)
FROM table1
INNER JOIN table2
ON table1.column_name = table2.column_name; -- ON dùng để xác định điều kiện khớp
```
- Ví dụ:  
Bảng Products:  

| ProductID | ProductName   | CategoryID | Price |
|----------|--------------|-----------|------|
| 3         | Aniseed Syrup | 2          | 10.00 |  

Bảng Categories:  
| CategoryID | CategoryName | Description |
|:---|:---|:---|
| 2 | Condiments | Sweet and savory sauces, relishes, spreads, and seasonings |  

```sql
SELECT ProductID, ProductName, CategoryName
FROM Products
INNER JOIN Categories ON Products.CategoryID = Categories.CategoryID;
```

Kết quả:  
| ProductID | ProductName   | CategoryName |
|:----------|:--------------|:-------------|
| 3         | Aniseed Syrup | Condiments   |

#### b. Câu lệnh LEFT JOIN, RIGHT JOIN
- `LEFT JOIN` trả về tất cả các hàng từ bảng bên trái (bảng1) và chỉ các hàng phù hợp từ bảng bên phải (bảng2).
- Nếu không có kết quả phù hợp trong bảng bên phải, kết quả cho các cột từ bảng bên phải sẽ là NULL.
- Syntax:
```sql
SELECT column_name(s)
FROM table1
LEFT JOIN table2
ON table1.column_name = table2.column_name;
```
- Ví dụ:  

Bảng Customers

| CustomerID | CustomerName | ContactName | Address | City | PostalCode | Country |
|:---|:---|:---|:---|:---|:---|:---|
| 1 | Alfreds Futterkiste | Maria Anders | Obere Str. 57 | Berlin | 12209 | Germany |
| 2 | Ana Trujillo Emparedados y helados | Ana Trujillo | Avda. de la Constitución 2222 | México D.F. | 05021 | Mexico |
| 3 | Antonio Moreno Taquería | Antonio Moreno | Mataderos 2312 | México D.F. | 05023 | Mexico |

Bảng Orders

| OrderID | CustomerID | EmployeeID | OrderDate | ShipperID |
|:---|:---|:---|:---|:---|
| 10308 | 2 | 7 | 1996-09-18 | 3 |
| 10309 | 37 | 3 | 1996-09-19 | 1 |
| 10310 | 77 | 8 | 1996-09-20 | 2 |

```sql
SELECT Customers.CustomerName, Orders.OrderID
FROM Customers
LEFT JOIN Orders
ON Customers.CustomerID=Orders.CustomerID
```

Kết quả:  
| CustomerName | OrderID |
|:---|:---|
| Alfreds Futterkiste | NULL |
| Ana Trujillo Emparedados y helados | 10308 |
| Antonio Moreno Taquería | NULL |


### 2. Câu lệnh UNION
- `UNION` được dùng để kết hợp tập hợp kết quả của hai hoặc nhiều câu lệnh `SELECT` và tự động loại bỏ các hàng trùng lặp khỏi tập hợp kết quả.  
- Lưu ý khi dùng UNION:
    - Mọi câu lệnh `SELECT` trong `UNION` phải có cùng số lượng cột
    - Các cột phải có kiểu dữ liệu tương tự
    - Các cột trong mỗi câu lệnh `SELECT` cũng phải theo cùng một thứ tự  

- Syntax:
```sql
SELECT column_name(s) FROM table1
UNION
SELECT column_name(s) FROM table2;
```

- Ví dụ:
```sql
SELECT 'Customer' AS Type, ContactName, City, Country
FROM Customers
UNION
SELECT 'Supplier', ContactName, City, Country
FROM Suppliers;
```
->  Tạo một cột tạm thời tên là "Type", liệt kê xem người liên hệ là "Customer" hay "Supplier".

## IV. Tổng hợp và nhóm dữ liệu: COUNT, SUM, AVG, GROUP BY
### 1. Hàm COUNT()
- `COUNT()` trả về số hàng khớp với một tiêu chí đã chỉ định.
- Syntax:
```sql
SELECT COUNT([DISTINCT] column_name | *)
FROM table_name
WHERE condition;
```

- Ví dụ:  
Bảng Products:  

| ProductID | ProductName | SupplierID | CategoryID | Unit | Price |
|:---|:---|:---|:---|:---|:---|
| 1 | Chai | 1 | 1 | 10 boxes x 20 bags | 18.00 |
| 2 | Chang | 1 | 1 | 24 - 12 oz bottles | 19.00 |
| 3 | Aniseed Syrup | 1 | 2 | 12 - 550 ml bottles | 10.00 |
| 4 | Chef Anton's Cajun Seasoning | 2 | 2 | 48 - 6 oz jars | 22.00 |
| 5 | Chef Anton's Gumbo Mix | 2 | 2 | 36 boxes | 21.35 |

```sql
-- Đếm tổng số hàng trong bảng "Products" (bao gồm các giá trị NULL)
SELECT COUNT(*)
FROM Products;

-- Đếm tất cả các giá trị khác NULL của cột "ProductName"
SELECT COUNT(ProductName)
FROM Products;

-- Đếm có bao nhiêu mức giá khác nhau trong bảng "Products"
SELECT COUNT(DISTINCT Price)
FROM Products;
```
### 2. Hàm SUM()
- `SUM()` được dùng để tính tổng các giá trị trong một cột số.
- Syntax:
```sql
SELECT SUM(column_name)
FROM table_name
WHERE condition;
```
### 3. Hàm AVG()
- `AVG()` trả về giá trị trung bình của một cột số.
- Syntax:
```sql
SELECT AVG(Price)
FROM Products;
```
### 4. Câu lệnh GROUP BY
- `GROUP BY` được sử dụng để nhóm các hàng có cùng giá trị thành các hàng tóm tắt, ví dụ như "Tìm số lượng khách hàng ở mỗi quốc gia".
- `GROUP BY` hầu thường được sử dụng cùng với các hàm tổng hợp như COUNT(), MAX(), MIN(), SUM(), AVG() để thực hiện tính toán trên mỗi nhóm.
- Syntax:
```sql
SELECT column1, aggregate_function(column2), column3, ...
FROM table_name
WHERE condition
GROUP BY column1, column3
ORDER BY column_name;
```
- Ví dụ:
```sql
SELECT Country, COUNT(CustomerID) AS [Number of Customers]
FROM Customers
GROUP BY Country;
```

## V. Truy vấn con: Subquery
### 1. Định nghĩa
Truy vấn con (Subquery) trong SQL là một truy vấn được lồng vào bên trong một truy vấn SQL khác. Nó cho phép lọc, tổng hợp và thao tác dữ liệu phức tạp bằng cách sử dụng kết quả của một truy vấn bên trong một truy vấn khác. Chúng là một công cụ thiết yếu khi chúng ta cần thực hiện các thao tác như:  
- Áp dụng các hàm tổng hợp như SUM, COUNT hoặc AVG một cách linh hoạt.
- Cập nhật dữ liệu bằng cách sử dụng các giá trị từ các bảng khác.
- Xóa các hàng dựa trên các điều kiện được trả về bởi một truy vấn khác.
### 2. Các loại Subquery
#### a. Single-Row Subquery
Single-Row Subquery là một truy vấn con chỉ trả về một giá trị.Nó thường được sử dụng với các toán tử so sánh như =, >, <.

Ví dụ:
```sql
-- Tìm ra nhân viên có lương cao nhất
SELECT * FROM Employees
WHERE Salary = (SELECT MAX(Salary) FROM Employees);
```

#### b. Multi-Row Subquery
Multi-Row Subquery là một truy vấn con trả về nhiều hơn một giá trị. 

Ví dụ:
```sql
-- Xác định tất cả các nhân viên có căn hộ ở New York
SELECT * FROM Employees
WHERE DepartmentID IN (SELECT DepartmentID FROM Departments WHERE Location = 'New York');
```
#### c. Correlated Subquery
Correlated Subquery là một truy vấn con phụ thuộc vào truy vấn bên ngoài về các giá trị.

Ví dụ:
```sql
-- Trả về nhân viên có mức lương cao hơn mức trung bình của phòng ban của họ.
SELECT e.Name, e.Salary
FROM Employees e
WHERE e.Salary > (SELECT AVG(Salary) 
                  FROM Employees 
                  WHERE DepartmentID = e.DepartmentID);
```
## VI. Thứ tự thực thi logic của truy vấn
Thứ tự thực thi logic của truy vấn:
1. `FROM`  
Đây là bước đầu tiên, SQL xác định các bảng cần truy vấn.
Nếu có các phép nối (INNER JOIN, LEFT JOIN, RIGHT JOIN, v.v.), SQL thiết lập các mối quan hệ giữa các bảng và kết hợp theo chỉ định.
2. `WHERE`  
Sau khi xác định và kết hợp các bảng, câu lệnh WHERE được sử dụng để lọc các hàng dựa trên các điều kiện đã chỉ định.
Chỉ những hàng thỏa mãn điều kiện mới được chuyển đến bước tiếp theo.
3. `GROUP BY`  
Nếu truy vấn có bao gồm các phép tổng hợp, câu lệnh GROUP BY sẽ nhóm các hàng có cùng giá trị trong các cột đã chỉ định thành các hàng tóm tắt.
4. `HAVING`  
Câu lệnh này được áp dụng cho các nhóm được tạo bởi GROUP BY.
5. `SELECT`  
Bước này xác định các cột sẽ được trả về trong tập kết quả.
Có thể bao gồm các cột từ bảng, các hàm tổng hợp và các phép tính dựa trên các cột.
6. `DISTINCT`  
Sau khi chọn các cột, câu lệnh DISTINCT được áp dụng để loại bỏ các hàng trùng lặp khỏi tập kết quả.
7. `ORDER BY`  
Câu lệnh này sắp xếp tập kết quả dựa trên một hoặc nhiều cột.
Việc sắp xếp có thể theo thứ tự tăng dần (ASC) hoặc giảm dần (DESC).
8. `LIMIT/OFFSET`  
Câu lệnh LIMIT giới hạn số hàng được trả về.
Câu lệnh OFFSET có thể được sử dụng để bỏ qua một số hàng nhất định trước khi bắt đầu trả về các hàng.

Ví dụ:
```sql
-- Tìm các phòng ban có tổng chi tiêu lương cao trong năm 2023
SELECT DISTINCT                      -- [BƯỚC 5 & 6] 5: Tính toán biểu thức/cột, 6: Loại bỏ các dòng trùng lặp
    d.department_name,
    COUNT(e.employee_id) AS total_employees,
    SUM(e.salary) AS total_salary
FROM departments d                   -- [BƯỚC 1] Xác định bảng gốc và thực hiện JOIN các bảng liên quan
JOIN employees e 
    ON d.department_id = e.department_id
WHERE e.hire_date >= '2023-01-01'    -- [BƯỚC 2] Lọc các dòng thô trước khi gom nhóm
GROUP BY d.department_name           -- [BƯỚC 3] Gom nhóm dữ liệu theo cột chỉ định
HAVING SUM(e.salary) > 500000        -- [BƯỚC 4] Lọc các nhóm sau khi đã gom và tính toán hàm tổng hợp
ORDER BY total_salary DESC           -- [BƯỚC 7] Sắp xếp tập kết quả theo thứ tự chỉ định
LIMIT 5 OFFSET 0;                    -- [BƯỚC 8] Phân trang, lấy tối đa 5 bản ghi đầu tiên
```
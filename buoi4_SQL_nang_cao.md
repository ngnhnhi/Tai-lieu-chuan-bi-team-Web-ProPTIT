# BUỔI 4: SQL NÂNG CAO

- [BUỔI 4: SQL NÂNG CAO](#buổi-4-sql-nâng-cao)
  - [I. Tối ưu truy vấn](#i-tối-ưu-truy-vấn)
    - [1. Đọc kế hoạch thực thi (Execution Plan)](#1-đọc-kế-hoạch-thực-thi-execution-plan)
      - [1.1 Sử dụng EXPLAIN](#11-sử-dụng-explain)
      - [1.2 Sử dụng EXPLAIN ANALYZE](#12-sử-dụng-explain-analyze)
    - [2. Các bước tối ưu một truy vấn](#2-các-bước-tối-ưu-một-truy-vấn)
    - [3. Các kỹ thuật tối ưu truy vấn thường gặp](#3-các-kỹ-thuật-tối-ưu-truy-vấn-thường-gặp)
      - [3.1 Chỉ định cột thay vì SELECT \*](#31-chỉ-định-cột-thay-vì-select-)
      - [3.2 Tận dụng chỉ số (Index)](#32-tận-dụng-chỉ-số-index)
      - [3.3 Tránh dùng hàm trong mệnh đề WHERE](#33-tránh-dùng-hàm-trong-mệnh-đề-where)
      - [3.4 Rút gọn truy vấn con (Un-nest sub-query)](#34-rút-gọn-truy-vấn-con-un-nest-sub-query)
      - [3.5 Hạn chế DISTINCT và HAVING khi không cần thiết](#35-hạn-chế-distinct-và-having-khi-không-cần-thiết)
      - [3.6 Xử lý mệnh đề OR phức tạp](#36-xử-lý-mệnh-đề-or-phức-tạp)
      - [3.7 Ưu tiên UNION ALL thay cho UNION](#37-ưu-tiên-union-all-thay-cho-union)
  - [II. Index](#ii-index)
    - [1. Khái niệm Index](#1-khái-niệm-index)
    - [2. Khi nào nên tạo Index?](#2-khi-nào-nên-tạo-index)
      - [2.1. Cột thường xuyên sử dụng trong WHERE](#21-cột-thường-xuyên-sử-dụng-trong-where)
      - [2.2. Cột thường xuyên sử dụng trong JOIN](#22-cột-thường-xuyên-sử-dụng-trong-join)
      - [2.3. Cột thường xuyên sử dụng trong ORDER BY](#23-cột-thường-xuyên-sử-dụng-trong-order-by)
      - [2.4. Cột thường xuyên được tìm kiếm](#24-cột-thường-xuyên-được-tìm-kiếm)
    - [3. Thao tác với Index](#3-thao-tác-với-index)
      - [3.1. Tạo Index trên một cột](#31-tạo-index-trên-một-cột)
      - [3.2. Tạo Unique Index](#32-tạo-unique-index)
      - [3.3. Xóa Index](#33-xóa-index)
    - [4. Các kiểu Index thường gặp](#4-các-kiểu-index-thường-gặp)
      - [4.1. Single-column Index](#41-single-column-index)
      - [4.2. Unique Index](#42-unique-index)
      - [4.3. Composite Index](#43-composite-index)
    - [5. Quy tắc tiền tố trái - Left-prefix Rule](#5-quy-tắc-tiền-tố-trái---left-prefix-rule)
    - [6. Chiến lược chọn thứ tự cột trong Composite Index](#6-chiến-lược-chọn-thứ-tự-cột-trong-composite-index)
    - [7. Nguyên tắc "Equality trước, Range sau"](#7-nguyên-tắc-equality-trước-range-sau)
    - [8. Các trường hợp không nên hoặc hạn chế sử dụng Index](#8-các-trường-hợp-không-nên-hoặc-hạn-chế-sử-dụng-index)
      - [8.1. Bảng quá nhỏ](#81-bảng-quá-nhỏ)
      - [8.2. Cột có quá ít giá trị khác nhau](#82-cột-có-quá-ít-giá-trị-khác-nhau)
      - [8.3. Cột hiếm khi được tìm kiếm](#83-cột-hiếm-khi-được-tìm-kiếm)
      - [8.4. Bảng có nhiều thao tác INSERT, UPDATE, DELETE](#84-bảng-có-nhiều-thao-tác-insert-update-delete)
      - [8.5. Tạo quá nhiều Index](#85-tạo-quá-nhiều-index)
  - [III. Khái niệm Transaction, ACID, dirty read, dirty write](#iii-khái-niệm-transaction-acid-dirty-read-dirty-write)
    - [1. Khái niệm Transaction](#1-khái-niệm-transaction)
    - [2. Các lệnh điều khiển Transaction](#2-các-lệnh-điều-khiển-transaction)
    - [3. ACID](#3-acid)
      - [3.1. Atomicity – Tính nguyên tử](#31-atomicity--tính-nguyên-tử)
      - [3.2. Consistency – Tính nhất quán](#32-consistency--tính-nhất-quán)
      - [3.3. Isolation – Tính cô lập](#33-isolation--tính-cô-lập)
      - [3.4. Durability – Tính bền vững](#34-durability--tính-bền-vững)
    - [4. Dirty Read](#4-dirty-read)
    - [5. Dirty Write](#5-dirty-write)


## I. Tối ưu truy vấn
Các câu lệnh SQL được viết không tốt có thể làm giảm tốc độ của cơ sở dữ liệu, tiêu tốn nhiều tài nguyên, gây ra các vấn đề về khóa dữ liệu và ảnh hưởng tiêu cực đến trải nghiệm của người dùng.  

Việc tuân thủ các nguyên tắc thực hành tốt khi viết câu lệnh SQL hiệu quả giúp cải thiện hiệu năng của cơ sở dữ liệu và đảm bảo sử dụng tài nguyên hệ thống một cách tối ưu.
- Giảm thời gian thực thi truy vấn và cải thiện hiệu năng tổng thể.
- Giảm mức tiêu thụ tài nguyên, đồng thời hạn chế các vấn đề liên quan đến khóa và chặn dữ liệu.

### 1. Đọc kế hoạch thực thi (Execution Plan)

**Execution Plan*- là kế hoạch mà hệ quản trị cơ sở dữ liệu sử dụng để thực hiện một câu lệnh SQL.

Đọc Execution Plan giúp chúng ta biết:

- Bảng nào được truy cập.
- Có sử dụng Index hay không.
- Có phải quét toàn bộ bảng (`Full Table Scan`) hay không.
- Ước tính số dòng cần xử lý.
- Từ đó tìm ra nguyên nhân khiến truy vấn chậm và tối ưu câu SQL.

#### 1.1 Sử dụng EXPLAIN

`EXPLAIN` cho biết **MySQL dự định thực hiện câu SQL như thế nào*- mà không thực sự chạy câu truy vấn.

Ví dụ: Tìm tất cả đơn hàng của khách hàng có `customer_id = 101`.

Table orders:

| order_id | customer_id | order_date | total_amount |
|---------:|------------:|------------|-------------:|
| 1 | 101 | 2026-09-01 | 250000 |
| 2 | 102 | 2026-09-01 | 180000 |
| 3 | 103 | 2026-09-02 | 320000 |
| 4 | 101 | 2026-09-02 | 150000 |
| 5 | 104 | 2026-09-03 | 450000 |
| 6 | 102 | 2026-09-03 | 200000 |
| 7 | 105 | 2026-09-04 | 275000 |
| 8 | 101 | 2026-09-04 | 120000 |


```sql
SELECT *
FROM orders
WHERE customer_id = 101;
```

Output:  
| order_id | customer_id | order_date | total_amount |
|---------:|------------:|------------|-------------:|
| 1 | 101 | 2026-09-01 | 250000 |
| 4 | 101 | 2026-09-02 | 150000 |
| 8 | 101 | 2026-09-04 | 120000 |  

Sử dụng EXPLAIN để xem kế hoạch thực thi:  
```sql
EXPLAIN
SELECT *
FROM orders
WHERE customer_id = 101;
```  

Output:  
| id | select_type | table | type | possible_keys | key | rows | Extra |
|---:|-------------|-------|------|---------------|-----|-----:|-------|
| 1 | SIMPLE | orders | ALL | NULL | NULL | 8 | Using where |  

type = ALL cho thấy MySQL đang quét toàn bộ bảng. key = NULL cho thấy chưa có Index được sử dụng.  

#### 1.2 Sử dụng EXPLAIN ANALYZE
EXPLAIN ANALYZE thực sự chạy câu SQL và cung cấp thêm thông tin về quá trình thực thi, chẳng hạn như thời gian thực tế và số dòng thực tế được xử lý.

Ví dụ: Tiếp tục sử dụng bảng orders ở trên.  
Table orders:  
| order_id | customer_id | order_date | total_amount |
|---------:|------------:|------------|-------------:|
| 1 | 101 | 2026-09-01 | 250000 |
| 2 | 102 | 2026-09-01 | 180000 |
| 3 | 103 | 2026-09-02 | 320000 |
| 4 | 101 | 2026-09-02 | 150000 |
| 5 | 104 | 2026-09-03 | 450000 |
| 6 | 102 | 2026-09-03 | 200000 |
| 7 | 105 | 2026-09-04 | 275000 |
| 8 | 101 | 2026-09-04 | 120000 |  

```sql
SELECT *
FROM orders
WHERE customer_id = 101;
```  

Output:  
| order_id | customer_id | order_date | total_amount |
|---------:|------------:|------------|-------------:|
| 1 | 101 | 2026-09-01 | 250000 |
| 4 | 101 | 2026-09-02 | 150000 |
| 8 | 101 | 2026-09-04 | 120000 |  

Sử dụng EXPLAIN ANALYZE:  
```sql
EXPLAIN ANALYZE
SELECT *
FROM orders
WHERE customer_id = 101;
```  

Output:  
| Operation | Estimated Rows | Actual Rows | Actual Time | Loops |
|-----------|---------------:|------------:|-------------|------:|
| Table scan on orders | 8 | 8 | 0.020–0.028 ms | 1 |
| Filter: customer_id = 101 | 3 | 3 | 0.030–0.035 ms | 1 |   

-> Quét 8 dòng và lọc ra 3 dòng thỏa mãn điều kiện customer_id = 101.  

### 2. Các bước tối ưu một truy vấn  
Quy trình tối ưu truy vấn có thể thực hiện theo các bước:
- Xác định truy vấn cần tối ưu
- Kiểm tra Execution Plan
- Xác định vấn đề
- Ap dụng phương pháp tối ưu và kiểm tra lại hiệu năng sau khi tối ưu.

### 3. Các kỹ thuật tối ưu truy vấn thường gặp
#### 3.1 Chỉ định cột thay vì SELECT *
Khi chỉ cần một số cột, nên chỉ định rõ các cột cần lấy thay vì sử dụng SELECT *. Điều này giúp giảm lượng dữ liệu phải đọc và truyền.  

#### 3.2 Tận dụng chỉ số (Index)
Index giúp MySQL tìm dữ liệu nhanh hơn thay vì phải quét toàn bộ bảng.

**Table Input**

| order_id | customer_id | order_date | total_amount |
|---------:|------------:|------------|-------------:|
|        1 |         101 | 2026-09-01 |       250000 |
|        2 |         102 | 2026-09-01 |       180000 |
|        3 |         103 | 2026-09-02 |       320000 |
|        4 |         101 | 2026-09-02 |       150000 |
|        5 |         104 | 2026-09-03 |       450000 |
|        6 |         102 | 2026-09-03 |       200000 |
|        7 |         105 | 2026-09-04 |       275000 |
|        8 |         101 | 2026-09-04 |       120000 |

**Code SQL**
```sql
SELECT - FROM orders WHERE customer_id = 101;
```

**Table Output**

| order_id | customer_id | order_date | total_amount |
|---------:|------------:|------------|-------------:|
|        1 |         101 | 2026-09-01 |       250000 |
|        4 |         101 | 2026-09-02 |       150000 |
|        8 |         101 | 2026-09-04 |       120000 |

Tạo Index trên `customer_id`:
```sql
CREATE INDEX idx_orders_customer_id ON orders(customer_id);
```

Kiểm tra Execution Plan:
```sql
EXPLAIN SELECT - FROM orders WHERE customer_id = 101;
```

**Table Output**

| id | table  | type | possible_keys          | key                    | rows |
|---:|--------|------|------------------------|------------------------|-----:|
|  1 | orders | ref  | idx_orders_customer_id | idx_orders_customer_id |    3 |  

#### 3.3 Tránh dùng hàm trong mệnh đề WHERE
Việc áp dụng hàm trực tiếp lên cột trong `WHERE` có thể khiến Index trên cột đó không được tận dụng hiệu quả.

**Table Input**

| order_id | order_date | customer_id | total_amount |
|---------:|------------|------------:|-------------:|
|        1 | 2026-09-01 |         101 |       250000 |
|        2 | 2026-09-02 |         102 |       180000 |
|        3 | 2026-09-03 |         103 |       320000 |
|        4 | 2026-09-04 |         101 |       150000 |
|        5 | 2026-09-05 |         104 |       450000 |

**Code SQL**
```sql
SELECT - FROM orders WHERE YEAR(order_date) = 2026;
```

**Table Output**

| order_id | order_date | customer_id | total_amount |
|---------:|------------|------------:|-------------:|
|        1 | 2026-09-01 |         101 |       250000 |
|        2 | 2026-09-02 |         102 |       180000 |
|        3 | 2026-09-03 |         103 |       320000 |
|        4 | 2026-09-04 |         101 |       150000 |
|        5 | 2026-09-05 |         104 |       450000 |

Có thể viết lại điều kiện để tránh áp dụng hàm lên cột:

```sql
SELECT - FROM orders 
WHERE order_date >= '2026-01-01' 
  AND order_date < '2027-01-01';
```

**Table Output**

| order_id | order_date | customer_id | total_amount |
|---------:|------------|------------:|-------------:|
|        1 | 2026-09-01 |         101 |       250000 |
|        2 | 2026-09-02 |         102 |       180000 |
|        3 | 2026-09-03 |         103 |       320000 |
|        4 | 2026-09-04 |         101 |       150000 |
|        5 | 2026-09-05 |         104 |       450000 |


#### 3.4 Rút gọn truy vấn con (Un-nest sub-query)
Trong một số trường hợp, có thể thay thế subquery bằng JOIN để truy vấn dễ tối ưu hơn.

**Table Input**

*customers*

| customer_id | customer_name |
|------------:|---------------|
|         101 | Nguyễn An     |
|         102 | Trần Bình     |
|         103 | Lê Chi        |

*orders*

| order_id | customer_id | total_amount |
|---------:|------------:|-------------:|
|        1 |         101 |       250000 |
|        2 |         102 |       180000 |
|        3 |         103 |       320000 |
|        4 |         101 |       150000 |

**Code SQL**
```sql
SELECT customer_id, customer_name 
FROM customers 
WHERE customer_id IN (
    SELECT customer_id
    FROM orders
    WHERE total_amount > 300000
);
```

**Table Output**

| customer_id | customer_name |
|------------:|---------------|
|         103 | Lê Chi        |

Có thể viết lại bằng JOIN:

```sql
SELECT DISTINCT c.customer_id, c.customer_name 
FROM customers c 
JOIN orders o ON c.customer_id = o.customer_id 
WHERE o.total_amount > 300000;
```

**Table Output**

| customer_id | customer_name |
|------------:|---------------|
|         103 | Lê Chi        |


#### 3.5 Hạn chế DISTINCT và HAVING khi không cần thiết
`DISTINCT` và `HAVING` có thể yêu cầu MySQL thực hiện thêm các bước xử lý dữ liệu. Nếu điều kiện có thể xử lý bằng `WHERE` hoặc dữ liệu vốn đã duy nhất, nên tránh sử dụng chúng không cần thiết.

**Table Input**

| order_id | customer_id | total_amount |
|---------:|------------:|-------------:|
|        1 |         101 |       250000 |
|        2 |         102 |       180000 |
|        3 |         103 |       320000 |
|        4 |         101 |       150000 |
|        5 |         104 |       450000 |

Ví dụ không cần `DISTINCT` vì `order_id` đã là khóa chính và luôn duy nhất.

**Code SQL**
```sql
SELECT DISTINCT order_id, customer_id, total_amount 
FROM orders 
WHERE total_amount > 200000;
```

**Table Output**

| order_id | customer_id | total_amount |
|---------:|------------:|-------------:|
|        1 |         101 |       250000 |
|        3 |         103 |       320000 |
|        4 |         101 |       150000 |
|        5 |         104 |       450000 |

Có thể bỏ `DISTINCT`:

```sql
SELECT order_id, customer_id, total_amount 
FROM orders 
WHERE total_amount > 200000;
```

**Table Output**

| order_id | customer_id | total_amount |
|---------:|------------:|-------------:|
|        1 |         101 |       250000 |
|        3 |         103 |       320000 |
|        5 |         104 |       450000 |


#### 3.6 Xử lý mệnh đề OR phức tạp
Các điều kiện `OR` phức tạp đôi khi khiến MySQL khó tận dụng Index. Trong một số trường hợp, có thể tách thành nhiều truy vấn bằng `UNION ALL`.

**Table Input**

| product_id | product_name | category    | price    |
|-----------:|--------------|-------------|---------:|
|          1 | Laptop       | Electronics | 15000000 |
|          2 | Mouse        | Electronics |   300000 |
|          3 | Keyboard     | Electronics |   800000 |
|          4 | Desk         | Furniture   |  2500000 |
|          5 | Chair        | Furniture   |  1500000 |

**Code SQL**
```sql
SELECT - FROM products 
WHERE category = 'Electronics'
   OR price > 2000000;
```

**Table Output**

| product_id | product_name | category    | price    |
|-----------:|--------------|-------------|---------:|
|          1 | Laptop       | Electronics | 15000000 |
|          2 | Mouse        | Electronics |   300000 |
|          3 | Keyboard     | Electronics |   800000 |
|          4 | Desk         | Furniture   |  2500000 |

Có thể tách điều kiện thành các truy vấn riêng:

```sql
SELECT - FROM products 
WHERE category = 'Electronics'
UNION
SELECT - FROM products 
WHERE price > 2000000;
```

**Table Output**

| product_id | product_name | category    | price    |
|-----------:|--------------|-------------|---------:|
|          1 | Laptop       | Electronics | 15000000 |
|          2 | Mouse        | Electronics |   300000 |
|          3 | Keyboard     | Electronics |   800000 |
|          4 | Desk         | Furniture   |  2500000 |


#### 3.7 Ưu tiên UNION ALL thay cho UNION
`UNION` loại bỏ các dòng trùng nhau nên MySQL phải thực hiện thêm bước kiểm tra. Nếu chắc chắn các kết quả không bị trùng hoặc không cần loại bỏ dòng trùng, nên sử dụng `UNION ALL`.

**Table Input**

| product_id | product_name | category    |
|-----------:|--------------|-------------|
|          1 | Laptop       | Electronics |
|          2 | Mouse        | Electronics |
|          3 | Desk         | Furniture   |
|          4 | Chair        | Furniture   |

**Code SQL**
```sql
SELECT product_id, product_name 
FROM products 
WHERE category = 'Electronics'
UNION
SELECT product_id, product_name 
FROM products 
WHERE product_id > 2;
```

**Table Output**

| product_id | product_name |
|-----------:|--------------|
|          1 | Laptop       |
|          2 | Mouse        |
|          3 | Desk         |
|          4 | Chair        |

Nếu hai tập kết quả chắc chắn không có dòng trùng nhau, có thể sử dụng:

```sql
SELECT product_id, product_name 
FROM products 
WHERE category = 'Electronics'
UNION ALL
SELECT product_id, product_name 
FROM products 
WHERE product_id > 2;
```

**Table Output**

| product_id | product_name |
|-----------:|--------------|
|          1 | Laptop       |
|          2 | Mouse        |
|          3 | Desk         |
|          4 | Chair        |

`UNION ALL` không thực hiện bước loại bỏ dòng trùng, vì vậy thường nhanh hơn `UNION` khi việc loại bỏ dữ liệu trùng là không cần thiết.



## II. Index
### 1. Khái niệm Index
Index là một cấu trúc dữ liệu được tạo trên một hoặc nhiều cột của bảng, giúp Database tìm kiếm dữ liệu nhanh hơn mà không cần quét toàn bộ bảng.
Có thể hình dung Index giống như mục lục của một cuốn sách. Thay vì đọc toàn bộ cuốn sách để tìm một nội dung, ta tra mục lục để xác định nhanh vị trí cần tìm.
Trong MySQL, Index thường được sử dụng để tối ưu các truy vấn có điều kiện `WHERE`, `JOIN`, `ORDER BY` và một số trường hợp `GROUP BY`.

Ví dụ, ta có bảng `customers`:

**Table Input**

| customer_id | customer_name | city      |
|-------------|---------------|-----------|
| 101         | Nguyễn An     | Hà Nội    |
| 102         | Trần Bình     | Hải Phòng |
| 103         | Lê Chi        | Đà Nẵng   |
| 104         | Phạm Dũng     | Hà Nội    |
| 105         | Hoàng Lan     | TP.HCM    |

Nếu thường xuyên tìm khách hàng theo `customer_id`, ta có thể tạo Index:

```sql
CREATE INDEX idx_customers_customer_id
ON customers(customer_id);
```

Sau đó truy vấn:

```sql
SELECT *
FROM customers
WHERE customer_id = 104;
```

**Table Output**

| customer_id | customer_name | city   |
|-------------|---------------|--------|
| 104         | Phạm Dũng     | Hà Nội |

Index giúp Database có thêm một cấu trúc để tìm kiếm dữ liệu hiệu quả hơn. Tuy nhiên, Index cũng chiếm bộ nhớ và làm tăng chi phí khi thêm, sửa hoặc xóa dữ liệu.

### 2. Khi nào nên tạo Index?
Không phải cột nào cũng cần tạo Index. Nên cân nhắc Index cho những cột thường xuyên xuất hiện trong các truy vấn.

#### 2.1. Cột thường xuyên sử dụng trong WHERE
Ví dụ thường xuyên tìm đơn hàng theo khách hàng:

**Table Input**

| order_id | customer_id | order_date | total_amount |
|----------|-------------|------------|--------------|
| 1        | 101         | 2026-09-01 | 250000       |
| 2        | 102         | 2026-09-01 | 180000       |
| 3        | 103         | 2026-09-02 | 320000       |
| 4        | 101         | 2026-09-02 | 150000       |
| 5        | 104         | 2026-09-03 | 450000       |

**SQL**
```sql
SELECT *
FROM orders
WHERE customer_id = 101;
```

**Table Output**

| order_id | customer_id | order_date | total_amount |
|----------|-------------|------------|--------------|
| 1        | 101         | 2026-09-01 | 250000       |
| 4        | 101         | 2026-09-02 | 150000       |

Nếu truy vấn này được thực hiện thường xuyên, có thể tạo:
```sql
CREATE INDEX idx_orders_customer_id
ON orders(customer_id);
```

#### 2.2. Cột thường xuyên sử dụng trong JOIN
Ví dụ:
```sql
SELECT o.order_id, c.customer_name
FROM orders o
JOIN customers c
ON o.customer_id = c.customer_id;
```
Các cột được sử dụng để liên kết bảng, đặc biệt là các cột khóa ngoại, thường là ứng viên tốt để tạo Index.

#### 2.3. Cột thường xuyên sử dụng trong ORDER BY
Ví dụ:
```sql
SELECT *
FROM orders
ORDER BY order_date;
```
Nếu bảng lớn và thường xuyên sắp xếp theo `order_date`, có thể cân nhắc Index trên cột này.

#### 2.4. Cột thường xuyên được tìm kiếm
Ví dụ tìm khách hàng bằng email:
```sql
SELECT *
FROM customers
WHERE email = 'an@gmail.com';
```
Nếu email phải là duy nhất, có thể sử dụng Unique Index.

### 3. Thao tác với Index

#### 3.1. Tạo Index trên một cột
Cú pháp:
```sql
CREATE INDEX index_name
ON table_name(column_name);
```

Ví dụ:
```sql
CREATE INDEX idx_customers_city
ON customers(city);
```

Có thể kiểm tra các Index của bảng bằng:
```sql
SHOW INDEX FROM customers;
```

**Table Output**

| Table     | Key_name           | Column_name | Non_unique |
|-----------|--------------------|-------------|------------|
| customers | PRIMARY            | customer_id | 0          |
| customers | idx_customers_city | city        | 1          |

- `Non_unique = 0` nghĩa là Index không cho phép trùng giá trị, thường gặp với PRIMARY KEY.
- `Non_unique = 1` nghĩa là Index cho phép các giá trị trùng nhau.

#### 3.2. Tạo Unique Index
Unique Index là Index yêu cầu giá trị trong cột được Index không được trùng nhau.

Ví dụ bảng khách hàng:

**Table Input**

| customer_id | customer_name | email          |
|-------------|---------------|----------------|
| 101         | Nguyễn An     | an@gmail.com   |
| 102         | Trần Bình     | binh@gmail.com |
| 103         | Lê Chi        | chi@gmail.com  |

Tạo Unique Index:
```sql
CREATE UNIQUE INDEX idx_customers_email
ON customers(email);
```

Sau đó thử thêm một khách hàng có email đã tồn tại:
```sql
INSERT INTO customers(customer_id, customer_name, email)
VALUES (104, 'Phạm Dũng', 'an@gmail.com');
```

**Table Output**

| customer_id | customer_name | email          |
|-------------|---------------|----------------|
| 101         | Nguyễn An     | an@gmail.com   |
| 102         | Trần Bình     | binh@gmail.com |
| 103         | Lê Chi        | chi@gmail.com  |

Câu lệnh INSERT bị từ chối vì `an@gmail.com` đã tồn tại.
*Lưu ý*: Unique Index có hai vai trò: hỗ trợ truy vấn và quan trọng hơn là đảm bảo tính duy nhất của dữ liệu.

#### 3.3. Xóa Index
Cú pháp:
```sql
DROP INDEX index_name
ON table_name;
```

Ví dụ:
```sql
DROP INDEX idx_customers_city
ON customers;
```

Kiểm tra lại:
```sql
SHOW INDEX FROM customers;
```

**Table Output**

| Table     | Key_name | Column_name | Non_unique |
|-----------|----------|-------------|------------|
| customers | PRIMARY  | customer_id | 0          |

### 4. Các kiểu Index thường gặp

#### 4.1. Single-column Index
Index được tạo trên một cột.
```sql
CREATE INDEX idx_orders_customer
ON orders(customer_id);
```
Ví dụ phù hợp khi truy vấn thường xuyên:
```sql
SELECT *
FROM orders
WHERE customer_id = 101;
```

#### 4.2. Unique Index
Index đảm bảo giá trị không bị trùng.
```sql
CREATE UNIQUE INDEX idx_customers_email
ON customers(email);
```
Thường được sử dụng cho các dữ liệu như:
- Email
- Mã tài khoản
- Username
- Mã sản phẩm
nếu các giá trị này phải duy nhất.

#### 4.3. Composite Index
Composite Index là Index được tạo trên nhiều cột.

Ví dụ:
```sql
CREATE INDEX idx_orders_customer_date
ON orders(customer_id, order_date);
```
Index có thứ tự: `customer_id` -> `order_date`

Giả sử:

**Table Input**

| order_id | customer_id | order_date | total_amount |
|----------|-------------|------------|--------------|
| 1        | 101         | 2026-09-01 | 250000       |
| 2        | 101         | 2026-09-02 | 150000       |
| 3        | 102         | 2026-09-01 | 180000       |
| 4        | 102         | 2026-09-03 | 200000       |
| 5        | 103         | 2026-09-02 | 320000       |

**SQL**
```sql
SELECT *
FROM orders
WHERE customer_id = 101
AND order_date = '2026-09-02';
```

**Table Output**

| order_id | customer_id | order_date | total_amount |
|----------|-------------|------------|--------------|
| 2        | 101         | 2026-09-02 | 150000       |

Composite Index đặc biệt hữu ích khi các truy vấn thường xuyên lọc theo nhiều cột cùng lúc.

### 5. Quy tắc tiền tố trái - Left-prefix Rule
Với Composite Index:
```sql
CREATE INDEX idx_orders_customer_date
ON orders(customer_id, order_date);
```
ta có thứ tự: `customer_id` -> `order_date`

Quy tắc Left-prefix nói rằng Composite Index được tận dụng dựa trên các cột bắt đầu từ bên trái của Index.

Ví dụ với Index: `INDEX(A, B, C)`
các trường hợp:
- A
- A + B
- A + B + C
có thể tận dụng Index theo thứ tự tiền tố trái.

Trong khi đó:
- B
- C
- B + C
không tận dụng được toàn bộ tiền tố của Index.

Ví dụ:
```sql
CREATE INDEX idx_orders_customer_date
ON orders(customer_id, order_date);
```
Truy vấn:
```sql
SELECT *
FROM orders
WHERE customer_id = 101;
```
-> Có thể tận dụng Index.

Truy vấn:
```sql
SELECT *
FROM orders
WHERE order_date = '2026-09-02';
```
-> Không tận dụng được tiền tố trái `customer_id` theo cách hiệu quả như truy vấn trên.

*Ghi nhớ*: Với Composite Index (A, B, C), thứ tự cột rất quan trọng. Không nên chỉ tạo Index rồi bỏ qua thứ tự các cột.

### 6. Chiến lược chọn thứ tự cột trong Composite Index
Khi tạo Composite Index, cần dựa trên mẫu truy vấn thực tế.

Ví dụ truy vấn thường xuyên:
```sql
SELECT *
FROM orders
WHERE customer_id = 101
AND order_date >= '2026-09-01';
```
Ở đây:
- `customer_id = 101` -> **Equality**
- `order_date >= '2026-09-01'` -> **Range**

Có thể tạo:
```sql
CREATE INDEX idx_orders_customer_date
ON orders(customer_id, order_date);
```
Thay vì:
```sql
CREATE INDEX idx_orders_date_customer
ON orders(order_date, customer_id);
```

Tuy nhiên, đây là một nguyên tắc định hướng, không phải quy tắc tuyệt đối. Với truy vấn thực tế nên kiểm tra bằng `EXPLAIN` để xem MySQL thực sự sử dụng Index như thế nào.

### 7. Nguyên tắc "Equality trước, Range sau"
Một nguyên tắc phổ biến khi thiết kế Composite Index là: **Equality trước, Range sau.**

Trong đó:
- **Equality**: `=`
- **Range**: `>`, `<`, `>=`, `<=`, `BETWEEN`

Ví dụ:

**Table Input**

| order_id | customer_id | order_date | total_amount |
|----------|-------------|------------|--------------|
| 1        | 101         | 2026-08-30 | 200000       |
| 2        | 101         | 2026-09-01 | 250000       |
| 3        | 101         | 2026-09-03 | 300000       |
| 4        | 102         | 2026-09-02 | 180000       |
| 5        | 103         | 2026-09-04 | 450000       |

**SQL**
```sql
SELECT *
FROM orders
WHERE customer_id = 101
AND order_date >= '2026-09-01';
```

**Table Output**

| order_id | customer_id | order_date | total_amount |
|----------|-------------|------------|--------------|
| 2        | 101         | 2026-09-01 | 250000       |
| 3        | 101         | 2026-09-03 | 300000       |

Composite Index phù hợp:
```sql
CREATE INDEX idx_orders_customer_date
ON orders(customer_id, order_date);
```
Ở đây:
- `customer_id = 101` ↓ (Equality)
- `order_date >= '2026-09-01'` ↓ (Range)

Do đó đặt `customer_id` trước `order_date` thường là lựa chọn hợp lý.

### 8. Các trường hợp không nên hoặc hạn chế sử dụng Index
Index không phải lúc nào cũng giúp truy vấn nhanh hơn. Một số trường hợp cần hạn chế sử dụng Index:

#### 8.1. Bảng quá nhỏ
Nếu bảng chỉ có vài dòng, việc quét toàn bộ bảng có thể đã rất nhanh.
Ví dụ:

**Table Input**

| employee_id | employee_name | gender |
|-------------|---------------|--------|
| 1           | An            | Male   |
| 2           | Bình          | Male   |
| 3           | Chi           | Female |
| 4           | Dũng          | Male   |
| 5           | Lan           | Female |

Với chỉ 5 dòng dữ liệu, tạo Index trên `gender` thường không mang lại nhiều lợi ích.

#### 8.2. Cột có quá ít giá trị khác nhau
Ví dụ: `gender = Male / Female`
Cột `gender` chỉ có một số ít giá trị khác nhau.
Nếu truy vấn:
```sql
SELECT *
FROM employees
WHERE gender = 'Male';
```
và phần lớn bảng đều là Male, Index có thể không giúp nhiều vì Database vẫn phải lấy ra rất nhiều dòng.

#### 8.3. Cột hiếm khi được tìm kiếm
Nếu một cột gần như không xuất hiện trong:
- `WHERE`
- `JOIN`
- `ORDER BY`

thì thường không cần tạo Index cho cột đó.

#### 8.4. Bảng có nhiều thao tác INSERT, UPDATE, DELETE
Index giúp đọc dữ liệu nhanh hơn nhưng mỗi khi dữ liệu thay đổi, Database cũng phải cập nhật các Index liên quan.
Do đó, quá nhiều Index có thể làm giảm hiệu năng của các thao tác ghi.

#### 8.5. Tạo quá nhiều Index
Không nên tạo Index cho tất cả các cột.
Ví dụ:
```
customers
├── customer_id
├── customer_name
├── email
├── phone
├── address
└── city
```
Không có nghĩa là tất cả các cột trên đều cần Index.
Quá nhiều Index sẽ:
- Tốn thêm dung lượng.
- Làm `INSERT`, `UPDATE`, `DELETE` chậm hơn.
- Tăng chi phí cập nhật dữ liệu.
- Làm việc quản lý và tối ưu Database phức tạp hơn.

## III. Khái niệm Transaction, ACID, dirty read, dirty write
### 1. Khái niệm Transaction
Transaction (giao dịch) là một tập hợp một hoặc nhiều thao tác trên Database được xem như một đơn vị công việc hoàn chỉnh.
Transaction phải đảm bảo rằng:
- Nếu thực hiện thành công -> tất cả thay đổi được lưu.
- Nếu xảy ra lỗi -> các thay đổi có thể được hoàn tác về trạng thái ban đầu.

Ví dụ, khi khách hàng chuyển 1.000.000 VNĐ từ tài khoản A sang tài khoản B, hệ thống phải thực hiện hai thao tác:
1. Trừ 1.000.000 VNĐ khỏi tài khoản A.
2. Cộng 1.000.000 VNĐ vào tài khoản B.

Hai thao tác này phải được thực hiện như một Transaction.
Nếu trừ tiền ở A thành công nhưng cộng tiền vào B thất bại thì không được để Database ở trạng thái tiền đã bị mất.

### 2. Các lệnh điều khiển Transaction
Trong MySQL thường sử dụng:
- `START TRANSACTION` hoặc `BEGIN` để bắt đầu Transaction.
- `COMMIT` để xác nhận và lưu toàn bộ thay đổi.
- `ROLLBACK` để hủy các thay đổi kể từ khi Transaction bắt đầu.

Ví dụ:

**Table Input**

| account_id | account_name | balance |
|------------|--------------|---------|
| 1          | Nguyễn An    | 5000000 |
| 2          | Trần Bình    | 3000000 |

**SQL**
```sql
START TRANSACTION;

UPDATE accounts
SET balance = balance - 1000000
WHERE account_id = 1;

UPDATE accounts
SET balance = balance + 1000000
WHERE account_id = 2;

COMMIT;
```

**Table Output**

| account_id | account_name | balance |
|------------|--------------|---------|
| 1          | Nguyễn An    | 4000000 |
| 2          | Trần Bình    | 4000000 |

Nếu xảy ra lỗi trước `COMMIT`, ta có thể sử dụng:
```sql
ROLLBACK;
```
Khi đó các thay đổi trong Transaction sẽ được hoàn tác.

### 3. ACID
ACID là bốn thuộc tính quan trọng giúp đảm bảo Transaction hoạt động an toàn và đáng tin cậy.
ACID gồm:
- **A – Atomicity**: Tính nguyên tử
- **C – Consistency**: Tính nhất quán
- **I – Isolation**: Tính cô lập
- **D – Durability**: Tính bền vững

#### 3.1. Atomicity – Tính nguyên tử
Atomicity đảm bảo Transaction được thực hiện theo nguyên tắc:
Hoặc tất cả thao tác thành công, hoặc không thao tác nào được ghi nhận.

Ví dụ chuyển tiền:
```sql
START TRANSACTION;

UPDATE accounts
SET balance = balance - 1000000
WHERE account_id = 1;

UPDATE accounts
SET balance = balance + 1000000
WHERE account_id = 2;

COMMIT;
```
Nếu câu lệnh thứ hai bị lỗi, ta có thể:
```sql
ROLLBACK;
```
-> Số tiền đã trừ ở tài khoản A cũng được hoàn tác.

#### 3.2. Consistency – Tính nhất quán
Consistency đảm bảo Database luôn chuyển từ một trạng thái hợp lệ sang một trạng thái hợp lệ khác.

Ví dụ, hệ thống quy định:
`balance >= 0`

Nếu tài khoản có 500.000 VNĐ thì không thể thực hiện giao dịch làm số dư trở thành -500.000.
Database có thể sử dụng các ràng buộc như:
- `PRIMARY KEY`
- `FOREIGN KEY`
- `UNIQUE`
- `NOT NULL`
- `CHECK`

để giúp duy trì tính nhất quán của dữ liệu.

#### 3.3. Isolation – Tính cô lập
Isolation đảm bảo các Transaction chạy đồng thời không gây ra những kết quả không hợp lệ do nhìn thấy dữ liệu trung gian của nhau.

Ví dụ có hai Transaction:
- **Transaction A**
- `UPDATE` dữ liệu
- **Transaction B**
- **Transaction A**

Nếu Transaction B đọc được dữ liệu mà A vừa thay đổi nhưng chưa `COMMIT`, B có thể nhìn thấy dữ liệu chưa chính thức được xác nhận.
Đây chính là một ví dụ của Dirty Read.

#### 3.4. Durability – Tính bền vững
Durability đảm bảo rằng sau khi Transaction đã `COMMIT`, thay đổi sẽ được lưu bền vững trong Database và không bị mất chỉ vì hệ thống gặp sự cố thông thường.

Ví dụ:
```sql
START TRANSACTION;

UPDATE accounts
SET balance = balance - 1000000
WHERE account_id = 1;

COMMIT;
```
Sau khi `COMMIT` thành công, số dư mới được xem là đã được xác nhận và lưu lại.

### 4. Dirty Read
Dirty Read xảy ra khi một Transaction đọc dữ liệu mà Transaction khác đã thay đổi nhưng chưa `COMMIT`.
Nói đơn giản:
Transaction A sửa dữ liệu -> chưa `COMMIT` -> Transaction B đọc dữ liệu đó -> A `ROLLBACK`.
Khi đó B đã đọc một dữ liệu "bẩn" - dữ liệu sau đó không còn tồn tại.

**Ví dụ**
Ban đầu:

**Table Input**

| account_id | account_name | balance |
|------------|--------------|---------|
| 1          | Nguyễn An    | 5000000 |

**Transaction A**
```sql
START TRANSACTION;

UPDATE accounts
SET balance = 3000000
WHERE account_id = 1;
```
Transaction A chưa `COMMIT`.

Trong lúc đó, **Transaction B** thực hiện:
```sql
SELECT balance
FROM accounts
WHERE account_id = 1;
```

**Table Output của Transaction B**

| balance |
|---------|
| 3000000 |

Transaction B đã đọc 3000000, nhưng đây vẫn là dữ liệu chưa được Transaction A xác nhận.
Sau đó Transaction A xảy ra lỗi:
```sql
ROLLBACK;
```

Dữ liệu quay lại:

| account_id | account_name | balance |
|------------|--------------|---------|
| 1          | Nguyễn An    | 5000000 |

Như vậy Transaction B đã đọc giá trị 3000000 nhưng giá trị này sau đó bị hủy.
-> Đây là **Dirty Read**.

### 5. Dirty Write
Dirty Write xảy ra khi một Transaction ghi đè lên dữ liệu mà Transaction khác đã thay đổi nhưng chưa `COMMIT`.
Nói đơn giản:
Transaction A sửa dữ liệu -> chưa `COMMIT` -> Transaction B tiếp tục sửa cùng dữ liệu đó.
Điều này có thể khiến các thay đổi của các Transaction ghi đè lên nhau và tạo ra trạng thái dữ liệu không mong muốn.

**Ví dụ**
Ban đầu:

**Table Input**

| product_id | product_name | price    |
|------------|--------------|----------|
| 1          | Laptop       | 20000000 |

**Transaction A:**
```sql
START TRANSACTION;

UPDATE products
SET price = 19000000
WHERE product_id = 1;
```
Lúc này Transaction A chưa `COMMIT`.

**Transaction B** tiếp tục:
```sql
START TRANSACTION;

UPDATE products
SET price = 18000000
WHERE product_id = 1;
```
Hai Transaction cùng thay đổi một dữ liệu.
Nếu hệ thống cho phép việc ghi đè này xảy ra không kiểm soát, giá trị mà Transaction A đang xử lý có thể bị Transaction B ghi đè.
-> Đây là **Dirty Write**.
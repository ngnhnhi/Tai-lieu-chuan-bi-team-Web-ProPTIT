# Buổi 1: Nhập môn CSDL

## 1. CSDL là gì?
### 1.1 Khái niệm
Cơ sở dữ liệu (Database) là một tập hợp các dữ liệu được hệ thống, lưu trữ trong hệ thống máy tính. Một CSDL thường được quản lý bởi một hệ quản trị CSDL (Database management system).

### 1.2 So sánh CSDL và bảng tính
| Tiêu chí | Hệ thống tập tin (File System) | Hệ quản trị CSDL (DBMS) |
| :--- | :--- | :--- |
| **Cấu trúc (Structure)** | Là phương thức sắp xếp và lưu trữ các tập tin trong môi trường lưu trữ của máy tính. | Là phần mềm chuyên dụng dùng để quản lý và vận hành cơ sở dữ liệu. |
| **Dư thừa dữ liệu (Data Redundancy)** | Dữ liệu có thể bị trùng lặp, dư thừa ở nhiều nơi. | Giảm thiểu tối đa hoặc không có dữ liệu dư thừa. |
| **Sao lưu & Phục hồi (Backup & Recovery)** | Không có sẵn cơ chế tự động để sao lưu và phục hồi khi xảy ra mất mát dữ liệu. | Tích hợp sẵn các công cụ mạnh mẽ để sao lưu và phục hồi dữ liệu khi có sự cố. |
| **Xử lý truy vấn (Query Processing)** | Không hỗ trợ xử lý truy vấn hiệu quả. | Hỗ trợ xử lý và tối ưu hóa truy vấn rất hiệu quả. |
| **Tính nhất quán (Consistency)** | Tính nhất quán dữ liệu thấp. | Tính nhất quán dữ liệu cao (nhờ vào quá trình chuẩn hóa - Normalization). |
| **Độ phức tạp (Complexity)** | Ít phức tạp hơn so với DBMS. | Phức tạp hơn trong việc thiết lập, quản lý và vận hành. |
| **Bảo mật (Security Constraints)** | Mức độ bảo mật kém hơn. | Cung cấp nhiều cơ chế bảo mật nâng cao và phân quyền chặt chẽ. |
| **Chi phí (Cost)** | Chi phí thấp hơn DBMS. | Chi phí triển khai và duy trì cao hơn đáng kể. |
| **Tính độc lập dữ liệu (Data Independence)** | Không có tính độc lập dữ liệu. | Hỗ trợ độc lập dữ liệu (gồm độc lập dữ liệu mức logic và mức vật lý). |
| **Truy cập đồng thời (User Access)** | Thường chỉ cho phép một người dùng truy cập tại một thời điểm. | Cho phép nhiều người dùng truy cập đồng thời cùng lúc. |
| **Cơ chế xử lý (Meaning)** | Người dùng không cần viết các thủ tục phức tạp. | Người dùng/Quản trị viên cần viết các thủ tục/câu lệnh để quản lý cơ sở dữ liệu. |
| **Chia sẻ dữ liệu (Sharing)** | Dữ liệu phân tán ở nhiều file khác nhau nên khó chia sẻ. | Dữ liệu tập trung nên việc chia sẻ diễn ra rất dễ dàng. |
| **Trừu tượng hóa dữ liệu (Data Abstraction)** | Phải nắm rõ chi tiết về nơi lưu trữ và cách biểu diễn của dữ liệu. | Ẩn đi các chi tiết kỹ thuật phức tạp bên trong cơ sở dữ liệu. |
| **Ràng buộc toàn vẹn (Integrity Constraints)** | Rất khó để áp dụng và kiểm soát các ràng buộc toàn vẹn. | Dễ dàng thiết lập và thực thi các ràng buộc toàn vẹn. |
| **Thuộc tính truy cập (Attributes)** | Cần các thông tin như tên file, đường dẫn lưu trữ để truy cập dữ liệu. | Không cần quan tâm vị trí lưu trữ vật lý, chỉ cần truy vấn theo định danh dữ liệu. |
| **Ví dụ** | COBOL, C++ (xử lý file truyền thống). | Oracle, SQL Server, MySQL, PostgreSQL. |

## 2. Hệ quản trị CSDL là gì?
### 2.1 Khái niệm
Hệ quản trị CSDL (DBMS) là một hệ thống có nhiệm vụ quản lý, lưu trữ và truy vấn dữ liệu . DBMS đóng vai trò là cầu nối giữa CSDL và người dùng.

Có một số dạng dữ liệu phổ biến như cơ sở dữ liệu quan hệ (Relational Databases), cơ sở dữ liệu phi quan hệ NoSQL, dữ liệu theo thời gian (Time Series), dữ liệu NewSQL, ….

### 2.2 Thành phần của DBMS
DBMS gồm 6 thành phần chính:
- Phần cứng (Hardware): Hệ thống lưu trữ và thiết bị mà phần mềm CSDL chạy trên đó.
- Phần mềm (Software): Hệ quản trị CSDL, hay hệ thống phép người dùng truy cập và quản lý dữ liệu.
- Dữ liệu (Data): Thông tin được lưu trữ một cách có hệ thống trong CSDL.
- Ngôn ngữ truy cập dữ liệu (Data access language): một ngôn ngữ lập trình (VD: SQL), được DBMS sử dụng để quản lý và sắp xếp dữ liệu. Người dùng cần ngôn ngữ này để viết lệnh và truy vấn CSDL.
- Thủ tục (Procedures): các quy tắc được định nghĩa mà người dùng tuân theo để truy cập và tổ chức dữ liệu.
- Giao diện truy cập (Interfaces for access) : các công cụ và phương pháp kết nối mà người dùng và hệ thống dựa vào để tương tác với CSDL

## 4. Câu lệnh tạo database, table trong MS SQL Server
### 4.1 Tạo database
Cú pháp
```sql
CREATE DATABASE ten_database;
```

Ví dụ:
```sql
CREATE DATABASE students;
```

### 4.2 Tạo table
Cú pháp
```sql
CREATE TABLE ten_bang (
    ten_cot_1 kieu_du_lieu rang_buoc,
    ten_cot_2 kieu_du_lieu rang_buoc,
    ten_cot_3 kieu_du_lieu rang_buoc,
    ...
    [Cac_rang_buoc_bang]
);

Ví dụ:
```sql
CREATE TABLE students (
    id INT PRIMARY KEY,
    fullname NVARCHAR(100) NOT NULL,
    age INT CHECK (age >= 16 AND age <= 100),
    address NVARCHAR(255),
    gpa DECIMAL(3, 2) CHECK (gpa >= 0.0 AND gpa <= 4.0)
);
```

### 4.3 Một số kiểu dữ liệu thường dùng

| Nhóm dữ liệu | Kiểu dữ liệu | Phạm vi / Quy cách | Dung lượng | Ứng dụng thực tế phổ biến |
| :--- | :--- | :--- | :--- | :--- |
| **Chuỗi ký tự (Văn bản)** | `NVARCHAR(n)` | 1 đến 4.000 ký tự (Hỗ trợ Unicode/Tiếng Việt) | 2n + 2 bytes | Họ tên, địa chỉ, tiêu đề bài viết *(khuyên dùng nhất)* |
| | `NVARCHAR(MAX)` | Tối đa 2 GB văn bản Unicode | Tùy kích thước | Nội dung bài viết dài, bài luận, mô tả chi tiết |
| | `VARCHAR(n)` | 1 đến 8.000 ký tự (Không dấu / ASCII) | n + 2 bytes | Email, URL, Username không dấu, mã Hash |
| | `CHAR(n)` | Chuỗi cố định chiều dài (1 ≤ n ≤ 8.000) | n bytes | Mã định danh cố định (Mã quốc gia: `VN`, `US`, Mã bưu chính) |
| **Số nguyên** | `BIT` | `0`, `1` hoặc `NULL` | 1 bit | Trạng thái (IsActive), Đúng/Sai (True/False), Giới tính |
| | `TINYINT` | `0` đến `255` | 1 byte | Tuổi, tháng trong năm (1–12), xếp hạng (1–5 sao) |
| | `INT` | `-2.147.483.648` đến `2.147.483.647` | 4 bytes | Khóa chính (`ID`), số lượng tồn kho, lượt xem |
| | `BIGINT` | Khoảng -9 x 10^18 đến +9 x 10^18 | 8 bytes | ID hệ thống lớn (Big Data), giao dịch tài chính lớn |
| **Số thập phân & Tiền tệ** | `DECIMAL(p, s)` | p: tổng chữ số (1–38), s: chữ số thập phân | 5–17 bytes | Giá tiền, lương, tỷ giá, điểm GPA *(độ chính xác tuyệt đối)* |
| | `FLOAT` | Số thực dấu phẩy động (xấp xỉ) | 4 hoặc 8 bytes | Tính toán khoa học, dữ liệu cảm biến *(không dùng cho tiền)* |
| **Ngày & Giờ** | `DATE` | `YYYY-MM-DD` (từ 0001-01-01 đến 9999-12-31) | 3 bytes | Ngày sinh, ngày cấp bằng/CCCD, ngày phát hành |
| | `TIME` | `hh:mm:ss[.nnnnnnn]` | 3–5 bytes | Giờ mở cửa, thời lượng làm việc |
| | `DATETIME2` | `YYYY-MM-DD hh:mm:ss[.nnnnnnn]` | 6–8 bytes | `created_at`, `updated_at`, nhật ký giao dịch (log) |


### 4.4 Một số kiểu ràng buộc

| Ràng buộc | Mục đích chính | Cho phép `NULL`? | Số lượng trong 1 bảng |
| :--- | :--- | :--- | :--- |
| `PRIMARY KEY` | Khóa chính: Định danh duy nhất từng bản ghi (dòng) | Không | Tối đa **1** khóa chính / bảng |
| `FOREIGN KEY` | Khóa ngoại: Liên kết dữ liệu tham chiếu giữa 2 bảng | Có | Không giới hạn |
| `NOT NULL` | Bắt buộc phải có dữ liệu, không được để trống | Không | Không giới hạn |
| `UNIQUE` | Đảm bảo giá trị trong cột là duy nhất, không trùng lặp | Có (tối đa 1 giá trị NULL) | Không giới hạn |
| `CHECK` | Kiểm tra giá trị nhập vào phải thỏa mãn điều kiện logic | Có | Không giới hạn |
| `DEFAULT` | Tự động gán giá trị mặc định nếu không nhập | — | Không giới hạn |
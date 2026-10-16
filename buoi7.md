# BUỔI 7: CRUD CƠ BẢN

## I. JPA, Hibernate
### 1. JPA
- JPA (Java Persistence API) là một đặc tả (specification - a collection of rules, interfaces, and guidelines) để quy định cách thực hiện 
ORM (Object - Relational Mapping). 
- Các bước áp dụng Spring Data JPA:
  - B1: Thêm thư viện (dependencies): Thêm `spring-boot-starter-data-jpa` và driver kết nối cơ sở dữ liệu (ví dụ: 
  mysql-connector-java) vào file cấu hình `pom.xml` hoặc `build.gradle`.
  - B2: Cấu hình kết nối: Khai báo thông tin kết nối cơ sở dữ liệu trong file `application.properties` hoặc `application.yml`
  - B3: Tạo Entity class: Sử dụng các chú thích (annotations) như @Entity, @Table, @Id, và @GeneratedValue để ánh xạ một lớp 
  Java thành bảng trong cơ sở dữ liệu.
  - B4: Tạo Repository interface: Viết một interface kế thừa từ JpaRepository<EntityClass, ID_Type> để tự động có sẵn các 
  thao tác CRUD cơ bản mà không cần viết câu lệnh SQL thủ công.  


- Note:
  - ORM  is a programming technique that connects object-oriented code to relational databases by turning database tables
  into classes and rows into objects.
  - JDBC (Java Database Connectivity) is a standard Java application programming interface (API) that lets Java programs 
  connect and run commands on relational databases.  

### 2. Hibernate
- Hibernate là một open-source ORM framework được sử dụng để ánh xạ các class vào tables trong CSDL. Hibernate viết sẵn 
toàn bộ mã lệnh để hiện thực hóa các chuẩn mà JPA đã định nghĩa.
- Mối quan hệ giữa ORM, JPA và Hibernate trong Java có thể hiểu theo cấp độ từ khái niệm chung, đến bản đặc tả (tiêu chuẩn), 
và đến mã nguồn thực thi cụ thể

## II. Kiến trúc 4 lớp Spring Boot

| STT | Spring Boot Architecture | Thành phần tương đương | Nhiệm vụ |
|:---:|---|---|---|
| 1 | Presentation Layer | Controller | Xử lý các HTTP request, response |
| 2 | Business Layer | Service | Xử lý logic |
| 3 | Persistence Layer | Repository | Kết nối code và database |
| 4 | Database Layer | DBMS | Lưu trữ dữ liệu |

## III. Request Flow
Trong ứng dụng Spring Boot sử dụng JPA/Hibernate, một request từ phía client thường đi qua các layer theo thứ tự:

Client → Controller → Service → Repository → Database  
Sau khi database xử lý xong, kết quả sẽ được trả ngược lại.

Nhiệm vụ của từng layer:  

| Layer | Thành phần | Vai trò trong Request Flow |
| :--- | :--- | :--- |
| **1** | Client | Gửi HTTP Request và nhận HTTP Response |
| **2** | Controller | Tiếp nhận request và gọi Service |
| **3** | Service | Xử lý business logic và gọi Repository |
| **4** | Repository | Thực hiện thao tác với database thông qua JPA |
| **5** | Database | Lưu trữ và trả về dữ liệu |

## IV. Cấu hình DataSource
Trong Spring, DataSource là một thành phần (interface javax.sql.DataSource) cung cấp kết nối đến cơ sở dữ liệu quan hệ 
(như MySQL, PostgreSQL, H2...) cho ứng dụng Java.  

Cấu hình trong Spring Boot:  
Trong tệp  `application.properties` hoặc `application.yml`, ta khai báo thông tin kết nối thông qua tiền tố `spring.datasource.*`. 

## V. Một số annotation thường dùng
Annotation trong Spring Boot và JPA được sử dụng để cung cấp thông tin cho framework về vai trò của class, cách ánh xạ dữ 
liệu và cách xử lý request hoặc transaction.  

| Annotation | Thành phần | Ví dụ trong module | Chức năng |
|---|---|---|---|
| `@RestController` | Controller | `TableController`, `ReceiptController` | Tiếp nhận HTTP request và trả HTTP response |
| `@Service` | Service | `PaymentService`, `ReceiptService` | Xử lý business logic |
| `@Repository` | Repository | `FoodRepository`, `ReceiptRepository` | Tương tác với database |
| `@Entity` | Entity | `Food`, `Receipt`, `Customer` | Đánh dấu class là JPA Entity |
| `@Table` | Entity | `@Table(name = "food")` | Xác định bảng database được ánh xạ |
| `@Id` | Entity | `Food.id`, `Receipt.id` | Xác định Primary Key |
| `@GeneratedValue` | Entity | `@GeneratedValue(...)` | Tự động sinh giá trị ID |
| `@Transactional` | Service | `confirmPayment()` | Đảm bảo nhiều thao tác database được thực hiện trong một transaction |
| `@Query` | Repository | Tìm hóa đơn đang sử dụng của bàn | Viết truy vấn tùy chỉnh |


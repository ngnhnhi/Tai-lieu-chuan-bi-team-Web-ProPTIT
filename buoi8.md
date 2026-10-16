# BUỔI 8: TỐI ƯU JPA, VALIDATION VÀ EXCEPTION HANDLING

## I. Các phương pháp tối ưu JPA
### 1. Lazy/Eager Initialization
#### 1.1 Lazy Initialization
Lazy Initialization là một kiểu thiết kế mà trong đó chỉ khởi tạo object, các phương thức tại thời điểm lần đầu tiên nó 
được s dụng.

Mục đích:  
- Khởi động chương trình nhanh hơn
- Tiết kiệm bộ nhớ.  

Enable Lazy Initialization: configure trong `application.yml`:  
```java
spring:
  main:
    lazy-initialization: true
```
hoặc `application.properties`:       
```java
spring.main.lazy-initialization=true
```

#### 1.2. Eager Initialization
Eager Initialization là kỹ thuật khởi tạo đối tượng hoặc tải tài nguyên ngay lập tức khi lớp (class), module hoặc ngữ 
cảnh ứng dụng (application context) được nạp vào bộ nhớ, thay vì đợi đến lúc thực sự cần dùng.  

| Ưu điểm (+) | Nhược điểm (-) |
| :--- | :--- |
| **Truy cập nhanh (Fast runtime access):** Đối tượng đã sẵn sàng trong bộ nhớ, không có độ trễ ở lần gọi đầu tiên. | **Tốn thời gian khởi động (Slower startup):** Làm chậm quá trình bật ứng dụng do phải tạo toàn bộ đối tượng cùng lúc. |
| **Dễ cài đặt và debug:** Code đơn giản, không cần viết logic kiểm tra null hay xử lý phức tạp. | **Lãng phí tài nguyên:** Nếu đối tượng không bao giờ được dùng đến trong suốt phiên làm việc thì bộ nhớ vẫn bị chiếm dụng. |  


### 2. Pagination (Phân trang)
Phân trang là kỹ thuật chia một tập dữ liệu lớn thành nhiều phần. Giống như việc lướt web, mỗi lần cuộn xuống sẽ thấy 10-15 
bài viết, khi cuộn tiếp thì mới load thêm.
### 3. Caching
Caching là kỹ thuật lưu trữ dữ liệu tạm thời vào bộ nhớ tốc độ cao (như RAM) để tái sử dụng nhanh cho các lần yêu cầu sau, 
thay vì phải tính toán lại hoặc truy vấn vào cơ sở dữ liệu.  

Mục đích:
- Tăng tốc độ phản hồi
- Giảm tải cho Database
- Tiết kiệm tài nguyên: Giảm bớt các tác vụ tính toán nặng hoặc các cuộc gọi API bên thứ ba (third-party) tốn kém.

Cách áp dụng:  
Bước 1: Thêm thư viện (Dependency trong pom.xml)
```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```  

Bước 2: Kích hoạt Caching (Thêm @EnableCaching vào class chính):
```java
@SpringBootApplication
@EnableCaching
public class Application { ... }
```

Bước 3: Gắn Annotation lên hàm xử lý dữ liệu (Tầng Service):  
- @Cacheable: Kiểm tra cache trước, nếu có thì lấy luôn, chưa có thì chạy hàm rồi lưu vào cache.
- @CachePut: Luôn chạy hàm và cập nhật lại dữ liệu mới vào cache (thường dùng khi Update).
- @CacheEvict: Xóa dữ liệu khỏi cache (thường dùng khi Delete).

### 4. Manual/Native query
JPA (Java Persistence API) sử dụng ORM (Object Relational Mapping) để ánh xạ trực tiếp các đối tượng Java với các bảng cơ 
sở dữ liệu. Nó hỗ trợ cả truy vấn JPQL và SQL thuần túy, cho phép truy cập dữ liệu linh hoạt trong các ứng dụng Spring Boot.

- Native query là các câu lệnh SQL dành riêng cho cơ sở dữ liệu (ví dụ: MySQL) không giống như JPQL.
- Sử dụng @Query(nativeQuery = true) để thực thi SQL thuần túy trong Spring Data JPA.
- Hữu ích khi các thao tác phức tạp hoặc dành riêng cho cơ sở dữ liệu không thể được xử lý bằng JPQL.  

Ví dụ:
```java
@Query(nativeQuery = true, value = "SELECT * FROM Student ORDER BY age") 
Optional<Student> findSortedStudentByAge();
```

### 5. Dynamic Query 
Dynamic Query là truy vấn được xây dựng hoặc thay đổi trong quá trình chương trình chạy, tùy thuộc vào điều kiện tìm kiếm của người dùng.  
Ví dụ: Trong hệ thống quản lý nhà hàng, nhân viên có thể tìm bàn theo trạng thái: bàn trống, bàn đang sử dụng hoặc tất cả bàn.
- Static Query (truy vấn tĩnh): Câu SQL cố định, luôn tìm bàn trống.
- Dynamic Query (truy vấn động): Câu SQL thay đổi tùy theo lựa chọn của nhân viên.  

Một số cách triển khai:
- JPQL với @Query: Phù hợp với truy vấn có cấu trúc tương đối cố định và tham số thay đổi.
- JPA Specification: Phù hợp khi có nhiều điều kiện tìm kiếm tùy chọn, chẳng hạn trạng thái, số bàn và sức chứa.
- Criteria API hoặc QueryDSL: Hỗ trợ xây dựng truy vấn động phức tạp bằng Java.

### 6. Phòng tránh N+1 query
#### 6.1 N+1 Query là gì?
N+1 Query là vấn đề xảy ra khi chương trình thực hiện 1 câu SQL để lấy danh sách dữ liệu, sau đó thực hiện thêm N câu SQL để lấy dữ liệu liên quan cho từng phần tử.

Ví dụ: Một nhà hàng có 100 hóa đơn, mỗi hóa đơn thuộc về một bàn. 
- 1 query: Lấy danh sách 100 hóa đơn. 
- 100 query: Lấy thông tin bàn tương ứng với từng hóa đơn.

Tổng cộng: 1 + 100 = 101 queries, thay vì chỉ cần một vài truy vấn được tối ưu.
#### 6.2 Nguyên nhân
Nguyên nhân phổ biến là cơ chế tải dữ liệu liên quan của JPA/Hibernate.
- Lazy Loading: Chỉ tải dữ liệu liên quan khi chương trình thực sự truy cập dữ liệu đó. Nếu truy cập từng phần tử, nhiều query có thể phát sinh.
- Eager Loading: Yêu cầu tải dữ liệu liên quan ngay từ đầu, nhưng không đảm bảo chỉ sử dụng một query. Hibernate vẫn có thể thực hiện nhiều truy vấn riêng.

Lưu ý: LAZY không phải lúc nào cũng gây N+1, và EAGER không tự động giải quyết được vấn đề.

Cách khắc phục: JOIN FETCH, @EntityGraph
## II. Validation
### 1. Định nghĩa
Validation là quá trình kiểm tra dữ liệu đầu vào có hợp lệ hay không trước khi xử lý hoặc lưu vào database.

Trong Spring Boot, Validation thường được sử dụng để kiểm tra dữ liệu từ request gửi lên API, giúp hạn chế dữ liệu sai và đảm bảo các quy tắc nghiệp vụ cơ bản.

Ví dụ: Khi nhân viên tạo hóa đơn, hệ thống cần kiểm tra mã bàn không được để trống và số lượng không được âm.
### 2. Các annotation pổ biến  
| Annotation | Chức năng | Ví dụ |
| :--- | :--- | :--- |
| `@NotNull` | Giá trị không được null | `Long tableId` |
| `@NotBlank` | Chuỗi không được null, rỗng hoặc chỉ chứa khoảng trắng | `String name` |
| `@NotEmpty` | Chuỗi hoặc collection không được rỗng | `List<Long> itemIds` |
| `@Size` | Giới hạn độ dài hoặc kích thước | `@Size(max = 100)` |
| `@Min` | Giá trị số nguyên không nhỏ hơn mức quy định | `@Min(1)` |
| `@Max` | Giá trị số nguyên không lớn hơn mức quy định | `@Max(100)` |
| `@Positive` | Giá trị số phải lớn hơn 0 | `BigDecimal price` |
| `@Email` | Kiểm tra định dạng email | `String email` |
| `@Pattern` | Kiểm tra chuỗi theo biểu thức chính quy | Số điện thoại |  

### 3. Cách sử dụng
Bước 1: Thêm dependency

Với Spring Boot, thêm spring-boot-starter-validation vào pom.xml  
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```  

Bước 2: Khai báo validation trong DTO  (Định nghĩa các tiêu chuẩn)
```java
public class ReceiptRequest {
    @NotNull(message = "Table ID không được để trống")
    private Long tableId;

    @NotNull(message = "Số tiền không được để trống")
    @Positive(message = "Số tiền phải lớn hơn 0")
    private BigDecimal amount;
}
```  
Bước 3: Sử dụng @Valid trong Controller  
Khi dữ liệu không hợp lệ, Spring MVC sẽ phát sinh lỗi validation trước khi gọi phương thức xử lý request. Thông thường, ứng dụng trả về HTTP 400 Bad Request nếu không có cơ chế xử lý lỗi tùy chỉnh.   

Phân bieetj Validation với Business validation:
- Validation: Kiểm tra hình thức và ràng buộc cơ bản của dữ liệu, ví dụ tableId không được để trống hoặc amount phải dương.
- Business validation: Kiểm tra dữ liệu có thỏa mãn quy tắc nghiệp vụ hay không, ví dụ bàn phải đang được sử dụng mới được thanh toán.

## III. Exception Handling  
### 1. Định nghĩa
Exception Handling (xử lý ngoại lệ) là cơ chế phát hiện và xử lý các lỗi phát sinh khi chương trình chạy, giúp ứng dụng không bị dừng đột ngột và trả về thông báo lỗi phù hợp.  
Trong Spring Boot, Exception Handling thường được sử dụng để xử lý các lỗi như:
- Không tìm thấy hóa đơn hoặc bàn (404 Not Found).
- Dữ liệu đầu vào không hợp lệ (400 Bad Request).
- Người dùng không có quyền thực hiện thao tác (403 Forbidden).  

Nếu không có Exception Handling thì sao?
Nếu không có Exception Handling, chương trình Spring Boot vẫn có thể chạy bình thường khi không xảy ra lỗi. Tuy nhiên, khi có lỗi, ứng dụng sẽ khó kiểm soát cách xử lý và phản hồi cho client hơn.  
Giả sử nhân viên tìm hóa đơn có id = 999, nhưng hóa đơn này không tồn tại trong database. Nếu không tìm thấy hóa đơn, .get() sẽ ném NoSuchElementException.

Khi không có cơ chế xử lý phù hợp, Spring Boot thường trả về lỗi HTTP 500 Internal Server Error, dù nguyên nhân thực tế là không tìm thấy dữ liệu. Response có thể chứa thông báo lỗi chung hoặc thông tin lỗi tùy cấu hình.  

| Không có Exception Handling phù hợp | Có Exception Handling |
| :--- | :--- |
| Lỗi có thể trả về HTTP status không phù hợp | Chủ động trả về status phù hợp |
| Thông báo lỗi có thể chung chung | Thông báo rõ ràng, dễ hiểu |
| Khó thống nhất response lỗi giữa các API | Có thể chuẩn hóa response lỗi |
| Khó kiểm soát cách xử lý từng loại lỗi | Có thể xử lý từng loại exception riêng |

### 2. Các thành phần phổ biến
| Annotation | Chức năng |
| :--- | :--- |
| `@ExceptionHandler` | Xử lý một hoặc nhiều loại exception cụ thể |
| `@ControllerAdvice` | Xử lý exception tập trung cho nhiều Controller |
| `@RestControllerAdvice` | Tương tự `@ControllerAdvice`, mặc định trả response dưới dạng dữ liệu |
| `ResponseEntity` | Tùy chỉnh HTTP status và nội dung response |  

### 3. Cách triển khai 
Bước 1: Tạo custom exception

Ví dụ, khi không tìm thấy hóa đơn:
```java
public class ReceiptNotFoundException
        extends RuntimeException {

    public ReceiptNotFoundException(Long id) {
        super("Không tìm thấy hóa đơn: " + id);
    }
}
```  
Bước 2: Ném exception trong Service
```java
public Receipt getReceipt(Long id) {
    return receiptRepository.findById(id)
        .orElseThrow(
            () -> new ReceiptNotFoundException(id)
        );
}
```
Nếu không tìm thấy hóa đơn, Service sẽ ném ReceiptNotFoundException thay vì trả về dữ liệu không tồn tại.  

Bước 3: Xử lý exception tập trung
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<String> handleNotFound(
            ReceiptNotFoundException ex) {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
    }
}
```  
Khi exception xảy ra, Spring sẽ chuyển đến handler phù hợp và trả về HTTP 404 Not Found cùng thông báo lỗi.
## IV. Override default fetch plan với join fetch / entity graph  
### 1. Default Fetch Plan 
Fetch Plan là cách JPA/Hibernate xác định dữ liệu nào cần được tải từ database khi truy vấn một entity.

Mặc định, các quan hệ trong entity có chiến lược tải dữ liệu như sau:
- @ManyToOne, @OneToOne: mặc định là EAGER (tải ngay).
- @OneToMany, @ManyToMany: mặc định là LAZY (tải khi cần).
### 2. Sử dụng JOIN FETCH
JOIN FETCH cho phép lấy entity chính và dữ liệu liên quan trong cùng một truy vấn JPQL, giúp hạn chế vấn đề N+1 Query.

Ví dụ: Một hóa đơn (Receipt) liên kết với một bàn (TableEntity). Khi hiển thị danh sách hóa đơn, ta muốn lấy luôn thông tin bàn.  
```java
@Query("""
    SELECT r FROM Receipt r
    JOIN FETCH r.table
""")
List<Receipt> findAllWithTable();
```  
JOIN FETCH sử dụng INNER JOIN, nên hóa đơn không có bàn liên kết sẽ bị loại khỏi kết quả. Nếu cần giữ lại các hóa đơn đó, có thể dùng LEFT JOIN FETCH.  

### 3. Sử duụng @EntityGraph
@EntityGraph cho phép khai báo những quan hệ cần tải ngay khi thực hiện một truy vấn repository, mà không cần tự viết JPQL với JOIN FETCH.  
```java
@EntityGraph(attributePaths = {"table"})
List<Receipt> findAll();
```  

| Tiêu chí | `JOIN FETCH` | `@EntityGraph` |
| :--- | :--- | :--- |
| **Cách khai báo** | Viết JPQL | Khai báo thuộc tính cần tải |
| **Kiểm soát truy vấn** | Chủ động hơn về cấu trúc JOIN | Tập trung vào fetch plan |
| **Khả năng tái sử dụng** | Gắn với truy vấn cụ thể | Có thể dùng với nhiều phương thức repository |
| **Mục đích** | Lấy entity và quan hệ liên quan | Chỉ định dữ liệu cần tải cho truy vấn |

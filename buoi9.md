# BUỔI 9: THAO TÁC TỚI DATABASE

## I. Các mối quan hệ trong JPA
### 1. Khái niệm
Trong cơ sở dữ liệu quan hệ, các bảng có thể liên kết với nhau thông qua khóa chính (Primary Key) và khóa ngoại (Foreign Key). JPA cho phép biểu diễn các mối quan hệ này giữa các Entity trong Java bằng các annotation.  
JPA hỗ trợ 4 loại quan hệ chính:  
- @OneToOne
- @OneToMany
- @ManyToOne
- @ManyToMany

### 2. Các mối quan hệ
| Annotation | Ý nghĩa | Vị trí khóa ngoại thường gặp |
| :--- | :--- | :--- |
| **`@OneToOne`** | Một-một | Một trong hai bảng |
| **`@OneToMany`** | Một-nhiều | Bảng phía nhiều |
| **`@ManyToOne`** | Nhiều-một | Bảng phía nhiều |
| **`@ManyToMany`** | Nhiều-nhiều | Bảng trung gian |  

### 3. Phía sở hữu và phía không sở hữu quan hệ

Trong quan hệ hai chiều, JPA phân biệt hai phía:

- Owning side (phía sở hữu): Quyết định việc cập nhật quan hệ trong database, thường là phía chứa @JoinColumn hoặc @JoinTable.
- Inverse side (phía không sở hữu): Sử dụng mappedBy để tham chiếu đến thuộc tính quan hệ ở phía sở hữu.  

Ví dụ: 
```java
@OneToMany(mappedBy = "receipt")
private List<ReceiptItem> items;

@ManyToOne
@JoinColumn(name = "receipt_id")
private Receipt receipt;
```  
-> @OneToMany(mappedBy = "receipt")
private List<ReceiptItem> items;

@ManyToOne
@JoinColumn(name = "receipt_id")
private Receipt receipt;

Trong ví dụ trên, ReceiptItem.receipt là phía sở hữu. Khi thêm một dòng hóa đơn vào danh sách Receipt.items, cần đồng thời thiết lập ReceiptItem.receipt để quan hệ được lưu đúng.

### 4. Cascade
Cascade quy định các thao tác trên Entity cha có được truyền sang Entity liên quan hay không.   

| Giá trị | Ý nghĩa |
| :--- | :--- |
| **`PERSIST`** | Truyền thao tác lưu mới |
| **`MERGE`** | Truyền thao tác cập nhật |
| **`REMOVE`** | Truyền thao tác xóa |
| **`REFRESH`** | Truyền thao tác làm mới |
| **`DETACH`** | Truyền thao tác tách khỏi persistence context |
| **`ALL`** | Bao gồm tất cả các thao tác trên |  

Ví dụ:
```java
@OneToMany(mappedBy = "receipt",
        cascade = CascadeType.PERSIST)
private List<ReceiptItem> items = new ArrayList<>();
```  
Khi persist Receipt, các ReceiptItem liên quan cũng được persist.

Lưu ý: Không nên sử dụng CascadeType.REMOVE hoặc CascadeType.ALL một cách tùy tiện, đặc biệt với dữ liệu dùng chung giữa nhiều Entity.


## II. Derived query methods
### 1. Khái niệm
Derived Query Methods là cơ chế của Spring Data JPA cho phép tạo truy vấn database dựa trên tên phương thức trong Repository mà không cần viết câu lệnh SQL hoặc JPQL.

Spring Data JPA phân tích tên phương thức, xác định điều kiện truy vấn và tự động tạo câu truy vấn tương ứng.  
Ví dụ: 
```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
```  

public interface UserRepository extends JpaRepository<User, Long> {
List<User> findByName(String name);
}

Khi gọi:

List<User> users = userRepository.findByName("Nhi");

Spring Data JPA tự tạo truy vấn tương đương:

SELECT *
FROM users
WHERE name = 'Nhi';
### 2. Cấu trúc tên phương thức

Tên phương thức thường có cấu trúc:

findBy + Tên thuộc tính + Điều kiện

### 3. Các từ khóa thường dùng
Truy vấn cơ bản:  

| Từ khóa | Ý nghĩa | Ví dụ |
| :--- | :--- | :--- |
| **`findBy`** | Tìm theo điều kiện | `findByName()` |
| **`readBy`** | Đọc dữ liệu theo điều kiện | `readByName()` |
| **`getBy`** | Lấy dữ liệu theo điều kiện | `getByName()` |
| **`existsBy`** | Kiểm tra dữ liệu tồn tại | `existsByEmail()` |
| **`countBy`** | Đếm số bản ghi | `countByStatus()` |
| **`deleteBy`** | Xóa theo điều kiện | `deleteByStatus()` |  

Các toán tử điều kiện  

| Từ khóa | Ý nghĩa | Ví dụ |
| :--- | :--- | :--- |
| **`findBy`** | Tìm theo điều kiện | `findByName()` |
| **`readBy`** | Đọc dữ liệu theo điều kiện | `readByName()` |
| **`getBy`** | Lấy dữ liệu theo điều kiện | `getByName()` |
| **`existsBy`** | Kiểm tra dữ liệu tồn tại | `existsByEmail()` |
| **`countBy`** | Đếm số bản ghi | `countByStatus()` |
| **`deleteBy`** | Xóa theo điều kiện | `deleteByStatus()` |  


## III. JPQL với @Query

### 1. Khái niệm JPQL
JPQL (Java Persistence Query Language) là ngôn ngữ truy vấn của JPA, cho phép truy vấn dữ liệu thông qua Entity và thuộc tính Java thay vì tên bảng và tên cột trong database.

JPQL có cú pháp gần giống SQL nhưng có một số điểm khác biệt:
- JPQL truy vấn trên Entity, còn SQL truy vấn trên bảng.
- JPQL sử dụng tên thuộc tính của Entity, còn SQL sử dụng tên cột database.
- JPQL có thể truy vấn các Entity liên quan thông qua quan hệ đã khai báo trong JPA.  

Ví dụ, Entity User được ánh xạ tới bảng users:
```java
@Entity
@Table(name = "users")
public class User {
@Id
private Long id;

    private String name;

    private String email;
}
```  
Truy vấn bằng JPQL:

SELECT u FROM User u WHERE u.name = :name

Truy vấn tương đương bằng SQL:

SELECT * FROM users WHERE name = ?

Lưu ý: User là tên Entity, còn u.name là thuộc tính Java trong Entity. JPQL không bắt buộc tên Entity phải trùng với tên bảng database.

### 2. Các dạng truy vấn JPQL thường dùng
#### 2.1 Truy vấn cơ bản với SELECT
```java
@Query("SELECT u FROM User u")
List<User> findAllUsers();
```
Truy vấn lấy tất cả đối tượng User.

Lọc theo điều kiện:

```java
@Query("SELECT u FROM User u WHERE u.age >= :age")
List<User> findUsersByMinimumAge(@Param("age") int age);
```
Ý nghĩa: Tìm những người dùng có tuổi lớn hơn hoặc bằng giá trị truyền vào.  

#### 2.2 Kết hợp nhiều điều kiện
```java
@Query("""
SELECT u FROM User u
WHERE u.status = :status
AND u.age >= :age
""")
List<User> findUsers(
@Param("status") String status,
@Param("age") int age
);
```


JPQL hỗ trợ các toán tử như AND, OR, NOT, IN, BETWEEN, LIKE, IS NULL và IS NOT NULL.

Ví dụ tìm tên có chứa từ khóa:
```java
@Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
List<User> searchByName(@Param("keyword") String keyword);
```

### 2.3 Truy vấn thuộc tính của Entity liên quan

JPQL có thể truy vấn thông qua các mối quan hệ được khai báo trong Entity.

Ví dụ: ReceiptItem có thuộc tính receipt, trong đó Receipt có thuộc tính id.

```java
@Query("""
    SELECT ri FROM ReceiptItem ri
    WHERE ri.receipt.id = :receiptId
    """)
List<ReceiptItem> findItemsByReceiptId(
    @Param("receiptId") Long receiptId
);
```

## IV. Native Query  
Native Query là cách thực hiện truy vấn bằng câu lệnh SQL trực tiếp trên database thông qua JPA và Spring Data JPA.

Khác với JPQL truy vấn trên Entity và thuộc tính Java, Native Query sử dụng tên bảng, tên cột và cú pháp SQL thực tế của database.

Truy vấn bằng JPQL:
```java
@Query("SELECT u FROM User u WHERE u.name = :name")
List<User> findByName(@Param("name") String name);
```

Truy vấn bằng Native Query:
```java
@Query(value = "SELECT * FROM users WHERE name = :name",
nativeQuery = true)
List<User> findByNameNative(@Param("name") String name);
```

Trong đó:

value: Chứa câu lệnh SQL cần thực thi.

nativeQuery = true: Cho Spring Data JPA biết câu truy vấn là SQL thuần, không phải JPQL.

users: Tên bảng trong database.

name: Tên cột trong database.
## V. Truy vấn sử dụng Entity manager
EntityManager là thành phần của JPA dùng để quản lý vòng đời của Entity và thực hiện các thao tác với database như lưu, tìm kiếm, cập nhật, xóa dữ liệu và tạo truy vấn.

Khác với JpaRepository, cung cấp sẵn nhiều phương thức CRUD, EntityManager cho phép lập trình viên kiểm soát trực tiếp quá trình truy vấn và quản lý Entity trong Persistence Context.

Một số phương thức thường dùng:   

| Phương thức | Chức năng |
| :--- | :--- |
| **`persist()`** | Đưa Entity mới vào trạng thái được quản lý |
| **`find()`** | Tìm Entity theo khóa chính |
| **`merge()`** | Đồng bộ trạng thái Entity với Persistence Context |
| **`remove()`** | Đánh dấu Entity được quản lý để xóa |
| **`createQuery()`** | Tạo truy vấn JPQL |
| **`createNativeQuery()`** | Tạo truy vấn SQL thuần |
| **`flush()`** | Đồng bộ các thay đổi đang chờ xuống database |
| **`refresh()`** | Nạp lại trạng thái Entity từ database |  


**Khai baáo EntityManager trong SpringBoot**
Trong Spring Boot, có thể sử dụng @PersistenceContext để nhận một EntityManager do JPA quản lý.

Ví dụ:
```java
@Repository
public class UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
}
```

Giải thích:

@Repository: Đánh dấu lớp thực hiện các thao tác truy cập dữ liệu.

@PersistenceContext: Tiêm Persistence Context do JPA quản lý.

EntityManager: Cung cấp các phương thức thao tác với Entity và tạo truy vấn.

Trong ứng dụng Spring, đối tượng được tiêm thường là proxy phù hợp với transaction hiện tại, không nhất thiết là một đối tượng EntityManager dùng chung cho mọi request.
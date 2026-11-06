# BUỔI 10: SECURITY BASIC IMPLEMENT

- [BUỔI 10: SECURITY BASIC IMPLEMENT](#buổi-10-security-basic-implement)
  - [I. Spring Security](#i-spring-security)
    - [1. Định nghĩa](#1-định-nghĩa)
    - [2. Cách Spring Security hoạt động](#2-cách-spring-security-hoạt-động)
    - [3. Cài đặt](#3-cài-đặt)
    - [4. Implement](#4-implement)
      - [4.1 Kích hoạt tính năng WebSecurity](#41-kích-hoạt-tính-năng-websecurity)
      - [4.2 Tạo tài khoản user](#42-tạo-tài-khoản-user)
      - [4.3 Phân quyền truy cập](#43-phân-quyền-truy-cập)
      - [4.4 Thêm controller.](#44-thêm-controller)
  - [II. JWT](#ii-jwt)
    - [1. Định nghĩa](#1-định-nghĩa-1)
    - [2. Cấu trúc JWT](#2-cấu-trúc-jwt)
    - [3. Cách JWT hoạt động](#3-cách-jwt-hoạt-động)
    - [4. Access Token và Refresh Token](#4-access-token-và-refresh-token)
    - [5. Ưu điểm và nhược điểm của JWT](#5-ưu-điểm-và-nhược-điểm-của-jwt)
  - [III. Authentication, Authorization](#iii-authentication-authorization)
    - [1. Authentication](#1-authentication)
      - [1.1 Định nghĩa](#11-định-nghĩa)
      - [1.2 Cách hoạt động](#12-cách-hoạt-động)
      - [1.3 Các thành phần quan trọng](#13-các-thành-phần-quan-trọng)
      - [1.4 Authentication và Session](#14-authentication-và-session)
    - [2. Authorization](#2-authorization)
      - [2.1 Định nghĩa](#21-định-nghĩa)
      - [2.2 Role và Authority](#22-role-và-authority)
      - [2.3 Phân quyền trong Spring Security](#23-phân-quyền-trong-spring-security)


## I. Spring Security
### 1. Định nghĩa
Spring Security là một framework của Spring giúp bảo vệ ứng dụng bằng cách kiểm soát:
- Authentication: Xác định người dùng là ai.
- Authorization: Xác định người dùng được phép làm gì.  

Nếu không có cơ chế bảo mật, bất kỳ ai biết URL đều có thể gửi request đến API này. Spring Security giúp giải quyết những vấn đề này bằng cách kiểm tra quyền truy cập trước khi request được xử lý.

### 2. Cách Spring Security hoạt động
Spring Security sử dụng một chuỗi các bộ lọc gọi là Security Filter Chain.  

Luồng xử lý cơ bản:
1. Client gửi HTTP Request đến Server.
2. Request đi qua Security Filter Chain.
3. Spring Security kiểm tra thông tin xác thực.
4. Nếu chưa xác thực hoặc không đủ quyền, request bị từ chối.
5. Nếu hợp lệ, request được chuyển đến Controller để xử lý.
### 3. Cài đặt
Để cài đặt Spring Security, ta thêm dependencies:  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springframework</groupId>
    <artifactId>spring-security-example</artifactId>
    <version>0.1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.5.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```  

### 4. Implement
#### 4.1 Kích hoạt tính năng WebSecurity
Trước tiên, để kích hoạt tính năng Spring Security trên ứng dụng Web, ta cần gắn annotation `@EnableWebSecurity` trên một bean bất kỳ của mình.

Ví dụ: Tạo ra một class `WebSecurityConfig` để là nơi tập trung các xử lý các thông tin liên quan tới security.

```java
@EnableWebSecurity
public class WebSecurityConfig {
    // ...
}
```    

#### 4.2 Tạo tài khoản user

Thông thường, tài khoản user của người dùng sẽ được lưu trong csdl và mã hóa. Tuy nhiên, trong ví dụ basic này, một tài khoản người dùng sẽ đuợc lưu trong chính bộ nhớ chương trình.

```java

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // Tạo ra user trong bộ nhớ
        // lưu ý, chỉ sử dụng cách này để minh họa
        // Còn thực tế chúng ta sẽ kiểm tra user trong csdl
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
                    .username("loda")
                    .password("loda")
                    .roles("USER") // phân quyền là người dùng.
                    .build()
        );
        return manager;
    }
}
```
`WebSecurityConfigurerAdapter` là một interface tiện ích của Spring Security giúp chúng ta cài đặt các thông tin dễ dàng hơn.

_Method_ `userDetailsService()` có tác dụng cung cấp thông tin user cho Spring Security, chúng ta _Override_ lại method này và cung cấp cho nó một `User` là `loda`.

#### 4.3 Phân quyền truy cập

Khi đã có `User`, chúng ta sẽ cần phân quyền xem một `User` sẽ được phép truy cập vào những tài nguyên nào.

Lúc này, vẫn ở trong `WebSecurityConfigurerAdapter`, chúng ta override lại method `protected void configure(HttpSecurity http)` để thực hiện việc phân quyền.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // Tạo ra user trong bộ nhớ
        // lưu ý, chỉ sử dụng cách này để minh họa
        // Còn thực tế chúng ta sẽ kiểm tra user trong csdl
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
                    .username("loda")
                    .password("loda")
                    .roles("USER") // phân quyền là người dùng.
                    .build()
        );
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll() // Cho phép tất cả mọi người truy cập vào 2 địa chỉ này
                    .anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
                    .and()
                .formLogin() // Cho phép người dùng xác thực bằng form login
                    .defaultSuccessUrl("/hello")
                    .permitAll() // Tất cả đều được truy cập vào địa chỉ này
                    .and()
                .logout() // Cho phép logout
                    .permitAll();
    }
}
```

`HttpSecurity` là đối tượng chính của Spring Security, cho phép chúng ta cấu hình mọi thứ cần bảo mật, và nó được xây dựng dưới design pattern giống với `Builder Pattern`, nên mọi cài đặt có thể viết liên tục thông qua toán tử `.`

Ở ví dụ trên, những gì chúng ta muốn cho phép, chúng ta sẽ xài method `.permit()`, còn những gì cấm hoặc yêu cầu xác thực sẽ dùng `.authenticated()`

Khi gọi `.formLogin()` thì chúng ta cấu hình cho phép người dùng đăng nhập, thông qua địa chỉ mặc định `/login` do Spring Security tự tạo ra (Cái này có thể custom theo ý mình được, nhưng chúng ta sẽ tiếp cận ở bài sau).

Tương tự `.logout()` cho phép người dùng logout, Nếu không nói gì thêm, Spring Security sẽ mặc định tự tạo ra một trang logout với địa chỉ `/logout`.

#### 4.4 Thêm controller.

Chúng ta tạo ra trang web của mình gồm có trang `homepage` và trang `hello`.

- Trang `homepage` thì ai cũng truy cập được.
- Trang `hello` thì phải xác thực mới được truy cập.

Các địa chỉ `/login` và `/logout` thì không cần tạo, Spring Security đã tạo sẵn rồi, chúng ta dùng lại luôn.

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/", "/home"})
    public String homepage() {
        return "home"; // Trả về home.html
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello"; // Trả về hello.html
    }

}
```


## II. JWT
### 1. Định nghĩa
JWT (JSON Web Token) là một tiêu chuẩn mở (RFC 7519) dùng để truyền tải thông tin giữa các bên dưới dạng JSON, được ký số để có thể xác minh tính toàn vẹn.  

Trong Spring Boot, JWT thường được sử dụng để xây dựng cơ chế Authentication không sử dụng Session (Stateless Authentication).

Thay vì lưu trạng thái đăng nhập trên Server, hệ thống cấp một token cho Client sau khi đăng nhập thành công.

Client gửi token này trong những request tiếp theo để chứng minh danh tính.

### 2. Cấu trúc JWT
Một JWT gồm 3 phần, được ngăn cách bởi dấu chấm (.). Cấu trúc: Header.Payload.Signature

**a. Header**
Chứa thông tin về thuật toán ký và loại token.

Ví dụ:
```json
{
    "alg": "HS256",
    "typ": "JWT"
}
```
- alg: Thuật toán ký token.
- typ: Loại token.

**b. Payload**

Chứa các thông tin được gọi là Claims.

Ví dụ:
```json
{
    "sub": "admin",
    "role": "ADMIN",
    "iat": 1793350800,
    "exp": 1793354400
}
```  
Một số Claims phổ biến:  

| Claim | Ý nghĩa |
| :--- | :--- |
| **`sub`** | Subject – chủ thể của token |
| **`iat`** | Issued At – thời điểm tạo token |
| **`exp`** | Expiration Time – thời điểm hết hạn |
| **`iss`** | Issuer – bên phát hành token |
| **`aud`** | Audience – đối tượng nhận token |

**c. Signature**

Chữ ký được tạo từ Header, Payload và Secret Key.

Ví dụ với thuật toán HS256:  
HMACSHA256(
Base64Url(Header) + "." + Base64Url(Payload),
SecretKey
)

### 3. Cách JWT hoạt động
Quy trình gồm 2 giai đoạn:

**Giai đoạn 1: Đăng nhập**
1. Client gửi username và password đến Server.
2. Server kiểm tra thông tin đăng nhập.
3. Nếu hợp lệ, Server tạo JWT.
4. Server trả JWT về cho Client.
5. Client lưu token để sử dụng cho các request tiếp theo.

**Giai đoạn 2: Gửi request**
1. Client gửi request đến API.
2. Đính kèm JWT trong HTTP Header.
3. Server kiểm tra chữ ký và thời hạn token.
4. Nếu hợp lệ, Server xác định danh tính và quyền của người dùng.
5. Request được chuyển đến Controller nếu đáp ứng yêu cầu phân quyền.
### 4. Access Token và Refresh Token  
Trong thực tế, hệ thống có thể sử dụng hai loại token:  

| Access Token | Refresh Token |
| :--- | :--- |
| Dùng để truy cập API | Dùng để cấp Access Token mới |
| Thời hạn ngắn | Thời hạn dài hơn |
| Gửi trong mỗi request cần xác thực | Chỉ gửi khi cần làm mới token |

Ví dụ:
- Access Token hết hạn sau 15 phút.
- Refresh Token hết hạn sau 7 ngày.
- Khi Access Token hết hạn, Client gửi Refresh Token để xin Access Token mới.
  Refresh Token cần được bảo vệ cẩn thận và có cơ chế thu hồi khi cần thiết.   

### 5. Ưu điểm và nhược điểm của JWT
| Ưu điểm | Nhược điểm |
| :--- | :--- |
| Không cần lưu Session trên Server | Khó thu hồi token trước khi hết hạn nếu không có cơ chế bổ sung |
| Phù hợp với REST API | Token bị đánh cắp có thể bị sử dụng |
| Dễ mở rộng nhiều Server | Token có thể chiếm dung lượng request |
| Có thể chứa thông tin Role, Authority | Cần quản lý Secret Key và thời hạn token an toàn |
## III. Authentication, Authorization
### 1. Authentication
#### 1.1 Định nghĩa
Authentication (Xác thực) là quá trình xác minh danh tính của người dùng, nhằm xác định người đang truy cập hệ thống có đúng là người mà họ khai báo hay không.

#### 1.2 Cách hoạt động
1. Client gửi username và password đến Server.
2. AuthenticationManager tiếp nhận yêu cầu xác thực.
3. UserDetailsService tìm thông tin người dùng trong database.
4. PasswordEncoder kiểm tra mật khẩu.
5. Nếu hợp lệ, hệ thống trả về thông tin xác thực thành công.  

#### 1.3 Các thành phần quan trọng  
**UserDetails** là Interface đại diện cho thông tin người dùng trong Spring Security.  

Một số phương thức quan trọng:  

| Phương thức | Ý nghĩa |
| :--- | :--- |
| **`getUsername()`** | Lấy username |
| **`getPassword()`** | Lấy mật khẩu đã mã hóa |
| **`getAuthorities()`** | Lấy danh sách quyền |
| **`isAccountNonExpired()`** | Kiểm tra tài khoản hết hạn |
| **`isAccountNonLocked()`** | Kiểm tra tài khoản bị khóa |
| **`isEnabled()`** | Kiểm tra tài khoản có được kích hoạt |  

**UserDetailsService** là Interface dùng để tìm kiếm thông tin người dùng, thường từ database.  
```java
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
``` 
-> Spring Security sử dụng phương thức loadUserByUsername() để lấy thông tin tài khoản khi đăng nhập.  

**PasswordEncoder**: Dùng để mã hóa mật khẩu và kiểm tra mật khẩu người dùng nhập vào.  

Ví dụ sử dụng BCrypt:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```  
Mã hóa và kiểm tra:
```java
String encodedPassword = passwordEncoder.encode("123456");
```  
```java
passwordEncoder.matches("123456", encodedPassword); 
```  

**AuthenticationManager** Là thành phần điều phối quá trình xác thực.
```java
Authentication authentication =
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            username, password
        )
    );
```  
Nếu xác thực thành công, trả về đối tượng Authentication. Nếu thất bại, Spring Security ném exception.  

**SecurityContextHolder** Lưu trữ thông tin xác thực của người dùng hiện tại trong quá trình xử lý request.
```java
Authentication authentication =
    SecurityContextHolder.getContext().getAuthentication();

String username = authentication.getName();
```  

#### 1.4 Authentication và Session
Sau khi đăng nhập thành công, hệ thống cần một cách để ghi nhớ người dùng trong những request tiếp theo.

Có hai cách phổ biến:  

| Tiêu chí | Session-based Authentication | Token-based Authentication (JWT) |
| :--- | :--- | :--- |
| **Trạng thái lưu trữ (State)** | Stateful (Server phải lưu trạng thái phiên đăng nhập) | Stateless (Server không lưu, token tự chứa thông tin) |
| **Nơi lưu ở Client** | Thường giữ Session ID trong Cookie | Lưu Token trong localStorage hoặc Cookie |
| **Cách gửi mỗi request** | Tự động gửi qua Session Cookie | Gửi trong Header (Authorization: Bearer <Token>) |
| **Xác thực ở Server** | Server phải tra cứu Database / RAM / Redis | Server chỉ cần giải mã và kiểm tra chữ ký |
| **Khả năng mở rộng (Scale)** | Kém hơn (Cần đồng bộ session giữa các server) | Rất tốt (Phù hợp cho Microservices, hệ thống phân tán) |
| **Hủy đăng nhập sớm** | Rất dễ (Xóa session trên server là xong) | Khó hơn (Phải dùng Blacklist hoặc đợi token hết hạn) |
| **Dung lượng truyền tải** | Nhỏ (Chỉ là một chuỗi ID ngắn) | Lớn (Chứa payload thông tin mã hóa và chữ ký) |

### 2. Authorization
#### 2.1 Định nghĩa
Authorization (Phân quyền) là quá trình xác định người dùng đã đăng nhập được phép thực hiện những hành động nào trong hệ thống.
#### 2.2 Role và Authority
Spring Security sử dụng hai khái niệm:

**Authority** (Quyền hạn): Đại diện cho một quyền cụ thể mà người dùng được cấp.  
Ví dụ:  
- READ_FOOD 
- CREATE_FOOD 
- DELETE_FOOD 
- PAYMENT
  
**Role** (Vai trò): Đại diện cho một nhóm quyền của người dùng.
Ví dụ:
- ROLE_ADMIN
- ROLE_STAFF
- ROLE_CUSTOMER  
Một Role có thể bao gồm nhiều Authority.

#### 2.3 Phân quyền trong Spring Security
Có hai cách phổ biến để kiểm soát quyền truy cập:  
**a. Request-level Authorization**: Phân quyền dựa trên URL và HTTP Method trong SecurityFilterChain.  

Ví dụ:  
```java
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll() // Ai cũng được truy cập
            .requestMatchers("/api/admin/**").hasRole("ADMIN") // 
            .requestMatchers("/api/staff/**").hasAnyRole("ADMIN", "STAFF")
            .anyRequest().authenticated() //Yêu cầu đăng nhập
        );

    return http.build();
}
```

**b. Method-level Authorization** Phân quyền trực tiếp tại các method trong Controller hoặc Service.

Trước tiên, bật method security:
```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
}
```  
Sau đó sử dụng annotation:
```java
@PreAuthorize("hasRole('ADMIN')") //Kiểm tra quyền trước khi thực thi method
@DeleteMapping("/foods/{id}")
public void deleteFood(@PathVariable Long id) {
    foodService.deleteFood(id);
}
```

-----------
Note:
- Cần xem kỹ hơn về từng tầng trong filter chain.
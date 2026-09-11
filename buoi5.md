# BUỔI 5: KIẾN THỨC SPRING BOOT CƠ BẢN

## I. HTTP
### 1. Khái niệm HTTP
HTTP (Hypertext Transfer Protocol) - Giao thức truyền tải siêu văn bản là nền tảng của World Wide Web, được sử dụng để truy cập và hiển thị các trang web thông qua các liên kết siêu văn bản, cho phép người dùng điều hướng giữa các trang khác nhau dễ dàng.  

Ví dụ: Trình duyệt sẽ gửi một yêu cầu HTTP đến máy chủ; sau đó máy chủ sẽ trả về một phản hồi cho trình duyệt. Phản hồi này chứa thông tin trạng thái về yêu cầumvà cũng có thể chứa nội dung được yêu cầu.  

### 2. Các method trong HTTP
#### 2.1 GET
- GET được dùng để - Các yêu cầudữ liệu từ một tài nguyên được chỉ định.  

- Lưu ý rằng chuỗi truy vấn (các cặp tên/giá trị) được gửi trong URL của - Các yêu cầuGET:
/test/demo_form.php?name1=value1&name2=value2  

**Một số lưu ý về yêu GET:**
- Các yêu cầu GET có thể được lưu vào bộ nhớ cache
- Các yêu cầu GET vẫn còn trong lịch sử trình duyệt
- Các yêu cầu GET có thể được đánh dấu trang
- Không nên sử dụng các các yêu cầu GET khi xử lý dữ liệu nhạy cảm
- Các yêu cầu GET có giới hạn về độ dài
- Các yêu cầu GET chỉ được sử dụng để yêu cầudữ liệu (không sửa đổi)  

#### 2.2 POST
- POST được sử dụng để gửi dữ liệu đến máy chủ để tạo/cập nhật một tài nguyên.

- Dữ liệu được gửi đến máy chủ bằng POST được lưu trữ trong phần thân - Các yêu cầucủa - Các yêu cầuHTTP:  

POST /test/demo_form.php HTTP/1.1
Host: nhi

name1=value1&name2=value2  

**Một số lưu ý về - Các yêu cầu POST:**
- Các yêu cầu POST không bao giờ được lưu vào bộ nhớ cache
- Các yêu cầu POST không nằm trong lịch sử trình duyệt
- Các yêu cầu POST không thể được đánh dấu trang
- Các yêu cầu POST không có giới hạn về độ dài dữ liệu

**So sánh GET và POST**  
| GETPOST | GET | POST |
|---|---|---|
| **Nút BACK / Tải lại trang (Reload)** | Không gây ảnh hưởng | Dữ liệu sẽ được gửi lại (trình duyệt có thể cảnh báo người dùng rằng dữ liệu sắp được gửi lại) |
| **Đánh dấu trang (Bookmarked)** | Có thể đánh dấu trang | Không thể đánh dấu trang |
| **Bộ nhớ đệm (Cached)** | Có thể được lưu vào bộ nhớ đệm | Không được lưu vào bộ nhớ đệm |
| **Kiểu mã hóa (Encoding type)** | `application/x-www-form-urlencoded` | `application/x-www-form-urlencoded` hoặc `multipart/form-data`. Sử dụng mã hóa multipart khi gửi dữ liệu nhị phân |
| **Lịch sử (History)** | Các tham số được lưu lại trong lịch sử trình duyệt | Các tham số không được lưu trong lịch sử trình duyệt |
| **Giới hạn độ dài dữ liệu** | Có. Khi gửi dữ liệu, phương thức GET thêm dữ liệu vào URL; độ dài URL bị giới hạn (độ dài URL tối đa là 2048 ký tự) | Không có giới hạn |
| **Giới hạn về kiểu dữ liệu** | Chỉ cho phép các ký tự ASCII | Không có giới hạn. Dữ liệu nhị phân cũng được phép |
| **Bảo mật (Security)** | GET kém bảo mật hơn POST vì dữ liệu được gửi và hiển thị trong URL. Không bao giờ sử dụng GET khi gửi mật khẩu hoặc thông tin nhạy cảm khác! | POST an toàn hơn GET một chút vì các tham số không được lưu trong lịch sử trình duyệt hoặc nhật ký máy chủ web |
| **Khả năng hiển thị (Visibility)** | Dữ liệu hiển thị cho mọi người trong URL | Dữ liệu không được hiển thị trong URL |
#### 2.3 PUT
PUT được dùng để gửi dữ liệu đến máy chủ để tạo/cập nhật một tài nguyên.

Sự khác biệt giữa POST và PUT là các yêu cầu PUT có tính chất lũy đẳng (idempotent). Nghĩa là, việc gọi cùng một yêu cầu PUT nhiều lần sẽ luôn cho ra cùng một kết quả. Ngược lại, việc gọi một yêu cầu POST lặp đi lặp lại có thể gây ra các tác dụng phụ như tạo cùng một tài nguyên nhiều lần.
#### 2.4 HEAD
HEAD gần như giống hệt GET, nhưng không có phần thân phản hồi.

Nói cách khác, nếu GET /users trả về danh sách người dùng thì HEAD /users sẽ thực hiện yêu cầu tương tự nhưng sẽ không trả về danh sách người dùng.

Yêu cầu HEAD hữu ích để kiểm tra những gì yêu cầu GET sẽ trả về trước khi thực sự thực hiện yêu cầu GET - yêu cầu HEAD có thể đọc tiêu đề Content-Length để kiểm tra kích thước của tệp mà không thực sự tải xuống tệp.
#### 2.5 DELETE
Phương thức DELETE xoá tài nguyên được chỉ định.
#### 2.6 PATCH
Phương thức PATCH được dùng để áp dụng các sửa đổi một phần cho một tài nguyên.
#### 2.7 OPTIONS 
Phương thức OPTIONS mô tả các tùy chọn giao tiếp cho tài nguyên đích.
#### 2.8 CONNECT
Phương thức CONNECT được dùng để bắt đầu giao tiếp hai chiều (a tunnel) với tài nguyên được yêu cầu.
#### 2.9 TRACE
Phương thức TRACE được sử dụng để thực hiện kiểm tra vòng lặp ngược thông báo nhằm kiểm tra đường dẫn cho tài nguyên đích (thường dùng để gỡ lỗi).
### 3. Request, Response
#### 3.1 Cấu trúc HTTP Request
Một request gồm bốn thành phần, xếp theo thứ tự từ trên xuống:

1. Request Line (dòng yêu cầu) - dòng đầu tiên, gồm ba phần:

Method: hành động muốn thực hiện.
Path / URL: địa chỉ tài nguyên muốn tác động, ví dụ /api/v1/orders/123.
Phiên bản HTTP: ví dụ HTTP/1.1.
Ví dụ: GET /api/v1/products?category=skincare HTTP/1.1  

2. Headers (phần đầu) - các cặp Key: Value mô tả metadata của request. Ví dụ:

Host: api.vietnamcos.com - server đích.
Authorization: Bearer eyJhbGc... - thông tin xác thực.
Content-Type: application/json - định dạng của body.
Accept: application/json - định dạng client mong muốn nhận về.  

3. Dòng trống - một dòng trống ngăn cách giữa headers và body. 
4. Body (phần thân) - dữ liệu gửi kèm, thường có với POST/PUT/PATCH. Với GET thì thường không có body. Body có thể là JSON, form data, hoặc file.

#### 3.2 Cấu trúc HTTP Response
Response cũng có bốn phần tương tự, chỉ khác dòng đầu tiên:

1. Status Line (dòng trạng thái) - gồm phiên bản HTTP, mã trạng thái (status code), và mô tả ngắn. Ví dụ: HTTP/1.1 201 Created.

2. Headers - metadata của response, ví dụ:

Content-Type: application/json - định dạng body server trả về.
Content-Length: 142 - độ dài body tính theo byte.
Cache-Control: no-store - chỉ dẫn về cache.
X-RateLimit-Remaining: 98 - số request còn lại trước khi bị giới hạn.

3. Dòng trống.
4. Body - nội dung server trả về, thường là JSON với dữ liệu hoặc thông báo lỗi. 

**Status code của Response**:
1xx - thông tin.  
2xx - thành công (200 OK, 201 Created, 204 No Content).  
3xx - chuyển hướng (301, 302).  
4xx - lỗi từ phía client, tức bên gọi sai (400 Bad Request, 401 Unauthorized, 404 Not Found).  
5xx - lỗi từ phía server, tức bên server hỏng (500 Internal Server Error, 503 Service Unavailable).  
## II. API, RestAPI
### 1. API
#### 1.1 Khái niệm API
API (Application Programming Interface -  Giao diện lập trình ứng dụng) là một cơ chế cho phép các hệ thống phần mềm khác nhau tương tác với nhau. Nó định nghĩa cách một máy khách có thể yêu cầu dữ liệu hoặc dịch vụ từ máy chủ mà không cần biết cách triển khai nội bộ của nó.

- Cung cấp một cách tiêu chuẩn để các ứng dụng tương tác.
- Hỗ trợ tích hợp giữa các hệ thống và dịch vụ khác nhau.
- Kiểm soát quyền truy cập vào dữ liệu hoặc chức năng cụ thể.  

Ví dụ: Khi một ứng dụng giao đồ ăn hiển thị bản đồ, nó có thể sử dụng API Bản đồ để yêu cầu dữ liệu vị trí và bản đồ từ dịch vụ bản đồ.

#### 1.2 Thành phần API
Một API bao gồm một số yếu tố chính nhằm xác định cách máy khách giao tiếp với máy chủ và trao đổi dữ liệu. Các yếu tố này chỉ định nơi các yêu cầu được gửi, cách chúng được cấu trúc và cách các phản hồi được trả về.
- API Endpoint: Một URL cụ thể mà qua đó các máy khách truy cập một tài nguyên hoặc dịch vụ cụ thể.
- HTTP Methods: Xác định hoạt động được thực hiện bởi một yêu cầu, chẳng hạn như truy xuất, tạo, cập nhật hoặc xóa dữ liệu.
- Headers: Cung cấp thông tin bổ sung về yêu cầu hoặc phản hồi, chẳng hạn như loại nội dung và chi tiết xác thực.
- Parameters: Cung cấp thông tin bổ sung cần thiết để xử lý yêu cầu, chẳng hạn như định danh tài nguyên hoặc tiêu chí lọc.
- Request Body: Chứa dữ liệu được gửi bởi máy khách đến máy chủ, thường ở định dạng JSON hoặc XML.
- Response: Chứa kết quả được máy chủ trả về sau khi xử lý yêu cầu.
- HTTP Status Code: Cho biết kết quả của một yêu cầu, chẳng hạn như thành công, lỗi máy khách hoặc lỗi máy chủ.

### 2. RestAPI
REST API (Representational State Transfer API) là một kiểu kiến trúc API cho phép giao tiếp giữa máy khách và máy chủ qua HTTP. Nó trao đổi dữ liệu thường ở định dạng JSON bằng cách sử dụng các giao thức web tiêu chuẩn.

- Sử dụng các phương thức HTTP như GET, POST, PUT, PATCH và DELETE.
- Máy khách gửi yêu cầu đến các điểm cuối (URL) của máy chủ.
- Máy chủ trả về các phản hồi như JSON, XML, HTML hoặc hình ảnh.
- Ánh xạ các phương thức HTTP tới các thao tác CRUD (Tạo, Đọc, Cập nhật, Xóa).

**Các đặc điểm của REST API**:
- Không trạng thái (Statelessness): Mỗi yêu cầu chứa tất cả thông tin cần thiết và máy chủ không lưu trữ trạng thái phiên của máy khách.
- Kiến trúc Client-Server: Máy khách và máy chủ được tách biệt, giúp cải thiện khả năng mở rộng và phân tách các mối quan tâm.
- Phản hồi có thể lưu vào bộ nhớ đệm (Cacheable Responses): Các phản hồi có thể được đánh dấu là có thể lưu vào bộ nhớ đệm để cải thiện hiệu suất và giảm tải máy chủ.
- Giao diện đồng nhất (Uniform Interface): REST sử dụng các URL, phương thức HTTP và mã trạng thái nhất quán để giao tiếp được tiêu chuẩn hóa.
- Hệ thống phân lớp (Layered System): REST có thể hoạt động trên nhiều lớp, chẳng hạn như proxy, cổng và lớp bảo mật, để cải thiện khả năng mở rộng và bảo mật.

**Ví dụ thực tế về RestAPI**  
RestAPI được sử dụng rộng rãi trong nhiều ngành khác nhau để đơn giản hóa giao tiếp giữa các hệ thống. Một số ứng dụng phổ biến bao gồm:

- Mạng xã hội: Tích hợp các nền tảng của bên thứ ba như Facebook, Twitter và Instagram cho các tính năng như đăng nhập, chia sẻ và đăng bài.
- Thương mại điện tử: Quản lý sản phẩm, xử lý thanh toán, xử lý đơn hàng và quản lý khách hàng.
- Dịch vụ định vị địa lý: Theo dõi GPS, cập nhật vị trí theo thời gian thực và các dịch vụ dựa trên vị trí như tìm địa điểm lân cận.
- Dự báo thời tiết: Lấy dữ liệu thời tiết từ các nguồn bên ngoài để cung cấp thông tin cập nhật và dự báo thời tiết theo thời gian thực.  

**Hạn chế của RestAPI**  
- Mỗi yêu cầu phải bao gồm tất cả thông tin cần thiết, điều này có thể làm tăng kích thước yêu cầu.
- REST tuân theo mô hình yêu cầu-phản hồi, nên nó ít phù hợp hơn cho giao tiếp thời gian thực.
- Trong một số trường hợp, máy khách có thể nhận được nhiều hoặc ít dữ liệu hơn mức cần thiết.
- REST không có các quy tắc nghiêm ngặt như SOAP, do đó việc triển khai có thể khác nhau giữa các API.
- Việc lập phiên bản API và duy trì khả năng tương thích ngược có thể trở nên khó khăn khi API phát triển.
## III. Design partern
### 1. Dependency Injection (DI)
Dependency Injection (DI) là một mẫu thiết kế trong đó một đối tượng nhận các đối tượng cần thiết (các dependency) từ một nguồn bên ngoài thay vì tự tạo ra chúng.

- DI giúp giảm sự kết nối chặt chẽ giữa các lớp
- Cải thiện khả năng tái sử dụng và kiểm thử mã.
- Được sử dụng rộng rãi trong các ứng dụng Java doanh nghiệp.

**Sự phụ thuộc giữa các lớp**
Một dependency là một đối tượng mà một đối tượng khác yêu cầu để thực hiện công việc của nó.
- Một lớp có thể sử dụng chức năng do một lớp khác cung cấp
- Lớp được sử dụng được gọi là một dependency
- Việc tạo đối tượng trực tiếp có thể dẫn đến sự kết nối chặt chẽ
- Những thay đổi trong một triển khai có thể ảnh hưởng đến nhiều lớp
- Dependency Injection giúp quản lý các phần phụ thuộc một cách hiệu quả

**Ví dụ**  
Không dùng Dependency Injection:

```java
class PaymentService {
    public void pay() {
        System.out.println("Thanh toán...");
    }
}

class OrderService {
    private PaymentService paymentService;

    public OrderService() {
        paymentService = new PaymentService();
    }

    public void checkout() {
        paymentService.pay();
        System.out.println("Đặt hàng thành công");
    }
}
```  

```java
public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        orderService.checkout();
    }
}
```
-> `OrderService` phụ thuộc trực tiếp vào `PaymentService`. Nếu sau này muốn đổi sang `MomoPaymentService` thì phải sửa bên trong `OrderService`.

Dùng Dependency Injection: Thay vì để OrderService tự tạo PaymentService, bên ngoài truyền dependency vào:
```java
class PaymentService {
    public void pay() {
        System.out.println("Thanh toán...");
    }
}

class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void checkout() {
        paymentService.pay();
        System.out.println("Đặt hàng thành công");
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();

        OrderService orderService = new OrderService(paymentService);

        orderService.checkout();
    }
}
```
-> Main tạo PaymentService -> Truyển vào OrderService.

### 2. Inversion of Control (IoC)
Inversion of Control (IoC), hay Đảo ngược điều khiển - là một nguyên lý thiết kế phần mềm trong đó quyền kiểm soát luồng thực thi của chương trình bị đảo ngược so với cách lập trình truyền thống. IoC là một nguyên lý lớn, và để đưa nó vào thực tế, người ta thường dùng các kỹ thuật cụ thể (ví dụ như Dependency Injection).

**Bản chất của IoC**
- Cách truyền thống: Đoạn code chủ động gọi các thư viện, tự khởi tạo đối tượng và quyết định thời điểm thực hiện từng tác vụ. 
- Cách dùng IoC: Chương trình chính hoặc một framework bên ngoài (như Spring, .NET Core) sẽ điều khiển luồng hoạt động. Khi cần, framework sẽ gọi ngược lại mã nguồn.

**Ứng dụng**
- Các module hoạt động độc lập hơn và ít bị ràng buộc chặt chẽ với nhau.
- Dễ bảo trì và mở rộng: Dễ dàng thay đổi linh kiện, thành phần hoặc dịch vụ trong hệ thống mà không phải sửa đổi nhiều mã nguồn cũ.
- Dễ kiểm thử (Testability): Giúp việc viết Unit Test cho từng thành phần trở nên thuận tiện hơn.
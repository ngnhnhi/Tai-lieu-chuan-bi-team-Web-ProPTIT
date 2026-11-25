# [BUỔI 1] KICK-OFF & UI/UX CHO DEVELOPER

## I. Khi gõ một địa chỉ web và nhấn Enter, chuyện gì xảy ra cho đến khi trang hiện ra?

### 1. Trình duyệt đọc URL như thế nào?

Giả sử ta nhập `https://example.com/khoa-hoc?mon=frontend#lich-hoc`.

| Thành phần | Ví dụ | Trình duyệt dùng để làm gì? |
| --- | --- | --- |
| Giao thức (scheme) | `https` | Dùng HTTP qua kết nối được bảo mật bằng TLS |
| Tên miền (domain) | `example.com` | Xác định máy chủ cần kết nối thông qua DNS |
| Đường dẫn (path) | `/khoa-hoc` | Xác định tài nguyên cần yêu cầu |
| Chuỗi truy vấn (query string) | `?mon=frontend` | Gửi tham số cho máy chủ, chẳng hạn môn cần lọc |
| Đoạn định vị (fragment) | `#lich-hoc` | Định vị phần tử có `id="lich-hoc"` trong trang. Trình duyệt giữ phần này và không gửi kèm yêu cầu HTTP. |

### 2. Từ tên miền đến phản hồi HTTP

1. **Tra cứu DNS.** DNS giúp tìm địa chỉ IP ứng với tên miền. Trình duyệt hoặc hệ điều hành có thể dùng kết quả đã lưu. Khi chưa có kết quả phù hợp trong bộ nhớ đệm, hệ thống hỏi máy chủ phân giải DNS.
2. **Mở kết nối.** Trình duyệt dùng địa chỉ IP để kết nối đến máy chủ, hoặc dùng lại kết nối sẵn có. Với HTTPS, TLS giúp xác thực máy chủ bằng chứng chỉ và mã hóa dữ liệu trao đổi.
3. **Gửi yêu cầu (request).** Khi mở trang, trình duyệt thường gửi yêu cầu `GET /khoa-hoc?mon=frontend`. Yêu cầu có phương thức, địa chỉ tài nguyên và các header, chẳng hạn thông tin về loại nội dung trình duyệt chấp nhận.
4. **Nhận phản hồi (response).** Máy chủ có thể trả về tệp có sẵn hoặc tạo nội dung từ dữ liệu ứng dụng. Phản hồi gồm mã trạng thái, các header như `Content-Type` và phần nội dung nếu có. Ví dụ, `200` báo yêu cầu thành công, `302` hướng trình duyệt tới URL trong header `Location`, `404` báo không tìm thấy tài nguyên và `500` báo lỗi phía máy chủ.

### 3. Từ HTML đến giao diện trên màn hình

1. Trình duyệt đọc HTML và tạo cây DOM (Document Object Model). Mỗi tiêu đề, ô nhập và nút là một phần tử trong cây mà trình duyệt và JavaScript có thể truy cập.
2. Khi gặp tham chiếu đến CSS, JavaScript hoặc ảnh, trình duyệt tải các tài nguyên cần thiết. Một số tệp có thể đã có trong bộ nhớ đệm. Nhiều yêu cầu có thể chạy song song.
3. Trình duyệt áp dụng CSS để xác định kiểu của phần tử, tính kích thước và vị trí (layout), rồi vẽ chữ, màu và hình ảnh lên màn hình (paint).
4. JavaScript có thể chạy trong lúc tải hoặc sau khi trang hiển thị, tùy cách nạp script. Chẳng hạn, khi người dùng chọn bộ lọc “Front End”, JavaScript có thể gửi yêu cầu lấy danh sách khóa học và cập nhật DOM ngay trên trang.

| Thành phần | Vai trò | Ví dụ trên trang đăng ký |
| --- | --- | --- |
| HTML | Xác định nội dung và cấu trúc | Tiêu đề, ô nhập email và nút gửi |
| CSS | Quy định cách trình bày | Màu nút, khoảng cách và bố cục trên điện thoại |
| JavaScript | Xử lý tương tác và có thể cập nhật trang | Hiện trạng thái “Đang gửi…” khi người dùng gửi biểu mẫu |

**Thử trên trình duyệt.** Mở DevTools, chọn tab **Network**, rồi tải lại một trang đang dùng. Chọn yêu cầu tải tài liệu chính và tìm **Request URL**, **Request Method**, **Status Code** và `Content-Type` trong **Headers**. Sau đó tìm một yêu cầu tải CSS hoặc ảnh và quan sát thời điểm bắt đầu của nó trên cột **Waterfall**.

## II. UX là gì? UI là gì? Hai cái khác nhau thế nào?

### 1. UI (User Interface) – Giao diện người dùng

UI gồm các thành phần người dùng dùng để đọc thông tin và thao tác với sản phẩm. Khi triển khai một biểu mẫu, developer cần xác định:

- **Nội dung và thứ tự.** Trường nào cần có, nhãn nào đi với ô nào, nút nào thực hiện hành động chính.
- **Cách trình bày.** Cỡ chữ, độ tương phản, khoảng cách giữa các nhóm thông tin và cách bố cục thay đổi khi màn hình hẹp.
- **Trạng thái tương tác.** Nút và ô nhập hiển thị thế nào khi được chọn bằng bàn phím (focus), khi đang gửi, khi bị vô hiệu hóa hoặc khi có lỗi.

### 2. UX (User Experience) – Trải nghiệm người dùng

UX là trải nghiệm trong cả quá trình thực hiện mục tiêu. Với việc đăng ký khóa học, luồng bắt đầu từ lúc người dùng tìm lớp phù hợp, xem lịch và số chỗ, điền thông tin, rồi nhận kết quả. Mỗi bước cần trả lời một câu hỏi của người dùng, chẳng hạn “Lớp này có hợp lịch của mình không?”, “Mình đã gửi thành công chưa?” hoặc “Nếu nhập sai thì sửa ở đâu?”.

UI là một phần của UX. Tốc độ phản hồi, số bước cần làm và việc giữ lại dữ liệu khi xảy ra lỗi cũng ảnh hưởng đến trải nghiệm.

### 3. Phân biệt UI và UX qua luồng đăng ký

| Tình huống | Developer triển khai gì trên UI? | Quan sát gì về UX? |
| --- | --- | --- |
| Chọn lớp | Đặt ngày, giờ và số chỗ còn lại ngay cạnh tên lớp | Người dùng có chọn được lớp phù hợp trong lần thử đầu tiên không? |
| Gửi biểu mẫu | Đổi nhãn nút thành “Đang gửi…” trong khi chờ phản hồi và ngăn gửi lặp | Người dùng có hiểu yêu cầu đang được xử lý hay họ bấm lại? |
| Email sai định dạng | Hiện hướng dẫn ngay cạnh ô email và giữ nguyên các trường đã điền | Người dùng có tìm ra chỗ sai, sửa và gửi lại mà không phải nhập từ đầu không? |
| Đăng ký thành công | Hiện tên lớp, giờ học và bước tiếp theo trên màn hình xác nhận | Người dùng có biết mình đã đăng ký lớp nào và cần làm gì tiếp theo không? |

## III. 10 Usability Heuristics của Nielsen Norman Group

Đây là 10 nguyên tắc tổng quát để rà soát tính dễ sử dụng của giao diện. Khi áp dụng, hãy bắt đầu từ một thao tác cụ thể của người dùng, ghi lại chỗ họ gặp khó khăn và đề xuất thay đổi cho đúng chỗ đó.

### 1. Visibility of system status - Khả năng hiển thị trạng thái hệ thống
- Hệ thống phải thông báo cho người dùng về những gì đang diễn ra thông qua phản hồi kịp thời, phù hợp.
- Cần cân nhắc yếu tố:
  - Hệ thống cần phản hồi ngay sau mỗi thao tác của người dùng, dù là nhỏ nhất (như nhấn nút, tải trang, gửi biểu mẫu).
  - Sử dụng thanh tiến trình, thông báo trạng thái hoặc biểu tượng đang tải để cho người dùng biết là hệ thống đang hoạt động
  - Thông tin phản hồi nên ngắn gọn, dễ hiểu và xuất hiện đúng thời điểm

Ví dụ. Hiển thị thanh tiến trình khi tải tệp.  
Lỗi thường gặp: Không hiển thị trạng thái khiến người dùng nghĩ hệ thống bị treo

### 2. Sự tương thích giữa hệ thống và thế giới thực (Match Between System and the Real World)
Sử dụng các từ ngữ, biểu tượng và khái niệm quen thuộc với người dùng phổ thông thay vì dùng thuật ngữ chuyên ngành, kỹ thuật hoặc từ viết tắt khó hiểu.  

Ví dụ: Thay vì ghi: “Lỗi không xác định”, hãy ghi: “Xin lỗi, có lỗi xảy ra. Vui lòng thử lại sau vài phút."

### 3. Quyền kiểm soát và tự do của người dùng (User Control and Freedom)
- Cung cấp thao tác “Quay lại”, “Hủy” hoặc “Hoàn tác” ở những bước phù hợp. Ví dụ, sau khi xóa email, hiển thị thông báo “Đã xóa thư” kèm nút “Hoàn tác” trong vài giây.
- Nếu hành động có thể gây hậu quả nghiêm trọng (xóa dữ liệu, gửi thanh toán,…), cần xác nhận lại bằng hộp thoại.

### 4. Nhất quán và tiêu chuẩn hóa (Consistency and Standards)
- Sử dụng chung một kiểu định dạng cho các nút, biểu tượng, màu sắc và cách trình bày ở tất cả các trang để tạo sự quen thuộc.
- Tuân theo các chuẩn thiết kế phổ biến (vd: kính lúp cho biểu tượng tìm kiếm)

### 5. Phòng ngừa lỗi (Error Prevention)
- Thiết kế các biểu mẫu và quy trình sao cho người dùng khó có thể nhập sai dữ liệu.
- Hiển thị cảnh báo trước khi người dùng thực hiện hành động không thể hoàn tác được.
- Dùng tính năng gợi ý, kiểm tra thời gian thực (real-time validation) để ngăn lỗi từ sớm thay vì báo sau khi gửi.

### 6. Nhận biết thay vì ghi nhớ (Recognition Rather Than Recall)
Giảm gánh nặng ghi nhớ cho người dùng. Người dùng không phải nhớ thông tin từ phần này sang phần khác trong giao diện. Và để làm được điều đó, trong thiết kế cần:  
- Cung cấp danh sách các tùy chọn, gợi ý tự động, hoặc thông tin đã dùng gần đây để người dùng chọn nhanh mà không cần nhớ lại
- Sử dụng biểu tượng dễ hiểu, nhãn rõ ràng và nhóm thông tin theo logic quen thuộc

### 7. Tính linh hoạt và hiệu quả sử dụng (Flexibility and Efficiency of Use)
Cho phép người dùng mới thao tác dễ dàng qua giao diện trực quan, hướng dẫn từng bước hoặc hướng dẫn khởi động nhanh.  
Ví dụ: Ứng dụng ngân hàng hiển thị hướng dẫn từng bước khi lần đầu dùng tính năng chuyển khoản.  

Cung cấp khả năng tùy chỉnh trải nghiệm (ẩn bớt bước, thay đổi bố cục, lưu mẫu thao tác...)

### 8. Thiết kế tối giản và thẩm mỹ (Aesthetic and Minimalist Design)
Ưu tiên thông tin cần cho thao tác hiện tại. Có thể thu gọn phần chi tiết và cho người dùng mở ra khi cần.

Ví dụ: Trong ứng dụng đặt vé máy bay, chỉ hiển thị giá, giờ bay, điểm đi - điểm đến một cách nổi bật, còn chi tiết hành lý, chính sách hoàn vé được ẩn trong nút “Xem thêm”

### 9. Giúp người dùng nhận biết, chẩn đoán và khắc phục lỗi (Help Users Recognize, Diagnose, and Recover from Errors)
Thông báo lỗi nên giải thích rõ ràng điều gì sai, vì sao sai và hướng dẫn cụ thể cách khắc phục.    

### 10. Trợ giúp và tài liệu (Help and Documentation)  
Cung cấp tài liệu, hướng dẫn sử dụng cho người dùng ban đầu.  
Cung cấp tính năng trợ giúp dễ tìm, chẳng hạn như biểu tượng “?” hoặc mục “Hỗ trợ” ở vị trí dễ thấy.

Ví dụ: Trên ứng dụng ngân hàng số, có biểu tượng dấu hỏi “?” hoặc chữ “Hỗ trợ” nằm ở góc phải trên cùng của mỗi màn hình.

## IV. AI Slop trong thiết kế giao diện là gì? Vì sao nhiều giao diện do AI làm trông na ná nhau?

### 1. AI Slop là gì?

Trong buổi này, “AI Slop” là cách gọi không chính thức cho giao diện do AI tạo ra có vẻ hoàn chỉnh khi nhìn lướt qua nhưng còn nhiều chi tiết chung chung hoặc chưa được kiểm tra với nhu cầu thực tế. Ví dụ, một trang đăng ký có nhiều thẻ giới thiệu và hiệu ứng, trong khi thông tin về lịch học và số chỗ còn lại lại khó tìm.

### 2. Vì sao các giao diện dễ giống nhau?

- Yêu cầu như “làm giao diện hiện đại, đẹp mắt” để ngỏ người dùng là ai, họ cần làm gì và nội dung nào quan trọng. Công cụ có thể điền chỗ trống bằng các mẫu quen thuộc như tiêu đề lớn, nền chuyển màu và lưới thẻ bo góc.
- Khi dùng cùng mẫu hoặc thư viện thành phần và giữ nguyên bản nháp, các trang dễ lặp lại bố cục, cách đặt nhãn và thứ tự nội dung.
- Bản xem trước thường tập trung vào màn hình có dữ liệu. Các trạng thái đang tải, danh sách trống, lỗi và thành công dễ bị bỏ sót nếu chưa thử luồng thao tác.

### 3. Dấu hiệu cần sửa trong bản nháp

| Dấu hiệu | Ví dụ trên trang đăng ký | Cách sửa |
| --- | --- | --- |
| Thông tin chính bị chìm | Người dùng phải cuộn qua nhiều thẻ giới thiệu mới thấy lịch học | Đặt lịch và tình trạng chỗ ở ngay phần chọn lớp |
| Nhãn thiếu ngữ cảnh | Bốn nút cùng ghi “Bắt đầu”, mỗi nút dẫn đến một chức năng khác nhau | Đặt nhãn theo kết quả của thao tác, như “Xem lịch học” và “Đăng ký lớp” |
| Dữ liệu mẫu quá đồng đều | Tên mọi lớp đều ngắn, lịch luôn có đúng ba dòng | Thử tên lớp dài, lịch nhiều buổi và trường hợp chưa có lịch |
| Thiếu trạng thái | Bấm gửi xong mà nút không thay đổi. Khi máy chủ báo hết chỗ, giao diện vẫn giữ trạng thái còn chỗ. | Thêm trạng thái đang gửi, thông báo lớp đã đầy và cho phép chọn lớp khác |
| Khó dùng bằng bàn phím | Hiệu ứng chỉ xuất hiện khi rê chuột, thứ tự nhấn Tab bỏ qua nút chính | Dùng phần tử HTML đúng chức năng, thử nhấn Tab và kiểm tra chỉ báo focus |

### 4. Từ yêu cầu đến bản nháp có thể kiểm tra

Một yêu cầu giao diện có thể viết cụ thể như sau.

> Làm trang đăng ký cho sinh viên cần chọn lớp phù hợp với lịch cá nhân. Mỗi lớp hiển thị tên, ngày giờ và số chỗ còn lại. Sau khi chọn lớp, sinh viên nhập họ tên và email rồi bấm “Đăng ký”. Trên điện thoại, thông tin lớp nằm phía trên biểu mẫu. Khi đang gửi, hiển thị “Đang gửi…”. Nếu email sai, chỉ rõ lỗi cạnh ô nhập. Nếu lớp đã đầy, hiện thông báo và cho phép chọn lớp khác. Khi thành công, hiển thị tên lớp và thời gian học.

Khi rà soát bản nháp, dùng dữ liệu mẫu sát thực tế và lần lượt thử ba trường hợp: đăng ký thành công, nhập email sai và lớp hết chỗ sau khi người dùng đã mở trang. Thử lại trên màn hình hẹp và dùng phím Tab để đi từ chọn lớp đến nút đăng ký. Ghi nhận bước nào khiến người thử phải dừng lại để đoán, rồi sửa nội dung hoặc cách tương tác ở bước đó.

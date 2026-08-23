# BÁO CÁO SO SÁNH CÁC GIẢI PHÁP LƯU TRỮ FILE CHO SERVER
Hệ thống quản lý bài tập và chấm chữa bài tiếng anh thông minh

## 1. Bối cảnh và yêu cầu
Hệ thống được xây dựng trên nền tảng Spring Boot và triển khai trên VPS, có chức năng giao và nộp bài tập tiếng Anh với bốn kỹ năng: đọc, nói, nghe và viết. Do đó, hệ thống phải tiếp nhận và cung cấp nhiều loại tệp như TXT, PDF, DOCX, MP3, WAV và có thể mở rộng sang các định dạng khác trong tương lai.
Giải pháp lưu trữ cần đáp ứng các yêu cầu chính: dễ triển khai khi hệ thống còn nhỏ; chi phí ban đầu thấp hoặc bằng không; có khả năng lưu trữ lượng lớn dữ liệu; hỗ trợ upload/download hiệu quả; bảo đảm an toàn dữ liệu; và có thể mở rộng mà không phải thay đổi lớn kiến trúc của hệ thống.
## 2. Các phương án lưu trữ được xem xét
### 2.1. Lưu trữ trực tiếp trên ổ đĩa của VPS
File được lưu trực tiếp vào filesystem của máy chủ, ví dụ trong thư mục /uploads. Spring Boot tiếp nhận MultipartFile và ghi dữ liệu xuống ổ đĩa.
Ưu điểm: triển khai đơn giản, không cần dịch vụ bên ngoài và không phát sinh chi phí storage riêng nếu dung lượng VPS đã bao gồm.
Nhược điểm: dung lượng bị giới hạn bởi ổ đĩa VPS; backup và khôi phục phải tự quản lý; khó mở rộng khi có nhiều VPS; rủi ro mất dữ liệu nếu storage của VPS gặp sự cố.
Đánh giá: phù hợp cho môi trường phát triển hoặc hệ thống rất nhỏ, nhưng không phù hợp làm giải pháp lưu trữ lâu dài.
### 2.2. MinIO tự triển khai
MinIO là một object storage có API tương thích với Amazon S3. MinIO có thể chạy trên VPS và cung cấp giao diện lưu trữ đối tượng thay vì chỉ lưu file theo filesystem thông thường.
Ưu điểm: phần mềm có thể tự triển khai; API S3-compatible; phù hợp để học và kiểm soát toàn bộ hạ tầng; có thể bắt đầu với một VPS.
Nhược điểm: phải tự quản lý backup, monitoring, dung lượng, bảo mật và khả năng chịu lỗi; khi mở rộng thành storage cluster, vận hành sẽ phức tạp hơn.
Đánh giá: phù hợp nếu ưu tiên tự quản lý hạ tầng, nhưng tạo thêm công việc vận hành khi hệ thống phát triển.
### 2.3. Cloudflare R2
Cloudflare R2 là dịch vụ object storage của Cloudflare, cung cấp API tương thích với S3. File được lưu dưới dạng object trong bucket và có thể được truy cập thông qua API hoặc các URL được cấp quyền. R2 đặc biệt phù hợp với hệ thống cần lưu nhiều file và muốn giảm phụ thuộc vào filesystem của VPS.
Ưu điểm: có mức miễn phí cho quy mô nhỏ; không tính phí egress Internet theo chính sách R2 hiện tại; S3-compatible; khả năng mở rộng cao; giảm công việc vận hành storage.
Nhược điểm: khi vượt mức miễn phí sẽ phát sinh chi phí theo mức sử dụng; dữ liệu phụ thuộc vào một nhà cung cấp cloud.
Đánh giá: rất phù hợp với hệ thống hiện tại và có đường mở rộng rõ ràng khi số lượng người dùng và file tăng.
### 2.4. Backblaze B2
Backblaze B2 là dịch vụ cloud object storage, hỗ trợ mô hình lưu trữ đối tượng và API tương thích với S3. Đây là một lựa chọn hướng tới chi phí storage thấp và có thể mở rộng theo nhu cầu.
Ưu điểm: có mức miễn phí ban đầu; chi phí storage tương đối thấp; phù hợp với lượng lớn file như MP3, WAV và tài liệu.
Nhược điểm: khi sử dụng nhiều cần tính toán thêm chi phí truy xuất và egress theo chính sách dịch vụ.
Đánh giá: là một lựa chọn tốt nếu ưu tiên chi phí storage thấp.
### 2.5. Amazon S3
Amazon Simple Storage Service (Amazon S3) là dịch vụ object storage của AWS. S3 được thiết kế để lưu trữ và truy xuất dữ liệu với quy mô rất lớn, hỗ trợ nhiều tính năng như versioning, lifecycle, encryption, access control, presigned URL và tích hợp với nhiều dịch vụ AWS.
Ưu điểm: trưởng thành, ổn định, khả năng mở rộng rất cao, hệ sinh thái lớn và phù hợp với hệ thống production quy mô lớn.
Nhược điểm: mô hình tính phí có nhiều thành phần; với một project nhỏ, việc sử dụng toàn bộ hệ sinh thái AWS có thể phức tạp hơn nhu cầu thực tế.
Đánh giá: là nền tảng object storage rất mạnh và phù hợp cho hệ thống lớn, nhưng chưa nhất thiết là lựa chọn tối ưu về độ đơn giản cho giai đoạn hiện tại.
### 2.6. Google Cloud Storage và Azure Blob Storage
Google Cloud Storage và Azure Blob Storage đều là các dịch vụ cloud object storage có khả năng mở rộng cao. Hai dịch vụ này phù hợp khi hệ thống đã sử dụng sâu hệ sinh thái Google Cloud hoặc Microsoft Azure tương ứng.
Ưu điểm: khả năng mở rộng cao, độ tin cậy tốt và tích hợp sâu với các dịch vụ cloud khác.
Nhược điểm: có thể tạo thêm độ phức tạp và chi phí quản trị nếu hệ thống không sử dụng các dịch vụ khác trong cùng hệ sinh thái.
Đánh giá: là các lựa chọn tốt về mặt kỹ thuật nhưng không phải ưu tiên đầu tiên đối với hệ thống hiện tại.
## 3. So sánh tổng quan

| Giải pháp | Chi phí ban đầu | Khả năng mở rộng | Tự vận hành | S3-compatible | Độ phức tạp | Đánh giá |
|---|---|---|---|---|---|---|
| Filesystem VPS | Thấp | Thấp | Cao | Không | Thấp | Chỉ phù hợp giai đoạn nhỏ |
| MinIO | Thấp | Trung bình–Cao | Cao | Có | Trung bình–Cao | Tốt nếu muốn tự quản lý |
| Cloudflare R2 | Thấp/miễn phí ở quy mô nhỏ | Rất cao | Thấp | Có | Thấp | Phù hợp nhất |
| Backblaze B2 | Thấp/miễn phí ở quy mô nhỏ | Rất cao | Thấp | Có | Thấp | Rất phù hợp |
| Amazon S3 | Thấp theo mức sử dụng | Rất cao | Thấp | Là chuẩn S3 | Trung bình | Rất mạnh cho production |
| Google Cloud Storage | Theo mức sử dụng | Rất cao | Thấp | Tương thích qua công cụ/API | Trung bình | Tốt nếu dùng Google Cloud |
| Azure Blob Storage | Theo mức sử dụng | Rất cao | Thấp | Tương thích qua công cụ/API | Trung bình | Tốt nếu dùng Azure |

## 4. Amazon S3 là gì và có chức năng gì?
S3 là viết tắt của Simple Storage Service, dịch vụ object storage của Amazon Web Services (AWS). Khác với việc lưu file trực tiếp trên filesystem của một server, object storage lưu dữ liệu dưới dạng object trong các bucket. Mỗi object có dữ liệu và một key dùng để xác định vị trí logic của object.
Ví dụ, một file MP3 có thể được lưu với key:
`submissions/2026/08/assignment-123/student-456/speaking.mp3`
S3 cung cấp các chức năng quan trọng như lưu trữ và truy xuất object, kiểm soát quyền truy cập, presigned URL để cho phép client upload/download trực tiếp trong một khoảng thời gian giới hạn, versioning, lifecycle để tự động quản lý dữ liệu theo thời gian, mã hóa và khả năng tích hợp với các dịch vụ khác.
Khái niệm S3-compatible có nghĩa là một dịch vụ khác cung cấp API và mô hình thao tác tương thích với S3. Vì vậy, ứng dụng có thể sử dụng các thư viện và cách tiếp cận phổ biến của hệ sinh thái S3 mà không nhất thiết phải lưu dữ liệu trên Amazon S3.
## 5. Kiến trúc lưu trữ đề xuất
Đối với hệ thống này, Spring Boot nên quản lý nghiệp vụ, xác thực, phân quyền và metadata của file; object storage nên chịu trách nhiệm lưu binary file. Không nên để VPS trở thành file server cho toàn bộ lưu lượng upload/download.
**Client → Spring Boot → tạo presigned URL → Client upload trực tiếp lên Cloudflare R2**
Database chỉ lưu metadata, ví dụ tên file, content type, kích thước, storage key, người nộp, bài tập và thời gian upload. File thực tế được lưu trên R2. Cách thiết kế này giúp giảm tải cho VPS và cho phép storage mở rộng độc lập với application server.
## 6. Lý do lựa chọn Cloudflare R2
Cloudflare R2 được lựa chọn làm giải pháp lưu trữ file cho hệ thống vì đáp ứng tốt cả yêu cầu hiện tại lẫn định hướng phát triển trong tương lai.
Tương thích với S3: R2 cung cấp API tương thích S3, giúp ứng dụng sử dụng các thư viện, khái niệm bucket, object, key và presigned URL phổ biến trong hệ sinh thái S3.
Chi phí ban đầu thấp: khi hệ thống còn nhỏ, mức miễn phí của R2 có thể đáp ứng nhu cầu lưu trữ và số lượng thao tác ở quy mô ban đầu, giúp giảm chi phí triển khai.
Khả năng mở rộng: khi số lượng học sinh, bài tập và file audio tăng, có thể tiếp tục sử dụng cùng một kiến trúc và trả chi phí theo mức sử dụng thay vì phải chuyển sang một hệ thống storage mới.
Giảm tải cho VPS: client có thể upload/download trực tiếp với R2 thông qua presigned URL, giúp Spring Boot tập trung xử lý nghiệp vụ.
Phù hợp với dữ liệu của hệ thống: R2 có thể lưu PDF, TXT, DOCX, MP3, WAV và các định dạng file khác dưới dạng object.
## 7. Kết luận
Sau khi so sánh các phương án lưu trữ, Cloudflare R2 là lựa chọn phù hợp nhất đối với hệ thống giao và nộp bài tập tiếng Anh ở giai đoạn hiện tại. Giải pháp này cho phép hệ thống bắt đầu với chi phí rất thấp khi quy mô còn nhỏ, đồng thời không giới hạn kiến trúc theo mô hình VPS filesystem.
Một ưu điểm quan trọng là Cloudflare R2 tương thích với S3. S3 là một chuẩn và hệ sinh thái object storage phổ biến, cung cấp các cơ chế lưu trữ, truy xuất, phân quyền và quản lý object. Việc sử dụng R2 theo API tương thích S3 giúp hệ thống có tính linh hoạt cao và giảm rủi ro phụ thuộc vào một cách triển khai storage riêng biệt.
Do đó, phương án được lựa chọn là sử dụng Cloudflare R2 làm object storage, PostgreSQL để lưu metadata và Spring Boot trên VPS để xử lý API và nghiệp vụ. Khi hệ thống phát triển, chi phí R2 có thể tăng theo mức sử dụng, nhưng kiến trúc không cần thay đổi đáng kể. Đây là sự đánh đổi phù hợp: chấp nhận phát sinh chi phí khi hệ thống lớn hơn để đổi lấy khả năng mở rộng, tính ổn định và giảm công sức vận hành storage.
## 8. Kiến trúc tổng quát được đề xuất
```text
Frontend / Mobile / Client
↓
Spring Boot API trên VPS
├── Authentication / Authorization
├── Assignment / Submission / Grading
├── File metadata → PostgreSQL
└── Presigned URL
↓
Cloudflare R2
├── PDF / TXT / DOCX
├── MP3 / WAV
└── Các định dạng khác
```

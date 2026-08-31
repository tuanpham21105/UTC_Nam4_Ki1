# TÀI LIỆU ĐẶC TẢ CA SỬ DỤNG (USE CASE SPECIFICATION)
## Hệ thống quản lý bài tập và chấm chữa bài tiếng Anh thông minh

Tài liệu này mô tả chi tiết 36 ca sử dụng (Use Case) của hệ thống theo chuẩn định dạng yêu cầu.

---

### 1. Đăng nhập
1. **Tên ca sử dụng:** Đăng nhập
2. **Các tác nhân liên hệ:** Quản lý (Admin), Giáo viên (Teacher), Học viên (Student)
3. **Mục tiêu (Goal):** Cho phép người dùng truy cập vào hệ thống với quyền hạn tương ứng của mình.
4. **Mô tả tổng quan (Overview):** Người dùng nhập tài khoản và mật khẩu; hệ thống xác thực thông tin, kiểm tra trạng thái và cấp quyền truy cập.
5. **Các yêu cầu tham khảo (Cross-reference):** Yêu cầu bảo mật mật khẩu, hệ thống phân quyền.
6. **Tiền điều kiện (Pre-condition):** Người dùng đã có tài khoản hợp lệ trên hệ thống.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Người dùng truy cập trang Đăng nhập.
   - Bước 2: Người dùng nhập Tên đăng nhập/Email và Mật khẩu.
   - Bước 3: Người dùng nhấn nút "Đăng nhập".
   - Bước 4: Hệ thống kiểm tra thông tin đối chiếu với cơ sở dữ liệu.
   - Bước 5: Hệ thống xác định vai trò và chuyển hướng người dùng đến trang chủ (Dashboard) tương ứng.
8. **Các dòng sự kiện phụ (Alternative courses):**
   - *Sai thông tin:* Hệ thống hiển thị lỗi "Tên đăng nhập hoặc mật khẩu không đúng".
   - *Tài khoản bị khóa:* Hệ thống thông báo "Tài khoản đang bị vô hiệu hóa".
9. **Hậu điều kiện (Post-condition):** Phiên làm việc (session) của người dùng được tạo, người dùng có thể sử dụng các chức năng theo quyền hạn.

---

### 2. Đăng xuất
1. **Tên ca sử dụng:** Đăng xuất
2. **Các tác nhân liên hệ:** Quản lý, Giáo viên, Học viên
3. **Mục tiêu (Goal):** Kết thúc phiên làm việc an toàn.
4. **Mô tả tổng quan (Overview):** Người dùng click đăng xuất để xóa phiên đăng nhập hiện tại.
5. **Các yêu cầu tham khảo (Cross-reference):** UC1 (Đăng nhập).
6. **Tiền điều kiện (Pre-condition):** Người dùng đang đăng nhập vào hệ thống.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Người dùng chọn chức năng "Đăng xuất" trên menu.
   - Bước 2: Hệ thống hủy bỏ phiên làm việc (xóa token/session).
   - Bước 3: Hệ thống chuyển hướng người dùng về trang Đăng nhập.
8. **Các dòng sự kiện phụ (Alternative courses):** Không có.
9. **Hậu điều kiện (Post-condition):** Người dùng không thể truy cập các trang yêu cầu đăng nhập nếu không thực hiện đăng nhập lại.

---

### 3. Quản lý tài khoản cá nhân
1. **Tên ca sử dụng:** Quản lý tài khoản cá nhân
2. **Các tác nhân liên hệ:** Quản lý, Giáo viên, Học viên
3. **Mục tiêu (Goal):** Cập nhật thông tin hồ sơ và bảo mật cá nhân.
4. **Mô tả tổng quan (Overview):** Xem, chỉnh sửa thông tin cá nhân (tên, SĐT, avatar) và đổi mật khẩu.
5. **Các yêu cầu tham khảo (Cross-reference):** Không.
6. **Tiền điều kiện (Pre-condition):** Người dùng đã đăng nhập.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Người dùng truy cập trang "Hồ sơ cá nhân".
   - Bước 2: Hệ thống hiển thị thông tin hiện tại.
   - Bước 3: Người dùng nhập các thay đổi (thông tin hoặc mật khẩu mới).
   - Bước 4: Người dùng nhấn "Lưu thay đổi".
   - Bước 5: Hệ thống cập nhật DB và thông báo thành công.
8. **Các dòng sự kiện phụ (Alternative courses):**
   - *Mật khẩu cũ không đúng:* Yêu cầu nhập lại mật khẩu hiện tại.
   - *Dữ liệu không hợp lệ:* Báo lỗi format (VD: sai định dạng số điện thoại).
9. **Hậu điều kiện (Post-condition):** Thông tin người dùng được cập nhật mới nhất.

---

### 4. Quản lý tài khoản người dùng
1. **Tên ca sử dụng:** Quản lý tài khoản người dùng
2. **Các tác nhân liên hệ:** Quản lý (Admin)
3. **Mục tiêu (Goal):** Duy trì và kiểm soát danh sách tài khoản trong hệ thống.
4. **Mô tả tổng quan (Overview):** Admin thêm mới, chỉnh sửa, khóa/mở khóa tài khoản.
5. **Các yêu cầu tham khảo (Cross-reference):** UC5 (Phân quyền).
6. **Tiền điều kiện (Pre-condition):** Đăng nhập với quyền Quản lý.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Quản lý chọn menu "Quản lý tài khoản".
   - Bước 2: Hệ thống hiển thị danh sách tài khoản.
   - Bước 3: Quản lý chọn Thêm/Sửa/Khóa tài khoản.
   - Bước 4: Quản lý nhập thông tin và xác nhận.
   - Bước 5: Hệ thống lưu thay đổi và cập nhật danh sách.
8. **Các dòng sự kiện phụ (Alternative courses):**
   - *Trùng email:* Hệ thống báo lỗi "Email đã tồn tại".
9. **Hậu điều kiện (Post-condition):** Danh sách tài khoản và trạng thái được cập nhật.

---

### 5. Phân quyền người dùng
1. **Tên ca sử dụng:** Phân quyền người dùng
2. **Các tác nhân liên hệ:** Quản lý
3. **Mục tiêu (Goal):** Gán đúng vai trò (Role) cho tài khoản để đảm bảo tính bảo mật.
4. **Mô tả tổng quan (Overview):** Cài đặt quyền (Admin, Teacher, Student) cho các tài khoản.
5. **Các yêu cầu tham khảo (Cross-reference):** UC4.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Quản lý, tài khoản cần phân quyền đã tồn tại.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Quản lý chọn tài khoản cần phân quyền.
   - Bước 2: Chọn chức năng "Chỉnh sửa vai trò".
   - Bước 3: Chọn vai trò từ danh sách (Giáo viên, Học viên...).
   - Bước 4: Nhấn "Lưu". Hệ thống cập nhật quyền cho tài khoản.
8. **Các dòng sự kiện phụ (Alternative courses):** Không có.
9. **Hậu điều kiện (Post-condition):** Tài khoản sở hữu các chức năng tương ứng với quyền mới gán.

---

### 6. Quản lý giáo viên
1. **Tên ca sử dụng:** Quản lý giáo viên
2. **Các tác nhân liên hệ:** Quản lý
3. **Mục tiêu (Goal):** Quản lý hồ sơ chuyên môn và phân công giảng dạy.
4. **Mô tả tổng quan (Overview):** Xem, thêm, cập nhật hồ sơ giáo viên và trạng thái làm việc.
5. **Các yêu cầu tham khảo (Cross-reference):** UC4, UC8.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Quản lý.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Truy cập "Quản lý giáo viên".
   - Bước 2: Xem danh sách và thông tin (trình độ, chứng chỉ).
   - Bước 3: Thêm mới hoặc cập nhật thông tin giáo viên.
   - Bước 4: Lưu dữ liệu.
8. **Các dòng sự kiện phụ (Alternative courses):** Hủy thao tác (quay về màn hình trước).
9. **Hậu điều kiện (Post-condition):** Danh sách hồ sơ giáo viên được đồng bộ.

---

### 7. Quản lý học viên
1. **Tên ca sử dụng:** Quản lý học viên
2. **Các tác nhân liên hệ:** Quản lý
3. **Mục tiêu (Goal):** Kiểm soát thông tin đầu vào và trạng thái học tập của học viên.
4. **Mô tả tổng quan (Overview):** Quản lý hồ sơ cá nhân, trình độ đầu vào của học viên.
5. **Các yêu cầu tham khảo (Cross-reference):** UC4, UC9.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Quản lý.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Truy cập "Quản lý học viên".
   - Bước 2: Thêm học viên đơn lẻ hoặc import từ file Excel.
   - Bước 3: Cập nhật thông tin (Tên, tuổi, số điện thoại phụ huynh).
   - Bước 4: Hệ thống lưu và tạo hồ sơ.
8. **Các dòng sự kiện phụ (Alternative courses):**
   - *Import lỗi:* File Excel sai format, hệ thống báo lỗi các dòng cụ thể.
9. **Hậu điều kiện (Post-condition):** Học viên có hồ sơ lưu trữ trên hệ thống.

---

### 8. Quản lý lớp học
1. **Tên ca sử dụng:** Quản lý lớp học
2. **Các tác nhân liên hệ:** Quản lý
3. **Mục tiêu (Goal):** Tổ chức các lớp học và phân công giáo viên.
4. **Mô tả tổng quan (Overview):** Tạo lớp, đóng/mở lớp, gán giáo viên phụ trách.
5. **Các yêu cầu tham khảo (Cross-reference):** UC6.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Quản lý.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Chọn "Quản lý lớp học" -> "Tạo mới".
   - Bước 2: Nhập Tên lớp, Trình độ, Thời gian học.
   - Bước 3: Chọn giáo viên phụ trách từ danh sách.
   - Bước 4: Nhấn "Lưu".
8. **Các dòng sự kiện phụ (Alternative courses):** Đóng lớp học khi đã hoàn thành khóa học.
9. **Hậu điều kiện (Post-condition):** Lớp học được tạo, sẵn sàng để thêm học viên.

---

### 9. Quản lý thành viên lớp học
1. **Tên ca sử dụng:** Quản lý thành viên lớp học
2. **Các tác nhân liên hệ:** Quản lý, Giáo viên
3. **Mục tiêu (Goal):** Sắp xếp học viên vào đúng lớp.
4. **Mô tả tổng quan (Overview):** Thêm, xóa, hoặc chuyển lớp cho học viên.
5. **Các yêu cầu tham khảo (Cross-reference):** UC7, UC8.
6. **Tiền điều kiện (Pre-condition):** Lớp học và hồ sơ học viên đã tồn tại.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Truy cập chi tiết Lớp học -> "Thành viên".
   - Bước 2: Nhấn "Thêm học viên". Chọn từ danh sách có sẵn.
   - Bước 3: Nhấn xác nhận.
   - Bước 4: Hệ thống thêm học viên vào lớp.
8. **Các dòng sự kiện phụ (Alternative courses):** Chuyển học viên từ lớp A sang lớp B.
9. **Hậu điều kiện (Post-condition):** Danh sách (sĩ số) của lớp học được cập nhật.

---

### 10. Xem danh sách lớp học
1. **Tên ca sử dụng:** Xem danh sách lớp học
2. **Các tác nhân liên hệ:** Giáo viên, Học viên
3. **Mục tiêu (Goal):** Biết được các lớp mình đang giảng dạy hoặc tham gia.
4. **Mô tả tổng quan (Overview):** Hiển thị danh sách các lớp học được phân công.
5. **Các yêu cầu tham khảo (Cross-reference):** UC8, UC9.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Teacher hoặc Student.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Truy cập menu "Lớp học của tôi".
   - Bước 2: Hệ thống truy xuất DB và hiển thị danh sách lớp (Tên lớp, sĩ số, lịch học).
8. **Các dòng sự kiện phụ (Alternative courses):** Nếu chưa có lớp, hệ thống thông báo "Bạn chưa tham gia lớp học nào".
9. **Hậu điều kiện (Post-condition):** Người dùng thấy được lớp học tương ứng.

---

### 11. Quản lý bài tập
1. **Tên ca sử dụng:** Quản lý bài tập
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Kiểm soát thư viện bài tập của lớp.
4. **Mô tả tổng quan (Overview):** Xem, tìm kiếm, chỉnh sửa, xóa bài tập đã tạo.
5. **Các yêu cầu tham khảo (Cross-reference):** UC12.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Teacher.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Vào mục "Bài tập" của lớp.
   - Bước 2: Hệ thống hiển thị danh sách bài tập.
   - Bước 3: Sử dụng bộ lọc (kỹ năng, trạng thái) để tìm kiếm.
   - Bước 4: Chọn Sửa hoặc Xóa bài tập.
8. **Các dòng sự kiện phụ (Alternative courses):** Sao chép bài tập sang lớp khác.
9. **Hậu điều kiện (Post-condition):** Thư viện bài tập được cập nhật/sắp xếp.

---

### 12. Tạo bài tập
1. **Tên ca sử dụng:** Tạo bài tập
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Xây dựng nội dung bài kiểm tra/luyện tập cho học viên.
4. **Mô tả tổng quan (Overview):** Thiết lập câu hỏi, đáp án, chọn kỹ năng, thời gian làm bài, điểm số.
5. **Các yêu cầu tham khảo (Cross-reference):** UC11.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Teacher.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Chọn "Tạo bài tập mới".
   - Bước 2: Nhập Tiêu đề, Chọn loại kỹ năng (Nghe/Nói/Đọc/Viết).
   - Bước 3: Soạn thảo nội dung (tải file Audio, tạo câu hỏi trắc nghiệm, tạo đề tự luận).
   - Bước 4: Nhập đáp án chuẩn và thiết lập điểm.
   - Bước 5: Lưu lại dưới dạng bản nháp hoặc xuất bản luôn.
8. **Các dòng sự kiện phụ (Alternative courses):** Import ngân hàng câu hỏi từ file Excel/Word.
9. **Hậu điều kiện (Post-condition):** Bài tập được lưu vào cơ sở dữ liệu hệ thống.

---

### 13. Mở bài tập
1. **Tên ca sử dụng:** Mở bài tập
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Giao bài cho học viên bắt đầu làm.
4. **Mô tả tổng quan (Overview):** Đổi trạng thái bài tập sang "Đang mở", thiết lập deadline.
5. **Các yêu cầu tham khảo (Cross-reference):** UC12, UC15.
6. **Tiền điều kiện (Pre-condition):** Bài tập đã được tạo xong nội dung.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Chọn bài tập trong danh sách.
   - Bước 2: Chọn "Giao bài / Mở bài".
   - Bước 3: Thiết lập thời gian bắt đầu và hạn chót (Deadline).
   - Bước 4: Xác nhận. Hệ thống gửi thông báo cho học viên.
8. **Các dòng sự kiện phụ (Alternative courses):** Hẹn giờ mở bài tự động.
9. **Hậu điều kiện (Post-condition):** Học viên có thể nhìn thấy và truy cập làm bài tập.

---

### 14. Khóa bài tập
1. **Tên ca sử dụng:** Khóa bài tập
2. **Các tác nhân liên hệ:** Giáo viên (hoặc hệ thống tự động)
3. **Mục tiêu (Goal):** Dừng việc nhận bài làm mới từ học viên.
4. **Mô tả tổng quan (Overview):** Vô hiệu hóa chức năng nộp bài khi hết hạn hoặc khi giáo viên chủ động khóa.
5. **Các yêu cầu tham khảo (Cross-reference):** UC13.
6. **Tiền điều kiện (Pre-condition):** Bài tập đang ở trạng thái Mở.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Giáo viên chọn bài tập đang mở.
   - Bước 2: Chọn chức năng "Khóa bài tập".
   - Bước 3: Hệ thống chặn nút nộp bài đối với học viên.
8. **Các dòng sự kiện phụ (Alternative courses):** Hệ thống tự động khóa khi tới thời điểm Deadline đã thiết lập.
9. **Hậu điều kiện (Post-condition):** Bài tập chuyển sang trạng thái "Đã đóng", học viên không thể nộp bài.

---

### 15. Xem bài tập
1. **Tên ca sử dụng:** Xem bài tập
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Nắm bắt yêu cầu và deadline của bài tập được giao.
4. **Mô tả tổng quan (Overview):** Học viên xem danh sách bài tập, trạng thái và yêu cầu chi tiết.
5. **Các yêu cầu tham khảo (Cross-reference):** UC13.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập quyền Student.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên truy cập "Bài tập của tôi".
   - Bước 2: Hệ thống hiển thị danh sách (Tiêu đề, Deadline, Trạng thái: Chưa làm, Đã nộp...).
   - Bước 3: Nhấn vào một bài tập để xem hướng dẫn chi tiết.
8. **Các dòng sự kiện phụ (Alternative courses):** Lọc bài tập theo kỹ năng (Nghe, Nói, Đọc, Viết).
9. **Hậu điều kiện (Post-condition):** Học viên hiểu yêu cầu và sẵn sàng làm bài.

---

### 16. Làm bài tập viết (Writing)
1. **Tên ca sử dụng:** Làm bài tập viết
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Hoàn thành phần trả lời cho kỹ năng Viết.
4. **Mô tả tổng quan (Overview):** Học viên đọc đề bài, soạn thảo nội dung trực tiếp trên web hoặc tải file lên.
5. **Các yêu cầu tham khảo (Cross-reference):** UC15, UC20, UC21.
6. **Tiền điều kiện (Pre-condition):** Bài tập Viết đang mở.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên mở bài tập Viết.
   - Bước 2: Đọc đề bài (Task 1, Task 2).
   - Bước 3: Nhập văn bản vào khung soạn thảo (có bộ đếm từ - word count).
   - Bước 4: Kiểm tra lại văn bản.
8. **Các dòng sự kiện phụ (Alternative courses):** Upload file (.docx, .pdf) thay vì gõ trực tiếp.
9. **Hậu điều kiện (Post-condition):** Nội dung viết được điền sẵn sàng để lưu nháp hoặc nộp.

---

### 17. Làm bài tập nói (Speaking)
1. **Tên ca sử dụng:** Làm bài tập nói
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Hoàn thành bài kiểm tra kỹ năng Nói.
4. **Mô tả tổng quan (Overview):** Ghi âm trực tiếp qua trình duyệt hoặc tải file âm thanh lên hệ thống.
5. **Các yêu cầu tham khảo (Cross-reference):** UC15, UC21.
6. **Tiền điều kiện (Pre-condition):** Bài tập Nói đang mở, thiết bị có Microphone hợp lệ.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Mở bài tập Nói, đọc/nghe câu hỏi.
   - Bước 2: Nhấn "Ghi âm" (Cấp quyền Micro cho trình duyệt).
   - Bước 3: Trả lời câu hỏi.
   - Bước 4: Nhấn "Dừng" và nghe lại đoạn ghi âm.
8. **Các dòng sự kiện phụ (Alternative courses):**
   - Tải lên tệp âm thanh có sẵn (.mp3, .wav).
   - Ghi âm lại nhiều lần trước khi nộp.
9. **Hậu điều kiện (Post-condition):** File âm thanh được tải lên máy chủ chờ nộp bài.

---

### 18. Làm bài tập đọc (Reading)
1. **Tên ca sử dụng:** Làm bài tập đọc
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Kiểm tra kỹ năng đọc hiểu.
4. **Mô tả tổng quan (Overview):** Đọc văn bản, trả lời các dạng câu hỏi trắc nghiệm, điền từ, True/False/Not Given.
5. **Các yêu cầu tham khảo (Cross-reference):** UC15, UC21.
6. **Tiền điều kiện (Pre-condition):** Bài tập Đọc đang mở.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Mở bài tập Đọc.
   - Bước 2: Đọc đoạn văn hiển thị bên trái màn hình.
   - Bước 3: Trả lời các câu hỏi tương ứng bên phải màn hình.
8. **Các dòng sự kiện phụ (Alternative courses):** Highlight (bôi màu) các đoạn văn bản nháp trực tiếp trên trình duyệt.
9. **Hậu điều kiện (Post-condition):** Các câu trả lời được chọn đầy đủ.

---

### 19. Làm bài tập nghe (Listening)
1. **Tên ca sử dụng:** Làm bài tập nghe
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Kiểm tra kỹ năng nghe hiểu âm thanh tiếng Anh.
4. **Mô tả tổng quan (Overview):** Học viên phát file audio và chọn/nhập đáp án cùng lúc.
5. **Các yêu cầu tham khảo (Cross-reference):** UC15, UC21.
6. **Tiền điều kiện (Pre-condition):** Bài tập Nghe đang mở, thiết bị có loa/tai nghe hoạt động.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Mở bài tập Nghe.
   - Bước 2: Nhấn nút "Play" (Phát) file Audio.
   - Bước 3: Nghe và chọn/điền đáp án vào các câu hỏi bên dưới.
8. **Các dòng sự kiện phụ (Alternative courses):** Giáo viên giới hạn số lần Play audio (vd: chỉ được nghe 1 lần) -> Hệ thống tự động disable nút Play sau khi hết lượt.
9. **Hậu điều kiện (Post-condition):** Câu trả lời được nhập đầy đủ, kết thúc thời gian nghe.

---

### 20. Kiểm tra và xem lại bài làm trước khi nộp
1. **Tên ca sử dụng:** Kiểm tra và xem lại bài làm trước khi nộp
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Đảm bảo học viên có thể kiểm tra toàn bộ bài làm, phát hiện phần thiếu/sai và chỉnh sửa trước khi gửi bài chính thức.
4. **Mô tả tổng quan (Overview):** Sau khi hoàn thành bài tập Nghe/Nói/Đọc/Viết, học viên truy cập màn hình kiểm tra, xem lại câu trả lời, kiểm tra các phần chưa hoàn thành và xác nhận bài đã sẵn sàng.
5. **Các yêu cầu tham khảo (Cross-reference):** UC16, UC17, UC18, UC19, UC21.
6. **Tiền điều kiện (Pre-condition):** Học viên đã đăng nhập, có quyền làm bài, bài tập đang mở và học viên đã có bài làm.
7. **Dòng sự kiện chính (Typical course of events):**
   Học viên chọn kiểm tra bài → hệ thống tải toàn bộ câu trả lời → hiển thị trạng thái từng câu → học viên kiểm tra → hệ thống cảnh báo phần chưa hoàn thành → học viên chỉnh sửa nếu cần → học viên xác nhận bài đã sẵn sàng → hệ thống chuyển sang bước nộp bài.
8. **Các dòng sự kiện phụ (Alternative courses):** Có câu chưa trả lời → cảnh báo; dữ liệu không hợp lệ → yêu cầu sửa; học viên quay lại làm bài → cho phép chỉnh sửa; bài hết hạn → không cho tiếp tục và xử lý theo chính sách.
9. **Hậu điều kiện (Post-condition):** Bài làm đã được học viên kiểm tra và xác nhận sẵn sàng để chuyển sang UC21 — Nộp bài tập.

---

### 21. Nộp bài tập
1. **Tên ca sử dụng:** Nộp bài tập
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Gửi bài làm lên hệ thống để chờ chấm điểm.
4. **Mô tả tổng quan (Overview):** Học viên xác nhận hoàn thành và nộp; hệ thống ghi nhận thời gian nộp.
5. **Các yêu cầu tham khảo (Cross-reference):** UC16-UC19, UC23.
6. **Tiền điều kiện (Pre-condition):** Đã hoàn thành các câu hỏi, bài tập chưa bị khóa.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên nhấn nút "Nộp bài".
   - Bước 2: Hệ thống hiển thị popup xác nhận "Bạn có chắc chắn muốn nộp bài?".
   - Bước 3: Học viên nhấn "Xác nhận".
   - Bước 4: Hệ thống ghi nhận trạng thái bài nộp, thời gian nộp và khóa bài làm của cá nhân học viên.
8. **Các dòng sự kiện phụ (Alternative courses):** Chưa làm hết câu hỏi -> Hệ thống cảnh báo "Bạn còn X câu chưa làm, vẫn tiếp tục nộp?".
9. **Hậu điều kiện (Post-condition):** Bài làm được gửi thành công, chuyển sang trạng thái "Chờ chấm".

---

### 22. Quản lý lần làm và nộp lại bài
1. **Tên ca sử dụng:** Quản lý lần làm và nộp lại bài
2. **Các tác nhân liên hệ:** Học viên, Giáo viên
3. **Mục tiêu (Goal):** Cho phép làm lại bài nếu chính sách bài tập cho phép.
4. **Mô tả tổng quan (Overview):** Hệ thống kiểm tra số lần được phép làm; học viên có thể nộp lại.
5. **Các yêu cầu tham khảo (Cross-reference):** UC21.
6. **Tiền điều kiện (Pre-condition):** Bài tập được thiết lập số lần làm > 1 và chưa quá hạn.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên đã nộp bài lần 1.
   - Bước 2: Học viên vào lại bài tập, thấy nút "Làm lại bài (Còn x lượt)".
   - Bước 3: Chọn "Làm lại", hệ thống tạo form bài làm mới trắng trơn.
   - Bước 4: Thực hiện và nộp bài lần 2.
8. **Các dòng sự kiện phụ (Alternative courses):** Hết số lượt làm lại -> Nút bị ẩn.
9. **Hậu điều kiện (Post-condition):** Lưu trữ lịch sử tất cả các lần nộp của học viên.

---

### 23. Xem trạng thái bài làm
1. **Tên ca sử dụng:** Xem trạng thái bài làm
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Theo dõi tiến trình bài tập của mình.
4. **Mô tả tổng quan (Overview):** Cập nhật hiển thị (Chưa làm, Đã nộp, Đang chấm, Đã chấm).
5. **Các yêu cầu tham khảo (Cross-reference):** UC21, UC24.
6. **Tiền điều kiện (Pre-condition):** Đã được giao bài tập.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên truy cập trang tổng quan (Dashboard) hoặc danh sách bài tập.
   - Bước 2: Hệ thống hiển thị nhãn trạng thái (Status tag) màu sắc bên cạnh mỗi bài tập.
8. **Các dòng sự kiện phụ (Alternative courses):** Không có.
9. **Hậu điều kiện (Post-condition):** Học viên biết rõ bài nào cần làm, bài nào đã có kết quả.

---

### 24. Chấm bài
1. **Tên ca sử dụng:** Chấm bài
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Đánh giá năng lực của học viên và trả kết quả.
4. **Mô tả tổng quan (Overview):** Giáo viên xem bài làm, chấm điểm, nhận xét thủ công.
5. **Các yêu cầu tham khảo (Cross-reference):** UC21.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập Teacher, Học viên đã nộp bài.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Giáo viên chọn danh sách bài "Chờ chấm".
   - Bước 2: Mở bài làm của 1 học viên.
   - Bước 3: Đọc/nghe bài làm, nhập điểm cho từng câu/phần.
   - Bước 4: Ghi chú, comment nhận xét vào trực tiếp bài làm (inline comment) hoặc nhận xét chung.
   - Bước 5: Nhấn "Xác nhận & Trả bài".
8. **Các dòng sự kiện phụ (Alternative courses):** Lưu nháp kết quả chấm để xem lại sau.
9. **Hậu điều kiện (Post-condition):** Trạng thái bài làm đổi thành "Đã chấm", học viên nhận được thông báo có điểm.

---

### 25. Hỗ trợ chấm chữa bài bằng AI (Chung)
1. **Tên ca sử dụng:** Hỗ trợ chấm chữa bài bằng AI
2. **Các tác nhân liên hệ:** Giáo viên, Hệ thống AI
3. **Mục tiêu (Goal):** Tối ưu hóa thời gian chấm bài cho giáo viên, đưa ra đánh giá khách quan.
4. **Mô tả tổng quan (Overview):** Gọi API AI để phân tích bài làm, đề xuất lỗi và điểm, sau đó giáo viên duyệt lại.
5. **Các yêu cầu tham khảo (Cross-reference):** UC26, UC27.
6. **Tiền điều kiện (Pre-condition):** Hệ thống được tích hợp module AI hoạt động ổn định.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Giáo viên mở bài làm của học viên.
   - Bước 2: Nhấn "Gợi ý chấm bằng AI".
   - Bước 3: Hệ thống gửi dữ liệu lên AI model, chờ trả kết quả.
   - Bước 4: Hệ thống hiển thị điểm đề xuất, các lỗi highlight trên giao diện.
   - Bước 5: Giáo viên kiểm tra, có thể sửa lại điểm AI đề xuất nếu thấy chưa phù hợp.
   - Bước 6: Xác nhận kết quả cuối.
8. **Các dòng sự kiện phụ (Alternative courses):** Lỗi kết nối AI -> Báo lỗi "Hệ thống AI đang gián đoạn, vui lòng chấm thủ công".
9. **Hậu điều kiện (Post-condition):** Bài làm có kết quả đánh giá sơ bộ từ AI được lưu lại.

---

### 26. Hỗ trợ chấm chữa bài viết bằng AI
1. **Tên ca sử dụng:** Hỗ trợ chấm chữa bài viết bằng AI
2. **Các tác nhân liên hệ:** Giáo viên, AI
3. **Mục tiêu (Goal):** Phát hiện lỗi ngữ pháp, từ vựng và cấu trúc bài viết tự động.
4. **Mô tả tổng quan (Overview):** AI đánh giá Writing theo các tiêu chí (Grammar, Lexical Resource, Coherence, Task Achievement).
5. **Các yêu cầu tham khảo (Cross-reference):** UC25.
6. **Tiền điều kiện (Pre-condition):** Bài làm thuộc kỹ năng Viết.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Gửi text bài viết lên AI.
   - Bước 2: AI trả về kết quả bôi đỏ (lỗi chính tả), bôi vàng (lỗi ngữ pháp), bôi xanh lam (từ vựng chưa hay).
   - Bước 3: AI đề xuất cách sửa (Rewrite suggestion) cho từng câu lỗi.
   - Bước 4: AI tổng hợp điểm từng tiêu chí.
   - Bước 5: Giáo viên duyệt, chọn Accept (chấp nhận) hoặc Reject (từ chối) gợi ý của AI.
8. **Các dòng sự kiện phụ (Alternative courses):** Bài viết phát hiện đạo văn (Plagiarism) -> AI cảnh báo đỏ 100%.
9. **Hậu điều kiện (Post-condition):** Bài viết có đầy đủ chú thích lỗi và điểm chi tiết.

---

### 27. Hỗ trợ chấm chữa bài nói bằng AI
1. **Tên ca sử dụng:** Hỗ trợ chấm chữa bài nói bằng AI
2. **Các tác nhân liên hệ:** Giáo viên, AI
3. **Mục tiêu (Goal):** Đánh giá phát âm, độ lưu loát của giọng nói tự động.
4. **Mô tả tổng quan (Overview):** AI chuyển Speech-to-text và phân tích phát âm (Pronunciation), độ trôi chảy (Fluency).
5. **Các yêu cầu tham khảo (Cross-reference):** UC25.
6. **Tiền điều kiện (Pre-condition):** Bài làm thuộc kỹ năng Nói, chất lượng âm thanh rõ.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Hệ thống gửi file Audio lên AI.
   - Bước 2: AI bóc băng (Transcript) đoạn nói của học viên.
   - Bước 3: Phân tích các từ phát âm sai (mispronounced words) hiển thị màu đỏ trên Transcript.
   - Bước 4: Đánh giá nhịp điệu (Intonation) và độ ngập ngừng. Đề xuất điểm.
   - Bước 5: Giáo viên nghe lại và chốt điểm cuối.
8. **Các dòng sự kiện phụ (Alternative courses):** Tạp âm quá ồn -> AI trả về cảnh báo "Chất lượng âm thanh quá thấp không thể phân tích".
9. **Hậu điều kiện (Post-condition):** Đánh giá kỹ năng Speaking hoàn tất.

---

### 28. Chấm bài đọc (Reading)
1. **Tên ca sử dụng:** Chấm bài đọc
2. **Các tác nhân liên hệ:** Hệ thống, Giáo viên
3. **Mục tiêu (Goal):** Tự động chấm điểm khách quan.
4. **Mô tả tổng quan (Overview):** Hệ thống so khớp đáp án của học viên với đáp án gốc (Answer key) của giáo viên.
5. **Các yêu cầu tham khảo (Cross-reference):** UC18.
6. **Tiền điều kiện (Pre-condition):** Đã khai báo đáp án đúng khi tạo bài.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên nộp bài Đọc.
   - Bước 2: Hệ thống tự động so sánh từng câu trả lời.
   - Bước 3: Tính tổng số câu đúng và quy đổi ra điểm hệ 10 hoặc hệ IELTS/TOEIC.
   - Bước 4: Lưu điểm lập tức vào hệ thống.
8. **Các dòng sự kiện phụ (Alternative courses):** Có câu trả lời dạng điền từ (tương đối đúng) -> Hệ thống gán trạng thái "Cần giáo viên duyệt lại".
9. **Hậu điều kiện (Post-condition):** Điểm số phần Reading được xác định.

---

### 29. Chấm bài nghe (Listening)
1. **Tên ca sử dụng:** Chấm bài nghe
2. **Các tác nhân liên hệ:** Hệ thống, Giáo viên
3. **Mục tiêu (Goal):** Tự động hóa chấm điểm nghe.
4. **Mô tả tổng quan (Overview):** Tương tự bài đọc, hệ thống đối chiếu và tính điểm tự động.
5. **Các yêu cầu tham khảo (Cross-reference):** UC19.
6. **Tiền điều kiện (Pre-condition):** Khai báo sẵn Answer key.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên nộp bài Nghe.
   - Bước 2: Hệ thống tự động chấm câu trắc nghiệm, điền từ (khớp chính xác).
   - Bước 3: Đưa ra điểm tự động.
8. **Các dòng sự kiện phụ (Alternative courses):** Nếu học viên điền sai viết hoa/thường nhưng đúng từ -> Cấu hình cho phép tính là Đúng.
9. **Hậu điều kiện (Post-condition):** Điểm Listening được ghi nhận.

---

### 30. Xem điểm số
1. **Tên ca sử dụng:** Xem điểm số
2. **Các tác nhân liên hệ:** Học viên, Giáo viên
3. **Mục tiêu (Goal):** Nắm bắt điểm số của các bài tập đã nộp.
4. **Mô tả tổng quan (Overview):** Hiển thị điểm thi/điểm bài tập trực quan.
5. **Các yêu cầu tham khảo (Cross-reference):** Không.
6. **Tiền điều kiện (Pre-condition):** Bài đã được chấm và công bố điểm.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Truy cập mục "Bảng điểm / Kết quả".
   - Bước 2: Hệ thống hiển thị danh sách bài tập kèm điểm số đạt được, điểm tối đa.
8. **Các dòng sự kiện phụ (Alternative courses):** Không.
9. **Hậu điều kiện (Post-condition):** Thông tin điểm số minh bạch đến người dùng.

---

### 31. Quản lý điểm số
1. **Tên ca sử dụng:** Quản lý điểm số
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Quản lý sổ điểm của cả lớp.
4. **Mô tả tổng quan (Overview):** Xem bảng điểm tổng hợp, cập nhật, chỉnh sửa điểm nếu có sai sót.
5. **Các yêu cầu tham khảo (Cross-reference):** UC30.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập Teacher.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Vào mục "Quản lý điểm số" của 1 lớp.
   - Bước 2: Hệ thống hiển thị bảng Matrix (Học viên x Bài tập).
   - Bước 3: Giáo viên có thể click vào ô điểm để sửa thủ công (kèm lý do sửa).
   - Bước 4: Lưu thay đổi và xuất file Excel nếu cần.
8. **Các dòng sự kiện phụ (Alternative courses):** Không.
9. **Hậu điều kiện (Post-condition):** Sổ điểm chuẩn xác được lưu vết (audit log).

---

### 32. Xem kết quả học tập
1. **Tên ca sử dụng:** Xem kết quả học tập
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Nhìn nhận bức tranh toàn cảnh về tiến bộ cá nhân.
4. **Mô tả tổng quan (Overview):** Xem biểu đồ, điểm trung bình, xếp hạng trong lớp.
5. **Các yêu cầu tham khảo (Cross-reference):** UC30.
6. **Tiền điều kiện (Pre-condition):** Có dữ liệu điểm số hệ thống.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên chọn "Tiến độ học tập".
   - Bước 2: Hệ thống render biểu đồ radar (4 kỹ năng Nghe-Nói-Đọc-Viết) thể hiện điểm yếu/mạnh.
   - Bước 3: Xem điểm trung bình toàn khóa.
8. **Các dòng sự kiện phụ (Alternative courses):** Không.
9. **Hậu điều kiện (Post-condition):** Học viên có định hướng cải thiện việc học.

---

### 33. Xem bài chữa
1. **Tên ca sử dụng:** Xem bài chữa
2. **Các tác nhân liên hệ:** Học viên
3. **Mục tiêu (Goal):** Học hỏi từ các lỗi sai để rút kinh nghiệm.
4. **Mô tả tổng quan (Overview):** Học viên xem lại chi tiết bài làm, đọc comment nhận xét của giáo viên và AI.
5. **Các yêu cầu tham khảo (Cross-reference):** UC24, UC25.
6. **Tiền điều kiện (Pre-condition):** Bài đã được chấm.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Học viên click vào "Chi tiết bài chữa" của bài đã có điểm.
   - Bước 2: Hệ thống hiển thị bài làm kèm các đánh dấu màu sắc (highlight lỗi).
   - Bước 3: Đọc/nghe phần nhận xét (Feedback) từ giáo viên.
   - Bước 4: Xem đáp án chuẩn (Answer key/Sample answer).
8. **Các dòng sự kiện phụ (Alternative courses):** Không.
9. **Hậu điều kiện (Post-condition):** Học viên tiếp thu kiến thức sửa lỗi.

---

### 34. Xem và đánh giá kết quả học viên
1. **Tên ca sử dụng:** Xem và đánh giá kết quả học viên
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Đánh giá định kỳ năng lực từng cá nhân.
4. **Mô tả tổng quan (Overview):** Giáo viên xem hồ sơ tiến bộ của một học viên cụ thể và viết nhận xét cuối tháng/khóa.
5. **Các yêu cầu tham khảo (Cross-reference):** UC32.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập Teacher.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Chọn một học viên trong danh sách lớp.
   - Bước 2: Xem lịch sử làm bài, biểu đồ điểm số các kỹ năng của học viên đó.
   - Bước 3: Viết đánh giá (Report) tổng quan về thái độ và năng lực.
   - Bước 4: Gửi đánh giá cho học viên.
8. **Các dòng sự kiện phụ (Alternative courses):** Không.
9. **Hậu điều kiện (Post-condition):** Báo cáo đánh giá cá nhân được lưu vào hồ sơ học viên.

---

### 35. Theo dõi tiến độ học tập
1. **Tên ca sử dụng:** Theo dõi tiến độ học tập
2. **Các tác nhân liên hệ:** Giáo viên
3. **Mục tiêu (Goal):** Nhận diện tình hình chung của lớp, phát hiện học viên tụt hậu.
4. **Mô tả tổng quan (Overview):** Xem tỷ lệ nộp bài, biểu đồ điểm trung bình của cả lớp.
5. **Các yêu cầu tham khảo (Cross-reference):** Không.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập Teacher.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Truy cập "Báo cáo lớp học".
   - Bước 2: Hệ thống hiển thị Dashboard: Tỷ lệ hoàn thành bài tập (%), tỷ lệ học viên dưới trung bình.
   - Bước 3: Giáo viên dựa vào đó để điều chỉnh phương pháp dạy.
8. **Các dòng sự kiện phụ (Alternative courses):** Không.
9. **Hậu điều kiện (Post-condition):** Giáo viên có dữ liệu để quản trị chất lượng lớp học.

---

### 36. Xem báo cáo và thống kê học tập
1. **Tên ca sử dụng:** Xem báo cáo và thống kê học tập
2. **Các tác nhân liên hệ:** Quản lý (Admin), Giáo viên
3. **Mục tiêu (Goal):** Quản lý bức tranh tổng thể hoạt động của toàn trung tâm/nhiều lớp.
4. **Mô tả tổng quan (Overview):** Xuất báo cáo hiệu suất, thống kê số lượng bài được giao, điểm số trung bình các khóa.
5. **Các yêu cầu tham khảo (Cross-reference):** Không.
6. **Tiền điều kiện (Pre-condition):** Đăng nhập Admin hoặc Teacher.
7. **Dòng sự kiện chính (Typical course of events):**
   - Bước 1: Quản lý truy cập "Thống kê báo cáo".
   - Bước 2: Thiết lập bộ lọc (Từ tháng X đến tháng Y, theo chi nhánh/lớp/giáo viên).
   - Bước 3: Hệ thống xuất ra các báo cáo dạng số liệu và biểu đồ (KPI).
   - Bước 4: Nhấn "Xuất PDF/Excel" để tải báo cáo.
8. **Các dòng sự kiện phụ (Alternative courses):** Không có dữ liệu trong khoảng thời gian đã chọn -> Hiển thị "Không có dữ liệu".
9. **Hậu điều kiện (Post-condition):** Tải về được file báo cáo thống kê chính xác phục vụ cho việc vận hành trung tâm.

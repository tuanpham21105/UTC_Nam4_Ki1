# SƠ ĐỒ HOẠT ĐỘNG (ACTIVITY DIAGRAM) — PlantUML
## Hệ thống quản lý bài tập và chấm chữa bài tiếng Anh thông minh

Quy ước áp dụng cho toàn bộ 36 sơ đồ dưới đây:

- Vẽ bằng **PlantUML**, chỉ dùng ký hiệu UML chuẩn (start/stop, action, decision, swimlane), **không màu, không icon** (`skinparam monochrome true`).
- Mỗi sơ đồ có 2–3 **swimlane** (làn bơi) theo đúng các tác nhân được khai báo trong đặc tả use case, lane cuối luôn là **"Hệ thống"** (hoặc **"AI"** nếu use case có tác nhân Hệ thống AI riêng).
- Các khối hành động được viết bằng **ngôn ngữ tự nhiên**, bám sát **dòng sự kiện chính** và **dòng sự kiện phụ** trong `Detailed_UseCase_Specifications.md`.
- Khi hành động liên quan đến **thêm/sửa/xóa dữ liệu**, khối hành động ghi rõ **tên bảng** theo đúng `ERD_reference.md` (ví dụ: bảng `users`, `assignments`, `submissions`...). Các use case không có bảng tương ứng trong ERD (ví dụ quản lý phiên đăng nhập, xuất báo cáo PDF) sẽ **không** gán tên bảng, chỉ mô tả hành vi.
- Nhánh sự kiện phụ dạng "sai/không hợp lệ → nhập lại" được vẽ bằng vòng lặp `repeat / repeat while` (giống ảnh ví dụ: quay lại bước nhập trước đó). Nhánh sự kiện phụ dạng "kết thúc bằng thông báo" được vẽ bằng `if/else`.

---

## 1. Đăng nhập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Người dùng|
start
:Truy cập trang đăng nhập;
|Hệ thống|
:Hiển thị form đăng nhập;
|Người dùng|
repeat
  :Nhập tên đăng nhập/email và mật khẩu;
  :Nhấn nút "Đăng nhập";
  |Hệ thống|
  :Kiểm tra thông tin trong bảng "users";
  if (Tài khoản tồn tại và đúng mật khẩu?) then (Không)
    :Hiển thị lỗi "Tên đăng nhập hoặc mật khẩu không đúng";
    |Người dùng|
  else (Có)
    |Hệ thống|
    if (Trạng thái tài khoản đang bị khóa?) then (Có)
      :Thông báo "Tài khoản đang bị vô hiệu hóa";
      |Người dùng|
    else (Không)
      break
    endif
  endif
repeat while (Đăng nhập thất bại?) is (Có) not (Không)
:Xác định vai trò (role) của tài khoản;
:Tạo phiên làm việc (session) cho người dùng;
:Chuyển hướng đến trang chủ (Dashboard) tương ứng vai trò;
|Người dùng|
stop
@enduml
```

---

## 2. Đăng xuất

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Người dùng|
start
:Chọn chức năng "Đăng xuất" trên menu;
|Hệ thống|
:Hủy bỏ phiên làm việc hiện tại (xóa token/session);
:Chuyển hướng người dùng về trang Đăng nhập;
|Người dùng|
stop
@enduml
```

---

## 3. Quản lý tài khoản cá nhân

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Người dùng|
start
:Truy cập trang "Hồ sơ cá nhân";
|Hệ thống|
:Truy vấn và hiển thị thông tin hiện tại từ bảng "users"
(và "teacher_profiles"/"student_profiles" nếu có);
|Người dùng|
repeat
  :Nhập thông tin cần thay đổi (họ tên, SĐT, avatar)
  hoặc mật khẩu mới;
  :Nhấn "Lưu thay đổi";
  |Hệ thống|
  if (Dữ liệu hợp lệ và mật khẩu hiện tại đúng?) then (Không)
    if (Sai mật khẩu hiện tại?) then (Có)
      :Yêu cầu nhập lại mật khẩu hiện tại;
    else (Không)
      :Báo lỗi định dạng dữ liệu (VD: sai SĐT);
    endif
    |Người dùng|
  else (Có)
    :Cập nhật bản ghi tương ứng trong bảng "users";
    :Thông báo cập nhật thành công;
    break
  endif
repeat while (Còn lỗi?) is (Có) not (Không)
|Người dùng|
stop
@enduml
```

---

## 4. Quản lý tài khoản người dùng

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý|
start
:Chọn menu "Quản lý tài khoản";
|Hệ thống|
:Truy vấn và hiển thị danh sách tài khoản từ bảng "users";
|Quản lý|
:Chọn thao tác Thêm/Sửa/Khóa-Mở khóa tài khoản;
:Nhập thông tin tài khoản và xác nhận;
|Hệ thống|
if (Thao tác là "Thêm mới"?) then (Có)
  if (Email đã tồn tại trong bảng "users"?) then (Có)
    :Báo lỗi "Email đã tồn tại";
    |Quản lý|
    stop
  else (Không)
    :Thêm bản ghi mới vào bảng "users"
    (và bảng "teacher_profiles"/"student_profiles" tương ứng "role");
  endif
else (Không)
  :Cập nhật thông tin hoặc trường "status"
  (ACTIVE/LOCKED) của bản ghi trong bảng "users";
endif
:Lưu thay đổi và cập nhật danh sách tài khoản;
|Quản lý|
stop
@enduml
```

---

## 5. Phân quyền người dùng

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý|
start
:Chọn tài khoản cần phân quyền;
:Chọn chức năng "Chỉnh sửa vai trò";
:Chọn vai trò mới (Admin/Teacher/Student) từ danh sách;
:Nhấn "Lưu";
|Hệ thống|
:Cập nhật trường "role" của bản ghi trong bảng "users";
:Cấp lại các chức năng tương ứng với vai trò mới;
|Quản lý|
stop
@enduml
```

---

## 6. Quản lý giáo viên

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý|
start
:Truy cập "Quản lý giáo viên";
|Hệ thống|
:Truy vấn và hiển thị danh sách từ bảng "users" (role=TEACHER)
kết hợp bảng "teacher_profiles";
|Quản lý|
:Chọn Thêm mới hoặc Cập nhật thông tin giáo viên
(trình độ, chuyên môn);
if (Thao tác cần hủy?) then (Có)
  |Hệ thống|
  :Quay về màn hình danh sách, không lưu thay đổi;
  |Quản lý|
  stop
else (Không)
endif
:Nhấn "Lưu";
|Hệ thống|
:Thêm/cập nhật bản ghi trong bảng "users"
và bảng "teacher_profiles" (trường "specialization");
:Đồng bộ lại danh sách hồ sơ giáo viên;
|Quản lý|
stop
@enduml
```

---

## 7. Quản lý học viên

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý|
start
:Truy cập "Quản lý học viên";
:Chọn cách thêm: nhập đơn lẻ hoặc import file Excel;
if (Import từ Excel?) then (Có)
  :Tải lên file danh sách học viên;
  |Hệ thống|
  if (File đúng định dạng?) then (Không)
    :Báo lỗi cụ thể theo từng dòng dữ liệu sai;
    |Quản lý|
    stop
  else (Có)
    :Thêm hàng loạt bản ghi vào bảng "users" (role=STUDENT)
    và bảng "student_profiles";
  endif
else (Không)
  |Quản lý|
  :Nhập thông tin học viên (tên, tuổi, SĐT phụ huynh...);
  :Nhấn "Lưu";
  |Hệ thống|
  :Thêm/cập nhật bản ghi trong bảng "users"
  và bảng "student_profiles" (trường "student_code");
endif
:Tạo/cập nhật hồ sơ học viên trên hệ thống;
|Quản lý|
stop
@enduml
```

---

## 8. Quản lý lớp học

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý|
start
:Chọn "Quản lý lớp học" -> "Tạo mới";
:Nhập tên lớp, trình độ, thời gian học;
:Chọn giáo viên phụ trách từ danh sách;
:Nhấn "Lưu";
|Hệ thống|
:Thêm bản ghi mới vào bảng "classes"
(gán "teacher_id" tham chiếu "teacher_profiles");
:Đặt trạng thái lớp học là "ACTIVE";
|Quản lý|
if (Khóa học đã hoàn thành?) then (Có)
  :Chọn "Đóng lớp học";
  |Hệ thống|
  :Cập nhật trường "status" của bảng "classes" thành "ENDED";
  |Quản lý|
else (Không)
endif
stop
@enduml
```

---

## 9. Quản lý thành viên lớp học

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý / Giáo viên|
start
:Truy cập chi tiết lớp học -> "Thành viên";
:Nhấn "Thêm học viên" và chọn từ danh sách có sẵn;
:Nhấn xác nhận;
|Hệ thống|
:Thêm bản ghi mới vào bảng "class_members"
(class_id, student_id, member_type);
|Quản lý / Giáo viên|
if (Cần chuyển học viên sang lớp khác?) then (Có)
  :Chọn học viên và lớp học đích;
  |Hệ thống|
  :Cập nhật/xóa bản ghi "class_members" của lớp cũ
  và thêm bản ghi "class_members" mới ở lớp đích;
  |Quản lý / Giáo viên|
else (Không)
endif
|Hệ thống|
:Cập nhật sĩ số lớp học tương ứng;
|Quản lý / Giáo viên|
stop
@enduml
```

---

## 10. Xem danh sách lớp học

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Người dùng|
start
:Truy cập menu "Lớp học của tôi";
|Hệ thống|
:Truy vấn bảng "classes" kết hợp "class_members"
theo tài khoản đang đăng nhập;
if (Người dùng chưa tham gia lớp học nào?) then (Có)
  :Hiển thị thông báo "Bạn chưa tham gia lớp học nào";
else (Không)
  :Hiển thị danh sách lớp (tên lớp, sĩ số, lịch học);
endif
|Người dùng|
stop
@enduml
```

---

## 11. Quản lý bài tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Vào mục "Bài tập" của lớp;
|Hệ thống|
:Truy vấn và hiển thị danh sách bài tập
từ bảng "assignments" (is_deleted = false);
|Giáo viên|
:Sử dụng bộ lọc theo kỹ năng/trạng thái để tìm kiếm;
:Chọn Sửa, Xóa hoặc Sao chép bài tập sang lớp khác;
|Hệ thống|
if (Thao tác là "Xóa"?) then (Có)
  :Đánh dấu "is_deleted = true" cho bản ghi
  trong bảng "assignments" (xóa mềm);
elseif (Thao tác là "Sửa"?) then (Có)
  :Cập nhật bản ghi tương ứng trong bảng "assignments";
else (Sao chép)
  :Tạo bản ghi mới trong bảng "assignments"
  (và các bảng "modules"/"questions" liên quan) cho lớp đích;
endif
:Cập nhật lại thư viện bài tập hiển thị;
|Giáo viên|
stop
@enduml
```

---

## 12. Tạo bài tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Chọn "Tạo bài tập mới";
:Nhập tiêu đề và chọn loại kỹ năng (Nghe/Nói/Đọc/Viết);
if (Import ngân hàng câu hỏi có sẵn?) then (Có)
  :Tải lên file Excel/Word chứa câu hỏi;
else (Không)
  :Soạn thảo nội dung thủ công
  (tải file audio đề bài, tạo câu hỏi trắc nghiệm, tạo đề tự luận);
endif
:Nhập đáp án chuẩn và thiết lập điểm số;
:Chọn lưu nháp hoặc xuất bản luôn;
|Hệ thống|
:Thêm bản ghi mới vào bảng "assignments";
:Thêm bản ghi vào bảng "modules"
(skill, task_type, source_audio_storage_key nếu là Listening);
:Thêm các bản ghi câu hỏi vào bảng "questions";
:Lưu bài tập vào cơ sở dữ liệu hệ thống;
|Giáo viên|
stop
@enduml
```

---

## 13. Mở bài tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Chọn bài tập trong danh sách;
:Chọn "Giao bài / Mở bài";
if (Hẹn giờ mở bài tự động?) then (Có)
  :Thiết lập thời điểm tự động mở bài;
else (Không)
  :Thiết lập thời gian bắt đầu và hạn chót (deadline) ngay;
endif
:Xác nhận;
|Hệ thống|
:Cập nhật trường "open_at"/"close_at" của bản ghi
trong bảng "assignments";
:Gửi thông báo cho học viên trong lớp;
|Giáo viên|
stop
@enduml
```

---

## 14. Khóa bài tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Chọn bài tập đang mở;
:Chọn chức năng "Khóa bài tập";
|Hệ thống|
:Cập nhật trường "is_manually_closed = true"
của bản ghi trong bảng "assignments";
:Chặn chức năng nộp bài đối với học viên;
|Giáo viên|
stop

' Dòng sự kiện phụ: hệ thống tự khóa khi hết hạn
|Hệ thống|
start
:Định kỳ kiểm tra trường "close_at" của bảng "assignments";
if (Đã đến thời điểm "close_at"?) then (Có)
  :Tự động chuyển bài tập sang trạng thái "Đã đóng";
  :Chặn chức năng nộp bài đối với học viên;
else (Không)
endif
stop
@enduml
```

---

## 15. Xem bài tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Truy cập "Bài tập của tôi";
|Hệ thống|
:Truy vấn bảng "assignments" theo lớp học viên đang tham gia;
:Hiển thị danh sách (tiêu đề, deadline, trạng thái);
|Học viên|
if (Muốn lọc theo kỹ năng?) then (Có)
  :Chọn bộ lọc kỹ năng (Nghe/Nói/Đọc/Viết);
  |Hệ thống|
  :Lọc lại danh sách theo bảng "modules.skill";
  |Học viên|
else (Không)
endif
:Nhấn vào một bài tập để xem hướng dẫn chi tiết;
|Hệ thống|
:Hiển thị nội dung chi tiết từ bảng "modules"/"questions";
|Học viên|
stop
@enduml
```

---

## 16. Làm bài tập viết (Writing)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Mở bài tập Viết;
:Đọc đề bài (Task 1, Task 2);
if (Nhập trực tiếp trên web?) then (Có)
  :Nhập văn bản vào khung soạn thảo (có bộ đếm từ);
else (Không)
  :Tải lên tệp bài làm (.docx, .pdf);
endif
:Kiểm tra lại nội dung đã nhập;
|Hệ thống|
:Lưu nội dung vào trường "content" của bảng "answers"
(gắn với bản ghi "submission_modules" tương ứng);
|Học viên|
stop
@enduml
```

---

## 17. Làm bài tập nói (Speaking)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Mở bài tập Nói, đọc/nghe câu hỏi;
if (Ghi âm trực tiếp?) then (Có)
  :Nhấn "Ghi âm" (cấp quyền micro cho trình duyệt);
  :Trả lời câu hỏi;
  :Nhấn "Dừng" và nghe lại đoạn ghi âm;
  if (Muốn ghi âm lại?) then (Có)
    :Xóa bản ghi tạm và ghi âm lại;
  else (Không)
  endif
else (Không)
  :Tải lên tệp âm thanh có sẵn (.mp3, .wav);
endif
|Hệ thống|
:Tải file âm thanh lên Cloudflare R2;
:Lưu "audio_storage_key", thời lượng, dung lượng, mime type
vào bảng "answers"; cập nhật "audio_upload_status";
|Học viên|
stop
@enduml
```

---

## 18. Làm bài tập đọc (Reading)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Mở bài tập Đọc;
:Đọc đoạn văn hiển thị bên trái màn hình;
:Trả lời các câu hỏi tương ứng bên phải màn hình
(trắc nghiệm, điền từ, True/False/Not Given);
if (Cần đánh dấu (highlight) đoạn văn?) then (Có)
  :Bôi màu đoạn văn bản nháp trực tiếp trên trình duyệt;
else (Không)
endif
|Hệ thống|
:Lưu tạm các câu trả lời vào bảng "answers"
(gắn "question_id" tương ứng);
|Học viên|
stop
@enduml
```

---

## 19. Làm bài tập nghe (Listening)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Mở bài tập Nghe;
:Nhấn nút "Play" phát file audio
(lấy từ "source_audio_storage_key" của bảng "modules");
|Học viên|
repeat
  :Nghe và chọn/điền đáp án vào các câu hỏi;
  if (Muốn nghe lại?) then (Có)
    |Hệ thống|
    if (Còn lượt phát cho phép?) then (Không)
      :Vô hiệu hóa nút "Play";
    else (Có)
      :Cho phép phát lại audio;
    endif
    |Học viên|
  else (Không)
    break
  endif
repeat while (Còn muốn nghe tiếp?) is (Có) not (Không)
|Hệ thống|
:Lưu các câu trả lời vào bảng "answers";
|Học viên|
stop
@enduml
```

---

## 20. Kiểm tra và xem lại bài làm trước khi nộp

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Chọn "Kiểm tra bài làm";
|Hệ thống|
if (Bài tập đã hết hạn?) then (Có)
  :Thông báo không thể tiếp tục và xử lý theo chính sách;
  |Học viên|
  stop
else (Không)
endif
:Tải toàn bộ câu trả lời từ bảng "answers"
theo "submission_modules" hiện tại;
:Hiển thị trạng thái từng câu (đã trả lời/chưa trả lời);
|Học viên|
repeat
  :Kiểm tra lại từng câu trả lời;
  |Hệ thống|
  if (Còn câu chưa trả lời hoặc dữ liệu không hợp lệ?) then (Có)
    :Cảnh báo phần chưa hoàn thành/không hợp lệ;
    |Học viên|
    :Quay lại làm bài, chỉnh sửa nếu cần
    (cập nhật bảng "answers");
  else (Không)
    break
  endif
repeat while (Còn thiếu/lỗi?) is (Có) not (Không)
:Xác nhận bài làm đã sẵn sàng;
|Hệ thống|
:Chuyển sang luồng UC21 — Nộp bài tập;
|Học viên|
stop
@enduml
```

---

## 21. Nộp bài tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Nhấn nút "Nộp bài";
|Hệ thống|
:Hiển thị popup xác nhận "Bạn có chắc chắn muốn nộp bài?";
if (Còn câu hỏi chưa làm?) then (Có)
  :Cảnh báo "Bạn còn X câu chưa làm, vẫn tiếp tục nộp?";
else (Không)
endif
|Học viên|
:Nhấn "Xác nhận";
|Hệ thống|
:Cập nhật trạng thái bản ghi "submissions" thành "SUBMITTED"
và ghi nhận "submitted_at";
:Cập nhật trạng thái các bản ghi liên quan
trong bảng "submission_modules" thành "SUBMITTED";
:Khóa bài làm của học viên (không cho sửa thêm);
|Học viên|
stop
@enduml
```

---

## 22. Quản lý lần làm và nộp lại bài

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Đã nộp bài lần 1;
:Vào lại bài tập;
|Hệ thống|
:Kiểm tra "max_submissions" của bảng "assignments"
so với số lần đã nộp trong bảng "submissions";
if (Còn lượt làm lại?) then (Không)
  :Ẩn nút "Làm lại bài";
  |Học viên|
  stop
else (Có)
  :Hiển thị nút "Làm lại bài (Còn x lượt)";
endif
|Học viên|
:Chọn "Làm lại";
|Hệ thống|
:Tạo bản ghi mới trong bảng "submissions"
với "attempt_number" tăng thêm 1;
|Học viên|
:Thực hiện bài làm và nộp bài lần tiếp theo (UC21);
|Hệ thống|
:Lưu trữ lịch sử tất cả các lần nộp của học viên;
|Học viên|
stop
@enduml
```

---

## 23. Xem trạng thái bài làm

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Truy cập trang tổng quan (Dashboard)
hoặc danh sách bài tập;
|Hệ thống|
:Truy vấn trạng thái từ bảng "submissions"
và "submission_modules" (Chưa làm/Đã nộp/Đang chấm/Đã chấm);
:Hiển thị nhãn trạng thái (status tag) bên cạnh mỗi bài tập;
|Học viên|
stop
@enduml
```

---

## 24. Chấm bài

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Chọn danh sách bài "Chờ chấm";
:Mở bài làm của một học viên;
|Hệ thống|
:Truy vấn bài làm từ bảng "answers"
theo "submission_module_id";
|Giáo viên|
:Đọc/nghe bài làm, nhập điểm cho từng câu/phần;
:Ghi chú, nhận xét trực tiếp vào bài làm hoặc nhận xét chung;
if (Muốn lưu nháp để xem lại sau?) then (Có)
  |Hệ thống|
  :Lưu tạm kết quả chấm (chưa hoàn tất) vào bảng "gradings"
  với "status = PENDING";
  |Giáo viên|
  stop
else (Không)
endif
:Nhấn "Xác nhận & Trả bài";
|Hệ thống|
:Thêm/cập nhật bản ghi bảng "gradings"
("method = TEACHER_MANUAL", "final_score", "final_feedback");
:Thêm các ghi chú vào bảng "answer_annotations" (source = TEACHER);
:Cập nhật trạng thái bài làm thành "Đã chấm";
:Gửi thông báo điểm cho học viên;
|Giáo viên|
stop
@enduml
```

---

## 25. Hỗ trợ chấm chữa bài bằng AI (Chung)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Mở bài làm của học viên;
:Nhấn "Gợi ý chấm bằng AI";
|Hệ thống|
:Gửi dữ liệu bài làm lên AI model;
|AI|
:Phân tích bài làm theo kỹ năng tương ứng
(UC26 — Viết hoặc UC27 — Nói);
|Hệ thống|
if (Kết nối AI bị lỗi/gián đoạn?) then (Có)
  :Báo lỗi "Hệ thống AI đang gián đoạn, vui lòng chấm thủ công";
  |Giáo viên|
  stop
else (Không)
endif
:Nhận kết quả và hiển thị điểm đề xuất,
các lỗi được đánh dấu (highlight) trên giao diện;
:Lưu "ai_suggested_score", "ai_feedback" vào bảng "gradings"
("method = AI");
|Giáo viên|
:Kiểm tra, có thể sửa lại điểm AI đề xuất nếu chưa phù hợp;
:Xác nhận kết quả cuối;
|Hệ thống|
:Cập nhật "final_score", "final_feedback", "reviewed_by",
"reviewed_at" trong bảng "gradings";
|Giáo viên|
stop
@enduml
```

---

## 26. Hỗ trợ chấm chữa bài viết bằng AI

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Hệ thống|
start
:Gửi nội dung bài viết (bảng "answers") lên AI;
|AI|
:Phân tích văn bản theo các tiêu chí
(Grammar, Lexical Resource, Coherence, Task Achievement);
if (Phát hiện đạo văn (Plagiarism)?) then (Có)
  :Gắn cảnh báo đỏ 100% cho bài làm;
else (Không)
endif
:Đánh dấu lỗi chính tả, lỗi ngữ pháp, từ vựng chưa hay
trên nội dung bài viết;
:Đề xuất cách sửa (rewrite suggestion) cho từng câu lỗi;
:Tổng hợp điểm theo từng tiêu chí;
|Hệ thống|
:Lưu các đánh dấu lỗi vào bảng "answer_annotations"
("source = AI", "error_type", "suggested_fix");
:Lưu điểm từng tiêu chí vào bảng "criteria_scores"
(gắn với bản ghi "gradings" tương ứng);
|Giáo viên|
:Xem từng gợi ý của AI;
if (Chấp nhận gợi ý?) then (Có)
  :Chọn "Accept";
else (Không)
  :Chọn "Reject";
endif
|Hệ thống|
:Cập nhật trạng thái duyệt cho các bản ghi
trong bảng "answer_annotations"/"criteria_scores";
|Giáo viên|
stop
@enduml
```

---

## 27. Hỗ trợ chấm chữa bài nói bằng AI

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Hệ thống|
start
:Gửi file audio bài làm (bảng "answers") lên AI;
|AI|
:Chuyển giọng nói thành văn bản (transcript);
if (Chất lượng âm thanh quá thấp?) then (Có)
  :Trả về cảnh báo "Chất lượng âm thanh quá thấp,
  không thể phân tích";
  |Hệ thống|
  :Thông báo cho giáo viên chấm thủ công;
  |Giáo viên|
  stop
else (Không)
endif
:Phân tích các từ phát âm sai (mispronounced words)
và đánh dấu trên transcript;
:Đánh giá nhịp điệu (intonation) và độ ngập ngừng;
:Đề xuất điểm theo từng tiêu chí (Pronunciation, Fluency...);
|Hệ thống|
:Lưu các đánh dấu lỗi phát âm vào bảng "answer_annotations"
("source = AI");
:Lưu điểm từng tiêu chí vào bảng "criteria_scores";
|Giáo viên|
:Nghe lại bài làm và đối chiếu đề xuất của AI;
:Chốt điểm cuối;
|Hệ thống|
:Cập nhật "final_score" trong bảng "gradings";
|Giáo viên|
stop
@enduml
```

---

## 28. Chấm bài đọc (Reading)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Nộp bài Đọc;
|Hệ thống|
:Tự động so sánh từng câu trả lời trong bảng "answers"
với "correct_answer" của bảng "questions";
if (Có câu trả lời dạng điền từ tương đối đúng?) then (Có)
  :Gán trạng thái "Cần giáo viên duyệt lại"
  cho bản ghi "gradings" liên quan;
  |Giáo viên|
  :Xem lại và xác nhận đáp án;
  |Hệ thống|
else (Không)
endif
:Tính tổng số câu đúng và quy đổi ra điểm
(hệ 10 hoặc hệ IELTS/TOEIC);
:Lưu điểm vào bảng "gradings" ("method = AUTO", "final_score");
|Học viên|
stop
@enduml
```

---

## 29. Chấm bài nghe (Listening)

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Nộp bài Nghe;
|Hệ thống|
:Tự động chấm câu trắc nghiệm, điền từ trong bảng "answers"
bằng cách đối chiếu với "correct_answer" của bảng "questions";
if (Sai viết hoa/thường nhưng đúng từ và cấu hình cho phép?) then (Có)
  :Tính là câu trả lời đúng;
else (Không)
endif
:Đưa ra điểm tự động;
:Lưu điểm vào bảng "gradings" ("method = AUTO", "final_score");
|Học viên|
stop
@enduml
```

---

## 30. Xem điểm số

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Người dùng|
start
:Truy cập mục "Bảng điểm / Kết quả";
|Hệ thống|
:Truy vấn "final_score" từ bảng "gradings"
của các bài đã được công bố điểm;
:Hiển thị danh sách bài tập kèm điểm đạt được
và điểm tối đa;
|Người dùng|
stop
@enduml
```

---

## 31. Quản lý điểm số

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Vào mục "Quản lý điểm số" của một lớp;
|Hệ thống|
:Truy vấn và hiển thị bảng ma trận (Học viên x Bài tập)
từ bảng "gradings";
|Giáo viên|
if (Cần sửa điểm thủ công?) then (Có)
  :Click vào ô điểm cần sửa và nhập lý do sửa;
  |Hệ thống|
  :Cập nhật "final_score" trong bảng "gradings"
  và ghi vết thay đổi (audit log);
  |Giáo viên|
else (Không)
endif
if (Cần xuất file?) then (Có)
  :Nhấn "Xuất file Excel";
  |Hệ thống|
  :Tổng hợp dữ liệu từ bảng "gradings" và xuất file Excel;
  |Giáo viên|
else (Không)
endif
:Lưu thay đổi;
stop
@enduml
```

---

## 32. Xem kết quả học tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Chọn "Tiến độ học tập";
|Hệ thống|
:Truy vấn "final_score" và "criteria_scores"
theo từng kỹ năng (Nghe-Nói-Đọc-Viết) từ bảng "gradings";
:Vẽ biểu đồ radar 4 kỹ năng thể hiện điểm yếu/mạnh;
:Tính điểm trung bình toàn khóa;
|Học viên|
:Xem điểm trung bình và biểu đồ;
stop
@enduml
```

---

## 33. Xem bài chữa

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Học viên|
start
:Click vào "Chi tiết bài chữa" của bài đã có điểm;
|Hệ thống|
:Truy vấn bài làm từ bảng "answers"
kèm các đánh dấu lỗi từ bảng "answer_annotations";
:Hiển thị bài làm kèm đánh dấu màu sắc (highlight lỗi);
|Học viên|
:Đọc/nghe phần nhận xét (feedback) từ giáo viên hoặc AI;
:Xem đáp án chuẩn (answer key/sample answer)
từ bảng "questions"/"gradings";
stop
@enduml
```

---

## 34. Xem và đánh giá kết quả học viên

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Chọn một học viên trong danh sách lớp;
|Hệ thống|
:Truy vấn lịch sử làm bài (bảng "submissions")
và điểm số các kỹ năng (bảng "gradings") của học viên đó;
:Hiển thị biểu đồ điểm số theo từng kỹ năng;
|Giáo viên|
:Xem lịch sử làm bài và biểu đồ điểm số;
:Viết đánh giá (report) tổng quan về thái độ và năng lực;
:Gửi đánh giá cho học viên;
|Hệ thống|
:Lưu bản đánh giá vào hồ sơ học viên;
|Giáo viên|
stop
@enduml
```

---

## 35. Theo dõi tiến độ học tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Giáo viên|
start
:Truy cập "Báo cáo lớp học";
|Hệ thống|
:Tổng hợp tỷ lệ hoàn thành bài tập từ bảng "submissions"
và tỷ lệ học viên dưới trung bình từ bảng "gradings";
:Hiển thị Dashboard tổng quan của lớp;
|Giáo viên|
:Dựa vào số liệu để điều chỉnh phương pháp dạy;
stop
@enduml
```

---

## 36. Xem báo cáo và thống kê học tập

```plantuml
@startuml
skinparam monochrome true
skinparam shadowing false

|Quản lý / Giáo viên|
start
:Truy cập "Thống kê báo cáo";
:Thiết lập bộ lọc (từ tháng X đến tháng Y,
theo chi nhánh/lớp/giáo viên);
|Hệ thống|
:Tổng hợp số liệu từ các bảng "assignments",
"submissions" và "gradings" theo bộ lọc;
if (Không có dữ liệu trong khoảng thời gian đã chọn?) then (Có)
  :Hiển thị "Không có dữ liệu";
  |Quản lý / Giáo viên|
  stop
else (Không)
endif
:Xuất báo cáo dạng số liệu và biểu đồ (KPI);
|Quản lý / Giáo viên|
:Nhấn "Xuất PDF/Excel" để tải báo cáo;
|Hệ thống|
:Sinh file báo cáo (PDF/Excel) để tải về;
|Quản lý / Giáo viên|
stop
@enduml
```

---

*Ghi chú tổng hợp bảng dữ liệu tham chiếu (theo `ERD_reference.md`):* `users`, `teacher_profiles`, `student_profiles`, `classes`, `class_members`, `assignments`, `modules`, `questions`, `submissions`, `submission_modules`, `answers`, `answer_annotations`, `gradings`, `criteria_scores`.

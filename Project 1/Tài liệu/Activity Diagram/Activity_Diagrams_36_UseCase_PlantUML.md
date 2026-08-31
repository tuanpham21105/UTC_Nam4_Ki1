# ACTIVITY DIAGRAM — CODE PLANTUML CHO 36 USE CASE
## Website quản lý bài tập và chấm chữa bài tiếng Anh thông minh — Group 4

Chuyển đổi 1:1 từ bộ Mermaid activity diagram (`Activity_Diagrams_36_UseCase_v3_ERD_Compliant.md`) sang code PlantUML activity diagram (cú pháp mới: `|Swimlane|`, `if/then/else/endif`, `switch/case/endswitch`, `label`/`goto` cho các vòng lặp quay lại bước trước). Giữ nguyên toàn bộ logic, tên bảng/cột ERD và các quyết định đã chốt theo file giải trình.

> Cách dùng: copy từng khối code trong thẻ ```plantuml``` vào [PlantUML online editor](https://www.plantuml.com/plantuml/uml/) hoặc plugin PlantUML (VS Code/IntelliJ) để render hình.

---

## UC1. Đăng nhập

```plantuml
@startuml
|Người dùng|
start
:Truy cập trang đăng nhập;
:Nhập Email + Mật khẩu;
:Nhấn Đăng nhập;
|Hệ thống|
:Truy vấn users theo email;
if (Tồn tại users.email?) then (Có)
  :So khớp mật khẩu với users.password_hash;
  if (Khớp mật khẩu?) then (Đúng)
    if (users.status = ACTIVE?) then (ACTIVE)
      :Đọc users.role (ADMIN/TEACHER/STUDENT);
      :Sinh JWT Access Token (payload: user_id, role) — không ghi bảng nào;
      :Trả token về client, chuyển hướng Dashboard tương ứng;
      stop
    else (LOCKED)
      :Thông báo tài khoản đang LOCKED;
      stop
    endif
  else (Sai)
    :Thông báo sai mật khẩu;
    stop
  endif
else (Không)
  :Thông báo tài khoản không tồn tại;
  stop
endif
@enduml
```

---

## UC2. Đăng xuất

```plantuml
@startuml
|Người dùng (Client)|
start
:Chọn chức năng Đăng xuất trên menu;
:Xóa Access Token khỏi bộ nhớ trình duyệt (localStorage/memory);
|Hệ thống|
:Không cần xử lý gì thêm — JWT stateless, không có bảng sessions;
:Chuyển hướng client về trang Đăng nhập;
stop
@enduml
```

---

## UC3. Quản lý tài khoản cá nhân

```plantuml
@startuml
|Người dùng|
start
:Truy cập trang Hồ sơ cá nhân;
|Hệ thống|
:SELECT users theo id hiện tại, hiển thị thông tin;
|Người dùng|
label NhapLai
:Sửa full_name / phone / avatar_url và/hoặc email và/hoặc mật khẩu mới;
:Nhấn Lưu thay đổi;
|Hệ thống|
if (Có đổi email?) then (Có)
  :Kiểm tra UNIQUE(users.email);
  if (Email đã tồn tại ở user khác?) then (Có)
    :Báo lỗi Email đã được sử dụng;
    |Người dùng|
    goto NhapLai
  else (Không)
  endif
else (Không)
endif
if (Có đổi mật khẩu?) then (Có)
  :So khớp mật khẩu cũ với password_hash hiện tại;
  if (Đúng mật khẩu cũ?) then (Sai)
    :Yêu cầu nhập lại mật khẩu hiện tại;
    |Người dùng|
    goto NhapLai
  else (Đúng)
  endif
else (Không)
endif
if (Dữ liệu hợp lệ (định dạng phone...)?) then (Không hợp lệ)
  :Báo lỗi định dạng dữ liệu;
  |Người dùng|
  goto NhapLai
else (Hợp lệ)
  :UPDATE users SET ..., updated_at = now();
  :Thông báo cập nhật thành công;
  stop
endif
@enduml
```

---

## UC4. Quản lý tài khoản người dùng

```plantuml
@startuml
|Quản lý|
start
:Chọn menu Quản lý tài khoản;
|Hệ thống|
:SELECT users, hiển thị danh sách;
|Quản lý|
switch (Thao tác?)
case (Thêm mới)
  label NhapTK
  :Nhập full_name, email, phone, chọn role (ADMIN/TEACHER/STUDENT);
  |Hệ thống|
  :Kiểm tra UNIQUE(users.email);
  if (Email đã tồn tại?) then (Có)
    :Báo lỗi Email đã tồn tại;
    |Quản lý|
    goto NhapTK
  else (Không)
    :INSERT users (kèm role, password_hash mặc định)\n+ INSERT hồ sơ theo role: teacher_profiles(user_id, specialization)\nhoặc student_profiles(user_id, student_code) — 1 bước gộp;
  endif
case (Khóa/Mở khóa)
  |Quản lý|
  :Chọn Khóa/Mở khóa tài khoản;
  |Hệ thống|
  :UPDATE users.status = LOCKED / ACTIVE;
endswitch
:Cập nhật danh sách tài khoản;
stop
@enduml
```

---

## UC5. Phân quyền người dùng

```plantuml
@startuml
|Quản lý|
start
:Chọn tài khoản cần xem/đổi quyền;
|Hệ thống|
:SELECT users.role hiện tại của tài khoản;
if (Tài khoản đã có dữ liệu gắn theo role hiện tại?\n(TEACHER: classes.teacher_id/assignments.teacher_id;\nSTUDENT: submissions.student_id/class_members.student_id)) then (Có dữ liệu)
  :Từ chối đổi role — thông báo cần xử lý dữ liệu cũ trước\n(chưa có rule migrate dữ liệu giữa 2 subtype);
  stop
else (Chưa có dữ liệu)
  |Quản lý|
  :Chọn vai trò mới (nếu hệ thống cho phép đổi);
  |Hệ thống|
  :DELETE dòng profile cũ (teacher_profiles/student_profiles)\n+ UPDATE users.role + INSERT dòng profile mới;
  :Cập nhật quyền truy cập chức năng theo role mới;
  stop
endif
@enduml
```

---

## UC6. Quản lý giáo viên

```plantuml
@startuml
|Quản lý|
start
:Truy cập Quản lý giáo viên;
|Hệ thống|
:SELECT users JOIN teacher_profiles WHERE role=TEACHER;
|Quản lý|
switch (Thao tác?)
case (Thêm/Sửa)
  :Nhập full_name, email, phone, specialization;
  |Hệ thống|
  :INSERT users(role=TEACHER) + INSERT teacher_profiles(user_id, specialization)\n— 1 bước gộp;
case (Vô hiệu hóa)
  |Quản lý|
  :Chọn Vô hiệu hóa giáo viên;
  |Hệ thống|
  :Kiểm tra classes WHERE teacher_id = :id AND status='ACTIVE';
  if (Còn lớp ACTIVE đang phụ trách?) then (Còn)
    :Chặn vô hiệu hóa — yêu cầu bàn giao lớp trước\n(classes.teacher_id là FK NOT NULL);
    stop
  else (Không còn)
    :UPDATE users.status = LOCKED;
  endif
endswitch
:Đồng bộ danh sách hồ sơ giáo viên;
stop
@enduml
```

---

## UC7. Quản lý học viên

```plantuml
@startuml
|Quản lý|
start
:Truy cập Quản lý học viên;
switch (Cách thêm?)
case (Thêm đơn lẻ)
  label NhapHV
  :Nhập full_name, email, phone, student_code;
  |Hệ thống|
  :Kiểm tra UNIQUE(users.email), UNIQUE(student_profiles.student_code);
  if (Trùng dữ liệu?) then (Có)
    :Báo lỗi trùng email/student_code;
    |Quản lý|
    goto NhapHV
  else (Không)
    :INSERT users(role=STUDENT) + INSERT student_profiles(user_id, student_code)\n— 1 bước gộp;
    stop
  endif
case (Import Excel hàng loạt)
  |Quản lý|
  #pink:⚠️ Chọn Import Excel hàng loạt\n(ngoài phạm vi ERD 14 bảng hiện tại);
  |Hệ thống|
  #pink:⚠️ Chưa có cơ chế bulk-insert ở tầng service/schema\n— cần PM duyệt phạm vi trước khi triển khai;
  stop
endswitch
@enduml
```

---

## UC8. Quản lý lớp học

```plantuml
@startuml
|Quản lý|
start
:Chọn Quản lý lớp học → Tạo mới;
:Nhập name, level, description, start_date, end_date;
:Chọn giáo viên phụ trách (teacher_profiles);
:Nhấn Lưu;
|Hệ thống|
:INSERT classes(name, level, description, start_date, end_date, teacher_id, status=ACTIVE);
:«include» Quản lý thành viên lớp học UC9;
|Quản lý|
if (Đóng lớp học?) then (Có)
  :Chọn Đóng lớp học;
  |Hệ thống|
  :UPDATE classes.status = ARCHIVED (soft-delete, không xóa dòng);
  stop
else (Không)
  stop
endif
@enduml
```

---

## UC9. Quản lý thành viên lớp học

```plantuml
@startuml
|Quản lý / Giáo viên|
start
:Truy cập chi tiết lớp → Thành viên;
:Nhấn Thêm học viên, chọn từ danh sách;
:Chọn loại thành viên: PRIMARY (Chính thức) / SUPPLEMENTARY (Bổ trợ);
:Nhấn xác nhận;
|Hệ thống|
:SELECT class_members WHERE class_id=... AND student_id=...;
if (Kết quả tồn tại?) then (Chưa có dòng nào)
  :INSERT class_members(class_id, student_id, member_type, status=ACTIVE);
else (Đã có dòng)
  if (status hiện tại của dòng cũ?) then (ACTIVE)
    :Chặn — báo trùng thành viên đang ACTIVE;
    stop
  else (ENDED)
    :UPDATE class_members SET status=ACTIVE (ENDED → ACTIVE, học lại);
  endif
endif
:Nếu PRIMARY: kiểm tra unique partial index — chỉ 1 PRIMARY ACTIVE/student;
label CapNhatSiSo
:Cập nhật sĩ số lớp học;
if (Nhấn Cập nhật trạng thái - rời lớp?) then (Có)
  :UPDATE class_members SET status=ENDED (không DELETE);
  goto CapNhatSiSo
else (Không)
  stop
endif
@enduml
```

---

## UC10. Xem danh sách lớp học

```plantuml
@startuml
|Giáo viên / Học viên|
start
:Truy cập menu Lớp học của tôi;
|Hệ thống|
:Nếu Teacher: SELECT classes WHERE teacher_id=:id AND status != ARCHIVED;
:Nếu Student: SELECT classes JOIN class_members WHERE student_id=:id\nAND class_members.status = ACTIVE;
if (Có kết quả?) then (Không)
  :Thông báo Bạn chưa tham gia lớp học nào;
  stop
else (Có)
  :Hiển thị classes.name, class_members (sĩ số), start_date/end_date;
  stop
endif
@enduml
```

---

## UC11. Quản lý bài tập

```plantuml
@startuml
|Giáo viên|
start
:Vào mục Bài tập của lớp;
|Hệ thống|
:SELECT assignments WHERE class_id=... AND is_deleted=false;
|Giáo viên|
:Lọc theo modules.skill / assignments.status;
switch (Thao tác?)
case (Sửa/Sao chép)
  :Chọn Sửa/Sao chép bài tập;
  |Hệ thống|
  :«include» Tạo bài tập UC12;
case (Xóa)
  |Giáo viên|
  :Chọn Xóa bài tập;
  |Hệ thống|
  :UPDATE assignments SET is_deleted = true (soft-delete, luôn thực hiện được);
endswitch
:Cập nhật danh sách hiển thị;
stop
@enduml
```

---

## UC12. Tạo bài tập

```plantuml
@startuml
|Giáo viên|
start
:Chọn Tạo bài tập mới;
label NhapThoiGian
:Nhập title, description, open_at, close_at;
:Thiết lập max_submissions (NULL=không giới hạn / 0=không nộp lại / N);
|Hệ thống|
if (open_at < close_at?) then (Không hợp lệ)
  :Báo lỗi thời gian không hợp lệ;
  |Giáo viên|
  goto NhapThoiGian
else (Hợp lệ)
  :INSERT assignments(..., status = DRAFT);
endif
label ThemModule
|Giáo viên|
:Chọn skill (READING/LISTENING/WRITING/SPEAKING) + task_type cho 1 module;
:Soạn questions (content, question_type, correct_answer, score)\nhoặc upload source_audio (Listening);
|Hệ thống|
:INSERT modules(assignment_id, skill, task_type, order_index, max_score,\ninstructions[, source_audio_*]);
:INSERT questions(module_id, content, question_type, correct_answer, score, order_index);
note right
  ⚠️ Import ngân hàng câu hỏi Excel/Word — ngoài phạm vi hiện tại,
  questions chỉ hỗ trợ insert đơn lẻ
end note
|Giáo viên|
if (Thêm module (kỹ năng) khác vào bài tập này?) then (Có)
  goto ThemModule
else (Không)
  :Nhấn Lưu nháp;
  stop
endif
@enduml
```

---

## UC13. Mở bài tập

```plantuml
@startuml
|Giáo viên|
start
:Chọn bài tập status = DRAFT;
:Chọn Mở bài tập;
|Hệ thống|
:Kiểm tra mọi module READING/LISTENING có ≥ 1 questions?;
if (Đủ điều kiện?) then (Thiếu)
  :Chặn mở — báo thiếu câu hỏi;
  stop
else (Đủ)
  :Kiểm tra module LISTENING: source_audio_upload_status = READY?;
  if (Audio đề bài sẵn sàng?) then (Chưa sẵn sàng)
    :Chặn mở — audio đề bài chưa xử lý xong (PROCESSING/FAILED);
    stop
  else (READY / không có module Listening)
    :UPDATE assignments SET status = OPEN;
    :Gửi thông báo cho học viên trong class_members ACTIVE;
    stop
  endif
endif
@enduml
```

---

## UC14. Khóa bài tập

```plantuml
@startuml
|Giáo viên|
start
if (Kích hoạt bởi?) then (Tự động, đến close_at)
  |Hệ thống|
  :Job định kỳ: quét assignments WHERE status=OPEN AND close_at <= now();
else (Giáo viên chủ động)
  |Giáo viên|
  :Chọn bài tập status = OPEN;
  :Chọn Khóa bài tập;
  |Hệ thống|
endif
:UPDATE assignments SET status = CLOSED;
:Chặn chức năng nộp bài (UC21) đối với học viên;
stop
@enduml
```

---

## UC15. Xem bài tập

```plantuml
@startuml
|Học viên|
start
:Truy cập Bài tập của tôi;
|Hệ thống|
:SELECT assignments JOIN classes JOIN class_members\nWHERE student_id=... AND status IN (OPEN, CLOSED) AND is_deleted=false;
:SELECT submissions WHERE assignment_id=... AND student_id=...\nđể suy ra trạng thái làm bài;
|Học viên|
:Lọc theo modules.skill;
:Nhấn vào một bài tập;
|Hệ thống|
:Hiển thị title, description, open_at, close_at, status, modules.skill;
stop
@enduml
```

---

## UC16. Làm bài tập viết (Writing)

```plantuml
@startuml
|Học viên|
start
:Mở bài tập Viết;
:Đọc questions.content của module WRITING;
:Nhập câu trả lời vào khung soạn thảo;
|Hệ thống|
:Lấy hoặc tạo submissions(status=IN_PROGRESS, attempt_number)\n+ submission_modules(status=IN_PROGRESS) cho module WRITING;
:UPSERT answers(submission_module_id, question_id, content = văn bản,\naudio_storage_key = NULL);
|Học viên|
:Kiểm tra lại nội dung;
stop
@enduml
```

---

## UC17. Làm bài tập nói (Speaking)

```plantuml
@startuml
|Học viên|
start
:Mở bài tập Nói, đọc questions.content;
|Hệ thống|
:Lấy/tạo submissions + submission_modules(status=IN_PROGRESS);
|Học viên|
label GhiAm
switch (Cách trả lời?)
case (Ghi âm trực tiếp)
  :Ghi âm trực tiếp qua trình duyệt;
  :Nghe lại bản ghi;
  if (Hài lòng?) then (Không, ghi lại)
    goto GhiAm
  else (Có)
  endif
case (Upload file có sẵn)
  :Tải lên file .mp3/.wav có sẵn;
endswitch
|Hệ thống|
:UPLOAD file lên Cloudflare R2 → answers.audio_storage_key,\naudio_duration_seconds, audio_file_size_bytes, audio_mime_type;
:answers.audio_upload_status = UPLOADING → PROCESSING;
if (Xử lý xong trên R2?) then (Lỗi)
  :answers.audio_upload_status = FAILED — yêu cầu ghi/tải lại;
  |Học viên|
  goto GhiAm
else (Thành công)
  :answers.audio_upload_status = READY;
  stop
endif
@enduml
```

---

## UC18. Làm bài tập đọc (Reading)

```plantuml
@startuml
|Học viên|
start
:Mở bài tập Đọc;
:Đọc questions.content (đoạn văn + câu hỏi);
:Chọn/nhập đáp án theo question_type (MULTIPLE_CHOICE/SHORT_ANSWER);
|Hệ thống|
:Lấy/tạo submissions + submission_modules(status=IN_PROGRESS)\ncho module READING;
:UPSERT answers(submission_module_id, question_id, content = đáp án đã chọn/nhập);
stop
@enduml
```

---

## UC19. Làm bài tập nghe (Listening)

```plantuml
@startuml
|Học viên|
start
:Mở bài tập Nghe;
|Hệ thống|
:Kiểm tra modules.source_audio_upload_status = READY;
:Sinh presigned URL (15 phút) từ modules.source_audio_storage_key;
|Học viên|
:Nhấn Play;
|Hệ thống|
:Phát audio đề bài cho học viên;
|Học viên|
:Nghe và chọn/điền đáp án;
|Hệ thống|
:Lấy/tạo submissions + submission_modules(status=IN_PROGRESS);
:UPSERT answers(question_id, content = đáp án);
stop
@enduml
```

---

## UC20. Kiểm tra và xem lại bài làm trước khi nộp

```plantuml
@startuml
|Học viên|
start
:Đã làm xong (một phần/toàn bộ) bài Nghe/Nói/Đọc/Viết — «cross-ref» UC16-19;
label ChonKiemTra
:Chọn Kiểm tra bài làm;
|Hệ thống|
:Kiểm tra assignments.status = OPEN và close_at chưa qua;
if (Bài tập còn hạn (chưa CLOSED)?) then (Hết hạn)
  :Chặn — bài đã hết hạn, xử lý theo chính sách\n(không cho kiểm tra/nộp tiếp);
  stop
else (Còn hạn)
  :SELECT answers JOIN submission_modules JOIN questions\nWHERE submission_module_id thuộc submissions hiện tại (status=IN_PROGRESS);
  :Hiển thị trạng thái từng câu: đã trả lời / chưa trả lời\n(so khớp questions.id với answers.question_id hiện có);
endif
|Học viên|
:Xem lại từng câu trả lời theo trạng thái hiển thị;
|Hệ thống|
if (Còn questions chưa có answers tương ứng?) then (Có câu chưa trả lời)
  :Cảnh báo: còn X câu chưa trả lời;
else (Đã trả lời hết)
  if (content/audio_storage_key hợp lệ theo đúng question_type/skill?) then (Không hợp lệ)
    :Yêu cầu sửa lại dữ liệu không hợp lệ\n(vd: SHORT_ANSWER rỗng, Speaking chưa audio_upload_status=READY);
  else (Hợp lệ)
  endif
endif
|Học viên|
if (Cần chỉnh sửa câu nào không?) then (Có, cần sửa)
  :Quay lại làm bài (chỉnh sửa) — «cross-ref» UC16-19;
  |Hệ thống|
  :Cho phép chỉnh sửa: quay lại UPSERT answers ở UC16-19;
  |Học viên|
  goto ChonKiemTra
else (Không, đã ổn)
  :Xác nhận bài đã sẵn sàng;
  |Hệ thống|
  :Đánh dấu bài làm đã sẵn sàng, chuyển sang «include» Nộp bài tập UC21\n(KHÔNG đổi submissions.status — vẫn IN_PROGRESS);
  stop
endif
@enduml
```

---

## UC21. Nộp bài tập

```plantuml
@startuml
|Học viên|
start
:Nhấn nút Nộp bài;
|Hệ thống|
if (assignments.status = OPEN?) then (CLOSED)
  :Chặn nộp — bài đã CLOSED;
  stop
else (OPEN)
  if (submissions.attempt_number < assignments.max_submissions?\n(NULL=không giới hạn, 0=không cho nộp)) then (Hết lượt)
    :Chặn — hết lượt nộp;
    stop
  else (Còn lượt)
    if (Còn questions chưa có answers tương ứng?) then (Có)
      :Cảnh báo Còn X câu chưa làm, vẫn tiếp tục?;
    else (Không)
    endif
  endif
endif
|Học viên|
:Nhấn Xác nhận;
|Hệ thống|
:UPDATE submissions SET status=SUBMITTED, submitted_at=now()\n(XÁC NHẬN, không phải tạo mới);
:UPDATE submission_modules SET status=SUBMITTED;
:INSERT gradings(submission_module_id, status=PENDING) cho mỗi submission_module;
stop
@enduml
```

---

## UC22. Quản lý lần làm và nộp lại bài

```plantuml
@startuml
|Học viên|
start
:Đã nộp bài — submissions.status=SUBMITTED, attempt_number=N;
|Hệ thống|
if (attempt_number < assignments.max_submissions?) then (Hết lượt)
  :Ẩn nút Làm lại;
  stop
else (Còn lượt)
  :Hiển thị nút Làm lại (còn X lượt, tính từ max_submissions - attempt_number);
endif
|Học viên|
:Vào lại bài tập;
:Chọn Làm lại bài;
|Hệ thống|
:INSERT submissions(assignment_id, student_id, attempt_number = N+1, status=IN_PROGRESS)\n— UNIQUE(assignment_id, student_id, attempt_number);
|Học viên|
:«include» Làm bài (UC16-19) → «include» Nộp bài tập UC21;
stop
@enduml
```

---

## UC23. Xem trạng thái bài làm

```plantuml
@startuml
|Học viên|
start
:Truy cập Dashboard/danh sách bài tập;
|Hệ thống|
:Nếu chưa có submissions → Chưa làm;
:submissions.status = IN_PROGRESS → Đang làm;
:submissions.status = SUBMITTED → Đã nộp / Đang chấm (tùy gradings.status);
:Tất cả gradings liên quan status = COMPLETED → Đã chấm / Đã có bài chữa;
:Hiển thị Status tag theo màu tương ứng;
stop
@enduml
```

---

## UC24. Chấm bài

```plantuml
@startuml
|Giáo viên|
start
:Chọn ds submission_modules WHERE status=SUBMITTED;
:Mở bài làm của 1 học viên;
switch (Kỹ năng của module?)
case (READING)
  |Hệ thống|
  :«include» Chấm bài đọc UC28 (AUTO);
case (LISTENING)
  |Hệ thống|
  :«include» Chấm bài nghe UC29 (AUTO);
case (WRITING/SPEAKING - dùng AI)
  |Hệ thống|
  :«include» Hỗ trợ chấm chữa bài bằng AI UC25;
case (WRITING/SPEAKING - chấm tay)
  |Giáo viên|
  :Nhập final_score, final_feedback thủ công;
endswitch
|Hệ thống|
:UPDATE gradings SET max_score_snapshot = modules.max_score\n(chốt tại thời điểm chấm, tránh sai hồi tố);
:UPDATE gradings SET final_score, final_feedback, reviewed_by, reviewed_at, status=COMPLETED;
|Giáo viên|
:Nhấn Xác nhận và Trả bài;
|Hệ thống|
:UPDATE submission_modules.status = GRADED;\nnếu đủ mọi module → submissions.status = GRADED;
:Gửi thông báo học viên có điểm;
stop
@enduml
```

---

## UC25. Hỗ trợ chấm chữa bài bằng AI

```plantuml
@startuml
|Giáo viên|
start
:Mở bài làm, nhấn Gợi ý chấm bằng AI;
|Hệ thống|
:INSERT gradings(method=AI, status=PENDING);
|AI|
:Nhận dữ liệu bài làm (answers);
switch (Kỹ năng?)
case (Viết)
  :«include» UC26 - chấm Viết bằng AI;
case (Nói)
  :«include» UC27 - chấm Nói bằng AI;
endswitch
if (Kết nối/xử lý AI thành công?) then (Lỗi)
  |Hệ thống|
  :UPDATE gradings SET status=FAILED, method giữ nguyên = AI\n(KHÔNG merge vào nhánh thành công);
  :Báo Hệ thống AI đang gián đoạn, cần chấm thủ công;
  |Giáo viên|
  :Vào chấm thủ công (nếu AI lỗi);
  |Hệ thống|
  :UPDATE gradings SET method=TEACHER_MANUAL, status=PENDING\n(chờ giáo viên chấm tay);
else (Thành công)
  |Hệ thống|
  :UPDATE gradings SET ai_suggested_score, ai_feedback;
  :Hiển thị điểm đề xuất + answer_annotations (source=AI) highlight lỗi;
  |Giáo viên|
  :Xem điểm/feedback AI đề xuất, có thể sửa lại;
endif
|Giáo viên|
:Xác nhận kết quả cuối;
|Hệ thống|
:UPDATE gradings SET final_score, final_feedback, status=COMPLETED;
stop
@enduml
```

---

## UC26. Hỗ trợ chấm chữa bài viết bằng AI

```plantuml
@startuml
|AI|
start
:Nhận answers.content (văn bản Writing);
:Phân tích lỗi: chính tả/ngữ pháp/từ vựng/cấu trúc câu;
:Với mỗi lỗi: chuẩn bị start_offset, end_offset, error_type, suggested_fix;
|Hệ thống|
:INSERT answer_annotations(answer_id, source=AI, start_offset, end_offset,\nerror_type, comment, suggested_fix) — N dòng, mỗi lỗi 1 dòng;
|AI|
:Tính điểm theo từng tiêu chí: Grammar, Vocabulary, Spelling,\nSentence structure, Coherence, Relevance, Writing quality;
|Hệ thống|
:INSERT criteria_scores(grading_id, criteria_name, score, feedback)\n— N dòng, mỗi tiêu chí 1 dòng;
|AI|
if (Phát hiện đạo văn?) then (Có)
  #pink:⚠️ Cảnh báo Plagiarism — ngoài phạm vi ERD hiện tại\n(không có pipeline/cột lưu kết quả plagiarism);
else (Không)
endif
|Hệ thống|
:UPDATE gradings.ai_suggested_score = SUM/AVG(criteria_scores.score theo trọng số),\nai_feedback = tổng hợp;
|Giáo viên|
:Duyệt: Accept hoặc Reject từng gợi ý AI (answer_annotations);
stop
@enduml
```

---

## UC27. Hỗ trợ chấm chữa bài nói bằng AI

```plantuml
@startuml
|Hệ thống|
start
:Kiểm tra answers.audio_upload_status = READY;
if (Sẵn sàng?) then (Chưa)
  :Chặn — audio chưa xử lý xong;
  stop
else (READY)
  :Sinh presigned URL từ answers.audio_storage_key, gửi cho AI;
endif
|AI|
:ASR: chuyển giọng nói → văn bản (Speech-to-Text);
if (Chất lượng âm thanh đạt?) then (Không đạt)
  :Trả cảnh báo: chất lượng âm thanh quá thấp;
  |Hệ thống|
  :UPDATE gradings.status = FAILED, ai_feedback = 'Chất lượng âm thanh thấp';
  stop
else (Đạt)
  :Phân tích từ phát âm sai, tính offset trong transcript;
  |Hệ thống|
  :UPDATE answers.content = transcript\n(KHÔNG xóa audio_storage_key — cả 2 cùng tồn tại, riêng cho Speaking);
  :INSERT answer_annotations(answer_id, source=AI, start_offset, end_offset\ntrỏ vào content=transcript, error_type='pronunciation');
  |AI|
  :Đánh giá Fluency: WPM, số lần ngừng, filler word...;
  |Hệ thống|
  :UPDATE gradings.ai_feedback += chỉ số Fluency dạng text\n(không tách cột riêng);
  |AI|
  :Đề xuất điểm (ai_suggested_score);
  |Hệ thống|
  :UPDATE gradings.ai_suggested_score;
  |Giáo viên|
  :Nghe lại bài nói và chốt điểm cuối;
  stop
endif
@enduml
```

---

## UC28. Chấm bài đọc (Reading)

```plantuml
@startuml
|Học viên|
start
:Nộp bài Đọc (trigger từ UC21);
|Hệ thống|
:INSERT gradings(submission_module_id, method=AUTO, status=PENDING);
:Với mỗi answers: so sánh content với questions.correct_answer;
if (question_type = SHORT_ANSWER và gần đúng (tương đối)?) then (Có, cần duyệt lại)
  :UPDATE gradings.status = PENDING, ghi chú cần giáo viên duyệt lại\n(chưa COMPLETED);
  stop
else (Không, so khớp rõ ràng)
  :SUM(questions.score) cho các answers đúng;
  :UPDATE gradings SET final_score = tổng điểm, max_score_snapshot = modules.max_score,\nstatus = COMPLETED, graded_at = now();
  stop
endif
@enduml
```

---

## UC29. Chấm bài nghe (Listening)

```plantuml
@startuml
|Học viên|
start
:Nộp bài Nghe (trigger từ UC21);
|Hệ thống|
:INSERT gradings(submission_module_id, method=AUTO, status=PENDING);
:Với mỗi answers: so sánh content với questions.correct_answer;
if (Cấu hình cho phép sai hoa/thường?) then (Có)
  :Chuẩn hóa lowercase trước khi so sánh;
else (Không)
endif
:SUM(questions.score) cho các answers đúng;
:UPDATE gradings SET final_score, max_score_snapshot = modules.max_score,\nstatus = COMPLETED, graded_at = now();
stop
@enduml
```

---

## UC30. Xem điểm số

```plantuml
@startuml
|Học viên / Giáo viên|
start
:Truy cập Bảng điểm / Kết quả;
|Hệ thống|
:SELECT assignments → submission_modules → modules(skill) → gradings\nWHERE gradings.status = COMPLETED;
:Với mỗi module: hiển thị final_score / max_score_snapshot theo skill riêng\n(KHÔNG có cột điểm tổng hợp cấp assignment trong schema);
if (Assignment có > 1 module?) then (Có)
  :Hiển thị danh sách N dòng điểm — 1 dòng / module / skill;
else (Không)
  :Hiển thị 1 dòng điểm duy nhất;
endif
stop
@enduml
```

---

## UC31. Quản lý điểm số

```plantuml
@startuml
|Giáo viên|
start
:Vào Quản lý điểm số của 1 lớp;
|Hệ thống|
:SELECT gradings JOIN submission_modules JOIN submissions JOIN class_members\n→ hiển thị bảng Matrix Học viên × Module;
|Giáo viên|
:Click ô điểm để sửa thủ công, nhập lý do sửa;
|Hệ thống|
:Lưu old_score = gradings.final_score (giá trị trước khi sửa);
:UPDATE gradings SET final_score = giá trị mới;
:🆕 INSERT grading_change_logs(grading_id, changed_by, old_score, new_score,\nreason, changed_at) — bảng MỚI, cần bổ sung migration;
|Giáo viên|
if (Xuất Excel?) then (Có)
  :Nhấn Xuất Excel;
  |Hệ thống|
  :Xuất file Excel bảng điểm hiện hành;
  stop
else (Không)
  stop
endif
@enduml
```

---

## UC32. Xem kết quả học tập

```plantuml
@startuml
|Học viên|
start
:Chọn Tiến độ học tập;
|Hệ thống|
:SELECT gradings JOIN submission_modules JOIN modules(skill)\nWHERE student_id=... AND status=COMPLETED;
:Tính tỷ lệ = final_score / max_score_snapshot cho từng dòng\n(KHÔNG dùng điểm thô, vì max_score khác nhau giữa các module);
:Nhóm theo modules.skill (READING/LISTENING/WRITING/SPEAKING) → AVG(tỷ lệ);
:Render biểu đồ radar 4 kỹ năng;
:Tính điểm trung bình toàn khóa = AVG(tỷ lệ tất cả module);
stop
@enduml
```

---

## UC33. Xem bài chữa

```plantuml
@startuml
|Học viên|
start
:Click Chi tiết bài chữa của module đã status=COMPLETED;
|Hệ thống|
:SELECT answers WHERE submission_module_id=...;
:SELECT answer_annotations WHERE answer_id IN (...)\n— hiển thị highlight lỗi (source AI hoặc TEACHER);
:SELECT gradings.final_feedback, criteria_scores (nếu Writing/Speaking);
:Hiển thị questions.correct_answer làm đáp án chuẩn;
stop
@enduml
```

---

## UC34. Xem và đánh giá kết quả học viên

```plantuml
@startuml
|Giáo viên|
start
:Chọn một học viên trong danh sách lớp;
|Hệ thống|
:«include» Xem kết quả học tập UC32\n(tính theo tỷ lệ final_score/max_score_snapshot);
|Giáo viên|
:Xem lịch sử: SELECT gradings/criteria_scores theo student_id,\nnhóm theo modules.skill;
:Viết đánh giá tổng quan (nhận xét thái độ, năng lực);
|Hệ thống|
#pink:⚠️ Không có bảng lưu 'đánh giá tổng quan' (report) riêng trong 14 bảng hiện tại.
Trong phạm vi ERD hiện hành CHỈ có thể: (a) ghi tạm vào gradings.final_feedback
của 1 module bất kỳ (không đúng bản chất), hoặc (b) chưa lưu — chỉ hiển thị tức thời.
|Giáo viên|
:Gửi đánh giá cho học viên;
stop
@enduml
```

---

## UC35. Theo dõi tiến độ học tập

```plantuml
@startuml
|Giáo viên|
start
:Truy cập Báo cáo lớp học;
|Hệ thống|
:SELECT class_members WHERE class_id=... AND status=ACTIVE → tổng số học viên;
:SELECT submissions WHERE assignment_id IN (assignments của lớp)\n→ tỷ lệ đã nộp/chưa nộp;
:SELECT gradings JOIN submission_modules\n→ tính tỷ lệ final_score/max_score_snapshot theo học viên;
:Tính % hoàn thành bài tập, % học viên có tỷ lệ điểm dưới ngưỡng trung bình;
:Hiển thị Dashboard tiến độ lớp học;
|Giáo viên|
:Xem Dashboard, điều chỉnh phương pháp giảng dạy;
stop
@enduml
```

---

## UC36. Xem báo cáo và thống kê học tập

```plantuml
@startuml
|Quản lý / Giáo viên|
start
:Truy cập Thống kê báo cáo;
label ThietLapBoLoc
:Thiết lập bộ lọc: khoảng thời gian, theo lớp (classes)/giáo viên (teacher_profiles)/kỹ năng (modules.skill);
|Hệ thống|
if (Bộ lọc có bao gồm 'chi nhánh'?) then (Có)
  #pink:⚠️ Không hỗ trợ — classes/users hiện không có cột liên kết chi nhánh\n(ngoài phạm vi ERD 14 bảng);
  |Quản lý / Giáo viên|
  goto ThietLapBoLoc
else (Không)
  :Tổng hợp: SUM/COUNT assignments, gradings(final_score/max_score_snapshot)\ntheo bộ lọc hợp lệ;
  if (Có dữ liệu trong khoảng đã chọn?) then (Không có dữ liệu)
    :Hiển thị Không có dữ liệu;
    |Quản lý / Giáo viên|
    goto ThietLapBoLoc
  else (Có dữ liệu)
    :Xuất báo cáo dạng số liệu + biểu đồ KPI;
    |Quản lý / Giáo viên|
    :Nhấn Xuất PDF/Excel;
    |Hệ thống|
    :Tạo và trả về file PDF/Excel;
    stop
  endif
endif
@enduml
```

---

## Ghi chú kỹ thuật khi dùng PlantUML

- Cú pháp `|Tên lane|` chuyển swimlane — dùng cho Người dùng/Quản lý/Giáo viên/Học viên, `Hệ thống`, và `AI` (UC25–27) đúng theo 3 làn đã thiết kế ở bản Mermaid.
- `label X` + `goto X` thay cho các cạnh quay lui (vd: sửa lỗi rồi quay lại nhập liệu ở UC3, UC4, UC7, UC9, UC12, UC17, UC20, UC36) — PlantUML hỗ trợ `goto` cho activity diagram (khác `while` khi vòng lặp không đơn giản là "lặp cho tới khi đúng điều kiện đầu vào").
- `#pink:...;` dùng để tô màu các bước ⚠️ ngoài phạm vi ERD hiện tại, dễ nhận diện khi trình bày.
- `switch/case/endswitch` dùng cho các decision có >2 nhánh xuất phát từ 1 điểm (UC4, UC6, UC7, UC17, UC24, UC25).
- Toàn bộ nội dung text bên trong node giữ nguyên các tên bảng/cột ERD như bản Mermaid gốc để đảm bảo tính nhất quán khi đối chiếu với `ERD_reference.md`.

# ACTIVITY DIAGRAM — CODE PLANTUML CHO 36 USE CASE
## Website quản lý bài tập và chấm chữa bài tiếng Anh thông minh — Group 4

> Cách dùng: copy từng khối code vào [PlantUML online editor](https://www.plantuml.com/plantuml/uml/) hoặc plugin PlantUML (VS Code/IntelliJ) để render hình.

---

## UC1. Đăng nhập

```plantuml
@startuml
|Người dùng|
start
:Nhập email và mật khẩu;
:Nhấn Đăng nhập;
|Hệ thống|
if (Email tồn tại?) then (Không)
  :Báo tài khoản không tồn tại;
  stop
else (Có)
  if (Mật khẩu đúng?) then (Sai)
    :Báo sai mật khẩu;
    stop
  else (Đúng)
    if (Tài khoản ACTIVE?) then (LOCKED)
      :Báo tài khoản bị khóa;
      stop
    else (ACTIVE)
      :Sinh JWT token (user_id, role);
      :Chuyển hướng đến Dashboard;
      stop
    endif
  endif
endif
@enduml
```

---

## UC2. Đăng xuất

```plantuml
@startuml
|Người dùng|
start
:Chọn Đăng xuất;
:Xóa token khỏi trình duyệt;
|Hệ thống|
:Chuyển về trang Đăng nhập;
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
:Hiển thị thông tin tài khoản hiện tại;
|Người dùng|
label NhapLai
:Chỉnh sửa thông tin và nhấn Lưu;
|Hệ thống|
if (Đổi email?) then (Có)
  if (Email đã dùng bởi user khác?) then (Có)
    :Báo lỗi email đã tồn tại;
    |Người dùng|
    goto NhapLai
  else (Không)
  endif
else (Không)
endif
if (Đổi mật khẩu?) then (Có)
  if (Mật khẩu cũ đúng?) then (Sai)
    :Báo sai mật khẩu hiện tại;
    |Người dùng|
    goto NhapLai
  else (Đúng)
  endif
else (Không)
endif
if (Dữ liệu hợp lệ?) then (Không)
  :Báo lỗi định dạng;
  |Người dùng|
  goto NhapLai
else (Có)
  :Lưu thông tin, thông báo thành công;
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
:Vào menu Quản lý tài khoản;
|Hệ thống|
:Hiển thị danh sách tài khoản;
|Quản lý|
switch (Thao tác?)
case (Thêm mới)
  label NhapTK
  :Nhập thông tin và chọn vai trò;
  |Hệ thống|
  if (Email đã tồn tại?) then (Có)
    :Báo lỗi email trùng;
    |Quản lý|
    goto NhapTK
  else (Không)
    :Tạo tài khoản và hồ sơ tương ứng;
  endif
case (Khóa / Mở khóa)
  |Quản lý|
  :Chọn Khóa hoặc Mở khóa;
  |Hệ thống|
  :Cập nhật trạng thái tài khoản;
endswitch
:Cập nhật danh sách;
stop
@enduml
```

---

## UC5. Phân quyền người dùng

```plantuml
@startuml
|Quản lý|
start
:Chọn tài khoản cần thay đổi quyền;
|Hệ thống|
:Kiểm tra vai trò hiện tại;
if (Đã có dữ liệu gắn theo vai trò cũ?) then (Có)
  :Từ chối — yêu cầu xử lý dữ liệu cũ trước;
  stop
else (Chưa có)
  |Quản lý|
  :Chọn vai trò mới;
  |Hệ thống|
  :Xóa hồ sơ cũ, cập nhật vai trò, tạo hồ sơ mới;
  :Cập nhật quyền truy cập;
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
:Vào Quản lý giáo viên;
|Hệ thống|
:Hiển thị danh sách giáo viên;
|Quản lý|
switch (Thao tác?)
case (Thêm / Sửa)
  :Nhập thông tin giáo viên;
  |Hệ thống|
  :Lưu tài khoản và hồ sơ giáo viên;
case (Vô hiệu hóa)
  |Quản lý|
  :Chọn Vô hiệu hóa;
  |Hệ thống|
  if (Còn lớp ACTIVE đang phụ trách?) then (Có)
    :Chặn — yêu cầu bàn giao lớp trước;
    stop
  else (Không)
    :Khóa tài khoản giáo viên;
  endif
endswitch
:Cập nhật danh sách;
stop
@enduml
```

---

## UC7. Quản lý học viên

```plantuml
@startuml
|Quản lý|
start
:Vào Quản lý học viên;
switch (Cách thêm?)
case (Thêm đơn lẻ)
  label NhapHV
  :Nhập thông tin học viên;
  |Hệ thống|
  if (Trùng email hoặc mã học viên?) then (Có)
    :Báo lỗi trùng dữ liệu;
    |Quản lý|
    goto NhapHV
  else (Không)
    :Tạo tài khoản và hồ sơ học viên;
    stop
  endif
case (Import Excel hàng loạt)
  |Quản lý|
  #pink:⚠️ Chức năng ngoài phạm vi hiện tại\n— cần PM duyệt trước khi triển khai;
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
:Nhập thông tin lớp và chọn giáo viên phụ trách;
:Nhấn Lưu;
|Hệ thống|
:Tạo lớp học với trạng thái ACTIVE;
:Chuyển sang quản lý thành viên (UC9);
|Quản lý|
if (Đóng lớp?) then (Có)
  :Chọn Đóng lớp;
  |Hệ thống|
  :Chuyển lớp sang trạng thái ARCHIVED;
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
:Mở danh sách thành viên của lớp;
:Chọn học viên và loại thành viên (Chính thức / Bổ trợ);
|Hệ thống|
if (Học viên đã trong lớp?) then (Chưa có)
  :Thêm học viên vào lớp;
else (Đã có)
  if (Trạng thái hiện tại?) then (ACTIVE)
    :Chặn — học viên đang hoạt động trong lớp;
    stop
  else (ENDED)
    :Kích hoạt lại học viên;
  endif
endif
label CapNhatSiSo
:Cập nhật sĩ số lớp;
if (Rời lớp?) then (Có)
  :Đánh dấu học viên rời lớp (ENDED);
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
:Vào menu Lớp học của tôi;
|Hệ thống|
:Lấy danh sách lớp theo vai trò người dùng;
if (Có lớp nào không?) then (Không)
  :Thông báo chưa tham gia lớp nào;
  stop
else (Có)
  :Hiển thị danh sách lớp học;
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
:Hiển thị danh sách bài tập;
|Giáo viên|
:Lọc theo kỹ năng hoặc trạng thái;
switch (Thao tác?)
case (Sửa / Sao chép)
  :Chọn bài tập;
  |Hệ thống|
  :Chuyển sang tạo/chỉnh sửa bài tập (UC12);
case (Xóa)
  |Giáo viên|
  :Chọn Xóa bài tập;
  |Hệ thống|
  :Ẩn bài tập (xóa mềm);
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
:Nhập tiêu đề, mô tả, thời gian mở/đóng, số lượt nộp;
|Hệ thống|
if (Thời gian hợp lệ?) then (Không)
  :Báo lỗi thời gian;
  |Giáo viên|
  goto NhapThoiGian
else (Hợp lệ)
  :Tạo bài tập trạng thái DRAFT;
endif
label ThemModule
|Giáo viên|
:Chọn kỹ năng và soạn câu hỏi cho module;
|Hệ thống|
:Lưu module và danh sách câu hỏi;
note right
  ⚠️ Import câu hỏi từ Excel/Word
  chưa được hỗ trợ
end note
|Giáo viên|
if (Thêm module khác?) then (Có)
  goto ThemModule
else (Không)
  :Lưu nháp bài tập;
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
:Chọn bài tập DRAFT và nhấn Mở;
|Hệ thống|
if (Đủ câu hỏi cho mọi module?) then (Thiếu)
  :Chặn — yêu cầu bổ sung câu hỏi;
  stop
else (Đủ)
  if (Audio đề bài sẵn sàng (Listening)?) then (Chưa)
    :Chặn — audio chưa xử lý xong;
    stop
  else (Sẵn sàng)
    :Chuyển bài tập sang OPEN;
    :Gửi thông báo cho học viên trong lớp;
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
if (Kích hoạt bởi?) then (Tự động khi hết hạn)
  |Hệ thống|
  :Quét các bài tập OPEN đã qua thời hạn đóng;
else (Giáo viên chủ động)
  |Giáo viên|
  :Chọn bài tập OPEN và nhấn Khóa;
  |Hệ thống|
endif
:Chuyển bài tập sang CLOSED;
:Chặn học viên nộp bài;
stop
@enduml
```

---

## UC15. Xem bài tập

```plantuml
@startuml
|Học viên|
start
:Vào Bài tập của tôi;
|Hệ thống|
:Hiển thị danh sách bài tập (OPEN/CLOSED) của lớp;
:Hiển thị trạng thái làm bài của từng bài;
|Học viên|
:Lọc theo kỹ năng và mở bài tập cụ thể;
|Hệ thống|
:Hiển thị chi tiết bài tập;
stop
@enduml
```

---

## UC16. Làm bài tập viết (Writing)

```plantuml
@startuml
|Học viên|
start
:Mở bài tập Viết, đọc đề;
:Nhập bài làm vào khung soạn thảo;
|Hệ thống|
:Khởi tạo hoặc lấy phiên làm bài hiện tại;
:Lưu câu trả lời (văn bản);
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
:Mở bài tập Nói, đọc đề;
|Hệ thống|
:Khởi tạo phiên làm bài;
|Học viên|
label GhiAm
switch (Cách trả lời?)
case (Ghi âm trực tiếp)
  :Ghi âm qua trình duyệt;
  :Nghe lại bản ghi;
  if (Hài lòng?) then (Không)
    goto GhiAm
  else (Có)
  endif
case (Upload file)
  :Tải lên file âm thanh;
endswitch
|Hệ thống|
:Upload file lên lưu trữ đám mây;
if (Upload thành công?) then (Thất bại)
  :Báo lỗi upload;
  |Học viên|
  goto GhiAm
else (Thành công)
  :Đánh dấu audio sẵn sàng;
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
:Mở bài tập Đọc, đọc đoạn văn;
:Chọn hoặc nhập đáp án từng câu;
|Hệ thống|
:Khởi tạo phiên làm bài;
:Lưu đáp án;
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
:Kiểm tra audio đề bài sẵn sàng;
:Tạo link phát audio (hết hạn sau 15 phút);
|Học viên|
:Nhấn Play nghe audio;
:Chọn hoặc điền đáp án;
|Hệ thống|
:Khởi tạo phiên làm bài;
:Lưu đáp án;
stop
@enduml
```

---

## UC20. Kiểm tra và xem lại bài làm

```plantuml
@startuml
|Học viên|
start
:Chọn Kiểm tra bài làm;
|Hệ thống|
if (Bài tập còn trong hạn?) then (Đã hết hạn)
  :Chặn — không thể kiểm tra/nộp;
  stop
else (Còn hạn)
  :Hiển thị trạng thái từng câu (đã / chưa trả lời);
endif
|Học viên|
:Xem lại các câu trả lời;
|Hệ thống|
if (Còn câu chưa trả lời?) then (Có)
  :Cảnh báo số câu còn bỏ trống;
else (Không)
  if (Dữ liệu hợp lệ?) then (Không hợp lệ)
    :Yêu cầu sửa lại câu không hợp lệ;
  else (Hợp lệ)
  endif
endif
|Học viên|
label ChonKiemTra
if (Cần chỉnh sửa?) then (Có)
  :Quay lại làm bài (UC16-19);
  |Hệ thống|
  :Cho phép cập nhật đáp án;
  |Học viên|
  goto ChonKiemTra
else (Không)
  :Xác nhận bài sẵn sàng nộp;
  |Hệ thống|
  :Chuyển sang Nộp bài (UC21);
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
if (Bài tập còn OPEN?) then (Đã CLOSED)
  :Chặn — bài đã đóng;
  stop
else (OPEN)
  if (Còn lượt nộp?) then (Hết lượt)
    :Chặn — hết số lần nộp;
    stop
  else (Còn lượt)
    if (Còn câu chưa làm?) then (Có)
      :Cảnh báo — vẫn có thể tiếp tục nộp;
    else (Không)
    endif
  endif
endif
|Học viên|
:Nhấn Xác nhận nộp;
|Hệ thống|
:Đánh dấu bài đã nộp;
:Tạo bản ghi chờ chấm điểm;
stop
@enduml
```

---

## UC22. Quản lý lần làm và nộp lại bài

```plantuml
@startuml
|Học viên|
start
:Xem bài đã nộp;
|Hệ thống|
if (Còn lượt nộp lại?) then (Hết lượt)
  :Ẩn nút Làm lại;
  stop
else (Còn lượt)
  :Hiển thị số lượt còn lại và nút Làm lại;
endif
|Học viên|
:Chọn Làm lại bài;
|Hệ thống|
:Tạo lần nộp mới;
|Học viên|
:Làm bài (UC16-19) và nộp lại (UC21);
stop
@enduml
```

---

## UC23. Xem trạng thái bài làm

```plantuml
@startuml
|Học viên|
start
:Vào Dashboard / danh sách bài tập;
|Hệ thống|
:Xác định trạng thái từng bài:\n· Chưa làm\n· Đang làm\n· Đã nộp / Đang chấm\n· Đã có điểm;
:Hiển thị nhãn trạng thái tương ứng;
stop
@enduml
```

---

## UC24. Chấm bài

```plantuml
@startuml
|Giáo viên|
start
:Chọn bài làm cần chấm;
switch (Kỹ năng của module?)
case (Đọc - Reading)
  |Hệ thống|
  :Chấm tự động (UC28);
case (Nghe - Listening)
  |Hệ thống|
  :Chấm tự động (UC29);
case (Viết / Nói - dùng AI)
  |Hệ thống|
  :Gợi ý chấm bằng AI (UC25);
case (Viết / Nói - chấm tay)
  |Giáo viên|
  :Nhập điểm và nhận xét thủ công;
endswitch
|Hệ thống|
:Lưu điểm, chốt điểm tối đa tại thời điểm chấm;
:Cập nhật trạng thái hoàn thành chấm;
|Giáo viên|
:Xác nhận và trả bài;
|Hệ thống|
:Cập nhật trạng thái bài nộp sang GRADED;
:Gửi thông báo cho học viên;
stop
@enduml
```

---

## UC25. Hỗ trợ chấm chữa bài bằng AI

```plantuml
@startuml
|Giáo viên|
start
:Mở bài làm và nhấn Gợi ý chấm bằng AI;
|Hệ thống|
:Tạo bản ghi chấm trạng thái PENDING;
|AI|
:Nhận dữ liệu bài làm;
switch (Kỹ năng?)
case (Viết)
  :Chấm bài Viết (UC26);
case (Nói)
  :Chấm bài Nói (UC27);
endswitch
if (AI xử lý thành công?) then (Thất bại)
  |Hệ thống|
  :Đánh dấu chấm AI thất bại;
  :Yêu cầu giáo viên chấm tay;
  |Giáo viên|
  :Chuyển sang chấm thủ công;
  |Hệ thống|
  :Cập nhật phương thức chấm thành TEACHER_MANUAL;
else (Thành công)
  |Hệ thống|
  :Hiển thị điểm đề xuất và highlight lỗi của AI;
  |Giáo viên|
  :Xem kết quả AI, điều chỉnh nếu cần;
endif
|Giáo viên|
:Xác nhận kết quả cuối;
|Hệ thống|
:Lưu điểm và nhận xét chính thức;
stop
@enduml
```

---

## UC26. Hỗ trợ chấm chữa bài viết bằng AI

```plantuml
@startuml
|AI|
start
:Nhận bài viết của học viên;
:Phân tích lỗi chính tả, ngữ pháp, từ vựng, cấu trúc;
|Hệ thống|
:Lưu từng lỗi kèm vị trí và gợi ý sửa;
|AI|
:Chấm điểm theo từng tiêu chí (Ngữ pháp, Từ vựng, Chính tả, Cấu trúc, Mạch lạc, ...);
|Hệ thống|
:Lưu điểm và nhận xét từng tiêu chí;
|AI|
if (Phát hiện đạo văn?) then (Có)
  #pink:⚠️ Cảnh báo đạo văn — ngoài phạm vi ERD hiện tại;
else (Không)
endif
|Hệ thống|
:Tổng hợp điểm AI đề xuất từ các tiêu chí;
|Giáo viên|
:Duyệt hoặc bỏ từng gợi ý của AI;
stop
@enduml
```

---

## UC27. Hỗ trợ chấm chữa bài nói bằng AI

```plantuml
@startuml
|Hệ thống|
start
:Kiểm tra audio bài nói sẵn sàng;
if (Audio sẵn sàng?) then (Chưa)
  :Chặn — audio chưa xử lý xong;
  stop
else (Sẵn sàng)
  :Tạo link truy cập audio và gửi cho AI;
endif
|AI|
:Chuyển giọng nói sang văn bản (Speech-to-Text);
if (Chất lượng âm thanh đạt?) then (Không đạt)
  :Báo chất lượng âm thanh quá thấp;
  |Hệ thống|
  :Đánh dấu chấm thất bại do chất lượng âm thanh;
  stop
else (Đạt)
  :Xác định từ phát âm sai và vị trí trong transcript;
  |Hệ thống|
  :Lưu transcript vào câu trả lời; 
  :Lưu các lỗi phát âm kèm vị trí;
  |AI|
  :Đánh giá độ trôi chảy (tốc độ, ngắt, filler word);
  |Hệ thống|
  :Ghi chỉ số fluency vào nhận xét;
  |AI|
  :Đề xuất điểm tổng;
  |Hệ thống|
  :Lưu điểm AI đề xuất;
  |Giáo viên|
  :Nghe lại và xác nhận điểm cuối;
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
:Nộp bài Đọc (từ UC21);
|Hệ thống|
:Khởi tạo bản ghi chấm tự động;
:So sánh từng đáp án với đáp án chuẩn;
if (Có câu trả lời ngắn cần duyệt thêm?) then (Có)
  :Đánh dấu cần giáo viên duyệt lại;
  stop
else (Không — so khớp rõ ràng)
  :Tính tổng điểm các câu đúng;
  :Lưu điểm và đánh dấu hoàn thành;
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
:Nộp bài Nghe (từ UC21);
|Hệ thống|
:Khởi tạo bản ghi chấm tự động;
:So sánh từng đáp án với đáp án chuẩn;
if (Cho phép bỏ qua hoa/thường?) then (Có)
  :Chuẩn hóa chữ thường trước khi so sánh;
else (Không)
endif
:Tính tổng điểm;
:Lưu điểm và đánh dấu hoàn thành;
stop
@enduml
```

---

## UC30. Xem điểm số

```plantuml
@startuml
|Học viên / Giáo viên|
start
:Vào Bảng điểm;
|Hệ thống|
:Lấy danh sách kết quả chấm đã hoàn thành;
if (Bài tập có nhiều module?) then (Có)
  :Hiển thị điểm theo từng module / kỹ năng;
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
:Vào Quản lý điểm số của lớp;
|Hệ thống|
:Hiển thị bảng điểm học viên theo từng module;
|Giáo viên|
:Click vào ô điểm cần sửa, nhập lý do;
|Hệ thống|
:Lưu điểm mới và ghi log thay đổi (điểm cũ, điểm mới, lý do);
|Giáo viên|
if (Xuất Excel?) then (Có)
  :Nhấn Xuất Excel;
  |Hệ thống|
  :Tạo và trả về file bảng điểm;
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
:Vào Tiến độ học tập;
|Hệ thống|
:Lấy kết quả tất cả module đã hoàn thành;
:Tính tỷ lệ điểm (điểm đạt / điểm tối đa) từng module;
:Nhóm và tính trung bình theo 4 kỹ năng;
:Hiển thị biểu đồ radar 4 kỹ năng;
:Tính điểm trung bình toàn khóa;
stop
@enduml
```

---

## UC33. Xem bài chữa

```plantuml
@startuml
|Học viên|
start
:Mở bài đã chấm xong;
|Hệ thống|
:Hiển thị đáp án của từng câu;
:Hiển thị các lỗi được highlight (nguồn AI hoặc giáo viên);
:Hiển thị nhận xét, điểm theo tiêu chí (nếu Viết/Nói);
:Hiển thị đáp án chuẩn;
stop
@enduml
```

---

## UC34. Xem và đánh giá kết quả học viên

```plantuml
@startuml
|Giáo viên|
start
:Chọn một học viên trong lớp;
|Hệ thống|
:Hiển thị kết quả học tập tổng quan (UC32);
|Giáo viên|
:Xem lịch sử điểm theo từng kỹ năng;
:Viết nhận xét tổng quan về học viên;
|Hệ thống|
#pink:⚠️ Chưa có bảng lưu nhận xét tổng quan\n— tạm ghi vào nhận xét module hoặc chỉ hiển thị tức thời;
|Giáo viên|
:Gửi nhận xét cho học viên;
stop
@enduml
```

---

## UC35. Theo dõi tiến độ học tập

```plantuml
@startuml
|Giáo viên|
start
:Vào Báo cáo lớp học;
|Hệ thống|
:Đếm số học viên đang hoạt động trong lớp;
:Tính tỷ lệ đã nộp / chưa nộp bài;
:Tính điểm trung bình và tỷ lệ học viên dưới ngưỡng;
:Hiển thị Dashboard tiến độ lớp;
|Giáo viên|
:Xem Dashboard, điều chỉnh phương pháp dạy;
stop
@enduml
```

---

## UC36. Xem báo cáo và thống kê học tập

```plantuml
@startuml
|Quản lý / Giáo viên|
start
:Vào Thống kê báo cáo;
label ThietLapBoLoc
:Thiết lập bộ lọc: thời gian, lớp, giáo viên, kỹ năng;
|Hệ thống|
if (Bộ lọc có 'chi nhánh'?) then (Có)
  #pink:⚠️ Không hỗ trợ — cơ sở dữ liệu chưa có thông tin chi nhánh;
  |Quản lý / Giáo viên|
  goto ThietLapBoLoc
else (Không)
  if (Có dữ liệu trong khoảng đã chọn?) then (Không)
    :Thông báo không có dữ liệu;
    |Quản lý / Giáo viên|
    goto ThietLapBoLoc
  else (Có)
    :Tổng hợp số liệu và vẽ biểu đồ KPI;
    |Quản lý / Giáo viên|
    :Nhấn Xuất PDF/Excel;
    |Hệ thống|
    :Tạo và trả về file báo cáo;
    stop
  endif
endif
@enduml
```

---

## Ghi chú kỹ thuật khi dùng PlantUML

- `|Tên lane|` chuyển swimlane — dùng cho Người dùng/Quản lý/Giáo viên/Học viên, `Hệ thống`, và `AI` (UC25–27).
- `label X` + `goto X` thay cho các cạnh quay lui (sửa lỗi rồi nhập lại) — dùng ở UC3, UC4, UC7, UC9, UC12, UC17, UC20, UC36.
- `#pink:...;` tô màu các bước ⚠️ ngoài phạm vi ERD hiện tại.
- `switch/case/endswitch` dùng cho decision có >2 nhánh (UC4, UC6, UC7, UC11, UC17, UC24, UC25).
- Mỗi nhánh trong `switch` cần có hành động trong đúng swimlane trước khi kết thúc hoặc hợp nhất.

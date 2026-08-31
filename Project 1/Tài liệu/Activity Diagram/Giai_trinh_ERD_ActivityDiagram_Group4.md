TRƯỜNG ĐẠI HỌC GIAO THÔNG VẬN TẢI

KHOA CÔNG NGHỆ THÔNG TIN

---

FILE GIẢI TRÌNH

SOÁT ĐỐI CHIẾU ACTIVITY DIAGRAM VỚI ERD/RM

và Đề xuất điều chỉnh Sơ đồ quan hệ thực thể (ERD)

HỌC PHẦN PROJECT 1

ĐỀ TÀI: XÂY DỰNG WEBSITE QUẢN LÝ BÀI TẬP VÀ CHẤM CHỮA BÀI TIẾNG ANH THÔNG MINH

| Giảng viên hướng dẫn: | TS. Nguyễn Trọng Phúc |
| --- | --- |
| Nhóm: | 4 |
| Người soạn thảo: | Đoàn Thái Sơn — Backend Developer |
| Vai trò trong nội dung này: | Chủ trì soát ERD/RM đối chiếu Activity Diagram |
| Ngày lập: | 30/08/2026 |
| Phiên bản: | 1.0 — Tổng hợp từ Đợt soát 1 → 9 |

# 1. Mục đích và phạm vi tài liệu

Tài liệu này tổng hợp kết quả rà soát đối chiếu toàn bộ Activity Diagram của hệ thống (khoảng 20 sơ đồ, thực hiện qua 9 đợt soát) với Sơ đồ quan hệ thực thể (ERD) và Bảng ánh xạ quan hệ (RM) hiện hành — schema 14 bảng đã chốt trong ERD_reference.md / giai-thich-schema-15-bang.md (bản cập nhật 29/08/2026, đã bỏ bảng notifications, đã bổ sung cột lưu voice vào answers).

Mục đích của tài liệu là:

- Liệt kê toàn bộ điểm lệch giữa Activity Diagram và ERD/RM đã phát hiện, phân loại theo mức độ nghiêm trọng.

- Trình bày rõ nguyên nhân, không né tránh hay chỉ sửa qua loa từng hình vẽ đơn lẻ, mà truy về gốc rễ kiến trúc khi có nhiều diagram cùng vướng 1 vấn đề.

- Đề xuất phương án xử lý cụ thể cho từng lỗi — bao gồm cả các lỗi chỉ cần sửa hình vẽ, và các lỗi bắt buộc phải sửa ERD/schema vì dữ liệu chưa có chỗ lưu.

- Làm căn cứ chính thức (enterprise-style) để trình PM và giảng viên hướng dẫn phê duyệt trước khi cập nhật lại toàn bộ artifact liên quan (Activity Diagram, ERD, RM, migration V2).

Phạm vi rà soát bao gồm 9 đợt, tương ứng các nhóm chức năng: (1) Tài khoản cá nhân — Đăng nhập/Đăng xuất; (2) Quản lý tài khoản/phân quyền/giáo viên/lớp học; (3) Quản lý học viên trong lớp & Quản lý bài tập; (4) Mở/Khóa bài tập & Làm bài Reading/Listening; (5) Nộp bài chung & Writing+AI (nộp); (6) Speaking+AI & Chấm chữa bài (giáo viên); (7) Quản lý điểm số; (8) Thống kê điểm & Xem điểm/Feedback (học viên); (9) Theo dõi kết quả học tập (học viên).

# 2. Quy ước phân loại mức độ

| Ký hiệu | Ý nghĩa | Cách xử lý |
| --- | --- | --- |
| 🔴 Phải sửa ERD | Activity Diagram mô tả đúng nghiệp vụ nhưng schema 14 bảng hiện tại chưa có bảng/cột tương ứng để lưu dữ liệu. | Bắt buộc sửa V1__init_schema.sql + ERD_reference.md + giai-thich-schema-15-bang.md, không thể chỉ sửa hình vẽ. |
| 🔴 Phải sửa Diagram | Diagram vẽ sai/thiếu bước so với luồng dữ liệu thực tế của schema đã có sẵn. | Chỉ cần vẽ lại Activity Diagram cho khớp, không cần đổi ERD. |
| 🟡 Cần làm rõ | Không sai hẳn nhưng mô tả chưa đủ chi tiết, dễ hiểu nhầm khi lập trình. | Bổ sung ghi chú vào đặc tả use case/API, không bắt buộc sửa hình. |
| 🟢 / ✅ Khớp tốt | Diagram khớp đúng ERD/RM, không có vấn đề. | Không cần chỉnh sửa. |

# 3. Tóm tắt số liệu toàn đợt soát

| Đợt | Nhóm diagram | 🔴 ERD | 🔴 Diagram | 🟡 Làm rõ | 🟢 Khớp tốt |
| --- | --- | --- | --- | --- | --- |
| 1 | Đăng nhập / Đăng xuất / Đổi mật khẩu / Xem trang chủ / Xem-cập nhật thông tin | 0 | 2 | 2 | 1 |
| 2 | Quản lý TK & phân quyền / Quản lý giáo viên / Quản lý lớp học | 0 | 2 | 1 | 1 |
| 3 | Quản lý học viên trong lớp / Tạo-sửa bài tập / Xem-xóa bài tập | 1 (status) | 3 | 3 | 0 |
| 4 | Mở/Khóa bài tập / Làm bài Reading / Làm bài Listening | 0* | 1 | 5 | 2 |
| 5 | Nộp bài (UC020) / Writing+AI nộp / Writing+AI chấm chữa | 0 | 0 | 6 | 3 |
| 6 | Speaking+AI nộp / Speaking+AI chấm / Chấm chữa bài (GV) | 1 (transcript) | 0 | 6 | 3 |
| 7 | Quản lý điểm số — Xem/Cập nhật | 1 (audit trail) | 0 | 2 | 1 |
| 8 | Thống kê điểm / Xem điểm-Feedback (HV) | 0** | 0 | 3 | 2 |
| 9 | Tổng hợp kết quả / Phân tích tiến độ (HV) | 0 | 0 | 2 | 4 |

(*) Lỗi status của assignments được phát hiện ở Đợt 3 nhưng được xác nhận và chốt hướng xử lý dựa trên bằng chứng ở Đợt 4, nên được tính 1 lần duy nhất ở mục 4.1.

(**) Phát hiện ở Đợt 8 (mục 18) không phải lỗi ERD độc lập mới mà là bằng chứng củng cố thêm cho lỗi Transcript đã ghi nhận ở Đợt 6, nên gộp chung, không đếm trùng.

# 4. Danh sách lỗi 🔴 bắt buộc phải sửa ERD/Schema

Đây là nhóm lỗi nghiêm trọng nhất: Activity Diagram mô tả đúng một nghiệp vụ có thật, nhưng schema 14 bảng hiện tại không có chỗ lưu dữ liệu tương ứng. Không thể xử lý bằng cách chỉ sửa lại hình vẽ — bắt buộc phải cập nhật migration, ERD_reference.md và giai-thich-schema-15-bang.md.

## 4.1. Bổ sung cột status (ENUM) cho bảng assignments

Diagram liên quan:  Tạo/Chỉnh sửa bài tập (Đợt 3), Mở/Khóa bài tập (Đợt 4), Xem/Xóa bài tập (Đợt 3).

Mô tả lỗi: Diagram "Tạo bài tập" có bước "Đặt trạng thái Nháp" ngay sau khi lưu; diagram "Mở/Khóa bài tập" có 2 bước tường minh "Chuyển trạng thái Đang mở" và "Chuyển trạng thái Đã khóa", đều do giáo viên chủ động thao tác. Ba trạng thái Nháp → Đang mở → Đã khóa tạo thành một state machine thật — không phải giá trị tính toán ngầm từ open_at/close_at. Tuy nhiên bảng assignments trong schema hiện tại không có cột status/ENUM nào.

Xung đột phát sinh:  assignments đã có sẵn cột is_manually_closed (BOOLEAN). Nếu chỉ thêm status mà giữ nguyên is_manually_closed, 2 cột sẽ lưu trùng lặp cùng 1 ý nghĩa (rủi ro lệch dữ liệu, ví dụ status=OPEN nhưng is_manually_closed=true).

### Quyết định (đã chốt theo bằng chứng Đợt 4):

- Bổ sung cột assignments.status ENUM (DRAFT / OPEN / CLOSED), tương ứng Nháp / Đang mở / Đã khóa.

- Bỏ cột is_manually_closed — dùng status làm nguồn dữ liệu duy nhất cho trạng thái bài tập, tránh 2 nguồn dữ liệu chồng chéo.

- Sửa diagram "Mở/Khóa bài tập": bỏ 2 bước "Thiết lập lại thời gian mở"/"Thiết lập lại hạn nộp" đang bị lặp với diagram Tạo bài tập — thời gian chỉ nhập 1 lần lúc tạo, "Mở/Khóa" chỉ đổi cột status.

- Bổ sung mô tả điều kiện "Có thể mở?" vào đặc tả UC013: mọi module Reading/Listening trong assignment phải có ≥ 1 câu hỏi, và module Listening phải có source_audio_upload_status = READY.

## 4.2. Bổ sung chỗ lưu Transcript cho bài Speaking

Diagram liên quan:  Speaking+AI — AI chấm chữa (Đợt 6), Xem điểm và Feedback — học viên (Đợt 8).

Mô tả lỗi: Diagram AI chấm chữa Speaking có bước Hệ thống "Lưu Transcript" (sau khi AI thực hiện ASR — chuyển giọng nói thành văn bản), và học viên có bước riêng "Xem Transcript". Đây là dữ liệu hoàn toàn mới, không trùng với answers.content (cột này luôn NULL với Speaking theo đúng thiết kế đã chốt — Speaking chỉ dùng audio_storage_key). Rà soát toàn bộ 14 bảng: không có bảng/cột nào lưu transcript.

Hệ quả kéo theo (Đợt 8):  Cơ chế answer_annotations (đánh dấu lỗi phát âm cụ thể trong bài nói) dùng start_offset/end_offset trỏ vào answers.content. Với Speaking, content hiện luôn NULL — nên nếu không giải quyết đồng thời việc lưu transcript ngay trong content, cơ chế gạch chân lỗi phát âm cho học viên xem sẽ không hoạt động được.

### Quyết định:

- Cho phép answers của Speaking có đồng thời cả audio_storage_key (để phát lại bản ghi âm) lẫn content (mang nghĩa "transcript" thay vì "câu trả lời gõ tay").

- Bỏ ràng buộc nghiệp vụ cũ "chỉ 1 trong 2, không cùng lúc cả 2" đã ghi trong giai-thich-schema-15-bang.md mục 11 — áp dụng riêng cho trường hợp Speaking.

- Nhờ vậy answer_annotations giữ nguyên cơ chế offset sẵn có, không cần sửa thêm bảng nào khác — đây là phương án ít việc sửa ERD nhất trong các phương án đã xét (so với phương án tách bảng speaking_analyses riêng).

- Các chỉ số Fluency chi tiết (WPM, số lần ngừng, filler word...) nếu cần lưu, gộp chung vào cột ai_feedback (dạng text) của gradings, không tách cột riêng ở giai đoạn này.

## 4.3. Quyết định về lịch sử thay đổi điểm (audit trail)

Diagram liên quan:  Quản lý điểm số — Xem và cập nhật điểm (Đợt 7).

Mô tả lỗi: Diagram có chuỗi Cập nhật điểm → Lưu lịch sử thay đổi → Thông báo thành công, ngụ ý cần lưu lại lịch sử mỗi lần điểm bị chỉnh sửa (ai sửa, từ bao nhiêu thành bao nhiêu, khi nào). Bảng gradings hiện chỉ lưu đúng 1 dòng cho mỗi submission_module (UNIQUE), lưu giá trị hiện tại — không có cơ chế nào lưu các phiên bản điểm trước đó.

### Phương án đề xuất (khuyến nghị — độ ưu tiên: ít việc sửa ERD nhất):

- Bỏ yêu cầu lưu lịch sử đầy đủ — chỉ bổ sung 1 cột đơn giản gradings.adjustment_note (TEXT, không hiển thị cho học viên), mỗi lần sửa điểm thì ghi đè (không giữ lịch sử các lần trước).

- Cách này nhất quán với thiết kế sẵn có: cột reviewed_by/reviewed_at hiện tại cũng chỉ lưu lần review gần nhất, không phải lịch sử — dùng chung logic, không tạo ngoại lệ mới.

### Phương án thay thế (nếu PM/giảng viên yêu cầu audit trail đầy đủ):

- Thêm bảng mới grading_change_logs: id, grading_id (FK), changed_by (FK → teacher_profiles.user_id), old_score, new_score, reason, changed_at.

- Tốn công hơn — cần thêm 1 bảng + migration mới, nên chỉ chọn nếu có yêu cầu tường minh về audit/truy vết.

⚠ Cần chốt trước khi đưa vào bản ERD chính thức:  đây là quyết định còn treo, cần Sơn (PM) và Trần Tiến Sơn xác nhận trước khi cập nhật migration V2.

# 5. Danh sách lỗi 🔴 phải sửa Activity Diagram (không đổi ERD)

Nhóm lỗi này không cần thay đổi schema — chỉ cần vẽ lại luồng cho khớp với dữ liệu và ràng buộc đã có sẵn trong 14 bảng.

## 5.1. Đăng nhập / Đăng xuất (Đợt 1)

- Đổi bước "Nhập tên đăng nhập" thành "Nhập email" — bảng users không có cột username, trường đăng nhập duy nhất là email (UNIQUE).

- Chuỗi "Tạo phiên đăng nhập → Cấp Access Token → Cấp Refresh Token" và bước "Xóa key JWT" khi đăng xuất ngụ ý có bảng lưu session/token, nhưng schema không có bảng sessions/refresh_tokens nào. Khuyến nghị chốt kiến trúc JWT stateless thuần túy (không lưu gì ở DB) — ít việc sửa nhất: bỏ bước "Tạo phiên đăng nhập" khỏi diagram Đăng nhập; chuyển bước "Xóa key JWT" ở diagram Đăng xuất sang lane Người dùng (xóa token phía client), không phải lane Hệ thống.

## 5.2. Quản lý tài khoản & phân quyền / Quản lý giáo viên (Đợt 2)

- Gộp đúng cơ chế shared-PK subtype: đổi chuỗi "Tạo tài khoản → Gán vai trò" thành 1 bước duy nhất "Tạo tài khoản (users, kèm role) + Tạo hồ sơ theo vai trò (teacher_profiles/student_profiles nếu cần)" — áp dụng đồng nhất cho cả 2 diagram Quản lý tài khoản và Quản lý giáo viên (cùng 1 gốc lỗi).

- Nhánh "Phân quyền" (đổi role user đã tồn tại) mâu thuẫn với kiến trúc shared-PK subtype (đổi role đồng nghĩa xóa dòng profile cũ + tạo dòng profile mới, chưa có rule xử lý dữ liệu cũ liên quan). Khuyến nghị: bỏ hẳn nhánh "Phân quyền" — vai trò cố định ngay từ lúc tạo, đúng tinh thần thiết kế hiện tại.

- Quản lý giáo viên — nhánh "Vô hiệu hóa": bổ sung decision "Còn lớp ACTIVE đang phụ trách?" sau bước "Kiểm tra lớp đang phụ trách" — hiện bước kiểm tra có mặt nhưng không dẫn tới rẽ nhánh nào, trong khi classes.teacher_id là NOT NULL FK.

## 5.3. Quản lý học viên trong lớp học (Đợt 3)

- Thêm bước "Chọn loại thành viên (Chính thức/Bổ trợ)" trước khi thêm học viên vào lớp — class_members.member_type là ENUM NOT NULL, diagram hiện chưa có bước nhập.

- Sửa nhánh "Đã thuộc lớp?" từ 2 trường hợp thành 3 trường hợp: (1) chưa có dòng nào → INSERT mới; (2) có dòng, status=ACTIVE → chặn, báo trùng; (3) có dòng, status=ENDED → UPDATE lại dòng cũ (ENDED→ACTIVE) thay vì báo lỗi — vì UNIQUE(class_id, student_id) là tuyệt đối, không phải partial theo status, nên học viên rời lớp rồi học lại phải xử lý bằng UPDATE.

- Đổi tên hành động "Xóa học viên khỏi lớp" thành "Cập nhật trạng thái (ENDED)" — khớp đúng tinh thần soft-status, đồng thời giải quyết nhất quán với ý trên.

## 5.4. Tạo/Chỉnh sửa bài tập (Đợt 3)

- Thêm decision "Thêm phần kỹ năng khác vào bài tập này?" sau khi nhập xong 1 module — schema cho phép 1 assignment chứa nhiều module (nhiều kỹ năng), nhưng diagram hiện chỉ cho tạo đúng 1 kỹ năng/lượt.

- Thêm bước "Thiết lập số lần nộp tối đa (không giới hạn / 0 / N)" ứng với assignments.max_submissions — hiện diagram hoàn toàn thiếu bước này.

- Nhánh "Chỉnh sửa" cần bổ sung bước "Kiểm tra dữ liệu hợp lệ" cho nhất quán với nhánh "Tạo" (đặc biệt vì CHECK(open_at < close_at) vẫn áp dụng khi sửa giờ).

## 5.5. Xem/Xóa bài tập (Đợt 3)

assignments đã có sẵn cột is_deleted với đúng mục đích: soft-delete vì lo ngại mất bài tập đã có bài nộp. Diagram hiện tại dùng pattern "chặn xóa nếu đã có bài nộp" — pattern dành cho DELETE vật lý bị FK constraint chặn, không khớp tinh thần soft-delete đã thiết kế.

- Khuyến nghị: bỏ hẳn nhánh "Kiểm tra bài nộp / Đã có bài nộp?" — thao tác Xóa bài tập luôn thực hiện được (UPDATE is_deleted = true), không cần điều kiện chặn.

- Nếu team vẫn muốn giữ việc chặn xóa khi đã có bài nộp (business rule bổ sung để tránh mất minh bạch điểm số), giữ nguyên diagram nhưng phải ghi rõ ràng buộc này vào giai-thich-schema-15-bang.md (hiện tài liệu chưa nhắc điều kiện này).

## 5.6. Chuỗi "nộp bài" dùng chung cho 4 kỹ năng (Đợt 4–6)

- Bắt buộc:  Bổ sung decision "Còn lượt nộp không?" (so submissions.attempt_number với assignments.max_submissions) — hiện không có diagram nào trong 4 luồng làm bài (Reading, Listening, Writing, Speaking) kiểm tra điều này, dù submissions.attempt_number + UNIQUE(assignment_id, student_id, attempt_number) đã thiết kế sẵn cho đúng mục đích. Nên đặt tại diagram "Nộp bài (UC020)" dùng chung, tránh sửa lặp ở 4 nơi.

- Làm rõ và thống nhất thứ tự "Lưu bài làm" và "Tạo bài nộp": answers.submission_module_id là FK bắt buộc trỏ vào submission_modules — phải có submission_modules tồn tại trước khi insert answers. Khuyến nghị hiểu "Tạo bài nộp" là bước cập nhật bản ghi submission đã được tạo sẵn từ lúc bắt đầu làm bài (status=IN_PROGRESS), không phải tạo mới lúc nộp — đổi tên bước cho đúng bản chất (ví dụ "Xác nhận bài nộp").

## 5.7. Logic xử lý lỗi AI trong chấm chữa Writing/Speaking (Đợt 5–6)

Lỗi lặp lại ở cả 2 diagram Writing và Speaking:   nhánh xử lý khi AI trả kết quả không hợp lệ (Ghi nhận lỗi AI → Chuyển sang chấm thủ công) hiện có nguy cơ merge lại vào cùng nhánh thành công trước khi hiển thị kết quả cho học viên/giáo viên, nếu không tách rõ 2 luồng — nếu để lẫn, hệ thống có thể hiển thị điểm/feedback của lượt chấm bị lỗi (status=FAILED) như thể đã chấm xong.

- Sửa: giữ nhánh lỗi AI tách biệt cho tới khi có kết quả TEACHER_MANUAL thật sự (gradings.status chuyển từ FAILED sang COMPLETED chỉ sau khi giáo viên chấm tay xong) — không gộp merge point trước bước hiển thị kết quả.

- Làm rõ đồng thời: hành động "Chuyển sang chấm thủ công" có đổi gradings.method từ AI sang TEACHER_MANUAL hay chỉ set status=FAILED và để giáo viên tự vào chấm sau — cần chốt 1 cách hiểu để code nhất quán.

# 6. Đề xuất điều chỉnh ERD — tổng hợp theo bảng bị ảnh hưởng

| Bảng | Thay đổi đề xuất | Lý do (tham chiếu) |
| --- | --- | --- |
| assignments | Thêm cột status ENUM (DRAFT/OPEN/CLOSED). Xóa cột is_manually_closed. | Mục 4.1 — bằng chứng từ diagram Tạo bài tập + Mở/Khóa bài tập. |
| answers | Bỏ ràng buộc nghiệp vụ "chỉ 1 trong 2 (content HOẶC audio_storage_key)" — riêng Speaking cho phép có cả 2, content mang nghĩa transcript. | Mục 4.2 — diagram AI chấm chữa Speaking + Xem điểm/Feedback học viên. |
| gradings | Thêm cột adjustment_note (TEXT, nội bộ, ghi đè mỗi lần sửa điểm). Phương án thay thế: thêm bảng grading_change_logs. | Mục 4.3 — diagram Quản lý điểm số, quyết định còn treo. |

Các bảng còn lại trong schema (users, teacher_profiles, student_profiles, classes, class_members, modules, questions, submissions, submission_modules, answer_annotations, criteria_scores) không phát sinh yêu cầu sửa cột/bảng mới — các lỗi liên quan (nếu có) đều thuộc nhóm chỉ cần sửa Activity Diagram (mục 5) hoặc chỉ cần làm rõ trong đặc tả (mục 7).

# 7. Danh sách 🟡 cần làm rõ trong đặc tả (không bắt buộc sửa hình/ERD)

| # | Nội dung cần làm rõ | Diagram liên quan |
| --- | --- | --- |
| 1 | Có cho sửa email trong "Cập nhật thông tin cá nhân" không? Nếu có, cần thêm nhánh check trùng UNIQUE(email) riêng biệt với lỗi "dữ liệu không hợp lệ". | Xem/cập nhật thông tin cá nhân (Đợt 1) |
| 2 | "Xóa lớp học" nên dùng classes.status=ARCHIVED (soft-delete) hay DELETE thật? classes.status đã có sẵn giá trị ARCHIVED trong ENUM. | Quản lý lớp học (Đợt 2) |
| 3 | assignments có cần cột word_limit (giới hạn số từ Writing) không? Cột này từng có trong domain model nháp (BaiTapViet.gioiHanTu) nhưng đã rơi mất khi chốt schema chính thức. | Writing+AI — học viên nộp (Đợt 5) |
| 4 | "Lưu điểm AI" có lặp lưu N dòng criteria_scores (theo từng tiêu chí) hay chỉ 1 điểm tổng? Theo domain rule #6, điểm Writing/Speaking phải tổng hợp từ criteria_scores. | Writing+AI chấm chữa (Đợt 5), Speaking+AI chấm chữa (Đợt 6) |
| 5 | "Điều chỉnh điểm" của giáo viên có đồng bộ lại từng dòng criteria_scores hay chỉ sửa final_score (điểm tổng)? Nếu không đồng bộ, điểm tổng và tổng các tiêu chí hiển thị cho học viên có thể lệch nhau. | Chấm chữa bài — Giáo viên (Đợt 6) |
| 6 | Cần chờ audio_upload_status = READY trước khi cho phép "Mở bài tập" (Listening) / chuyển "Chờ chấm" (Speaking) — upload lên Cloudflare R2 là bất đồng bộ. | Tạo bài tập — Listening (Đợt 3), Speaking — ghi âm & nộp (Đợt 6) |
| 7 | "Chọn bài tập" → "Hiển thị danh sách điểm" cần hiển thị theo từng module/kỹ năng riêng khi 1 assignment có nhiều module, vì mỗi module có 1 dòng gradings riêng — không có cột tính điểm tổng hợp cấp assignment. | Quản lý điểm số (Đợt 7), Xem điểm/Feedback học viên (Đợt 8) |
| 8 | Khi tính điểm trung bình/so sánh giữa các module (thống kê, tổng hợp kết quả, phân tích tiến độ), phải dùng tỷ lệ final_score/max_score_snapshot thay vì điểm thô, vì max_score khác nhau giữa các module — dùng chung 1 công thức ở tầng Backend, tránh lặp code ở 3 nơi khác nhau cho 3 kết quả lệch nhau. | Thống kê điểm (Đợt 8), Tổng hợp kết quả & Phân tích tiến độ — học viên (Đợt 9) |
| 9 | UC033 "Theo dõi tiến độ học tập" (actor Giáo viên) chưa có Activity Diagram riêng trong toàn bộ 9 đợt đã soát — khác UC029 (actor Học viên, đã có diagram). | Ghi chú phạm vi (Đợt 9) |

# 8. Điểm sáng — các nhóm diagram khớp ERD tốt, không cần sửa

- Đổi mật khẩu (Đợt 1), Quản lý lớp học (Đợt 2): khớp ERD gần như hoàn toàn.

- Toàn bộ nhóm Speaking/Writing AI ở phía học viên nộp bài (Đợt 5–6): không có lỗi bịa dữ liệu, chỉ có các điểm cần làm rõ chi tiết kỹ thuật.

- Chấm chữa bài — Giáo viên (Đợt 6): khớp tốt, đúng cơ chế review AI → final_score/final_feedback.

- Thống kê điểm & Theo dõi kết quả học viên (Đợt 8–9): khớp rất tốt với modules.skill ENUM, không phát sinh bảng mới — Phân tích tiến độ (19.2) là diagram sạch nhất toàn bộ đợt soát.

- Việc gắn source_audio_* vào modules (không phải questions) cho Listening được vẽ đúng tinh thần thiết kế ngay từ diagram Tạo bài tập.

- Việc tách 2 lane "Hệ thống" và "AI" riêng biệt ở các diagram chấm AI (Writing/Speaking) thể hiện đúng tinh thần tránh bị đánh giá là "AI wrapper" — có bước xử lý dữ liệu thật (rubric, lưu điểm theo tiêu chí, review thủ công khi lỗi) chứ không chỉ gọi API rồi trả thẳng kết quả.

# 10. Đợt 10 — Soát bản làm lại: bộ 36 Use Case (Activity_Diagrams_36_UseCase.md)

Sau khi nhận file giải trình bản 1.0 (mục 1–8), nhóm đã vẽ lại toàn bộ Activity Diagram theo bộ 36 Use Case mới (thay cho bộ 33 UC trong SRS gốc). Đợt soát này đối chiếu bản vẽ lại với đúng ERD 14 bảng hiện hành (chưa có thay đổi schema mới) và với các quyết định đã chốt ở mục 4–5.

## 10.1. Các lỗi đã được sửa đúng — xác nhận đóng

| Lỗi đã nêu | Bằng chứng đã sửa trong bản mới | Trạng thái |
| --- | --- | --- |
| 4.1 — assignments.status | UC12/13/14 dùng đúng state machine Lưu nháp → Đang mở → Đã đóng, khớp ENUM đã khuyến nghị. | Đã đóng |
| Check max_submissions/attempt_number (mục 5.6) | Tách hẳn thành UC22 "Quản lý lần làm và nộp lại bài", có decision "Còn lượt làm lại?". | Đã đóng |
| 4.3 — lịch sử thay đổi điểm | UC31 chọn hướng (a) Audit log đầy đủ (không chọn adjustment_note). | Đã chốt hướng — cần bổ sung ERD |

## 10.2. Lỗi còn tồn đọng — chưa xử lý (carry-over từ bản 1.0)

### 10.2.1 — UC1 Đăng nhập vẫn dùng "tên đăng nhập"

Bước "Nhập tên đăng nhập / mật khẩu" chưa đổi. Bảng users không có cột username, chỉ có email (UNIQUE). → Vẫn cần đổi thành "Nhập email", đúng như lỗi 1.1 đã nêu ở bản giải trình 1.0.

### 10.2.2 — UC2 Đăng xuất chưa chốt kiến trúc token

Bước "Hủy phiên làm việc, xóa token/session" vẫn mô tả chung chung, không rõ có bảng lưu hay không. → Cần họp chốt 1 trong 2 hướng (JWT stateless hoặc có bảng refresh_tokens) rồi sửa lại đúng theo hướng đã chọn — đây là quyết định kiến trúc duy nhất trong toàn bộ giải trình vẫn chưa được xử lý dù đã nêu từ Đợt 1.

### 10.2.3 — UC5 Phân quyền người dùng vẫn cho đổi role tự do

Nhánh vẫn giữ nguyên: chọn tài khoản đã tồn tại → chọn vai trò mới (Giáo viên/Học viên/Quản lý) → cập nhật. Điều này vẫn mâu thuẫn với kiến trúc shared-PK subtype (teacher_profiles/student_profiles) đã phân tích ở lỗi 5.2 — đổi role một tài khoản đã có dữ liệu (VD học viên đã có submissions) sẽ không có quy tắc xử lý dữ liệu cũ. UC4 (Quản lý tài khoản người dùng) cũng đang include UC5 khi tạo mới tài khoản lẫn khi đổi role sau này, khiến 2 tình huống khác bản chất bị gộp làm 1.

- Khuyến nghị giữ nguyên như đã đề xuất ở mục 5.2: tách UC5 thành 2 luồng riêng — "gán role lúc tạo tài khoản" (dùng trong UC4, không phải sửa role, chỉ set 1 lần) và bỏ hẳn khả năng đổi role cho tài khoản đã tồn tại, trừ khi PM xác nhận cần và bổ sung rule xử lý dữ liệu cũ.

## 10.3. Tính năng mới trong bản 36 UC — ngoài phạm vi ERD 14 bảng hiện hành

Đã xác nhận với nhóm: ERD vẫn giữ nguyên 14 bảng, các tính năng dưới đây trong bản vẽ mới mới chỉ là ý tưởng, chưa được chốt vào schema. Vì vậy các Activity Diagram tương ứng hiện đang mô tả nghiệp vụ mà hệ thống chưa có chỗ lưu — không được đưa vào SRS chính thức cho tới khi có 1 trong 2 hướng: (a) bỏ khỏi phạm vi bản nộp hiện tại, hoặc (b) được duyệt bổ sung ERD/migration riêng.

| UC | Tính năng mới | Thiếu gì trong ERD 14 bảng |
| --- | --- | --- |
| UC36 | Lọc báo cáo theo "chi nhánh" | Không có bảng branches/chi nhánh nào; classes, users hiện không có cột liên kết chi nhánh. |
| UC6, UC7 | Import Excel hàng loạt giáo viên/học viên | Không có cơ chế bulk-insert nào được thiết kế ở tầng service lẫn schema. |
| UC12 | Import ngân hàng câu hỏi từ Excel/Word | questions hiện chỉ hỗ trợ insert đơn lẻ qua form, chưa có đặc tả cho import hàng loạt. |
| UC9 | Chuyển học viên từ lớp A sang lớp B | class_members chưa có business rule riêng cho thao tác này — khác hẳn add/remove đơn thuần, cần xử lý cả 2 dòng cùng lúc. |
| UC20 | Auto-save 30 giây + khôi phục bài nháp | Không có bảng/cột nào lưu trạng thái nháp tạm thời tách biệt với answers chính thức. |
| UC26 | Phát hiện đạo văn (Plagiarism) | Không có pipeline hay cột nào lưu kết quả plagiarism — ngoài phạm vi Writing Evaluation Pipeline đã tài liệu hóa. |
| UC31 | Audit log đầy đủ cho lịch sử điểm | Cần bảng grading_change_logs mới (đã nêu ở mục 4.3, phương án thay thế) — team đã chọn hướng này nên đây là hạng mục ưu tiên bổ sung ERD sớm nhất trong nhóm này. |

Lưu ý riêng UC31:   đây là trường hợp khác các dòng còn lại — team đã chủ động chọn hướng "Audit log đầy đủ" ở mục 4.3 (thay vì adjustment_note), nên không còn là quyết định treo nữa, chỉ còn thiếu bước cập nhật ERD (thêm bảng grading_change_logs) để khớp với lựa chọn đã chốt. Các dòng UC36/6/7/12/9/20/26 khác vẫn là ý tưởng chưa chốt, cần PM/giảng viên duyệt phạm vi trước khi tính vào schema.

## 10.4. Tổng hợp Đợt 10

| Nhóm | Việc cần làm | Ưu tiên |
| --- | --- | --- |
| Carry-over 3 lỗi (10.2) | Sửa UC1 (email), chốt kiến trúc token cho UC2, giới hạn lại UC5. | Cao — cần xong trước khi nộp SRS |
| UC31 — Audit log | Bổ sung bảng grading_change_logs vào migration + ERD_reference.md, khớp với hướng đã chọn. | Cao — đã chốt hướng, chỉ còn thiếu ERD |
| 6 tính năng còn lại (10.3) | PM xác nhận có đưa vào phạm vi bản nộp hiện tại không. Nếu có → làm giải trình ERD riêng cho từng mục trước khi giữ trong Activity Diagram. Nếu không → gỡ khỏi bản vẽ hoặc ghi chú rõ "đề xuất mở rộng, ngoài phạm vi hiện tại". | Trung bình — không chặn tiến độ nếu ghi chú rõ |

# 11. Kết luận và bước tiếp theo

Qua 10 đợt soát (9 đợt bản gốc 33 UC + 1 đợt soát lại bản 36 UC), nhóm đã xử lý dứt điểm phần lớn lỗi kiến trúc quan trọng: trạng thái bài tập, số lần nộp lại, và đã chốt hướng cho lịch sử thay đổi điểm. Còn lại 3 lỗi carry-over (đăng nhập dùng sai trường, kiến trúc token chưa chốt, phân quyền mâu thuẫn shared-PK subtype) và 1 việc bổ sung ERD cho audit log — đều là các hạng mục có phương án rõ ràng, chỉ cần thực hiện.

Bộ 36 UC cũng cho thấy nhóm đang có xu hướng mở rộng phạm vi nghiệp vụ (chi nhánh, import Excel, plagiarism...) nhanh hơn tốc độ cập nhật ERD — cần kiểm soát chặt trước khi những ý tưởng này lọt vào SRS chính thức mà chưa có chỗ lưu dữ liệu, để tránh lặp lại đúng vấn đề đã mất công xử lý ở 9 đợt đầu.

Đề xuất các bước tiếp theo:

- Sửa dứt điểm 3 lỗi carry-over ở mục 10.2 — đây là các lỗi đã được nêu từ Đợt 1 (giải trình bản 1.0) nhưng qua 1 lần làm lại vẫn chưa xử lý, cần ưu tiên cao nhất.

- Bổ sung bảng grading_change_logs vào migration V2 để khớp với quyết định Audit log đã chọn ở UC31.

- PM họp chốt phạm vi 6 tính năng mới ở mục 10.3 — quyết định giữ hay bỏ khỏi bản nộp hiện tại, tránh để Activity Diagram "đi trước" ERD quá xa.

- Sau khi chốt toàn bộ, cập nhật lại 1 lần cuối ERD_reference.md, giai-thich-schema-15-bang.md và migration, rồi đối chiếu lại danh sách 36 UC lần cuối trước khi đóng gói vào SRS chính thức.

- Bổ sung Activity Diagram còn thiếu cho UC035 (Theo dõi tiến độ học tập — Giáo viên) nếu bộ 36 UC hiện tại chưa có (đã kiểm tra thấy UC35 trong bản mới trùng đúng nội dung này — xác nhận đã được bổ sung, đóng mục ghi chú phạm vi cũ).

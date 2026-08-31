# TÀI LIỆU THAM CHIẾU VẼ ERD

## Website quản lý bài tập & chấm chữa bài tiếng Anh thông minh — Group 4

### Tổng: 14 bảng + 1 view

*(Bản cập nhật theo yêu cầu PM: bỏ bảng `notifications`, thêm trường lưu voice vào `answers`. Vẫn giữ nền `V1__init_schema.sql` — đã tách `teacher_profiles` / `student_profiles` khỏi `users` và rà soát chuẩn hóa 3NF)*

---

## 1. DANH SÁCH BẢNG VÀ THUỘC TÍNH

### 1.1. users

Chỉ giữ thông tin định danh + xác thực dùng chung cho cả 3 role.

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| full_name | VARCHAR(150) | NOT NULL |
| email | VARCHAR(150) | UNIQUE, NOT NULL |
| phone | VARCHAR(20) | |
| avatar_url | VARCHAR(255) | |
| password_hash | VARCHAR(255) | NOT NULL |
| role | ENUM(ADMIN, TEACHER, STUDENT) | NOT NULL |
| status | ENUM(ACTIVE, LOCKED) | DEFAULT ACTIVE |
| is_deleted | BOOLEAN | DEFAULT false |
| created_at | TIMESTAMPTZ | |
| updated_at | TIMESTAMPTZ | |

> `student_code` và `specialization` **không còn** ở bảng này — đã tách xuống 2 bảng profile bên dưới (xem mục 5.1).

### 1.2. teacher_profiles *(bảng mới)*

Subtype của `users`, dùng shared-PK — chỉ tồn tại row khi `role = TEACHER`.

| Cột | Kiểu | Ghi chú |
|---|---|---|
| user_id | BIGINT | **PK, FK → users.id** |
| specialization | VARCHAR(150) | |

### 1.3. student_profiles *(bảng mới)*

Subtype của `users`, dùng shared-PK — chỉ tồn tại row khi `role = STUDENT`.

| Cột | Kiểu | Ghi chú |
|---|---|---|
| user_id | BIGINT | **PK, FK → users.id** |
| student_code | VARCHAR(30) | UNIQUE |

### 1.4. classes

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| name | VARCHAR(150) | NOT NULL |
| level | VARCHAR(50) | |
| description | TEXT | |
| start_date | DATE | NOT NULL |
| end_date | DATE | |
| status | VARCHAR(20) | ACTIVE/ENDED/ARCHIVED |
| teacher_id | BIGINT | **FK → teacher_profiles.user_id** |
| created_at | TIMESTAMPTZ | |
| updated_at | TIMESTAMPTZ | |

### 1.5. class_members

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| class_id | BIGINT | **FK → classes.id** |
| student_id | BIGINT | **FK → student_profiles.user_id** |
| member_type | ENUM(PRIMARY, SUPPLEMENTARY) | NOT NULL |
| status | ENUM(ACTIVE, ENDED) | DEFAULT ACTIVE |
| created_at | TIMESTAMPTZ | |

Ràng buộc: UNIQUE(class_id, student_id); unique index riêng — 1 student chỉ có 1 PRIMARY active tại 1 thời điểm.

### 1.6. assignments

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| class_id | BIGINT | **FK → classes.id** |
| teacher_id | BIGINT | **FK → teacher_profiles.user_id** |
| title | VARCHAR(200) | NOT NULL |
| description | TEXT | |
| open_at | TIMESTAMPTZ | NOT NULL |
| close_at | TIMESTAMPTZ | NOT NULL, CHECK open_at < close_at |
| max_submissions | INT | NULL=không giới hạn, 0=không cho nộp lại, N=cho nộp lại N lần |
| is_manually_closed | BOOLEAN | DEFAULT false |
| is_deleted | BOOLEAN | DEFAULT false (soft delete) |
| created_at | TIMESTAMPTZ | |
| updated_at | TIMESTAMPTZ | |

### 1.7. modules

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| assignment_id | BIGINT | **FK → assignments.id** (ON DELETE CASCADE) |
| skill | ENUM(READING, LISTENING, WRITING, SPEAKING) | NOT NULL |
| task_type | ENUM(QUIZ, REWRITE, RECORDING, ESSAY) | NOT NULL |
| order_index | INT | NOT NULL |
| instructions | TEXT | |
| max_score | NUMERIC(5,2) | DEFAULT 10 |
| source_audio_storage_key | VARCHAR(255) | NULLABLE — object key file audio **đề bài** trên Cloudflare R2 (dùng khi `skill = LISTENING`), giáo viên upload sẵn khi soạn đề. KHÔNG lưu URL trực tiếp, cùng nguyên tắc presigned URL như `answers` |
| source_audio_duration_seconds | INT | NULLABLE |
| source_audio_mime_type | VARCHAR(50) | NULLABLE |
| source_audio_upload_status | ENUM(UPLOADING, PROCESSING, READY, FAILED) | NULLABLE |

Ràng buộc: UNIQUE(assignment_id, order_index)

> Tiền tố `source_audio_*` (khác `audio_*` bên `answers`) để phân biệt rõ: đây là audio **đề bài** (input, giáo viên upload), còn `answers.audio_*` là audio **bài làm** (output, học viên nộp). Không ràng buộc "1 trong 2" như `answers`, vì cột này chỉ có giá trị khi `skill = LISTENING`, không xung đột với `instructions`.

### 1.8. questions

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| module_id | BIGINT | **FK → modules.id** (ON DELETE CASCADE) |
| content | TEXT | NOT NULL |
| question_type | ENUM(MULTIPLE_CHOICE, SHORT_ANSWER) | NOT NULL |
| correct_answer | TEXT | |
| score | NUMERIC(5,2) | DEFAULT 1 |
| order_index | INT | NOT NULL |

### 1.9. submissions

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| assignment_id | BIGINT | **FK → assignments.id** |
| student_id | BIGINT | **FK → student_profiles.user_id** |
| attempt_number | INT | NOT NULL |
| submitted_at | TIMESTAMPTZ | |
| status | ENUM(IN_PROGRESS, SUBMITTED, GRADED) | DEFAULT IN_PROGRESS |
| created_at | TIMESTAMPTZ | |
| updated_at | TIMESTAMPTZ | |

Ràng buộc: UNIQUE(assignment_id, student_id, attempt_number)

### 1.10. submission_modules

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| submission_id | BIGINT | **FK → submissions.id** (ON DELETE CASCADE) |
| module_id | BIGINT | **FK → modules.id** |
| status | ENUM(IN_PROGRESS, SUBMITTED, GRADED) | DEFAULT IN_PROGRESS |

Ràng buộc: UNIQUE(submission_id, module_id)

### 1.11. answers

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| submission_module_id | BIGINT | **FK → submission_modules.id** (ON DELETE CASCADE) |
| question_id | BIGINT | **FK → questions.id**, NULLABLE (null nếu bài tự do) |
| content | TEXT | text trả lời (dùng cho QUIZ/REWRITE/SHORT_ANSWER); NULL nếu là câu trả lời dạng voice |
| audio_storage_key | VARCHAR(255) | NULLABLE — **object key** của file trên Cloudflare R2 (VD `submissions/2026/08/assignment-123/student-456/uuid.mp3`), KHÔNG lưu URL trực tiếp (xem ghi chú bên dưới) |
| audio_duration_seconds | INT | NULLABLE — thời lượng file ghi âm |
| audio_file_size_bytes | BIGINT | NULLABLE — dung lượng file, phục vụ giới hạn upload |
| audio_mime_type | VARCHAR(50) | NULLABLE — VD: audio/mpeg, audio/wav |
| audio_upload_status | ENUM(UPLOADING, PROCESSING, READY, FAILED) | NULLABLE — trạng thái upload/xử lý file trên R2 |
| created_at | TIMESTAMPTZ | |

> Ràng buộc nghiệp vụ (xử lý ở service, không CHECK ở DB): đúng 1 trong 2 — `content` có giá trị (bài text) HOẶC `audio_storage_key` có giá trị (bài voice) — không cùng lúc NULL cả 2, không cùng lúc có cả 2.

> **Vì sao lưu `storage_key` chứ không lưu URL cố định?** R2 để **private**, không public bucket. BE chỉ sinh **presigned URL có hạn dùng** (VD 15 phút) từ `storage_key` tại đúng thời điểm client cần nghe/upload — không lưu sẵn URL trong DB. Lý do: nếu lưu URL cố định public, ai có link cũng nghe được vĩnh viễn, không kiểm soát được quyền truy cập (học viên khác lớp, người ngoài hệ thống). Đây đúng theo kiến trúc presigned URL đã chốt trong báo cáo DevOps.

### 1.12. answer_annotations

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| answer_id | BIGINT | **FK → answers.id** (ON DELETE CASCADE) |
| source | ENUM(AI, TEACHER) | NOT NULL |
| start_offset | INT | NOT NULL |
| end_offset | INT | NOT NULL, CHECK end_offset >= start_offset |
| error_type | VARCHAR(50) | grammar/spelling/vocabulary/sentence_structure... |
| comment | TEXT | |
| suggested_fix | TEXT | |
| created_at | TIMESTAMPTZ | |

### 1.13. gradings

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| submission_module_id | BIGINT | **FK → submission_modules.id**, UNIQUE (ON DELETE CASCADE) |
| method | ENUM(AUTO, AI, TEACHER_MANUAL) | NOT NULL |
| status | ENUM(PENDING, COMPLETED, FAILED) | DEFAULT PENDING |
| ai_suggested_score | NUMERIC(5,2) | chỉ có khi method=AI |
| ai_feedback | TEXT | |
| final_score | NUMERIC(5,2) | giá trị chính thức |
| final_feedback | TEXT | |
| max_score_snapshot | NUMERIC(5,2) | chốt max_score tại thời điểm chấm |
| reviewed_by | BIGINT | **FK → teacher_profiles.user_id**, NULLABLE |
| reviewed_at | TIMESTAMPTZ | |
| graded_at | TIMESTAMPTZ | |

### 1.14. criteria_scores

| Cột | Kiểu | Ghi chú |
|---|---|---|
| id | BIGSERIAL | PK |
| grading_id | BIGINT | **FK → gradings.id** (ON DELETE CASCADE) |
| criteria_name | VARCHAR(100) | NOT NULL (Grammar/Vocabulary/Spelling/Sentence structure/Coherence/Relevance/Writing quality) |
| score | NUMERIC(5,2) | NOT NULL |
| feedback | TEXT | |

---

## 2. DANH SÁCH QUAN HỆ (VẼ ĐƯỜNG NỐI + MULTIPLICITY)

| # | Từ bảng | Đến bảng | Qua cột FK | Multiplicity | Loại đường |
|---|---|---|---|---|---|
| 1 | teacher_profiles | users | user_id | 1 — 1 (shared-PK) | thường |
| 2 | student_profiles | users | user_id | 1 — 1 (shared-PK) | thường |
| 3 | classes | teacher_profiles | teacher_id | N — 1 | thường |
| 4 | class_members | classes | class_id | N — 1 | thường |
| 5 | class_members | student_profiles | student_id | N — 1 | thường |
| 6 | assignments | classes | class_id | N — 1 | thường |
| 7 | assignments | teacher_profiles | teacher_id | N — 1 | thường |
| 8 | modules | assignments | assignment_id | N — 1 | CASCADE |
| 9 | questions | modules | module_id | N — 1 | CASCADE |
| 10 | submissions | assignments | assignment_id | N — 1 | thường |
| 11 | submissions | student_profiles | student_id | N — 1 | thường |
| 12 | submission_modules | submissions | submission_id | N — 1 | CASCADE |
| 13 | submission_modules | modules | module_id | N — 1 | thường |
| 14 | answers | submission_modules | submission_module_id | N — 1 | CASCADE |
| 15 | answers | questions | question_id | N — 1 (nullable) | thường |
| 16 | answer_annotations | answers | answer_id | N — 1 | CASCADE |
| 17 | gradings | submission_modules | submission_module_id | **1 — 1** (UNIQUE) | CASCADE |
| 18 | gradings | teacher_profiles | reviewed_by | N — 1 (nullable) | thường |
| 19 | criteria_scores | gradings | grading_id | N — 1 | CASCADE |

---

## 3. GỢI Ý BỐ CỤC KHI VẼ (nhóm theo cụm chức năng)

**Cụm 1 — Con người & Lớp học** (đặt bên trái)
`users` → `teacher_profiles` / `student_profiles` → `classes` → `class_members`

**Cụm 2 — Cấu trúc bài tập** (đặt giữa trên)
`assignments` → `modules` → `questions`

**Cụm 3 — Bài làm học viên** (đặt giữa dưới)
`submissions` → `submission_modules` → `answers` → `answer_annotations`

**Cụm 4 — Chấm điểm** (đặt bên phải)
`gradings` → `criteria_scores`

Gợi ý: vẽ `users` ở trung tâm vì nó vẫn là gốc của toàn bộ nhánh role (qua `teacher_profiles`/`student_profiles`) — đặt giữa sẽ giảm số đường dây chéo nhau. Sau khi bỏ `notifications`, `users` chỉ còn 2 đường ra (`teacher_profiles`, `student_profiles`) nên bố cục sẽ gọn hơn hẳn bản trước.

---

## 4. VIEW (không vẽ như bảng, ghi chú riêng nếu cần)

**ai_calibration_report** — tổng hợp từ `gradings` JOIN `submission_modules` JOIN `modules`, group theo skill + tuần (chỉ tính khi `method = 'AI'` và đã có `reviewed_by`). Không lưu dữ liệu riêng, chỉ tính từ 3 bảng trên → có thể vẽ bằng hình chữ nhật nét đứt hoặc ghi chú bên cạnh `gradings`, không cần vẽ như 1 entity riêng.

---

## 5. LƯU Ý & RÀNG BUỘC (RÀ SOÁT CHUẨN HÓA 3NF)

Nguyên tắc kiểm tra: mọi cột không khóa phải phụ thuộc **đầy đủ** vào khóa chính và **không phụ thuộc bắc cầu** qua một cột không khóa khác.

**Kết quả: 14/14 bảng đạt 3NF.** Có 5 điểm cần giải trình khi bị hỏi — đây là quyết định thiết kế có chủ đích, không phải lỗi.

### 5.1. Tách `teacher_profiles` / `student_profiles` khỏi `users`

- Trước đây `student_code`, `specialization` nằm chung trong `users`, chỉ có giá trị tùy theo `role`. Rủi ro: (a) cột NULL tràn lan theo role; (b) các FK ở bảng khác trỏ thẳng vào `users.id` không ràng buộc được đúng role ở tầng DB (ví dụ có thể gán nhầm 1 student làm `teacher_id`).
- **Đã sửa**: tách thành 2 bảng shared-PK subtype (`teacher_profiles.user_id` / `student_profiles.user_id` vừa là PK vừa là FK → `users.id`). Mọi FK gắn với role cụ thể (`classes.teacher_id`, `assignments.teacher_id`, `class_members.student_id`, `submissions.student_id`, `gradings.reviewed_by`) đổi sang trỏ vào bảng profile tương ứng — DB tự chặn sai role ngay khi insert, không cần trigger hay CHECK subquery.

### 5.2. `assignments.teacher_id` — denormalization có chủ đích

- Về lý thuyết đây là phụ thuộc bắc cầu: `assignments.id → class_id → (qua classes) → teacher_id`. Vì 1 lớp chỉ có đúng 1 giáo viên phụ trách tại 1 thời điểm nên xét thuần 3NF, cột này là dư thừa.
- **Cố ý giữ lại**: nếu sau này lớp đổi giáo viên phụ trách (`classes.teacher_id` thay đổi), `assignments.teacher_id` vẫn cần giữ nguyên "ai là người thực sự tạo bài tập này" để phục vụ audit/lịch sử, không bị ghi đè theo giáo viên mới.
- Rule kèm theo (xử lý ở service, không ở DB): khi tạo assignment, validate `teacher_id` phải trùng `classes.teacher_id` tại **thời điểm tạo**; sau đó cho phép lệch nếu lớp đổi giáo viên.

### 5.3. `gradings.max_score_snapshot` — temporal snapshot bắt buộc

- Có thể suy ra từ `submission_module_id → module_id → modules.max_score`, nên xét thuần lý thuyết cũng là dữ liệu suy ra được (derived data).
- **Cố ý giữ lại**: nếu giáo viên sửa `modules.max_score` **sau khi** đã chấm bài mà không có snapshot, công thức tính điểm cuối sẽ tính sai hồi tố cho toàn bộ bài đã chấm trước đó. Bắt buộc đóng băng giá trị tại thời điểm chấm — đây là yêu cầu toàn vẹn dữ liệu theo thời gian, không phải lỗi chuẩn hóa.

### 5.4. Bỏ bảng `notifications`

- Theo yêu cầu PM, bỏ khỏi phạm vi thiết kế đợt này. Không có bảng nào khác FK vào `notifications`, nên việc bỏ **không ảnh hưởng** tới bất kỳ bảng nào còn lại — an toàn để xóa mà không phải sửa domino sang bảng khác.
- Nếu sau này PM yêu cầu thêm lại, cấu trúc cũ (tham khảo bản trước) vẫn dùng được nguyên vẹn.

### 5.5. Thêm trường lưu voice vào `answers`

- Trước đây `answers.content` (TEXT) dùng chung cho cả text trả lời lẫn URL file ghi âm — gộp 2 loại dữ liệu khác bản chất vào 1 cột.
- **Đã sửa**: thêm các cột riêng cho voice (`audio_storage_key`, `audio_duration_seconds`, `audio_file_size_bytes`, `audio_mime_type`, `audio_upload_status`), giữ `content` chỉ dùng cho câu trả lời dạng text. DB chỉ lưu **object key + metadata**, không lưu URL cố định — file thật nằm trên R2.
- **Vì sao `audio_storage_key` chứ không phải `audio_url`?** R2 để private, không public bucket. Mỗi lần cần nghe/upload, BE sinh **presigned URL có hạn dùng** (VD 15 phút) từ `storage_key` tại đúng thời điểm request — không lưu sẵn URL cố định trong DB, tránh việc ai có link cũng truy cập được vĩnh viễn. Đúng theo kiến trúc presigned URL đã chốt trong báo cáo DevOps của team.
- `audio_upload_status` cần thiết vì upload lên R2 là bất đồng bộ — FE cần biết file đã sẵn sàng (`READY`) hay còn đang xử lý (`PROCESSING`) trước khi cho phép sinh presigned URL để phát lại hoặc gửi AI chấm.
- Đánh đổi: nhiều cột NULL hơn (khi answer là text thì 5 cột audio đều NULL, và ngược lại). Chấp nhận được vì đây chỉ là 1 bảng, không lặp lại ở nhiều bảng như trường hợp `users` — không cần tách bảng riêng cho mức độ này.

### 5.6. Thêm trường lưu audio đề bài vào `modules` (phục vụ Listening)

- Trước đây schema chỉ xử lý audio **học viên nộp** (`answers.audio_*`), thiếu chỗ lưu audio **đề bài** mà học viên phải nghe trước khi trả lời (bài Listening).
- **Đã sửa**: thêm `source_audio_storage_key`, `source_audio_duration_seconds`, `source_audio_mime_type`, `source_audio_upload_status` vào `modules`. Gắn ở cấp `modules` chứ không phải `questions`, vì 1 bài Listening thường là 1 đoạn audio dài kèm nhiều câu hỏi nhỏ — không phải mỗi câu hỏi có 1 file audio riêng.
- Tiền tố `source_audio_*` (khác `audio_*` bên `answers`) để phân biệt: đây là audio **đề bài** (input, giáo viên upload khi soạn đề), còn `answers.audio_*` là audio **bài làm** (output, học viên nộp). Cùng nguyên tắc lưu `storage_key` + sinh presigned URL khi cần, không lưu URL cố định.
- Không cần ràng buộc "1 trong 2" như `answers`, vì các cột này chỉ có giá trị khi `skill = LISTENING`, không xung đột với `instructions` (vẫn dùng cho hướng dẫn dạng chữ ở mọi skill).

### 5.7. Các ràng buộc UNIQUE / CHECK chính cần thể hiện trên ERD

| Bảng | Ràng buộc |
|---|---|
| users | UNIQUE(email) |
| teacher_profiles | PK = FK → users.id |
| student_profiles | PK = FK → users.id; UNIQUE(student_code) |
| class_members | UNIQUE(class_id, student_id); unique partial index — 1 student chỉ 1 PRIMARY active |
| assignments | CHECK(open_at < close_at) |
| modules | UNIQUE(assignment_id, order_index) |
| submissions | UNIQUE(assignment_id, student_id, attempt_number) |
| submission_modules | UNIQUE(submission_id, module_id) |
| answer_annotations | CHECK(end_offset >= start_offset) |
| gradings | UNIQUE(submission_module_id) — thể hiện quan hệ 1–1 với submission_modules |

### 5.8. Index phục vụ truy vấn (không bắt buộc vẽ trên ERD, nhưng nên liệt kê khi bảo vệ đồ án)

`classes(teacher_id)`, `class_members(student_id)`, `class_members(class_id)`, `assignments(class_id)`, `modules(assignment_id)`, `questions(module_id)`, `submissions(assignment_id, student_id)`, `submission_modules(submission_id)`, `answers(submission_module_id)`, `answer_annotations(answer_id)`, `criteria_scores(grading_id)`.

### 5.9. Các bảng đã rà soát đạt 3NF sạch, không cần điều chỉnh

`class_members`, `modules`, `questions`, `submissions`, `submission_modules`, `answers`, `answer_annotations`, `criteria_scores` — mọi cột không khóa đều phụ thuộc trực tiếp và đầy đủ vào khóa chính của đúng bảng đó, không có cột nào suy ra được từ bảng khác.

# GIẢI THÍCH CHI TIẾT THAY ĐỔI SCHEMA — BẢN V2 (DELTA)

## Website quản lý bài tập & chấm chữa bài tiếng Anh thông minh — Group 4

*(Tài liệu này giải thích LÝ DO của từng thay đổi trong V2, dùng kèm `ERD_reference_V2.md`. Các bảng/cột không nhắc ở đây giữ nguyên như bản V1 — xem `giai-thich-schema-17-bang.md`.)*

**Nguồn gốc**: Bug report của Tester (16 bug, đặt tên BUG_DB_01 → BUG_DB_16) sau khi review schema V1, cộng thêm 2 điểm phát sinh từ thảo luận flow AI grading trong nhóm chat (Tùng, Tiến Sơn, Thái Sơn).

---

## PHẦN A — 2 BẢNG MỚI (khắc phục Blocker)

### 1. `question_options`

**Vấn đề gốc (BUG_DB_01)**: Bảng `questions` ở V1 có `question_type = MULTIPLE_CHOICE` nhưng không có chỗ nào lưu nội dung các lựa chọn (A, B, C, D) để hiển thị cho học viên chọn. Đây là lỗi Blocker thật sự — không có bảng này thì tính năng làm bài trắc nghiệm (UC018 — Làm bài tập đọc, và phần trắc nghiệm của UC019 — Làm bài tập nghe) không thể build được, vì FE không có dữ liệu gì để render.

**Vì sao tách bảng riêng thay vì lưu JSONB trong `questions`?** Vì `is_correct` cần so sánh trực tiếp khi chấm tự động (`answer_results`) — để dạng JSONB sẽ phải parse mỗi lần chấm, tách bảng quan hệ chuẩn giúp query đơn giản và tận dụng được index/FK thay vì xử lý JSON thủ công ở tầng service.

### 2. `answer_results`

**Vấn đề gốc (BUG_DB_04)**: Hệ thống tự động chấm bài Đọc/Nghe (UC026, UC027 trong `Detailed_Usecase.docx`) nhưng schema V1 chỉ có tổng điểm ở `gradings.final_score`, không lưu chi tiết đúng/sai từng câu. Hậu quả: học viên xem bài chữa (UC030) không biết mình sai ở câu nào — vi phạm thẳng mục tiêu nghiệp vụ của tính năng "xem bài chữa".

**Vì sao 1-1 với `answers` (UNIQUE answer_id) chứ không phải nhiều-1?** Vì mỗi câu trả lời chỉ được chấm tự động đúng 1 lần (không giống `gradings` có thể sửa điểm nhiều lần qua `grading_change_logs` — auto-grade Đọc/Nghe là chấm máy, không có khái niệm "giáo viên sửa lại điểm từng câu" ở mức granular này).

---

## PHẦN B — CÁC BẢNG SỬA CỘT

### 3. `classes.status`: VARCHAR → ENUM

**Vấn đề gốc (BUG_DB_10)**: Cột tự do dễ bị rác dữ liệu (`"ACTIVE"`, `"Active"`, `"actived"` đều hợp lệ với varchar, nhưng có nghĩa khác nhau khi so sánh chuỗi). Đổi sang ENUM để Postgres tự chặn giá trị sai ngay ở tầng DB, không phải chờ code service validate.

### 4. `assignments.status`: thêm ENUM DRAFT/PUBLISHED/CLOSED

**Vấn đề gốc (BUG_DB_09)**: UC012 (Tạo bài tập) trong `Detailed_Usecase.docx` có hậu điều kiện *"Bài tập được tạo ở trạng thái phù hợp"* — ngầm định có nhiều trạng thái, bao gồm cả lúc mới soạn (chưa công bố). Schema V1 chỉ có `open_at`/`close_at` (theo thời gian) + `is_manually_closed` (đóng sớm thủ công) — không có khái niệm "nháp, giáo viên soạn xong nhưng chưa cho học viên thấy". PM Tùng đã xác nhận CÓ cần trạng thái này.

**Vấn đề còn treo**: `is_manually_closed` (boolean) và `status` (enum) giờ có khả năng **lưu trùng ý nghĩa** — cả 2 đều thể hiện việc "đóng bài tập trước hạn". Nếu giữ cả 2 mà code không đồng bộ cẩn thận, rất dễ xảy ra tình huống `status='CLOSED'` nhưng `is_manually_closed=false` (hoặc ngược lại) — dữ liệu tự mâu thuẫn với chính nó. Khuyến nghị: drop `is_manually_closed`, dùng đúng 1 cột `status` làm nguồn chân lý duy nhất. Chờ Sơn/PM xác nhận cuối trước khi đưa vào migration.

### 5. `submissions`: thêm 2 CHECK constraint

**Vấn đề gốc (BUG_DB_13 + phần của BUG_DB_06)**: Không có gì ngăn dữ liệu tự mâu thuẫn — VD status=SUBMITTED nhưng `submitted_at` vẫn NULL (không có mốc thời gian nộp), hoặc `attempt_number = 0` (vô nghĩa, lần thử phải tính từ 1). CHECK constraint là cách rẻ nhất để DB tự bảo vệ tính toàn vẹn, không phải chờ đúng logic code service mọi lúc.

### 6. `answers`: thêm UNIQUE + 2 CHECK

**Vấn đề gốc (BUG_DB_05 + phần của BUG_DB_06)**: Thiếu UNIQUE(`submission_module_id`, `question_id`) khiến học viên có thể double-click nộp và tạo ra 2 dòng answer cho cùng 1 câu hỏi trong cùng 1 lần làm bài — dữ liệu trùng lặp, dễ gây sai lệch khi tính điểm. CHECK duration/file_size >= 0 chặn dữ liệu vật lý vô nghĩa (không có file âm thanh dài -5 giây).

### 7. `questions.score`: thêm CHECK >= 0

Cùng lý do BUG_DB_06 — điểm câu hỏi âm là vô nghĩa, có thể làm sai tổng điểm bài tập nếu lọt vào tính toán.

### 8. `class_members`: thêm `ended_at` + đổi UNIQUE thành partial index

**Vấn đề gốc (BUG_DB_15 + BUG_DB_16)**: UNIQUE(`class_id`, `student_id`) cứng ở V1 vô tình chặn luôn trường hợp hợp lệ: học viên **rời lớp rồi học lại lớp đó lần 2** (VD học lại vì trượt, hoặc bảo lưu rồi quay lại) — vì cặp (class_id, student_id) đã tồn tại 1 lần dù dòng cũ đã ở trạng thái ENDED. Giải pháp: đổi thành partial unique index chỉ tính khi `status = 'ACTIVE'`, cho phép nhiều dòng lịch sử ENDED cùng 1 cặp class-student, chỉ chặn trùng khi đang active. Thêm `ended_at` để biết chính xác thời điểm rời lớp, phục vụ thống kê lịch sử.

### 9. `modules.ai_instruction` (cột mới, không thuộc 16 bug gốc)

**Phát sinh từ**: thảo luận flow AI grading trong nhóm — chốt rằng AI **không chấm theo rubric có cấu trúc/điểm breakdown**, mà chỉ nhận **hướng dẫn dạng văn bản tự do do giáo viên viết** (kiểu prompt engineering), rồi trả về **nhận xét dạng text** (`gradings.ai_feedback` — cột này đã có sẵn từ V1, không cần thêm).

**Vì sao đặt ở `modules` chứ không phải `assignments` hay `gradings`?**
- Không đặt ở `assignments`: vì 1 assignment có tới 4 module (Nghe/Nói/Đọc/Viết), mỗi kỹ năng cần hướng dẫn chấm khác nhau hoàn toàn (hướng dẫn cho Writing khác Speaking) — gộp chung ở cấp assignment sẽ mất khả năng tùy biến theo từng kỹ năng.
- Không đặt ở `gradings`: vì hướng dẫn là thứ giáo viên soạn 1 lần lúc tạo bài tập, áp dụng cho mọi học viên nộp vào module đó — không phải thứ thay đổi theo từng lần chấm riêng lẻ.
- `modules` là đúng cấp độ: mỗi module (1 kỹv năng, thuộc 1 assignment cụ thể) có đúng 1 hướng dẫn tương ứng.

**Nullable vì sao?** Giáo viên có thể không viết hướng dẫn — hệ thống vẫn cho AI chấm theo mặc định chung, không bắt buộc phải điền.

**Vấn đề còn treo**: có cần lưu "snapshot" hướng dẫn tại thời điểm chấm vào `gradings` không (tương tự cách `max_score_snapshot` đã làm với điểm tối đa), để tránh trường hợp giáo viên sửa `ai_instruction` sau khi đã có bài chấm cũ, làm dữ liệu lịch sử "nhìn như" được chấm theo hướng dẫn mới dù thực tế chấm theo hướng dẫn cũ? Đề xuất tạm thời: KHÔNG làm snapshot, giữ đơn giản đúng tinh thần đồ án (giống nguyên tắc đã áp dụng với `grading_change_logs` — bảng dữ liệu đơn giản, không phải audit system phức tạp). Cần Sơn xác nhận cuối.

### 10. `gradings`: DROP `ai_suggested_score` + recreate `grading_method_enum`

**Vấn đề gốc**: Phát sinh khi làm rõ lại flow AI grading — thảo luận ban đầu vô tình để lộ giả định sai rằng AI có thể "đề xuất điểm số" (`ai_suggested_score`). Thực tế đã chốt: **AI không bao giờ tự chấm điểm**, chỉ làm 2 việc — chỉ ra lỗi cụ thể (ghi vào `answer_annotations`, bảng đã có sẵn từ V1 với đúng cấu trúc `error_type`/`comment`/`suggested_fix`) và đưa nhận xét tổng quan (`ai_feedback`, cũng đã có từ V1). Điểm số cuối cùng luôn do giáo viên nhập qua `final_score` — không tồn tại khái niệm "điểm AI đề xuất" trong toàn hệ thống.

**Vì sao drop hẳn cột thay vì để nullable không dùng?** Giữ cột chết (luôn NULL, không code nào ghi/đọc) chỉ gây nhiễu cho người đọc sau này (kể cả giảng viên chấm đồ án) — hiểu lầm rằng tính năng "AI đề xuất điểm" từng tồn tại hoặc sắp làm. Drop hẳn để schema phản ánh đúng 100% flow thực tế đã chốt.

**Vì sao phải recreate cả `grading_method_enum` (bỏ giá trị `AI`)?** Vì giá trị `method = 'AI'` mang đúng ý nghĩa sai lệch tương tự — ngầm hiểu "bản chấm này do AI quyết định" — trong khi thực tế mọi bản ghi `gradings` chỉ có thể ở 2 trạng thái phương thức: `AUTO` (máy tự chấm Đọc/Nghe theo đáp án đúng/sai) hoặc `TEACHER_MANUAL` (giáo viên tự nhập điểm, có hoặc không có AI hỗ trợ phân tích lỗi phía sau). Postgres không hỗ trợ `ALTER TYPE ... DROP VALUE` trực tiếp, nên phải: tạo enum mới không có giá trị `AI` → chuyển cột `method` sang dùng enum mới → xóa enum cũ → đổi tên enum mới về tên cũ. Cần đảm bảo không còn dòng nào mang giá trị `method = 'AI'` trong dữ liệu hiện có trước khi chạy bước chuyển đổi này (an toàn nhất là trên môi trường dev đang trống).

**Về tên trạng thái `AI_GRADED` (thêm ở mục grading_status_enum)**: dù tên gọi nghe dễ hiểu lầm là "AI đã cho điểm", nhóm đã thống nhất **giữ nguyên tên này** vì đã quen dùng, hiểu ngầm trong nhóm là "AI đã hoàn tất phân tích/chú thích, đang chờ giáo viên vào chấm điểm" — không đổi thành `AI_REVIEWED` hay `PENDING_TEACHER_REVIEW` như các phương án khác đã cân nhắc.

### 11. `gradings.ai_transcript` (JSONB)

**Vấn đề gốc (BUG_DB_11, đã điều chỉnh kiểu dữ liệu)**: Đề xuất ban đầu của tester là cột `text`, nhưng qua trao đổi xác nhận code AI hiện tại (`Quy trình Đánh giá Kỹ năng.docx`, Giai đoạn 1 — ASR) đã sinh transcript kèm timestamp và độ tự tin (confidence) cho từng từ — cấu trúc lồng nhau, không phải văn bản phẳng. JSONB là kiểu phù hợp để lưu nguyên cấu trúc này mà không mất thông tin, đồng thời Postgres vẫn query được bên trong JSONB nếu cần sau này (khác hẳn so với lưu text thuần rồi phải tự parse ở tầng application).

---

## PHẦN C — BUG ĐÃ ĐÓNG (không cần đổi DB)

- **BUG_DB_02** (gán chéo submission/module/answer sai cha-con), **BUG_DB_03** (rò rỉ quyền teacher/class), **BUG_DB_07** (xung đột content/audio/doc trong `answers`): cả 3 đều là ràng buộc **liên bảng phức tạp hoặc loại trừ lẫn nhau**, Postgres không có cơ chế constraint gọn để chặn (composite FK kiểu này rất cồng kềnh so với lợi ích ở quy mô đồ án) — đúng hướng xử lý là validate chặt ở Service layer của Backend, đã note lại làm tài liệu tham chiếu cho code review sau này, không đổi schema.
- **BUG_DB_08** (thời khóa biểu lớp học): PM Tùng xác nhận hệ thống không quản lý lịch học vật lý — chỉ tập trung giao bài/chấm bài, không cần bảng `class_schedules`.
- **BUG_DB_14** (criteria_name text tự do): Superseded — không còn là vấn đề "text tự do dễ lệch chính tả" nữa, vì bảng `criteria_scores` chứa cột này đã bị DROP hoàn toàn. Xem Phần C.2.

---

## PHẦN C.1 — TÓM TẮT VAI TRÒ AI (đã chốt, để tránh hiểu lầm về sau)

AI trong hệ thống này **KHÔNG CHẤM ĐIỂM**. Bảng dưới đây là nguồn tham chiếu chuẩn cho toàn nhóm khi code phần AI grading:

| AI làm | AI KHÔNG làm |
|---|---|
| Đọc bài làm + `modules.ai_instruction` do giáo viên viết | Tự quyết `final_score` |
| Ghi chú lỗi cụ thể vào `answer_annotations` (source=AI) | Tự quyết hoặc breakdown điểm theo từng tiêu chí |
| Viết nhận xét tổng quan vào `gradings.ai_feedback` | Đề xuất điểm số dưới bất kỳ hình thức nào |

## PHẦN C.2 — DROP: bảng `criteria_scores` và cột đạo văn (mới, ĐÃ CHỐT)

### Bảng `criteria_scores` — DROP TABLE

Thiết kế ban đầu (V1) giả định điểm được breakdown theo tiêu chí cố định (Fluency, Grammar...) do AI chấm rồi giáo viên duyệt (Accept/Reject qua `review_status`). Nghiệp vụ thật đã chốt lại hoàn toàn khác: **giáo viên chấm theo tiêu chí riêng, không cố định**, AI chỉ hỗ trợ theo `ai_instruction` (prompt tự do), và **cả 2 đều ra thẳng 1 điểm tổng duy nhất** (`gradings.final_score`) — không có bước breakdown theo từng tiêu chí ở bất kỳ đâu trong flow thật. Bảng `criteria_scores` vì vậy chưa từng và sẽ không có dữ liệu — drop hẳn để schema phản ánh đúng thực tế, không giữ lại bảng chết.

### `gradings.is_plagiarism_flagged` + `gradings.plagiarism_score` — DROP COLUMN

Truy lại nguồn gốc: 2 cột này phát sinh từ đúng 1 nhánh mồ côi trên activity diagram UC26 (*"Phát hiện đạo văn?"* → *"Gắn cảnh báo đỏ 100%"*), không hề xuất hiện trong mô tả UC24/UC25 bằng văn bản — cùng dạng tình huống với bảng `branches` từng bị treo và loại bỏ ở V1 (xem `ERD_reference.md` gốc, mục 6.1).

Khi làm rõ lại nghiệp vụ thật, "đạo văn" trong ngữ cảnh dự án này nghĩa là **2 học viên copy bài của nhau** — đây là bài toán **so sánh chéo giữa 2 bài làm**, cần biết bài A giống bài B của ai và giống bao nhiêu phần trăm. Thiết kế cũ đặt 2 cột này ở `gradings` (thuộc tính của đúng 1 bài làm, cô lập) là sai bản chất dữ liệu ngay từ đầu — dù có build tính năng thật, cũng không thể dùng lại 2 cột này (thiếu hẳn phần liên kết tới bài làm bị nghi ngờ giống). Vì hiện tại nhóm chưa có kế hoạch build tính năng này, quyết định drop hẳn, không giữ "phòng hờ" theo dạng sai thiết kế.
| Sinh transcript (Speaking) vào `gradings.ai_transcript` | — |

Giáo viên là người **duy nhất** có quyền nhập `final_score`/`final_feedback`, dựa trên tham khảo các chú thích + nhận xét mà AI cung cấp.

## PHẦN D — GHI CHÚ QUY TRÌNH

Toàn bộ thay đổi V2 sẽ đóng gói vào 1 file migration mới `V2__fix_db_review_bugs.sql` — **không sửa lại `V1__init_schema.sql`** vì Flyway đã khóa checksum sau khi chạy. Trước khi apply, cần test lại từ đầu bằng `docker compose down -v` để xóa sạch volume, sau đó `flyway migrate` lại toàn bộ (V1 + V2) trên môi trường local để đảm bảo không có lỗi FK/constraint trước khi merge.

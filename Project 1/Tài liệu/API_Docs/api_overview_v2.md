# Tổng quan API — V2

*Bản V2 = áp dụng schema V4 đã chốt (gộp `question_options`/`answer_results` vào JSON, drop `criteria_scores`) + 5 thay đổi trong `api_overview_v1_review.md`. Vẫn chỉ ở mức sương sương: method + endpoint + mục đích, chưa thiết kế request/response chi tiết.*

**Tổng: 63 endpoint** (giảm từ 65 của bản V4-merge, do review cắt thêm 2 cái + tách cụm không đổi số lượng).

---

## Changelog áp dụng từ review

| # | Thay đổi | Tác động |
|---|---|---|
| 1 | Tách cụm Auth và cụm User | Tổ chức lại 2 cụm, không đổi tổng số endpoint |
| 2 | Bỏ `register` + `register/verify` | Hệ thống không có tự đăng ký học viên — tài khoản chỉ tạo qua Admin (`POST /admin/users`). **−2 endpoint** |
| 3 | Tách API sửa thông tin cá nhân thành 2: sửa info riêng, đổi mật khẩu riêng | Tách trách nhiệm rõ ràng (đổi mật khẩu thường có luồng validate khác — nhập mật khẩu cũ, check độ mạnh...). **+1 endpoint** |
| 4 | Rời lớp (`class_members`) đổi thành xoá cứng | Đổi hành vi từ soft (set `ended_at`) sang xoá dòng thật. ⚠️ Xem lưu ý DB bên dưới |
| 5 | Bỏ API đếm lượt nghe audio | Không cần track `audio_play_count` nữa. **−1 endpoint** |

**Lưu ý về thay đổi #4 (ảnh hưởng schema):** Ở schema V3, cột `ended_at` và partial unique index `WHERE status='ACTIVE'` được thiết kế **riêng để hỗ trợ lịch sử** — cho phép học viên rời lớp rồi học lại (giữ dòng ENDED cũ, thêm dòng ACTIVE mới). Nếu đổi rời lớp thành xoá cứng, lịch sử "học viên từng ở lớp nào, rời khi nào" sẽ **mất hoàn toàn** sau khi xoá — không tra cứu lại được. Nên xác nhận rõ với PM: có cần giữ lịch sử member cũ để thống kê/báo cáo sau này không? Nếu chắc chắn không cần, cột `ended_at` cũng nên cân nhắc bỏ luôn ở lần chốt schema tiếp theo cho gọn (đang bị mồ côi mục đích nếu xoá cứng).

---

## CỤM 1 — Auth (3 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 1 | POST /auth/login | Đăng nhập, trả access token + tạo refresh token |
| 2 | POST /auth/refresh | Cấp access token mới từ refresh token |
| 3 | POST /auth/logout | Set `revoked_at` cho refresh token đang dùng |

---

## CỤM 2 — User (8 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 4 | GET /users/me | Xem thông tin bản thân (mọi role) |
| 5 | PUT /users/me | Sửa thông tin cá nhân (tên, phone, avatar...) |
| 6 | PATCH /users/me/password | Đổi mật khẩu riêng (tách khỏi sửa thông tin) |
| 7 | GET /admin/users | Admin xem danh sách + search user |
| 8 | POST /admin/users | Admin tạo tài khoản giáo viên/học viên |
| 9 | PUT /admin/users/{id} | Admin sửa thông tin 1 user |
| 10 | DELETE /admin/users/{id} | Admin xoá (hoặc soft-delete) user |
| 11 | PATCH /admin/users/{id}/status | Admin khoá/mở khoá tài khoản |

---

## CỤM 3 — Lớp học (9 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 12 | GET /classes | Danh sách lớp (lọc theo giáo viên, trạng thái) |
| 13 | POST /classes | Tạo lớp mới |
| 14 | GET /classes/{id} | Xem chi tiết 1 lớp |
| 15 | PUT /classes/{id} | Sửa thông tin lớp |
| 16 | DELETE /classes/{id} | Xoá lớp |
| 17 | GET /classes/{id}/members | Danh sách học viên trong lớp |
| 18 | POST /classes/{id}/members | Thêm học viên vào lớp |
| 19 | PATCH /classes/{id}/members/{memberId} | Đổi loại thành viên (PRIMARY/SUPPLEMENTARY) hoặc chuyển lớp |
| 20 | DELETE /classes/{id}/members/{memberId} | Học viên rời lớp — **xoá cứng** (đổi từ soft-delete ở V1) |

---

## CỤM 4 — Cấu trúc bài tập: Assignment / Module / Question (17 endpoint)

*Options của câu hỏi trắc nghiệm đã gộp vào JSON trong `questions` (theo schema V4) — không còn endpoint riêng cho option.*

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 21 | GET /classes/{id}/assignments | Danh sách bài tập của 1 lớp |
| 22 | POST /classes/{id}/assignments | Giáo viên tạo bài tập mới (status DRAFT) |
| 23 | GET /assignments/{id} | Xem chi tiết bài tập |
| 24 | PUT /assignments/{id} | Sửa thông tin bài tập |
| 25 | DELETE /assignments/{id} | Soft-delete bài tập |
| 26 | PATCH /assignments/{id}/status | Đổi trạng thái DRAFT → PUBLISHED → CLOSED |
| 27 | GET /assignments/{id}/modules | Danh sách module (kỹ năng) trong bài tập |
| 28 | POST /assignments/{id}/modules | Thêm module (Reading/Listening/Writing/Speaking) |
| 29 | GET /modules/{id} | Xem chi tiết 1 module |
| 30 | PUT /modules/{id} | Sửa module (đổi `ai_instruction`, `max_score`...) |
| 31 | DELETE /modules/{id} | Xoá module (cascade luôn questions) |
| 32 | POST /modules/{id}/audio | Upload file audio đề bài (Listening) lên R2 |
| 33 | GET /modules/{id}/questions | Danh sách câu hỏi trong module (kèm options nếu là trắc nghiệm) |
| 34 | POST /modules/{id}/questions | Tạo câu hỏi mới (options gửi kèm trong body nếu trắc nghiệm) |
| 35 | GET /questions/{id} | Xem chi tiết câu hỏi (kèm options) |
| 36 | PUT /questions/{id} | Sửa câu hỏi — sửa/thêm/xoá option = gửi lại toàn bộ mảng options |
| 37 | DELETE /questions/{id} | Xoá câu hỏi |

---

## CỤM 5 — Bài làm học viên: Submission / SubmissionModule / Answer (11 endpoint)

*Đã bỏ API đếm lượt nghe audio theo review V1.*

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 38 | POST /assignments/{id}/submissions | Học viên bắt đầu 1 lần làm bài (tạo attempt mới) |
| 39 | GET /submissions/{id} | Xem chi tiết 1 lần làm bài |
| 40 | GET /submissions | Danh sách bài làm (lọc theo học viên/bài tập, cho giáo viên xem) |
| 41 | POST /submissions/{id}/submit | Học viên nộp bài chính thức (đổi status → SUBMITTED) |
| 42 | GET /submissions/{id}/modules | Danh sách phần làm bài theo từng module |
| 43 | GET /submission-modules/{id} | Xem chi tiết 1 phần làm bài (VD phần Listening) |
| 44 | POST /submission-modules/{id}/answers | Học viên nộp 1 câu trả lời |
| 45 | GET /submission-modules/{id}/answers | Danh sách câu trả lời trong 1 phần làm bài |
| 46 | GET /answers/{id} | Xem chi tiết 1 câu trả lời (kèm kết quả chấm tự động nếu có — theo schema V4) |
| 47 | POST /answers/{id}/audio | Upload file ghi âm (Speaking) |
| 48 | POST /answers/{id}/document | Upload file bài viết .docx/.pdf (Writing) |

---

## CỤM 6 — Chấm điểm: Grading / Annotation / Change log (10 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 49 | GET /submission-modules/{id}/grading | Xem bản chấm điểm của 1 phần làm bài |
| 50 | POST /submission-modules/{id}/grading/ai-analyze | Kích hoạt AI phân tích (Writing/Speaking) — chạy async |
| 51 | PUT /gradings/{id} | Giáo viên nhập/sửa điểm cuối (`final_score`, `final_feedback`) |
| 52 | GET /gradings/{id} | Xem chi tiết 1 bản chấm |
| 53 | GET /gradings | Danh sách bản chấm (lọc theo lớp/học viên/trạng thái) |
| 54 | GET /answers/{id}/annotations | Danh sách lỗi được đánh dấu (AI hoặc giáo viên) trong 1 câu trả lời |
| 55 | POST /answers/{id}/annotations | Giáo viên tự thêm 1 đánh dấu lỗi |
| 56 | PATCH /annotations/{id}/review | Giáo viên Accept/Reject 1 gợi ý lỗi do AI đưa ra |
| 57 | DELETE /annotations/{id} | Xoá 1 đánh dấu lỗi |
| 58 | GET /gradings/{id}/change-logs | Xem lịch sử các lần sửa điểm tay |

---

## CỤM 7 — Đánh giá tổng quan: Student Evaluation (5 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 59 | GET /students/{id}/evaluations | Danh sách đánh giá tổng quan của 1 học viên (theo thời gian) |
| 60 | POST /students/{id}/evaluations | Giáo viên viết đánh giá mới cho học viên (trong ngữ cảnh 1 lớp) |
| 61 | GET /evaluations/{id} | Xem chi tiết 1 đánh giá |
| 62 | PUT /evaluations/{id} | Sửa nội dung đánh giá |
| 63 | DELETE /evaluations/{id} | Xoá đánh giá |

---

## Việc cần làm rõ trước khi viết docs chi tiết

1. **Xác nhận với PM** về lưu ý #4 ở Changelog (xoá cứng class_members mất lịch sử) — nếu PM đồng ý đánh đổi này, ghi lại quyết định để tránh bị hỏi lại lúc bảo vệ đồ án.
2. Với việc bỏ tự đăng ký (thay đổi #2), cần xác nhận `POST /admin/users` (#8) có cần OTP xác thực email cho tài khoản mới không, hay Admin tạo xong là dùng được ngay — ảnh hưởng tới việc có cần endpoint verify riêng hay không.

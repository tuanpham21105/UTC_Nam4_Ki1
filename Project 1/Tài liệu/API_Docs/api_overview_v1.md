# Tổng quan API dự kiến — V3 (hiện tại) vs V4 (dự kiến)

*File này chỉ để HÌNH DUNG phạm vi — mỗi dòng là 1 endpoint, kèm mục đích ngắn. Chưa thiết kế request/response chi tiết, chưa chốt status code, chưa chốt role được phép gọi.*

**V3 (hiện tại, đã chốt)**: 18 bảng, tách riêng `question_options` và `answer_results`.
**V4 (dự kiến, đang họp chốt)**: dự tính gộp `question_options` → JSON trong `questions`, gộp `answer_results` → JSON/field trong `answers`, `criteria_scores` bỏ 100% (đã drop từ V2, không đổi gì thêm).

Các endpoint bị ảnh hưởng bởi V4 được đánh dấu **★ V4** ngay trong bảng — phần còn lại giữ nguyên dù chốt theo V3 hay V4.

**Tổng: V3 = 70 endpoint | V4 (dự kiến) = 65 endpoint** — xem bảng so sánh nhanh ở cuối file.

---

## CỤM 1 — Auth & User (12 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 1 | POST /auth/register | Học viên tự đăng ký (nếu hệ thống cho tự đăng ký) |
| 2 | POST /auth/register/verify | Xác thực OTP sau khi đăng ký |
| 3 | POST /auth/login | Đăng nhập, trả access token + tạo refresh token |
| 4 | POST /auth/refresh | Cấp access token mới từ refresh token |
| 5 | POST /auth/logout | Set `revoked_at` cho refresh token đang dùng |
| 6 | GET /users/me | Xem thông tin bản thân (mọi role) |
| 7 | PATCH /users/me | Sửa thông tin cá nhân (đổi mật khẩu, tên...) |
| 8 | GET /admin/users | Admin xem danh sách + search user |
| 9 | POST /admin/users | Admin tạo tài khoản giáo viên/học viên |
| 10 | PUT /admin/users/{id} | Admin sửa thông tin 1 user |
| 11 | DELETE /admin/users/{id} | Admin xoá (hoặc soft-delete) user |
| 12 | PATCH /admin/users/{id}/status | Admin khoá/mở khoá tài khoản |

---

## CỤM 2 — Lớp học (9 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 13 | GET /classes | Danh sách lớp (lọc theo giáo viên, trạng thái) |
| 14 | POST /classes | Tạo lớp mới |
| 15 | GET /classes/{id} | Xem chi tiết 1 lớp |
| 16 | PUT /classes/{id} | Sửa thông tin lớp |
| 17 | DELETE /classes/{id} | Xoá lớp |
| 18 | GET /classes/{id}/members | Danh sách học viên trong lớp |
| 19 | POST /classes/{id}/members | Thêm học viên vào lớp |
| 20 | PATCH /classes/{id}/members/{memberId} | Đổi loại thành viên (PRIMARY/SUPPLEMENTARY) hoặc chuyển lớp |
| 21 | DELETE /classes/{id}/members/{memberId} | Học viên rời lớp (set `ended_at`, không xoá cứng) |

---

## CỤM 3 — Cấu trúc bài tập: Assignment / Module / Question / Option (21 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 22 | GET /classes/{id}/assignments | Danh sách bài tập của 1 lớp |
| 23 | POST /classes/{id}/assignments | Giáo viên tạo bài tập mới (status DRAFT) |
| 24 | GET /assignments/{id} | Xem chi tiết bài tập |
| 25 | PUT /assignments/{id} | Sửa thông tin bài tập |
| 26 | DELETE /assignments/{id} | Soft-delete bài tập |
| 27 | PATCH /assignments/{id}/status | Đổi trạng thái DRAFT → PUBLISHED → CLOSED |
| 28 | GET /assignments/{id}/modules | Danh sách module (kỹ năng) trong bài tập |
| 29 | POST /assignments/{id}/modules | Thêm module (Reading/Listening/Writing/Speaking) |
| 30 | GET /modules/{id} | Xem chi tiết 1 module |
| 31 | PUT /modules/{id} | Sửa module (đổi `ai_instruction`, `max_score`...) |
| 32 | DELETE /modules/{id} | Xoá module (cascade luôn questions) |
| 33 | POST /modules/{id}/audio | Upload file audio đề bài (Listening) lên R2 |
| 34 | GET /modules/{id}/questions | Danh sách câu hỏi trong module |
| 35 | POST /modules/{id}/questions | Tạo câu hỏi mới |
| 36 | GET /questions/{id} | Xem chi tiết câu hỏi |
| 37 | PUT /questions/{id} | Sửa câu hỏi |
| 38 | DELETE /questions/{id} | Xoá câu hỏi |
| 39 | ★V4 GET /questions/{id}/options | Danh sách lựa chọn (Multiple Choice) — **V4: bỏ, options nằm sẵn trong response của #36** |
| 40 | ★V4 POST /questions/{id}/options | Thêm 1 lựa chọn — **V4: bỏ, thêm option = gửi lại toàn bộ mảng qua PUT #37** |
| 41 | ★V4 PUT /questions/{id}/options/{optId} | Sửa 1 lựa chọn — **V4: bỏ, sửa option = gửi lại toàn bộ mảng qua PUT #37** |
| 42 | ★V4 DELETE /questions/{id}/options/{optId} | Xoá 1 lựa chọn — **V4: bỏ, xoá option = gửi lại mảng đã bớt phần tử qua PUT #37** |

---

## CỤM 4 — Bài làm học viên: Submission / SubmissionModule / Answer (12 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 43 | POST /assignments/{id}/submissions | Học viên bắt đầu 1 lần làm bài (tạo attempt mới) |
| 44 | GET /submissions/{id} | Xem chi tiết 1 lần làm bài |
| 45 | GET /submissions | Danh sách bài làm (lọc theo học viên/bài tập, cho giáo viên xem) |
| 46 | POST /submissions/{id}/submit | Học viên nộp bài chính thức (đổi status → SUBMITTED) |
| 47 | GET /submissions/{id}/modules | Danh sách phần làm bài theo từng module |
| 48 | GET /submission-modules/{id} | Xem chi tiết 1 phần làm bài (VD phần Listening) |
| 49 | PATCH /submission-modules/{id}/play-count | Tăng đếm số lần nghe lại audio |
| 50 | POST /submission-modules/{id}/answers | Học viên nộp 1 câu trả lời |
| 51 | GET /submission-modules/{id}/answers | Danh sách câu trả lời trong 1 phần làm bài |
| 52 | GET /answers/{id} | Xem chi tiết 1 câu trả lời |
| 53 | POST /answers/{id}/audio | Upload file ghi âm (Speaking) |
| 54 | POST /answers/{id}/document | Upload file bài viết .docx/.pdf (Writing) |

---

## CỤM 5 — Chấm điểm: Grading / Annotation / Change log (11 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 55 | ★V4 GET /answers/{id}/result | Xem kết quả chấm tự động (đúng/sai) — **V4: bỏ, `is_correct`/`score` nằm sẵn trong response của #52** |
| 56 | GET /submission-modules/{id}/grading | Xem bản chấm điểm của 1 phần làm bài |
| 57 | POST /submission-modules/{id}/grading/ai-analyze | Kích hoạt AI phân tích (Writing/Speaking) — chạy async |
| 58 | PUT /gradings/{id} | Giáo viên nhập/sửa điểm cuối (`final_score`, `final_feedback`) |
| 59 | GET /gradings/{id} | Xem chi tiết 1 bản chấm |
| 60 | GET /gradings | Danh sách bản chấm (lọc theo lớp/học viên/trạng thái) |
| 61 | GET /answers/{id}/annotations | Danh sách lỗi được đánh dấu (AI hoặc giáo viên) trong 1 câu trả lời |
| 62 | POST /answers/{id}/annotations | Giáo viên tự thêm 1 đánh dấu lỗi |
| 63 | PATCH /annotations/{id}/review | Giáo viên Accept/Reject 1 gợi ý lỗi do AI đưa ra |
| 64 | DELETE /annotations/{id} | Xoá 1 đánh dấu lỗi |
| 65 | GET /gradings/{id}/change-logs | Xem lịch sử các lần sửa điểm tay |

---

## CỤM 6 — Đánh giá tổng quan: Student Evaluation (5 endpoint)

| # | Method + Endpoint | Mục đích |
|---|---|---|
| 66 | GET /students/{id}/evaluations | Danh sách đánh giá tổng quan của 1 học viên (theo thời gian) |
| 67 | POST /students/{id}/evaluations | Giáo viên viết đánh giá mới cho học viên (trong ngữ cảnh 1 lớp) |
| 68 | GET /evaluations/{id} | Xem chi tiết 1 đánh giá |
| 69 | PUT /evaluations/{id} | Sửa nội dung đánh giá |
| 70 | DELETE /evaluations/{id} | Xoá đánh giá |

---

## So sánh nhanh V3 vs V4 (dự kiến)

| | V3 (hiện tại) | V4 (dự kiến) |
|---|---|---|
| `question_options` | Bảng riêng, 4 endpoint CRUD (#39-42) | Gộp JSON vào `questions.options`, **0 endpoint riêng** — đọc/sửa qua #36/#37 |
| `answer_results` | Bảng riêng, 1 endpoint xem (#55) | Gộp field vào `answers`, **0 endpoint riêng** — đọc qua #52 |
| `criteria_scores` | Đã DROP từ V2, không có endpoint | Vẫn DROP, không đổi gì |
| **Tổng endpoint** | **70** | **65** (−5) |
| Các cụm còn lại (Auth, Class, Assignment/Module, Submission, Grading chính, Evaluation) | Giữ nguyên | Giữ nguyên — không đổi 1 dòng nào |

**Kết luận: V4 chỉ thu hẹp đúng 2 điểm nhỏ (option, result), 65/70 endpoint còn lại giống 100% giữa 2 bản.**

## Khuyến nghị cho buổi họp tối nay (PM muốn chốt nhanh)

Vì phần khác nhau giữa V3/V4 chỉ nằm ở **2 chỗ cực nhỏ và độc lập** (không đụng tới Auth, Class, Submission, Grading, Evaluation), gợi ý chốt nhanh theo hướng:

1. **Không cần chờ chốt V4 mới bắt đầu viết docs** — 65/70 endpoint là chung cho cả 2 phương án, cứ viết trước, không mất công làm lại.
2. Câu hỏi cần PM/team trả lời dứt điểm tối nay chỉ có **đúng 1 câu**: *"Có cần sửa 1 lựa chọn/1 kết quả chấm riêng lẻ (không kèm cả object cha) ở đâu trong nghiệp vụ không?"* — nếu **không**, JSON (V4) là lựa chọn hợp lý, đơn giản hơn, ít endpoint hơn. Nếu **có** (VD: muốn drag-drop sắp xếp lại option mà không load lại cả câu hỏi), thì nên giữ bảng riêng (V3).
3. Ở quy mô đồ án, khả năng cao câu trả lời là "không cần" → **V4 (JSON) là hướng nên chọn** để giảm việc, đúng tinh thần PM muốn làm nhanh.

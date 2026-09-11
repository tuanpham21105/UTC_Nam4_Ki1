# Câu hỏi làm rõ nghiệp vụ trong Database V2 sau review

1. Vấn đề 2.B: Có muốn thêm trigger đảm bảo mỗi `question` (MULTIPLE\_CHOICE) có đúng 1 `question_option.is_correct = true` không? Hay chỉ kiểm tra ở service layer là đủ?

   * Trả lời: Question dạng lựa chọn tick box có thể có nhiều correct answer, question dạng radio button lại chỉ có một correct answer, vậy nên ko cần trigger đảm bảo chỉ có 1 `question_option.is_correct = true`.
2. Vấn đề 4: Mâu thuẫn ngữ nghĩa gradings.method = AUTO vs. ghi chú "final_score luôn do giáo viên nhập"

   * Trả lời: AUTO chỉ áp dụng Reading/Listening, điểm cộng tự động từ `answer_results`, giáo viên chỉ duyệt chứ không gõ điểm → cần sửa lại note "final\_score luôn do giáo viên nhập".
3. Vấn đề phụ: Sửa lại ghi chú của bảng `gradings`

   * Trả lời: Có sửa
4. Vấn đề 9: (`status` vs `is_manually_closed` của `assignments`): hợp nhất theo hướng nào? Hiện tại đang xung đột trạng thái giữa cả 2, vấn đề xảy ra khi một cái ghi `CLOSED` cái còn lại thì ko.

   * Trả lời: Bỏ hẳn thuộc tính `is_manually_closed` tránh xung đột trạng thái
5. Vấn đề 11: (`assignments.teacher_id` không nhất thiết trùng `classes.teacher_id` hiện tại): có đúng chủ đích "đóng băng giáo viên tạo bài" không?

   * Trả lời: Phải trùng, chỉ giáo viên giao bài mới được chấm bài
6. Vấn đề 12: (soft-delete vs hard-delete cascade): có đồng ý is\_deleted là hướng đi cho `classes`, `modules`, `questions`, `submissions`, `gradings`... không, hay giữ cascade cứng như hiện tại?soft-delete vs hard-delete cascade

   * Trả lời: Giữ cascade cho `classes`, `modules`, `questions`, `submissions`, `gradings`..., soft delete cho `users` và `assigments`
7. Vấn đề 13: (review\_status mặc định PENDING kể cả source=TEACHER): sửa ở DB (default theo source) hay chỉ ở service layer như review đề xuất?

   * Trả lời: Mặc định là `PENDING` bất kể `source`, chỉnh sửa ở service layer như đề xuất.

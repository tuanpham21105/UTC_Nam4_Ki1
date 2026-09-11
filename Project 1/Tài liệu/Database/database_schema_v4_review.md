# Review Database Schema V4

1. Bỏ thuộc tính `class_members.ended_at`, xóa cứng `class_members` khỏi lớp
2. Bảng `questions` thiếu thuộc tính đáp án, đang bị nhầm lẫn thuộc tính cho đề bài thành thuộc tính `content` khiến đề bài và đáp án đúng bị trùng, thêm thuộc tính `correct_answer` kiểu text.
3. Bỏ thuộc tính `submission_modules.audio_play_count`, bởi vì ko cần thiết phải đếm

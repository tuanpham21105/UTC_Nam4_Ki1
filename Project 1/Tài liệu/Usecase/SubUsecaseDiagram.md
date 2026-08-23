# Sơ đồ Use Case (Mermaid) — Website quản lý bài tập và chấm chữa bài tiếng Anh thông minh

Mỗi ca sử dụng gốc được vẽ thành một sơ đồ riêng, kèm theo các ca sử dụng con mở rộng theo quan hệ `«include»` (bắt buộc xảy ra) hoặc `«extend»` (có thể xảy ra thêm/tùy chọn), tương tự cách một chức năng "Quản lý" được mở rộng thành các thao tác CRUD (Xem/Thêm/Sửa/Xóa).

---

## 1. Đăng nhập

```mermaid
flowchart LR
    A1["Quản lý"]
    A2["Giáo viên"]
    A3["Học viên"]
    UC(["Đăng nhập"])
    A1 --> UC
    A2 --> UC
    A3 --> UC
```

## 2. Đăng xuất

```mermaid
flowchart LR
    A1["Quản lý"]
    A2["Giáo viên"]
    A3["Học viên"]
    UC(["Đăng xuất"])
    A1 --> UC
    A2 --> UC
    A3 --> UC
```

## 3. Quản lý tài khoản cá nhân

```mermaid
flowchart LR
    A1["Quản lý"]
    A2["Giáo viên"]
    A3["Học viên"]
    UC(["Quản lý tài khoản cá nhân"])
    A1 --> UC
    A2 --> UC
    A3 --> UC
    S1(["Xem thông tin cá nhân"])
    UC -.->|"«extend»"| S1
    S2(["Cập nhật thông tin cá nhân"])
    UC -.->|"«extend»"| S2
    S3(["Đổi mật khẩu"])
    UC -.->|"«extend»"| S3
```

## 4. Quản lý tài khoản người dùng

```mermaid
flowchart LR
    A1["Quản lý"]
    UC(["Quản lý tài khoản người dùng"])
    A1 --> UC
    S1(["Xem danh sách tài khoản"])
    UC -.->|"«extend»"| S1
    S2(["Tạo tài khoản"])
    UC -.->|"«extend»"| S2
    S3(["Cập nhật tài khoản"])
    UC -.->|"«extend»"| S3
    S4(["Khóa/Mở khóa tài khoản"])
    UC -.->|"«extend»"| S4
```

## 5. Phân quyền người dùng

```mermaid
flowchart LR
    A1["Quản lý"]
    UC(["Phân quyền người dùng"])
    A1 --> UC
    S1(["Xem danh sách quyền"])
    UC -.->|"«extend»"| S1
    S2(["Gán quyền theo vai trò"])
    UC -.->|"«extend»"| S2
    S3(["Thu hồi quyền"])
    UC -.->|"«extend»"| S3
```

## 6. Quản lý giáo viên

```mermaid
flowchart LR
    A1["Quản lý"]
    UC(["Quản lý giáo viên"])
    A1 --> UC
    S1(["Xem danh sách giáo viên"])
    UC -.->|"«extend»"| S1
    S2(["Thêm giáo viên"])
    UC -.->|"«extend»"| S2
    S3(["Cập nhật thông tin giáo viên"])
    UC -.->|"«extend»"| S3
    S4(["Khóa/Xóa giáo viên"])
    UC -.->|"«extend»"| S4
```

## 7. Quản lý học viên

```mermaid
flowchart LR
    A1["Quản lý"]
    UC(["Quản lý học viên"])
    A1 --> UC
    S1(["Xem danh sách học viên"])
    UC -.->|"«extend»"| S1
    S2(["Thêm học viên"])
    UC -.->|"«extend»"| S2
    S3(["Cập nhật thông tin học viên"])
    UC -.->|"«extend»"| S3
    S4(["Khóa/Xóa học viên"])
    UC -.->|"«extend»"| S4
```

## 8. Quản lý lớp học

```mermaid
flowchart LR
    A1["Quản lý"]
    UC(["Quản lý lớp học"])
    A1 --> UC
    S1(["Xem danh sách lớp học"])
    UC -.->|"«extend»"| S1
    S2(["Tạo lớp học"])
    UC -.->|"«extend»"| S2
    S3(["Cập nhật lớp học"])
    UC -.->|"«extend»"| S3
    S4(["Phân công giáo viên phụ trách"])
    UC -.->|"«extend»"| S4
```

## 9. Quản lý thành viên lớp học

```mermaid
flowchart LR
    A1["Quản lý"]
    A2["Giáo viên"]
    UC(["Quản lý thành viên lớp học"])
    A1 --> UC
    A2 --> UC
    S1(["Xem danh sách thành viên"])
    UC -.->|"«extend»"| S1
    S2(["Thêm học viên vào lớp"])
    UC -.->|"«extend»"| S2
    S3(["Xóa học viên khỏi lớp"])
    UC -.->|"«extend»"| S3
```

## 10. Xem danh sách lớp học

```mermaid
flowchart LR
    A1["Giáo viên"]
    A2["Học viên"]
    UC(["Xem danh sách lớp học"])
    A1 --> UC
    A2 --> UC
    S1(["Xem chi tiết lớp học"])
    UC -.->|"«extend»"| S1
    S2(["Xem thời khóa biểu lớp"])
    UC -.->|"«extend»"| S2
```

## 11. Quản lý bài tập

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Quản lý bài tập"])
    A1 --> UC
    S1(["Tạo bài tập"])
    UC -.->|"«extend»"| S1
    S2(["Chỉnh sửa bài tập"])
    UC -.->|"«extend»"| S2
    S3(["Xóa bài tập"])
    UC -.->|"«extend»"| S3
    S4(["Xem danh sách bài tập"])
    UC -.->|"«extend»"| S4
```

## 12. Tạo bài tập

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Tạo bài tập"])
    A1 --> UC
    S1(["Thiết lập nội dung bài tập"])
    UC -.->|"«include»"| S1
    S2(["Thiết lập thời gian làm bài"])
    UC -.->|"«include»"| S2
```

## 13. Mở bài tập

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Mở bài tập"])
    A1 --> UC
```

## 14. Khóa bài tập

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Khóa bài tập"])
    A1 --> UC
```

## 15. Xem bài tập

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Xem bài tập"])
    A1 --> UC
    S1(["Xem chi tiết bài tập"])
    UC -.->|"«extend»"| S1
    S2(["Xem thời hạn nộp bài"])
    UC -.->|"«extend»"| S2
    S3(["Xem trạng thái bài tập"])
    UC -.->|"«extend»"| S3
```

## 16. Làm bài tập viết

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Làm bài tập viết"])
    A1 --> UC
    S1(["Nhập nội dung bài viết"])
    UC -.->|"«include»"| S1
```

## 17. Làm bài tập nói

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Làm bài tập nói"])
    A1 --> UC
    S1(["Ghi âm câu trả lời"])
    UC -.->|"«include»"| S1
    S2(["Tải lên file ghi âm"])
    UC -.->|"«include»"| S2
```

## 18. Làm bài tập đọc

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Làm bài tập đọc"])
    A1 --> UC
    S1(["Đọc đề bài"])
    UC -.->|"«include»"| S1
    S2(["Chọn đáp án đọc hiểu"])
    UC -.->|"«include»"| S2
```

## 19. Làm bài tập nghe

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Làm bài tập nghe"])
    A1 --> UC
    S1(["Nghe nội dung audio"])
    UC -.->|"«include»"| S1
    S2(["Trả lời câu hỏi nghe"])
    UC -.->|"«include»"| S2
```

## 20. Nộp bài tập

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Nộp bài tập"])
    A1 --> UC
    S1(["Xác nhận nộp bài"])
    UC -.->|"«include»"| S1
```

## 21. Xem trạng thái bài làm

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Xem trạng thái bài làm"])
    A1 --> UC
```

## 22. Chấm bài

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Chấm bài"])
    A1 --> UC
    S1(["Xem bài làm học viên"])
    UC -.->|"«include»"| S1
    S2(["Nhập điểm số"])
    UC -.->|"«include»"| S2
    S3(["Nhập nhận xét"])
    UC -.->|"«include»"| S3
    S4(["Hỗ trợ chấm chữa bài bằng AI"])
    UC -.->|"«extend»"| S4
```

## 23. Hỗ trợ chấm chữa bài bằng AI

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Hỗ trợ chấm chữa bài bằng AI"])
    A1 --> UC
    S1(["Hỗ trợ chấm chữa bài viết bằng AI"])
    UC -.->|"«extend»"| S1
    S2(["Hỗ trợ chấm chữa bài nói bằng AI"])
    UC -.->|"«extend»"| S2
```

## 24. Hỗ trợ chấm chữa bài viết bằng AI

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Hỗ trợ chấm chữa bài viết bằng AI"])
    A1 --> UC
    S1(["Phát hiện lỗi ngữ pháp"])
    UC -.->|"«include»"| S1
    S2(["Phát hiện lỗi từ vựng/chính tả"])
    UC -.->|"«include»"| S2
    S3(["Gợi ý sửa lỗi diễn đạt"])
    UC -.->|"«extend»"| S3
```

## 25. Hỗ trợ chấm chữa bài nói bằng AI

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Hỗ trợ chấm chữa bài nói bằng AI"])
    A1 --> UC
    S1(["Phân tích phát âm"])
    UC -.->|"«include»"| S1
    S2(["Phân tích ngữ pháp và từ vựng"])
    UC -.->|"«include»"| S2
    S3(["Gợi ý cải thiện độ trôi chảy"])
    UC -.->|"«extend»"| S3
```

## 26. Chấm bài đọc

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Chấm bài đọc"])
    A1 --> UC
    S1(["So sánh đáp án"])
    UC -.->|"«include»"| S1
    S2(["Tính điểm tự động"])
    UC -.->|"«include»"| S2
```

## 27. Chấm bài nghe

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Chấm bài nghe"])
    A1 --> UC
    S1(["So sánh đáp án"])
    UC -.->|"«include»"| S1
    S2(["Tính điểm tự động"])
    UC -.->|"«include»"| S2
```

## 28. Xem điểm số

```mermaid
flowchart LR
    A1["Giáo viên"]
    A2["Học viên"]
    UC(["Xem điểm số"])
    A1 --> UC
    A2 --> UC
    S1(["Xem chi tiết điểm theo bài tập"])
    UC -.->|"«extend»"| S1
    S2(["Xem điểm theo kỹ năng"])
    UC -.->|"«extend»"| S2
```

## 29. Xem kết quả học tập

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Xem kết quả học tập"])
    A1 --> UC
    S1(["Xem tiến độ theo kỹ năng"])
    UC -.->|"«extend»"| S1
    S2(["Xem tiến độ theo lớp học"])
    UC -.->|"«extend»"| S2
```

## 30. Xem bài chữa

```mermaid
flowchart LR
    A1["Học viên"]
    UC(["Xem bài chữa"])
    A1 --> UC
    S1(["Xem nhận xét của giáo viên/AI"])
    UC -.->|"«extend»"| S1
    S2(["Xem đáp án"])
    UC -.->|"«extend»"| S2
    S3(["Xem hướng dẫn sửa bài"])
    UC -.->|"«extend»"| S3
```

## 31. Xem và đánh giá kết quả học viên

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Xem và đánh giá kết quả học viên"])
    A1 --> UC
    S1(["Xem điểm số học viên"])
    UC -.->|"«extend»"| S1
    S2(["Xem bài làm học viên"])
    UC -.->|"«extend»"| S2
    S3(["Nhận xét đánh giá"])
    UC -.->|"«extend»"| S3
```

## 32. Quản lý điểm số

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Quản lý điểm số"])
    A1 --> UC
    S1(["Xem điểm số"])
    UC -.->|"«extend»"| S1
    S2(["Cập nhật điểm số"])
    UC -.->|"«extend»"| S2
    S3(["Xuất bảng điểm"])
    UC -.->|"«extend»"| S3
```

## 33. Theo dõi tiến độ học tập

```mermaid
flowchart LR
    A1["Giáo viên"]
    UC(["Theo dõi tiến độ học tập"])
    A1 --> UC
    S1(["Xem tiến độ theo học viên"])
    UC -.->|"«extend»"| S1
```

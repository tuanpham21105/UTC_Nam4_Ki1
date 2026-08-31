# USE CASE SPECIFICATION
## Website quản lý bài tập và chấm chữa bài tiếng Anh thông minh

**Tổng số Use Case: 36**

### 1. Danh sách tổng hợp 36 Use Case

| STT | Ca sử dụng | Mô tả ngắn | Tác nhân |
|---|---|---|---|
| 1 | Đăng nhập | Người dùng đăng nhập vào hệ thống bằng tài khoản và mật khẩu; hệ thống xác thực thông tin, kiểm tra trạng thái tài khoản và xác định quyền truy cập. | Quản lý, Giáo viên, Học viên |
| 2 | Đăng xuất | Người dùng kết thúc phiên đăng nhập hiện tại và đăng xuất khỏi hệ thống. | Quản lý, Giáo viên, Học viên |
| 3 | Quản lý tài khoản cá nhân | Người dùng xem và cập nhật thông tin cá nhân, ảnh đại diện, mật khẩu và các thông tin liên quan đến tài khoản. | Quản lý, Giáo viên, Học viên |
| 4 | Quản lý tài khoản người dùng | Quản lý trung tâm tạo, cập nhật, xem, khóa/mở khóa, kích hoạt hoặc vô hiệu hóa tài khoản người dùng. | Quản lý |
| 5 | Phân quyền người dùng | Quản lý trung tâm quản lý vai trò, quyền hạn và gán quyền truy cập chức năng cho người dùng theo từng vai trò. | Quản lý |
| 6 | Quản lý giáo viên | Quản lý trung tâm xem, thêm, cập nhật và quản lý thông tin giáo viên, đồng thời quản lý trạng thái và phân công giáo viên. | Quản lý |
| 7 | Quản lý học viên | Quản lý trung tâm quản lý hồ sơ, tài khoản, trạng thái và thông tin học tập cơ bản của học viên. | Quản lý |
| 8 | Quản lý lớp học | Quản lý trung tâm tạo, cập nhật, xem, đóng/mở lớp học và quản lý giáo viên phụ trách lớp. | Quản lý |
| 9 | Quản lý thành viên lớp học | Quản lý trung tâm hoặc giáo viên thêm, xóa, chuyển và quản lý danh sách học viên thuộc lớp học. | Quản lý, Giáo viên |
| 10 | Xem danh sách lớp học | Giáo viên hoặc học viên xem danh sách các lớp học mà mình được phân công hoặc tham gia. | Giáo viên, Học viên |
| 11 | Quản lý bài tập | Giáo viên xem, tìm kiếm, lọc, chỉnh sửa, sao chép, xóa và quản lý các bài tập theo lớp học và kỹ năng. | Giáo viên |
| 12 | Tạo bài tập | Giáo viên tạo bài tập, thiết lập nội dung, câu hỏi, đáp án, kỹ năng, điểm số, thời gian, số lần làm và các yêu cầu làm bài. | Giáo viên |
| 13 | Mở bài tập | Giáo viên công bố/mở bài tập để học viên có thể truy cập và thực hiện theo thời gian và điều kiện đã thiết lập. | Giáo viên |
| 14 | Khóa bài tập | Giáo viên khóa bài tập để ngăn học viên tiếp tục làm hoặc nộp bài; hệ thống cũng có thể tự động khóa khi hết hạn. | Giáo viên |
| 15 | Xem bài tập | Học viên xem danh sách, nội dung, hướng dẫn, thời hạn, trạng thái và các yêu cầu của bài tập được giao. | Học viên |
| 16 | Làm bài tập viết | Học viên đọc đề bài, nhập nội dung bài viết, chỉnh sửa, lưu bài và hoàn thành phần trả lời của kỹ năng Viết. | Học viên |
| 17 | Làm bài tập nói | Học viên thực hiện bài Nói bằng cách ghi âm hoặc tải tệp âm thanh, nghe lại, ghi âm lại nếu cần và hoàn thành phần trả lời. | Học viên |
| 18 | Làm bài tập đọc | Học viên đọc nội dung, thực hiện các dạng câu hỏi và nhập/chọn đáp án cho bài tập Đọc. | Học viên |
| 19 | Làm bài tập nghe | Học viên nghe nội dung âm thanh, thực hiện các câu hỏi và nhập/chọn đáp án cho bài tập Nghe. | Học viên |
| 20 | Kiểm tra và xem lại bài làm trước khi nộp |Học viên xem lại toàn bộ câu trả lời đã thực hiện, kiểm tra câu chưa trả lời, chỉnh sửa nội dung và xác nhận bài đã sẵn sàng trước khi thực hiện nộp bài. | Học viên |
| 21 | Nộp bài tập | Học viên kiểm tra bài làm, xác nhận và gửi bài lên hệ thống; hệ thống ghi nhận thời gian, trạng thái và lần nộp của bài làm. | Học viên |
| 22 | Quản lý lần làm và nộp lại bài | Hệ thống quản lý số lần làm, lịch sử các lần nộp và cho phép học viên nộp lại bài khi đáp ứng chính sách của bài tập. | Học viên, Giáo viên |
| 23 | Xem trạng thái bài làm | Học viên theo dõi trạng thái bài làm như chưa làm, đang làm, đã nộp, đang chấm, đã chấm hoặc đã có bài chữa. | Học viên |
| 24 | Chấm bài | Giáo viên xem bài làm, chấm từng câu hoặc toàn bài, nhập điểm, nhận xét và xác nhận kết quả chấm. | Giáo viên |
| 25 | Hỗ trợ chấm chữa bài bằng AI | Hệ thống sử dụng AI để phân tích bài làm, đề xuất điểm số, nhận xét, phát hiện lỗi và gợi ý sửa bài để giáo viên tham khảo. | Giáo viên |
| 26 | Hỗ trợ chấm chữa bài viết bằng AI | AI phân tích bài viết theo các tiêu chí như chính tả, ngữ pháp, từ vựng, diễn đạt, cấu trúc và nội dung; đưa ra điểm và đề xuất sửa lỗi. | Giáo viên |
| 27 | Hỗ trợ chấm chữa bài nói bằng AI | AI phân tích bài nói dựa trên nội dung, phát âm, từ vựng, ngữ pháp, độ trôi chảy và các tiêu chí đánh giá phù hợp để đưa ra kết quả đề xuất. | Giáo viên |
| 28 | Chấm bài đọc | Hệ thống tự động đối chiếu đáp án của bài Đọc, xác định câu đúng/sai và tính điểm cho bài làm. | Giáo viên |
| 29 | Chấm bài nghe | Hệ thống tự động đối chiếu đáp án của bài Nghe, xác định câu đúng/sai và tính điểm cho bài làm. | Giáo viên |
| 30 | Xem điểm số | Giáo viên hoặc học viên xem điểm của các bài tập, điểm theo kỹ năng và kết quả học tập đã được công bố. | Giáo viên, Học viên |
| 31 | Quản lý điểm số | Giáo viên xem, cập nhật, xác nhận và quản lý điểm số của học viên trong lớp; hệ thống lưu lại lịch sử thay đổi điểm. | Giáo viên |
| 32 | Xem kết quả học tập | Học viên theo dõi kết quả học tập tổng quan và kết quả theo từng bài tập, kỹ năng hoặc lớp học. | Học viên |
| 33 | Xem bài chữa | Học viên xem đáp án, câu trả lời của mình, lỗi sai, nhận xét của giáo viên/AI, hướng dẫn sửa và các nội dung chữa bài sau khi được chấm. | Học viên |
| 34 | Xem và đánh giá kết quả học viên | Giáo viên xem điểm số, bài làm, kết quả theo từng kỹ năng và đưa ra nhận xét, đánh giá về năng lực của học viên. | Giáo viên |
| 35 | Theo dõi tiến độ học tập | Giáo viên theo dõi tình trạng làm bài, tỷ lệ hoàn thành, bài chưa nộp, điểm số và tiến độ của học viên theo từng kỹ năng. | Giáo viên |
| 36 | Xem báo cáo và thống kê học tập | Giáo viên hoặc quản lý xem các báo cáo, thống kê về bài tập, điểm số, tỷ lệ hoàn thành và kết quả học tập theo lớp, học viên hoặc kỹ năng. | Quản lý, Giáo viên |

---

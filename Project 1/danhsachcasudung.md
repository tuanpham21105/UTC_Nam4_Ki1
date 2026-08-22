**Tên đề tài:** Xây dựng website quản lý bài tập và chấm chữa bài tiếng anh thông minh

**Mục tiêu dự án:** Xây dựng một website quản lý bài tập và chấm chữa bài tiếng Anh, tập trung vào việc hỗ trợ giáo viên giao bài, mở/khóa bài tập, chấm điểm và chữa bài (kết hợp chấm chữa thông minh bằng AI) cho học viên trên cả 4 kỹ năng Nghe – Nói – Đọc – Viết theo từng lớp học; đồng thời cho phép học viên làm bài, nộp bài, xem lại phần chữa bài và theo dõi kết quả trực tuyến. Hệ thống có tích hợp chức năng quản lý cơ bản về học viên, lớp học, điểm số, và một phân hệ Admin dành cho trung tâm để quản lý tài khoản người dùng.

**Đối tượng sử dụng:** Quản lý trung tâm, giáo viên, học viên


| STT | Ca sử dụng                             | Mô tả ngắn                                                                                                                            | Tác nhân                         |
| --- | ---------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------- |
| 1   | Đăng nhập                             | Người dùng đăng nhập vào hệ thống bằng tài khoản và mật khẩu.                                                             | Quản lý, Giáo viên, Học viên |
| 2   | Đăng xuất                             | Người dùng đăng xuất khỏi tài khoản đang sử dụng.                                                                            | Quản lý, Giáo viên, Học viên |
| 3   | Quản lý tài khoản cá nhân          | Người dùng xem và cập nhật thông tin cá nhân, mật khẩu và các thông tin liên quan.                                        | Quản lý, Giáo viên, Học viên |
| 4   | Quản lý tài khoản người dùng      | Quản lý trung tâm tạo, cập nhật, khóa/mở khóa và quản lý tài khoản người dùng.                                          | Quản lý                          |
| 5   | Phân quyền người dùng               | Quản lý trung tâm thiết lập và quản lý quyền truy cập theo vai trò của người dùng.                                        | Quản lý                          |
| 6   | Quản lý giáo viên                    | Quản lý trung tâm xem, thêm, cập nhật và quản lý thông tin giáo viên.                                                        | Quản lý                          |
| 7   | Quản lý học viên                     | Quản lý trung tâm quản lý thông tin, tài khoản và trạng thái của học viên.                                                 | Quản lý                          |
| 8   | Quản lý lớp học                      | Quản lý trung tâm tạo, cập nhật, xem và quản lý lớp học, cập nhật giáo viên quản lý lớp.                              | Quản lý                          |
| 9  | Quản lý thành viên lớp học         | Quản lý trung tâm hoặc giáo viên thêm, xóa và quản lý học viên thuộc lớp học.                                            | Quản lý, Giáo viên             |
| 10  | Xem danh sách lớp học                 | Người dùng xem các lớp học mà mình được tham gia hoặc quản lý.                                                             | Giáo viên, Học viên           |
| 11  | Quản lý bài tập                      | Giáo viên tạo, chỉnh sửa, xóa, xem và quản lý các bài tập theo lớp học.                                                    | Giáo viên                        |
| 12  | Tạo bài tập                           | Giáo viên tạo bài tập, thiết lập nội dung, thời gian, điểm số và các yêu cầu làm bài.                                  | Giáo viên                        |
| 13  | Mở bài tập                            | Giáo viên mở bài tập để học viên có thể truy cập và thực hiện.                                                            | Giáo viên                        |
| 14  | Khóa bài tập                          | Giáo viên khóa bài tập để ngăn học viên tiếp tục làm hoặc nộp bài.                                                       | Giáo viên                        |
| 15  | Xem bài tập                            | Học viên xem danh sách, nội dung, thời hạn và trạng thái của các bài tập được giao.                                      | Học viên                         |
| 16  | Làm bài tập viết                     | Học viên thực hiện và nộp bài tập kỹ năng Viết.                                                                               | Học viên                         |
| 17  | Làm bài tập nói                      | Học viên thực hiện bài tập Nói, ghi âm hoặc tải lên phần trả lời và nộp bài.                                            | Học viên                         |
| 18  | Làm bài tập đọc                     | Học viên thực hiện các câu hỏi và bài tập kỹ năng Đọc.                                                                     | Học viên                         |
| 19  | Làm bài tập nghe                      | Học viên nghe nội dung và thực hiện các câu hỏi của bài tập Nghe.                                                            | Học viên                         |
| 20  | Nộp bài tập                           | Học viên hoàn thành và gửi bài làm lên hệ thống để chấm điểm.                                                            | Học viên                         |
| 21  | Xem trạng thái bài làm               | Học viên theo dõi trạng thái bài làm như chưa làm, đã nộp, đã chấm hoặc đã chữa.                                     | Học viên                         |
| 22  | Chấm bài                               | Giáo viên xem bài làm và thực hiện chấm điểm cho học viên.                                                                   | Giáo viên                        |
| 23  | Hỗ trợ chấm chữa bài bằng AI       | Hệ thống sử dụng AI để phân tích bài làm, đưa ra điểm số, nhận xét và gợi ý sửa lỗi.                               | Giáo viên                        |
| 24  | Hỗ trợ chấm chữa bài viết bằng AI | AI phân tích bài viết, phát hiện lỗi ngữ pháp, từ vựng, chính tả, diễn đạt và đưa ra nhận xét.                      | Giáo viên                        |
| 25  | Hỗ trợ chấm chữa bài nói bằng AI  | AI phân tích phần nói dựa trên nội dung và các tiêu chí phù hợp như phát âm, từ vựng, ngữ pháp và độ trôi chảy. | Giáo viên                        |
| 26  | Chấm bài đọc                         | Hệ thống tự động chấm các câu trả lời của bài Đọc và tính điểm.                                                        | Giáo viên                       |
| 27  | Chấm bài nghe                          | Hệ thống tự động chấm các câu trả lời của bài Nghe và tính điểm.                                                         | Giáo viên                        |
| 28  | Xem điểm số                           | Người dùng xem điểm của các bài tập và kết quả học tập.                                                                    | Giáo viên, Học viên            |
| 29  | Xem kết quả học tập                  | Học viên theo dõi kết quả và tiến độ học tập theo bài tập, kỹ năng hoặc lớp học.                                       | Học viên                         |
| 30  | Xem bài chữa                           | Học viên xem nhận xét, lỗi sai, đáp án và hướng dẫn sửa bài sau khi được chấm.                                         | Học viên                         |
| 31  | Xem và đánh giá kết quả học viên | Giáo viên xem điểm số, bài làm và kết quả học tập của học viên trong lớp.                                                | Giáo viên                        |
| 32  | Quản lý điểm số                     | Giáo viên xem, cập nhật và quản lý điểm số của học viên trong lớp.                                                         | Giáo viên                        |
| 33  | Theo dõi tiến độ học tập           | Giáo viên theo dõi tình trạng làm bài và kết quả của học viên theo từng kỹ năng.                                         | Giáo viên                        |

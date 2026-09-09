# NHÁP NỘI DUNG — TÀI LIỆU KẾ HOẠCH DỰ ÁN (PP)

---

# CHƯƠNG 1: GIỚI THIỆU

## 1.1. Mục đích của kế hoạch quản lý dự án

Mục đích của kế hoạch quản lý dự án là cung cấp một khuôn khổ toàn diện, có hệ thống cho việc lập kế hoạch, theo dõi và quản lý toàn bộ hoạt động trong vòng đời của dự án "Website quản lý bài tập và chấm chữa bài tiếng Anh thông minh". Tài liệu này đóng vai trò là điểm tham chiếu chung để mọi bên liên quan — giảng viên hướng dẫn cùng các thành viên đảm nhận vai trò Project Manager, DevOps, Frontend Developer, Backend Developer và Tester — có cùng một cách hiểu thống nhất về mục tiêu, phạm vi, tiến độ và trách nhiệm của từng cá nhân trong nhóm.

Bên cạnh vai trò định hướng chung, kế hoạch quản lý dự án còn là công cụ để nhóm kiểm soát rủi ro và kiểm soát thay đổi trong suốt quá trình phát triển. Do đặc thù của dự án là xây dựng một hệ thống nghiệp vụ có cấu trúc dữ liệu tương đối phức tạp — với luồng giao bài, làm bài, nộp bài và chấm chữa xuyên suốt bốn kỹ năng Nghe, Nói, Đọc, Viết, đồng thời tích hợp trí tuệ nhân tạo hỗ trợ chấm chữa hai kỹ năng Nói và Viết — việc có một kế hoạch quản lý rõ ràng ngay từ đầu giúp nhóm chủ động phân bổ nguồn lực hợp lý, tránh phát sinh xung đột giữa các artifact đã được phê duyệt (như sơ đồ thực thể quan hệ, đặc tả use case) trong quá trình phát triển về sau.

## 1.2. Đặc điểm của dự án

Dự án mang những đặc điểm sau, được xác định làm cơ sở cho toàn bộ các quyết định về phạm vi, công nghệ và cách thức tổ chức triển khai trong các chương tiếp theo:

- **Mục tiêu chiến lược:** Đây là một dự án phát triển phần mềm quy mô nhỏ, được thực hiện trong khuôn khổ học phần Project 1, nhằm số hóa toàn bộ quy trình giao bài, làm bài, nộp bài và chấm chữa bài tiếng Anh trên bốn kỹ năng Nghe – Nói – Đọc – Viết theo từng lớp học cụ thể. Điểm nhấn chiến lược của dự án nằm ở việc tích hợp trí tuệ nhân tạo vào khâu chấm chữa hai kỹ năng Nói và Viết — vốn là hai kỹ năng tốn nhiều thời gian chấm thủ công nhất — nhằm rút ngắn thời gian phản hồi cho giáo viên và giúp học viên nhận được nhận xét chi tiết, kịp thời hơn sau mỗi lần nộp bài. Dự án không hướng tới mục tiêu thương mại hóa mà tập trung vào việc rèn luyện năng lực phân tích yêu cầu, thiết kế hệ thống và làm việc nhóm theo quy trình phát triển phần mềm thực tế.

- **Phân khúc kiến trúc doanh nghiệp:** Dự án có quy mô nhỏ, mô phỏng bài toán quản lý học tập của một trung tâm Anh ngữ đơn lẻ, không có khái niệm nhiều chi nhánh (điểm này đã được nhóm rà soát kỹ trong toàn bộ 33 use case và xác nhận không đưa khái niệm "chi nhánh" vào phạm vi thiết kế, do không có yêu cầu nghiệp vụ nào thực sự cần đến). Vì vậy, hệ thống không tác động và cũng không cần tương thích với các hệ thống doanh nghiệp lớn hơn, mà chỉ phục vụ đúng mục đích học tập và báo cáo môn học.

- **Đầu tư IT:** Dự án không nhận được bất kỳ khoản đầu tư tài chính thực tế nào. Toàn bộ hạ tầng và công cụ được sử dụng đều là các giải pháp miễn phí hoặc có chi phí rất thấp — bao gồm GitHub cho quản lý mã nguồn, Docker cho container hóa, PostgreSQL cho cơ sở dữ liệu, cùng các dịch vụ hạ tầng có gói miễn phí phù hợp với ngân sách sinh viên. Cách tiếp cận này vừa đảm bảo tính khả thi về mặt chi phí, vừa giúp nhóm làm quen với các công cụ phổ biến trong thực tế công việc sau này.

---

# CHƯƠNG 2: TÓM TẮT ĐIỀU LỆ DỰ ÁN

**Mục tiêu**

Dự án hướng tới xây dựng một nền tảng quản lý học tập trực tuyến, cho phép trung tâm Anh ngữ và giáo viên tổ chức lớp học, giao bài tập theo từng kỹ năng Nghe, Nói, Đọc, Viết một cách linh hoạt và có kiểm soát. Đồng thời, hệ thống hỗ trợ học viên làm bài, nộp bài và theo dõi kết quả học tập của bản thân theo thời gian một cách trực quan. Một mục tiêu quan trọng khác là tích hợp trí tuệ nhân tạo vào quy trình chấm chữa bài Nói và bài Viết, nhằm rút ngắn đáng kể thời gian phản hồi cho giáo viên so với phương pháp chấm thủ công truyền thống.

**Phạm vi**

Hệ thống phục vụ ba nhóm tác nhân chính: Quản lý trung tâm, Giáo viên và Học viên. Giáo viên có thể tạo, mở và khóa bài tập theo lớp học phụ trách; chấm điểm bài làm của học viên, trong đó có thể tham khảo gợi ý phân tích từ AI đối với bài Nói và bài Viết, hoặc dựa vào kết quả chấm tự động đối với bài Đọc và bài Nghe. Học viên thực hiện bài tập trên cả bốn kỹ năng, nộp bài đúng thời hạn được thiết lập, xem lại phần chữa bài chi tiết sau khi được chấm và theo dõi lịch sử điểm số, tiến độ học tập theo thời gian. Quản lý trung tâm phụ trách quản lý tài khoản người dùng, phân quyền theo vai trò, quản lý thông tin giáo viên, học viên và tổ chức lớp học.

**Thời gian**

Dự kiến dự án được triển khai trong khoảng từ ngày `[__/__/2026]` đến ngày `[__/__/2026]`, tương ứng với thời lượng học phần Project 1 theo lịch của Khoa Công nghệ Thông tin. *(Ghi chú: PM cần đối chiếu và điền chính xác theo lịch giảng dạy học kỳ hiện tại; bài mẫu PP khóa trước sử dụng khung khoảng 3 tháng làm tham chiếu.)*

**Nguồn lực**

Đội ngũ thực hiện dự án gồm 5 thành viên, đảm nhận đồng thời 5 vai trò: 1 Project Manager (kiêm định hướng và phối hợp chung), 1 DevOps, 1 Frontend Developer, 1 Backend Developer và 1 Tester, cụ thể theo bảng phân công và đánh giá ở phần đầu tài liệu.

---

# CHƯƠNG 3: TỔNG QUAN

Dự án được thực hiện trong khuôn khổ học phần Project 1 tại Khoa Công nghệ Thông tin, Trường Đại học Giao thông Vận tải, dưới sự hướng dẫn của TS. Nguyễn Trọng Phúc. Dự án dự kiến bắt đầu từ `[__/__/2026]` và hoàn thành trong khoảng thời gian tương ứng với thời lượng học phần. Các bên liên quan chính của dự án bao gồm nhóm phát triển gồm năm sinh viên và giảng viên hướng dẫn, trong đó giảng viên đóng vai trò định hướng chuyên môn, phê duyệt các cột mốc quan trọng và đánh giá chất lượng sản phẩm cuối cùng.

Do đây là một dự án học phần mang tính chất mô phỏng môi trường phát triển phần mềm doanh nghiệp, không tồn tại hợp đồng thương mại theo nghĩa thông thường, mà thay vào đó là một quy ước nội bộ giữa nhóm phát triển và giảng viên hướng dẫn, với các mốc bàn giao tương ứng với yêu cầu của học phần. Cụ thể, các mốc chính bao gồm việc hoàn thành giai đoạn phân tích yêu cầu và thiết kế (thể hiện qua Tài liệu đặc tả yêu cầu SRS, sơ đồ thực thể quan hệ gồm 17 bảng, sơ đồ CRC và đặc tả 33 use case), giai đoạn lập kế hoạch quản lý dự án và kế hoạch kiểm thử, và cuối cùng là giai đoạn lập trình, tích hợp, kiểm thử và bàn giao sản phẩm hoàn chỉnh.

- Loại hợp đồng: dự án nội bộ sinh viên, không có hợp đồng thương mại.
- Ngày bắt đầu: `[__/__/2026]`.
- Ngày kết thúc: `[__/__/2026]`.
- Mốc quan trọng: hoàn thành Tài liệu đặc tả yêu cầu (SRS) bao gồm sơ đồ ERD đã chốt ở mức 17 bảng, Domain Model, thẻ CRC và đặc tả chi tiết 33 use case; hoàn thành Tài liệu kế hoạch quản lý dự án (PP) và Tài liệu kế hoạch kiểm thử (TP); hoàn thành thiết lập môi trường phát triển cục bộ (đã hoàn tất bởi DevOps, thể hiện qua tài liệu `dev-environment-setup.md` và tệp `docker-compose.dev.yml`); hoàn thành migration cơ sở dữ liệu bằng Flyway (`V1__init_schema.sql`), hiện đang được Backend Developer triển khai.
- Bên liên quan: nhóm phát triển gồm năm sinh viên và giảng viên hướng dẫn TS. Nguyễn Trọng Phúc.

---

# CHƯƠNG 4: GIẢ ĐỊNH, RÀNG BUỘC, RỦI RO

## 4.1. Giả định

Trong quá trình lập kế hoạch, nhóm đưa ra một số giả định làm cơ sở để xây dựng lịch trình và phân bổ nguồn lực. Các giả định này cần được rà soát lại định kỳ trong suốt vòng đời dự án, vì nếu một giả định không còn đúng, kế hoạch tương ứng cũng cần được điều chỉnh:

- Đội ngũ phát triển có đủ nguồn lực về thời gian, kiến thức nền tảng và nhận được sự hỗ trợ kỹ thuật cần thiết từ giảng viên hướng dẫn trong suốt quá trình thực hiện dự án.
- Mỗi thành viên trong nhóm thực hiện nhiệm vụ được phân công theo đúng kế hoạch và hoàn thành đúng tiến độ đề ra, qua đó đảm bảo dự án không bị chậm trễ và có thể đạt được các mốc quan trọng đã lên kế hoạch.
- Các công cụ và công nghệ phát triển được lựa chọn — bao gồm Spring Boot, Spring Data JPA kết hợp Hibernate, Flyway cho phía Backend, cùng ReactJS, Vite, TypeScript cho phía Frontend, và Docker cho môi trường triển khai — hoạt động ổn định trong suốt quá trình phát triển và kiểm thử, không phát sinh sự cố nghiêm trọng về tương thích phiên bản.
- Các yêu cầu đã được thống nhất và ghi nhận trong Tài liệu đặc tả yêu cầu, bao gồm sơ đồ thực thể quan hệ đã được nhóm và Project Manager cùng chốt ở mức 17 bảng, sẽ không có thay đổi lớn nào trong quá trình thực hiện dự án, trừ trường hợp có văn bản giải trình chính thức và được Project Manager phê duyệt theo đúng quy trình quản lý thay đổi tại mục 5.6.
- Giáo viên và học viên — với vai trò là người dùng được mô phỏng trong quá trình kiểm thử và trình diễn sản phẩm — có thể dễ dàng làm quen và sử dụng thành thạo các chức năng cơ bản của hệ thống như giao bài, làm bài, nộp bài và theo dõi kết quả mà không cần đào tạo chuyên sâu.
- Dịch vụ trí tuệ nhân tạo hỗ trợ chấm chữa (thông qua các pipeline đánh giá kỹ năng Nói và Viết đã được nhóm nghiên cứu và mô tả chi tiết) hoạt động ổn định trong phạm vi môi trường thử nghiệm của dự án; nhóm giả định không tính đến rủi ro gián đoạn dài hạn từ phía nhà cung cấp dịch vụ mô hình ngôn ngữ lớn bên thứ ba trong phạm vi của giả định này.

## 4.2. Ràng buộc

Bên cạnh các giả định, dự án chịu sự chi phối của một số ràng buộc khách quan mà nhóm cần tuân thủ trong suốt quá trình triển khai:

- Dự án phải được hoàn thành trong khoảng thời gian quy định của học phần và không thể xin gia hạn thêm thời gian thực hiện.
- Hệ thống cần chạy ổn định trên các trình duyệt web phổ biến, cụ thể là Google Chrome, Microsoft Edge và Mozilla Firefox ở các phiên bản gần đây.
- Nhóm chỉ có nguồn lực nhân sự giới hạn ở năm thành viên, trong đó một số thành viên đảm nhận kiêm nhiệm nhiều vai trò cùng lúc (ví dụ Project Manager kiêm định hướng chung, một số thành viên hỗ trợ chéo giữa Backend và DevOps), do đó khối lượng công việc cần được phân chia hợp lý để tránh quá tải cục bộ.
- Dự án không có nguồn tài trợ tài chính thực tế, vì vậy nhóm chỉ có thể sử dụng các tài nguyên miễn phí hoặc có chi phí rất thấp, bao gồm các dịch vụ hạ tầng có gói miễn phí và các công cụ mã nguồn mở.
- Hệ thống cần tuân thủ các nguyên tắc bảo mật cơ bản — bao gồm cơ chế xác thực, phân quyền theo vai trò, mã hóa (hash) mật khẩu trước khi lưu trữ, và không lưu trữ token xác thực dưới dạng dữ liệu gốc trong cơ sở dữ liệu — dù đây là một dự án mang tính chất mô phỏng phục vụ mục đích học tập.
- Chức năng chấm chữa bằng trí tuệ nhân tạo cho kỹ năng Nói và Viết phụ thuộc vào việc gọi API tới dịch vụ mô hình ngôn ngữ lớn của bên thứ ba; điều này nằm ngoài khả năng kiểm soát trực tiếp của nhóm về mặt độ trễ phản hồi cũng như chi phí phát sinh khi gọi API với tần suất lớn.

## 4.3. Rủi ro

Nhóm xác định các rủi ro tiềm ẩn có thể ảnh hưởng đến tiến độ, chất lượng hoặc phạm vi của dự án, đồng thời đánh giá mức độ ưu tiên xử lý dựa trên tích của xác suất xảy ra và mức độ ảnh hưởng nếu rủi ro thực sự xảy ra. Cách đánh giá này giúp nhóm tập trung nguồn lực vào những rủi ro có mức độ Cao trước, thay vì dàn trải đều cho mọi rủi ro:

| Mã | Mô tả rủi ro | Xác suất | Ảnh hưởng | Mức độ | Ghi chú |
| --- | --- | --- | --- | --- | --- |
| R1 | Thành viên không đáp ứng deadline hoặc gặp khó khăn kỹ thuật, làm trì hoãn tiến độ chung | Trung bình | Cao | **Cao** | Ảnh hưởng trực tiếp đến thời điểm bàn giao cuối cùng |
| R2 | Phân chia công việc không hợp lý, dẫn đến chồng chéo hoặc bỏ sót nhiệm vụ | Trung bình | Trung bình | Trung bình | Cần Project Manager theo dõi sát qua công cụ quản lý tiến độ |
| R3 | Sử dụng công nghệ chưa thực sự thành thạo (đặc biệt là việc tích hợp pipeline AI cho Nói/Viết) gây ra lỗi kỹ thuật khó xử lý | Trung bình | Cao | **Cao** | Rủi ro tập trung nhiều nhất ở module chấm chữa bằng AI |
| R4 | Thời gian và nguồn lực có giới hạn khiến một số tính năng không đạt chất lượng như kỳ vọng ban đầu | Cao | Trung bình | **Cao** | Cần ưu tiên hoàn thiện các tính năng lõi trước các tính năng mở rộng |
| R5 | Phụ thuộc vào các công cụ, dịch vụ miễn phí và mã nguồn mở có thể gặp sự cố hoặc ngừng hỗ trợ | Thấp | Trung bình | Thấp | Đã có phương án dự phòng, xem thêm mục 6.5.4 |
| R6 | Chi phí hoặc độ trễ khi gọi API dịch vụ mô hình ngôn ngữ lớn (phục vụ chấm AI) vượt quá dự kiến, hoặc dịch vụ bên thứ ba gặp sự cố | Trung bình | Trung bình | Trung bình | Cần có phương án chấm thủ công thay thế khi AI không khả dụng |
| R7 | Phát sinh yêu cầu thay đổi ảnh hưởng đến các artifact đã được phê duyệt (đặc biệt là sơ đồ ERD 17 bảng) | Thấp | Cao | Trung bình | Đã có quy trình giải trình bắt buộc trước khi sửa artifact đã duyệt, xem mục 5.6 |

Đối với các rủi ro được xếp vào nhóm mức độ Cao (R1, R3, R4), nhóm áp dụng các giải pháp phòng ngừa chủ động: thiết lập lịch trình với các mốc thời gian cụ thể và có khoảng đệm hợp lý; tổ chức họp định kỳ để đánh giá tiến độ thực tế và kịp thời điều chỉnh kế hoạch; ưu tiên hoàn thiện trước các tính năng cốt lõi liên quan đến xác thực, phân quyền và luồng giao – nộp – chấm bài cơ bản, trước khi đầu tư thời gian vào các tính năng nâng cao như tích hợp AI chấm chữa chuyên sâu. Đối với các rủi ro còn lại, nhóm duy trì việc theo dõi định kỳ và chuẩn bị sẵn phương án dự phòng tương ứng như đã đề cập ở cột Ghi chú.

---

# CHƯƠNG 5: PHẠM VI DỰ ÁN

## 5.1. Quản lý yêu cầu

Toàn bộ yêu cầu của dự án được trình bày một cách chi tiết trong Tài liệu đặc tả yêu cầu (SRS), bao gồm hai nhóm chính: yêu cầu chức năng, được đặc tả thông qua 33 use case chi tiết theo mẫu biểu thống nhất (bao trùm các nghiệp vụ từ đăng nhập, quản lý tài khoản, quản lý lớp học, quản lý bài tập, đến làm bài, nộp bài, chấm bài và xem kết quả học tập); và yêu cầu phi chức năng, được tổ chức thành tám nhóm tiêu chí gồm Bảo mật (Security), Hiệu năng (Performance), Khả năng chịu tải (Capacity), Tương thích (Compatibility), Độ tin cậy (Reliability), Khả năng mở rộng (Scalability), Khả năng bảo trì (Maintainability) và Khả năng sử dụng (Usability).

Để đảm bảo tính khả thi về mặt kỹ thuật lẫn thời gian triển khai, nhóm thống nhất chỉ thực hiện đúng các chức năng và yêu cầu phi chức năng đã được xác nhận trong SRS, cụ thể bao gồm:

- Đăng nhập, đăng xuất và quản lý tài khoản cá nhân cho cả ba vai trò người dùng.
- Quản lý tài khoản người dùng và phân quyền theo vai trò Quản lý, Giáo viên, Học viên.
- Quản lý thông tin giáo viên, học viên, lớp học và thành viên lớp học.
- Quản lý bài tập — bao gồm tạo, mở và khóa bài tập — trên cả bốn kỹ năng Nghe, Nói, Đọc, Viết.
- Làm bài, nộp bài và theo dõi trạng thái bài làm của học viên.
- Chấm bài, kết hợp giữa chấm thủ công có tham khảo gợi ý từ AI đối với kỹ năng Nói và Viết, và chấm tự động đối với kỹ năng Đọc và Nghe.
- Xem điểm số, xem kết quả học tập và xem bài chữa chi tiết sau khi được chấm.
- Quản lý điểm số và theo dõi tiến độ học tập theo từng học viên, từng lớp học.
- Các yêu cầu phi chức năng liên quan đến bảo mật, hiệu năng phản hồi, khả năng chịu tải khi nhiều người dùng thao tác đồng thời và khả năng bảo trì mã nguồn lâu dài.

## 5.2. Mô tả phạm vi

**Mô tả sản phẩm và dịch vụ:** Dự án xây dựng một website quản lý bài tập và chấm chữa bài tiếng Anh, phục vụ đồng thời ba nhóm người dùng là Quản lý trung tâm, Giáo viên và Học viên. Về phía Giáo viên, hệ thống cho phép giao bài, chủ động mở và khóa bài tập theo mốc thời gian quy định, chấm điểm bài làm của học viên (có thể tham khảo phân tích gợi ý từ AI đối với hai kỹ năng Nói và Viết), đồng thời theo dõi sát tình trạng nộp bài của từng học viên trong lớp mình phụ trách. Về phía Học viên, hệ thống cho phép làm bài tập trực tuyến trên cả bốn kỹ năng, nộp bài đúng hạn, xem lại phần chữa bài cá nhân một cách chi tiết ngay sau khi được chấm, cũng như theo dõi lịch sử điểm số và tiến độ học tập của bản thân theo thời gian. Bên cạnh đó, dự án còn xây dựng một phân hệ quản lý cơ bản dành cho Quản lý trung tâm, bao gồm quản lý học viên, lớp học và điểm số.

**Giới hạn dự án:**

- Không phát triển ứng dụng di động riêng biệt; hệ thống chỉ được triển khai dưới dạng ứng dụng web truy cập qua trình duyệt.
- Không tích hợp hệ thống thanh toán học phí trong phạm vi phiên bản hiện tại — đây là điểm khác biệt so với hướng đề tài "quản lý học viên trung tâm Tiếng Anh" được cân nhắc ở giai đoạn khởi tạo dự án, đã được Project Manager và cả nhóm thống nhất chốt lại theo đúng đề tài chính thức là quản lý bài tập và chấm chữa bài.
- Không tích hợp với các nền tảng học tập hoặc dịch vụ bên ngoài khác, ngoại trừ dịch vụ trí tuệ nhân tạo được lựa chọn phục vụ riêng cho chức năng chấm chữa bài Nói và bài Viết.

## 5.3. Quản lý phạm vi

Phạm vi của dự án được xác định rõ ràng là: xây dựng một nền tảng hỗ trợ đầy đủ chu trình giao bài – làm bài – nộp bài – chấm chữa (có sự hỗ trợ của trí tuệ nhân tạo) cho bốn kỹ năng tiếng Anh, được tổ chức theo từng lớp học cụ thể, đi kèm với một phân hệ quản lý cơ bản về học viên, lớp học và điểm số dành cho Quản lý trung tâm.

Về công cụ quản lý phạm vi, dự án sử dụng Trello để theo dõi tiến độ triển khai và đảm bảo các tính năng nằm đúng trong phạm vi đã xác định được thực hiện đầy đủ. Nhóm thực hiện đánh giá định kỳ về phạm vi trong các buổi họp Sprint, nhằm đảm bảo không có tính năng nào bị mở rộng vượt quá phạm vi ban đầu mà chưa thông qua quy trình phê duyệt của Project Manager.

## 5.4. Cấu trúc công việc

Cấu trúc công việc của dự án được tổ chức thành các nhóm hạng mục lớn theo trình tự triển khai như sau:

**Chuẩn bị môi trường và công cụ** — bao gồm việc cài đặt các công cụ phát triển cần thiết (Docker, Node.js, JDK 21, Gradle) và thiết lập cấu hình môi trường phát triển cục bộ. Hạng mục này đã được hoàn thành bởi DevOps, thể hiện qua tài liệu `dev-environment-setup.md` và tệp cấu hình `docker-compose.dev.yml`.

**Thiết kế và cài đặt cơ sở dữ liệu** — bao gồm việc phân tích yêu cầu nghiệp vụ, xây dựng sơ đồ thực thể quan hệ (đã được chốt ở mức 17 bảng sau nhiều vòng rà soát và đối chiếu với các sơ đồ hoạt động), và hiện thực hóa thành tệp migration Flyway `V1__init_schema.sql`, bao gồm các định nghĩa kiểu liệt kê (ENUM) và các câu lệnh tạo bảng tương ứng.

**Phát triển Backend và Frontend** — bao gồm phát triển các API phục vụ đăng ký, đăng nhập và phân quyền người dùng; API quản lý lớp học và thành viên lớp; API quản lý bài tập theo từng kỹ năng; API xử lý việc nộp bài, chấm bài và quản lý điểm số; đồng thời xây dựng giao diện người dùng tương ứng cho từng vai trò Quản lý, Giáo viên và Học viên, đảm bảo phản ánh đúng mô hình dữ liệu một bài tập có thể bao gồm nhiều học phần (module) trải trên nhiều kỹ năng khác nhau.

**Tích hợp chấm chữa bằng trí tuệ nhân tạo** — bao gồm việc tích hợp quy trình đánh giá kỹ năng Nói, gồm các bước xử lý âm thanh song song để trích xuất văn bản và phân tích phát âm, trích xuất các đặc trưng về độ trôi chảy và ngôn ngữ, rồi tổng hợp để một mô hình ngôn ngữ lớn đóng vai trò giám khảo đưa ra đánh giá cuối cùng; và việc tích hợp quy trình đánh giá kỹ năng Viết, gồm các bước trích xuất nội dung bài làm, xây dựng prompt theo tiêu chí chấm điểm, và gọi mô hình ngôn ngữ lớn để trả về kết quả đánh giá có cấu trúc.

**Tích hợp và kiểm thử** — bao gồm việc tích hợp giữa Frontend và Backend, thực hiện kiểm thử đơn vị, kiểm thử tích hợp, kiểm thử bảo mật cơ bản và kiểm thử hiệu năng, sau đó chạy thử nghiệm toàn bộ hệ thống trước khi bàn giao.

**Triển khai** — bao gồm việc triển khai hệ thống lên môi trường gần giống sản xuất, theo phương án hạ tầng do DevOps đề xuất, cùng với việc theo dõi, bảo trì hệ thống và cập nhật sửa lỗi khi cần thiết trong giai đoạn cuối dự án.

## 5.5. Kế hoạch triển khai

**Chiến lược triển khai:** dự án áp dụng phương pháp phát hành dần dần theo từng Sprint. Cuối mỗi Sprint, nhóm triển khai một phiên bản demo nội bộ để tự đánh giá tiến độ và, khi cần thiết, trình bày với giảng viên hướng dẫn để nhận phản hồi sớm. Sau khi hoàn tất việc sửa lỗi và cải thiện dựa trên phản hồi thu được, nhóm mới tiến hành tổng hợp thành phiên bản hoàn chỉnh để nộp vào cuối học phần.

**Thời gian triển khai:** việc triển khai từng phần được thực hiện vào cuối mỗi Sprint theo lịch trình cụ thể tại mục 6.2.1; hệ thống dự kiến được triển khai toàn bộ ở phiên bản hoàn chỉnh trước ngày `[__/__/2026]`.

**Tiêu chí nghiệm thu (Definition of Done):** để đảm bảo tính nhất quán trong việc đánh giá mức độ hoàn thành của từng hạng mục công việc, nhóm thống nhất một hạng mục chỉ được coi là hoàn thành khi đáp ứng đầy đủ các điều kiện sau:

1. Chức năng hoạt động đúng theo nội dung đặc tả use case tương ứng đã được ghi nhận trong SRS.
2. API liên quan đã được kiểm thử qua Postman với ít nhất các trường hợp chính (happy path) và một số trường hợp lỗi cơ bản (dữ liệu thiếu, không hợp lệ, không đủ quyền truy cập).
3. Mã nguồn đã trải qua quy trình Pull Request và được ít nhất một thành viên khác trong nhóm review trước khi được merge vào nhánh `develop`.
4. Không còn tồn đọng lỗi ở mức độ nghiêm trọng Cao hoặc Nghiêm trọng (theo cách phân loại mức độ nghiêm trọng tại Tài liệu kế hoạch kiểm thử) liên quan trực tiếp đến hạng mục đang xét.
5. Hạng mục đã được Tester xác nhận đạt (pass) đối với các test case liên quan đã thiết kế sẵn.
6. Nếu việc hoàn thành hạng mục kéo theo thay đổi ở một artifact đã được phê duyệt trước đó (ví dụ sơ đồ thực thể quan hệ, sơ đồ hoạt động), thay đổi đó đã được ghi nhận đầy đủ trong văn bản giải trình tương ứng và được Project Manager phê duyệt theo đúng quy trình quản lý thay đổi.

## 5.6. Quản lý thay đổi

Trong quá trình phát triển dự án, nhóm có thể đối mặt với các tình huống phát sinh làm thay đổi hoặc ảnh hưởng đến phạm vi, tiến độ đã lập kế hoạch ban đầu. Mọi đề xuất thay đổi từ các thành viên trong nhóm đều phải được trình bày rõ ràng, đánh giá về mức độ tác động và được Project Manager phê duyệt trước khi chính thức thực hiện.

Đối với các trường hợp đặc biệt liên quan đến việc sửa đổi những artifact đã được phê duyệt trước đó — điển hình như sơ đồ thực thể quan hệ, sơ đồ hoạt động hay đặc tả use case — nhóm áp dụng nguyên tắc bắt buộc phải có văn bản giải trình đi kèm, nêu rõ lý do thay đổi, nội dung cụ thể được điều chỉnh và người thực hiện thay đổi. Cách làm này mô phỏng đúng môi trường phát triển phần mềm chuyên nghiệp thực tế, nơi mọi thay đổi đối với các tài liệu đã được phê duyệt (baseline) đều cần được kiểm soát chặt chẽ. Sau khi một thay đổi được phê duyệt, Project Manager có trách nhiệm cập nhật lại kế hoạch tổng thể, thông báo tới toàn bộ thành viên liên quan và theo dõi việc triển khai thay đổi đó cho đến khi hoàn tất.

---

# CHƯƠNG 6: PHƯƠNG PHÁP QUẢN LÝ DỰ ÁN TỔNG THỂ

Trong dự án phần mềm này, nhóm sử dụng mô hình quản lý dự án Scrum với chu kỳ làm việc (Sprint) kéo dài `[__ tuần]`. Đây là mô hình quản lý dự án theo phương pháp Agile được sử dụng phổ biến hiện nay, với mục tiêu hoàn thành một phiên bản sản phẩm có giá trị và có thể sử dụng được ở cuối mỗi Sprint. Việc áp dụng mô hình này giúp nhóm dễ dàng theo dõi, đánh giá và điều chỉnh tiến độ phát triển dự án một cách chặt chẽ và linh hoạt hơn so với mô hình phát triển tuần tự truyền thống.

## 6.1. Ước tính

**Ước tính về công sức:** các thành viên trong nhóm dự tính cần khoảng `[__ tuần]` để hoàn thiện việc phát triển và triển khai hệ thống, cùng với `[__ tuần]` bổ sung để chuẩn bị sản phẩm, tài liệu và bài trình bày báo cáo cuối cùng với giảng viên.

**Ước tính về thời gian:** theo dự kiến, dự án bắt đầu triển khai từ ngày `[__/__/2026]` và kết thúc vào ngày `[__/__/2026]`. Trong suốt quá trình phát triển, dự án được chia thành các Sprint có độ dài `[__ tuần]` mỗi Sprint; kết thúc mỗi Sprint, nhóm tiến hành tổng kết và báo cáo kết quả trước khi chuyển sang Sprint tiếp theo.

**Ước tính về tài nguyên:**

- *Về nhân sự:* nhóm gồm 5 thành viên, phân công theo các vai trò Project Manager, DevOps, Frontend Developer, Backend Developer và Tester như đã trình bày trong bảng phân công đánh giá ở đầu tài liệu, trong đó Project Manager đồng thời giữ vai trò điều phối chung để đảm bảo cả nhóm làm việc đúng tiến độ theo nguyên tắc Scrum.
- *Về thiết bị:* mỗi thành viên sử dụng laptop cá nhân để lập trình, kiểm thử cũng như giao tiếp và trao đổi tài liệu trong suốt quá trình phát triển.
- *Về công cụ:* nhóm sử dụng các công cụ mã nguồn mở và miễn phí để phát triển phần mềm; danh mục công cụ cụ thể được trình bày chi tiết tại mục 6.6.1 — Quản lý cấu hình.

## 6.2. Quản lý lịch trình

Nhóm sử dụng Trello để quản lý lịch trình của dự án, bao gồm việc theo dõi tiến độ từng công việc theo Sprint và ghi nhận, báo cáo mọi thay đổi về lịch trình nếu phát sinh.

### 6.2.1. Cột mốc quan trọng

| Thời gian dự kiến | Mốc quan trọng |
| --- | --- |
| `[__/__ – __/__]` | Cài đặt công cụ và thiết lập cấu hình môi trường phát triển (đã hoàn thành); thiết kế sơ đồ thực thể quan hệ (đã chốt 17 bảng) và triển khai migration Flyway |
| `[__/__ – __/__]` | Xây dựng API và giao diện cho chức năng đăng ký, đăng nhập, phân quyền, quản lý tài khoản cá nhân và quản lý lớp học |
| `[__/__ – __/__]` | Xây dựng API và giao diện quản lý bài tập trên bốn kỹ năng Nghe, Nói, Đọc, Viết; chức năng làm bài và nộp bài |
| `[__/__ – __/__]` | Tích hợp chức năng chấm bài — chấm tự động cho kỹ năng Đọc, Nghe; chấm có AI hỗ trợ cho kỹ năng Nói, Viết — cùng chức năng quản lý điểm số |
| `[__/__ – __/__]` | Hoàn thiện trang quản trị dành cho Quản lý trung tâm, chức năng theo dõi tiến độ học tập, sửa lỗi tồn đọng và tối ưu giao diện người dùng |
| `[__/__ – __/__]` | Giai đoạn hoàn thiện: kiểm thử hồi quy (regression test) toàn bộ hệ thống, chuẩn bị báo cáo, slide thuyết trình và kịch bản demo |

*(Ghi chú cho Project Manager: cấu trúc bảng và trình tự các cột mốc giữ nguyên logic nghiệp vụ như bản kế hoạch tham chiếu của khóa trước, chỉ thay nội dung cho đúng với đề tài quản lý bài tập và chấm chữa bài — cần điền ngày tháng cụ thể theo lịch học phần thực tế trước khi hoàn thiện.)*

### 6.2.2. Tiến độ dự án

*(Mục này cần đính kèm ảnh chụp màn hình board Trello thể hiện trực quan tiến độ các Sprint, tương tự Hình 6.1 trong bài mẫu tham chiếu — Project Manager chụp và chèn hình trước khi hoàn thiện bản nộp cuối cùng.)*

## 6.3. Quản lý chất lượng

Chất lượng của dự án được đảm bảo thông qua việc tổ chức hoạt động kiểm thử song song với quá trình phát triển theo từng Sprint, được trình bày chi tiết trong Tài liệu kế hoạch kiểm thử (TP). Mỗi tính năng khi được hoàn thành trong một Sprint đều phải được kiểm thử ngay theo đúng tiêu chí nghiệm thu (Definition of Done) đã nêu tại mục 5.5, trước khi được xem là chính thức hoàn tất và được tích hợp vào phiên bản demo cuối Sprint. Cách tiếp cận kiểm thử song song này giúp nhóm phát hiện và xử lý lỗi sớm, tránh việc lỗi tích lũy dồn vào giai đoạn cuối dự án.

## 6.4. Quản lý nhân viên

Bảng dưới đây trình bày phân công nhân sự của dự án, trong đó bổ sung thêm việc phân định trách nhiệm chính và trách nhiệm hỗ trợ cho từng thành viên, nhằm làm rõ ai là người chịu trách nhiệm cuối cùng (Responsible) đối với từng mảng công việc và ai đóng vai trò hỗ trợ (Support) khi cần thiết:

| Họ tên | Vị trí | Vai trò chính | Trách nhiệm chính | Trách nhiệm hỗ trợ |
| --- | --- | --- | --- | --- |
| Hồ Việt Tùng | Project Manager, DevOps | Lập kế hoạch, giao nhiệm vụ và theo dõi tiến độ | Quản lý tiến độ tổng thể, phê duyệt các đề xuất thay đổi, phụ trách hạ tầng triển khai | Hỗ trợ nhóm hoàn thiện các tài liệu quản lý dự án (PP, SRS) |
| Phạm Minh Tuấn | DevOps | Hỗ trợ phát triển Backend | Thiết lập CI/CD, quản lý môi trường phát triển và sản xuất, container hóa ứng dụng | Hỗ trợ Đoàn Thái Sơn trong việc phát triển một số API |
| Trần Tiến Sơn | Frontend Developer | Phát triển giao diện người dùng | Xây dựng giao diện theo bản thiết kế thống nhất, tích hợp API từ Backend | Kiểm tra tính tương thích giao diện trên nhiều trình duyệt |
| Đoàn Thái Sơn | Backend Developer | Phát triển Backend | Thiết kế cơ sở dữ liệu, phát triển các API RESTful, tích hợp pipeline chấm chữa bằng AI | Chủ trì viết văn bản giải trình khi cần điều chỉnh artifact đã được phê duyệt |
| Nguyễn Văn Tú | Tester | Kiểm thử phần mềm | Thiết kế test case, thực hiện kiểm thử thủ công và tự động, ghi nhận và báo cáo lỗi | Phối hợp với đội phát triển để xác nhận việc khắc phục lỗi |

## 6.5. Quản lý rủi ro

Khi phát triển hệ thống quản lý bài tập và chấm chữa bài tiếng Anh, nhóm xác định có thể sẽ phải đối mặt với một số nhóm rủi ro chính sau đây. Mức độ ưu tiên xử lý của từng rủi ro đã được trình bày tại bảng đánh giá ở mục 4.3; phần dưới đây phân tích chi tiết nguyên nhân và giải pháp tương ứng cho từng nhóm rủi ro.

### 6.5.1. Rủi ro về tiến độ dự án

**Mô tả:** các thành viên trong nhóm không đáp ứng được deadline hoặc gặp khó khăn kỹ thuật có thể dẫn đến việc trì hoãn tiến độ dự án, ảnh hưởng trực tiếp đến thời gian bàn giao cuối cùng.

**Nguyên nhân:** thiếu kinh nghiệm thực tế của các thành viên trong nhóm khi làm việc với công nghệ mới; khối lượng công việc phát sinh lớn hơn so với ước tính ban đầu; thiếu kế hoạch chi tiết cho từng giai đoạn cụ thể của dự án.

**Giải pháp:** thiết lập lịch trình rõ ràng và mang tính thực tế với các mốc thời gian cụ thể; tổ chức các cuộc họp định kỳ để đánh giá tiến độ thực tế và điều chỉnh kế hoạch khi cần thiết; chủ động cung cấp hỗ trợ và tài nguyên bổ sung cho các thành viên đang gặp khó khăn trong tiến độ công việc.

### 6.5.2. Rủi ro về công nghệ

**Mô tả:** việc sử dụng các công nghệ chưa thực sự quen thuộc, đặc biệt là việc tích hợp các pipeline trí tuệ nhân tạo phục vụ chấm chữa kỹ năng Nói và Viết, có thể dẫn đến những lỗi kỹ thuật nghiêm trọng, gây khó khăn trong quá trình phát triển và kiểm thử.

**Nguyên nhân:** thiếu kiến thức hoặc kỹ năng chuyên sâu trong nhóm về các công nghệ liên quan đến xử lý âm thanh, mô hình ngôn ngữ lớn và các dịch vụ AI bên thứ ba; không có đủ thời gian để nghiên cứu và làm quen kỹ lưỡng với các công nghệ này trước khi triển khai chính thức.

**Giải pháp:** chủ động tìm kiếm sự hỗ trợ từ cộng đồng lập trình viên cũng như tài liệu chính thức của các thư viện, dịch vụ liên quan; dành thời gian riêng ở đầu Sprint để làm quen và thử nghiệm với công nghệ mới trước khi tích hợp vào hệ thống chính thức; xây dựng sẵn phương án chấm thủ công dự phòng cho các trường hợp dịch vụ AI gặp sự cố hoặc không phản hồi.

### 6.5.3. Rủi ro về chất lượng

**Mô tả:** do thời gian và nguồn lực của dự án có giới hạn, một số tính năng của hệ thống có thể không đạt được mức chất lượng như kỳ vọng ban đầu của nhóm.

**Nguyên nhân:** áp lực về thời gian làm giảm khả năng kiểm thử kỹ lưỡng và hoàn thiện sản phẩm; nguồn lực dành cho công tác phát triển và kiểm thử còn hạn chế so với khối lượng công việc thực tế.

**Giải pháp:** thiết lập các tiêu chí chất lượng rõ ràng ngay từ đầu — cụ thể là tiêu chí nghiệm thu (Definition of Done) đã trình bày tại mục 5.5 — và đảm bảo mọi tính năng đều phải đạt các tiêu chí này trước khi được coi là hoàn thành; đầu tư vào việc kiểm thử tự động cho các luồng nghiệp vụ đã ổn định nhằm tiết kiệm thời gian và nâng cao độ chính xác; tổ chức các giai đoạn phát hành nhỏ hơn theo từng Sprint để có thể kiểm tra và cải thiện chất lượng một cách liên tục thay vì dồn vào cuối dự án.

### 6.5.4. Rủi ro phụ thuộc vào công cụ miễn phí và mã nguồn mở

**Mô tả:** dự án phụ thuộc vào các công cụ miễn phí, mã nguồn mở cũng như dịch vụ mô hình ngôn ngữ lớn của bên thứ ba phục vụ chức năng chấm chữa bằng AI. Nếu có bất kỳ sự cố nào xảy ra với các dịch vụ này, dự án có thể bị gián đoạn hoặc không thể hoàn thành đúng hạn các tính năng liên quan.

**Nguyên nhân:** nhóm không có khả năng kiểm soát trực tiếp về độ ổn định và tính khả dụng của các công cụ, dịch vụ miễn phí; không nhận được hỗ trợ kỹ thuật chính thức từ phía nhà cung cấp dịch vụ khi có sự cố xảy ra.

**Giải pháp:** lập kế hoạch dự phòng cho các công cụ và dịch vụ đóng vai trò quan trọng, đặc biệt là dịch vụ mô hình ngôn ngữ lớn phục vụ chấm chữa AI; xem xét phương án chuyển đổi sang nhà cung cấp khác nếu cần thiết; theo dõi thường xuyên tình trạng hoạt động của các công cụ, dịch vụ đang sử dụng để có thể phản ứng kịp thời trước khi sự cố ảnh hưởng nghiêm trọng đến tiến độ chung.

## 6.6. Quản lý cấu hình

### 6.6.1. Phương pháp và công cụ sử dụng để quản lý

- **Công cụ quản lý mã nguồn:** GitHub — địa chỉ kho mã nguồn: `[link repo]`.
- **Công cụ quản lý dự án và tiến độ:** Trello — địa chỉ board quản lý: `[link board]`.
- **Công cụ quản lý báo cáo và tài liệu dự án:** OneDrive/Google Drive — địa chỉ thư mục lưu trữ: `[link thư mục]`.
- **Quy trình kiểm soát mã nguồn:** nhánh `main` đại diện cho phiên bản ổn định của dự án, chỉ chứa mã nguồn đã hoàn thiện và được kiểm thử đầy đủ, có thể sử dụng cho các bản demo hoặc kiểm thử cuối cùng; nhánh `develop` chứa mã nguồn của tất cả các tính năng mới và cải tiến đang được phát triển, là nơi tổng hợp các thay đổi từ các nhánh tính năng sau khi hoàn thành và đã qua kiểm thử cơ bản; nhánh tính năng theo quy ước `feature/tên-tính-năng` (ví dụ `feature/ai-writing-grading`, `feature/class-management`) được tạo riêng cho mỗi tính năng mới hoặc mỗi lần sửa lỗi, và được merge trở lại nhánh `develop` sau khi hoàn thành và kiểm thử cục bộ.
- **Công cụ CI/CD:** GitHub Actions, dùng để tự động hóa quy trình kiểm thử và triển khai mỗi khi có thay đổi được đẩy lên kho mã nguồn.
- **Công cụ quản lý test case và bug:** Excel, theo đề xuất của Tester nhằm ghi nhận test case, dữ liệu kiểm thử và theo dõi kết quả một cách trực quan, dễ tổng hợp báo cáo.
- **Môi trường phát triển tích hợp:** Visual Studio Code cho phát triển chung, IntelliJ IDEA cho phát triển và kiểm thử phía Backend.
- **Ngôn ngữ lập trình:** Java cho phía Backend, TypeScript cho phía Frontend.
- **Công nghệ chính:** PostgreSQL, Spring Boot, Spring Data JPA kết hợp Hibernate, Flyway phục vụ quản lý migration cơ sở dữ liệu, ReactJS kết hợp Vite cho giao diện người dùng, và Docker phục vụ container hóa ứng dụng.

### 6.6.2. Vai trò và trách nhiệm

**Project Manager** chịu trách nhiệm quản lý tổng thể dự án, đảm bảo các quy trình quản lý cấu hình được tuân thủ đúng quy định đã thống nhất trong nhóm; phê duyệt mọi yêu cầu thay đổi trước khi được nhóm phát triển thực hiện; theo dõi tiến độ và trạng thái cấu hình của dự án thông qua các báo cáo định kỳ từ các thành viên; đồng thời đảm bảo rằng tất cả các baseline — bao gồm yêu cầu, thiết kế và mã nguồn — được lưu trữ và duy trì đúng cách trên các công cụ đã lựa chọn.

**Developer**, bao gồm cả Frontend Developer và Backend Developer, có trách nhiệm thực hiện các thay đổi mã nguồn theo đúng quy trình đã thống nhất; mỗi thay đổi cần tuân theo tiêu chuẩn đặt tên nhánh, tuân thủ quy ước commit và được ghi lại đầy đủ; đảm bảo rằng mọi yêu cầu thay đổi mã nguồn, dù là tính năng mới hay sửa lỗi, đều được ghi nhận rõ ràng trên công cụ quản lý dự án; phối hợp chặt chẽ với Tester để khắc phục các lỗi phát sinh và đảm bảo mã nguồn ổn định trước khi được đưa vào các baseline chính thức; chỉ tích hợp mã nguồn vào nhánh `develop` sau khi đã được kiểm thử và phê duyệt.

**Tester** thực hiện kiểm tra các thay đổi mã nguồn, đảm bảo rằng các chức năng hoạt động đúng như yêu cầu ban đầu đã đặc tả; xác nhận các yêu cầu thay đổi và báo cáo các lỗi phát sinh trong quá trình phát triển; đảm bảo phiên bản đang được kiểm thử phù hợp với baseline đã được chấp thuận trước khi triển khai; phối hợp chặt chẽ với Developer để tái kiểm thử sau khi lỗi được sửa, nhằm đảm bảo các thay đổi không gây ảnh hưởng đến các chức năng khác đã hoạt động ổn định trước đó.

**DevOps** chịu trách nhiệm triển khai và duy trì hạ tầng CI/CD nhằm đảm bảo quá trình tích hợp và phát hành phần mềm diễn ra một cách tự động hóa, nhất quán và an toàn; quản lý môi trường phát triển, kiểm thử và sản xuất, đảm bảo tính ổn định và sẵn sàng của hệ thống ở mọi giai đoạn; đảm bảo các thay đổi mã nguồn sau khi được phê duyệt sẽ được triển khai đúng quy trình và có khả năng khôi phục (rollback) nhanh chóng khi xảy ra sự cố; đồng thời giám sát hiệu năng hệ thống, ghi nhận log hoạt động và phối hợp xử lý sự cố liên quan đến cấu hình và môi trường triển khai.

### 6.6.3. Quy trình quản lý cấu hình

**Xác định cấu hình:** tiêu chuẩn đặt tên nhánh mã nguồn được quy ước thống nhất, gồm nhánh chính `main`, nhánh phát triển `develop`, và nhánh tính năng theo mẫu `feature/tên-tính-năng`. Về loại baseline, nhóm xác định ba baseline chính: Baseline Yêu cầu, bao gồm toàn bộ 33 use case và các yêu cầu phi chức năng đã được đặc tả trong SRS; Baseline Thiết kế, bao gồm sơ đồ thực thể quan hệ 17 bảng, thẻ CRC và tài liệu đặc tả API; và Baseline Mã nguồn, là phần mã nguồn đã ổn định, được kiểm thử đầy đủ và sẵn sàng cho việc phát hành. Toàn bộ mã nguồn được lưu trữ tập trung trên GitHub.

**Kiểm soát cấu hình:** mọi yêu cầu thay đổi phải được ghi nhận trên công cụ quản lý dự án (Trello), kèm theo mô tả chi tiết về nội dung thay đổi và lý do đề xuất. Các yêu cầu này bắt buộc phải được Project Manager phê duyệt trước khi được thực hiện chính thức. Về việc kiểm soát và theo dõi thay đổi, mọi thay đổi đối với mã nguồn đều được ghi lại đầy đủ thông qua lịch sử commit trên GitHub, sử dụng cơ chế Pull Request để yêu cầu kiểm tra và phê duyệt thay đổi trước khi được merge vào nhánh `develop` hoặc `main`.

**Đảm bảo tính toàn vẹn cấu hình:** nhóm thực hiện báo cáo định kỳ về trạng thái cấu hình thông qua GitHub và Trello, bao gồm thông tin về các thay đổi đã được thực hiện, các lỗi đã được khắc phục và tình trạng hiện tại của từng baseline. Project Manager cùng với Tester thực hiện các cuộc kiểm tra định kỳ nhằm đảm bảo rằng mọi thay đổi đều đã được ghi nhận, phê duyệt và kiểm thử đầy đủ trước khi được xem là hoàn tất.

### 6.6.4. Quản lý phiên bản tài liệu dự án

Bên cạnh việc quản lý cấu hình mã nguồn, nhóm cũng áp dụng một quy trình quản lý phiên bản riêng cho các tài liệu dự án — bao gồm SRS, PP, TP và Tài liệu đặc tả API — nhằm tránh tình trạng các thành viên chỉnh sửa chồng chéo hoặc sử dụng nhầm phiên bản cũ. Cụ thể, các tài liệu được lưu trữ tập trung trên thư mục dùng chung của nhóm, đồng thời được đánh số phiên bản rõ ràng (ví dụ theo quy ước `Tên_tài_liệu-v1.0`, `Tên_tài_liệu-v2.0`) mỗi khi có một đợt chỉnh sửa nội dung đáng kể được thực hiện.

Đặc biệt, mỗi lần một artifact đã được Project Manager phê duyệt trước đó — chẳng hạn như sơ đồ thực thể quan hệ hoặc sơ đồ hoạt động — cần được chỉnh sửa, nhóm luôn đi kèm một văn bản giải trình riêng nêu rõ lý do thay đổi, nội dung cụ thể được điều chỉnh và người thực hiện thay đổi đó, theo đúng tinh thần quy trình quản lý thay đổi đã trình bày tại mục 5.6. Bên cạnh đó, mỗi tài liệu chính đều duy trì một bảng ghi thay đổi (Change Log) ở cuối tài liệu, giúp việc tra cứu lại lịch sử các phiên bản trở nên thuận tiện hơn khi cần đối chiếu hoặc giải trình với giảng viên hướng dẫn.

## 6.7. Phương pháp phát triển

### 6.7.1. Phương pháp phát triển hệ thống

Dự án áp dụng phương pháp Scrum trong suốt quá trình phát triển. Scrum là một phương pháp phát triển phần mềm thuộc nhóm Agile, tập trung vào việc tổ chức công việc theo từng vòng lặp ngắn gọi là Sprint, với mỗi Sprint kéo dài khoảng `[__ tuần]`. Trong mỗi Sprint, đội phát triển tập trung hoàn thành một tập hợp các tính năng cụ thể đã được lựa chọn từ danh sách yêu cầu chung của hệ thống.

Quá trình triển khai hệ thống sử dụng mô hình tích hợp và triển khai liên tục (CI/CD) nhằm đảm bảo rằng mọi thay đổi trong mã nguồn đều được kiểm tra và triển khai một cách tự động, giảm thiểu rủi ro phát sinh lỗi khi phát hành. Nhóm sử dụng GitHub Actions trong quy trình phát triển để tự động hóa việc kiểm tra và triển khai mã nguồn mỗi khi có thay đổi được đẩy lên nhánh chính.

### 6.7.2. Quản lý vòng đời và chuyển giao

Vòng đời của dự án được quản lý và triển khai thông qua các Sprint diễn ra liên tiếp, cụ thể theo các giai đoạn sau:

- **Giai đoạn khởi tạo dự án:** nhóm lập danh sách toàn bộ yêu cầu dưới dạng các hạng mục trong Product Backlog, được xây dựng dựa trên 33 use case đã được đặc tả chi tiết trong SRS.
- **Giai đoạn phát triển:** trong mỗi Sprint, nhóm lựa chọn một tập con các yêu cầu từ Product Backlog để đưa vào Sprint Backlog, và đội phát triển tập trung thực hiện các yêu cầu này trong suốt thời gian Sprint diễn ra. Kết quả sau mỗi Sprint là một phiên bản phần mềm có thể hoạt động và mang lại giá trị sử dụng thực tế.
- **Giai đoạn kiểm thử và đánh giá:** sau khi mỗi Sprint kết thúc, hệ thống được kiểm thử một cách kỹ lưỡng nhằm đảm bảo các tính năng mới đáp ứng đúng yêu cầu đã đặt ra. Đồng thời, nhóm tổ chức đánh giá lại toàn bộ Sprint vừa qua nhằm rút kinh nghiệm và cải tiến quy trình làm việc cho các Sprint tiếp theo.
- **Giai đoạn chuyển giao:** khi toàn bộ các Sprint đã hoàn tất và hệ thống đạt được các yêu cầu đã đề ra, sản phẩm được chuyển giao kèm theo đầy đủ các tài liệu hướng dẫn sử dụng và tài liệu hướng dẫn bảo trì hệ thống.

### 6.7.3. Vai trò và trách nhiệm

**Project Manager** có trách nhiệm xác định và sắp xếp thứ tự ưu tiên cho các yêu cầu trong Product Backlog, đảm bảo hệ thống được phát triển đúng theo mục đích ban đầu đã đề ra, đồng thời hỗ trợ đội phát triển tuân thủ đầy đủ các nguyên tắc của phương pháp Scrum trong suốt quá trình làm việc.

**Developer**, bao gồm cả Backend Developer và Frontend Developer, đảm nhận việc thực hiện phần công việc chính của dự án, chịu trách nhiệm phát triển các chức năng theo đúng yêu cầu đã được xác định trong từng Sprint Backlog.

**Tester** đảm nhiệm vai trò kiểm thử các tính năng mới được phát triển trong từng Sprint, đảm bảo các tính năng này hoạt động chính xác và không gây ra ảnh hưởng tiêu cực đến các chức năng khác đã ổn định trước đó của hệ thống.

**DevOps** chịu trách nhiệm thiết lập và duy trì hạ tầng tích hợp và triển khai liên tục (CI/CD), đảm bảo việc phát hành các phiên bản phần mềm trong từng Sprint diễn ra một cách trơn tru và ổn định. Ngoài ra, DevOps còn quản lý môi trường kiểm thử và sản xuất, hỗ trợ nhóm phát triển và kiểm thử trong việc triển khai và giám sát hệ thống, đồng thời đảm bảo rằng các thay đổi được triển khai kịp thời, an toàn và có khả năng khôi phục nhanh chóng khi gặp sự cố.

### 6.7.4. Đánh giá và cải tiến sau mỗi sprint

Nhằm liên tục cải thiện chất lượng quy trình làm việc, cuối mỗi Sprint nhóm tổ chức một buổi Sprint Retrospective ngắn, kéo dài khoảng `[__ phút]`, để cùng nhau trao đổi và nhìn lại ba câu hỏi chính: điều gì đã được thực hiện tốt trong Sprint vừa qua; điều gì chưa đạt được như kỳ vọng hoặc gặp khó khăn trong quá trình thực hiện; và những điểm cần được cải thiện ở Sprint tiếp theo.

Kết quả của mỗi buổi retrospective được Project Manager ghi nhận ngắn gọn trên công cụ quản lý dự án (dưới dạng ghi chú hoặc thẻ công việc riêng), làm căn cứ để điều chỉnh cách phân công công việc hoặc điều chỉnh quy trình làm việc chung cho Sprint kế tiếp. Đối với những vấn đề kỹ thuật hoặc quy trình lặp lại nhiều lần qua các Sprint liên tiếp — chẳng hạn tình trạng liên tục bị chậm tiến độ ở một khâu công việc cụ thể — nhóm sẽ đưa vấn đề này vào danh sách rủi ro đã trình bày tại mục 6.5, nhằm có giải pháp xử lý tận gốc nguyên nhân thay vì chỉ ghi nhận đơn lẻ ở từng lần retrospective.

---

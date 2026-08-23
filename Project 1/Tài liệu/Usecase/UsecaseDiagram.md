```mermaid
flowchart LR

    %% =========================
    %% ACTORS
    %% =========================
    QL{{"Quản lý"}}
    GV{{"Giáo viên"}}
    HV{{"Học viên"}}

    %% =========================
    %% SYSTEM
    %% =========================
    subgraph SYS["HỆ THỐNG QUẢN LÝ BÀI TẬP VÀ CHẤM CHỮA BÀI TIẾNG ANH"]

        %% Tài khoản
        UC02(["Đăng nhập"])
        UC03(["Đăng xuất"])
        UC04(["Quản lý tài khoản cá nhân"])

        %% Quản trị người dùng
        UC05(["Quản lý tài khoản người dùng"])
        UC06(["Phân quyền người dùng"])
        UC07(["Quản lý giáo viên"])
        UC08(["Quản lý học viên"])

        %% Lớp học
        UC09(["Quản lý lớp học"])
        UC10(["Quản lý thành viên lớp học"])
        UC11(["Xem danh sách lớp học"])

        %% Bài tập
        UC12(["Quản lý bài tập"])
        UC13(["Tạo bài tập"])
        UC14(["Mở bài tập"])
        UC15(["Khóa bài tập"])
        UC16(["Xem bài tập"])

        %% Làm bài
        UC17(["Làm bài tập viết"])
        UC18(["Làm bài tập nói"])
        UC19(["Làm bài tập đọc"])
        UC20(["Làm bài tập nghe"])
        UC21(["Nộp bài tập"])
        UC22(["Xem trạng thái bài làm"])

        %% Chấm bài
        UC23(["Chấm bài"])
        UC24(["Hỗ trợ chấm chữa bài bằng AI"])
        UC25(["Hỗ trợ chấm chữa bài viết bằng AI"])
        UC26(["Hỗ trợ chấm chữa bài nói bằng AI"])
        UC27(["Chấm bài đọc"])
        UC28(["Chấm bài nghe"])

        %% Kết quả
        UC29(["Xem điểm số"])
        UC30(["Xem kết quả học tập"])
        UC31(["Xem bài chữa"])
        UC32(["Xem và đánh giá kết quả học viên"])
        UC33(["Quản lý điểm số"])
        UC34(["Theo dõi tiến độ học tập"])

    end

    %% =========================
    %% QUẢN LÝ
    %% =========================

    QL --> UC02
    QL --> UC03
    QL --> UC04

    QL --> UC05
    QL --> UC06
    QL --> UC07
    QL --> UC08
    QL --> UC09
    QL --> UC10

    %% =========================
    %% GIÁO VIÊN
    %% =========================

    GV --> UC02
    GV --> UC03
    GV --> UC04

    GV --> UC10
    GV --> UC11

    GV --> UC12
    GV --> UC13
    GV --> UC14
    GV --> UC15

    GV --> UC23

    GV --> UC29
    GV --> UC32
    GV --> UC33
    GV --> UC34

    %% =========================
    %% HỌC VIÊN
    %% =========================
    
    HV --> UC02
    HV --> UC03
    HV --> UC04

    HV --> UC11

    HV --> UC16
    HV --> UC17
    HV --> UC18
    HV --> UC19
    HV --> UC20
    HV --> UC21
    HV --> UC22

    HV --> UC29
    HV --> UC30
    HV --> UC31

    %% =========================
    %% EXTEND - CHẤM BÀI
    %% =========================

    UC23 -.->|"<<extend>>"| UC24
    UC23 -.->|"<<extend>>"| UC25
    UC23 -.->|"<<extend>>"| UC26
    UC23 -.->|"<<extend>>"| UC27
    UC23 -.->|"<<extend>>"| UC28
# Báo cáo khởi tạo môi trường Dev (Local)

## 1. Mục đích

Tài liệu mô tả cách setup môi trường **dev chạy local trên máy** cho dự án gồm:

| Thành phần | Công nghệ | Cách chạy khi dev |
|---|---|---|
| Frontend (FE) | ReactJS + Vite + TypeScript | `npm run dev` (Vite dev server) |
| Backend (BE) | Java 21, Spring 4.1.1, Gradle (Groovy DSL) | `./gradlew bootRun` **hoặc** build Docker image rồi chạy local |
| Database (DB) | PostgreSQL | Docker container local |

Tài liệu chia làm 2 luồng:

- **A. Khởi tạo dự án mới** — áp dụng khi tạo repo từ đầu, cần tạo các file cấu hình phục vụ dev local (Dockerfile để build image local, docker-compose cho DB, file properties...).
- **B. Clone dự án đã khởi tạo** — áp dụng khi thành viên mới tham gia dự án, các file trên đã có sẵn trong repo, chỉ cần cài công cụ + biến môi trường rồi chạy.

---

## 2. Yêu cầu công cụ (Prerequisites)

Áp dụng cho **cả hai luồng A và B**:

| Công cụ | Phiên bản khuyến nghị | Dùng cho |
|---|---|---|
| Node.js | 20.x LTS trở lên | FE (Vite) |
| npm / pnpm | npm ≥ 10 hoặc pnpm ≥ 9 | FE |
| JDK | 21 (Temurin/Adoptium) | BE khi chạy `bootRun` |
| Gradle | dùng qua `./gradlew` (Gradle Wrapper), không cần cài riêng | BE |
| Docker & Docker Compose | Docker ≥ 24, Compose v2 | DB local, build & chạy image BE local |
| Git | bản mới nhất | Toàn bộ |

> Không cần cài PostgreSQL trực tiếp trên máy — DB dev chạy qua Docker container.
> Không cần cài Docker nếu chỉ chạy BE bằng `bootRun` — Docker chỉ bắt buộc nếu muốn build/chạy BE dưới dạng container hoặc chạy DB.

---

## 3. Cấu trúc thư mục đề xuất

```
project-root/
├── frontend/                     # ReactJS + Vite + TS
│   ├── src/
│   ├── index.html
│   ├── vite.config.ts
│   ├── package.json
│   └── env.frontend.example      # mẫu biến môi trường FE → chuyển thành .env
├── backend/                       # Spring Boot (Java 21, Gradle Groovy)
│   ├── src/
│   │   └── main/
│   │       └── resources/
│   │           ├── application.properties        # cấu hình chung
│   │           └── application-dev.properties     # trỏ tới Postgres Docker local
│   ├── build.gradle
│   ├── settings.gradle
│   ├── gradlew / gradlew.bat
│   ├── Dockerfile                 # build image BE để chạy local qua Docker
│   └── .dockerignore
├── database
│   ├── docker-compose.dev.yml         # chạy Postgres cho môi trường dev
│   └── env.docker_compose.example     # mẫu biến môi trường docker-compose → chuyển thành .env
└── docs/
    └── dev-environment-setup.md   # chính là file báo cáo này
```

> **Quy ước đặt tên file môi trường:**
> - `env.frontend.example` — mẫu cho FE, nằm trong `frontend/`, copy thành `frontend/.env`.
> - `env.docker_compose.example` — mẫu cho docker-compose, nằm ở root, copy thành `.env` ở root.
> - Chỉ commit file `*.example`. File `.env` (chứa giá trị thật) **không được commit** — đã có trong `.gitignore`.

---

## 4. LUỒNG A — Khởi tạo dự án mới từ đầu

### 4.1. Khởi tạo Frontend (React + Vite + TypeScript)

```bash
npm create vite@latest frontend -- --template react-ts
cd frontend
npm install
```

Tạo file `frontend/env.frontend.example` khai báo biến `VITE_API_BASE_URL` trỏ tới BE local. *(Phụ lục A)*

Chạy dev:

```bash
cp env.frontend.example .env
npm run dev
```

### 4.2. Khởi tạo Backend (Java 21 + Spring 4.1.1 + Gradle Groovy)

Khởi tạo project qua [start.spring.io](https://start.spring.io) hoặc IDE, chọn:
- Project: Gradle - Groovy
- Language: Java 21
- Spring: 4.1.1
- Dependencies tối thiểu: Spring Web, Spring Data JPA, PostgreSQL Driver, Validation, Actuator, Spring Boot DevTools (dev only)

Tạo `backend/src/main/resources/application-dev.properties` — datasource trỏ vào container Postgres local. *(Phụ lục B)*

Tạo `backend/Dockerfile` và `backend/.dockerignore` để phục vụ việc build/chạy BE dưới dạng container local. *(Phụ lục C, D)*

### 4.3. Khởi tạo Database cho môi trường dev (Docker)

Tạo `docker-compose.dev.yml` ở root dự án để chạy PostgreSQL local, dùng chung cho mọi dev trong team. *(Phụ lục E)*

Tạo `env.docker_compose.example` ở root khai báo `DB_USER`, `DB_PASSWORD`, `DB_NAME`, `DB_PORT`. *(Phụ lục F)*

### 4.4. Commit các file cấu hình vào repo

Sau khi hoàn tất, các file sau **cần được commit** để lần sau các thành viên khác chỉ cần clone (xem Luồng B):

```
frontend/env.frontend.example
backend/Dockerfile
backend/.dockerignore
backend/src/main/resources/application.properties
backend/src/main/resources/application-dev.properties
docker-compose.dev.yml
env.docker_compose.example
docs/dev-environment-setup.md
```

> Chỉ commit file `*.example` (mẫu), **không commit** file `.env` chứa giá trị thật. Thêm `.env` vào `.gitignore`.

---

## 5. LUỒNG B — Clone dự án đã khởi tạo (không tạo lại config)

Áp dụng khi dự án đã có sẵn các file ở mục 4.4. **Bỏ qua** các bước tạo Dockerfile, docker-compose, properties... vì đã có sẵn trong repo.

```bash
git clone <repo-url>
cd project-root
```

### 5.1. Setup Database dev (Docker) — nên làm trước khi chạy BE

```bash
# Tạo file .env ở root từ file mẫu, rồi điền DB_USER/DB_PASSWORD/DB_NAME nếu muốn đổi giá trị mặc định
cp env.docker_compose.example .env

docker compose -f docker-compose.dev.yml up -d
docker ps                  # kiểm tra container postgres đang chạy healthy
```

### 5.2. Setup Frontend

```bash
cd frontend

# Tạo file .env trong thư mục frontend/ từ file mẫu, rồi điền VITE_API_BASE_URL trỏ về BE local
cp env.frontend.example .env

npm install
npm run dev
```

### 5.3. Setup Backend — 2 CÁCH CHẠY

Tuỳ máy/thói quen của từng dev, có thể chọn 1 trong 2 cách sau. **Cả hai đều cần DB Docker ở mục 5.1 đang chạy.**

#### Cách 1 — Chạy trực tiếp bằng `bootRun` (nhanh, phù hợp khi code/debug liên tục)

```bash
cd backend
./gradlew bootRun --args='--spring.profiles.active=dev'
```

- BE chạy trực tiếp bằng JDK trên máy, không qua Docker.
- Hot reload tốt hơn nhờ Spring DevTools.
- BE kết nối tới Postgres qua `localhost:5432` (container map port ra ngoài).

#### Cách 2 — Build Docker image và chạy container local (dùng để test giống môi trường container)

```bash
cd backend

# Build image, đè lên image cũ nếu đã tồn tại (dùng lại tag cũ)
docker build -t project-backend:dev .

# Dừng & xoá container cũ nếu đang chạy (bỏ qua nếu chưa có)
docker rm -f project-backend-dev 2>/dev/null || true

# Chạy container mới từ image vừa build
docker run -d \
  --name project-backend-dev \
  --network host \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e DB_USER=devuser \
  -e DB_PASSWORD=devpassword \
  -e DB_NAME=appdb \
  -e DB_PORT=5432 \
  project-backend:dev

# Xem log
docker logs -f project-backend-dev
```

Ghi chú:

- `docker build -t project-backend:dev .` khi chạy lại **sẽ tự đè lên image cùng tag** (`project-backend:dev`) — image cũ trở thành "dangling" (`<none>`), không cần push lên registry. Có thể dọn dẹp image cũ bằng `docker image prune -f` nếu muốn.
- Dùng `--network host` (Linux) để container BE gọi được `localhost:5432` nơi Postgres đang expose port. Trên macOS/Windows (Docker Desktop), `--network host` không hoạt động như Linux — thay bằng biến `host.docker.internal:5432` trong cấu hình datasource, hoặc chạy BE + DB trên cùng một Docker network (`docker network create dev-net`, thêm `--network dev-net` cho cả 2, rồi trỏ datasource tới tên service `postgres` thay vì `localhost`).
- Có thể gộp lại thành 1 script `run-backend-docker.sh` để khỏi gõ lại lệnh mỗi lần.

### 5.4. Checklist kiểm tra nhanh

- [ ] `docker ps` thấy container Postgres đang chạy, healthy.
- [ ] BE chạy ở `http://localhost:8080` (dù bằng `bootRun` hay Docker), endpoint `/actuator/health` trả về `UP`.
- [ ] FE chạy ở `http://localhost:5173`, gọi được API BE (không lỗi CORS/404).

---

## 6. Biến môi trường tổng hợp (local dev)

| File mẫu | File thật cần tạo | Vị trí | Dùng khi |
|---|---|---|---|
| `env.docker_compose.example` | `.env` | root project | `docker compose` chạy Postgres dev |
| `env.frontend.example` | `.env` | `frontend/` | `npm run dev` |
| Biến `-e` khi `docker run` hoặc `--args` khi `bootRun` | — | — | chạy BE local (2 cách ở mục 5.3) |

> File `.env` thật cần nằm **đúng cùng thư mục** với file `*.example` tương ứng thì mới được đọc đúng — docker-compose đọc `.env` ở root, Vite đọc `.env` trong `frontend/`.

---

## 7. Phụ lục — nội dung các file cấu hình

Xem các file đính kèm theo báo cáo này:

- **Phụ lục A:** `frontend/env.frontend.example`
- **Phụ lục B:** `backend/src/main/resources/application-dev.properties`
- **Phụ lục C:** `backend/Dockerfile`
- **Phụ lục D:** `backend/.dockerignore`
- **Phụ lục E:** `docker-compose.dev.yml`
- **Phụ lục F:** `env.docker_compose.example` (root)

---

## 8. Tóm tắt luồng làm việc

```
Khởi tạo mới (1 lần)  →  Commit config files  →  Team member clone
      (Luồng A)                                       (Luồng B)
```

- Người khởi tạo dự án thực hiện **Luồng A** một lần duy nhất, tạo và commit toàn bộ file cấu hình dev.
- Từ lần thứ hai trở đi, mọi thành viên chỉ cần thực hiện **Luồng B**: clone → bật Docker DB → chạy FE → chạy BE bằng `bootRun` hoặc Docker image local, không cần tạo lại bất kỳ file cấu hình nào.

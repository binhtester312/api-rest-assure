# REST Assured API Automation Testing 🚀

Dự án học tập và thực hành kiểm thử tự động API (API Automation Testing) với **REST Assured**, **Java** và **TestNG**.

---

## 🛠️ Công Nghệ Sử Dụng (Tech Stack)

* **Ngôn ngữ:** Java (JDK 17)
* **API Testing:** REST Assured 5.5.0
* **Test Framework:** TestNG 7.10.2
* **Build Tool:** Apache Maven
* **Mock Server:** `json-server` (Node.js)
* **Xử lý dữ liệu:** `org.json`, `Jackson Databind`, `Gson`, `Lombok`

---

## 📂 Cấu Trúc Dự Án (Project Structure)

```text
api-rest-assure/
├── src/test/java/
│   ├── day1/
│   │   └── HTTPRequests.java                 # CRUD cơ bản: GET, POST, PUT, DELETE (ReqRes API)
│   └── day2/
│       ├── DiffWaysToCreatePostRequestBody.java  # 4 cách tạo POST Payload
│       └── Pojo_PostRequest.java             # POJO model cho sinh viên
├── body.json                                 # File JSON payload mẫu cho Day 2
├── db.json                                   # Cơ sở dữ liệu giả lập cho json-server
├── pom.xml                                   # Quản lý dependencies Maven
└── README.md
```

---

## 📚 Nội Dung Bài Học

### 🔹 Day 1: HTTP Requests Cơ Bản
* Thực hiện gửi các HTTP method chuẩn: **GET**, **POST**, **PUT**, **DELETE**.
* Validate status code, trích xuất dữ liệu (`jsonPath`) và sử dụng biến động giữa các test.

### 🔹 Day 2: Các Cách Tạo POST Request Payload
1. **Dùng HashMap:** Nhanh gọn cho dữ liệu đơn giản.
2. **Dùng thư viện Org.JSON (`JSONObject`):** Tạo JSON linh hoạt, gọi `.toString()`.
3. **Dùng POJO Class:** Chuẩn dự án thực tế (Best Practice), an toàn kiểu dữ liệu.
4. **Đọc từ file JSON bên ngoài (`body.json`):** Dùng `JSONTokener` để đọc file tĩnh lớn.

---

## 🚀 Hướng Dẫn Chạy Test

### 1. Khởi động Mock API Server (Dùng cho Day 2)
```bash
npx json-server@0.17.4 --watch db.json --port 3000
```
> Server sẽ chạy tại: `http://localhost:3000/students`

### 2. Chạy Kiểm Thử
Chạy toàn bộ test suites bằng Maven:
```bash
mvn test
```
Hoặc chạy trực tiếp từng file/method qua Test Runner (▶️) trên VS Code / IntelliJ IDEA.
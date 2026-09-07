# REST Assured API Automation Testing 🚀

Dự án học tập và thực hành **API Automation Testing** với REST Assured, Java và TestNG.  
Được tổ chức thành 2 tầng: **Learning** (code học tập day1–day8) và **Framework** (production-ready).

---

## 🛠️ Tech Stack

| Công nghệ | Version | Vai trò |
|---|---|---|
| Java | JDK 17 | Ngôn ngữ chính |
| REST Assured | 5.5.0 | HTTP API Testing |
| TestNG | 7.10.2 | Test Runner & Assertions |
| Apache Maven | 3.x | Build Tool |
| Jackson Databind | 2.17.2 | JSON Serialization / Deserialization |
| Lombok | 1.18.34 | Auto-generate Getter/Setter/Builder |
| Datafaker | 2.4.0 | Sinh test data ngẫu nhiên |
| Gson | 2.11.0 | Google JSON Parser |
| json-server (Node.js) | 0.17.4 | Mock API Server (dùng cho learning) |

---

## 📂 Cấu Trúc Dự Án

```
api-rest-assure/
├── src/test/java/
│   │
│   ├── learning/                        # 📚 Code học tập Day 1–8
│   │   ├── day1/
│   │   │   └── HTTPRequests.java        # CRUD cơ bản: GET, POST, PUT, DELETE
│   │   ├── day2/
│   │   │   ├── DiffWaysToCreatePostRequestBody.java  # 4 cách tạo POST Payload
│   │   │   └── Pojo_PostRequest.java    # POJO + Serialization cho POST
│   │   ├── day3/
│   │   │   ├── CookiesDemo.java         # Xử lý Cookies
│   │   │   ├── HeadersDemo.java         # Xử lý Headers
│   │   │   ├── LoggingDemo.java         # Request/Response Logging
│   │   │   └── PathAndQueryParameters.java  # Path & Query Params
│   │   ├── day4/
│   │   │   └── ParsingJSONResponseData.java  # Parse JSON response
│   │   ├── day5/
│   │   │   └── ParsingXMLResponse.java  # Parse XML response (SOAP)
│   │   ├── day6/
│   │   │   └── FileUploadAndDownload.java   # Upload & Download file
│   │   ├── day7/
│   │   │   ├── Authentications.java     # 7 kiểu Auth: Basic, Digest, Bearer, OAuth1/2, API Key
│   │   │   ├── FakerDataGenerator.java  # Sinh dữ liệu giả với DataFaker
│   │   │   ├── JSONSchemaValidation.java  # Validate JSON Schema
│   │   │   └── XMLSchemaValidation.java   # Validate XML Schema (XSD)
│   │   └── day8/
│   │       ├── CreateUser.java          # API Chaining: POST tạo user
│   │       ├── GetUser.java             # API Chaining: GET user
│   │       ├── UpdateUser.java          # API Chaining: PUT update user
│   │       ├── DeleteUser.java          # API Chaining: DELETE user
│   │       ├── Student.java             # POJO Student model
│   │       └── SerilizationDeserilization.java  # Jackson ObjectMapper demo
│   │
│   ├── api/                             # 🏬 Petstore Framework (End-to-End Enterprise)
│   │   ├── endpoints/
│   │   │   ├── Routes.java              # 📍 Cách 1: URL constants
│   │   │   ├── UserEndPoints.java       # 🚀 HTTP CRUD actions (Routes)
│   │   │   └── UserEndPoints2.java      # 🚀 HTTP CRUD actions (routes.properties via ResourceBundle)
│   │   ├── payload/
│   │   │   └── User.java                # 📦 POJO model cho Petstore User
│   │   ├── utilities/
│   │   │   ├── XLUtility.java           # 📊 Đọc/ghi Excel (Apache POI)
│   │   │   ├── DataProviders.java       # 🎲 TestNG DataProviders (Data-Driven)
│   │   │   └── ExtentReportManager.java # 📈 ITestListener sinh báo cáo HTML ExtentReports
│   │   └── test/
│   │       ├── UserTests.java           # 🧪 CRUD Test đơn lẻ (Faker)
│   │       ├── UserTests2.java          # 🧪 CRUD Test dùng properties
│   │       └── DDTests.java             # 🧪 Data-Driven Testing đọc từ Excel
│   │
│   ├── base/
│   │   └── BaseTest.java               # 🏗️ @BeforeSuite: setup RestAssured, Logging
│   ├── config/
│   │   └── ConfigManager.java          # 🔧 Đọc config.properties
│   ├── models/
│   │   ├── request/
│   │   │   └── UserRequest.java        # 📦 POJO request body (Lombok @Builder)
│   │   └── response/
│   │       └── UserResponse.java       # 📦 POJO response body (Jackson)
│   ├── services/
│   │   └── UserService.java            # 🔗 Service Layer — tập trung HTTP calls
│   ├── utils/
│   │   ├── FakerUtils.java             # 🎲 Sinh test data random + traceable
│   │   └── TokenManager.java           # 🔑 Đọc Bearer Token từ env var
│   └── tests/
│       └── UserCRUDTest.java           # ✅ 5 test cases CRUD + API Chaining
│
├── testData/
│   └── Userdata.xlsx                   # 📗 File Excel chứa dữ liệu Data-Driven Test
├── reports/                            # 📊 Báo cáo kiểm thử ExtentReports HTML
├── logs/
│   └── petstore.log                    # 📝 Nhật ký ghi bởi Log4j2
├── src/test/resources/
│   ├── routes.properties               # 🌐 Cấu hình API URLs cho Petstore
│   ├── log4j2.xml                      # ⚙️ Cấu hình Log4j2 logging
│   ├── config.properties               # URL, timeout (không chứa secrets)
│   ├── testng.xml                      # Suite: learning.day1 (learning)
│   ├── testng_chaining.xml             # Suite: learning.day8 API Chaining
│   ├── testng_framework.xml            # Suite: GoRest Framework (UserCRUDTest)
│   └── testng_petstore.xml             # Suite: Petstore Framework (10 TCs PASS)
│
├── db.json                             # Mock data cho json-server
├── body.json                           # JSON payload mẫu
├── traveler.xml                        # XML mẫu cho Day 5
├── pom.xml
└── README.md
```

---

## 🚀 Hướng Dẫn Chạy Test

### 1. Chạy Petstore Swagger Framework (Chuẩn bài giảng thầy — 10 TCs PASS)

Không cần cài đặt token hay mock server, chạy trực tiếp public API Petstore Swagger:

```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_petstore.xml
```

* 📝 Nhật ký ghi tại: `logs/petstore.log`
* 📊 Báo cáo đồ họa ExtentReports mở tại: `reports/Test-Report-[timestamp].html`

### 2. Chạy GoRest Framework (Production-style — 5 TC CRUD)

```bash
# Bước 1: Set Bearer Token (lấy từ https://gorest.co.in)
export GOREST_TOKEN="your_token_here"

# Bước 2: Chạy
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_framework.xml
```

> Hoặc truyền thẳng qua Maven:
> ```bash
> mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_framework.xml \
>          -Dgorest.token=your_token_here
> ```

### 2. Chạy API Chaining (Day 8 — learning)

```bash
export GOREST_TOKEN="your_token_here"
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng_chaining.xml \
         -Dgorest.token=your_token_here
```

### 3. Chạy Bài Học Day 1 (Mock Server)

```bash
# Khởi động mock server
npx json-server@0.17.4 --watch db.json --port 3000

# Chạy test
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml
```

### 4. Chạy 1 test method đơn lẻ

```bash
mvn test -Dtest=ClassName#methodName
# Ví dụ:
mvn test -Dtest=UserCRUDTest#tc01_createUser
```

---

## 🏛️ Kiến Trúc Framework

```
Test Class (UserCRUDTest)
    └── extends BaseTest         ← @BeforeSuite: setup RestAssured
         └── uses UserService    ← Service Layer (tương đương Page Object)
              └── uses Models    ← UserRequest / UserResponse (POJO + Lombok)
         └── uses FakerUtils     ← Sinh data random traceable
         └── uses TokenManager   ← Đọc token từ env var (bảo mật)
```

| Tầng | Day8 (cũ) | Framework (mới) |
|---|---|---|
| HTTP calls | `given().when()` trực tiếp trong test | Gọi qua `UserService` |
| Test data | `Faker` inline | `FakerUtils.generateInactiveUser()` |
| API Chaining | `ITestContext.setAttribute/getAttribute` | Instance field |
| Token | Hardcode ⚠️ | Env var `GOREST_TOKEN` ✅ |
| Config | Hardcode URL | `config.properties` |
| POJO | `JSONObject` thủ công | Lombok `@Builder @Data` |

---

## 🔑 Lấy Bearer Token (GoRest API)

1. Truy cập [gorest.co.in](https://gorest.co.in)
2. Đăng nhập bằng Google / GitHub
3. Copy token từ trang cá nhân
4. Set vào env var: `export GOREST_TOKEN="your_token"`

> ⚠️ Token mang tính cá nhân — **KHÔNG commit token vào source code**.
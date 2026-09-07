package learning.day7;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/*
 ===============================================================================
                     LÝ THUYẾT: AUTHENTICATION vs AUTHORIZATION
 ===============================================================================
 1. Authentication (Xác thực - Valid or not?):
    - Kiểm tra: "BẠN LÀ AI? THÔNG TIN ĐĂNG NHẬP CÓ HỢP LỆ KHÔNG?"
    - Ví dụ: Nhập Username/Password, quét vân tay, FaceID.
 
 2. Authorization (Phân quyền - Access?):
    - Kiểm tra: "BẠN CÓ QUYỀN TRUY CẬP TÀI NGUYÊN NÀY KHÔNG?"
    - Ví dụ: User bình thường chỉ được xem, Admin mới có quyền xóa bài.

 -------------------------------------------------------------------------------
 CÁC KIỂU XÁC THỰC PHỔ BIẾN TRONG API TESTING:
   [Nhóm 1: User / Password]
   - Basic      : Mã hóa Base64 đơn giản (username:password).
   - Digest     : Bảo mật hơn Basic (dùng thuật toán băm Hash/Nonce chống giả mạo).
   - Preemptive : Chủ động gửi thông tin xác thực ngay từ request đầu tiên
                  (thay vì chờ server từ chối 401 rồi mới gửi lại như Basic thông thường).

   [Nhóm 2: Token & Khóa API]
   - Bearer Token : Chuỗi token bản quyền (JWT / PAT) truyền qua Header Authorization.
   - OAuth 1.0, 2.0: Chuẩn ủy quyền bảo mật cao (Google, Facebook, GitHub API...).
   - API Key      : Khóa nhận diện do dịch vụ cấp (gửi qua Query Param hoặc Header).
 ===============================================================================
*/

public class Authentications {

	// 1. BASIC AUTHENTICATION

	@Test(priority = 1)
	void testBasicAuthentication() {
		given()
				.auth().basic("postman", "password")
				.when()
				.get("https://postman-echo.com/basic-auth")
				.then()
				.statusCode(200)
				.body("authenticated", equalTo(true))
				.log().all();
	}

	// -------------------------------------------------------------------------
	// 2. DIGEST AUTHENTICATION (Bảo mật cao hơn Basic qua hàm băm)
	// -------------------------------------------------------------------------
	@Test(priority = 2)
	void testDigestAuthentication() {
		given()
				.auth().digest("postman", "password")
				.when()
				.get("https://postman-echo.com/digest-auth")
				.then()
				.statusCode(200)
				.body("authenticated", equalTo(true))
				.log().all();
	}

	// -------------------------------------------------------------------------
	// 3. PREEMPTIVE AUTHENTICATION (Gửi sẵn xác thực ngay request đầu tiên)
	// -------------------------------------------------------------------------
	@Test(priority = 3)
	void testPreemptiveAuthentication() {
		given()
				.auth().preemptive().basic("postman", "password")
				.when()
				.get("https://postman-echo.com/basic-auth")
				.then()
				.statusCode(200)
				.body("authenticated", equalTo(true))
				.log().all();
	}

	// -------------------------------------------------------------------------
	// 4. BEARER TOKEN AUTHENTICATION (Thường dùng cho Microservices & GitHub API)
	// -------------------------------------------------------------------------
	@Test(priority = 4)
	void testBearerTokenAuthentication() {
		// Set GitHub PAT qua env var: export GITHUB_TOKEN="ghp_your_token_here"
		// Không hardcode token vào source code!
		String bearerToken = System.getenv("GITHUB_TOKEN") != null
				? System.getenv("GITHUB_TOKEN")
				: System.getProperty("github.token", "YOUR_GITHUB_PAT_HERE");

		given()
				.headers("Authorization", "Bearer " + bearerToken)
				.when()
				.get("https://api.github.com/user/repos")
				.then()
				.statusCode(200)
				.log().all();
	}

	// -------------------------------------------------------------------------
	// 5. OAUTH 1.0 AUTHENTICATION (Dùng 4 khóa bảo mật)
	// -------------------------------------------------------------------------
	@Test(priority = 5)
	void testOAuth1Authentication() {
		given()
				.auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret")
				.when()
				.get("https://api.example.com/endpoint")
				.then()
				.statusCode(200)
				.log().all();
	}

	// -------------------------------------------------------------------------
	// 6. OAUTH 2.0 AUTHENTICATION (Chuẩn hiện đại, phổ biến nhất)
	// -------------------------------------------------------------------------
	@Test(priority = 6)
	void testOAuth2Authentication() {
		// Set GitHub PAT qua env var: export GITHUB_TOKEN="ghp_your_token_here"
		String oauthToken = System.getenv("GITHUB_TOKEN") != null
				? System.getenv("GITHUB_TOKEN")
				: System.getProperty("github.token", "YOUR_GITHUB_PAT_HERE");

		given()
				.auth().oauth2(oauthToken)
				.when()
				.get("https://api.github.com/user/repos")
				.then()
				.statusCode(200)
				.log().all();
	}

	// -------------------------------------------------------------------------
	// 7. API KEY AUTHENTICATION (Truyền API Key vào Query Param hoặc Header)
	// -------------------------------------------------------------------------
	@Test(priority = 7)
	void testAPIKeyAuthentication() {
		given()
				.queryParam("appid", "fe9c5cddb7e01d747b4611c3fc9eaf2c") // API Key mẫu
				.queryParam("q", "Delhi")
				.queryParam("units", "metric")
				.queryParam("cnt", "7")
				.pathParam("mypath", "data/2.5/forecast/daily")
				.when()
				.get("https://api.openweathermap.org/{mypath}")
				.then()
				.statusCode(200)
				.log().all();
	}

}

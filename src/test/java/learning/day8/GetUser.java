package learning.day8;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetUser {

	@Test
	void test_getUser(ITestContext context) {
		// 1. Lấy ID người dùng được lưu từ CreateUser
		int id = (int) context.getAttribute("user_id");

		String bearerToken = "c35e10e748c6f113775527bcef204e9929b4c9f4b995a8ee253eec46aed57b06";

		// 2. Gửi request GET tra cứu thông tin theo ID đó
		given()
				.headers("Authorization", "Bearer " + bearerToken)
				.pathParam("id", id)
		.when()
				.get("https://gorest.co.in/public/v2/users/{id}")
		.then()
				.statusCode(200)
				.log().all();
	}

}

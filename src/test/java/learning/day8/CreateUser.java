package learning.day8;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;
import net.datafaker.Faker;

public class CreateUser {

	@Test
	void test_createUser(ITestContext context) {
		// 1. Dùng Faker sinh dữ liệu ngẫu nhiên để không bị trùng lặp
		Faker faker = new Faker();

		JSONObject data = new JSONObject();
		data.put("name", faker.name().fullName());
		data.put("gender", "Male");
		data.put("email", faker.internet().emailAddress());
		data.put("status", "inactive");

		// 2. Token xác thực của GoRest API
		String bearerToken = "c35e10e748c6f113775527bcef204e9929b4c9f4b995a8ee253eec46aed57b06";

		// 3. Gửi request POST tạo người dùng và trích xuất ID trả về
		int id = given()
				.headers("Authorization", "Bearer " + bearerToken)
				.contentType("application/json")
				.body(data.toString())
		.when()
				.post("https://gorest.co.in/public/v2/users")
				.jsonPath().getInt("id");

		System.out.println("Generated id is: " + id);

		// 4. API CHAINING: Lưu ID vào context để các class khác (GetUser, UpdateUser, DeleteUser) dùng lại
		context.setAttribute("user_id", id);
	}

}

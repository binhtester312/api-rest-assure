package learning.day8;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;
import net.datafaker.Faker;

public class UpdateUser {

	@Test
	void test_updateUser(ITestContext context) {
		Faker faker = new Faker();

		JSONObject data = new JSONObject();
		data.put("name", faker.name().fullName());
		data.put("gender", "Male");
		data.put("email", faker.internet().emailAddress());
		data.put("status", "active"); // Cập nhật từ inactive sang active

		int id = (int) context.getAttribute("user_id");
		String bearerToken = "c35e10e748c6f113775527bcef204e9929b4c9f4b995a8ee253eec46aed57b06";

		// Gửi request PUT để cập nhật
		given()
				.headers("Authorization", "Bearer " + bearerToken)
				.contentType("application/json")
				.pathParam("id", id)
				.body(data.toString())
		.when()
				.put("https://gorest.co.in/public/v2/users/{id}")
		.then()
				.statusCode(200)
				.log().all();
	}

}

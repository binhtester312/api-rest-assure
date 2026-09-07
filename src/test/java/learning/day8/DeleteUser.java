package learning.day8;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DeleteUser {

	@Test
	void test_deleteUser(ITestContext context) {
		int id = (int) context.getAttribute("user_id");
		String bearerToken = "c35e10e748c6f113775527bcef204e9929b4c9f4b995a8ee253eec46aed57b06";

		// Gửi request DELETE xóa người dùng vừa tạo
		given()
				.headers("Authorization", "Bearer " + bearerToken)
				.pathParam("id", id)
		.when()
				.delete("https://gorest.co.in/public/v2/users/{id}")
		.then()
				.statusCode(204)
				.log().all();
	}

}

package day7;

import org.testng.annotations.Test;

import io.restassured.matcher.RestAssuredMatchers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class XMLSchemaValidation {

	@Test
	void xmlSchemavalidation() {
		// ==================== APPROACH 1 (GỐC CỦA THẦY) ====================
		// URL http://restapi.adequateshop.com/api/Traveler hiện đã ngừng hoạt động (404)
		/*
		given()

		.when()
				.get("http://restapi.adequateshop.com/api/Traveler")

		.then()
				.assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("traveler.xsd"));
		*/

		// ==================== APPROACH 2 (CHẠY LOCAL TRÊN MÁY BẠN) ====================
		// Dùng API local http://localhost:8080/api/Traveler phục vụ dữ liệu traveler.xml
		given()

		.when()
				.get("http://localhost:8080/api/Traveler")

		.then()
				.statusCode(200)
				.assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("traveler.xsd"));
	}

}

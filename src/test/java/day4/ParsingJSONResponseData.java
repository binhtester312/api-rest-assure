package day4;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ParsingJSONResponseData {

    @Test(priority = 1)
    public void testJsonResponse() {

        // Approach 1

        given()
                .contentType(ContentType.JSON)

        .when()
                .get("http://localhost:3000/store")

        .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");

    }

}

package day4;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ParsingJSONResponseData {

        @Test(priority = 1)
        public void testJsonResponse() {

                // Approach 1
                /*
                given()
                                .contentType(ContentType.JSON)

                                .when()
                                .get("http://localhost:3000/store")

                                .then()
                                .statusCode(200)
                                .header("Content-Type", "application/json; charset=utf-8")
                                .body("book[3].title", equalTo("The Lord of the Rings"));
                */

                // Approach 2

                Response res = given()
                                .contentType(ContentType.JSON)

                                .when()
                                .get("http://localhost:3000/store");

                Assert.assertEquals(res.getStatusCode(), 200); // validation 1
                Assert.assertEquals(res.header("Content-Type"), "application/json; charset=utf-8");

                String bookname = res.jsonPath().get("book[3].title").toString();
                Assert.assertEquals(bookname, "The Lord of the Rings");

        }

}

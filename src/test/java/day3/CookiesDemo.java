package day3;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class CookiesDemo {

    @Test
    public void testCookies() {

        given()

        .when()
                .get("https://www.google.com/")

        .then()
                .log().all();

    }

}

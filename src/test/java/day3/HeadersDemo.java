package day3;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class HeadersDemo {

    @Test(priority = 1)
    public void testHeaders() {

        given()

        .when()
                .get("https://www.google.com/")

        .then()
                .header("Content-Type", "text/html; charset=ISO-8859-1")
                .and()
                .header("Content-Encoding", "gzip")
                .and()
                .header("Server", "gws");

    }

    @Test(priority = 2)
    public void getHeaders() {

        Response res = given()

        .when()
                .get("https://www.google.com/");

        // get single header info
        // String headervalue = res.getHeader("Content-Type");
        // System.out.println("The value of Content-type header is: " + headervalue);

        // get all headers info
        Headers myheaders = res.getHeaders();

        for (Header hd : myheaders) {
            System.out.println(hd.getName() + "    " + hd.getValue());
        }

    }

}

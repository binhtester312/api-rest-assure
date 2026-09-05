package day1;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

/*
 given()
     content type, set cookies, add auth, add param, set headers info etc....

 when()
     get, post, put, delete

 then()
     validate status code, extract response, extract headers cookies & response body....
*/

public class HTTPRequests {

    int id;

    @Test(priority = 1)
    public void getUsers() {
        given()

                .when()
                .get("https://reqres.in/api/users?page=1")

                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .log().all();
    }

    @Test(priority = 2)
    public void createUser() {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "Thong");
        data.put("job", "Developer");

        id = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .statusCode(201)
                .log().all()
                .extract().jsonPath().getInt("id");
    }

    @Test(priority = 3)
    public void updateUser() {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "Thong update");
        data.put("job", "Developer update");

        given()
                .contentType("application/json")
                .body(data)

                .when()
                .put("https://reqres.in/api/users/" + id)

                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 4)
    public void deleteUser() {

        given()

                .when()
                .delete("https://reqres.in/api/users/" + id)

                .then()
                .statusCode(204)
                .log().all();
    }

}

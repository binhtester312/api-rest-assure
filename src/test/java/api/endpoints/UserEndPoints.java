package api.endpoints;

// UserEndPoints.java
// Created to perform Create, Read, Update, Delete requests to the user API.

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {

    // =========================================================================
    //  CREATE — POST /user
    // =========================================================================

    public static Response createUser(User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);

        return response;
    }

    // =========================================================================
    //  READ — GET /user/{username}
    // =========================================================================

    public static Response readUser(String userName) {
        Response response = given()
                .pathParam("username", userName)
                .when()
                .get(Routes.get_url);

        return response;
    }

    // =========================================================================
    //  UPDATE — PUT /user/{username}
    // =========================================================================

    public static Response updateUser(String userName, User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", userName)
                .body(payload)
                .when()
                .put(Routes.update_url);

        return response;
    }

    // =========================================================================
    //  DELETE — DELETE /user/{username}
    // =========================================================================

    public static Response deleteUser(String userName) {
        Response response = given()
                .pathParam("username", userName)
                .when()
                .delete(Routes.delete_url);

        return response;
    }

}

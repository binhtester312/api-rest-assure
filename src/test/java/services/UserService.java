package services;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.UserRequest;
import models.response.UserResponse;

import static io.restassured.RestAssured.given;

/**
 * UserService — Service Layer cho GoRest User API.
 *
 * =========================================================
 *  TƯƠNG TỰ PAGE OBJECT MODEL (POM) TRONG UI TESTING:
 *
 *  UI Testing:         API Testing (framework này):
 *  ─────────────────   ─────────────────────────────
 *  Page Object class   Service class (UserService)
 *  findElement()       given().when().get/post/put/delete()
 *  click(), type()     Gọi HTTP method
 *  Page methods        Service methods (createUser, getUser...)
 *  Test class          Test class (UserCRUDTest)
 * =========================================================
 *
 * Mọi HTTP call đều đặt tại đây.
 * Test class chỉ gọi service method và assert kết quả.
 */
public class UserService {

    private final RequestSpecification spec;
    private final String token;

    private static final String USERS_ENDPOINT       = "/users";
    private static final String USER_BY_ID_ENDPOINT  = "/users/{id}";

    /**
     * @param spec  RequestSpecification từ BaseTest (đã có logging, content-type)
     * @param token Bearer token để xác thực
     */
    public UserService(RequestSpecification spec, String token) {
        this.spec  = spec;
        this.token = token;
    }

    // =========================================================================
    //  CREATE — POST /users
    // =========================================================================

    /**
     * Tạo người dùng mới.
     *
     * @param userRequest dữ liệu người dùng
     * @return Response thô (để test có thể assert status code, body, headers...)
     */
    public Response createUser(UserRequest userRequest) {
        return given(spec)
                .header("Authorization", "Bearer " + token)
                .body(userRequest)
                .when()
                .post(USERS_ENDPOINT);
    }

    // =========================================================================
    //  READ — GET /users/{id}
    // =========================================================================

    /**
     * Lấy thông tin người dùng theo ID.
     *
     * @param userId ID của người dùng cần tra cứu
     * @return Response thô
     */
    public Response getUserById(int userId) {
        return given(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", userId)
                .when()
                .get(USER_BY_ID_ENDPOINT);
    }

    // =========================================================================
    //  UPDATE — PUT /users/{id}
    // =========================================================================

    /**
     * Cập nhật toàn bộ thông tin người dùng (PUT — full update).
     *
     * @param userId      ID người dùng cần cập nhật
     * @param userRequest dữ liệu mới
     * @return Response thô
     */
    public Response updateUser(int userId, UserRequest userRequest) {
        return given(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", userId)
                .body(userRequest)
                .when()
                .put(USER_BY_ID_ENDPOINT);
    }

    // =========================================================================
    //  DELETE — DELETE /users/{id}
    // =========================================================================

    /**
     * Xóa người dùng theo ID.
     *
     * @param userId ID người dùng cần xóa
     * @return Response thô
     */
    public Response deleteUser(int userId) {
        return given(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", userId)
                .when()
                .delete(USER_BY_ID_ENDPOINT);
    }
}

package tests;

import base.BaseTest;
import models.request.UserRequest;
import models.response.UserResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.UserService;
import utils.FakerUtils;
import utils.TokenManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * UserCRUDTest — Test Suite CRUD đầy đủ cho GoRest User API.
 *
 * =========================================================
 *  API CHAINING FLOW (thực tế trong dự án):
 *
 *  [1] createUser  → POST   /users       → lưu userId (201)
 *  [2] getUser     → GET    /users/{id}  → verify fields (200)
 *  [3] updateUser  → PUT    /users/{id}  → verify update (200)
 *  [4] deleteUser  → DELETE /users/{id}  → verify xóa (204)
 *  [5] verifyGone  → GET    /users/{id}  → xác nhận đã xóa (404)
 * =========================================================
 *
 * Lưu ý: Class này extends BaseTest để kế thừa baseSpec & @BeforeSuite setup.
 */
public class UserCRUDTest extends BaseTest {

    private UserService userService;

    // Biến chia sẻ giữa các test (thay thế ITestContext trong day8)
    private int createdUserId;
    private String createdUserEmail;

    // =========================================================================
    //  SETUP
    // =========================================================================

    @BeforeClass
    public void setup() {
        // Đọc token an toàn qua TokenManager (env var / system property)
        String token = TokenManager.getToken();
        userService = new UserService(baseSpec, token);

        System.out.println("[UserCRUDTest] Setup hoàn tất. Token đã được nạp.");
    }

    // =========================================================================
    //  TC01: CREATE USER — POST /users
    // =========================================================================

    @Test(priority = 1, description = "TC01 - Tạo người dùng mới với status inactive")
    public void tc01_createUser() {
        // Sinh dữ liệu ngẫu nhiên + traceable
        UserRequest newUser = FakerUtils.generateInactiveUser();
        createdUserEmail = newUser.getEmail(); // lưu lại để verify sau

        System.out.println("[TC01] Tạo user: " + newUser.getEmail());

        // Gọi Service Layer → KHÔNG viết given().when() trực tiếp
        UserResponse response = userService.createUser(newUser)
                .then()
                .statusCode(201)
                .extract()
                .as(UserResponse.class);

        // Assertions rõ ràng với message dễ debug
        assertThat("User phải có ID > 0", response.getId(), greaterThan(0));
        assertThat("Tên user phải khớp", response.getName(), equalTo(newUser.getName()));
        assertThat("Email phải khớp", response.getEmail(), equalTo(newUser.getEmail()));
        assertThat("Gender phải khớp", response.getGender(), equalTo("male"));
        assertThat("Status phải là inactive", response.getStatus(), equalTo("inactive"));

        // Lưu ID để các test sau dùng (API Chaining)
        createdUserId = response.getId();
        System.out.println("[TC01] ✅ PASS — Created userId: " + createdUserId);
    }

    // =========================================================================
    //  TC02: GET USER — GET /users/{id}
    // =========================================================================

    @Test(priority = 2, description = "TC02 - Lấy thông tin user vừa tạo theo ID",
          dependsOnMethods = "tc01_createUser")
    public void tc02_getUserById() {
        System.out.println("[TC02] GET user id: " + createdUserId);

        UserResponse response = userService.getUserById(createdUserId)
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        assertThat("ID phải khớp", response.getId(), equalTo(createdUserId));
        assertThat("Email phải khớp với user đã tạo", response.getEmail(), equalTo(createdUserEmail));
        assertThat("Status phải là inactive", response.getStatus(), equalTo("inactive"));

        System.out.println("[TC02] ✅ PASS — Get user thành công: " + response.getName());
    }

    // =========================================================================
    //  TC03: UPDATE USER — PUT /users/{id}
    // =========================================================================

    @Test(priority = 3, description = "TC03 - Cập nhật status user từ inactive → active",
          dependsOnMethods = "tc02_getUserById")
    public void tc03_updateUser() {
        System.out.println("[TC03] UPDATE user id: " + createdUserId);

        // Sinh dữ liệu mới để update
        UserRequest updatedUser = FakerUtils.generateActiveUser(); // status = "active"

        UserResponse response = userService.updateUser(createdUserId, updatedUser)
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);

        assertThat("ID sau update phải giữ nguyên", response.getId(), equalTo(createdUserId));
        assertThat("Status phải được cập nhật thành active", response.getStatus(), equalTo("active"));
        assertThat("Tên phải được cập nhật", response.getName(), equalTo(updatedUser.getName()));

        System.out.println("[TC03] ✅ PASS — Update thành công. Status mới: " + response.getStatus());
    }

    // =========================================================================
    //  TC04: DELETE USER — DELETE /users/{id}
    // =========================================================================

    @Test(priority = 4, description = "TC04 - Xóa user vừa tạo",
          dependsOnMethods = "tc03_updateUser")
    public void tc04_deleteUser() {
        System.out.println("[TC04] DELETE user id: " + createdUserId);

        userService.deleteUser(createdUserId)
                .then()
                .statusCode(204); // 204 No Content = xóa thành công

        System.out.println("[TC04] ✅ PASS — User đã bị xóa. userId: " + createdUserId);
    }

    // =========================================================================
    //  TC05: VERIFY DELETED — GET /users/{id} → phải trả về 404
    // =========================================================================

    @Test(priority = 5, description = "TC05 - Xác nhận user đã xóa → GET phải trả về 404",
          dependsOnMethods = "tc04_deleteUser")
    public void tc05_verifyUserDeleted() {
        System.out.println("[TC05] Verify DELETE — GET user id: " + createdUserId);

        userService.getUserById(createdUserId)
                .then()
                .statusCode(404); // User đã xóa → 404 Not Found

        System.out.println("[TC05] ✅ PASS — Confirmed: User không còn tồn tại (404).");
    }
}

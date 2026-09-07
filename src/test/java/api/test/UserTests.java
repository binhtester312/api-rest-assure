package api.test;

// UserTests.java — Test class gọi UserEndPoints để thực thi CRUD tests.
// Theo flow bài giảng: Test class KHÔNG viết HTTP call trực tiếp,
// chỉ gọi method của UserEndPoints và assert kết quả.

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayload;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.internet().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
    }

    @Test(priority = 1)
    public void testPostUser() {
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    // -------------------------------------------------------------------------
    // TC02: Lấy thông tin user — GET /user/{username}
    // -------------------------------------------------------------------------
    @Test(priority = 2)
    public void testReadUser() {
        Response response = UserEndPoints.readUser(this.userPayload.getUsername());

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    // -------------------------------------------------------------------------
    // TC03: Cập nhật user — PUT /user/{username}
    // -------------------------------------------------------------------------
    @Test(priority = 3)
    public void testUpdateUser() {
        // Cập nhật tên mới
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    // -------------------------------------------------------------------------
    // TC04: Xóa user — DELETE /user/{username}
    // -------------------------------------------------------------------------
    @Test(priority = 4)
    public void testDeleteUser() {
        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

}

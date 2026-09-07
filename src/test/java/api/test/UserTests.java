package api.test;

// UserTests.java — Test class gọi UserEndPoints để thực thi CRUD tests.
// Theo flow bài giảng: Test class KHÔNG viết HTTP call trực tiếp,
// chỉ gọi method của UserEndPoints và assert kết quả.

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayload;
    public Logger logger;

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

        // Obtain logger
        logger = LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("********** Creating user **********");
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** User is created **********");
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("********** Reading User Info **********");
        Response response = UserEndPoints.readUser(this.userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** User info is displayed **********");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {
        logger.info("********** Updating User **********");
        // update data using payload
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200);

        // Checking data after update
        Response responseAfterupdate = UserEndPoints.readUser(this.userPayload.getUsername());
        Assert.assertEquals(responseAfterupdate.getStatusCode(), 200);
        logger.info("********** User updated **********");
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        logger.info("********** Deleting User **********");
        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** User deleted **********");
    }

}

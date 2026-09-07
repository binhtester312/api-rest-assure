package learning.day2;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
Different ways to create POST request body:
1) Post request body using HashMap
2) Post request body creation using Org.JSON library
3) Post request body creation using POJO class
4) Post request using external JSON file data
*/

public class DiffWaysToCreatePostRequestBody {

        String id;

        // =========================================================================
        // 1) Post request body using HashMap
        // =========================================================================

        @Test(priority = 1)
        public void testPostUsingHashMap() {

                HashMap<String, Object> data = new HashMap<>();
                data.put("name", "Thong");
                data.put("job", "Developer");
                data.put("phone", "12345");

                String courseArr[] = { "Java", "Python", "Rest API" };
                data.put("courses", courseArr);

                id = given()
                                .contentType("application/json")
                                .body(data)

                                .when()
                                .post("http://localhost:3000/students")

                                .then()
                                .statusCode(201)
                                .body("name", equalTo("Thong"))
                                .body("job", equalTo("Developer"))
                                .body("phone", equalTo("12345"))
                                .body("courses[0]", equalTo("Java"))
                                .body("courses[1]", equalTo("Python"))
                                .body("courses[2]", equalTo("Rest API"))
                                .header("Content-Type", "application/json; charset=utf-8")
                                .log().all()
                                .extract().jsonPath().getString("id");
        }

        // Xóa bản ghi sau khi test tạo bằng HashMap
        @Test(priority = 2)
        public void testDeleteStudentAfterHashMap() {
                given()

                                .when()
                                .delete("http://localhost:3000/students/" + id)

                                .then()
                                .statusCode(200)
                                .log().all();
        }

        // =========================================================================
        // 2) Post request body creation using Org.JSON library
        // =========================================================================

        @Test(priority = 3)
        public void testPostUsingOrgJson() {

                JSONObject data = new JSONObject();
                data.put("name", "Binh");
                data.put("job", "Developer");
                data.put("phone", "12345");

                String courseArr[] = { "Java", "Python", "Rest API" };
                data.put("courses", courseArr);

                id = given()
                                .contentType("application/json")
                                // Lưu ý: với JSONObject, bắt buộc phải gọi .toString()
                                .body(data.toString())

                                .when()
                                .post("http://localhost:3000/students")

                                .then()
                                .statusCode(201)
                                .body("name", equalTo("Binh"))
                                .body("job", equalTo("Developer"))
                                .body("phone", equalTo("12345"))
                                .body("courses[0]", equalTo("Java"))
                                .body("courses[1]", equalTo("Python"))
                                .body("courses[2]", equalTo("Rest API"))
                                .header("Content-Type", "application/json; charset=utf-8")
                                .log().all()
                                .extract().jsonPath().getString("id");
        }

        // Xóa bản ghi sau khi test tạo bằng Org.JSON
        @Test(priority = 4)
        public void testDeleteStudentAfterOrgJson() {
                given()

                                .when()
                                .delete("http://localhost:3000/students/" + id)

                                .then()
                                .statusCode(200)
                                .log().all();
        }

        // =========================================================================
        // 3) Post request body creation using POJO class
        // =========================================================================

        @Test(priority = 5)
        public void testPostUsingPOJO() {

                Pojo_PostRequest data = new Pojo_PostRequest();
                data.setName("An");
                data.setJob("Developer");
                data.setPhone("12345");

                String courseArr[] = { "Java", "Python", "Rest API" };
                data.setCourses(courseArr);

                id = given()
                                .contentType("application/json")
                                .body(data)

                                .when()
                                .post("http://localhost:3000/students")

                                .then()
                                .statusCode(201)
                                .body("name", equalTo("An"))
                                .body("job", equalTo("Developer"))
                                .body("phone", equalTo("12345"))
                                .body("courses[0]", equalTo("Java"))
                                .body("courses[1]", equalTo("Python"))
                                .body("courses[2]", equalTo("Rest API"))
                                .header("Content-Type", "application/json; charset=utf-8")
                                .log().all()
                                .extract().jsonPath().getString("id");
        }

        // Xóa bản ghi sau khi test tạo bằng POJO
        @Test(priority = 6)
        public void testDeleteStudentAfterPOJO() {
                given()

                                .when()
                                .delete("http://localhost:3000/students/" + id)

                                .then()
                                .statusCode(200)
                                .log().all();
        }

        // =========================================================================
        // 4) Post request using external JSON file data
        // =========================================================================

        @Test(priority = 7)
        public void testPostUsingExternalJsonFile() throws FileNotFoundException {

                // Đọc dữ liệu từ file body.json ở thư mục gốc
                File f = new File(".//body.json");
                FileReader fr = new FileReader(f);
                JSONTokener jt = new JSONTokener(fr);
                JSONObject data = new JSONObject(jt);

                id = given()
                                .contentType("application/json")
                                .body(data.toString())

                                .when()
                                .post("http://localhost:3000/students")

                                .then()
                                .statusCode(201)
                                .body("name", equalTo("Thong"))
                                .body("job", equalTo("Developer"))
                                .body("phone", equalTo("12345"))
                                .body("courses[0]", equalTo("Java"))
                                .body("courses[1]", equalTo("Python"))
                                .body("courses[2]", equalTo("Rest API"))
                                .header("Content-Type", "application/json; charset=utf-8")
                                .log().all()
                                .extract().jsonPath().getString("id");
        }

        // Xóa bản ghi sau khi test tạo bằng file JSON
        @Test(priority = 8)
        public void testDeleteStudentAfterExternalJson() {
                given()

                                .when()
                                .delete("http://localhost:3000/students/" + id)

                                .then()
                                .statusCode(200)
                                .log().all();
        }

}

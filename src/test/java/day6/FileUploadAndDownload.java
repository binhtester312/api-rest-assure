package day6;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import org.testng.annotations.Test;

public class FileUploadAndDownload {

	@Test
	void singleFileUpload() {
		// Thầy dùng: "C:\\AutomationPractice\\Test1.txt" (Windows)
		// Máy Mac của bạn dùng đường dẫn tương đối từ thư mục gốc dự án:
		File myfile = new File("Test1.txt");

		given()
				.multiPart("file", myfile)
				.contentType("multipart/form-data")

		.when()
				.post("http://localhost:8080/uploadFile")

		.then()
				.statusCode(200)
				.body("fileName", equalTo("Test1.txt"))
				.log().all();
	}

}

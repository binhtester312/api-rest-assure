package day5;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.builder.ResponseBuilder;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ParsingXMLResponse {

	@Test
	void testXMLResponse() throws Exception {

		// ==================== APPROACH 1 ====================
		/*
		 * given()
		 * 
		 * .when()
		 * .get("http://restapi.adequateshop.com/api/Traveler?page=1")
		 * .then()
		 * .statusCode(200)
		 * .header("Content-Type", "application/xml; charset=utf-8")
		 * .body("TravelerinformationResponse.page", equalTo("1"))
		 * .body("TravelerinformationResponse.travelers.Travelerinformation[0].name",
		 * equalTo("Vijay Bharath Reddy"));
		 */

		// ==================== APPROACH 2 ====================
		// Đọc nội dung file traveler.xml nội bộ và nạp vào đối tượng Response res
		File xmlFile = new File("traveler.xml");
		String xmlContent = new String(Files.readAllBytes(xmlFile.toPath()));

		Response res = new ResponseBuilder()
				.setStatusCode(200)
				.setHeader("Content-Type", "application/xml; charset=utf-8")
				.setBody(xmlContent)
				.build();

		// Approach 2: Dùng res
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.header("Content-Type"), "application/xml; charset=utf-8");

		String pageNo = res.xmlPath().get("TravelerinformationResponse.page").toString();
		Assert.assertEquals(pageNo, "1");

		String travelName = res.xmlPath().get("TravelerinformationResponse.travelers.Travelerinformation[0].name")
				.toString();
		Assert.assertEquals(travelName, "Vijay Bharath Reddy");

		// Kiểm tra thêm phần tử index [1] ("Bình")
		String travelName1 = res.xmlPath().get("TravelerinformationResponse.travelers.Travelerinformation[1].name")
				.toString();
		Assert.assertEquals(travelName1, "Bình");

		System.out.println("Status Code: " + res.getStatusCode());
		System.out.println("Content-Type: " + res.header("Content-Type"));
		System.out.println("pageNo: " + pageNo);
		System.out.println("travelName [0]: " + travelName);
		System.out.println("travelName [1]: " + travelName1);
	}

	@Test
	void testXMLResponseBody() throws Exception {
		File xmlFile = new File("traveler.xml");
		String xmlContent = new String(Files.readAllBytes(xmlFile.toPath()));

		Response res = new ResponseBuilder()
				.setStatusCode(200)
				.setHeader("Content-Type", "application/xml; charset=utf-8")
				.setBody(xmlContent)
				.build();

		XmlPath xmlobj = new XmlPath(res.asString());

		// Verify total number of travellers
		List<String> travellers = xmlobj.getList("TravelerinformationResponse.travelers.Travelerinformation");
		Assert.assertEquals(travellers.size(), 10);

		// verify traveller name is present in response
		List<String> traveller_names = xmlobj.getList("TravelerinformationResponse.travelers.Travelerinformation.name");

		boolean status = false;
		for (String travellername : traveller_names) {
			if (travellername.equals("Vijay Bharath Reddy")) {
				status = true;
				break;
			}
		}

		Assert.assertEquals(status, true);
	}

}

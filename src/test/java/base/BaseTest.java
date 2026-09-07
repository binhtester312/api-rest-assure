package base;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest — Lớp cha cho mọi Test Class trong framework.
 *
 * Trách nhiệm:
 *  - Setup RestAssured baseURI, timeout từ ConfigManager
 *  - Cung cấp RequestSpecification dùng chung (JSON + logging)
 *
 * Cách dùng:
 *   public class UserCRUDTest extends BaseTest { ... }
 */
public class BaseTest {

    /** RequestSpecification dùng chung cho mọi request (không có auth header) */
    protected static RequestSpecification baseSpec;

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        // 1. Set Base URI từ config.properties
        RestAssured.baseURI = ConfigManager.getBaseUrl();

        // 2. Build RequestSpecification chuẩn với Content-Type và Logging
        baseSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())   // Log mọi request
                .addFilter(new ResponseLoggingFilter())  // Log mọi response
                .setRelaxedHTTPSValidation()             // Bỏ qua SSL cert validation
                .build();

        System.out.println("====================================================");
        System.out.println("[BaseTest] Framework khởi động thành công!");
        System.out.println("[BaseTest] Base URL: " + ConfigManager.getBaseUrl());
        System.out.println("====================================================");
    }

    @AfterSuite(alwaysRun = true)
    public void globalTeardown() {
        System.out.println("====================================================");
        System.out.println("[BaseTest] Test suite hoàn thành.");
        System.out.println("====================================================");
    }
}

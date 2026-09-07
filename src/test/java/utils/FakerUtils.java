package utils;

import models.request.UserRequest;
import net.datafaker.Faker;

/**
 * FakerUtils — Sinh test data ngẫu nhiên, unique và traceable.
 *
 * Format email chuẩn: auto_<timestamp>@test.auto
 * → Cho phép truy vết test nào tạo ra user này khi debug.
 */
public class FakerUtils {

    private static final Faker faker = new Faker();

    /**
     * Sinh một UserRequest với dữ liệu random + traceable timestamp.
     *
     * @param status "active" hoặc "inactive"
     * @return UserRequest đã điền đầy đủ fields
     */
    public static UserRequest generateUser(String status) {
        long timestamp = System.currentTimeMillis();

        return UserRequest.builder()
                .name(faker.name().fullName())
                .gender("male")
                .email("auto_" + timestamp + "@test.auto")
                .status(status)
                .build();
    }

    /**
     * Sinh user mặc định với status = "active"
     */
    public static UserRequest generateActiveUser() {
        return generateUser("active");
    }

    /**
     * Sinh user mặc định với status = "inactive"
     */
    public static UserRequest generateInactiveUser() {
        return generateUser("inactive");
    }

    // Ẩn constructor
    private FakerUtils() {}
}

package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager — Singleton đọc config từ config.properties.
 *
 * Cách dùng:
 *   String baseUrl = ConfigManager.getBaseUrl();
 *   int timeout    = ConfigManager.getTimeout();
 */
public class ConfigManager {

    private static final Properties props = new Properties();

    // Static initializer: load file một lần duy nhất khi class được load
    static {
        try (InputStream input = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("[ConfigManager] Không tìm thấy 'config.properties' trong classpath!");
            }
            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("[ConfigManager] Lỗi đọc config.properties: " + e.getMessage(), e);
        }
    }

    // =========================================================================
    //  Public API
    // =========================================================================

    /** Base URL của API (ví dụ: https://gorest.co.in/public/v2) */
    public static String getBaseUrl() {
        return get("base.url");
    }

    /** Connection timeout tính bằng milliseconds (default: 10000) */
    public static int getConnectionTimeout() {
        return Integer.parseInt(get("connection.timeout"));
    }

    /** Socket timeout tính bằng milliseconds (default: 10000) */
    public static int getSocketTimeout() {
        return Integer.parseInt(get("socket.timeout"));
    }

    /** Log level: ALL | NONE | HEADERS | BODY | STATUS */
    public static String getLogLevel() {
        return props.getProperty("log.level", "ALL");
    }

    // =========================================================================
    //  Private helper
    // =========================================================================

    private static String get(String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("[ConfigManager] Property '" + key + "' không được định nghĩa trong config.properties");
        }
        return value.trim();
    }

    // Ẩn constructor — không cho phép khởi tạo
    private ConfigManager() {}
}

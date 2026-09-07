package utils;

/**
 * TokenManager — Quản lý Bearer Token an toàn.
 *
 * Token KHÔNG được hardcode trong source code.
 * Ưu tiên đọc theo thứ tự:
 *   1. Environment Variable : GOREST_TOKEN
 *   2. System Property      : -Dgorest.token=xxx  (truyền qua Maven hoặc IDE)
 *
 * Cách set trước khi chạy:
 *   Mac/Linux: export GOREST_TOKEN="your_token_here"
 *   Maven:     mvn test -Dgorest.token=your_token_here
 *   IntelliJ:  Run > Edit Configurations > Environment Variables > GOREST_TOKEN=xxx
 */
public class TokenManager {

    // Tên environment variable và system property
    private static final String ENV_VAR       = "GOREST_TOKEN";
    private static final String SYS_PROPERTY  = "gorest.token";

    // Cache token sau lần đọc đầu tiên
    private static String cachedToken = null;

    /**
     * Trả về Bearer Token hợp lệ.
     * Throw IllegalStateException nếu không tìm thấy ở cả 2 nguồn.
     */
    public static String getToken() {
        if (cachedToken != null && !cachedToken.isBlank()) {
            return cachedToken;
        }

        // 1. Thử đọc từ Environment Variable
        String token = System.getenv(ENV_VAR);

        // 2. Fallback sang System Property (ví dụ: -Dgorest.token=xxx)
        if (token == null || token.isBlank()) {
            token = System.getProperty(SYS_PROPERTY);
        }

        // 3. Nếu vẫn không có → throw để test fail rõ ràng thay vì NPE mơ hồ
        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                "[TokenManager] Không tìm thấy GoRest API Token!\n" +
                "Vui lòng set theo 1 trong 2 cách:\n" +
                "  1. export " + ENV_VAR + "=\"your_token_here\"\n" +
                "  2. mvn test -D" + SYS_PROPERTY + "=your_token_here"
            );
        }

        cachedToken = token.trim();
        return cachedToken;
    }

    // Ẩn constructor
    private TokenManager() {}
}

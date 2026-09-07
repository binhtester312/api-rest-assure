package models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * UserResponse — POJO để Deserialize JSON response body từ GoRest API.
 *
 * @JsonIgnoreProperties(ignoreUnknown = true):
 *   Bỏ qua các field trong JSON mà class này chưa khai báo.
 *   → Tránh lỗi khi API trả về thêm field mới mà ta chưa cần.
 *
 * Cách dùng trong test:
 *   UserResponse user = response.as(UserResponse.class);
 *   assertThat(user.getStatus()).isEqualTo("active");
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private Integer id;
    private String  name;
    private String  email;
    private String  gender;
    private String  status;
}

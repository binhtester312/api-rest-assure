package models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRequest — POJO cho phần body của POST/PUT request.
 *
 * Dùng @Builder cho phép tạo object theo kiểu fluent:
 *   UserRequest user = UserRequest.builder()
 *       .name("John")
 *       .gender("male")
 *       .email("john@test.com")
 *       .status("active")
 *       .build();
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String gender;
    private String email;
    private String status;
}

package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserInfoDTO(

        String userId,
        String userName,
        String password,
        String email,
        String nickname,
        String existsYn,
        String roles



) {


}

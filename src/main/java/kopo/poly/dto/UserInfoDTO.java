package kopo.poly.dto;

import lombok.Builder;

@Builder
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

package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoDTO(

        String id,
        String connected_at,
        Properties properties,
        KakaoAccount kakao_account,
        LinkedHashMap<String, Object>additional_properties
) {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {
        private String nickname;
        private Map<String, Object> additional_properties = new LinkedHashMap<>();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        private Boolean profile_nickname_needs_agreement;
        private Profile profile;
        private Boolean name_needs_agreement;
        private String name;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
        private Boolean has_phone_number;
        private Boolean phone_number_needs_agreement;
        private String phone_number;
        private Boolean has_birthyear;
        private Boolean birthyear_needs_agreement;
        private String birthyear;
        private Boolean has_birthday;
        private Boolean birthday_needs_agreement;
        private String birthday;
        private String birthday_type;
        private Boolean has_gender;
        private Boolean gender_needs_agreement;
        private String gender;

        @JsonIgnore
        private Map<String, Object> additional_properties = new LinkedHashMap<>();

        @Data
        public static class Profile {
            private String nickname;
            private Boolean is_default_nickname;
            private Map<String, Object> additional_properties = new LinkedHashMap<>();
        }
    }
}

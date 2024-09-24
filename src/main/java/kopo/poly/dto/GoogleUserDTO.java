package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleUserDTO(

        String iss,
        String azp,
        String aud,
        String sub,
        String email,
        String email_verified,
        String at_hash,
        String name,
        String picture,
        String given_name,
        String family_name,
        String locale,
        String iat,
        String exp,
        String alg,
        String kid,
        String typ
) {
}

package kopo.poly.dto;

import lombok.Builder;

@Builder
public record MsgDTO(

        String msg,
        int result
) {
}

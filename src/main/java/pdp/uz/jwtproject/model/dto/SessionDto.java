package pdp.uz.jwtproject.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDto {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private UserDto user;
}

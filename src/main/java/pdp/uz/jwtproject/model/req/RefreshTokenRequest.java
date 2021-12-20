package pdp.uz.jwtproject.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {

    @ApiModelProperty(required = true)
    private String refreshToken;
}

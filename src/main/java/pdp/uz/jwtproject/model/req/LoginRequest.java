package pdp.uz.jwtproject.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @ApiModelProperty(example = "admin", required = true)
    @JsonProperty("username")
    private String username;

    @ApiModelProperty(example = "12345", required = true)
    @JsonProperty("password")
    private String password;
}

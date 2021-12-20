package pdp.uz.jwtproject.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("User create request")
public class UserCreateRequest {

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(required = true)
    private String phone;

    @ApiModelProperty(required = true)
    private String email;
}

package pdp.uz.jwtproject.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachRoleToUserRequest {

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String roleName;
}

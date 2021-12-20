package pdp.uz.jwtproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pdp.uz.jwtproject.model.GenericDto;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"password"})
public class UserDto extends GenericDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

//    @JsonIgnore
    private String password;

    @JsonProperty("roles")
    private List<RoleDto> roles;
}

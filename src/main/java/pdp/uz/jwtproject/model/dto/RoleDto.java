package pdp.uz.jwtproject.model.dto;

import lombok.Getter;
import lombok.Setter;
import pdp.uz.jwtproject.model.GenericDto;

@Getter
@Setter
public class RoleDto extends GenericDto {
    private String name;
}

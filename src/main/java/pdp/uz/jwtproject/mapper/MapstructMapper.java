package pdp.uz.jwtproject.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pdp.uz.jwtproject.domain.Role;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.model.dto.RoleDto;
import pdp.uz.jwtproject.model.dto.UserDto;
import pdp.uz.jwtproject.model.req.UserCreateRequest;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    /**
     * User
     */

    User toUser(UserCreateRequest dto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);

    /**
     * Role
     */

    RoleDto toRoleDto(Role role);

    List<RoleDto> toRoleDto(List<Role> roles);
}

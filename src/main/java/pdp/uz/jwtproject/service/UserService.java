package pdp.uz.jwtproject.service;

import pdp.uz.jwtproject.domain.Role;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.model.dto.RoleDto;
import pdp.uz.jwtproject.model.dto.UserDto;
import pdp.uz.jwtproject.model.req.UserCreateRequest;

import java.util.List;

public interface UserService {

    UserDto getUser(Long id);

    List<UserDto> getUsers();

    UserDto saveUser(UserCreateRequest req);

    UserDto registration(UserCreateRequest req);

    Boolean verifyEmail(String email, String code);

    RoleDto saveRole(Role role);

    void attachRoleToUser(String username, String roleName);

    Boolean verifyOtpCode(String phone, String code);
}

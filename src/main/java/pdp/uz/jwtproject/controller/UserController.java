package pdp.uz.jwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pdp.uz.jwtproject.domain.Role;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.model.dto.RoleDto;
import pdp.uz.jwtproject.model.dto.UserDto;
import pdp.uz.jwtproject.model.req.AttachRoleToUserRequest;
import pdp.uz.jwtproject.model.req.UserCreateRequest;
import pdp.uz.jwtproject.model.res.DataDto;
import pdp.uz.jwtproject.service.SmsService;
import pdp.uz.jwtproject.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController extends ApiController {

    private final UserService userService;
    private final SmsService smsService;

    @GetMapping(API_PATH + "/user/{id}")
    public ResponseEntity<DataDto<UserDto>> getUser(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(new DataDto<>(userService.getUser(id)));
    }

    @GetMapping(API_PATH + "/users")
    public ResponseEntity<DataDto<List<UserDto>>> getUsers() {
        return ResponseEntity.ok(new DataDto<>(userService.getUsers()));
    }

    @PostMapping(API_PATH + "/user/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserCreateRequest req) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(req));
    }

    @PostMapping(API_PATH + PUBLIC + "/user/registration")
    private ResponseEntity<UserDto> registration(@RequestBody UserCreateRequest req) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/public/user/registration").toUriString());
        return ResponseEntity.created(uri).body(userService.registration(req));
    }

    @GetMapping(API_PATH + PUBLIC + "/user/code/verify")
    private ResponseEntity<Boolean> verifyOtpCode(@RequestParam(value = "phone") String phone,
                                                  @RequestParam(value = "code") String code) {
        return ResponseEntity.ok(userService.verifyOtpCode(phone, code));
    }

    @GetMapping(API_PATH + PUBLIC + "/user/verify")
    private ResponseEntity<Boolean> verifyEmail(@RequestParam(value = "email") String email,
                                                @RequestParam(value = "code") String code) {
        return ResponseEntity.ok(userService.verifyEmail(email, code));
    }

    @PostMapping(API_PATH + "/role/save")
    public ResponseEntity<RoleDto> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping(API_PATH + "/attach/role/to/user")
    public ResponseEntity<?> attachRoleToUser(@RequestBody AttachRoleToUserRequest req) {
        userService.attachRoleToUser(req.getUsername(), req.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping(API_PATH + PUBLIC + "/sms/send")
    public ResponseEntity<?> sendMessage(@RequestParam(value = "phone") String phone,
                                         @RequestParam(value = "text") String text) {
        return ResponseEntity.ok(smsService.sendMessage(phone, text));
    }
}

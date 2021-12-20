package pdp.uz.jwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pdp.uz.jwtproject.domain.Role;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.domain.UserOtpCode;
import pdp.uz.jwtproject.enums.OtpEnum;
import pdp.uz.jwtproject.helpers.Utils;
import pdp.uz.jwtproject.mapper.MapstructMapper;
import pdp.uz.jwtproject.model.dto.RoleDto;
import pdp.uz.jwtproject.model.dto.UserDto;
import pdp.uz.jwtproject.model.req.UserCreateRequest;
import pdp.uz.jwtproject.repository.RoleRepo;
import pdp.uz.jwtproject.repository.UserOtpCodeRepo;
import pdp.uz.jwtproject.repository.UserRepo;
import pdp.uz.jwtproject.service.SmsService;
import pdp.uz.jwtproject.service.UserService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserOtpCodeRepo userOtpCodeRepo;
    private final PasswordEncoder passwordEncoder;
    private final MapstructMapper mapper;
    private final JavaMailSender javaMailSender;
    private final SmsService smsService;

    @Value("${server.base.url}")
    private String baseUrl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserDto getUser(Long id) {
        log.info("Fetching user {}", id);
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.map(mapper::toUserDto).orElse(null);
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("Fetching all users");
        List<User> users = userRepo.findAll();
        return mapper.toUserDto(users);
    }

    @Transactional
    @Override
    public UserDto saveUser(UserCreateRequest req) {
        log.info("Saving new user {} to the database", req.getName());
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        User user = mapper.toUser(req);
        user.setActive(true);
        return mapper.toUserDto(userRepo.save(user));
    }

    @Transactional
    @Override
    public UserDto registration(UserCreateRequest req) {
        log.info("Saving new user {} to the database from registration", req.getName());
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        User user = mapper.toUser(req);
        String code = UUID.randomUUID().toString();
        user.setCode(code);
        user.setActive(false);
        user.getRoles().add(roleRepo.findByName("ROLE_USER"));
        userRepo.save(user);

        String optCode = Utils.generateRandom();
        boolean success = smsService.sendMessage(user.getPhone(), optCode);
        if (success) {
            UserOtpCode userOtpCode = new UserOtpCode();
            userOtpCode.setCode(optCode);
            userOtpCode.setPhone(user.getPhone());
            userOtpCodeRepo.save(userOtpCode);
        }

//        boolean success = sendMessage(user.getEmail(), code);
//        if (success) {
//            log.info("{} - email ga kod yuborildi!", user.getEmail());
//        }
        return mapper.toUserDto(user);
    }

    @Override
    public Boolean verifyOtpCode(String phone, String code) {
        UserOtpCode userOtpCode = userOtpCodeRepo.findByPhoneAndCode(phone, code);
        if (Objects.isNull(userOtpCode)) {
            return false;
        }
        User user = userRepo.findByPhone(phone);
        if (Objects.isNull(user)) {
            return false;
        }
        user.setActive(true);
        userRepo.save(user);

        userOtpCode.setStatus(OtpEnum.ACCEPTED);
        userOtpCodeRepo.save(userOtpCode);

        return true;
    }

    private Boolean sendMessage(String email, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Verify Email");
            message.setText(baseUrl + "/api/public/user/verify?email=" + email + "&code=" + code);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean verifyEmail(String email, String code) {
        User user = userRepo.findByEmail(email);
        if (Objects.nonNull(user)) {
            if (user.getCode().equals(code)) {
                user.setActive(true);
                userRepo.save(user);
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public RoleDto saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        roleRepo.save(role);
        return mapper.toRoleDto(role);
    }

    @Transactional
    @Override
    public void attachRoleToUser(String username, String roleName) {
        log.info("Attached role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}

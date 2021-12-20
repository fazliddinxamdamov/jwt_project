package pdp.uz.jwtproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pdp.uz.jwtproject.model.dto.SessionDto;
import pdp.uz.jwtproject.model.req.LoginRequest;
import pdp.uz.jwtproject.model.req.RefreshTokenRequest;
import pdp.uz.jwtproject.model.res.DataDto;
import pdp.uz.jwtproject.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AuthController extends ApiController {

    private final AuthService authService;

    @PostMapping(LOGIN_URL)
    public ResponseEntity<DataDto<SessionDto>> login(@RequestBody LoginRequest req, HttpServletRequest httpReq) {
        return authService.login(req, httpReq);
    }

    @PostMapping(REFRESH_TOKEN_URL)
    public ResponseEntity<DataDto<SessionDto>> refreshToken(@RequestBody RefreshTokenRequest req, HttpServletRequest httpReq) {
        return authService.refreshToken(req, httpReq);
    }
}

package pdp.uz.jwtproject.service;

import org.springframework.http.ResponseEntity;
import pdp.uz.jwtproject.model.dto.SessionDto;
import pdp.uz.jwtproject.model.req.LoginRequest;
import pdp.uz.jwtproject.model.req.RefreshTokenRequest;
import pdp.uz.jwtproject.model.res.DataDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    ResponseEntity<DataDto<SessionDto>> login(LoginRequest req, HttpServletRequest httpReq);

    ResponseEntity<DataDto<SessionDto>> refreshToken(RefreshTokenRequest req, HttpServletRequest httpReq);
}

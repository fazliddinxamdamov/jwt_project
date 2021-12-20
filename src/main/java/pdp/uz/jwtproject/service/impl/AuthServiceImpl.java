package pdp.uz.jwtproject.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pdp.uz.jwtproject.domain.Role;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.mapper.MapstructMapper;
import pdp.uz.jwtproject.model.dto.SessionDto;
import pdp.uz.jwtproject.model.req.LoginRequest;
import pdp.uz.jwtproject.model.req.RefreshTokenRequest;
import pdp.uz.jwtproject.model.res.AppErrorDto;
import pdp.uz.jwtproject.model.res.DataDto;
import pdp.uz.jwtproject.repository.UserRepo;
import pdp.uz.jwtproject.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pdp.uz.jwtproject.helpers.Utils.isEmpty;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final MapstructMapper mapper;

    @Override
    public ResponseEntity<DataDto<SessionDto>> login(LoginRequest req, HttpServletRequest httpReq) {
        if (isEmpty(req.getUsername())) {
            log.error("Username is required field!");
            throw new ValidationException("Username is required field!");
        }
        if (isEmpty(req.getPassword())) {
            log.error("Password is required field!");
            throw new ValidationException("Password is required field!");
        }

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("username", req.getUsername()));
            nameValuePairs.add(new BasicNameValuePair("password", req.getPassword()));

            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/login").toUriString());

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpPost);
            return getSessionDto(req, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<DataDto<SessionDto>> getSessionDto(LoginRequest req, HttpResponse response) throws IOException {
        JsonNode json_auth = new ObjectMapper().readTree(EntityUtils.toString(response.getEntity()));
        if (!json_auth.has("error") && !json_auth.has("detail_message")) {
            SessionDto sessionDto = SessionDto.builder()
                    .expiresIn(json_auth.get("expires_in").asLong())
                    .accessToken(json_auth.get("access_token").asText())
                    .refreshToken(json_auth.get("refresh_token").asText())
                    .user(mapper.toUserDto(userRepo.findByUsername(req.getUsername())))
                    .build();
            return ResponseEntity.ok(new DataDto<>(sessionDto));
        } else {
            String error_message = "";
            if (json_auth.has("error")) {
                error_message = json_auth.get("error").asText();
            } else if (json_auth.has("detail_message")) {
                error_message = json_auth.get("detail_message").asText();
            }
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(error_message)), HttpStatus.valueOf(response.getStatusLine().getStatusCode()));
        }
    }

    @Override
    public ResponseEntity<DataDto<SessionDto>> refreshToken(RefreshTokenRequest req, HttpServletRequest httpReq) {
        if (isEmpty(req.getRefreshToken())) {
            throw new RuntimeException("Refresh token is missing");
        }
        try {
            String refresh_token = req.getRefreshToken();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String username = decodedJWT.getSubject();
            User user = userRepo.findByUsername(username);
            long expireIn = 10 * 60 * 1000;  // 10 minutes
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireIn))   // 10 minutes
                    .withIssuer(httpReq.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                    .sign(algorithm);
            SessionDto sessionDto = SessionDto.builder()
                    .expiresIn(expireIn)
                    .accessToken(access_token)
                    .refreshToken(refresh_token)
                    .user(mapper.toUserDto(user))
                    .build();
            return ResponseEntity.ok(new DataDto<>(sessionDto));
        } catch (Exception e) {
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(e.getMessage())), FORBIDDEN);
        }
    }
}

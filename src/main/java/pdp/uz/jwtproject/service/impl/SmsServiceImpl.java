package pdp.uz.jwtproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pdp.uz.jwtproject.helpers.Utils;
import pdp.uz.jwtproject.model.sms.LoginRequest;
import pdp.uz.jwtproject.model.sms.LoginResponse;
import pdp.uz.jwtproject.model.sms.SendMessageRequest;
import pdp.uz.jwtproject.service.SmsService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SmsServiceImpl implements SmsService {

    private final RestTemplate restTemplate5;
    private static String token;
    private static String email;
    private static String password;

    @Value("${sms.auth.login.url}")
    private String loginURL;
    @Value("${sms.send.message.url}")
    private String sendMessageURL;

    @Autowired
    public SmsServiceImpl(@Qualifier(value = "restTemplate5") RestTemplate restTemplate5) {
        this.restTemplate5 = restTemplate5;
        token = "";
        email = "";  // todo set email
        password = "";  // todo set password
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        tokenGenerate();
        if (Objects.nonNull(token)) {
            headers.set("Authorization", "Bearer " + token);
        }
        return headers;
    }

    private void tokenGenerate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(new LoginRequest(email, password), headers);

        ResponseEntity<LoginResponse> response = restTemplate5.exchange(
                loginURL,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<LoginResponse>() {
                }
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LoginResponse loginResponse = response.getBody();
            if (!Utils.isEmpty(loginResponse)) {
                if (!Utils.isEmpty(loginResponse.getData())) {
                    token = loginResponse.getData().getToken();
                }
            }
        }
    }

    @Override
    public boolean sendMessage(String phone, String text) {
        if (Utils.isEmpty(phone) || Utils.isEmpty(text)) {
            return false;
        }
        SendMessageRequest request = new SendMessageRequest(text, phone, "4546");
        ResponseEntity<String> responseEntity = restTemplate5.exchange(
                sendMessageURL,
                HttpMethod.POST,
                new HttpEntity<>(request, getHeaders()),
                String.class
        );
        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}

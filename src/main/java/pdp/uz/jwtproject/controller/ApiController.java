package pdp.uz.jwtproject.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public abstract class ApiController {

    public static final String API_PATH = "/api";
    public static final String PUBLIC = "/public";
    public static final String AUTH = API_PATH + "/auth";

    public static final String LOGIN_URL = AUTH + "/login";
    public static final String REFRESH_TOKEN_URL = AUTH + "/refresh-token";
}

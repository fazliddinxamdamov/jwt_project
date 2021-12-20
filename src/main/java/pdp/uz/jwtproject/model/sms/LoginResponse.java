package pdp.uz.jwtproject.model.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginResponse implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private LoginDto data;

}

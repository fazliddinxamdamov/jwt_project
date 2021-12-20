package pdp.uz.jwtproject.model.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("mobile_phone")
    private String mobilePhone;

    @JsonProperty("from")
    private String from;
}

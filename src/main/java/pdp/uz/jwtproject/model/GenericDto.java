package pdp.uz.jwtproject.model;

import com.google.gson.Gson;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericDto implements Dto, Serializable {

    private Long id;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

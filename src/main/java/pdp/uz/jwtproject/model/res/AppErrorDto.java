package pdp.uz.jwtproject.model.res;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppErrorDto implements Serializable {
    private String errorMessage;
}

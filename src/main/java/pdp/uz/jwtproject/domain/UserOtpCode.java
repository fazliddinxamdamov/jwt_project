package pdp.uz.jwtproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pdp.uz.jwtproject.enums.OtpEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_user_otp_code")
public class UserOtpCode {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "phone")
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OtpEnum status = OtpEnum.ACTIVE;

    @Column(name = "send_date", columnDefinition = "timestamp default now()")
    private LocalDateTime sendDate = LocalDateTime.now();
}

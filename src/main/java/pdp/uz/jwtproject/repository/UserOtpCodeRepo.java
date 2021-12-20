package pdp.uz.jwtproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdp.uz.jwtproject.domain.UserOtpCode;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserOtpCodeRepo extends JpaRepository<UserOtpCode, Long> {

    @Query(value = "select * from auth_user_otp_code t " +
            " where t.phone = :phone " +
            " and t.code = :code " +
            " and t.status = 'ACTIVE' ", nativeQuery = true)
    UserOtpCode findByPhoneAndCode(String phone, String code);

    @Query(value = "select * from auth_user_otp_code t " +
            " where t.status = 'ACTIVE' and t.send_date < :dateTime " , nativeQuery = true)
    List<UserOtpCode> findAllActives(LocalDateTime dateTime);
}

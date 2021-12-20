package pdp.uz.jwtproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pdp.uz.jwtproject.domain.UserOtpCode;
import pdp.uz.jwtproject.enums.OtpEnum;
import pdp.uz.jwtproject.repository.UserOtpCodeRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BackgroundService {

    private final UserOtpCodeRepo userOtpCodeRepo;

    // every 1 minute
    @Scheduled(initialDelay = 1000, fixedDelay = 60 * 1000)
    public void checkAndInActiveCodes() {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(5L);
        List<UserOtpCode> list = userOtpCodeRepo.findAllActives(dateTime);
        list.forEach(userOtpCode -> {
            userOtpCode.setStatus(OtpEnum.IN_ACTIVE);
            userOtpCodeRepo.save(userOtpCode);
        });
    }
}

package pdp.uz.jwtproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.helpers.UserSession;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Autowired
    private UserSession userSession;

    @Override
    public Optional<Long> getCurrentAuditor() {
        User user = userSession.getUser();
        if (Objects.nonNull(user)) {
            return Optional.of(user.getId());
        }
        return Optional.empty();
    }
}

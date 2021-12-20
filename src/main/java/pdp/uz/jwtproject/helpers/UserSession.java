package pdp.uz.jwtproject.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.repository.UserRepo;

@Component
public class UserSession {

    @Autowired
    private UserRepo userRepo;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getPrincipal().toString();
            return userRepo.findByUsername(username);
        } else {
            return null;
        }
    }
}

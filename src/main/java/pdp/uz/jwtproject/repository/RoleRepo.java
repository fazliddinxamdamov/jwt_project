package pdp.uz.jwtproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdp.uz.jwtproject.domain.Role;

@Repository(value = "roleRepository")
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

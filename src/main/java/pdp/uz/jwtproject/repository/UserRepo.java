package pdp.uz.jwtproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pdp.uz.jwtproject.domain.User;

import java.util.List;

@Repository(value = "userRepository")
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select * from auth_users t where t.phone = :phone", nativeQuery = true)
    User findByPhone(String phone);

    @Query(value = "select * from auth_users t where t.email <> :email", nativeQuery = true)
    List<User> findByEmailNot(@Param(value = "email") String emailSubscribe);
}

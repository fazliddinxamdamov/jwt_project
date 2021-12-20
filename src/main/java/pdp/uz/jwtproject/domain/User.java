package pdp.uz.jwtproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_users")
@Where(clause = "active = true")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "active", columnDefinition = "boolean default false")
    private Boolean active;

    // CascadeType.ALL => {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @OrderBy(value = "id desc")
    private Collection<Role> roles = new ArrayList<>();
}

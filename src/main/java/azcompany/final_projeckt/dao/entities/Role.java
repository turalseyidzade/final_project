package azcompany.final_projeckt.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RoleName name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name.name();
    }

    public enum RoleName {
        USER,
        ADMIN
    }
}

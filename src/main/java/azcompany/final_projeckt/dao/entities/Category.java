package azcompany.final_projeckt.dao.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cartegories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    @Column(nullable = false)

    private boolean isDeleted = false;
}

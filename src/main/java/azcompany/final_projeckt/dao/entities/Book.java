package azcompany.final_projeckt.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@SoftDelete(columnName = "is_deleted")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String author;

    @NonNull
    @Column(unique = true)
    private String isbn;

    @NonNull
    private BigDecimal price;

    @Column(unique = false)
    private Integer stockQuantity = 0;

    private String description;
    private String coverImage;

    @ManyToMany
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @Column(nullable = false)
    private boolean isDeleted = false;


}

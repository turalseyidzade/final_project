package azcompany.final_projeckt.dao.repositories;

import azcompany.final_projeckt.dao.entities.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<@NonNull Category, @NonNull Long> {


}

package azcompany.final_projeckt.dao.repositories;

import azcompany.final_projeckt.dao.entities.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.shoppingCart")
    Page<CartItem> findAll(Pageable pageable);

    @Query("SELECT ci FROM CartItem ci JOIN fetch ci.shoppingCart c WHERE c.id = :itemId")
    List<CartItem> findAllById(Long itemId);
}

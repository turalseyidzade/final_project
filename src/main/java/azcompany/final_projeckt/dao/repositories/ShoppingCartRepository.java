package azcompany.final_projeckt.dao.repositories;

import azcompany.final_projeckt.dao.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @Query("SELECT sc FROM ShoppingCart sc JOIN FETCH sc.cartItems ci WHERE sc.user.id = :userId")
    Optional<ShoppingCart> findByUserId(Long userId);
}

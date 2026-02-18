package azcompany.final_projeckt.dao.repositories;

import azcompany.final_projeckt.dao.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems is WHERE o.user.id = :id")
    List<Order> findAllById(Long id);
}

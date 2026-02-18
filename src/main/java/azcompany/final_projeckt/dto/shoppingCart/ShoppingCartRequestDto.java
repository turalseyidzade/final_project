package azcompany.final_projeckt.dto.shoppingCart;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartRequestDto {

    @Positive
    private Long bookId;
    @Positive
    private Integer quantity;
}

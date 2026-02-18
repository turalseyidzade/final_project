package azcompany.final_projeckt.dto.cartItem;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCartItemRequestDto {
    @Positive
    private Long bookId;
    @Positive
    private Integer quantity;
}

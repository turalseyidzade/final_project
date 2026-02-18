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
public class PutCartItemRequestDto {

    @Positive
    private Integer quantity;
}

package azcompany.final_projeckt.dto.shoppingCart;

import azcompany.final_projeckt.dto.cartItem.CartItemResponseDto;
import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CartItemResponseDto> cartItems;
}

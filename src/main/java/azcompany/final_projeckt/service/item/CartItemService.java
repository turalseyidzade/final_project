package azcompany.final_projeckt.service.item;

import azcompany.final_projeckt.dto.cartItem.CartItemResponseDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartRequestDto;
import azcompany.final_projeckt.dao.entities.CartItem;
import azcompany.final_projeckt.dao.entities.ShoppingCart;

public interface CartItemService {
    CartItemResponseDto updateById(Long id, int quantity);

    void deleteCartItem(Long id);

    CartItem addCartItem(ShoppingCart shoppingCart, ShoppingCartRequestDto requestDto);
}

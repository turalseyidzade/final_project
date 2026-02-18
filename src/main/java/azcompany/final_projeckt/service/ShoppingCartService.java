package azcompany.final_projeckt.service;

import azcompany.final_projeckt.dto.cartItem.PutCartItemRequestDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartRequestDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartResponseDto;
import azcompany.final_projeckt.dao.entities.User;

public interface ShoppingCartService {

    ShoppingCartResponseDto findByUserId(Long id);

    void createShoppingCart(User user);

    ShoppingCartResponseDto addToShoppingCart(Long userId,
                                              ShoppingCartRequestDto requestDto);

    ShoppingCartResponseDto updateByCartId(
            Long userId,
            Long id,
            PutCartItemRequestDto requestDto
    );
}

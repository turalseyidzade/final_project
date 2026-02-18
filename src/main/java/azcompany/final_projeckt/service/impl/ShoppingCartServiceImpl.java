package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.repositories.ShoppingCartRepository;
import azcompany.final_projeckt.dto.cartItem.PutCartItemRequestDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartRequestDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartResponseDto;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.mapper.ShoppingCartMapper;
import azcompany.final_projeckt.dao.entities.CartItem;
import azcompany.final_projeckt.dao.entities.ShoppingCart;
import azcompany.final_projeckt.dao.entities.User;
import azcompany.final_projeckt.service.item.CartItemService;
import azcompany.final_projeckt.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CartItemService cartItemService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto findByUserId(Long id) {
        return shoppingCartMapper.toDto(shoppingCartRepository
                .findByUserId(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find"
                                + " shopping cart by user's id: " + id)
                ));
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addToShoppingCart(Long userId,
                                                     ShoppingCartRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find"
                                + " shopping cart by user's id: " + userId)
                );
        CartItem cartItem = cartItemService.addCartItem(shoppingCart, requestDto);
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateByCartId(
            Long userId,
            Long id,
            PutCartItemRequestDto requestDto
    ) {
        cartItemService.updateById(id, requestDto.getQuantity());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find"
                                + " shopping cart by user's id: " + userId)
                );
        return shoppingCartMapper.toDto(shoppingCart);
    }
}

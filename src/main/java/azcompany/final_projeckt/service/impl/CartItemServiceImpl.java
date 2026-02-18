package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.repositories.BookRepository;
import azcompany.final_projeckt.dao.repositories.CartItemRepository;
import azcompany.final_projeckt.dto.cartItem.CartItemResponseDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartRequestDto;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.mapper.CartItemMapper;
import azcompany.final_projeckt.dao.entities.CartItem;
import azcompany.final_projeckt.dao.entities.ShoppingCart;
import azcompany.final_projeckt.service.item.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public CartItemResponseDto updateById(Long id, int quantity) {
        CartItem cartItemById = getById(id);
        cartItemById.setQuantity(quantity);
        return cartItemMapper.toDto(cartItemRepository.save(cartItemById));
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.delete(getById(id));
    }

    @Override
    public CartItem addCartItem(ShoppingCart shoppingCart, ShoppingCartRequestDto requestDto) {
        CartItem cartItem = cartItemMapper.toCartItem(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        Long bookId = requestDto.getBookId();
        cartItem.setBook(bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + bookId)
        ));
        return cartItemRepository.save(cartItem);
    }

    private CartItem getById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find item by id: " + cartItemId)
        );
    }
}

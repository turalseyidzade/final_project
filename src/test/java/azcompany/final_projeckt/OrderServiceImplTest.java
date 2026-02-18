package azcompany.final_projeckt;

import azcompany.final_projeckt.dao.entities.*;
import azcompany.final_projeckt.dao.repositories.*;
import azcompany.final_projeckt.dto.order.OrderRequestDto;
import azcompany.final_projeckt.exceptions.OutOfStockException;
import azcompany.final_projeckt.mapper.OrderMapper;
import azcompany.final_projeckt.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Should throw OutOfStockException when stock is insufficient")
    void placeOrder_InsufficientStock_ThrowsException() {

        Long userId = 1L;
        Book book = new Book();
        book.setTitle("Java Masterclass");
        book.setStockQuantity(2);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(5);

        ShoppingCart cart = new ShoppingCart();
        cart.setCartItems(Set.of(cartItem));

        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));


        assertThrows(OutOfStockException.class, () -> {
            orderService.placeOrder(userId, new OrderRequestDto());
        });

        verify(orderRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should place order successfully when stock is sufficient")
    void placeOrder_Successful() {

        Long userId = 1L;
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(100));
        book.setStockQuantity(10);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(2);

        ShoppingCart cart = new ShoppingCart();
        cart.setCartItems(Set.of(cartItem));

        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setShippingAddress("Baku, Azerbaijan");

        orderService.placeOrder(userId, requestDto);

        assertEquals(8, book.getStockQuantity());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartItemRepository, times(1)).deleteAll(any());
    }
}


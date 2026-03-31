package azcompany.final_projeckt.service;

import azcompany.final_projeckt.dao.entities.*;
import azcompany.final_projeckt.dao.repositories.*;
import azcompany.final_projeckt.dto.order.OrderRequestDto;
import azcompany.final_projeckt.dto.order.OrderResponseDto;
import azcompany.final_projeckt.dto.order.UpdateOrderRequestDto;
import azcompany.final_projeckt.enums.OrderStatus;
import azcompany.final_projeckt.exceptions.InsufficientBalanceException;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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
    @Mock
    private UserRepository userRepository;

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
        verify(orderItemRepository, times(1)).saveAll(any());

    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException when balance is not enough")
    void placeOrder_InsufficientBalance() {

        Long userId = 1L;

        User user = new User();
        user.setBalance(BigDecimal.valueOf(50));

        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(100));
        book.setStockQuantity(10);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(1);

        ShoppingCart cart = new ShoppingCart();
        cart.setCartItems(Set.of(cartItem));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        assertThrows(InsufficientBalanceException.class, () -> {
            orderService.placeOrder(userId, new OrderRequestDto());
        });

        verify(orderRepository, never()).save(any());
    }

    @Test
    void updateStatus_success() {
        Long orderId = 1L;

        Order order = new Order();
        order.setId(orderId);

        UpdateOrderRequestDto dto = new UpdateOrderRequestDto();
        dto.setStatus(OrderStatus.COMPLETED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(new OrderResponseDto());

        OrderResponseDto result = orderService.updateStatus(orderId, dto);

        assertNotNull(result);
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void placeOrder_EmptyCart_ThrowsException() {
        Long userId = 1L;

        ShoppingCart cart = new ShoppingCart();
        cart.setCartItems(Set.of());

        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(userId, new OrderRequestDto());
        });
    }

    @Test
    void placeOrder_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(userId, new OrderRequestDto());
        });
    }
}


package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.entities.*;
import azcompany.final_projeckt.dao.repositories.*;
import azcompany.final_projeckt.dto.order.OrderRequestDto;
import azcompany.final_projeckt.dto.order.OrderResponseDto;
import azcompany.final_projeckt.dto.order.UpdateOrderRequestDto;
import azcompany.final_projeckt.enums.OrderStatus;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.exceptions.InsufficientBalanceException;
import azcompany.final_projeckt.exceptions.OutOfStockException;
import azcompany.final_projeckt.mapper.OrderMapper;
import azcompany.final_projeckt.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(Long id, OrderRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("ShoppingCart not found"));

        BigDecimal totalAmount = shoppingCart.getCartItems().stream()
                .map(item-> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (user.getBalance().compareTo(totalAmount)< 0){
            throw new InsufficientBalanceException("Not enough money! Required: " + totalAmount + ", Available: " + user.getBalance());
        }
        for (CartItem item : shoppingCart.getCartItems()) {
            Book book = item.getBook();
            if (book.getStockQuantity()< item.getQuantity()){
                throw new OutOfStockException("Insufficient stock for: " + book.getTitle());
            }
            book.setStockQuantity(book.getStockQuantity()- item.getQuantity());
        }
        user.setBalance(user.getBalance().subtract(totalAmount));
        userRepository.save(user);

        Order order = createOrder(id, shoppingCart, requestDto);
        order.setTotal(totalAmount);
        Order savedOrder = orderRepository.save(order);

        processorOrderItems(shoppingCart, savedOrder);

        cartItemRepository.deleteAll(shoppingCart.getCartItems());

        return orderMapper.toDto(savedOrder);

    }

    private void processorOrderItems(ShoppingCart shoppingCart, Order order) {
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toSet());

        orderItemRepository.saveAll(orderItems);
    }
    @Override
    public List<OrderResponseDto> getAll(Long id) {
        List<Order> orderList = orderRepository.findAllById(id);
        return orderList.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto updateStatus(Long id, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id: " + id)
        );
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    private Order createOrder(Long id, ShoppingCart shoppingCart, OrderRequestDto requestDto) {
        Order order = new Order();
        User user = new User();
        user.setId(id);
        order.setUser(user);
        order.setStatus(OrderStatus.PROCESSING);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(
                shoppingCart.getCartItems().stream()
                        .map(ci -> ci.getBook()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(ci.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private Set<OrderItem> getOrderItems(ShoppingCart shoppingCart) {
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        return cartItems.stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toSet());

    }

    private OrderItem convertToOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }
}

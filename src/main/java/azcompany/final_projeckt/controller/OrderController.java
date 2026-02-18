
package azcompany.final_projeckt.controller;
import azcompany.final_projeckt.dto.order.OrderRequestDto;
import azcompany.final_projeckt.dto.order.OrderResponseDto;
import azcompany.final_projeckt.dto.order.UpdateOrderRequestDto;
import azcompany.final_projeckt.dto.order.OrderItemResponseDto;
import azcompany.final_projeckt.dao.entities.User;
import azcompany.final_projeckt.service.OrderService;
import azcompany.final_projeckt.service.item.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@Tag(name = "Order management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Place order", description = "Place order")
    public OrderResponseDto placeOrder(
            Authentication authentication,
            @RequestBody OrderRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get all orders", description = "Return all orders")
    public List<OrderResponseDto> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAll(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Change order status", description = "Change order status")
    public OrderResponseDto updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderRequestDto requestDto) {
        return orderService.updateStatus(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/items")
    @Operation(
            summary = "Get all items by order id",
            description = "Retrieve all OrderItems for a specific order"
    )
    public List<OrderItemResponseDto> getAllById(@PathVariable Long id) {
        return orderItemService.getAllById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/items/{itemId}")
    @Operation(
            summary = "Get item from order by id",
            description = "Retrieve specific OrderItem from a specific order"
    )
    public OrderItemResponseDto getItemById(@PathVariable Long id, @PathVariable Long itemId) {
        return orderItemService.getItemById(id, itemId);
    }
}


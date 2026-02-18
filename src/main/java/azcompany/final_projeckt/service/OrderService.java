package azcompany.final_projeckt.service;

import azcompany.final_projeckt.dto.order.OrderRequestDto;
import azcompany.final_projeckt.dto.order.OrderResponseDto;
import azcompany.final_projeckt.dto.order.UpdateOrderRequestDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(Long id, OrderRequestDto requestDto);

    List<OrderResponseDto> getAll(Long id);

    OrderResponseDto updateStatus(Long id, UpdateOrderRequestDto requestDto);
}

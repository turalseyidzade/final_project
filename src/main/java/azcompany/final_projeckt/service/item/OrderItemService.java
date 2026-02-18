package azcompany.final_projeckt.service.item;

import azcompany.final_projeckt.dto.order.OrderItemResponseDto;

import java.util.List;

public interface OrderItemService {

    List<OrderItemResponseDto> getAllById(Long id);

    OrderItemResponseDto getItemById(Long id, Long itemId);
}

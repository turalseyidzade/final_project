package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.repositories.OrderItemRepository;
import azcompany.final_projeckt.dto.order.OrderItemResponseDto;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.mapper.OrderItemMapper;
import azcompany.final_projeckt.dao.entities.OrderItem;
import azcompany.final_projeckt.service.item.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponseDto> getAllById(Long id) {
        List<OrderItem> items = orderItemRepository.findAllById(id);
        return items.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getItemById(Long id, Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(id, itemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find item by id: " + id)
        );
        return orderItemMapper.toDto(orderItem);
    }
}

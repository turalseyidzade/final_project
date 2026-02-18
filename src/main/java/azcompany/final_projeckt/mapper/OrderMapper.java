package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.config.MapperConfig;
import azcompany.final_projeckt.dto.order.OrderRequestDto;
import azcompany.final_projeckt.dto.order.OrderResponseDto;
import azcompany.final_projeckt.dao.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config =  MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);

    Order toOrder(OrderRequestDto requestDto);
}

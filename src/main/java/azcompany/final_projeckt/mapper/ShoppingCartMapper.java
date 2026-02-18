package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.config.MapperConfig;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartRequestDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartResponseDto;
import azcompany.final_projeckt.dao.entities.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toShoppingCart(ShoppingCartRequestDto requestDto);
}

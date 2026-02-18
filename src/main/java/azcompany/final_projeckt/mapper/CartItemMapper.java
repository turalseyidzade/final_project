package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.config.MapperConfig;
import azcompany.final_projeckt.dto.cartItem.CartItemResponseDto;
import azcompany.final_projeckt.dto.cartItem.PutCartItemRequestDto;
import azcompany.final_projeckt.dto.shoppingCart.ShoppingCartRequestDto;
import azcompany.final_projeckt.dao.entities.Book;
import azcompany.final_projeckt.dao.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    CartItem toCartItem(PutCartItemRequestDto requestDto);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookFromId")
    CartItem toCartItem(ShoppingCartRequestDto requestDto);

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}

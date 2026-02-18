package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.config.MapperConfig;
import azcompany.final_projeckt.dto.book.BookDto;
import azcompany.final_projeckt.dto.book.BookDtoWithoutCategoryIds;
import azcompany.final_projeckt.dto.book.CreateBookRequestDto;
import azcompany.final_projeckt.dao.entities.Book;
import azcompany.final_projeckt.dao.entities.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config =  MapperConfig.class)
public interface BookMapper {

    BookDto toDto(Book book);
    Book toBook(CreateBookRequestDto bookRequestDto);
    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
    void updateBookFromDto(CreateBookRequestDto bookRequestDto, @MappingTarget Book entity);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            Set<Long> categories = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            bookDto.setCategoryIds(categories);
        }
    }
}

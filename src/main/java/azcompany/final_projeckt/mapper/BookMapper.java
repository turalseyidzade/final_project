package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.dao.entities.Book;
import azcompany.final_projeckt.dao.entities.Category;
import azcompany.final_projeckt.dto.book.BookDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import azcompany.final_projeckt.dto.book.BookDtoWithoutCategoryIds;
import azcompany.final_projeckt.dto.book.CreateBookRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toBook(CreateBookRequestDto dto);

    @Mapping(target = "categoryIds", expression = "java(mapCategoriesToIds(book.getCategories()))")
    BookDto toDto(Book book);

    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    default List<Long> mapCategoriesToIds(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream().map(Category::getId).toList();
    }
}

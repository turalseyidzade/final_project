package azcompany.final_projeckt.service;

import azcompany.final_projeckt.dto.book.BookDto;
import azcompany.final_projeckt.dto.book.BookDtoWithoutCategoryIds;
import azcompany.final_projeckt.dto.book.BookSearchParametersDto;
import azcompany.final_projeckt.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    void deleteBookById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto bookDto);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);

    List<BookDto> search(BookSearchParametersDto bookSearchParameters, Pageable pageable);

}

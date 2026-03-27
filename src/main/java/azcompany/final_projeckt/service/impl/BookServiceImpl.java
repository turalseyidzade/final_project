package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.entities.Category;
import azcompany.final_projeckt.dao.repositories.BookRepository;
import azcompany.final_projeckt.dao.repositories.CategoryRepository;
import azcompany.final_projeckt.dao.repositories.specification.BookSpecificationBuilder;
import azcompany.final_projeckt.dto.book.BookDto;
import azcompany.final_projeckt.dto.book.BookDtoWithoutCategoryIds;
import azcompany.final_projeckt.dto.book.BookSearchParametersDto;
import azcompany.final_projeckt.dto.book.CreateBookRequestDto;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.mapper.BookMapper;
import azcompany.final_projeckt.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import azcompany.final_projeckt.dao.entities.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public BookDto save(CreateBookRequestDto requestDto) {
        Set<Category> categories = new HashSet<>();
        if (requestDto.getCategoryIds() != null) {
            categories = categoryRepository.findAllById(requestDto.getCategoryIds())
                    .stream()
                    .collect(Collectors.toSet());
        }
        Book book = bookMapper.toBook(requestDto);
        book.setCategories(categories);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find book by id: " + id));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
       if (!bookRepository.existsById(id)) {
           throw new EntityNotFoundException("Can`t find book by id: " + id);
       }
       bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookDto updateBookById(Long id, CreateBookRequestDto bookDto) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id: " + id));
        bookMapper.updateBookFromDto(bookDto, existingBook);
        Set<Category> categories = new HashSet<>();

        if (bookDto.getCategoryIds() != null && !bookDto.getCategoryIds().isEmpty()) {
            categories = new HashSet<>(
                    categoryRepository.findAllById(bookDto.getCategoryIds())
            );
        }
        existingBook.setCategories(categories);

        return bookMapper.toDto(bookRepository.save(existingBook));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable){
        var spec = bookSpecificationBuilder.buildSpecification(searchParameters);

        return bookRepository.findAll(spec,pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

}

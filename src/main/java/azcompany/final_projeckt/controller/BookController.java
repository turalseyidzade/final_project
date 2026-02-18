package azcompany.final_projeckt.controller;

import azcompany.final_projeckt.dto.book.BookDto;
import azcompany.final_projeckt.dto.book.BookSearchParametersDto;
import azcompany.final_projeckt.dto.book.CreateBookRequestDto;
import azcompany.final_projeckt.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
@Tag(name = "Book management", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get products", description = "Get all available products")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get product", description = "Get product by id")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete product by id")
    public void delete(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update product by id")
    public BookDto update(@PathVariable Long id,
                          @RequestBody CreateBookRequestDto bookDto) {
        return bookService.updateBookById(id, bookDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters, Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}

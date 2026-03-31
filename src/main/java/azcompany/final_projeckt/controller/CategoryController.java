
package azcompany.final_projeckt.controller;
import azcompany.final_projeckt.dto.book.BookDtoWithoutCategoryIds;
import azcompany.final_projeckt.dto.category.CategoryResponseDto;
import azcompany.final_projeckt.dto.category.CreateCategoryRequestDto;
import azcompany.final_projeckt.service.BookService;
import azcompany.final_projeckt.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@Tag(name = "Category management", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Create category", description = "Create a new category")
    public CategoryResponseDto createCategory(
            @RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get all categories", description = "Get all categories with pagination and sorting")
    public Page<CategoryResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams.length > 1 ? sortParams[1] : "asc");
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));

        return categoryService.findAllPaged(pageRequest);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get category", description = "Get category by id")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update category by id")
    public CategoryResponseDto updateCategoryById(
            @PathVariable Long id,
            @RequestBody @Valid CreateCategoryRequestDto requestDto
    ) {
        return categoryService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete category by id")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}/books")
    @Operation(summary = "Get books", description = "Get books by category id")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id) {
        return bookService.getBooksByCategoryId(id);
    }
}


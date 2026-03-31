package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.repositories.CategoryRepository;
import azcompany.final_projeckt.dto.category.CategoryResponseDto;
import azcompany.final_projeckt.dto.category.CreateCategoryRequestDto;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.mapper.CategoryMapper;
import azcompany.final_projeckt.dao.entities.Category;
import azcompany.final_projeckt.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponseDto> findAllPaged(Pageable pageable) {
        if (pageable == null || !pageable.getSort().isSorted()) {
            pageable = PageRequest.of(
                    pageable != null ? pageable.getPageNumber() : 0,
                    pageable != null ? pageable.getPageSize() : 20,
                    Sort.by("name")
            );
        }

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find category by id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toCategory(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find category by id: " + id));
        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find category by id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}

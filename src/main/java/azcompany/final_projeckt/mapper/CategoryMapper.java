package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.config.MapperConfig;
import azcompany.final_projeckt.dto.category.CategoryResponseDto;
import azcompany.final_projeckt.dto.category.CreateCategoryRequestDto;
import azcompany.final_projeckt.dao.entities.Category;
import org.mapstruct.Mapper;

@Mapper(config =  MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toCategory(CreateCategoryRequestDto requestDto);
}

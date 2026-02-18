package azcompany.final_projeckt.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto extends BookDtoWithoutCategoryIds {
    private Set<Long> categoryIds;
}

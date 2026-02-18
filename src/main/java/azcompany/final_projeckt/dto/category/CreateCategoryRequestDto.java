package azcompany.final_projeckt.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequestDto {

    @NotNull
    @Length(min = 4, max = 20, message = "length should be between 4 and 20")
    private String name;

    private String description;
}

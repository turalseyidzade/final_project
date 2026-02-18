package azcompany.final_projeckt.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @Length(min = 4, max = 60, message = "can't be less than 4 and more than 60 letters")
    private String shippingAddress;
}

package azcompany.final_projeckt.dto.order;
import azcompany.final_projeckt.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderRequestDto {
    private OrderStatus status;
}

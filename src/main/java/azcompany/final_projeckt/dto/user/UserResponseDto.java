package azcompany.final_projeckt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto{
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}

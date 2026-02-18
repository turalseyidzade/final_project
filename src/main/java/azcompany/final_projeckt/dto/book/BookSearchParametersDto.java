package azcompany.final_projeckt.dto.book;

import java.math.BigDecimal;

public record BookSearchParametersDto(
        String title,
        String author,
        BigDecimal priceFrom,
        BigDecimal priceTo
) {
}

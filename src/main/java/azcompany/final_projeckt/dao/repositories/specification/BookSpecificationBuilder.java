package azcompany.final_projeckt.dao.repositories.specification;

import azcompany.final_projeckt.dao.entities.Book;
import azcompany.final_projeckt.dto.book.BookSearchParametersDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookSpecificationBuilder {

    public Specification<Book> buildSpecification(BookSearchParametersDto searchParameters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParameters.title() != null && !searchParameters.title().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), "%" + searchParameters.title().toLowerCase() + "%"));
            }

            if (searchParameters.author() != null && !searchParameters.author().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")), "%" + searchParameters.author().toLowerCase() + "%"));
            }

            if (searchParameters.priceFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchParameters.priceFrom()));
            }

            if (searchParameters.priceTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchParameters.priceTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

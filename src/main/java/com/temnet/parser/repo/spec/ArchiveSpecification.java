package com.temnet.parser.repo.spec;

import com.temnet.parser.domain.Archive;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Archive specification helps us to create dynamic queries based on the requirement at run time.
 * Spring Data Jpa Specifications allows a combination of the attributes or properties of a domain or entity class and creates a query
 *
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.specification-predicates">Spring Data Jpa Specifications</a>
 */
public class ArchiveSpecification {

    /**
     * Creates a specification for the Archive entity based on the given parameters.
     *
     * @param messageParts the message parts
     * @return the Archive specification object
     */
    public static Specification<Archive> txtContainsAny(Collection<String> messageParts) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            messageParts.forEach(txtPart -> predicate.getExpressions().add(cb.like(root.get("txt"), "%" + txtPart + "%")));
            return predicate;
        };
    }

    /**
     * Creates a specification for the Archive entity based on the given parameters.
     *
     * @param username     the user name
     * @param from         the start date
     * @param to           the end date
     * @param messageParts the message parts
     * @return the Archive specification object
     */
    public static Specification<Archive> usernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(String username, Timestamp from, Timestamp to, Collection<String> messageParts) {
        return (root, query, cb) ->
        {
            Predicate name = username.contains("help") ? cb.notLike(root.get("username"), "% %") : cb.notLike(root.get("username"), "%help%");
            return cb.and(cb.like(root.get("username"), "%" + username + "%"),
                    name,
                    cb.greaterThanOrEqualTo(root.get("createdAt"), from),
                    cb.lessThanOrEqualTo(root.get("createdAt"), to),
                    txtContainsAny(messageParts).toPredicate(root, query, cb));
        };
    }

    /**
     * Creates a specification for the Archive entity based on the given parameters.
     *
     * @param from         the start date
     * @param to           the end date
     * @param messageParts the message parts
     * @return the Archive specification object
     */
    public static Specification<Archive> createdAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(Timestamp from, Timestamp to, Collection<String> messageParts) {
        return (root, query, cb) ->
                cb.and(cb.greaterThanOrEqualTo(root.get("createdAt"), from),
                        cb.notLike(root.get("username"), "%help%"),
                        cb.lessThanOrEqualTo(root.get("createdAt"), to),
                        txtContainsAny(messageParts).toPredicate(root, query, cb));

    }
}

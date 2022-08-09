package com.temnet.parser.repo;

import com.temnet.parser.domain.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * JPA Repository for Archive entity providing basic operations for accessing database data
 *
 * @author Temnet
 */
@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    /**
     * Finds all archives rows by method parameters
     *
     * @param username   user name
     * @param createdAt  search start date
     * @param createdAt2 search end date
     * @param txt        message text
     * @param pageable   pagination parameters
     * @return pageable list of archives rows
     */
    @Query(value = "SELECT * FROM archive where username like concat ('%', :username, '%') and created_at between :createdAt and :createdAt2 and match (txt) against (:txt IN BOOLEAN MODE)", nativeQuery = true)
    Page<Archive> findByUsernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(String username, Timestamp createdAt, Timestamp createdAt2, String txt, Pageable pageable);

    /**
     * Finds count of all archives rows by method parameters
     *
     * @param createdAt  search start date
     * @param createdAt2 search end date
     * @param txt        message text
     * @return count of all archives rows by method parameters
     */
    Long countArchiveByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(Timestamp createdAt, Timestamp createdAt2, String txt);


}

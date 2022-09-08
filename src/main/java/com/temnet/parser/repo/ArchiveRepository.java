package com.temnet.parser.repo;

import com.temnet.parser.domain.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * JPA Repository for Archive entity providing basic operations for accessing database data
 *
 * @author Temnet
 */
@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long>, JpaSpecificationExecutor<Archive> {

    /**
     * Find all archives by custom criteria.
     *
     * @param spec Archive Specification
     * @return the list of archives
     */
    Page<Archive> findAll(Specification<Archive> spec, Pageable pageable);


    List<Archive> findAllByUsernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(String username, Timestamp createdAt, Timestamp createdAt2);

    Long countAllByUsernameContains(String username);

    @Query(value = "select distinct substring_index(username, '.', -1) from archive", nativeQuery = true)
    List<String> findGroups();

    List<Archive> findDistinctByUsernameContains(String username);

}

package com.temnet.parser.repo;

import com.temnet.parser.domain.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

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

}

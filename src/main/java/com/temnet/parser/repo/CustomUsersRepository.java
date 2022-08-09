package com.temnet.parser.repo;

import com.temnet.parser.domain.CustomUsers;
import com.temnet.parser.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for CustomUsers entity providing basic operations for accessing database data
 *
 * @author Temnet
 */
@Repository
public interface CustomUsersRepository extends JpaRepository<CustomUsers, Long> {
    /**
     * Finds all custom users rows by group
     *
     * @param group group name
     * @return list of custom users rows by group
     */
    List<CustomUsers> findAllByGroupOrderByUsernameAsc(Groups group);

    /**
     * Finds all custom users rows by id
     *
     * @param id user id
     * @return all custom users rows by id
     */
    CustomUsers findById(long id);

}

package com.temnet.parser.repo;

import com.temnet.parser.domain.CustomUsers;
import com.temnet.parser.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUsersRepository extends JpaRepository<CustomUsers, Long> {
    List<CustomUsers> findAllByGroupOrderByUsernameAsc(Groups groups);
    CustomUsers findById(long id);

}

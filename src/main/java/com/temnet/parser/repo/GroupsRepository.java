package com.temnet.parser.repo;

import com.temnet.parser.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {
     Groups findByIdOrderByNameAsc(long id);
}

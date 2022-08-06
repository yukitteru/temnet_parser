package com.temnet.parser.repo;

import com.temnet.parser.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups, Long> {
     Groups findByIdOrderByNameAsc(long id);
     Groups findAllByIdOrderByNameAsc(long id);
}

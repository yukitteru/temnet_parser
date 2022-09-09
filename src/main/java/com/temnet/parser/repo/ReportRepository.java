package com.temnet.parser.repo;

import com.temnet.parser.domain.Report;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {

    Page<Report> findAll(Pageable pageable);

    Page<Report> findAll(Specification<Report> spec, Pageable pageable);

    Page<Report> findDistinctByGroupname(String groupname, Pageable pageable);

    @Query(value = "select r.groupname, count(r2.username), count(r3.id) from report r " +
            "left join (select id, username, created_at from report where created_at between ?1 and ?2 group by username) r2 on r.id = r2.id " +
            "left join (select id, created_at from report where created_at between ?1 and ?2) r3 on r3.id = r.id " +
            "group by groupname", nativeQuery = true)
    @Cacheable("activeUsers")
    List<Object[]> findActiveUsers(Timestamp start, Timestamp end);

    @Query(value = "select r.username, count(r2.id), count(r3.id), count(r4.id), count(r.id) from report r\n" +
            "left join report r2 on r2.id = r.id and match(r2.message) against ('+ЗАКРЫТА +ЗАЯВКА' IN BOOLEAN MODE)\n" +
            "left join report r3 on r3.id = r.id and match(r3.message) against ('+ЗАЯВКА +ОТЛОЖЕНА' IN BOOLEAN MODE)\n" +
            "left join report r4 on r4.id = r.id and match(r4.message) against ('+ЗАЯВКА +ОТКЛОНЕНА' IN BOOLEAN MODE)\n" +
            "where r.created_at between ?2 and ?3\n" +
            "and r.groupname = ?1\n" +
            "group by r.username", nativeQuery = true)
    @Cacheable("detailsInfo")
    List<Object[]> findDetailInfo(String groupName, Timestamp start, Timestamp end);

}



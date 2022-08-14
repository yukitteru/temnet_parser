package com.temnet.parser.repo;

import com.temnet.parser.domain.SrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SrUserRepository extends JpaRepository<SrUser, Long> {

    /**
     * Find all users by group order by Jabber ID.
     *
     * @param grp the group
     * @return the list of users
     */
    List<SrUser> findByGrpOrderByJid(String grp);

    /**
     * Find all groups order by Group.
     *
     * @return the list of groups
     */
    @Query("select distinct u.grp from SrUser u order by u.grp")
    List<String> findAllGroups();
}

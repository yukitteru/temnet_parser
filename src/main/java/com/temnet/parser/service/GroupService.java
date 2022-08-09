package com.temnet.parser.service;

import com.temnet.parser.domain.CustomUsers;
import com.temnet.parser.repo.CustomUsersRepository;
import com.temnet.parser.repo.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service class for processing business logic related to the Groups entity
 * <p>
 * Please see the {@link CustomUsers} class for true identity
 *
 * @author Temnet
 */
@Service
@Transactional
public class GroupService {
    /**
     * @see GroupsRepository
     */
    private final GroupsRepository groupsRepository;

    @Autowired
    public GroupService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    /**
     * @see GroupsRepository#findByIdOrderByNameAsc(long)
     */
    String findById(long id) {
        return groupsRepository.findByIdOrderByNameAsc(id).getName();
    }
}

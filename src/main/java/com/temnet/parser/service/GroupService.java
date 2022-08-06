package com.temnet.parser.service;

import com.temnet.parser.repo.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupsRepository groupsRepository;

    @Autowired
    public GroupService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    String findById(long id) {
        return groupsRepository.findByIdOrderByNameAsc(id).getName();
    }
}

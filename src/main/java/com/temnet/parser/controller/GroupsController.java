package com.temnet.parser.controller;

import com.temnet.parser.domain.Groups;
import com.temnet.parser.repo.GroupsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/groups")
public class GroupsController extends AbstractController<Groups, GroupsRepository> {

    public GroupsController(GroupsRepository repo) {
        super(repo);
    }


}

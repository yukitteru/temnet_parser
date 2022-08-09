package com.temnet.parser.controller;

import com.temnet.parser.domain.Groups;
import com.temnet.parser.repo.GroupsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Groups Controller class using REST API to present data preprocessed by the corresponding service class*
 *
 * @author Temnet
 */
@RestController
@RequestMapping("api/groups")
public class GroupsController extends AbstractController<Groups, GroupsRepository> {

    public GroupsController(GroupsRepository repo) {
        super(repo);
    }


}

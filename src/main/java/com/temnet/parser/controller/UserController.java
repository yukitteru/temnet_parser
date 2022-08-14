package com.temnet.parser.controller;

import com.temnet.parser.domain.SrUser;
import com.temnet.parser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User Controller class using REST API to present data preprocessed by the corresponding service class*
 *
 * @author Temnet
 */
@RestController
@RequestMapping("/api")
public class UserController {
    /**
     * @see UserService
     */
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Find all users by group order by Jabber ID.
     * @return groups.json
     */
    @GetMapping("/groups")
    public List<String> findAllGroups() {
        return userService.findAllGroups();
    }

    /**
     * Find all users by group order by Jabber ID.
     * @param grp the group
     * @return users/{grp}.json
     */
    @GetMapping("users/{grp}")
    public List<String> findByGrp(@PathVariable String grp) {
        return userService.getUserData(grp);
    }
}

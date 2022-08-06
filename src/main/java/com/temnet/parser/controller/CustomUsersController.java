package com.temnet.parser.controller;

import com.temnet.parser.domain.CustomUsers;
import com.temnet.parser.domain.Groups;
import com.temnet.parser.repo.CustomUsersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class CustomUsersController extends AbstractController<CustomUsers, CustomUsersRepository> {

    public CustomUsersController(CustomUsersRepository repo) {
        super(repo);
    }

    @GetMapping("{group}")
    public List<CustomUsers> findAllByGroupO(@PathVariable Groups group) {
        return repo.findAllByGroupOrderByUsernameAsc(group);
    }
}

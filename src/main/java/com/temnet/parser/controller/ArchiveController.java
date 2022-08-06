package com.temnet.parser.controller;

import com.temnet.parser.domain.*;
import com.temnet.parser.repo.ArchiveRepository;
import com.temnet.parser.repo.CustomUsersRepository;
import com.temnet.parser.repo.GroupsRepository;
import com.temnet.parser.service.ArchiveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/archive")
public class ArchiveController extends AbstractController<Archive, ArchiveRepository> {
    private final ArchiveService archiveService;
    private final CustomUsersRepository customUsersRepository;
    private final GroupsRepository groupsRepository;

    public ArchiveController(ArchiveRepository repo, ArchiveService archiveService, CustomUsersRepository customUsersRepository, GroupsRepository groupsRepository) {
        super(repo);
        this.archiveService = archiveService;
        this.customUsersRepository = customUsersRepository;
        this.groupsRepository = groupsRepository;
    }

    @GetMapping( "/search/{gid}/{uid}/{from}/{to}/{status}")
    public Page<Report> getDataByStatus(@PathVariable Long gid, @PathVariable Long uid, @PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable Status status, @PageableDefault(size = 15) Pageable pageable) {
        return archiveService.getDataByStatus(gid, uid, from, to, status, pageable);
    }

    @GetMapping("/users/{id}")
    public List<String> getUsersByGroup(@PathVariable Long id) {
        List<CustomUsers> allByGroupsByGroupsId = customUsersRepository.findAllByGroupOrderByUsernameAsc(groupsRepository.findByIdOrderByNameAsc(id));
        return allByGroupsByGroupsId.stream().map(CustomUsers::getUsername).collect(Collectors.toList());
    }


}

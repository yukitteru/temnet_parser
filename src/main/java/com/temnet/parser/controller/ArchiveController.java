package com.temnet.parser.controller;

import com.temnet.parser.domain.Archive;
import com.temnet.parser.domain.Report;
import com.temnet.parser.domain.Status;
import com.temnet.parser.repo.ArchiveRepository;
import com.temnet.parser.service.ArchiveService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController extends AbstractController<Archive, ArchiveRepository> {
    private final ArchiveService archiveService;

    public ArchiveController(ArchiveRepository repo, ArchiveService archiveService) {
        super(repo);
        this.archiveService = archiveService;
    }

    @GetMapping("/search/{username}/{from}/{to}/{status}")
    public Page<Report> getOrgData(@PathVariable String username, @PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable Status status, @PageableDefault(size = 15) Pageable pageable) {
        return archiveService.getOrgData(username, from, to, status, pageable);
    }
    @GetMapping("/search/all/{from}/{to}/{status}")
    public Page<Report> getAll(@PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable Status status, @PageableDefault(size = 15) Pageable pageable) {
        return archiveService.getAllData(from, to, status, pageable);
    }

}

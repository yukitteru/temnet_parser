package com.temnet.parser.controller;

import com.temnet.parser.domain.Report;
import com.temnet.parser.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Archive Controller class using REST API to present data preprocessed by the corresponding service class*
 *
 * @author Temnet
 */
@RestController
@RequestMapping("/api/archive")
public class ArchiveController {
    private final ArchiveService archiveService;

    /**
     * @param archiveService message archive service class used to process API business logic
     * @see ArchiveService
     */
    @Autowired
    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }


    /**
     * @see ArchiveService#getDataByCustomMessage(String, String, Timestamp, Timestamp, String, Pageable)
     */
    @GetMapping("/search/{grp}/{username}/{from}/{to}/{message}")
    public Page<Report> getDataByCustomMessage(@PathVariable String grp, @PathVariable String username, @PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable String message, @PageableDefault Pageable pageable) {
        return archiveService.getDataByCustomMessage(grp, username, from, to, message, pageable);
    }

    /**
     * @see ArchiveService#getAllDataByCustomMessage(Timestamp, Timestamp, String, Pageable)
     */
    @GetMapping("/search/all/{from}/{to}/{message}")
    public Page<Report> getAllDataByCustomMessage(@PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable String message, @PageableDefault Pageable pageable) {
        return archiveService.getAllDataByCustomMessage(from, to, message, pageable);
    }


    /**
     * The method accepts the start date and end date of the search, as well as the text of the message,
     * and returns a paginated representation of the count of all messages for the generated user table
     *
     * @param grp      group name
     * @param username user name
     * @param from     search start date
     * @param to       search end date
     * @param message  message text
     * @param pageable pagination parameters
     * @return count of all messages
     * @see ArchiveService#getDataByCustomMessage(String, String, Timestamp, Timestamp, String, Pageable)
     */
    @GetMapping("/search/{grp}/{username}/{from}/{to}/{message}/totalCount")
    public Page<Report> getTotalCount(@PathVariable String grp, @PathVariable String username, @PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable String message, @PageableDefault Pageable pageable) {
        long totalCount = IntStream.of(archiveService.getDataByCustomMessage(grp, username, from, to, message, pageable)
                .getContent()
                .stream()
                .mapToInt(value -> value.getCount()
                        .intValue()).toArray()).sum();
        List<Report> result = new ArrayList<>();
        result.add(new Report("ВСЕГО", totalCount));
        return new PageImpl<>(result);
    }

}

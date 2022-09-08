package com.temnet.parser.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * Archive Controller class using REST API to present data preprocessed by the corresponding service class*
 *
 * @author Temnet
 */
@RestController
@RequestMapping("/api/archive")
public class ArchiveController {
   /* private final ArchiveService archiveService;
    private final TopService topService;

    *//**
     * @param archiveService message archive service class used to process API business logic
     * @see ArchiveService
     *//*
    @Autowired
    public ArchiveController(ArchiveService archiveService, TopService topService) {
        this.archiveService = archiveService;
        this.topService = topService;
    }


    *//**
     * @see ArchiveService#getDataByCustomMessage(String, String, Timestamp, Timestamp, String, Pageable)
     *//*
    @GetMapping("/search/{grp}/{username}/{from}/{to}/{message}")
    public Page<Report> getDataByCustomMessage(@PathVariable String grp, @PathVariable String username, @PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable String message, @PageableDefault Pageable pageable) {
        return archiveService.getDataByCustomMessage(grp, username, from, to, message, pageable);
    }

    *//**
     * @see ArchiveService#getAllDataByCustomMessage(Timestamp, Timestamp, String, Pageable)
     *//*
    @GetMapping("/search/all/{from}/{to}/{message}")
    public Page<Report> getAllDataByCustomMessage(@PathVariable Timestamp from, @PathVariable Timestamp to, @PathVariable String message, @PageableDefault Pageable pageable) {
        return archiveService.getAllDataByCustomMessage(from, to, message, pageable);
    }


    *//**
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
     *//*
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


    @GetMapping("/search/top/{start}/{end}")
    public Page<TopReport> getTopService(@PathVariable Timestamp start, @PathVariable Timestamp end, Pageable pageable) {
        return topService.getTopReport(start, end, pageable);
    }*/

}

package com.temnet.parser.service;

import com.temnet.parser.domain.Archive;
import com.temnet.parser.domain.Report;
import com.temnet.parser.repo.ArchiveRepository;
import com.temnet.parser.repo.spec.ArchiveSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ArchiveService {
    /**
     * @see ArchiveRepository
     */
    private final ArchiveRepository archiveRepository;

    @Autowired
    public ArchiveService(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }


    /**
     * Takes as a parameter a list typed with the type 'Archive' and returns a list typed with the type 'Report'
     *
     * @param archive list of archives to be processed
     * @see Archive
     * @see Report
     */
    public Page<Report> getReport(Page<Archive> archive) {
        LinkedHashMap<String, Long> stringLongLinkedHashMap = new LinkedHashMap<>();
        List<Report> report = new ArrayList<>();
        archive.forEach(a -> stringLongLinkedHashMap.put(a.getUsername(), stringLongLinkedHashMap.getOrDefault(a.getUsername(), 0L) + 1L));
        Map<String, Long> resultMap = orderByValue(stringLongLinkedHashMap);
        resultMap.forEach((key, value) -> report.add(new Report(key, value)));
        return new PageImpl<>(report);
    }


    /**
     * The method accepts the start date and end date of the search, as well as the text of the message,
     * and returns a paginated representation of the users and messages count
     *
     * @param grp      group name
     * @param username      user name
     * @param from     search start date
     * @param to       search end date
     * @param message      message text
     * @param pageable pagination parameters
     * @return count of all messages
     * @see ArchiveRepository
     * @see Report
     */
    public Page<Report> getDataByCustomMessage(String grp, String username, Timestamp from, Timestamp to, String message, Pageable pageable) {
        pageable = Pageable.unpaged();
        Page<Archive> find;
        username = username.equals("all") ? grp : username;
        find = archiveRepository.findAll(ArchiveSpecification.usernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(
                username,
                from,
                to,
                Arrays.stream(message.split(" ")).collect(Collectors.toList())
        ), pageable);
        return getReport(find);
    }

    /**
     * The method accepts the start date and end date of the search, as well as the text of the message,
     * and returns a pagination of a list of all users and the count of messages
     *
     * @param from     search start date
     * @param to       search end date
     * @param message      message text
     * @param pageable pagination parameters
     * @return paginated view of the summary report list
     * @see ArchiveRepository
     * @see Report
     */
    public Page<Report> getAllDataByCustomMessage(Timestamp from, Timestamp to, String message, Pageable pageable) {
        pageable = Pageable.unpaged();
        List<Report> r = new ArrayList<>();
        Page<Archive> find = archiveRepository.findAll(ArchiveSpecification.createdAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(
                from,
                to,
                Arrays.stream(message.split(" ")).collect(Collectors.toList())
        ), pageable);
        r.add(new Report("ВСЕ", find.getTotalElements()));
        return new PageImpl<>(r);
    }


    /**
     * Takes a LinkedHashMap<String, Long> as input and sorts the values in descending order
     *
     * @param map LinkedHashMap<String, Long>
     * @return sorted LinkedHashMap<String, Long>
     */
    private Map<String, Long> orderByValue(LinkedHashMap<String, Long> map) {
        Stream<Map.Entry<String, Long>> sorted = map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        return sorted.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}

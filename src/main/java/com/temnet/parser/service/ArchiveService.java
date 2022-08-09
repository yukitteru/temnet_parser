package com.temnet.parser.service;

import com.temnet.parser.domain.Archive;
import com.temnet.parser.domain.Report;
import com.temnet.parser.repo.ArchiveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for processing business logic related to the Archive entity
 * <p>
 * Please see the {@link Archive} class for true identity
 *
 * @author Temnet
 */
@Service
@Transactional
public class ArchiveService {
    /**
     * @see ArchiveRepository
     */
    private final ArchiveRepository archiveRepository;
    /**
     * @see com.temnet.parser.repo.CustomUsersRepository
     */
    private final CustomUserService customUserService;

    /**
     * @see com.temnet.parser.repo.GroupsRepository
     */
    private final GroupService groupService;

    public ArchiveService(ArchiveRepository archiveRepository, CustomUserService customUserService, GroupService groupService) {
        this.archiveRepository = archiveRepository;
        this.customUserService = customUserService;
        this.groupService = groupService;
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
     * @param gid      group identifier
     * @param uid      user identifier
     * @param from     search start date
     * @param to       search end date
     * @param txt      message text
     * @param pageable pagination parameters
     * @return count of all messages
     * @see GroupService
     * @see CustomUserService
     * @see ArchiveRepository
     * @see Report
     */
    public Page<Report> getDataByCustomMessage(Long gid, Long uid, Timestamp from, Timestamp to, String txt, @PageableDefault(size = 15) Pageable pageable) {
        pageable = Pageable.unpaged();
        Page<Archive> find;
        String username = uid == -1 ? groupService.findById(gid) : customUserService.findUserById(uid);
        find = archiveRepository.findByUsernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(username, from, to, txt, pageable);
        return getReport(find);
    }

    /**
     * The method accepts the start date and end date of the search, as well as the text of the message,
     * and returns a pagination of a list of all users and the count of messages
     *
     * @param from     search start date
     * @param to       search end date
     * @param txt      message text
     * @param pageable pagination parameters
     * @return paginated view of the summary report list
     * @see ArchiveRepository
     * @see Report
     */
    public Page<Report> getAllDataByCustomMessage(Timestamp from, Timestamp to, String txt, @PageableDefault(size = 15) Pageable pageable) {
        pageable = Pageable.unpaged();
        Page<Archive> find;
        List<Report> r = new ArrayList<>();
        r.add(new Report("ВСЕ", archiveRepository.countArchiveByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(from, to, txt) / 2));
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

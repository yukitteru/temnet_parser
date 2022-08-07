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

@Service
@Transactional
public class ArchiveService {
    private final ArchiveRepository archiveRepository;
    private final CustomUserService customUserService;
    private final GroupService groupService;

    public ArchiveService(ArchiveRepository archiveRepository, CustomUserService customUserService, GroupService groupService) {
        this.archiveRepository = archiveRepository;
        this.customUserService = customUserService;
        this.groupService = groupService;
    }

    public Page<Report> getReport(Page<Archive> archive) {
        LinkedHashMap<String, Long> stringLongLinkedHashMap = new LinkedHashMap<>();
        List<Report> report = new ArrayList<>();
        archive.forEach(a -> stringLongLinkedHashMap.put(a.getUsername(), stringLongLinkedHashMap.getOrDefault(a.getUsername(), 0L) + 1L));
        Map<String, Long> resultMap = orderByValue(stringLongLinkedHashMap);
        resultMap.forEach((key, value) -> report.add(new Report(key, value)));
        return new PageImpl<>(report);
    }

    public Page<Report> getDataByCustomMessage(Long gid, Long uid, Timestamp from, Timestamp to, String txt, @PageableDefault(size = 15) Pageable pageable) {
        pageable = Pageable.unpaged();
        Page<Archive> find;
        String username = uid == -1 ? groupService.findById(gid) : customUserService.findUserById(uid);
        find = archiveRepository.findByUsernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(username, from, to, txt, pageable);
        return getReport(find);
    }

    public Page<Report> getAllDataByCustomMessage(Timestamp from, Timestamp to, String txt, @PageableDefault(size = 15) Pageable pageable) {
        pageable = Pageable.unpaged();
        Page<Archive> find;
        List<Report> r = List.of(
                new Report("ВСЕ", archiveRepository.countArchiveByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxtContains(from, to, txt) / 2)
        );
        return new PageImpl<>(r);
    }

    private Map<String, Long> orderByValue(LinkedHashMap<String, Long> resultMap) {
        Stream<Map.Entry<String, Long>> sorted = resultMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        return sorted.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}

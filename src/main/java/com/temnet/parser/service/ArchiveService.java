package com.temnet.parser.service;

import com.temnet.parser.domain.Archive;
import com.temnet.parser.domain.Report;
import com.temnet.parser.domain.Status;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ArchiveService {
    private final Pattern closed = Pattern.compile("^.*(ЗАКРЫТА ЗАЯВКА|ЗАЯВКА ЗАКРЫТА).*", Pattern.CASE_INSENSITIVE);
    private final Pattern inProgress = Pattern.compile("^.*(В РАБОТЕ ЗАЯВКА|ЗАЯВКА В РАБОТЕ).*", Pattern.CASE_INSENSITIVE);
    private final Pattern rejected = Pattern.compile("^.*(ОТКЛОНЕНА ЗАЯВКА|ЗАЯВКА ОТКЛОНЕНА).*", Pattern.CASE_INSENSITIVE);
    private final ArchiveRepository repo;

    public ArchiveService(ArchiveRepository repo) {
        this.repo = repo;
    }

    public Page<Report> getReport(Page<Archive> archive) {
        LinkedHashMap<String, Long> stringLongLinkedHashMap = new LinkedHashMap<>();
        List<Report> report = new ArrayList<>();
        archive.forEach(a -> stringLongLinkedHashMap.put(a.getUsername(), stringLongLinkedHashMap.getOrDefault(a.getUsername(), 0L) + 1L));
        Map<String, Long> resultMap = orderByValue(stringLongLinkedHashMap);
        resultMap.forEach((key, value) -> report.add(new Report(key, value)));
        return new PageImpl<>(report);
    }

    public String getStatusString(Status status) {
        switch (status) {
            case close:
                return match(closed, status.toString());
            case in_progress:
                return match(inProgress, status.toString());
            case rejected:
                return match(rejected, status.toString());
            default:
                return "Not found";
        }
    }

    public Page<Report> getOrgData(String username, Timestamp from, Timestamp to, Status status, @PageableDefault(size = 15) Pageable pageable) {
        pageable = Pageable.unpaged();
        String statusString = getStatusString(status);
        Page<Archive> find = repo.findByUsernameContainsAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxt(
                username,
                from,
                to,
                statusString,
                pageable
        );
        return getReport(find);
    }

    public Page<Report> getAllData( Timestamp from, Timestamp to, Status status, @PageableDefault(size = 15) Pageable pageable) {
        pageable = Pageable.unpaged();
        String statusString = getStatusString(status);
        Page<Archive> find = repo.findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndTxt(
                from,
                to,
                statusString,
                pageable
        );
        return getReport(find);
    }

    private String match(Pattern p, String in) {
        Matcher m = p.matcher(in);
        boolean matches = m.matches();
        return m.group();
    }

    private Map<String, Long> orderByValue(LinkedHashMap<String, Long> resultMap) {
        Stream<Map.Entry<String, Long>> sorted = resultMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        return sorted.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}

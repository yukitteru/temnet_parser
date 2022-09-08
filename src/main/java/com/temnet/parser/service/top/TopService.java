package com.temnet.parser.service.top;

import com.temnet.parser.domain.SrUser;
import com.temnet.parser.dto.report.top.TopReport;
import com.temnet.parser.repo.SrUserRepository;
import com.temnet.parser.repo.ReportRepository;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopService {
    private final ReportRepository reportRepository;
    private final SrUserRepository srUserRepository;

    public TopService(ReportRepository reportRepository, SrUserRepository srUserRepository) {
        this.reportRepository = reportRepository;
        this.srUserRepository = srUserRepository;
    }


    public Page<TopReport> getTopReport(Timestamp start, Timestamp end, Pageable pageable) {
        List<Object[]> activeUsers = reportRepository.findActiveUsers(start, end);
        List<SrUser> accounts = srUserRepository.findAll();
        List<TopReport> collect = activeUsers.parallelStream().map(objects -> {
            String gn = (String) objects[0];
            Long au = ((BigInteger) objects[1]).longValue();
            Long mc = ((BigInteger) objects[2]).longValue();
            return new TopReport(gn, au, accounts.parallelStream()
                    .filter(u -> u.getJid().contains((String) objects[0]))
                    .count(), mc);
        }).collect(Collectors.toList());
        return new PageImpl<>(collect);
    }


}

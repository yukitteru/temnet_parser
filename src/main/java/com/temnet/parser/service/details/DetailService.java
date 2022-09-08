package com.temnet.parser.service.details;

import com.temnet.parser.dto.report.detail.DetailReport;
import com.temnet.parser.repo.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailService {
    private final ReportRepository reportRepository;

    public DetailService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Page<DetailReport> generateDetailReport(String groupName, Timestamp start, Timestamp end, Pageable pageable) {
        List<Object[]> detailInfo = reportRepository.findDetailInfo(groupName, start, end);
        List<DetailReport> collect = detailInfo.parallelStream().map(objects -> {
            String username = (String) objects[0];
            Long finished = ((BigInteger) objects[1]).longValue();
            Long inProgress = ((BigInteger) objects[2]).longValue();
            Long rejected = ((BigInteger) objects[3]).longValue();
            Long total = ((BigInteger) objects[4]).longValue();
            return new DetailReport(username, finished, inProgress, rejected, total);
        }).collect(Collectors.toList());
        return new PageImpl<>(collect);
    }

}

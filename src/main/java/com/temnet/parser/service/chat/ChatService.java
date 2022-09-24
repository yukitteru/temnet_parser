package com.temnet.parser.service.chat;

import com.temnet.parser.dto.report.chat.ChatReport;
import com.temnet.parser.repo.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ReportRepository reportRepository;

    public ChatService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Page<ChatReport> generateChatReport(String username, Timestamp start, Timestamp end, Pageable pageable) {
        List<ChatReport> collect = reportRepository.findChatHistory(username, start, end)
                .parallelStream()
                .map(objects -> {
                    String user = (String) objects[0];
                    String peer = (String) objects[1];
                    String message = (String) objects[2];
                    Timestamp createdAt = (Timestamp) objects[3];
                    return new ChatReport(user, peer, message, createdAt);
                }).collect(Collectors.toList());
        return new PageImpl<>(collect);
    }
}

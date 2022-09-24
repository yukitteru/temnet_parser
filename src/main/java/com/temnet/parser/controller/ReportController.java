package com.temnet.parser.controller;

import com.temnet.parser.dto.report.chat.ChatReport;
import com.temnet.parser.dto.report.detail.DetailReport;
import com.temnet.parser.dto.report.top.TopReport;
import com.temnet.parser.service.chat.ChatService;
import com.temnet.parser.service.details.DetailService;
import com.temnet.parser.service.top.TopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/report/")
@CrossOrigin(origins = "http://localhost:8080")
public class ReportController {
    private final TopService topService;
    private final DetailService detailService;
    private final ChatService chatService;

    public ReportController(TopService topService, DetailService detailService, ChatService chatService) {
        this.topService = topService;
        this.detailService = detailService;
        this.chatService = chatService;
    }

    @GetMapping("top/{start}/{end}")
    public Page<TopReport> getTopService(@PathVariable Timestamp start, @PathVariable Timestamp end, Pageable pageable) {
        return topService.getTopReport(start, end, pageable);
    }

    @GetMapping("/detail/{groupname}/{start}/{end}")
    public Page<DetailReport> getDetailReport(@PathVariable String groupname, @PathVariable Timestamp start, @PathVariable Timestamp end, Pageable pageable) {
        return detailService.generateDetailReport(groupname, start, end, pageable);
    }

    @GetMapping("/chat/{username}/{start}/{end}")
    public Page<ChatReport> getChatReport(@PathVariable String username, @PathVariable Timestamp start, @PathVariable Timestamp end, Pageable pageable) {
        return chatService.generateChatReport(username, start, end, pageable);
    }

}

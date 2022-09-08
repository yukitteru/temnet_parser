package com.temnet.parser.dto.report.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailReport {
    private String username;
    private Long finished;
    private Long inProgress;
    private Long rejected;
    private Long totalMessages;
}

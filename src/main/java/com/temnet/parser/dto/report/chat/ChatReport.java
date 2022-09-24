package com.temnet.parser.dto.report.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatReport {
    private String username;
    private String peer;
    private String message;
    private Timestamp createdAt;
}

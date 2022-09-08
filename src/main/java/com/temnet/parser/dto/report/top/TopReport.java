package com.temnet.parser.dto.report.top;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopReport {
    private String groupName;
    private Long activeUsers;
    private Long numberOfAccounts;
    private Long messageCount;
}

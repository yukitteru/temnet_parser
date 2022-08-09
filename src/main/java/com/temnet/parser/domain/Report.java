package com.temnet.parser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Report class using to store the final report
 *
 * @author Temnet
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    /**
     * User name
     */
    String username;
    /**
     * Count of reports
     */
    Long count;
}

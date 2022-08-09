package com.temnet.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ListItemDto class using as a Data Transfer Object to display data on the client side
 *
 * @author Temnet
 */
@Data
@AllArgsConstructor
public class ListItemDto {
    /**
     * Object ID
     */
    private Long id;
    /**
     * Object value
     */
    private String value;
}

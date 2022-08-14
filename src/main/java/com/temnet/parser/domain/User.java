package com.temnet.parser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User class using to store the user names and groups of users
 *
 * @author Temnet
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String username;
    String group;
}

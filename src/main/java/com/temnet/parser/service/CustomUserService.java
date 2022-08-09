package com.temnet.parser.service;

import com.temnet.parser.domain.CustomUsers;
import com.temnet.parser.domain.Groups;
import com.temnet.parser.repo.CustomUsersRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for processing business logic related to the CustomUsers entity
 * <p>
 * Please see the {@link CustomUsers} class for true identity
 *
 * @author Temnet
 */
@Service
@Transactional
public class CustomUserService {
    /**
     * @see CustomUsersRepository
     */
    private final CustomUsersRepository customUsersRepository;

    public CustomUserService(CustomUsersRepository customUsersRepository) {
        this.customUsersRepository = customUsersRepository;
    }

    /**
     * Accepts a group as input and returns a list of usernames of the corresponding group
     *
     * @param groups group entity
     * @return list of usernames of the corresponding group
     */
    public List<String> getCustomUsersByGroupsId(Groups groups) {
        return customUsersRepository.findAllByGroupOrderByUsernameAsc(groups)
                .stream()
                .map(CustomUsers::getUsername)
                .collect(Collectors.toList());
    }

    /**
     * @see CustomUsersRepository#findById(long)
     */
    public String findUserById(long id) {
        return customUsersRepository.findById(id).getUsername();
    }
}

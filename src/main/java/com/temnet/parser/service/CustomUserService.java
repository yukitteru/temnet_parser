package com.temnet.parser.service;

import com.temnet.parser.domain.CustomUsers;
import com.temnet.parser.domain.Groups;
import com.temnet.parser.repo.CustomUsersRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserService {
    private final CustomUsersRepository customUsersRepository;

    public CustomUserService(CustomUsersRepository customUsersRepository) {
        this.customUsersRepository = customUsersRepository;
    }

    public List<String> getCustomUsersByGroupsId(Groups groups) {
        return customUsersRepository.findAllByGroupOrderByUsernameAsc(groups)
                .stream()
                .map(CustomUsers::getUsername)
                .collect(Collectors.toList());
    }

    public String findUserById(long id) {
        return customUsersRepository.findById(id).getUsername();
    }
}

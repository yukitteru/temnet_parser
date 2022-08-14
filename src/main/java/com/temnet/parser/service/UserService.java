package com.temnet.parser.service;

import com.temnet.parser.domain.SrUser;
import com.temnet.parser.repo.SrUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    /**
     * @see SrUserRepository
     */
    private final SrUserRepository srUserRepository;

    @Autowired
    public UserService(SrUserRepository srUserRepository) {
        this.srUserRepository = srUserRepository;
    }

    /**
     * The method returns a list of all groups,
     * @return list of all groups
     */
    public List<String> findAllGroups() {
        return srUserRepository.findAllGroups();
    }

    /**
     * The method returns a list of all users in the group,
     * @param grp group name
     * @return list of all users in the group
     */
    public List<String> getUserData(String grp){
        return extractUsernameFromJids(srUserRepository.findByGrpOrderByJid(grp));
    }

    /**
     * Extract username from Jabber identifier
     * @param srUserList list of users
     * @return list of usernames
     */
    private List<String> extractUsernameFromJids(List<SrUser> srUserList) {
        return srUserList.stream().map(user -> Pattern.compile("@").splitAsStream(user.getJid())
                .collect(Collectors.toList()).get(0)).collect(Collectors.toList());
    }

}

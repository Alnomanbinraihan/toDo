package com.example.toDo.services;

import com.example.toDo.entity.UserInfo;
import com.example.toDo.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserInfoRepository userInfoRepository;

    public UserInfo saveUser(UserInfo userInfo) {

        String salt = BCrypt.gensalt();
        userInfo.setPassword(BCrypt.hashpw(userInfo.getPassword(), salt));
        return userInfoRepository.save(userInfo);
    }


}

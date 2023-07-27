package com.example.toDo.repo;

import com.example.toDo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;
import java.util.Optional;

public interface UserInfoRepository  extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByName(String username);

    Optional<UserInfo> findByEmail(String useremail);
}

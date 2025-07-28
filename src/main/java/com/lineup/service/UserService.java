package com.lineup.service;

import com.lineup.entity.UserEntity;

public interface UserService {

    UserEntity registerUser(UserEntity user);

    String login(String username, String password);

}

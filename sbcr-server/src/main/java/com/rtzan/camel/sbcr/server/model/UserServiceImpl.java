package com.rtzan.camel.sbcr.server.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Map<Integer, UserEntity> users = new TreeMap<>();

    public UserServiceImpl() {
        users.put(1, new UserEntity(1, "John Coltrane"));
        users.put(2, new UserEntity(2, "Miles Davis"));
        users.put(3, new UserEntity(3, "Sonny Rollins"));
    }

    @Override
    public UserEntity findUser(Integer id) {
        return users.get(id);
    }

    @Override
    public Collection<UserEntity> findUsers() {
        return users.values();
    }

    @Override
    public void updateUser(UserEntity user) {
        users.put(user.getId(), user);
    }

}

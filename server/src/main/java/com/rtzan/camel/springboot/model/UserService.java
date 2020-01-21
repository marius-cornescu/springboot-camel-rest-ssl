package com.rtzan.camel.springboot.model;

import java.util.Collection;

/**
 * Service interface for managing users.
 * 
 */
public interface UserService {

    /**
     * Find a user by the given ID
     *
     * @param id
     *            the ID of the user
     * @return the user, or <code>null</code> if user not found.
     */
    UserEntity findUser(Integer id);

    /**
     * Find all users
     *
     * @return a collection of all users
     */
    Collection<UserEntity> findUsers();

    /**
     * Update the given user
     *
     * @param user
     *            the user
     */
    void updateUser(UserEntity user);

}

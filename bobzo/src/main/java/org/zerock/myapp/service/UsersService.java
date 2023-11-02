package org.zerock.myapp.service;

import org.zerock.myapp.domain.Users;
import org.zerock.myapp.domain.UsersDTO;

public interface UsersService {
    public abstract void register_user(UsersDTO usersDTO);// register_user

    public abstract Users getUserById(String userId);

    public abstract boolean existsByUserId(String id);

} // end interface

package br.com.api.admin.service;

import br.com.api.admin.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<User> findAll(){
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setId(1L);
        user.setUsername("Rafael");
        user.setPassword("123");

        users.add(user);
        
        return users;
    }

}

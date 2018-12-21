package br.com.api.admin.service;

import br.com.api.admin.entity.User;
import br.com.api.admin.exception.BusinessException;
import br.com.api.admin.repository.UserRepository;
import br.com.api.admin.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        verifiyIfUserExist(user);
        user.setPassword(BCryptUtil.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private void verifiyIfUserExist(final User user) {
        Optional<User> userDB = userRepository.findByUsername(user.getUsername());
        if (userDB.isPresent()) {
            throw new BusinessException("error-user-8", HttpStatus.BAD_REQUEST);
        }
    }
}

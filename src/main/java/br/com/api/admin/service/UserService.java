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

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
    }

    public User save(User user) {
        verifiyIfUserExist(user);
        user.setPassword(BCryptUtil.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
    }

    private void verifiyIfUserExist(final User user) {
        Optional<User> userDB = userRepository.findByUsername(user.getUsername());
        if (userDB.isPresent() && userDB.get().getId() != user.getId()) {
            throw new BusinessException("error-user-8", HttpStatus.BAD_REQUEST);
        }
    }
}

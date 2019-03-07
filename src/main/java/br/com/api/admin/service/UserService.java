package br.com.api.admin.service;

import br.com.api.admin.entity.User;
import br.com.api.admin.exception.BusinessException;
import br.com.api.admin.repository.UserCustomizedQueries;
import br.com.api.admin.repository.UserRepository;
import br.com.api.admin.resource.user.UserRequest;
import br.com.api.admin.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserCustomizedQueries userCustomizedQueries;

    public UserService(@Autowired UserRepository userRepository, @Autowired UserCustomizedQueries userCustomizedQueries) {
        this.userRepository = userRepository;
        this.userCustomizedQueries = userCustomizedQueries;
    }

    public Page<User> findAll(UserRequest userRequest) {
        return userCustomizedQueries.findAll(userRequest);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
    }

    public User save(User user) {
        verifyIfUserExist(user);
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

    private void verifyIfUserExist(final User user) {
        Optional<User> userDB = userRepository.findByUsername(user.getUsername());
        if (userDB.isPresent() && userDB.get().getId() != user.getId()) {
            throw new BusinessException("error-user-8", HttpStatus.BAD_REQUEST);
        }
    }
}

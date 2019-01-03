package br.com.api.admin.service;

import br.com.api.admin.entity.User;
import br.com.api.admin.enumeration.ProfileEnum;
import br.com.api.admin.exception.BusinessException;
import br.com.api.admin.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    private User userInDB;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
        userInDB = new User();
        userInDB.setId(1L);
        userInDB.setName("Rafael");
        userInDB.setUsername("rafaalberto");
        userInDB.setPassword("123456");
        userInDB.setProfile(ProfileEnum.ADMIN);
    }

    @Test
    public void shouldCreateNewUser(){
        User user = new User();
        user.setName("Rafael");
        user.setUsername("rafaalberto");
        user.setPassword("123456");
        user.setProfile(ProfileEnum.ADMIN);

        when(userRepository.save(user)).thenReturn(userInDB);
        User userSaved = userService.save(user);

        assertThat(userSaved.getId(), equalTo(1L));
        assertThat(userSaved.getUsername(), equalTo("rafaalberto"));
    }

    @Test(expected = BusinessException.class)
    public void shouldDenySaveIfUserAlreadyExistsAndDifferentId(){
        when(userRepository.findByUsername("rafaalberto")).thenReturn(Optional.of(userInDB));
        User user = new User();
        user.setId(2L);
        user.setUsername("rafaalberto");
        userService.save(user);
    }

    @Test
    public void shouldAllowSaveIfUserAlreadyExistsAndSimilarId(){
        when(userRepository.findByUsername("rafaalberto")).thenReturn(Optional.of(userInDB));
        User user = new User();
        user.setId(1L);
        user.setUsername("rafaalberto");
        userService.save(user);
    }

}

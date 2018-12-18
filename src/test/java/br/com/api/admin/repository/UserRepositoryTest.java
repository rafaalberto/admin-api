package br.com.api.admin.repository;

import br.com.api.admin.entity.User;
import br.com.api.admin.enumeration.ProfileEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("rafaalberto");
        user.setPassword("123");
        user.setName("Rafael");
        user.setProfile(ProfileEnum.ADMIN);
    }

    @Test
    public void shouldCreateUser() {
        User userSaved = userRepository.save(user);
        assertThat(userSaved.getId()).isNotNull();
        assertThat(userSaved.getUsername()).isEqualTo("rafaalberto");
        assertThat(userSaved.getPassword()).isEqualTo("123");
    }

    @Test
    public void findAll() {
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(BigInteger.ONE.intValue());
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}

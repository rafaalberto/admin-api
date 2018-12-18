package br.com.api.admin.resource;

import br.com.api.admin.entity.User;
import br.com.api.admin.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserResourceTest {

    @InjectMocks
    private UserResource userResource = new UserResource();

    @Mock
    private UserService userService;

    @Test
    public void shouldReturnStatus200WhenFindAllIsOK(){
        ResponseEntity<List<User>> response = userResource.findAll();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

}

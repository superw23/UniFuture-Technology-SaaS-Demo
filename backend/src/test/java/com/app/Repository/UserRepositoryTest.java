//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.User;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    User user;

    @Mock
    private UserRepository userRepository;

//    @BeforeEach
//    public void setUp(){
//        user = User.builder()
//                .name("MockUser")
//                .email("MockUser@nottingham.edu.cn")
//                .password("MockUser")
//                .phone("12453")
//                .build();
//    }

    //JUnit5 test for saveUser
    @Test
    public void saveUserTest(){
        userRepository.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void getAllUsersTest(){
        userRepository.findAll();
        verify(userRepository).findAll();
    }

    @Test
    public void findByNameTest(){
        userRepository.findBy姓名("Sally");
        verify(userRepository).findBy姓名("Sally");
    }
}

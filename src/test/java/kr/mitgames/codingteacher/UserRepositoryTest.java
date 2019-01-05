package kr.mitgames.codingteacher;

import com.google.common.collect.Iterables;
import kr.mitgames.codingteacher.user.User;
import kr.mitgames.codingteacher.user.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {
    }

    @Test
    public void addNewUserTest() {
        User u = new User();
        u.setLoginId("test");
        u.setEmailAccount("test@example.org");
        u.setPasswordHash("test");
        u.setPermissionLevel(1);
        u.setStudyLevel(1);

        userRepository.save(u);
        Iterable<User> users = userRepository.findAll();
        Assert.assertEquals(1, Iterables.size(users));
    }

    @Test
    public void addDuplicatedUserTest() {
        User u = new User();
        u.setLoginId("test");
        u.setEmailAccount("test@example.org");
        u.setPasswordHash("test");
        u.setPermissionLevel(1);
        u.setStudyLevel(1);

        userRepository.save(u);

        User u2 = new User();
        u2.setLoginId("test");
        u2.setEmailAccount("test@example.org");
        u2.setPasswordHash("test");
        u2.setPermissionLevel(1);
        u2.setStudyLevel(1);

        userRepository.save(u2);
        Iterable<User> users = userRepository.findAll();
        Assert.assertEquals(2, Iterables.size(users));
    }

    @Test
    public void findUserByLoginIdTest() {
        User u = new User();
        u.setLoginId("test");
        u.setEmailAccount("test@example.org");
        u.setPasswordHash("test");
        u.setPermissionLevel(1);
        u.setStudyLevel(1);

        userRepository.save(u);

        List<User> userList = userRepository.findUserByLoginId(u.getLoginId());
        Assert.assertEquals(1, userList.size());
    }
}

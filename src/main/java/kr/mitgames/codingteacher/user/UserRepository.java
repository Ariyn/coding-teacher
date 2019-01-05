package kr.mitgames.codingteacher.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import javax.persistence.EntityManager;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    public List<User> findUserByLoginId(String loginId);
    public List<User> findUserByEmailAccount(String email);
}
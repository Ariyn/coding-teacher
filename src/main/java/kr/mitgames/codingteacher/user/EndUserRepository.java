package kr.mitgames.codingteacher.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EndUserRepository extends CrudRepository<EndUser, Integer> {
    public EndUser findUserByLoginId(String loginId);
    public EndUser findUserByEmailAccount(String email);
}
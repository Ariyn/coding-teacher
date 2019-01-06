package kr.mitgames.codingteacher.user;

        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.stereotype.Service;

        import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private EndUserRepository endUserRepository;

    public UserDetailsServiceImpl(EndUserRepository userRepo) {
        this.endUserRepository = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        EndUser user = endUserRepository.findUserByLoginId(loginId);
        if(user == null) {
            throw new LoginFailedException();
        }
        return new User(user.getLoginId(), user.getPassword(), emptyList());
    }
}

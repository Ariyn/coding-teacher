package kr.mitgames.codingteacher.user;

import kr.mitgames.codingteacher.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Level;

class LoginFailedException extends RuntimeException {
    LoginFailedException() {
        super("user not exists or wrong password");
    }

    LoginFailedException(String function) {
        super(function+" failed! user not exists or wrong password");
    }
}

@RestController
public class LoginController {
    @Autowired
    private EndUserRepository endUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody EndUserDTO userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        EndUser user = new EndUser();
        user.cast(userDto);

        endUserRepository.save(user);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody EndUserDTO userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        EndUser user = endUserRepository.findUserByLoginId(userDto.getLoginId());
        if(null != user)
            endUserRepository.delete(user);
        else {
            throw new LoginFailedException();
        }
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<EndUser> getAllUsers() {
        return endUserRepository.findAll();
    }
}

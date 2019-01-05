package kr.mitgames.codingteacher.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.mitgames.codingteacher.user.User;
import kr.mitgames.codingteacher.user.UserRepository;

@Controller
@RequestMapping(path="/login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping()
    public String loginPage(Model model) {
        return "login";
    }

    @RequestMapping("/check")
    public @ResponseBody String loginPage(@RequestParam String loginId, @RequestParam String password) {
        return loginId+" "+password;
    }

    @GetMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String loginId, @RequestParam String email) {
        User u = new User();
        u.setLoginId(loginId);
        u.setEmailAccount(email);
        u.setPasswordHash("test");
        u.setStudyLevel(1);
        u.setPermissionLevel(1);

        userRepository.save(u);

        return "Succeed";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}

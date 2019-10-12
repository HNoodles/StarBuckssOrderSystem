package fudan.se.lab4.controller;

import fudan.se.lab4.entity.User;
import fudan.se.lab4.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired(required = false)
    private AccountService accountService;

    @PostMapping(value = "/accounts/login")
    public boolean login(@RequestBody User user) {
        return accountService.login(user);
    }

    @PostMapping(value = "/accounts/signup")
    public boolean signup(@RequestBody User user) {
        return accountService.signup(user);
    }

    @PostMapping(value = "/accounts/checkStatus")
    public boolean signup() {
        return accountService.checkStatus();
    }

}

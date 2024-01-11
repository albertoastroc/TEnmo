package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDto;
import com.techelevator.tenmo.model.UsernameBalanceDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }


//    @PreAuthorize("isAuthenticated()")
    //Change return type from double to some kind of object
    @RequestMapping(path = "/accounts/{userId}", method = RequestMethod.GET)
    public UsernameBalanceDto checkBalance(@PathVariable int userId){
        return accountDao.checkBalance(userId);
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<UserDto> getAllUsers(){
       return userDao.getAllUsernames();

    }

}

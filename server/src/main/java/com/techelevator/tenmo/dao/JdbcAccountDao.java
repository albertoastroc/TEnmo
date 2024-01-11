package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDto;
import com.techelevator.tenmo.model.UsernameBalanceDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //need to replace double with object of some kind
    public UsernameBalanceDto checkBalance(int userId) {

        UsernameBalanceDto usernameBalanceDto = new UsernameBalanceDto();
        String sql = "SELECT username, balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE account.user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            usernameBalanceDto.setBalance(result.getDouble("balance"));
            usernameBalanceDto.setUsername(result.getString("username"));
            return usernameBalanceDto;
        }
        return usernameBalanceDto;
    }

}

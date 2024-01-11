package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UsernameBalanceDto;

public interface AccountDao {
    UsernameBalanceDto checkBalance(int id);
}

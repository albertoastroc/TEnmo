package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

private JdbcTemplate jdbcTemplate;

public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
}

    @Override
    public Transfer postTransfer(String transferTo, double transferAmount, String transferFrom) {
        Transfer transfer = new Transfer();

        String sqlGetBalance = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = " +
                "tenmo_user.user_id WHERE username = ?;";

        String sqlInsertTransfer = "INSERT INTO transfer (transfer_from, transfer_to, transfer_amount, status)" +
                "VALUES (?, ?, ?, ?) RETURNING transfer_id, transfer_from, transfer_to, transfer_amount, status;";
        String sqlUpdateFrom = "UPDATE account SET balance = ? WHERE user_id = (SELECT user_id FROM tenmo_user " +
                " WHERE username = ?);";
        String sqlUpdateTo = "UPDATE account SET balance = ? WHERE user_id = (SELECT user_id FROM tenmo_user " +
                "WHERE username = ?);";


        try {
            // Retrieve balance of sender
            SqlRowSet getBalanceFromResult = jdbcTemplate.queryForRowSet(sqlGetBalance, transferFrom);
            double fromBalance = 0;
            if (getBalanceFromResult.next()) {
                fromBalance = getBalanceFromResult.getDouble("balance");
            }
            if (!transferFrom.equals(transferTo)) {
                // Post transfer

                // Check balance has enough funds for transfer, set transfer object fields for JSON return format

                    if (transferAmount <= fromBalance && transferAmount > 0) {

                            SqlRowSet insertTransfer = jdbcTemplate.queryForRowSet(sqlInsertTransfer, transferFrom, transferTo, transferAmount, "approved");
                            if (insertTransfer.next()) {
                                transfer = mapRowToTransfer(insertTransfer);

                                // Update sender's balance
                                jdbcTemplate.update(sqlUpdateFrom, fromBalance - transferAmount, transferFrom);
                                // Update recipient's balance
                                SqlRowSet getBalanceToResult = jdbcTemplate.queryForRowSet(sqlGetBalance, transferTo);
                                double toBalance = 0;
                                if (getBalanceToResult.next()) {
                                    toBalance = getBalanceToResult.getDouble("balance");
                                }
                                jdbcTemplate.update(sqlUpdateTo, toBalance + transferAmount, transferTo);
                            }


                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't send money to yourself");
            }

        } catch (DataAccessException e) {
            System.out.println("Transfer failed.");
        }
        catch (RestClientResponseException e) {
            if (e.getRawStatusCode() >= 500) {
                System.out.println("Server Error.");
            }
            else if (e.getRawStatusCode() >= 400) {
                System.out.println("Client Error");
            }
        }
        return transfer;

    }

    //else if (transferAmount <= 0){
    //                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer must be positive amount.");
    //                        }

    //else {
   //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
   //}

    @Override
    public Transfer getTransfer(int transferId, String transferFrom) {
        return null;
    }

    @Override
    public List<Transfer> getTransfersForUser(String transferId, String transferFrom) {

        List<Transfer> transfers = new ArrayList<>();

        String sqlGetAllTransfersForUser = ("SELECT * FROM transfer WHERE transfer_from" +
                "= ?, OR transfer_to = ?;");

       // SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlGetAllTransfersForUser, transferFrom, transferFrom);

//        while (sqlRowSet.next()){
//            Transfer transfer = new Transfer();
//            transfer.set
//
//        }

        return null;
    }

    public Transfer mapRowToTransfer(SqlRowSet sqlRowSet){

        Transfer transfer = new Transfer();

        transfer.setTransferId(sqlRowSet.getInt("transfer_id"));
        transfer.setTransferTo(sqlRowSet.getString("transfer_to"));
        transfer.setTransferAmount(sqlRowSet.getDouble("transfer_amount"));
        transfer.setTransferFrom(sqlRowSet.getString("transfer_from"));
        transfer.setStatus(sqlRowSet.getString("status"));

        return transfer;

    }
}

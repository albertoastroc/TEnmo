package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    public Transfer postTransfer(String transferTo, double transferAmount, String transferFrom);

    Transfer getTransfer(int transferId, String transferFrom);

    List<Transfer> getTransfersForUser(String transferId, String transferFrom);
}

package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

//    @RequestMapping(path = "/transfers/{userId}")
//    public List<Transfer> getTransfersById(@PathVariable int userId, Principal principal){
//
//        return transferDao.getTransfersForUser(principal.getName());
//
//    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer postTransfer(Principal principal, @RequestBody Transfer transfer) {
        try {
            return transferDao.postTransfer(transfer.getTransferTo(), transfer.getTransferAmount(), principal.getName());
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't send money to yourself");

        }

    }
}

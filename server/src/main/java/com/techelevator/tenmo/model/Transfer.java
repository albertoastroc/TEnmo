package com.techelevator.tenmo.model;

public class Transfer {

    public int transferId;

    public double transferAmount;

    public String transferFrom;

    public String transferTo;

    public Transfer(double transferAmount, String transferFrom, String transferTo) {
        this.transferAmount = transferAmount;
        this.transferFrom = transferFrom;
        this.transferTo = transferTo;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", transferAmount=" + transferAmount +
                ", transferFrom='" + transferFrom + '\'' +
                ", transferTo='" + transferTo + '\'' +
                '}';
    }
}

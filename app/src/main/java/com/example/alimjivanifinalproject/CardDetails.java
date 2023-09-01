package com.example.alimjivanifinalproject;

public class CardDetails {
    public String cardNumber;
    public String cardHolderName;
    public String expiryMonth;
    public String expiryYear;
    public String cardCvv;

    public CardDetails(String cardNumber, String cardHolderName, String expiryMonth, String expiryYear, String cardCvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cardCvv = cardCvv;
    }
}

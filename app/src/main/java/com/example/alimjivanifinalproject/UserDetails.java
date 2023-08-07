package com.example.alimjivanifinalproject;

public class UserDetails {
    public int userId;
    public String fullName;
    public int phoneNumber;
    public String address;
    public String city;
    public String postalCode;
    public String province;
    public String cardName;
    public String cardHolderName;
    public String expiryMonth;
    public String expiryYear;
    public String cardCvv;

    public UserDetails() {}

    public UserDetails(int userId, String fullName, int phoneNumber, String address, String city, String postalCode, String province, String cardName, String cardHolderName, String expiryMonth, String expiryYear, String cardCvv) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.cardName = cardName;
        this.cardHolderName = cardHolderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cardCvv = cardCvv;
    }
}

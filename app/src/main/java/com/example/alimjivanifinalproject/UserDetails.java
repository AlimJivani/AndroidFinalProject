package com.example.alimjivanifinalproject;

public class UserDetails {
    public String fullName;
    public int phoneNumber;
    public String address;
    public String city;
    public String postalCode;
    public String province;
    public CardDetails cardDetails;

    public UserDetails() {}

    public UserDetails(String fullName, int phoneNumber, String address, String city, String postalCode, String province, CardDetails cardDetails) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.cardDetails = cardDetails;
    }
}

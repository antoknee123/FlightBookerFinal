package com.example.anthony.flightbookerfinal;


/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */
public class Client {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String cardNumber;
    private String expiryDate;


    public Client(String email, String password,
                  String firstName, String lastName, String phone,
                  String address, String cardNumber, String expiryDate) {

        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;

    }

public Client(){

}



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }



}

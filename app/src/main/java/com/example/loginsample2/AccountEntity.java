package com.example.loginsample2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountEntity implements Serializable {

    private List<AccountEntity> accountList = new ArrayList<>();
    private String firstName=" ";
    private String lastName=" ";
    private String email=" ";
    private String phone=" ";
    private String user=" ";
    private String password=" ";

    AccountEntity(){}
    AccountEntity(String firstName, String lastName,String email,String phone, String user, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phone=phone;
        this.user=user;
        this.password=password;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addAccount(AccountEntity account) {
        accountList.add(account);
    }
    public List<AccountEntity> getAccountList() {
        return accountList;
    }
}

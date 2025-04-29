package com.identity.verification.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistrationRequest {

    private String phoneNumber;
    private String nin;
    private String firstName;
    private String lastName;



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNin() {
        return nin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNin(String nin) {
        this.nin = nin;
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

    public RegistrationRequest(String phoneNumber, String nin) {
        this.phoneNumber = phoneNumber;
        this.nin = nin;
    }

}

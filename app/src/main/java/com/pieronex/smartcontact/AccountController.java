package com.pieronex.smartcontact;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by win.thitiwat on 11/20/2014.
 */
public class AccountController {

    ArrayList<String> tags;

    private Account model;
    private AccountView view;

    public AccountController(Account model, AccountView view) {
        this.model = model;
        this.view = view;
    }


    public void updateView(){
        view.showStudentDetails(model);
        // wait Nook printAccountDetails();
    }

    public String getAccountFirstName() {
        return model.getFirstName();
    }

    public void setAccountFirstName(String accountFirstName) {
        model.setFirstName(accountFirstName);
    }

    public String getAccountLastName() {
        return model.getLastName();
    }

    public void setAccountLastName(String accountLastName) {
        model.setLastName(accountLastName);
    }

    public String getAccountDisplayName() {
        return model.getDisplayName();
    }

    public void setAccountDisplayName(String accountDisplayName) {
        model.setDisplayName(accountDisplayName);
    }

    public String getAccountPhoneNo() {
        return model.getPhoneNo();
    }

    public void setAccountPhoneNo(String accountPhoneNo) {
        model.setPhoneNo(accountPhoneNo);
    }

    public String getAccountEMail() {
        return model.getEmail();
    }

    public void setAccountEMail(String accountEMail) {
        model.setEmail(accountEMail);
    }

    public Picture getAccountPictureProfile() {
        return model.getPictureProfile();
    }

    public void setAccountPictureProfile(Picture accountPictureProfile) {
        model.setPictureProfile(accountPictureProfile);
    }
}

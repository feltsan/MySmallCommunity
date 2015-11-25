package com.thinkmobiles.mysmallcommunity.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by dreamfire on 19.11.15.
 */
public class User {
    private static User sUser;

    public static User newInstance(){
        if(sUser == null){
            sUser = new User();
        }

        return sUser;
    }

    private User(){

    }

    private String userId;
    private String userFbId;
    private String firstName;
    private String lastName;
    private String email;
    private String emirate;
    private String community;
    private String area;
    private String familyStatus;
    private String token;
    private List<String> userInteres;
    private Bitmap profileImage;

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFbId() {
        return userFbId;
    }

    public void setUserFbId(String userFbId) {
        this.userFbId = userFbId;
    }

    public static User getsUser() {
        return sUser;
    }

    public static void setsUser(User sUser) {
        User.sUser = sUser;
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

    public String getEmirate() {
        return emirate;
    }

    public void setEmirate(String emirate) {
        this.emirate = emirate;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public List<String> getUserInteres() {
        return userInteres;
    }

    public void setUserInteres(List<String> userInteres) {
        this.userInteres = userInteres;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package edu.upc.dsa.models;

import java.util.List;


public class UserInformation {
    String name;
    String surname;
    String birthday;
    String email;
    String password;
    int coins;
    String profilePicture;
    Boolean admin;

    public UserInformation(){};

    public UserInformation(String name, String surname, String birthday, String email, String password, int coins, Boolean admin){
        this.setName(name);
        this.setSurname(surname);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPassword(password);
        this.setCoins(coins);
        this.setAdmin(admin);
    }

    public UserInformation(User u){
        this.setName(u.getName());
        this.setSurname(u.getSurname());
        this.setBirthday(u.getBirthday());
        this.setEmail(u.getEmail());
        this.setPassword(u.getPassword());
        this.setCoins(u.getCoins());
        this.setProfilePicture(u.getProfilePicture());
        this.setAdmin(u.getAdmin());
    }

    private void setProfilePicture(String profilePicture) {
        this.profilePicture=profilePicture;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }
    public String getProfilePicture(){
        return this.profilePicture;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
    public int getCoins(){
        return this.coins;
    }
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}

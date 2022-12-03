package edu.upc.dsa.models;

public class UserInformation {
    String name;
    String surname;
    String birthDate;
    String mail;
    String password;

    public UserInformation(){};

    public UserInformation(String name, String surname, String birthDate, String mail, String password){
        this.name=name;
        this.surname=surname;
        this.birthDate=birthDate;
        this.mail=mail;
        this.password=password;

        this.setName(name);
        this.setSurname(surname);
        this.setBirthDate(birthDate);
        this.setMail(mail);
        this.setPassword(password);

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }
}

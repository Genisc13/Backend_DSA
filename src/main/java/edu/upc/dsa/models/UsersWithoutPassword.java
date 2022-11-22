package edu.upc.dsa.models;

public class UsersWithoutPassword {
    String name;
    String surname;
    String birthDate;
    String mail;

    public UsersWithoutPassword(){};

    public UsersWithoutPassword(String name, String surname, String birthDate, String mail){
        this.name=name;
        this.surname=surname;
        this.birthDate=birthDate;
        this.mail=mail;


        this.setName(name);
        this.setSurname(surname);
        this.setBirthDate(birthDate);
        this.setMail(mail);


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



    public String getMail() {
        return this.mail;
    }



    public String getName() {
        return this.name;
    }


}

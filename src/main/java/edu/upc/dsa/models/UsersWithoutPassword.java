package edu.upc.dsa.models;

public class UsersWithoutPassword {
    String name;
    String surname;
    String birthDate;
    String email;

    public UsersWithoutPassword(){};

    public UsersWithoutPassword(String name, String surname, String birthDate, String email){
        this.name=name;
        this.surname=surname;
        this.birthDate=birthDate;
        this.email=email;


        this.setName(name);
        this.setSurname(surname);
        this.setBirthDate(birthDate);
        this.setEmail(email);


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

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

package edu.upc.dsa.models;


import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String id;
    String name;
    String surname;
    String birthday;
    String email;
    String password;
    Status status;
    public User(){};
    public User(String name, String surname,String date, String email, String password){
        this.id = RandomUtils.getId();
        this.name=name;
        this.surname=surname;
        this.birthday =date;
        this.email=email;
        this.password=password;
        this.status=new Status(id,0,50,0);
    }

    public String getName() {
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getBirthday(){   return this.birthday;}
    public String getUserId() {
        return this.id;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){ return this.password; }

    public Status getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void updateStatus(Gadget gadget){
        this.status.addGadget(gadget);
        this.status.setCoins(this.status.getCoins()-gadget.getCost());
    }

    @Override
    public String toString() {
        return "User [id="+id+", name=" + name + ", surname=" + surname +",birthday= "+ birthday +",email= "+ email+ ",password= "+ password+",status= "+status+"]";
    }

}

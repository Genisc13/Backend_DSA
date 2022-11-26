package edu.upc.dsa.models;


import edu.upc.dsa.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String id;
    String name;
    String surname;
    String birthday;
    String mail;
    String password;
    Status status;
    public User(){};
    public User(String name, String surname,String date, String mail, String password){
        this.id = RandomUtils.getId();
        this.name=name;
        this.surname=surname;
        this.birthday =date;
        this.mail=mail;
        this.password=password;
        this.status=new Status(id,0,0,0);
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
        return this.mail;
    }
    public String getPassword(){ return this.password; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void updateStatus(Gadget gadget){
        status.addGadget(gadget);
    }

    @Override
    public String toString() {
        return "User [id="+id+", name=" + name + ", surname=" + surname +",birthday= "+ birthday +",mail= "+ mail+ ",password= "+ password+",status= "+status+"]";
    }

}

package edu.upc.dsa.models;

public class Credentials {
    String Email;
    String password;
    public Credentials() {}
    public Credentials(String email, String password){
        this.Email=email;
        this.password=password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

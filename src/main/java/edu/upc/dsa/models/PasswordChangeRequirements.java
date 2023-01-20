package edu.upc.dsa.models;

public class PasswordChangeRequirements {
    String idUser;
    String email;
    String newPassword;
    String oldPassword;
    public PasswordChangeRequirements(){};

    public PasswordChangeRequirements(String idUser, String email, String oldPassword, String newPassword) {
        this.idUser = idUser;
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

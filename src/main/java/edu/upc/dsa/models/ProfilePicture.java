package edu.upc.dsa.models;

public class ProfilePicture {
    String idUser;
    String newProfilePicture;

    public ProfilePicture(){}
    public ProfilePicture(String idUser, String newProfilePicture){
        this.idUser=idUser;
        this.newProfilePicture=newProfilePicture;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setNewProfilePicture(String newProfilePicture) {
        this.newProfilePicture = newProfilePicture;
    }

    public String getIdUser(){
        return this.idUser;
    }

    public String getNewProfilePicture() {
        return newProfilePicture;
    }
}


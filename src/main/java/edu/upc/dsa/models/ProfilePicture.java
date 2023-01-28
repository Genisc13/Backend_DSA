package edu.upc.dsa.models;

public class ProfilePicture {
    String newProfilePicture;
    String idUser;
    public ProfilePicture(){}
    public ProfilePicture(String profile, String idUser){
        this.idUser=idUser;
        this.newProfilePicture=profile;
    }
    public String getIdUser(){
        return this.idUser;
    }
    public String getProfilePicture() {
        return newProfilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.newProfilePicture = profilePicture;
    }
}

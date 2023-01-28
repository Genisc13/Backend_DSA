package edu.upc.dsa.models;

public class ProfilePicture {
    String newProfilePicture;
    public ProfilePicture(){}
    public ProfilePicture(String profile){
        this.newProfilePicture=profile;
    }
    public String getProfilePicture() {
        return newProfilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.newProfilePicture = profilePicture;
    }
}

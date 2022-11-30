package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Status {
    String idPlayer;
    Integer currentlyPlaying;
    Integer coins;
    Integer experience;
    List<Gadget> gadgetsBought;

    public Status() {}
    public Status(String idPlayer, Integer currentlyPlaying, Integer coins, Integer experience) {
        this.idPlayer = idPlayer;
        this.currentlyPlaying = currentlyPlaying;
        this.coins = coins;
        this.experience = experience;
        this.gadgetsBought = new ArrayList<>();

    }
    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Integer getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(Integer currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getExperience() {
        return experience;
    }

    public List<Gadget> getGadgetsBought() {
        return gadgetsBought;
    }

    public void setGadgetsBought(List<Gadget> gadgetsBought) {
        this.gadgetsBought = gadgetsBought;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
    public void addGadget(Gadget gadget){
        this.gadgetsBought.add(gadget);
    }
}

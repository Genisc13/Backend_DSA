package edu.upc.dsa.models;

public class GameInfo {
    String idUser;
    String gameInfo;

    public GameInfo(){}

    public GameInfo(String idUser, String gameInfo) {
        this.idUser = idUser;
        this.gameInfo = gameInfo;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(String gameInfo) {
        this.gameInfo = gameInfo;
    }
}

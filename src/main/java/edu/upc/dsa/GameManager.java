package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;

import java.util.List;
import java.util.Map;

public interface GameManager {

    public int numUsers();
    public int numGadgets();
    public String addUser(String name, String surname, String date, String mail, String password) throws EmailAlreadyBeingUsedException;
    public Map<String, User> getUsers();
    public User getUser(String idUser) throws UserDoesNotExistException;
    public String userLogin(Credentials credentials) throws IncorrectCredentialsException;
    public List<Gadget> gadgetList();
    public void addGadget(String idGadget, int cost, String description, String unityShape);
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException;
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, UserDoesNotExistException;
    public Gadget getGadget(String id) throws GadgetDoesNotExistException;
    public Gadget deleteGadget(String id) throws GadgetDoesNotExistException;


}

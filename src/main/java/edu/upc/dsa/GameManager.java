package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;

import java.util.List;
import java.util.Map;

public interface GameManager {

    public int size();
    public String addUser(String name, String surname, String date, String mail, String password) throws EmailAlreadyBeingUsedException;
    public Map<String, User> getUsers();

    void userLogin(Credentials credentials) throws IncorrectCredentialsException;
    public List<Gadget> gadgetList();
    public void addGadget(String idGadget, int cost, String description, String unity_Shape);
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException;
    public void buyGadget(String idGadget, String idUser) throws IncorrectCredentialsException, NotEnoughMoneyException, GadgetDoesNotExistException;
    public Gadget getGadget(String id) throws GadgetDoesNotExistException;
    public Gadget deleteGadget(String id) throws GadgetDoesNotExistException;


}

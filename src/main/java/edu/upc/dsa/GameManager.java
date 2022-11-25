package edu.upc.dsa;

import edu.upc.dsa.exemptions.*;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Gadget;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UsersWithoutPassword;

import java.util.List;
import java.util.Map;

public interface GameManager {

    public int size();
    public String addUser(String name, String surname, String date, String mail, String password) throws EmailAlreadyBeingUsedException;
    public Map<String, User> getUsers();

    public List<Gadget> gadgetList();
    public Gadget getGadget(String id);
    public void deleteGadget(String id);
    public void LogIn(Credentials credentials) throws IncorrectCredentialsException;

}

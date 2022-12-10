package edu.upc.dsa;

import arg.crud.FactorySession;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Gadget;
import edu.upc.dsa.models.User;
import arg.crud.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameManagerDBImpl implements GameManager{

    Session session;

    public GameManagerDBImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/rooms","rooms", "rooms");
    }

    @Override
    public int numUsers() {
        return 0;
    }

    @Override
    public int numGadgets() {
        return 0;
    }

    @Override
    public String addUser(String name, String surname, String date, String mail, String password) throws EmailAlreadyBeingUsedException {
        User user = new User(name, surname, date, mail, password);
        this.session.save(user);
        return user.getIdUser();
    }

    @Override
    public Map<String, User> getUsers() {
        return null;
    }

    @Override
    public void userLogin(Credentials credentials) throws IncorrectCredentialsException {
        List<Object> users = this.session.findAll(User.class);
        for(Object user : users) {
            User u = (User) user;
            if(Objects.equals(u.getEmail(), credentials.getEmail())) {
                return;
            }
        }
        throw new IncorrectCredentialsException();
    }

    @Override
    public List<Gadget> gadgetList() {
        return null;
    }

    @Override
    public void addGadget(String idGadget, int cost, String description, String unityShape) {
        Gadget gadget=new Gadget(idGadget,cost,description,unityShape);
        this.session.save(gadget);
    }

    @Override
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException {

    }

    @Override
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, IncorrectIdException {

    }

    @Override
    public Gadget getGadget(String id) throws GadgetDoesNotExistException {
        return null;
    }

    @Override
    public Gadget deleteGadget(String id) throws GadgetDoesNotExistException {
        return null;
    }
}

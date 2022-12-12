package edu.upc.dsa;

import arg.crud.FactorySession;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Gadget;
import edu.upc.dsa.models.Purchase;
import edu.upc.dsa.models.User;
import arg.crud.Session;
import org.apache.log4j.Logger;

import java.util.*;

public class GameManagerDBImpl implements GameManager{

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    Session session;
    private static GameManager instance;
    List<Gadget> gadgetList;
    Map<String, User> users;

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerDBImpl();
        return instance;
    }

    public GameManagerDBImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/rooms","rooms", "rooms");
        this.gadgetList = new ArrayList<>();
        this.users = new HashMap<>();
        this.users = this.getUsers();
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
        List<Object> usersList= this.session.findAll(User.class);
        for(int i=0; i<usersList.size();i++){
            User user=(User) usersList.get(i);
            this.users.put(user.getIdUser(),user);
        }
        return this.users;

    }

    @Override
    public String userLogin(Credentials credentials) throws IncorrectCredentialsException {
        List<Object> usersList = this.session.findAll(User.class);
        for(Object user : usersList) {
            User u = (User) user;
            if(Objects.equals(u.getEmail(), credentials.getEmail())) {
                return u.getIdUser();
            }
        }
        throw new IncorrectCredentialsException();
    }

    @Override
    public List<Gadget> gadgetList() {
        List<Object> gadgets= this.session.findAll(Gadget.class);
        for(int i=0; i<gadgets.size();i++){
            Gadget gadget=(Gadget) gadgets.get(i);
            this.gadgetList.add(gadget);
        }
        return this.gadgetList;
    }

    @Override
    public void addGadget(String idGadget, int cost, String description, String unityShape) {
        Gadget gadget=new Gadget(idGadget,cost,description,unityShape);
        this.gadgetList.add(gadget);
        this.session.save(gadget);
    }

    @Override
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException {

    }

    @Override
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, UserDoesNotExistException {
        logger.info("buyGadget("+idGadget+", "+idUser+")");

        Gadget gadget = findGadget(idGadget);
        isGadgetNull(gadget);
        User user = findUser(idUser);
        isUserNull(user);

        int money = user.getCoins();
        int cost = gadget.getCost();
        if (money < cost){
            logger.warn(cost+" is not enough money");
            throw new NotEnoughMoneyException();
        }

        logger.info("Gadget bought");
        this.users.get(idUser).setCoins(money-cost);

        Purchase purchase= new Purchase(idUser,idGadget);
        this.session.save(purchase);
    }

    @Override
    public Gadget getGadget(String id) throws GadgetDoesNotExistException {
        return null;
    }

    @Override
    public Gadget deleteGadget(String id) throws GadgetDoesNotExistException {
        return null;
    }

    public Gadget findGadget(String idGadget) {
        return this.gadgetList.stream()
                .filter(x->idGadget.equals(x.getIdGadget()))
                .findFirst()
                .orElse(null);
    }

    public User findUser(String userIdName) {
        return this.users.get(userIdName);
    }

    public void isGadgetNull(Gadget gadget) throws GadgetDoesNotExistException {
        if(gadget==null) {
            logger.warn("Gadget does not exist");
            throw new GadgetDoesNotExistException();
        }
    }

    public void isUserNull(User user) throws UserDoesNotExistException {
        if(user==null) {
            logger.warn("User does not exist EXCEPTION");
            throw new UserDoesNotExistException();
        }
    }
}

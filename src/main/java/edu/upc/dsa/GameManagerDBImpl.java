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

    Session session;
    private static GameManager instance;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

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
        this.gadgetList = this.gadgetList();
    }

    @Override
    public int numUsers() {
        return users.size();
    }

    @Override
    public int numGadgets() {
        return gadgetList.size();
    }

    @Override
    public String addUser(String name, String surname, String date, String email, String password) throws EmailAlreadyBeingUsedException {
        User user = new User(name, surname, date, email, password);
        for (User u : this.users.values()) {
            if (Objects.equals(u.getEmail(), email)) {
                throw new EmailAlreadyBeingUsedException();
            }
        }
        this.session.save(user);
        logger.info("User has been added correctly in DB with id "+user.getIdUser());
        return user.getIdUser();
    }

    @Override
    public Map<String, User> getUsers() {
        List<Object> usersList= this.session.findAll(User.class);
        for(int i=0; i<usersList.size();i++) {
            User user = (User) usersList.get(i);
            this.users.put(user.getIdUser(), user);
        }
        logger.info("User list has been created correctly its size is: "+this.users.size());
        return this.users;

    }

    @Override
    public User getUser(String idUser) throws UserDoesNotExistException {
        logger.info("Identifier saved: "+idUser);
        User user = this.users.get(idUser);
        isUserNull(user);
        return user;
    }

    @Override
    public String userLogin(Credentials credentials) throws IncorrectCredentialsException {
        List<Object> usersList = this.session.findAll(User.class);
        logger.info("Starting logging...");
        for(Object user : usersList) {
            User u = (User) user;
            if(u.validCredentials(credentials)) {
                logger.info("Log in has been done correctly!");
                return u.getIdUser();
            }
        }
        logger.info("Incorrect credentials, try again.");
        throw new IncorrectCredentialsException();
    }

    @Override
    public List<Gadget> gadgetList() {
        List<Object> gadgets= this.session.findAll(Gadget.class);
        for(int i=0; i<gadgets.size();i++){
            Gadget gadget=(Gadget) gadgets.get(i);
            this.gadgetList.add(gadget);
        }
        logger.info("The list of gadgets has a size of "+this.gadgetList.size());
        return this.gadgetList;
    }

    @Override
    public void addGadget(String idGadget, int cost, String description, String unityShape) {
        Gadget gadget=new Gadget(idGadget,cost,description,unityShape);
        this.gadgetList.add(gadget);
        this.session.save(gadget);
        logger.info("Gadget correctly added in DB.");
    }

    @Override
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException {

    }

    @Override
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, UserDoesNotExistException {
        logger.info("Starting buyGadget("+idGadget+", "+idUser+")");

        Gadget gadget = findGadget(idGadget);
        isGadgetNull(gadget);
        User user = findUser(idUser);
        isUserNull(user);

        try {
            user.purchaseGadget(gadget);
        } catch (NotEnoughMoneyException e) {
            logger.warn("Not enough money exception");
            throw new NotEnoughMoneyException();
        }
        logger.info("Gadget bought");
        this.session.update(user);

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

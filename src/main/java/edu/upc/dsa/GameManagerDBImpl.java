package edu.upc.dsa;

import edu.upc.eetac.dsa.FactorySession;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Gadget;
import edu.upc.dsa.models.Purchase;
import edu.upc.dsa.models.User;
import edu.upc.eetac.dsa.Session;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class GameManagerDBImpl implements GameManager{
    Session session;
    private static GameManager instance;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    List<Object> gadgetList;
    Map<String, User> users;

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerDBImpl();
        return instance;
    }

    public GameManagerDBImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/rooms","rooms", "rooms");
        this.gadgetList = new ArrayList<Object>();
        this.users = new HashMap<>();
        this.users = this.getUsers();
    }

    @Override
    public int numUsers() {
        return this.session.findAll(User.class).size();
    }

    @Override
    public int numGadgets() {
        return this.session.findAll(Gadget.class).size();
    }

    @Override
    public String addUser(String name, String surname, String date, String email, String password) throws EmailAlreadyBeingUsedException, SQLException {
        User user = new User(name, surname, date, email, password);
        User userMatch = (User) this.session.get(User.class, "email", email);
        try {
            isUserNull(userMatch);
            throw new EmailAlreadyBeingUsedException();
        } catch (UserDoesNotExistException e) {
            this.session.save(user);
            logger.info("User has been added correctly in DB with id "+user.getIdUser());
            return user.getIdUser();
        }
    }

    @Override
    public Map<String, User> getUsers() {
        List<Object> usersList= this.session.findAll(User.class);
        for(int i=0; i<usersList.size();i++) {
            User user = (User) usersList.get(i);
            this.users.put(user.getIdUser(), user);
        }
        logger.info("User list has been created correctly its size is: "+usersList.size());
        return this.users;
    }

    @Override
    public User getUser(String idUser) throws UserDoesNotExistException {
        logger.info("Identifier saved: "+idUser);
        User user = (User) this.session.get(User.class, parseInt(idUser));
        isUserNull(user);
        return user;
    }

    @Override
    public String userLogin(Credentials credentials) throws IncorrectCredentialsException, SQLException {
        logger.info("Starting logging...");
        HashMap<String, String> credentialsHash = new HashMap<String, String>();
        credentialsHash.put("email", credentials.getEmail());
        credentialsHash.put("password", credentials.getPassword());

        List<Object> userMatch = this.session.findAll(User.class, credentialsHash);

        if (userMatch.size()!=0){
            logger.info("Log in has been done correctly!");
            User user = (User) userMatch.get(0);
            return user.getIdUser();
        }

        logger.info("Incorrect credentials, try again.");
        throw new IncorrectCredentialsException();

    }

    @Override
    public List<Gadget> gadgetList() {
        List<Object> gadgets= this.session.findAll(Gadget.class);
        List<Gadget> res = new ArrayList<>();
        for (Object o : gadgets){
            res.add((Gadget) o);
        }
        logger.info("The list of gadgets has a size of "+this.gadgetList.size());
        return res;
    }

    @Override
    public void addGadget(String idGadget, int cost, String description, String unityShape) {
        Gadget gadget=new Gadget(idGadget,cost,description,unityShape);
        this.session.save(gadget);
        logger.info("Gadget correctly added in DB.");
    }

    @Override
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException {
        this.session.update(gadget);
    }

    @Override
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, UserDoesNotExistException {
        logger.info("Starting buyGadget("+idGadget+", "+idUser+")");

        Gadget gadget = getGadget(idGadget);
        User user = getUser(idUser);

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
        Gadget gadget = (Gadget) this.session.get(Gadget.class, parseInt(id));
        isGadgetNull(gadget);
        return gadget;
    }

    @Override
    public Gadget deleteGadget(String id) throws GadgetDoesNotExistException {
        Gadget gadget = (Gadget) this.session.get(Gadget.class, parseInt(id));
        isGadgetNull(gadget);
        this.session.delete(gadget);
        return gadget;
    }

    public Gadget findGadget(String idGadget) {
        Gadget gadget = (Gadget) this.session.get(Gadget.class, parseInt(idGadget));
        return gadget;
    }

    public User findUser(String userIdName) {
        User user = (User) this.session.get(User.class, parseInt(userIdName));
        return user;
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

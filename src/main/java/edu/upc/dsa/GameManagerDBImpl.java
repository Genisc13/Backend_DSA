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
        this.gadgetList=new ArrayList<>();
        this.users=new HashMap<>();
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
        this.session.save(gadget);
    }

    @Override
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException {

    }

    @Override
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, IncorrectIdException {
        logger.info("buyGadget("+idGadget+", "+idUser+")");
        int position = searchGadgetPosition(idGadget);
        if (position==-1){
            logger.warn("Gadget does not exist");
            throw new GadgetDoesNotExistException();
        }
        else{
            User u = this.users.get(idUser);
            if (u==null) {
                logger.warn("Identifier not found");
                throw new IncorrectIdException();
            }
            int money = this.users.get(idUser).getCoins();
            int cost = this.gadgetList.get(position).getCost();
            if (money < cost){
                logger.warn(cost+" is not enough money");
                throw new NotEnoughMoneyException();
            }
            else {
                logger.info("Gadget bought");
                this.users.get(idUser).setCoins(money-cost);
                Purchase newPurchase= new Purchase(idUser,idGadget);
                this.session.save(newPurchase);
            }
        }

    }
    public int searchGadgetPosition(String idGadget){
        logger.info("searchGadgetPosition("+idGadget+")");
        int i=0;
        for (Gadget g: this.gadgetList) {
            if (g.getIdGadget().equals(idGadget)) {
                return i;
            }
            i+=1;
        }
        return -1;
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

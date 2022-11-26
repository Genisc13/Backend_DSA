package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Credentials;

import java.util.*;

import edu.upc.dsa.models.Gadget;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    Map<String, User> users;

    ArrayList<Gadget> gadgetList;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.users=new HashMap<>();
        this.gadgetList=new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public String addUser(String name, String surname, String date, String mail, String password) throws EmailAlreadyBeingUsedException{
        logger.info("Adding a new User Starting...");
        User newUser= new User(name,surname,date,mail,password);
        List<User> userList = new ArrayList<>(this.users.values());
        logger.info("Checking whether this users exists...");

        for(User u : userList){
            if(Objects.equals(u.getEmail(), mail)){
                logger.info("The user already exists!");
                throw new EmailAlreadyBeingUsedException();
            }
        }
        this.users.put(newUser.id, newUser);
        logger.info("User " + newUser.getName() +" has been added correctly with the id " + newUser.getUserId());
        return (newUser.id);
    }

    public Map<String,User> getUsers(){
        return this.users;
    }

    @Override
    public void LogIn(Credentials credentials) throws IncorrectCredentialsException {
        List<User> listUsers= new ArrayList<>(this.users.values());
        for (User u: listUsers){
            if(!Objects.equals(u.getEmail(), credentials.getEmail()) && !Objects.equals(u.getPassword(), credentials.getPassword())){
                logger.info("Wrong credentials!");
                throw new IncorrectCredentialsException();
            }
        }
        logger.info("Nice work logging the user!");
    }
    public List<Gadget> gadgetList(){
        return this.gadgetList;
    }

    @Override
    public void addGadget(String idGadget, int cost, String description, String unity_Shape) {
        Gadget g= new Gadget(idGadget,cost,description,unity_Shape);
        gadgetList.add(g);
    }

    @Override
    public void updateGadget(Gadget gadget) throws GadgetDoesNotExistException {
        logger.info("updateGadget("+gadget+")");
        Integer position = searchGadgetPosition(gadget.getId());
        if (position==-1){
            logger.warn("This gadget not found: " + gadget.getId());
            throw new GadgetDoesNotExistException();
        }
        else{
            gadgetList.get(position).setCost(gadget.getCost());
            gadgetList.get(position).setId(gadget.getId());
            gadgetList.get(position).setDescription(gadget.getDescription());
            gadgetList.get(position).setUnity_Shape(gadget.getUnity_Shape());
            logger.info("Gadget updated");
        }
    }
    public int searchGadgetPosition(String idGadget){
        logger.info("searchGadgetPosition("+idGadget+")");
        int i=0;
        for (Gadget g: this.gadgetList) {
            if (g.getId().equals(idGadget)) {
                i=i+1;
                return i;
            }
        }
        return -1;
    }
    @Override
    public void buyGadget(String idGadget, String idUser) throws IncorrectCredentialsException, NotEnoughMoneyException, GadgetDoesNotExistException {
        logger.info("buyGadget("+idGadget+", "+idUser+")");
        Integer position = searchGadgetPosition(idGadget);
        if (position==-1){
            logger.warn("Gadget does not exist");
            throw new GadgetDoesNotExistException();
        }
        else{
            User u = users.get(idUser);
            if (u==null) {
                logger.warn("Credentials not found");
                //no se si voldrieu fer una excepcio mes concreta de nomes l'id
                throw new IncorrectCredentialsException();
            }
            int money = users.get(idUser).getStatus().getCoins();
            Integer cost = gadgetList.get(position).getCost();
            if (money < cost){
                logger.warn(cost+" is not enough money");
                throw new NotEnoughMoneyException();
            }
            else {
                logger.info("Gadget bought");
                u.updateStatus(gadgetList.get(position));
            }
        }
    }

    public Gadget getGadget(String idGadget) throws GadgetDoesNotExistException {
        logger.info("getGadget("+idGadget+")");
        /*for (Gadget t: this.gadgetList) {
            if (t.getId().equals(idGadget)) {
                logger.info("getGadget("+idGadget+"): "+t);
                return t;
            }
        }*/
        Integer position = searchGadgetPosition(idGadget);
        if (position==-1){
            logger.warn("not found " + idGadget);
            throw new GadgetDoesNotExistException();
        }
        else{
            logger.info("Gadget found"+idGadget+")");
            return gadgetList.get(position);
        }
    }

    public void deleteGadget(String id) throws GadgetDoesNotExistException {
        Gadget t = this.getGadget(id);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.gadgetList.remove(t);
    }
}
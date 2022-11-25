package edu.upc.dsa;

import edu.upc.dsa.exemptions.*;
import edu.upc.dsa.models.Credentials;

import java.util.*;

import edu.upc.dsa.models.Gadget;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UsersWithoutPassword;
import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    Map<String, User> users;

    ArrayList<Gadget> gadgetlist;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.users=new HashMap<>();
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
        return this.gadgetlist;
    }
    public Gadget getGadget(String id){
        logger.info("getObjeto("+id+")");

        for (Gadget t: this.gadgetlist) {
            if (t.getId().equals(id)) {
                logger.info("getObjeto("+id+"): "+t);

                return t;
            }
        }
        logger.warn("not found " + id);
        return null;
    }
    public void deleteGadget(String id){
        Gadget t = this.getGadget(id);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.gadgetlist.remove(t);
    }
}
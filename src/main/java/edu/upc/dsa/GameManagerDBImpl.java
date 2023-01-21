package edu.upc.dsa;

//import arg.crud.*;
import edu.upc.dsa.CRUD.FactorySession;
import edu.upc.dsa.CRUD.Session;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.*;
public class GameManagerDBImpl implements GameManager{
    Session session;
    private static GameManager instance;
    User userForComparable;
    final static Logger logger = Logger.getLogger(GameManagerDBImpl.class);

    public static GameManager getInstance() {
        logger.info("Hey im here making the instance of the db implementation");
        if (instance==null) instance = new GameManagerDBImpl();
        return instance;
    }

    public GameManagerDBImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/rooms","rooms", "rooms");
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
    public int numFAQs() {
        return this.session.findAll(FAQ.class).size();
    }

    @Override
    public void addFAQ(String q, String a) throws SQLException, FAQAlreadyBeingAskedException {
        logger.info("Adding a FAQ...");
        FAQ faq = new FAQ(q,a);
        logger.info("New question: "+q);
        try{
            faq = (FAQ) this.session.get(FAQ.class, "questionFAQ", q);
            logger.info("FAQ cannot be added because this question is already in the system :(");
            throw new FAQAlreadyBeingAskedException();
        } catch(SQLException e) {
            this.session.save(faq);
            logger.info("FAQ has been added correctly in DB");
        }
    }

    @Override
    public String addUser(String name, String surname, String date, String email, String password, String profilePicture) throws EmailAlreadyBeingUsedException, SQLException {
        logger.info("Adding a user...");
        User user = new User(name, surname, date, email, password,profilePicture);
        try{
            user = (User) this.session.get(User.class, "email", email);
        } catch(SQLException e) {
            this.session.save(user);
            logger.info("User has been added correctly in DB with id "+user.getIdUser());
            return user.getIdUser();
        }

        logger.info("User cannot be added because this email is already being used :(");
        throw new EmailAlreadyBeingUsedException();
    }

    @Override
    public Map<String, User> getUsers() {
        logger.info("Getting all users...");
        List<Object> usersList= this.session.findAll(User.class);
        HashMap<String, User> users = new HashMap<>();
        for(int i = 0; i < usersList.size(); i++) {
            User user = (User) usersList.get(i);
            users.put(user.getIdUser(), user);
        }
        logger.info("User list has been created correctly its size is: "+usersList.size());
        return users;
    }

    @Override
    public User getUser(String idUser) throws UserDoesNotExistException {
        logger.info("Getting user with id: "+idUser);
        try {
            User user = (User) this.session.get(User.class, "id", (idUser));
            return user;
        } catch(SQLException e) {
            logger.warn("User does not exist EXCEPTION");
            throw new UserDoesNotExistException();
        }
    }

    @Override
    public String userLogin(Credentials credentials) throws IncorrectCredentialsException, SQLException {
        logger.info("Starting logging...");
        HashMap<String, String> credentialsHash = new HashMap<>();
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
        logger.info("Getting all gadgets...");
        List<Object> gadgets= this.session.findAll(Gadget.class);
        List<Gadget> res = new ArrayList<>();
        for (Object o : gadgets){
            res.add((Gadget) o);
        }
        logger.info("The list of gadgets has a size of "+res.size());
        return res;
    }

    @Override
    public List<FAQ> FAQsList() {
        logger.info("Getting all the FAQs...");
        List<Object> preguntas = this.session.findAll(FAQ.class);
        List<FAQ> res = new ArrayList<>();
        for (Object o : preguntas){
            res.add((FAQ) o);
        }
        logger.info("The list of FAQs has a size of "+res.size());
        return res;
    }

    @Override
    public void addGadget(String idGadget, int cost, String description, String unityShape) throws SQLException, GadgetWithSameIdAlreadyExists {
        logger.info("Adding a gadget...");
        Gadget gadget = new Gadget(idGadget, cost, description, unityShape);
        try{
            gadget = (Gadget) this.session.get(Gadget.class, "id", idGadget);
        } catch(SQLException e) {
            this.session.save(gadget);
            logger.info("Gadget has been added correctly in DB with id "+gadget.getIdGadget());
            return;
        }

        logger.info("User cannot be added because this email is already being used :(");
        throw new GadgetWithSameIdAlreadyExists();
    }

    @Override
    public void updateGadget(Gadget gadget) throws SQLException {
        this.session.update(gadget);
    }

    @Override
    public void buyGadget(String idGadget, String idUser) throws NotEnoughMoneyException, GadgetDoesNotExistException, UserDoesNotExistException, SQLException {
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
        try{
            Gadget gadget = (Gadget) this.session.get(Gadget.class, "id", (id));
            return gadget;
        } catch (SQLException e) {
            logger.warn("Gadget does not exist");
            throw new GadgetDoesNotExistException();
        }
    }

    @Override
    public Gadget deleteGadget(String id) throws GadgetDoesNotExistException {
        try{
            Gadget gadget = (Gadget) this.session.get(Gadget.class, "id", (id));
            this.session.delete(gadget);
            return gadget;
        } catch (SQLException e) {
            logger.warn("Gadget does not exist");
            throw new GadgetDoesNotExistException();
        }
    }

    @Override
    public void updateUserPassword(PasswordChangeRequirements passwordChangeRequirements) throws SQLException, IncorrectCredentialsException {
        logger.info("I am trying to update the user password!");
        User userToUpdate = (User) this.session.get(User.class, "id", passwordChangeRequirements.getIdUser());
        Credentials oldCredentials=new Credentials(passwordChangeRequirements.getEmail(), passwordChangeRequirements.getOldPassword());
        logger.info("The old password is "+oldCredentials.getPassword());
        if(!userToUpdate.validCredentials(oldCredentials))
            throw new IncorrectCredentialsException();
        logger.info("I am setting the password!");
        userToUpdate.setPassword(passwordChangeRequirements.getNewPassword());
        logger.info("Updating the password!");
        this.session.update(userToUpdate);
    }

    @Override
    public List<Gadget> purchasedGadgets(String idUser) throws SQLException, NoPurchaseWasFoundForIdUser, GadgetDoesNotExistException {
        logger.info("Looking for gadgets purchased by user with id: " + idUser);
        HashMap<String, String> user = new HashMap<>();
        user.put("idUser", idUser);

        List<Object> purchaseMatch = this.session.findAll(Purchase.class, user);
        List<Gadget> gadgetsOfUser=new ArrayList<>();

        if (purchaseMatch.size()!=0){
            logger.info("Purchase were found correctly for given user id!");
            for(Object object : purchaseMatch) {
                Purchase purchase = (Purchase) object;
                try{
                    gadgetsOfUser.add(this.getGadget(purchase.getIdGadget()));
                }
                catch(Exception e){
                    throw new GadgetDoesNotExistException();
                }
            }
            return gadgetsOfUser;
        }
        logger.info("No purchase was found for given user id");
        throw new NoPurchaseWasFoundForIdUser();
    }

    public List<User> rankingOfUsers() throws SQLException{
        logger.info("Getting all users...");
        List<Object> usersList= this.session.findAll(User.class);
        List<User> usersRanking=new ArrayList<>();
        for(Object o:usersList){
            User user =(User) o;
            usersRanking.add(user);
        }
        usersRanking.sort((u1,u2)->Integer.compare(u2.getExperience(),u1.getExperience()));
        return usersRanking;
    }

    public void deletePurchasedGadget(Purchase purchase){
        logger.info("Deleting the purchase of the gadget!");
        this.session.delete(purchase);
        logger.info("The purchase has been correctly deleted :)");
    }

    @Override
    public void postChatMessage(ChatMessage chatMessage) throws SQLException {
        logger.info("Cooking a new message of the chat");
        this.session.save(chatMessage);
        logger.info("The new message has been correctly created.");

    }

    @Override
    public List<ChatMessage> getChat(Integer firstMessage){
        logger.info("Getting forum...");
        List<ChatMessage> messages = new ArrayList<>();
        List<Object> allMessages = this.session.getMessagesSorted();
        for(int i = firstMessage; i < allMessages.size(); i++) {
            ChatMessage user = (ChatMessage) allMessages.get(i);
            messages.add(user);
        }
        logger.info("The list of chats has size of " + messages.size());
        return messages;
    }

    @Override
    public void reportAbuse(Abuse abuse) throws SQLException {
        logger.info("Report of the abuse being cooked");
        this.session.save(abuse);
        logger.info("The abuse is informed by "+abuse.getInformer()+" and its description is "+abuse.getMessage());
    }
}


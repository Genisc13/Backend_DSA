package edu.upc.dsa;


import edu.upc.dsa.exceptions.EmailAlreadyBeingUsedException;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class GameManagerImplTest {
    GameManager gameManager;
    String idAlba;
    String idPaula;
    String idMaria;
    String idGenis;
    String idGuillem;

    @Before
    public void setUp() throws EmailAlreadyBeingUsedException {
        gameManager = new GameManagerImpl();
        this.idAlba= gameManager.addUser("Alba","Roma", "23112001","albaroma@gmail.com","Test123");
        this.idPaula= gameManager.addUser( "Paula","Sopena", "22052001","paulasopena@gmail.com","Test123");
        this.idMaria= gameManager.addUser("Maria", "Ubiergo", "02112001","mariaubiergo@gmail.com","Test123");
        this.idGenis= gameManager.addUser("Genis","Castillo", "23112001","geniscastillo@gmail.com","Test123");
        this.idGuillem= gameManager.addUser( "Guillem","Purti", "22052001","guillempurti@gmail.com","Test123");

        gameManager.addGadget("1A", 10, "Fire", "Fire flames");
        gameManager.addGadget("2A", 4, "Water", "Blue water");
        gameManager.addGadget("3A", 5, "Air", "Fresh air");

    }

    @After
    public void tearDown(){this.gameManager = null;}

    @Test
    public void testAddUserAlreadyExistingThrowsException(){
        Assert.assertEquals(5, this.gameManager.numUsers());
        Assert.assertThrows(EmailAlreadyBeingUsedException.class, ()->this.gameManager.addUser("test", "test", "test", "albaroma@gmail.com", "test"));
        Assert.assertEquals(5, this.gameManager.numUsers());
    }

    @Test
    public void testAddUserIsPossible() throws EmailAlreadyBeingUsedException {
        Assert.assertEquals(5, this.gameManager.numUsers());
        this.gameManager.addUser("test", "test", "test", "susana@gmail.com", "test");
        Assert.assertEquals(6, this.gameManager.numUsers());
    }

    @Test
    public void testGetUsersReturnsUsers(){
        Assert.assertEquals(5, this.gameManager.numUsers());
        Map<String,User> users = this.gameManager.getUsers();
        Assert.assertEquals(6, this.gameManager.numUsers());
    }

}

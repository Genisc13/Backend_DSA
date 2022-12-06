package edu.upc.dsa;

import edu.upc.dsa.exceptions.EmailAlreadyBeingUsedException;
import edu.upc.dsa.exceptions.IncorrectCredentialsException;
import edu.upc.dsa.models.Credentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameManagerDBImplTest {
    GameManager gameManager;

    @Before
    public void setUp() {
        gameManager = new GameManagerDBImpl();
    }

    @After
    public void tearDown(){this.gameManager = null;}

    @Test
    public void testAddUser() throws EmailAlreadyBeingUsedException {
        this.gameManager.addUser("Alba","Roma","23/11/2001","albar@gmail.com","123456");

    }

    @Test
    public void testAddGadget() {
        this.gameManager.addGadget("A1", 19, "molt guai", "forma");

    }

    @Test
    public void testUserLogin() throws IncorrectCredentialsException {
        this.gameManager.userLogin(new Credentials("albar@gmail.com","123456"));

    }
}

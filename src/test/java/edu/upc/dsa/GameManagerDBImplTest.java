package edu.upc.dsa;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Credentials;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class GameManagerDBImplTest {
    GameManager gameManager;

    @Before
    public void setUp() throws FileNotFoundException {
        gameManager = new GameManagerDBImpl();
    }

    @After
    public void tearDown(){this.gameManager = null;}

}

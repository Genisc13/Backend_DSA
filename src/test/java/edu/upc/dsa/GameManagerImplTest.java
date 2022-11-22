package edu.upc.dsa;


import edu.upc.dsa.exemptions.EmailAlreadyBeingUsedException;
import edu.upc.dsa.exemptions.ProductDoesNotExistException;
import edu.upc.dsa.exemptions.UserDoesNotExistException;
import edu.upc.dsa.exemptions.UserHasNotMoneyException;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GameManagerImplTest {
    GameManager sm;
    String idAlba;
    String idPaula;
    String idAlberto;
    @Before
    public void setUp() throws EmailAlreadyBeingUsedException {
        sm = new GameManagerImpl();
        this.idAlba=sm.addUser("Alba","Roma", "23112001","albaroma@gmail.com","123456");
        this.idPaula=sm.addUser( "Paula","Sopena", "22052001","paulasopena@gmail.com","123456");
        this.idAlberto=sm.addUser("Maria", "Ubiergo", "02112001","mariaubiergo@gmail.com","123456");
    }
    @After
    public void tearDown(){this.sm=null;}







}

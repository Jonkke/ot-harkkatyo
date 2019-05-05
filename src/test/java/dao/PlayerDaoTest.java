/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Player;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.DatabaseService;

/**
 *
 * @author jonask
 */
public class PlayerDaoTest {

    DatabaseService dbs;
    PlayerDao pd;

    public PlayerDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.dbs = new DatabaseService();
        this.dbs.connect("testdb");
        this.pd = new PlayerDao(this.dbs);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newDatabaseHasOnlyDefaulPlayer() {
        List<Player> players = this.pd.getAll();
        assertEquals(1, players.size());
        assertEquals("default", players.get(0).getName());
    }

    @Test
    public void fetchingPlayerWithIdFromDBWorks() {
        Player p = this.pd.get(1);
        assertEquals("default", p.getName());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Score;
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
public class ScoreDaoTest {

    DatabaseService dbs;
    ScoreDao sd;

    public ScoreDaoTest() {
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
        this.sd = new ScoreDao(this.dbs);
    }

    @After
    public void tearDown() {
    }

     @Test
     public void newDatabaseHasNoScores() {
         List<Score> scores = this.sd.getAll();
         assertEquals(0, scores.size());
     }
}

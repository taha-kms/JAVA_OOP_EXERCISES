package it.polito.extgol.test;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import it.polito.extgol.Board;
import it.polito.extgol.Cell;
import it.polito.extgol.Coord;
import it.polito.extgol.EventType;
import it.polito.extgol.ExtendedGameOfLife;
import it.polito.extgol.Game;
import it.polito.extgol.Generation;
import it.polito.extgol.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import static it.polito.extgol.test.TestBranchUtils.assumeBranch;

public class ExtGOLR3GameTests {
    private ExtendedGameOfLife facade;
    private Game game;
    private Board board;

    /**
     * Close JPA after all tests.
     */
    @AfterClass
    public static void closeDB() {
        JPAUtil.close();
    }

    /**
     * Prepare a clean database and new game before each test.
     */
    @Before
    public void setUp() {
        clearDatabase();
        facade = new ExtendedGameOfLife();
        game  = Game.createExtended("TestGame", 6, 6);
        board = game.getBoard();
    }

    private void clearDatabase() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (String table : List.of("generation_state", "generation", "board", "game", "tile")) {
            try {
                em.createNativeQuery("DELETE FROM " + table).executeUpdate();
            } catch (Exception e) {
                System.out.println(table +" does not exist!");
            }
        }
        tx.commit();
        em.close();
    }

    // R3 Game Extended Behaviors

    @Test
    public void testR3BloomGivesTwoLifePoints() {
        assumeBranch("R3");
        // Start with four alive cells
        Generation.createInitial(game, board,
            List.of(new Coord(1,1), new Coord(1,2), new Coord(2,1), new Coord(2,2))
        );
        
        // Apply BLOOM at step 0, then evolve one generation
        Game result = facade.run(game, 1, Map.of(0, EventType.BLOOM));
        Generation gen1 = result.getGenerations().get(1);

        // Pick any alive cell and verify it gained exactly +2 lifePoints
        Cell sample = facade.getAliveCells(gen1).get(new Coord(1,1));
        int lp = gen1.getEnergyStates().get(sample);
        assertEquals("BLOOM should add +2 lifePoints", 2, lp);
    }

    @Test
    public void testR3CataclysmResetsLifePointsToZero() {
        assumeBranch("R3");
        // Kick off with one alive cell and give it some energy first
        Generation init = Generation.createInitial(game, board, List.of(new Coord(1,1)));
        Cell c0 = board.getTile(new Coord(1,1)).getCell();
        c0.setLifePoints(5);
        init.snapCells();
        
        // Apply CATACLYSM at step 0, then evolve one generation
        Game result = facade.run(game, 1, Map.of(0, EventType.CATACLYSM));
        result.getGenerations().get(1);

        int lp = c0.getLifePoints();
        assertEquals("CATACLYSM should zero out lifePoints", 0, lp);
    }

    @Test
    public void testR3FamineSubtractsOneLifePoint() {
        assumeBranch("R3");
        // Initialize with one alive cell and give it some energy
        Generation init = Generation.createInitial(game, board, List.of(new Coord(1,1)));
        Cell c0 = board.getTile(new Coord(1,1)).getCell();
        c0.setLifePoints(5);
        init.snapCells();
        
        // Apply FAMINE at step 0, then evolve one generation
        Game result = facade.run(game, 1, Map.of(0, EventType.FAMINE));
        result.getGenerations().get(1);

        int lp=c0.getLifePoints();
        assertEquals("FAMINE should subtract 1 lifePoint", 4, lp);
    }
}

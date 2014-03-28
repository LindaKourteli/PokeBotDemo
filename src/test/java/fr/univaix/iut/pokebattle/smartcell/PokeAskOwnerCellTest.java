package fr.univaix.iut.pokebattle.smartcell;

import fr.univaix.iut.pokebattle.jpa.DAOFactoryJPA;
import fr.univaix.iut.pokebattle.jpa.DAOPokemon;
import fr.univaix.iut.pokebattle.jpa.Pokemon;
import fr.univaix.iut.pokebattle.twitter.Tweet;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;

import static org.junit.Assert.assertEquals;

public class PokeAskOwnerCellTest {

    private static EntityManager entityManager;
    private static FlatXmlDataSet dataset;
    private static DatabaseConnection dbUnitConnection;
    private static EntityManagerFactory entityManagerFactory;
    private static DAOPokemon dao;

    PokeAskOwnerCell cell = new PokeAskOwnerCell();

    @BeforeClass
    public static void initTestFixture() throws Exception {

        entityManagerFactory = Persistence.createEntityManagerFactory("pokebattlePUTest");
        entityManager = entityManagerFactory.createEntityManager();
        DAOFactoryJPA.setEntityManager(entityManager);

        dao = DAOFactoryJPA.createDAOPokemon();

        Connection connection = ((EntityManagerImpl) (entityManager.getDelegate())).getServerSession().getAccessor().getConnection();

        dbUnitConnection = new DatabaseConnection(connection);
        //Loads the data set from a file
        dataset = new FlatXmlDataSetBuilder().build(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("pokemonDataset.xml"));
    }

    @AfterClass
    public static void finishTestFixture() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Before
    public void setUp() throws Exception {
        //Clean the data from previous test and insert new data test.
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataset);
    }


    @Test
	public void testEleveur() {
		Pokemon pokemon = new Pokemon("Ronflex", "Linda");	
		cell.setPokemon(pokemon);
		assertEquals("@huyvin My owner is @Linda.", cell.ask(new Tweet("huyvin", "Owner?")));
		
	}

    @Test
    public void testSansEleveur() {
        Pokemon pokemon = new Pokemon("Ronflex");
        cell.setPokemon(pokemon);
        assertEquals("@huyvin No owner", cell.ask(new Tweet("huyvin", "Owner?")));
    }

    @Test
    public void testEleveurBD() throws Exception {
        Pokemon pikachu = dao.getById("Pikachu");
        cell.setPokemon(pikachu);
        assertEquals("@huyvin My owner is @Slydevis.", cell.ask(new Tweet("huyvin", "Owner?")));
    }

    @Test
    public void testNoEleveurBD() throws Exception {
        Pokemon rattata = dao.getById("Rattata");
        cell.setPokemon(rattata);
        assertEquals("@huyvin No owner", cell.ask(new Tweet("huyvin", "Owner?")));
    }
}
package IUT.BoBot.SmartCells;

import fr.univaix.iut.pokebattle.smartcells.PokemonCriesCell;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokemonCriesCellTest {

    PokemonCriesCell cell = new PokemonCriesCell();

    @Test
    public void testSalut() {
        assertEquals("Pika pika", cell.ask("Salut!"));
    }

    @Test
    public void testNotSalut() {
        assertEquals(null, cell.ask("au revoir"));
    }

}